import * as Phaser from "phaser";
import { MAX_TICK_MS, MIN_TICK_MS, NATIVE_HEIGHT, NATIVE_WIDTH } from "../constants";
import { AssetRegistry } from "../systems/AssetRegistry";
import { BitmapFontRenderer } from "../systems/BitmapFontRenderer";
import { CameraController } from "../systems/CameraController";
import { CompositeAnimPlayer } from "../systems/CompositeAnimPlayer";
import {
  EVENT_CAMERA_PAN,
  EVENT_SELECT_KRATOS_ANIM,
  EVENT_SELECT_LEVEL,
  EVENT_SELECT_SCENE,
  EVENT_TELEMETRY_UPDATE,
  EVENT_TOGGLE_DEBUG_FLAG,
  EVENT_VIRTUAL_KEY,
  EVENT_VIRTUAL_KEY_DOWN,
  EVENT_VIRTUAL_KEY_UP,
  GameEvents,
} from "../systems/EventBus";
import { InputManager } from "../systems/InputManager";
import { KratosController } from "../systems/KratosController";
import { LevelRenderer } from "../systems/LevelRenderer";
import { SliceLoader } from "../systems/SliceLoader";
import { OrbManager } from "../systems/OrbManager";
import { EnemyManager } from "../systems/EnemyManager";
import { QTEManager } from "../systems/QTEManager";
import { ProgressionManager } from "../systems/ProgressionManager";
import { AudioManager } from "../systems/AudioManager";

/**
 * PlayScene: Phase 4 Gameplay Engine, Camera, Player Movement, Animation & Collision Scene.
 *
 * Implements:
 * - Layered LevelRenderer (Base 16x16 Tileset 2058 + Static Background/Foreground Stamps 3073/3076 + Interactive Props)
 * - KratosController (`e.java` state machine `s(int)`, root-motion physics, gravity, and sub-tile DDA collision solver `f(int, int)` / `g(int, int)`)
 * - InputManager (`s` single-tick press & `t` held directional/action priority matching `e.i()` / `e.j()`)
 * - CameraController (`e.m()` 8-bit fixed-point tracking, `±44` px look-ahead, `+52` px climb offset, Group 3 pan zones, door focus, and landing shake)
 * - Authentic Top-Left HUD (`1025`/`1036`, dynamic weapon icon `5 + fQ`, Health/Magic Bars, Red Orb Counter `1031`, and `@` Context Prompt `1027` at `(12, 183)`)
 * - Debug Mode (Hitbox/Hurtbox wireframes `J[]`, Kratos collision sensor box, 16-bit Tile Collision Grid `jb[]`, and live state telemetry)
 */
export class PlayScene extends Phaser.Scene {
  private levelRenderer!: LevelRenderer;
  private cameraController!: CameraController;
  private inputManager!: InputManager;
  private kratos!: KratosController;
  private orbManager!: OrbManager;
  private enemyManager!: EnemyManager;
  private qteManager!: QTEManager;
  private progressionManager!: ProgressionManager;

  private hudContainer!: Phaser.GameObjects.Container;
  private hudWeaponIcon?: Phaser.GameObjects.Image;
  private hudHealthBar?: Phaser.GameObjects.Graphics;
  private hudComboText?: Phaser.GameObjects.Text;
  private lastWeaponIconIdx: number = -1;
  private contextPromptPlayer?: CompositeAnimPlayer;
  private lastContextAnimIdx: number = -1;

  private debugOverlayGraphics!: Phaser.GameObjects.Graphics;
  private debugHudText!: Phaser.GameObjects.Text;

  private currentLevelResId: number = 9217;
  private isPaused: boolean = false;
  private isGameOver: boolean = false;
  private isVictory: boolean = false;
  private pauseMenuContainer?: Phaser.GameObjects.Container;
  private gameOverContainer?: Phaser.GameObjects.Container;
  private selectedPauseIdx: number = 0;

  private showHitboxes: boolean = true;
  private showCollisionGrid: boolean = false;
  private showHud: boolean = true;
  private showDebugOverlay: boolean = true;

