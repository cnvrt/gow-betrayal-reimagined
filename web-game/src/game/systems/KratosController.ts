import * as Phaser from "phaser";
import { AssetRegistry } from "./AssetRegistry";
import { CameraController } from "./CameraController";
import { CompositeAnimPlayer } from "./CompositeAnimPlayer";
import { InputManager } from "./InputManager";
import { LevelRenderer } from "./LevelRenderer";

const KRATOS_STATE_NAMES: Record<number, string> = {
  0: "IDLE",
  3: "CROUCH",
  4: "CROUCH_ENTER",
  5: "HIT_STUN_FRONT",
  7: "KNOCKDOWN",
  9: "RUN",
  10: "BEAM_IDLE",
  11: "BEAM_WALK",
  15: "LEVER_PULL_DOWN",
  17: "LEVER_PULL_UP",
  21: "BOX_GRAB_IDLE",
  22: "BOX_PUSH",
  23: "BOX_PULL",
  24: "JUMP",
  25: "DOUBLE_JUMP",
  26: "FALL",
  27: "LAND",
  28: "LAND_RECOVERY",
  30: "STAIR_WALK",
  31: "OPEN_CHEST",
  33: "CLIMB_IDLE",
  34: "CLIMB_DOWN",
  35: "CLIMB_UP",
  36: "CLIMB_ATTACK",
  37: "LEDGE_HANG",
  38: "LEDGE_CLIMB_UP",
  40: "LEDGE_DROP",
  44: "CLIMB_TURN",
  45: "CEILING_HANG_IDLE",
  46: "CEILING_HANG_MOVE",
  47: "CEILING_HANG_ATTACK",
  48: "ZIPLINE_MOUNT",
  50: "ZIPLINE_IDLE",
  51: "ZIPLINE_SLIDE",
  52: "DEATH_FALL",
  53: "DEATH_GROUND",
  54: "GROUND_ATTACK_1",
  67: "AIR_ATTACK_1",
  94: "MEDUSA_GAZE",
  95: "ARMY_OF_HADES",
  97: "CROUCH_HIT",
  99: "CEILING_BONK",
  100: "HIT_STUN_BACK",
  118: "CEILING_HIT_FRONT",
  119: "CEILING_HIT_BACK",
  120: "BEAM_CATCH",
  121: "BEAM_CLIMB_UP",
};

/**
 * Exact port of Kratos's movement, physics, animation state machine (`s(int)`),
 * input handlers (`H, I, J, K, G, D, E, F, h, z`), and sub-tile DDA collision solver
 * (`f(int, int)`, `g(int, int)`, `s()`) from `e.java`.
 */
export class KratosController {
  public player: CompositeAnimPlayer;
  private levelRenderer: LevelRenderer;
  private cameraController: CameraController;

  // World pixel coordinates (`ge, gf` in e.java) and checkpoint (`gc, gd, ga, gb`)
  public ge: number = 0;
  public gf: number = 0;
  public gc: number = 0;
  public gd: number = 0;
  public ga: number = 0;
  public gb: number = 0;

  // Tile coordinates (`gA, gB` in e.java) and 8-bit fixed-point sub-tile offsets (`hl, hm`)
  public gA: number = 0;
  public gB: number = 0;
  public hl: number = 0;
  public hm: number = 0;

  // Bounding box dimensions computed in `r()` (`e.java` lines 3848-3855)
  public gG: number = 16; // Half of idle width (`Q[2] >> 1`)
  public gH: number = 32; // Full idle height (`Q[3]`)
  public hk: number = 2; // Height in tiles (`gH / 16`)
  public hB: number = 8; // Quarter width (`gG >> 1`)
  public hC: number = -8; // Negative quarter width (`-(gG >> 1)`)

  // Facing and state machine variables (`hy, hz, hE, hF` in e.java)
  public hy: boolean = true; // Facing right
  public hz: boolean = true; // Checkpoint facing right
  public hE: number = 0; // Logical state ID
  public hF: number = 0; // Active animation sequence ID in Bank 28672

  // Sensor & posture flags (`gC..hA` in e.java)
  public gC: boolean = false; // Grounded on solid tile (flag 1)
  public gD: boolean = false; // Standing on balance beam (flag 8192)
  public gE: boolean = false; // Right-upward stair ahead (flag 2048)
  public gF: boolean = false; // Left-upward stair ahead (flag 4096)
  public gk: boolean = false; // Force-grounded override
  public gl: boolean = false; // Knockdown / hit-stun lock
  public gm: boolean = false; // Damage taken flag
  public gn: boolean = false; // Opening chest (`hE == 31`)
  public go: boolean = false; // Interacting with push-box (`hE == 21..23`)
  public gp: number = 0; // Push-box kick/push mode (`0` pull, `1` push)
  public gq: boolean = false; // Pulling lever (`hE == 15 || hE == 17`)
  public gr: boolean = false; // Climbing ladder / climbable wall (`hE == 33..36, 44`)
  public gs: boolean = false; // On zipline (`hE == 50 || hE == 51`)
  public gt: boolean = false; // Airborne attack (`hE >= 67 && hE < 76`)
  public gu: boolean = false; // Hanging from ceiling (`hE == 45..47, 118, 119`)
  public gv: boolean = false; // Hanging from ledge (`hE == 37`)
  public gw: boolean = false; // Walking stairs (`hE == 30`)
  public gx: boolean = false; // Running (`hE == 9`)
  public gy: boolean = false; // Reached top of ladder (`super.b < 0` on climbable edge)
  public gz: boolean = false; // Ground attack (`hE >= 54 && hE <= 66`)
  public hA: boolean = false; // Crouching (`hE == 3`)

  // Gravity & falling accumulation (`hO, hP` in e.java lines 4778-4788)
  public hO: number = 0;
  public hP: number = 0;

  // Sub-tile DDA collision solver flags (`hG, hH, hI, hJ, hM, hN` in e.java)
  private hG: boolean = true;
  private hH: boolean = true;
  private hI: boolean = false;
  private hJ: boolean = false;
  private hM: number = 0;
  private hN: number = 0;

  // Combat & Vitals (hn, hq, hp, hr, gL, gM)
  public hn: number = 1024;
  public hq: number = 1024;
  public hp: number = 1024;
  public hr: number = 1024;
  public gL: number = 0; // Combo hit counter
  public gM: number = 0; // Combo display timer (ms)
  public hu: number = 0; // Red orbs collected in chapter

  // Pushable box index (`gh` in e.java)
  public gh: number = -1;

