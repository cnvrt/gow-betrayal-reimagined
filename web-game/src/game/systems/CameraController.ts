import * as Phaser from "phaser";
import { NATIVE_HEIGHT, NATIVE_WIDTH } from "../constants";
import { LevelRenderer } from "./LevelRenderer";

/**
 * Viewport & Camera Controller matching `e.m()` (`e.java` lines 1572-1747)
 * and `b.d(int, int)` (`b.java` lines 88-115).
 *
 * Uses 8-bit fixed-point coordinates (`aP = iV << 8`, `aQ = iW << 8`)
 * with velocity-clamped interpolation, directional look-ahead (`±44` px),
 * climbing vertical offset (`+52` px), Group 3 pan zones, Group 7 platform hints,
 * temporary focus target (`az, aA, aB`), and screen shake (`aw, ax, ay`).
 */
export class CameraController {
  private scene: Phaser.Scene;
  private worldWidthPx: number = NATIVE_WIDTH;
  private worldHeightPx: number = NATIVE_HEIGHT;

  // Scroll bounds (`aC = width - 176`, `aD = height - 208`)
  private aC: number = 0;
  private aD: number = 0;

  // Pixel scroll coordinates (`iV`, `iW` in b.java / e.java)
  public scrollX: number = 0;
  public scrollY: number = 0;

  // 8-bit fixed-point scroll coordinates (`aP`, `aQ` in e.java)
  private aP: number = 0;
  private aQ: number = 0;

  // Camera pan offsets (`aJ`, `aK`, `aL = 45` in e.java)
  private aJ: number = 0;
  private aK: number = 0;
  private readonly aL: number = 45;
  private aO: boolean = false;
  private aM: boolean = false;
  private aN: boolean = false;

  // Temporary focus target (`az`, `aA`, `aB` in e.java when a lever opens a door with `da == true`)
  public focusX: number = -1;
  public focusY: number = -1;
  private focusTimerMs: number = 0;

  // Screen shake (`aw`, `ax`, `ay` in e.java lines 1609-1616)
  private shakeTimerMs: number = 0;
  private shakeOffsetX: number = 0;
  private shakeOffsetY: number = 0;

  public freeLookMode: boolean = false;

  constructor(scene: Phaser.Scene) {
    this.scene = scene;
  }

  public setWorldBounds(widthPx: number, heightPx: number): void {
    this.worldWidthPx = Math.max(NATIVE_WIDTH, widthPx);
    this.worldHeightPx = Math.max(NATIVE_HEIGHT, heightPx);
    this.aC = Math.max(0, this.worldWidthPx - NATIVE_WIDTH);
    this.aD = Math.max(0, this.worldHeightPx - NATIVE_HEIGHT);
    this.scene.cameras.main.setBounds(0, 0, this.worldWidthPx, this.worldHeightPx);
  }

  /**
   * Snaps camera immediately on level spawn (`e.m()` when `J == 100`, lines 1732-1744).
   */
  public centerOn(x: number, y: number, facingRight: boolean = true): void {
    const lookAheadX = facingRight ? 44 : -44;
    const targetX = Phaser.Math.Clamp(x - 88 + lookAheadX, 0, this.aC);
    const targetY = Phaser.Math.Clamp(y - 168, 0, this.aD);

    this.scrollX = Math.floor(targetX);
    this.scrollY = Math.floor(targetY);
    this.aP = this.scrollX << 8;
    this.aQ = this.scrollY << 8;
    this.aJ = 0;
    this.aK = 0;
    this.focusX = -1;
    this.focusY = -1;
    this.focusTimerMs = 0;
    this.shakeTimerMs = 0;
    this.shakeOffsetX = 0;
    this.shakeOffsetY = 0;
    this.scene.cameras.main.setScroll(this.scrollX, this.scrollY);
  }

  /**
   * Triggers temporary camera focus on a target (`az, aA` in `e.x(int)` lines 7689-7691).
   */
  public setFocusTarget(x: number, y: number): void {
    this.focusX = x;
    this.focusY = y;
    this.focusTimerMs = 0;
  }

  /**
   * Triggers heavy landing / impact screen shake (`ay = 200` in `e.g()` lines 4399-4402).
   */
  public triggerShake(durationMs: number = 200): void {
    this.shakeTimerMs = durationMs;
  }

  /**
   * Free-look pan (used when Debug Free-Cam is toggled on).
   */
  public panBy(dx: number, dy: number): void {
    this.scrollX = Math.floor(Phaser.Math.Clamp(this.scrollX + dx, 0, this.aC));
    this.scrollY = Math.floor(Phaser.Math.Clamp(this.scrollY + dy, 0, this.aD));
    this.aP = this.scrollX << 8;
    this.aQ = this.scrollY << 8;
    this.scene.cameras.main.setScroll(this.scrollX, this.scrollY);
  }