  // Manual animation preview override (`[` / `]` or dropdown selector)
  private manualAnimOverride: boolean = false;

  private cleanupListeners: (() => void)[] = [];

  constructor() {
    super({ key: "PlayScene" });
  }

  init(data?: { levelResId?: number }): void {
    if (data?.levelResId) {
      this.currentLevelResId = data.levelResId;
    }
  }

  create(): void {
    this.cameras.main.setBackgroundColor("#141824");
    this.cameraController = new CameraController(this);
    this.levelRenderer = new LevelRenderer(this);
    this.inputManager = new InputManager(this);
    this.orbManager = new OrbManager(this);
    this.enemyManager = new EnemyManager(this, this.levelRenderer);
    this.qteManager = new QTEManager(this);
    this.progressionManager = new ProgressionManager();

    // Wire lever-triggered door camera focus (`e.x(int)` lines 7689-7691)
    this.levelRenderer.onDoorCameraFocus = (focusX: number, focusY: number) => {
      this.cameraController.setFocusTarget(focusX, focusY);
    };

    this.debugOverlayGraphics = this.add.graphics().setDepth(60);
    this.hudContainer = this.add.container(0, 0).setScrollFactor(0).setDepth(70);
    this.debugHudText = this.add
      .text(3, NATIVE_HEIGHT - 36, "", {
        fontFamily: "monospace",
        fontSize: "8px",
        color: "#00ff88",
        backgroundColor: "#000000bb",
        padding: { x: 2, y: 1 },
      })
      .setScrollFactor(0)
      .setDepth(80);

    // Play level intro horn
    AudioManager.get().initAudio().then(() => {
      AudioManager.get().playSlot(1);
    });

    this.loadLevel(this.currentLevelResId);
    this.buildHud();
    this.setupInputs();
    this.setupEventBus();
  }

  public loadLevel(levelResId: number): void {
    const registry = AssetRegistry.get();
    const level = registry.levels.get(levelResId) ?? registry.levels.get(9217)!;
    this.currentLevelResId = level.res_id;

    // 1. Build layered level tilemap, stamps, interactive props, and collision grid
    this.levelRenderer.buildLevel(level);
    this.levelRenderer.renderDebugCollisionGrid(this.showCollisionGrid);
    this.enemyManager.populateFromLevel(level);
    this.orbManager.clear();
    this.qteManager.cancel();

    // 2. Configure camera bounds & Kratos spawn (`Group 26`)
    this.cameraController.setWorldBounds(level.dimensions.width_px, level.dimensions.height_px);
    const spawnX = level.player_spawn.x || 160;
    const spawnY = level.player_spawn.y || 160;

    // 3. Spawn KratosController (`28672` bank -> `28673` slice -> `28674` PNG)
    if (this.kratos) {
      this.kratos.destroy();
    }
    this.kratos = new KratosController(
      this,
      this.levelRenderer,
      this.cameraController,
      spawnX,
      spawnY,
      level.palette_variant
    );
    this.kratos.hy = level.header.hy_flag !== false;
    this.kratos.player.setFlip(!this.kratos.hy, false);
    this.kratos.player.container.setDepth(35);
    this.manualAnimOverride = false;

    this.cameraController.freeLookMode = false;
    this.cameraController.centerOn(this.kratos.ge, this.kratos.gf, this.kratos.hy);
  }