  // Weapon / magic selection (`fQ`: 0 = Blades, 1 = Blade of Artemis, 2 = Medusa's Gaze, 3 = Army of Hades)
  public fQ: number = 0;
  public hf: boolean = false;
  public hg: boolean = false;
  public hh: boolean = false;

  // Context prompt indicator (`aS, aT, aU` in `e.j(int, int)` lines 5185-5225)
  public aS: boolean = false;
  public aT: boolean = true;
  public aU: number = 0;

  constructor(
    scene: Phaser.Scene,
    levelRenderer: LevelRenderer,
    cameraController: CameraController,
    spawnX: number,
    spawnY: number,
    paletteVariant: number
  ) {
    this.levelRenderer = levelRenderer;
    this.cameraController = cameraController;

    this.player = new CompositeAnimPlayer(scene, 28672, paletteVariant, 0);
    this.levelRenderer.entitiesLayer.add(this.player.container);

    this.ga = spawnX;
    this.gb = spawnY;
    this.gc = spawnX;
    this.gd = spawnY;
    this.ge = spawnX;
    this.gf = spawnY;

    this.initPlayerState();
  }

  /**
   * Exact port of `e.r()` (`e.java` lines 3829-3856):
   * Initializes Kratos's bounding metrics (`gG, gH, hk, hB, hC`), tile coordinates (`gA, gB`),
   * and sub-tile offsets (`hl, hm`).
   */
  public initPlayerState(): void {
    this.gm = false;
    this.aS = false;
    this.hA = false;
    this.gy = false;
    this.gr = false;
    this.gk = false;
    this.gA = Math.floor(this.ge / 16);
    this.gB = Math.floor(this.gf / 16);
    this.setState(0);

    const bounds = this.player.getFrameBounds(0, 0, 0);
    this.hm = this.gf % 16;
    this.hl = this.ge % 16;
    this.gG = (bounds.width || 32) >> 1;
    this.gH = bounds.height || 32;
    this.hk = Math.max(1, Math.floor(this.gH / 16));
    this.hB = this.gG >> 1;
    this.hC = -(this.gG >> 1);

    this.player.setPosition(this.ge, this.gf);
    this.player.setFlip(!this.hy, false);
  }

  private get vTileX(): number {
    return Math.floor(this.ge / 16);
  }

  private get wTileY(): number {
    const maxTy = this.levelRenderer.level.dimensions.height_tiles - 1;
    const ty = Math.floor(this.gf / 16);
    return ty > maxTy ? maxTy : ty;
  }

  /**
   * Exact port of `e.s(int var1)` (`e.java` lines 4572-4736):
   * Transitions Kratos to state `var1`, updates all posture flags, maps weapon/beam variants,
   * and starts the corresponding animation sequence on Bank `28672`.
   */
  public setState(var1: number): void {
    this.hE = var1;
    if (var1 === 3 && this.hF !== 3) {
      var1 = 4;
    }

    if (this.hf) {
      if (var1 === 28 && this.hF !== 27) {
        var1 = 87;
      } else if (var1 === 0) {
        var1 = 79;
      } else if (var1 === 9) {
        var1 = 80;
      } else if (var1 === 24) {
        var1 = 81;
      } else if (var1 === 25) {
        var1 = 82;
      } else if (var1 === 26) {
        var1 = 83;
      } else if (var1 === 27) {
        var1 = 84;
      } else if (var1 === 28) {
        var1 = 85;
      } else if (var1 === 3) {
        var1 = 77;
      } else if (var1 === 4) {
        var1 = 78;
      } else if (var1 === 30) {
        var1 = 86;
      }
    }

    if (this.gD) {
      if (this.hE === 0) {
        var1 = 10;
      } else if (this.hE === 9) {
        var1 = 11;
      }
    }

    this.gq = this.hE === 15 || this.hE === 17;
    this.gn = this.hE === 31;
    this.go = this.hE === 21 || this.hE === 22 || this.hE === 23;
    this.gs = this.hE === 50 || this.hE === 51;
    this.gl = this.hE === 5 || this.hE === 100 || this.hE === 7;
    this.gz =
      (this.hE >= 54 && this.hE <= 66) ||
      (this.hE >= 76 && this.hE < 83 && var1 !== 79 && var1 !== 80 && var1 !== 81 && var1 !== 82) ||
      this.hE === 94 ||
      this.hE === 95;
    this.gt =
      (this.hE >= 67 && this.hE < 76) ||
      (this.hE >= 83 && this.hE <= 87 && var1 !== 83 && var1 !== 84 && var1 !== 85 && var1 !== 86 && var1 !== 87);
    this.gr =
      this.hE === 33 || this.hE === 34 || this.hE === 35 || this.hE === 36 || this.hE === 44;
    this.gu =
      this.hE === 45 || this.hE === 46 || this.hE === 47 || this.hE === 118 || this.hE === 119;
    this.gv = this.hE === 37;
    this.gw = this.hE === 30;
    this.gx = this.hE === 9;

    this.hF = var1;
    this.player.setAnimation(var1);
  }