  /**
   * Exact port of `e.m()` (`e.java` lines 1572-1747):
   * Advances 8-bit fixed-point camera tracking toward Kratos (or temporary focus target).
   */
  public updateTracking(
    dtMs: number,
    kratosX: number,
    kratosY: number,
    kratosW: number,
    kratosH: number,
    facingRight: boolean,
    isClimbing: boolean,
    isRunning: boolean,
    isJumping: boolean,
    levelRenderer?: LevelRenderer
  ): void {
    if (this.freeLookMode) return;

    // 1. Group 7 Platform camera hints (`e.m()` lines 1575-1607)
    let nearHintPlatform = false;
    if (levelRenderer) {
      for (const plat of levelRenderer.platforms) {
        if ((plat.fe & 1) === 0 && (plat.fn || plat.fo)) {
          const sx = plat.fh - this.scrollX;
          const sy = plat.fi - this.scrollY;
          if (
            sx + plat.fj >= 0 &&
            sy + plat.fk >= 0 &&
            sx - plat.fj <= NATIVE_WIDTH &&
            sy - plat.fk <= NATIVE_HEIGHT &&
            Math.abs(kratosY - plat.fi) < 16
          ) {
            if (plat.fn && this.scrollY + 104 < plat.fi - plat.fk) {
              this.aM = true;
              this.aN = false;
              nearHintPlatform = true;
              break;
            }
            if (plat.fo && this.scrollY + 104 > plat.fi) {
              this.aM = false;
              this.aN = true;
              nearHintPlatform = true;
              break;
            }
          }
        }
      }
    }

    if (!nearHintPlatform) {
      this.aM = false;
      this.aN = false;
    }

    // 2. Screen shake (`e.m()` lines 1609-1616)
    if (this.shakeTimerMs > 0) {
      this.shakeTimerMs -= dtMs;
      this.shakeOffsetY = Math.floor(Math.random() * 5) - 2;
      this.shakeOffsetX = Math.floor(Math.random() * 5) - 2;
    } else {
      this.shakeOffsetY = 0;
      this.shakeOffsetX = 0;
    }

    // 3. Target selection (`az >= 0` door focus vs Kratos `ge, gf`, lines 1618-1635)
    let targetX = kratosX;
    let targetY = kratosY;
    if (this.focusX >= 0 && this.focusY >= 0) {
      targetX = this.focusX;
      targetY = this.focusY;
      this.focusTimerMs += dtMs;
      if (this.focusTimerMs > 3500) {
        this.focusTimerMs = 0;
        this.focusX = -1;
        this.focusY = -1;
      }
    }

    // 4. Directional look-ahead & climbing offset (`e.m()` lines 1649-1663)
    const lookAheadX = (facingRight ? 44 : -44) - (isClimbing ? (facingRight ? 44 : -44) : 0);
    const climbOffsetY = isClimbing ? 52 : 0;
    const desiredX = targetX - 88 + lookAheadX;
    const desiredY = targetY - 168 + climbOffsetY;

    // 5. Group 3 camera trigger zones (`e.m()` lines 1680-1725)
    let inTriggerZone = false;
    if (levelRenderer) {
      for (const trig of levelRenderer.cameraTriggers) {
        const sx = trig.x - this.scrollX;
        const sy = trig.y - this.scrollY;
        if (sx >= 0 && sy >= 0 && sx <= NATIVE_WIDTH && sy <= NATIVE_HEIGHT) {
          if (
            LevelRenderer.rectsOverlap(
              trig.x,
              trig.y,
              16,
              16,
              kratosX - (kratosW >> 1),
              kratosY - kratosH,
              kratosW,
              kratosH
            )
          ) {
            inTriggerZone = true;
            this.aO = true;
            if (trig.aI && this.aJ <= 60) {
              this.aJ += (this.aL * dtMs) >> 8;
            } else if (!trig.aI && this.aJ >= -60) {
              this.aJ -= (this.aL * dtMs) >> 8;
            }
            if (trig.aH && this.aK <= 130) {
              this.aK += (this.aL * dtMs) >> 8;
            } else if (!trig.aH && this.aK >= -60) {
              this.aK -= (this.aL * dtMs) >> 8;
            }
          }
        }
      }
    }

    if (
      !inTriggerZone &&
      (isRunning || isJumping || !this.aO) &&
      !this.aN &&
      !this.aM &&
      (this.aK !== 0 || this.aJ !== 0)
    ) {
      this.aO = false;
      this.aJ -= (this.aJ * dtMs) >> 8;
      this.aK -= (this.aK * dtMs) >> 8;
      if (Math.abs(this.aJ) < 8) this.aJ = 0;
      if (Math.abs(this.aK) < 8) this.aK = 0;
    } else if (this.aN && this.aK <= 60) {
      this.aK += (this.aL * dtMs) >> 8;
    } else if (this.aM && this.aK >= -60) {
      this.aK -= (this.aL * dtMs) >> 8;
    }

    // 6. Velocity clamping in 8-bit fixed-point (`e.m()` lines 1664-1678, 1727-1731)
    const maxStep = dtMs << 8;
    const stepX = Phaser.Math.Clamp((desiredX - this.scrollX + this.aJ) * dtMs, -maxStep, maxStep);
    const stepY = Phaser.Math.Clamp((desiredY - this.scrollY + this.aK) * dtMs, -maxStep, maxStep);

    this.aP = Phaser.Math.Clamp(this.aP + (stepX << 1), 0, this.aC << 8);
    this.aQ = Phaser.Math.Clamp(this.aQ + (stepY << 1), 0, this.aD << 8);

    this.scrollX = Phaser.Math.Clamp((this.aP >> 8) + this.shakeOffsetX, 0, this.aC);
    this.scrollY = Phaser.Math.Clamp((this.aQ >> 8) + this.shakeOffsetY, 0, this.aD);

    this.scene.cameras.main.setScroll(this.scrollX, this.scrollY);
  }
}