  /**
   * Builds the authentic top-left HUD from `e.f(Graphics)` (`e.java` lines 2436-2461)
   * and bottom-left context prompt indicator (`1027` / `1040` at `(12, 183)`, line 2104):
   * - `1025` Anim `4` (`1036` / `1048`): HUD Blade Emblem & Frame at `(0, 0)`
   * - `1036` slice `5 + fQ`: Active Weapon/Magic Icon at `(6, 17)`
   * - Green Health Meter (`x=42, y=13..15`) & Blue Magic Meter (`x=42, y=17..19`)
   * - `1031` / `1033`: Red Orb Counter centered at `(58, 24)`
   */
  private buildHud(): void {
    this.hudContainer.removeAll(true);

    // 1. HUD Blade Emblem & Frame (`slot 133 = bank 1025 anim 4` in `e.r()` line 3847)
    const hudEmblem = new CompositeAnimPlayer(this, 1025, -1, 4, 1036);
    hudEmblem.setPosition(0, 0);
    this.hudContainer.add(hudEmblem.container);

    // 2. Active Weapon/Power Icon (`aM[5 + fQ].a(var1, 6, 17, 0)`)
    this.lastWeaponIconIdx = -1;
    this.updateHudWeaponIcon(this.kratos ? this.kratos.fQ : 0);

    // 3. Health & Magic Bars (`e.java` lines 2444-2459)
    const barsGfx = this.add.graphics();
    barsGfx.lineStyle(1, 0x424242, 1);
    barsGfx.strokeRect(42, 13, 36, 3);
    barsGfx.strokeRect(42, 17, 36, 2);
    barsGfx.fillStyle(0x000000, 1);
    barsGfx.fillRect(42, 14, 36, 2);
    barsGfx.fillRect(42, 17, 36, 2);
    // Green Health fill (`0x48FE00`)
    barsGfx.fillStyle(0x48fe00, 1);
    barsGfx.fillRect(42, 14, 36, 2);
    // Blue Magic fill (`0x0196FF`)
    barsGfx.fillStyle(0x0196ff, 1);
    barsGfx.fillRect(42, 17, 36, 2);
    this.hudContainer.add(barsGfx);

    // 4. Red Orb Counter (`((a)this).a(var1, String.valueOf(this.hu), 58, 24, 1)`)
    BitmapFontRenderer.renderText(this, 1031, "0", 58, 31, "center", this.hudContainer);

    // 5. Context Prompt / Arrow Indicator (`slot 131 = bank 1027 / slice 1040` at `(12, 183)` in `e.c(Graphics)` line 2104)
    this.contextPromptPlayer = new CompositeAnimPlayer(this, 1027, -1, 0, 1040);
    this.contextPromptPlayer.setPosition(12, 183);
    this.contextPromptPlayer.container.setVisible(false);
    this.hudContainer.add(this.contextPromptPlayer.container);
    this.lastContextAnimIdx = -1;
  }

  private updateHudWeaponIcon(fQ: number): void {
    const sliceIdx = 5 + (fQ & 3);
    if (sliceIdx === this.lastWeaponIconIdx) return;
    this.lastWeaponIconIdx = sliceIdx;
    if (this.hudWeaponIcon) {
      this.hudWeaponIcon.destroy();
      this.hudWeaponIcon = undefined;
    }
    const icon = SliceLoader.createSliceImage(this, 1036, sliceIdx, 6, 17, 0, -1);
    if (icon) {
      this.hudWeaponIcon = icon;
      this.hudContainer.add(icon);
    }
  }

  private setupInputs(): void {
    // Keyboard shortcuts for animation inspection & debug overlays
    this.input.keyboard?.on("keydown-OPEN_BRACKET", () => {
      this.manualAnimOverride = true;
      this.kratos.player.setAnimation(this.kratos.player.animIndex - 1, true);
    });
    this.input.keyboard?.on("keydown-CLOSED_BRACKET", () => {
      this.manualAnimOverride = true;
      this.kratos.player.setAnimation(this.kratos.player.animIndex + 1, true);
    });
    this.input.keyboard?.on("keydown-H", () => {
      this.showHitboxes = !this.showHitboxes;
    });
    this.input.keyboard?.on("keydown-G", () => {
      this.showCollisionGrid = !this.showCollisionGrid;
      this.levelRenderer.renderDebugCollisionGrid(this.showCollisionGrid);
    });
    this.input.keyboard?.on("keydown-F1", () => {
      this.showDebugOverlay = !this.showDebugOverlay;
      this.debugHudText.setVisible(this.showDebugOverlay);
    });
    this.input.keyboard?.on("keydown-P", () => {
      this.togglePause();
    });
    this.input.keyboard?.on("keydown-ESCAPE", () => {
      this.togglePause();
    });
  }