  /**
   * Exact port of `e.x()` (`e.java` lines 4751-4906):
   * Advances Kratos's sensors, animation, root motion, gravity, input handlers, and DDA collision.
   */
  public update(dtMs: number, input: InputManager): void {
    const var1 = this.vTileX;
    const var2 = (this.hm >> 8) + 1 >= 16 ? this.gB + 1 : this.gB;

    this.gC = this.gk;
    const prevBeam = this.gD;
    if (!this.gC) {
      this.gD =
        (this.levelRenderer.getTileFlags(var1, var2) & 8192) !== 0 &&
        this.hF !== 99 &&
        this.hF !== 24 &&
        this.hF !== 25 &&
        this.hF !== 26;
      this.gC = (this.levelRenderer.getTileFlags(var1, var2) & 1) !== 0;
      this.gE = (this.levelRenderer.getTileFlags(var1, var2 - 1) & 2048) !== 0;
      this.gF = (this.levelRenderer.getTileFlags(var1, var2 - 1) & 4096) !== 0;
    }

    if (this.gD && !prevBeam) {
      if (this.gx) {
        this.gC = true;
        this.setState(9);
      }
    } else if (!this.gD && prevBeam && this.gx) {
      this.setState(9);
    }

    let var6 = this.player.update(dtMs);
    this.checkContextTriggers(var1, var2);

    if (this.hE !== 52 && this.hE !== 53) {
      // Gravity accumulation in Fall state (`hE == 26`, lines 4778-4788)
      if (this.hE === 26) {
        this.player.stepB += this.hO;
        this.hP += dtMs;
        if (this.hP > 200) {
          this.hP -= 200;
          this.hO += this.hO >= 8 ? 0 : 4;
        }
      } else {
        this.hP = 0;
        this.hO = 0;
      }

      if (this.player.stepB < 0 && this.hE !== 60) {
        this.gC = false;
      }

      // Held directional input (`t`) when camera isn't focused on a door (`az < 0`)
      if (this.cameraController.focusX < 0) {
        switch (input.t) {
          case 1:
            this.handleHeldUp();
            break;
          case 2:
            this.handleHeldLeft();
            break;
          case 5:
            this.handleHeldRight();
            break;
          case 6:
            this.handleHeldDown();
            break;
          default:
            break;
        }

        // Single-press input (`s`)
        var6 = this.handlePressedInput(input.s, var6);
      }

      // Key release transitions (`z()`)
      this.handleReleaseTransitions(input.t);

      // Airborne & animation completion transitions (`e(var6)` and `d(var6)`, lines 4840-4846)
      if (
        !this.gC &&
        !this.gD &&
        !this.gt &&
        !this.gu &&
        !this.gk &&
        this.hE !== 26 &&
        this.hE !== 99 &&
        this.hE !== 48 &&
        !this.gw &&
        var6
      ) {
        this.handleAirborneTransition(var6);
      } else if (!var6) {
        this.handleAnimCompleteTransition(var6);
      } else if (this.hE === 3 && !this.hA) {
        this.setState(28);
      }

      // Sub-tile DDA collision solver (`f(var1, var2)`)
      this.solveCollision(var1, var2);
    }

    // Reset combo timer when expired
    if (this.gM > 0) {
      this.gM -= dtMs;
      if (this.gM <= 0) {
        this.gL = 0;
      }
    }

    // Zipline auto-mount when running into flag 16384 (`e.java` lines 4879-4881)
    if (
      this.hF === 9 &&
      ((this.levelRenderer.getTileFlags(this.gA + (this.hy ? 1 : -1), this.gB + 2) & 16384) !== 0 ||
        (this.levelRenderer.getTileFlags(this.gA + (this.hy ? 1 : -1), this.gB + 3) & 16384) !== 0)
    ) {
      this.setState(48);
    }

    // Sync container position & horizontal flip (`hy`)
    this.player.setPosition(this.ge, this.gf);
    this.player.setFlip(!this.hy, false);
  }

  /**
   * Exact port of `e.j(int var1, int var2)` (`e.java` lines 5185-5225):
   * Checks Group 8 context triggers (`flag 128`) at Kratos's tile `(var1, var2)` for `@<digit><digit>` prompts.
   */
  private checkContextTriggers(var1: number, var2: number): void {
    if ((this.levelRenderer.getTileFlags(var1, var2) & 128) > 0) {
      const locTable = AssetRegistry.get().strings.get(10240);
      for (let i = this.levelRenderer.contextTriggers.length - 1; i >= 0; i--) {
        const trig = this.levelRenderer.contextTriggers[i];
        if (trig.cp === var1 && trig.cq === var2) {
          const str = locTable?.strings[trig.cr]?.raw || "";
          if (str.startsWith("@") && str.length >= 3) {
            this.aS = true;
            this.aT = str.substring(2, 3) === "1";
            this.aU = parseInt(str.substring(1, 2), 10) || 0;
            return;
          }
        }
      }
      this.aS = false;
    } else {
      this.aS = false;
    }
  }

  /**
   * Exact port of `e.H()` (`e.java` lines 5711-5716): Held Up (`t == 1`).
   */
  private handleHeldUp(): void {
    const climbTileFlags = this.levelRenderer.getTileFlags(
      this.gA + (this.hy ? 1 : -1),
      this.gB - this.hk - 1
    );
    if (
      this.gr &&
      this.hE === 33 &&
      ((climbTileFlags & 8) !== 0 || (climbTileFlags & 1) === 0)
    ) {
      this.setState(35);
    }
  }

  /**
   * Exact port of `e.I()` (`e.java` lines 5718-5741): Held Down (`t == 6`).
   */
  private handleHeldDown(): void {
    if (this.gr) {
      if (this.hE === 33) {
        if (
          (this.levelRenderer.getTileFlags(this.gA + (this.hy ? 1 : -1), this.gB + 1) & 8) !== 0
        ) {
          this.setState(34);
          return;
        }
        this.setState(26);
        return;
      }
    } else {
      if (this.gu) {
        this.setState(26);
        return;
      }
      if (this.hF === 120) {
        this.setState(26);
        this.gf += 16;
      }
    }
  }

  /**
   * Exact port of `e.J()` (`e.java` lines 5743-5784): Held Right (`t == 5`).
   */
  private handleHeldRight(): void {
    if (
      !this.gr &&
      !this.gv &&
      !this.go &&
      this.hE !== 44 &&
      this.hE !== 22 &&
      this.hE !== 38
    ) {
      if (this.gu && !this.gl && !this.gz) {
        if (this.hE !== 46) {
          this.setState(46);
        }
        this.hy = true;
        return;
      }

      if (this.hE !== 0 && this.hE !== 3) {
        if (this.hE === 50) {
          this.hy = true;
          if ((this.levelRenderer.getTileFlags(this.gA + 1, this.gB) & 1) === 0) {
            this.setState(51);
          }
        }
      } else {
        const fRight0 = this.levelRenderer.getTileFlags(this.gA + 1, this.gB);
        const fRight1 = this.levelRenderer.getTileFlags(this.gA + 1, this.gB - 1);
        const fRight2 = this.levelRenderer.getTileFlags(this.gA + 1, this.gB - 2);
        if (
          ((fRight1 & 1) !== 0 || (fRight0 & 1) !== 0 || (fRight2 & 1) !== 0) &&
          (fRight0 & 512) === 0
        ) {
          if ((fRight0 & 1) !== 0) {
            this.ge = (this.gA + 1) * 16 - (this.gG >> 1);
          }
        } else {
          if ((fRight0 & 512) !== 0) {
            const var1 =
              (this.levelRenderer.getTileFlags(this.gA, this.gB) & 512) !== 0 ? -1 : 0;
            this.ge = (this.gA + 1 + var1) * 16 - (this.gG >> 1);
          }
          this.setState(9);
          this.hA = false;
        }
        this.hy = true;
      }

      if (this.hE === 9 || this.hE === 51) {
        this.hy = true;
      }
    }
  }