  private setupEventBus(): void {
    this.cleanupListeners.push(
      GameEvents.on<number>(EVENT_SELECT_LEVEL, (levelResId) => {
        if (typeof levelResId === "number") {
          this.loadLevel(levelResId);
        }
      }),
      GameEvents.on<number>(EVENT_SELECT_KRATOS_ANIM, (animIdx) => {
        if (typeof animIdx === "number") {
          this.manualAnimOverride = true;
          this.kratos.player.setAnimation(animIdx, true);
        }
      }),
      GameEvents.on<string>(EVENT_SELECT_SCENE, (sceneKey) => {
        if (sceneKey && sceneKey !== "PlayScene") {
          this.scene.start(sceneKey);
        }
      }),
      GameEvents.on<string>(EVENT_TOGGLE_DEBUG_FLAG, (flag) => {
        if (flag === "hitboxes") {
          this.showHitboxes = !this.showHitboxes;
        } else if (flag === "collision") {
          this.showCollisionGrid = !this.showCollisionGrid;
          this.levelRenderer.renderDebugCollisionGrid(this.showCollisionGrid);
        } else if (flag === "hud") {
          this.showHud = !this.showHud;
          this.hudContainer.setVisible(this.showHud);
        } else if (flag === "debugOverlay") {
          this.showDebugOverlay = !this.showDebugOverlay;
          this.debugHudText.setVisible(this.showDebugOverlay);
        } else if (flag === "resetCamera") {
          this.cameraController.freeLookMode = false;
          this.cameraController.centerOn(this.kratos.ge, this.kratos.gf, this.kratos.hy);
        }
      }),
      GameEvents.on<{ dx: number; dy: number }>(EVENT_CAMERA_PAN, (delta) => {
        if (delta) {
          this.cameraController.freeLookMode = true;
          this.cameraController.panBy(delta.dx, delta.dy);
        }
      }),
      GameEvents.on<string>(EVENT_VIRTUAL_KEY_DOWN, () => {
        this.manualAnimOverride = false;
        this.cameraController.freeLookMode = false;
      }),
      GameEvents.on<string>(EVENT_VIRTUAL_KEY_UP, () => {
        // Handled by InputManager
      }),
      GameEvents.on<string>(EVENT_VIRTUAL_KEY, (action) => {
        if (action === "NEXT_ANIM") {
          this.manualAnimOverride = true;
          this.kratos.player.setAnimation(this.kratos.player.animIndex + 1, true);
        } else if (action === "PREV_ANIM") {
          this.manualAnimOverride = true;
          this.kratos.player.setAnimation(this.kratos.player.animIndex - 1, true);
        } else {
          this.manualAnimOverride = false;
          this.cameraController.freeLookMode = false;
        }
      })
    );

    this.events.once("shutdown", () => {
      for (const fn of this.cleanupListeners) fn();
      this.cleanupListeners = [];
      this.inputManager.destroy();
    });
  }

  private togglePause(): void {
    if (this.isGameOver) return;
    this.isPaused = !this.isPaused;
    AudioManager.get().playSfx("cursor");

    if (this.isPaused) {
      if (!this.pauseMenuContainer) {
        this.pauseMenuContainer = this.add.container(0, 0).setScrollFactor(0).setDepth(90);
      }
      this.pauseMenuContainer.removeAll(true);
      const bg = this.add.graphics();
      bg.fillStyle(0x000000, 0.75);
      bg.fillRect(0, 0, NATIVE_WIDTH, NATIVE_HEIGHT);
      this.pauseMenuContainer.add(bg);

      BitmapFontRenderer.renderText(this, 1032, "PAUSED", NATIVE_WIDTH / 2, 60, "center", this.pauseMenuContainer);
      BitmapFontRenderer.renderText(this, 1031, "RESUME (P)", NATIVE_WIDTH / 2, 100, "center", this.pauseMenuContainer);
      BitmapFontRenderer.renderText(this, 1031, "MAIN MENU", NATIVE_WIDTH / 2, 125, "center", this.pauseMenuContainer);
      this.pauseMenuContainer.setVisible(true);
    } else if (this.pauseMenuContainer) {
      this.pauseMenuContainer.setVisible(false);
    }
  }

  private triggerGameOver(): void {
    if (this.isGameOver) return;
    this.isGameOver = true;
    AudioManager.get().playSfx("grunt");

    if (!this.gameOverContainer) {
      this.gameOverContainer = this.add.container(0, 0).setScrollFactor(0).setDepth(95);
    }
    this.gameOverContainer.removeAll(true);
    const bg = this.add.graphics();
    bg.fillStyle(0x400000, 0.85);
    bg.fillRect(0, 0, NATIVE_WIDTH, NATIVE_HEIGHT);
    this.gameOverContainer.add(bg);

    BitmapFontRenderer.renderText(this, 1032, "YOU ARE DEAD", NATIVE_WIDTH / 2, 70, "center", this.gameOverContainer);
    BitmapFontRenderer.renderText(this, 1031, "RETRY (SPACE)", NATIVE_WIDTH / 2, 110, "center", this.gameOverContainer);
    BitmapFontRenderer.renderText(this, 1031, "MAIN MENU (M)", NATIVE_WIDTH / 2, 135, "center", this.gameOverContainer);
    this.gameOverContainer.setVisible(true);

    const onRetry = (e: KeyboardEvent) => {
      if (e.code === "Space" || e.code === "Enter") {
        window.removeEventListener("keydown", onRetry);
        this.gameOverContainer?.setVisible(false);
        this.isGameOver = false;
        this.loadLevel(this.currentLevelResId);
      } else if (e.code === "KeyM" || e.code === "Escape") {
        window.removeEventListener("keydown", onRetry);
        this.scene.start("MenuScene");
      }
    };
    window.addEventListener("keydown", onRetry);
  }