  /**
   * Exact port of `e.K()` (`e.java` lines 5786-5826): Held Left (`t == 2`).
   */
  private handleHeldLeft(): void {
    if (
      !this.gr &&
      !this.gv &&
      !this.go &&
      this.hE !== 44 &&
      this.hE !== 22 &&
      this.hE !== 38
    ) {
      if (this.gu && !this.gl && !this.gz) {
        if (this.hE !== 46) {
          this.setState(46);
        }
        this.hy = false;
        return;
      }

      if (this.hE !== 0 && this.hE !== 3) {
        if (this.hE === 50) {
          this.hy = false;
          if ((this.levelRenderer.getTileFlags(this.gA - 1, this.gB) & 1) === 0) {
            this.setState(51);
          }
        }
      } else {
        this.hy = false;
        const fCurr0 = this.levelRenderer.getTileFlags(this.gA, this.gB);
        const fLeft0 = this.levelRenderer.getTileFlags(this.gA - 1, this.gB);
        const fLeft1 = this.levelRenderer.getTileFlags(this.gA - 1, this.gB - 1);
        const fLeft2 = this.levelRenderer.getTileFlags(this.gA - 1, this.gB - 2);
        if (
          ((fLeft0 & 1) !== 0 || (fCurr0 & 1) !== 0 || (fLeft1 & 1) !== 0 || (fLeft2 & 1) !== 0) &&
          (fLeft0 & 512) === 0
        ) {
          if (
            (fLeft0 & 1) !== 0 ||
            (fCurr0 & 1) !== 0 ||
            (fLeft1 & 1) !== 0 ||
            (fLeft2 & 1) !== 0
          ) {
            this.ge = (this.gA + ((fCurr0 & 1) !== 0 ? 1 : 0)) * 16 + (this.gG >> 1);
          }
        } else {
          if ((fLeft0 & 512) !== 0) {
            const var1 = (fCurr0 & 512) !== 0 ? 1 : 0;
            this.ge = (this.gA + var1) * 16 + (this.gG >> 1);
          }
          this.setState(9);
          this.hA = false;
        }
      }

      if (this.hE === 9 || this.hE === 51) {
        this.hy = false;
      }
    }
  }

  /**
   * Exact port of `e.f(boolean var1)` (`e.java` lines 5131-5183).
   */
  private handlePressedInput(s: number, var1: boolean): boolean {
    switch (s) {
      case 1:
        this.handlePressUp();
        break;
      case 2:
        this.handlePressLeft();
        break;
      case 5:
        this.handlePressRight();
        break;
      case 6:
        this.handlePressDown();
        break;
      case 8:
        var1 = this.handlePressAction(var1);
        break;
      case 10:
      case 27:
        if (!this.hA && !this.gz) {
          this.fQ = (this.fQ + 1) % 4;
          this.hf = this.fQ === 1;
          this.hg = this.fQ === 2;
          this.hh = this.fQ === 3;
          if (this.hE === 0) {
            this.setState(0);
          }
        }
        break;
      default:
        break;
    }
    return var1;
  }

  /**
   * Exact port of `e.G()` (`e.java` lines 5664-5709): Single-press Up / Jump (`s == 1`).
   */
  private handlePressUp(): void {
    if (this.hE === 21) {
      this.setState(24);
      return;
    }

    if (this.hF === 120) {
      this.setState(121);
      return;
    }

    if (!this.gw && this.hy && this.gE) {
      this.setState(30);
      return;
    }

    if (!this.gw && !this.hy && this.gF) {
      this.setState(30);
      return;
    }

    if (
      !this.gq &&
      !this.gn &&
      !this.gr &&
      !this.gu &&
      !this.gv &&
      (this.gC || this.gD) &&
      !this.go &&
      !this.gz
    ) {
      if (this.hA) {
        this.hA = false;
        this.setState(0);
        return;
      }

      if (
        this.hE !== 24 &&
        this.hE !== 26 &&
        this.hE !== 3 &&
        this.hE !== 38 &&
        this.hE !== 30
      ) {
        this.setState(24);
        return;
      }
    } else {
      if (this.gv) {
        this.setState(38);
        return;
      }

      if (this.hE === 24) {
        this.setState(25);
      }
    }
  }

  /**
   * Exact port of `e.D()` (`e.java` lines 5445-5487): Single-press Left (`s == 2`).
   */
  private handlePressLeft(): void {
    if (this.go && this.hE === 21) {
      if (this.hy) {
        this.setState(23);
        this.gp = 0;
      } else {
        this.setState(22);
        this.gp = 1;
      }

      if (!this.solvePushBoxMove()) {
        this.setState(21);
      }
    } else if (this.gr && !this.gz) {
      if (this.hy) {
        this.hy = false;
        this.setState(44);
      }
    } else if (this.gv && !this.hy) {
      this.setState(38);
    }
  }

  /**
   * Exact port of `e.E()` (`e.java` lines 5489-5531): Single-press Right (`s == 5`).
   */
  private handlePressRight(): void {
    if (this.go && this.hE === 21) {
      if (this.hy) {
        this.setState(22);
        this.gp = 1;
      } else {
        this.setState(23);
        this.gp = 0;
      }

      if (!this.solvePushBoxMove()) {
        this.setState(21);
      }
    } else if (this.gr && !this.gz) {
      if (!this.hy) {
        this.hy = true;
        this.setState(44);
      }
    } else if (this.gv && this.hy) {
      this.setState(38);
    }
  }