  update(_time: number, delta: number): void {
    if (this.isPaused || this.isGameOver) return;
    const dtMs = Math.min(MAX_TICK_MS, Math.max(MIN_TICK_MS, delta));

    // 1. Sample keyboard & virtual keypad inputs (`s` and `t`)
    this.inputManager.update();
    if (this.inputManager.s !== 0 || this.inputManager.t !== 0) {
      this.manualAnimOverride = false;
      this.cameraController.freeLookMode = false;
    }

    // 2. Advance Kratos state machine, root-motion physics, and DDA collision (`e.x()`)
    if (this.manualAnimOverride) {
      this.kratos.player.update(dtMs);
    } else {
      this.kratos.update(dtMs, this.inputManager);
    }

    // 3. Advance interactive level objects & enemies
    this.levelRenderer.updateInteractiveObjects(
      dtMs,
      this.kratos.ge,
      this.kratos.gf,
      this.kratos.gG,
      this.kratos.gH,
      this.kratos.gC,
      this.kratos.hy,
      this.kratos.hE
    );
    this.levelRenderer.update(dtMs);
    if (this.showCollisionGrid) {
      this.levelRenderer.renderDebugCollisionGrid(true);
    }

    // 3b. Advance Enemy AI, Attacks & Hurtbox collisions
    this.enemyManager.update(
      dtMs,
      this.kratos.ge,
      this.kratos.gf,
      this.kratos.gH,
      (dmg, fromRight) => {
        this.kratos.hn = Math.max(0, this.kratos.hn - dmg);
        AudioManager.get().playSfx("hit");
        if (this.kratos.hn <= 0) {
          this.kratos.setState(52); // Death fall
          this.triggerGameOver();
        } else {
          this.kratos.setState(fromRight ? 100 : 5); // Hit stun
        }
      }
    );

    // 3c. Kratos Weapon Hitbox vs Enemy Hurtbox Combat Resolution
    if (this.kratos.gz || this.kratos.gt) {
      const boxes = this.kratos.player.getActiveBoxes();
      for (const box of boxes) {
        if ((box.flags & 1) !== 0) {
          // Attack hitbox
          const hxLeft = this.kratos.ge + box.x1;
          const hxTop = this.kratos.gf + box.y1;
          const hxRight = hxLeft + box.width;
          const hxBottom = hxTop + box.height;

          for (const enemy of this.enemyManager.enemies) {
            if (!enemy.alive) continue;
            const enLeft = enemy.x - (enemy.width >> 1);
            const enTop = enemy.y - enemy.height;
            const enRight = enLeft + enemy.width;
            const enBottom = enemy.y;

            if (
              hxRight >= enLeft &&
              hxLeft <= enRight &&
              hxBottom >= enTop &&
              hxTop <= enBottom
            ) {
              const dmg = this.kratos.hf ? 45 : 25;
              const killed = enemy.takeDamage(dmg, this.kratos.hy);
              this.kratos.gL++;
              this.kratos.gM = 2000;
              this.cameraController.triggerShake(100);
              AudioManager.get().playSfx(this.kratos.hf ? "impact" : "slash1");

              if (killed) {
                this.orbManager.spawnChestOrbs(
                  Math.random() > 0.5 ? 0 : 1,
                  enemy.x,
                  enemy.y,
                  enemy.config.orbDrop
                );
              }
            }
          }
        }
      }
    }

    // 3d. Update QTE & Homing Orbs
    this.qteManager.update(dtMs);
    this.orbManager.update(
      dtMs,
      this.kratos.ge,
      this.kratos.gf,
      this.kratos.gH,
      (type, val) => {
        AudioManager.get().playSfx("orb");
        if (type === 0) {
          this.kratos.hu += val;
          this.progressionManager.addRedOrbs(val);
        } else if (type === 1) {
          this.kratos.hn = Math.min(this.kratos.hq, this.kratos.hn + val * 4);
        } else if (type === 2) {
          this.kratos.hp = Math.min(this.kratos.hr, this.kratos.hp + val * 4);
        }
      }
    );

    // 4. Advance 8-bit fixed-point camera tracking (`e.m()`)
    this.cameraController.updateTracking(
      dtMs,
      this.kratos.ge,
      this.kratos.gf,
      this.kratos.gG,
      this.kratos.gH,
      this.kratos.hy,
      this.kratos.gr,
      this.kratos.gx,
      this.kratos.hE === 24 || this.kratos.hE === 25,
      this.levelRenderer
    );

    // 5. Update HUD weapon icon & `@` context prompt (`e.c(Graphics)` line 2104)
    this.updateHudWeaponIcon(this.kratos.fQ);
    if (this.contextPromptPlayer) {
      const showPrompt = this.kratos.aS && this.kratos.aT === this.kratos.hy;
      this.contextPromptPlayer.container.setVisible(showPrompt);
      if (showPrompt) {
        if (this.lastContextAnimIdx !== this.kratos.aU) {
          this.lastContextAnimIdx = this.kratos.aU;
          this.contextPromptPlayer.setAnimation(this.kratos.aU);
        }
        this.contextPromptPlayer.update(dtMs);
      }
    }

    // 6. Render Hitboxes / Hurtboxes, Collision Body Box, & Origin Crosshair when enabled
    this.debugOverlayGraphics.clear();
    if (this.showHitboxes) {
      // Kratos physical collision bounds (`[ge - (gG >> 1), gf - gH, gG, gH]`)
      this.debugOverlayGraphics.lineStyle(1, 0x00ff88, 0.65);
      this.debugOverlayGraphics.strokeRect(
        this.kratos.ge - (this.kratos.gG >> 1),
        this.kratos.gf - this.kratos.gH,
        this.kratos.gG,
        this.kratos.gH
      );

      // Kratos origin crosshair (`ge, gf`)
      this.debugOverlayGraphics.lineStyle(1, 0xffffff, 0.9);
      this.debugOverlayGraphics.lineBetween(
        this.kratos.ge - 4,
        this.kratos.gf,
        this.kratos.ge + 4,
        this.kratos.gf
      );
      this.debugOverlayGraphics.lineBetween(
        this.kratos.ge,
        this.kratos.gf - 4,
        this.kratos.ge,
        this.kratos.gf + 4
      );

      const boxes = this.kratos.player.getActiveBoxes();
      for (const box of boxes) {
        const isAttackBox = (box.flags & 1) !== 0;
        const boxColor = isAttackBox ? 0xff2244 : 0x00ffff;
        this.debugOverlayGraphics.lineStyle(1, boxColor, 0.95);
        this.debugOverlayGraphics.strokeRect(
          this.kratos.ge + box.x1,
          this.kratos.gf + box.y1,
          box.width,
          box.height
        );
      }
    }

    // 7. Update compact on-canvas debug bar & React inspector telemetry
    const lvl = this.levelRenderer.level;
    const kf = this.kratos.player.getCurrentKeyframe();
    const cf = this.kratos.player.getCurrentCompositeFrame();
    if (this.showDebugOverlay && lvl) {
      this.debugHudText.setText(
        `ST:${this.kratos.hE}(${this.kratos.getStateName()}) ANM:${this.kratos.player.animIndex} FLG:${this.kratos.getActiveFlagsSummary()}\n` +
          `POS:${this.kratos.ge},${this.kratos.gf} TIL:${this.kratos.gA},${this.kratos.gB} CAM:${this.cameraController.scrollX},${this.cameraController.scrollY}\n` +
          `RM:${this.kratos.player.stepA},${this.kratos.player.stepB} IN:${this.inputManager.getActiveSummary()} FPS:${Math.round(this.game.loop.actualFps)}`
      );
    }

    const reg = AssetRegistry.get();
    GameEvents.emit(EVENT_TELEMETRY_UPDATE, {
      activeScene: "PlayScene",
      levelResId: lvl.res_id,
      levelTitle: lvl.title,
      chapterIndex: lvl.chapter_index,
      paletteVariant: lvl.palette_variant,
      levelSizePx: { w: lvl.dimensions.width_px, h: lvl.dimensions.height_px },
      levelSizeTiles: { w: lvl.dimensions.width_tiles, h: lvl.dimensions.height_tiles },
      cameraPos: { x: this.cameraController.scrollX, y: this.cameraController.scrollY },
      kratosPos: { x: this.kratos.ge, y: this.kratos.gf },
      kratosTilePos: { tx: this.kratos.gA, ty: this.kratos.gB },
      kratosSubTile: { hl: this.kratos.hl >> 8, hm: this.kratos.hm >> 8 },
      kratosStateId: this.kratos.hE,
      kratosStateName: this.kratos.getStateName(),
      kratosFlags: this.kratos.getActiveFlagsSummary(),
      activeInput: this.inputManager.getActiveSummary(),
      kratosAnimBankId: 28672,
      kratosSliceTableId: 28673,
      kratosPngId: 28674,
      kratosAnimIndex: this.kratos.player.animIndex,
      kratosTotalAnims: this.kratos.player.getBank()?.num_animations ?? 122,
      kratosKeyframeIndex: this.kratos.player.keyframeIndex,
      kratosCompFrameIndex: kf?.comp_frame_index ?? 0,
      kratosPartCount: cf?.part_count ?? 0,
      kratosBoxCount: cf?.box_count ?? 0,
      kratosRootMotion: { dx: this.kratos.player.stepA, dy: this.kratos.player.stepB },
      loadedTexturesCount: reg.loadedTextureKeys.size,
      loadedAnimBanksCount: reg.animBanks.size,
      loadedSliceTablesCount: reg.slices.size,
      loadedFontsCount: reg.fonts.size,
      loadedLevelsCount: reg.levels.size,
      renderedEntitiesCount: this.levelRenderer.renderedEntitiesCount + 1,
      renderedStampsCount: this.levelRenderer.renderedStampsCount,
      fps: Math.round(this.game.loop.actualFps),
      showHitboxes: this.showHitboxes,
      showCollisionGrid: this.showCollisionGrid,
      showHud: this.showHud,
      showDebugOverlay: this.showDebugOverlay,
    });
  }
}