  /**
   * Exact port of `e.F()` (`e.java` lines 5533-5662): Single-press Down / Crouch / Interact (`s == 6`).
   */
  private handlePressDown(): void {
    if (this.gv) {
      this.setState(40);
      this.gf += 1;
      return;
    }

    if (this.hE === 0) {
      // 1. Open Chests (Group 4, lines 5628-5635)
      if (this.hy) {
        for (let i = this.levelRenderer.chests.length - 1; i >= 0; i--) {
          const chest = this.levelRenderer.chests[i];
          if (
            !chest.ft &&
            LevelRenderer.rectsOverlap(
              chest.fu,
              chest.fv,
              chest.fr,
              chest.fs,
              this.ge - this.hB,
              this.gf,
              this.gG,
              this.gH
            ) &&
            Math.abs(this.gf - chest.fv) < 8
          ) {
            this.setState(31);
            this.ge = chest.fu + (chest.fr >> 1);
            return;
          }
        }
      }

      // 2. Pull Hand Levers (Group 34 `cE == true`, lines 5637-5650)
      for (let i = this.levelRenderer.levers.length - 1; i >= 0; i--) {
        const lever = this.levelRenderer.levers[i];
        if (
          this.hy &&
          lever.cE &&
          LevelRenderer.rectsOverlap(
            this.ge - (this.gG >> 1),
            this.gf - this.gH,
            this.gG,
            this.gH,
            lever.cC - 16,
            lever.cD,
            32,
            16
          )
        ) {
          this.levelRenderer.pendingLeverDoorTriggerIdx = i;
          this.levelRenderer.pendingLeverAnimIdx = i;
          if (!lever.cH) {
            this.setState(15);
          } else {
            this.setState(17);
          }
          this.ge = lever.cC;
          lever.cH = !lever.cH;
          return;
        }
      }
    }

    if (
      this.gC &&
      !this.gs &&
      !this.go &&
      !this.gn &&
      !this.gq &&
      this.hF !== 24 &&
      this.hF !== 48 &&
      this.hF !== 30
    ) {
      this.hA = true;
      this.setState(3);
    }

    if (this.gD) {
      this.setState(26);
    }
  }

  /**
   * Exact port of `e.h(boolean var1)` (`e.java` lines 5370-5443): Single-press Action (`s == 8`).
   */
  private handlePressAction(var1: boolean): boolean {
    if (!this.gq && !this.go && !this.gn && this.gC && !this.gD) {
      if (this.hE === 0 || this.hE === 3) {
        this.hA = false;
        var1 = true;
        if (this.hg && this.hp > 20) {
          // Medusa's Gaze
          this.setState(94);
        } else if (this.hh && this.hp > 100) {
          // Army of Hades
          this.setState(95);
        } else {
          // Basic ground attack chain
          this.setState(54);
        }
      } else if (this.gz) {
        // Combo advance
        if (this.hE === 54) this.setState(55);
        else if (this.hE === 55) this.setState(56);
        else if (this.hE === 56) this.setState(60);
        else if (this.hE === 72) this.setState(73);
        else if (this.hE === 73) this.setState(74);
        else if (this.hE === 74) this.setState(77);
      }
    } else if (
      !this.gC &&
      !this.gD &&
      !this.gr &&
      !this.gu &&
      this.hF !== 120 &&
      this.hF !== 121 &&
      this.hE !== 37
    ) {
      if (!this.gt) {
        this.setState(67);
      } else {
        // Air combo advance
        if (this.hE === 67) this.setState(68);
        else if (this.hE === 68) this.setState(69);
        else if (this.hE === 83) this.setState(84);
        else if (this.hE === 84) this.setState(85);
      }
    } else if (this.gu && !this.gz) {
      if (this.hE !== 47) {
        this.setState(47);
      }
    } else if (this.gr && !this.gz) {
      this.setState(36);
    } else if (this.hF === 21) {
      this.setState(0);
    }
    return var1;
  }

  /**
   * Exact port of `e.z()` (`e.java` lines 5096-5129): Key release transitions.
   */
  private handleReleaseTransitions(t: number): void {
    if (t !== 6 && this.hA) {
      this.hA = false;
      this.setState(0);
    }

    if (t !== 2 && t !== 5) {
      if (this.hE === 9) {
        this.setState(0);
      } else if (this.hE === 46) {
        this.setState(45);
      } else if (this.gs && this.hE !== 50) {
        this.setState(50);
      }
    }

    if ((t !== 1 && this.hF === 35) || (t !== 6 && this.hF === 34)) {
      this.setState(33);
    }
  }

  /**
   * Exact port of `e.e(boolean var1)` (`e.java` lines 5081-5094): Airborne transitions.
   */
  private handleAirborneTransition(var1: boolean): void {
    if (
      (this.hE !== 24 && this.hE !== 25 && this.hE !== 44 && this.hE !== 38 ? true : var1) &&
      (this.hE === 24 ||
        this.hE === 25 ||
        this.hE === 44 ||
        this.hE === 40 ||
        this.hE === 38 ||
        this.hF === 121 ||
        this.hF === 120 ||
        this.gv ||
        this.gr ||
        this.gu)
    ) {
      if ((this.hE === 47 || this.hE === 118 || this.hE === 119) && !var1) {
        this.setState(45);
      } else if (this.gC && this.hE !== 24 && this.hE !== 26) {
        this.setState(0);
      }
    } else {
      this.setState(26);
    }
  }

  /**
   * Exact port of `e.d(boolean var1)` (`e.java` lines 4917-5079): Animation completion transitions.
   */
  private handleAnimCompleteTransition(var1: boolean): void {
    // 1. Open Chest completion (`hE == 31`, lines 4918-4928)
    if (this.hE === 31 && !var1) {
      for (let i = this.levelRenderer.chests.length - 1; i >= 0; i--) {
        const chest = this.levelRenderer.chests[i];
        if (
          !chest.ft &&
          LevelRenderer.rectsOverlap(
            chest.fu,
            chest.fv,
            chest.fr,
            chest.fs,
            this.ge - this.hB,
            this.gf,
            this.gG,
            this.gH
          )
        ) {
          chest.fx += 4;
          this.setState(31);
          chest.player.setAnimation(chest.fx);
          chest.ft = true;
          this.levelRenderer.clearTriggerFlagsAround(this.gA, this.gB);
        }
      }
    }

    if (this.hE === 27 && !this.hA && !this.hf) {
      this.setState(28);
    } else if (this.hE === 27 && this.hA) {
      this.setState(3);
    } else if (this.hA && this.hE === 3) {
      this.setState(3);
    } else if (this.hE === 38) {
      this.setState(0);
    } else if (this.hE === 40) {
      this.setState(26);
    } else if (this.hF === 121) {
      if ((this.levelRenderer.getTileFlags(this.gA, this.gB + 1) & 8192) !== 0) {
        this.gD = true;
      }
      this.setState(0);
    } else if (this.hF === 120) {
      this.gf += 16;
      this.setState(26);
    } else if (this.hE !== 47 && this.hE !== 119 && this.hE !== 118) {
      if (this.hE === 97) {
        this.setState(3);
      } else {
        if (this.hE === 23) {
          this.setState(21);
          return;
        }
        if (this.hE === 36) {
          this.setState(33);
          return;
        }
        if (
          (!this.hA || !this.gC) &&
          (this.gC ||
            (this.gD &&
              this.hF !== 26 &&
              this.hF !== 25 &&
              this.hF !== 70 &&
              !this.gt &&
              this.hF !== 24))
        ) {
          if (this.hF === 48) {
            this.setState(50);
            return;
          }
          this.setState(0);
          return;
        }
        if (this.hE !== 26 && this.gt) {
          this.setState(70);
          return;
        }
        if ((!this.gD || this.hF === 70) && !this.gC && !this.gu) {
          this.setState(26);
        }
      }
    } else {
      this.setState(45);
    }
  }

  /**
   * Exact port of `e.s()` (`e.java` lines 4016-4075):
   * Validates tile clearance and shifts a Group 27 pushable box when Kratos pushes (`22`) or pulls (`23`).
   */
  private solvePushBoxMove(): boolean {
    const box = this.levelRenderer.pushBoxes[this.gh];
    if (!box) return false;

    const var1 =
      (!this.hy || this.hE !== 22) && (this.hy || this.hE !== 23) ? -1 : 1;
    let var2 = this.gp === 1 ? 1 : 0;
    const var3 = box.eU + (var1 < 0 ? box.eY - 1 : 0);
    const var4 = this.gB;
    let var5 = box.eZ;
    if (this.hE === 23 && var5 <= this.hk) {
      var5 = this.hk + 1;
    }

    for (let var6 = 0; var6 < var5; var6++) {
      if ((this.levelRenderer.getTileFlags(var3 + var1 + var1, var4 - var6) & 1) !== 0) {
        return false;
      }
      if (
        this.hE === 23 &&
        ((this.levelRenderer.getTileFlags(var3 + var1 * 3, var4 - var6) & 1) !== 0 ||
          (this.levelRenderer.getTileFlags(var3 + var1 * 4, var4 - var6) & 1) !== 0)
      ) {
        return false;
      }
      if (
        (this.levelRenderer.getTileFlags(var3 + var1 * (box.eY + var2), var4 - var6) & 1) !== 0
      ) {
        var2 = 0;
      }
    }

    if (
      this.hE === 23 &&
      (this.levelRenderer.getTileFlags(this.gA + var1, var4) & 1) !== 0
    ) {
      return false;
    }

    for (let dx = 0; dx < box.eY; dx++) {
      for (let dy = 0; dy < box.eZ; dy++) {
        this.levelRenderer.clearTileFlag(box.eU + dx, var4 - dy, 1 | 512);
        if (box.fa) {
          this.levelRenderer.clearTileFlag(box.eU + dx, var4 - dy, 8);
        }
      }
    }

    box.eU += var1 > 0 ? 1 + var2 : -(1 + var2);

    for (let dx = 0; dx < box.eY; dx++) {
      for (let dy = 0; dy < box.eZ; dy++) {
        this.levelRenderer.setTileFlag(box.eU + dx, var4 - dy, 1 | 512);
        if (box.fa) {
          this.levelRenderer.setTileFlag(var3 + dx + var2 + box.eY * var1, var4 - dy, 8);
        }
      }
    }

    box.player.setAnimation(box.eR + (3 << var2));
    return true;
  }

  /**
   * Exact port of `e.f(int var1, int var2)` (`e.java` lines 3858-4014):
   * Sub-tile 8-bit fixed-point DDA raycast solver stepping `(hl, hm)` across `(var5, var6)` tiles.
   */
  private solveCollision(var1: number, var2: number): void {
    const stepA = this.player.stepA;
    const stepB = this.player.stepB;

    if (stepA !== 0) {
      this.ge +=
        (!this.hy || stepA <= 0) && (this.hy || stepA >= 0) ? this.hC : this.hB - 1;
    }

    if (stepB < 0) {
      this.gf -= this.gH;
    } else {
      this.gf -= 1;
    }

    let var5 = var1;
    let var6 = this.wTileY;
    this.hl = (this.ge - var1 * 16) << 8;
    if (this.hl <= 0) {
      var5 = var1 - 1;
      this.hl += 4096;
    } else if (this.hl >= 4096) {
      var5 = var1 + 1;
      this.hl -= 4096;
    }

    this.hm = (this.gf - var6 * 16) << 8;
    if (stepA !== 0) {
      this.ge -=
        (!this.hy || stepA <= 0) && (this.hy || stepA >= 0) ? this.hC : this.hB - 1;
    }

    if (stepB < 0) {
      this.gf += this.gH;
    } else {
      this.gf += 1;
    }

    let var7 = (stepA > 0 ? stepA : -stepA) << 8;
    let var8 = (stepB > 0 ? stepB : -stepB) << 8;
    let var9 = var7;
    let var10 = var8;
    this.hG = true;
    this.hH = true;

    if (var7 > var8) {
      if (var7 > 4096) {
        var8 = Math.trunc(((var8 * 16) << 8) / var7);
        var7 = 4096;
      }
    } else if (var8 > 4096) {
      var7 = Math.trunc(((var7 * 16) << 8) / var8);
      var8 = 4096;
    }

    const var3 =
      (!this.hy || stepA <= 0) && (this.hy || stepA >= 0) ? -var7 : var7;
    const var4 = stepB > 0 ? var8 : -var8;
    let var11 = 0;
    let var12 = 0;
    this.hM = 0;
    this.hN = 0;

    while (var11 < var9 || var12 < var10) {
      this.hl += var3;
      this.hm += var4;
      if (this.hl >= 4096) {
        this.hl -= 4096;
        var5++;
      } else if (this.hl < 0) {
        this.hl += 4096;
        var5--;
      }

      if (this.hm >= 4096) {
        this.hm -= 4096;
        var6++;
      } else if (this.hm < 0) {
        this.hm += 4096;
        var6--;
      }

      this.checkTileStep(var5, var6);

      if (this.hG) {
        this.hM += var3;
        var11 += var7;
      } else {
        this.hl -= var3;
        var9 = var11;
        if ((this.levelRenderer.getTileFlags(var1, var2) & 1) !== 0 && !this.hJ) {
          this.hM = 0;
          if ((!this.hy || stepA <= 0) && (this.hy || stepA >= 0)) {
            this.ge = var1 * 16 + (this.gG >> 1);
          } else {
            this.ge = (var1 + 1) * 16 - (this.gG >> 1);
          }
        }
      }

      if (this.hH) {
        this.hN += var4;
        var12 += var8;
      } else {
        this.hm -= var4;
        var10 = var12;
        if (stepB < 0 && this.gr) {
          var6 += this.hk + 1;
        }
      }
    }

    this.ge += this.hM >> 8;
    this.gf += this.hN >> 8;
    if (var11 > var9) {
      const var14 = (var11 - var9) >> 8;
      this.ge -= var3 > 0 ? var14 : -var14;
    }

    if (var12 > var10) {
      const var15 = (var12 - var10) >> 8;
      this.gf -= var4 > 0 ? var15 : -var15;
    }

    if (!this.hG) {
      if (this.hl < 0) {
        var5--;
      } else if (this.hl > 16) {
        var5++;
      }
    }

    this.gA = var5;
    this.gB = var6;
  }

  /**
   * Exact port of `e.g(int var1, int var2)` (`e.java` lines 4077-4416):
   * Evaluates horizontal/vertical tile collisions, checkpoints (`1024`), rope doors (`256`),
   * pushable boxes (`512`), ledge grabs (`37`/`38`), balance beam catches (`120`),
   * ceiling grabs (`45`), ceiling bonks (`99`), ladder mounts (`33`), and landing (`27`).
   */
  private checkTileStep(var1: number, var2: number): void {
    const stepA = this.player.stepA;
    const stepB = this.player.stepB;
    const jc = this.levelRenderer.level.dimensions.width_tiles;
    const jd = this.levelRenderer.level.dimensions.height_tiles;
    const var3 = (this.hy && stepA > 0) || (!this.hy && stepA < 0);

    // 1. Checkpoint tile (flag 1024, lines 4079-4083)
    if ((this.levelRenderer.getTileFlags(var1, var2) & 1024) !== 0) {
      this.gc = var1 * 16;
      this.gd = var2 * 16;
      this.hz = this.hy;
    }

    // 2. Rope / Zipline door trigger strip (flag 256, lines 4085-4107)
    if ((this.levelRenderer.getTileFlags(var1, var2) & 256) !== 0) {
      let var4 = var2;
      while ((this.levelRenderer.getTileFlags(var1, var4) & 256) !== 0) {
        var4--;
      }
      var4++;

      for (const trig of this.levelRenderer.ropeDoorTriggers) {
        if (trig.dc === var1 && trig.dd === var4) {
          for (const door of this.levelRenderer.doors) {
            if (door.cW === trig.db) {
              door.cZ = !door.cZ;
            }
          }
          break;
        }
      }

      while ((this.levelRenderer.getTileFlags(var1, var4) & 256) !== 0) {
        this.levelRenderer.clearTileFlag(var1, var4, 256);
        var4++;
      }
    }

    // 3. Horizontal wall & push-box check across Kratos's vertical span (`gH`, lines 4163-4219)
    let var17 = 0;
    this.hI = false;

    for (let var24 = this.gH; var24 > 0; var24 -= 16) {
      if (var2 < jd) {
        if (var2 - var17 < 0 || var2 - var17 >= jd) {
          this.hG = false;
          break;
        }

        if (var24 > 0) {
          if (this.hG && var1 >= 0) {
            this.hG = (this.levelRenderer.getTileFlags(var1, var2 - var17) & 1) === 0;
          } else {
            this.hG = false;
          }

          if (!this.hG) {
            // Check Pushable Box grab (`flag 512`, lines 4181-4198)
            if (
              this.hE !== 22 &&
              this.gx &&
              (this.levelRenderer.getTileFlags(var1, var2 - var17) & 512) !== 0
            ) {
              const var27 = this.hy ? 1 : -1;
              let var30 = 0;
              while (
                (this.levelRenderer.getTileFlags(var1 + var30, var2) & 512) !== 0 &&
                !this.hy
              ) {
                var30 += var27;
              }
              var30 += this.hy ? 0 : 1;

              for (let i = this.levelRenderer.pushBoxes.length - 1; i >= 0; i--) {
                const box = this.levelRenderer.pushBoxes[i];
                if (box.eU === var1 + var30 && box.eV === var2 + 1) {
                  this.hJ = true;
                  this.ge = var1 * 16 + (!this.hy ? this.hB : this.hC);
                  this.setState(21);
                  this.gh = i;
                }
              }
            }
            break;
          }
        }

        if (var1 < 0 || var1 >= jc) {
          this.hG = false;
        }

        if (stepB >= 0) {
          var17++;
        } else {
          var17--;
        }

        // Ceiling hang drop-off when moving off flag 16 (`gu`, lines 4213-4217)
        if (
          this.gu &&
          var24 - 16 <= 0 &&
          stepB === 0 &&
          (this.levelRenderer.getTileFlags(var1, var2 - var17) & 16) === 0
        ) {
          this.hG = false;
          this.setState(26);
          this.gf += 1;
        }
      }
    }

    // 4. Ledge Grab & Ladder Top Climb-Up Detection (`hE = 37` / `38`, lines 4221-4260)
    let var25 = var1;
    if (stepA === 0 && stepB !== 0) {
      var25 = var1 + (this.hy ? 1 : -1);
    }

    if (stepB >= 0) {
      var17 = this.hk;
      if (this.hm < 2048) {
        var17++;
      }
    } else {
      var17 = 0;
    }

    const var28 = var2 - var17;
    const var32 = this.hy ? -1 : 1;
    if (
      !this.gz &&
      (this.levelRenderer.getTileFlags(var25, var28 - 1) & 1) === 0 &&
      ((this.levelRenderer.getTileFlags(var25, var28) & 1) !== 0 || this.gy) &&
      (this.levelRenderer.getTileFlags(var25 + var32, var28) & 1) === 0 &&
      (this.levelRenderer.getTileFlags(var25 + var32, var28 - 1) & 1) === 0 &&
      (this.levelRenderer.getTileFlags(var25 + var32, var28 + this.hk) & 1) === 0 &&
      stepB !== 0 &&
      (!this.gr || this.gy) &&
      !this.gv &&
      !this.gC
    ) {
      if (this.gy && stepB < 0) {
        this.setState(38);
        let var34 = 0;
        if ((this.levelRenderer.getTileFlags(var25, var2) & 1) === 0) {
          var34++;
        }
        this.gf = (var2 + var34) * 16 + this.gH;
      } else if (this.hE !== 40 && !this.gr) {
        this.setState(37);
        this.gf = (var2 - var17) * 16 + this.gH;
      }

      this.hN = 0;
      if (!this.hy) {
        var25++;
      }
      this.ge = var25 * 16 + (this.hy ? -(this.gG >> 1) : this.gG >> 1);
      this.hM = 0;
      this.hH = false;
      this.hG = false;
      return;
    }

    // 5. Vertical Floor, Balance Beam, Ceiling Hang, Zipline, & Landing Check (lines 4261-4358)
    if (!this.hG && stepA !== 0) {
      var17 = var3 ? -1 : 1;
    } else {
      var17 = 0;
    }

    const var33 = var2;
    let var9 = stepB > 0 ? var2 - this.hk : var2 + this.hk;
    if (var9 < 0) {
      var9 = 0;
    }

    if (stepB !== 0) {
      for (let var11 = this.gG; var11 > 0; var11 -= 16) {
        if (var1 + var17 >= 0 && var1 + var17 < jc) {
          if (this.hH) {
            this.hH = (this.levelRenderer.getTileFlags(var1 + var17, var33) & 1) === 0;
            const headFlags = this.levelRenderer.getTileFlags(var1 + var17, var9);
            if (
              ((headFlags & 16) !== 0 || (headFlags & 8192) !== 0) &&
              this.hF !== 121 &&
              this.hE !== 38
            ) {
              if (
                (headFlags & 8192) !== 0 &&
                this.hF !== 24 &&
                this.hF !== 25 &&
                !this.gt
              ) {
                this.setState(120);
                this.gf = (var9 + 1) * 16 + this.gH;
                this.hH = false;
                this.hN = 0;
                this.gD = true;
              } else if ((headFlags & 16) !== 0) {
                this.gf = (var9 + 1) * 16 + this.gH;
                this.hH = false;
                this.hN = 0;
                this.setState(45);
              }
            }
          }

          if (!this.hH && stepB > 0 && !this.gu && this.hE !== 38) {
            this.gf = var2 * 16;
            this.hN = 0;
            this.hI = true;
            let var12 = var1;
            if ((!this.hy || stepA <= 0) && (this.hy || stepA >= 0)) {
              if (stepA !== 0) {
                var12 = var1 + 1;
              }
            } else {
              var12 = var1 - 1;
            }

            if (
              (this.levelRenderer.getTileFlags(var12, var2) & 16384) !== 0 &&
              this.hF !== 48
            ) {
              this.setState(50);
            } else if (
              this.hE !== 77 &&
              this.hE !== 95 &&
              this.hE !== 60 &&
              this.hF !== 120 &&
              this.hF !== 121 &&
              this.hF !== 48
            ) {
              this.setState(27);
              if (this.hO >= 4) {
                this.cameraController.triggerShake(200);
              }
            }
          }

          if (!this.hH && (this.hF === 24 || this.hF === 25) && stepB < 0) {
            this.setState(99);
            break;
          }

          if (var33 < 0 || var33 >= jd) {
            this.hH = false;
          }

          if (stepA === 0) {
            break;
          }
        }

        if (var3) {
          var17--;
        } else {
          var17++;
        }
      }
    }

    // 6. Ladder / Climbable Wall Bounds Check (`gr`, lines 4360-4381)
    let climbCheckX = var1;
    if (this.gr && this.hE !== 38) {
      climbCheckX += this.hy ? 1 : -1;
      const climbFlags = this.levelRenderer.getTileFlags(climbCheckX, var33);
      if ((climbFlags & 8) === 0) {
        this.hH = false;
        this.gf = (var33 + (stepB < 0 ? 1 : 0)) * 16 + (stepB < 0 ? this.gH : 0);
        if (stepB >= 0 || (climbFlags & 1) !== 0) {
          if (this.hF === 34) {
            this.setState(26);
          } else {
            this.setState(33);
          }
          this.hH = false;
        }
        if (stepB < 0) {
          this.gy = true;
        }
      }
    }

    // 7. Wall Snap & Ladder Mount (`flag 8`, lines 4383-4401)
    if (!this.hG && stepA !== 0 && !this.hI && this.hE !== 38 && !this.gr && !this.gt) {
      if (
        (!this.hy || stepA <= 0 || this.hl << 8 <= 16 - (this.gG >> 1)) &&
        (this.hy || stepA >= 0 || this.hl >> 8 >= this.gG >> 1)
      ) {
        if (
          (!this.hy && stepA > 0 && this.hl << 8 > 16 - (this.gG >> 1)) ||
          (this.hy && stepA < 0 && this.hl >> 8 < this.gG >> 1)
        ) {
          this.ge = (climbCheckX + 1) * 16 - this.hC;
          this.hJ = true;
          if (
            (this.levelRenderer.getTileFlags(climbCheckX, var33 + (stepB > 0 ? -1 : 1)) & 8) !== 0 &&
            (this.levelRenderer.getTileFlags(
              climbCheckX,
              var33 + (stepB > 0 ? -this.hk : this.hk)
            ) &
              8) !== 0 &&
            stepB !== 0
          ) {
            this.setState(33);
          }
        }
      } else {
        this.ge = climbCheckX * 16 - this.hB;
        this.hJ = true;
        if (
          (this.levelRenderer.getTileFlags(climbCheckX, var33 + (stepB > 0 ? -1 : 1)) & 8) !== 0 &&
          (this.levelRenderer.getTileFlags(
            climbCheckX,
            var33 + (stepB > 0 ? -this.hk : this.hk)
          ) &
            8) !== 0 &&
          this.hE !== 38 &&
          stepB !== 0
        ) {
          this.setState(33);
        }
      }
      this.hM = 0;
    }

    // 8. Stop running / zipline sliding when hitting a wall (`!hG`, lines 4403-4409)
    if (!this.hG && stepA !== 0) {
      if (this.hE === 9) {
        this.setState(0);
      } else if (this.hE === 51) {
        this.setState(50);
      }
    }
  }

  public getStateName(): string {
    return KRATOS_STATE_NAMES[this.hE] || `STATE_${this.hE}`;
  }

  public getActiveFlagsSummary(): string {
    const flags: string[] = [];
    if (this.gC) flags.push("GROUND");
    if (this.gD) flags.push("BEAM");
    if (this.gx) flags.push("RUN");
    if (this.hA) flags.push("CROUCH");
    if (this.gr) flags.push("CLIMB");
    if (this.gv) flags.push("LEDGE");
    if (this.gu) flags.push("CEIL");
    if (this.gs) flags.push("ZIPLINE");
    if (this.gw) flags.push("STAIR");
    if (this.go) flags.push("PUSHBOX");
    if (this.gz) flags.push("ATK");
    if (this.gt) flags.push("AIR_ATK");
    if (this.aS) flags.push(`@${this.aU}`);
    return flags.length > 0 ? flags.join("|") : "AIR";
  }

  public destroy(): void {
    this.player.destroy();
  }
}
