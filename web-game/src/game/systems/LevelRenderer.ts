import * as Phaser from "phaser";
import { TILE_SIZE } from "../constants";
import type { LevelData } from "../types/assets";
import { AssetRegistry } from "./AssetRegistry";
import { CompositeAnimPlayer } from "./CompositeAnimPlayer";
import { SliceLoader } from "./SliceLoader";

export interface CameraTriggerZone {
  x: number;
  y: number;
  aI: boolean;
  aH: boolean;
}

export interface ChestInstance {
  fu: number;
  fv: number;
  fx: number;
  ft: boolean;
  fw: number;
  fr: number;
  fs: number;
  player: CompositeAnimPlayer;
}

export interface PlatformInstance {
  fe: number;
  ff: number;
  fg: number;
  fh: number;
  fi: number;
  fj: number;
  fk: number;
  fl: number;
  fm: number;
  fn: boolean;
  fo: boolean;
  player: CompositeAnimPlayer;
}

export interface ContextTriggerInstance {
  cp: number;
  cq: number;
  cr: number;
  cs: number;
}

export interface RopeDoorTriggerInstance {
  dc: number;
  dd: number;
  db: number;
  lengthTiles: number;
}

export interface PushBoxInstance {
  eR: number;
  eS: number;
  eT: number;
  eU: number;
  eV: number;
  eW: number;
  eX: number;
  eY: number;
  eZ: number;
  fa: boolean;
  player: CompositeAnimPlayer;
}

export interface LeverInstance {
  cC: number;
  cD: number;
  cJ: number;
  cK: number;
  cE: boolean;
  cF: number;
  cG: number;
  cH: boolean;
  cI: number;
  cL: number[];
  player: CompositeAnimPlayer;
}

export interface DoorInstance {
  cQ: number;
  cR: number;
  cO: number;
  cP: number;
  cW: number;
  cY: number;
  cZ: boolean;
  da: boolean;
  cS: number;
  cU: number;
  cT: number;
  cV: number;
  cX: number;
  player: CompositeAnimPlayer;
}

export interface RootMotionPropInstance {
  groupType: number;
  animBankId: number;
  animIndex: number;
  x: number;
  y: number;
  originX: number;
  originY: number;
  width: number;
  height: number;
  flag?: boolean;
  player: CompositeAnimPlayer;
}

/**
 * Layered Level Renderer & Collision World implementing the exact pipeline of `b.java` (`iT`)
 * and `e.java` (`a(byte[], boolean)`, `q()`, `h(int, int)`, `b(int, int, int)`, `c(int, int, int)`).
 */
export class LevelRenderer {
  private scene: Phaser.Scene;
  public level!: LevelData;

  public tilemapLayer!: Phaser.GameObjects.Container;
  public bgStampsLayer!: Phaser.GameObjects.Container;
  public propsLayer!: Phaser.GameObjects.Container;
  public entitiesLayer!: Phaser.GameObjects.Container;
  public fgStampsLayer!: Phaser.GameObjects.Container;
  public debugGridGraphics!: Phaser.GameObjects.Graphics;

  public animatedProps: CompositeAnimPlayer[] = [];
  public collisionGrid: Uint16Array = new Uint16Array(0);
  public renderedStampsCount: number = 0;
  public renderedEntitiesCount: number = 0;

  // Structured runtime level object tables matching e.java
  public cameraTriggers: CameraTriggerZone[] = []; // Group 3 (aF, aG, aI, aH)
  public chests: ChestInstance[] = []; // Group 4 (fu, fv, fx, ft, fw, fr, fs)
  public scriptedAnimsA: number[] = []; // Group 6 (aZ[40])
  public scriptedAnimsB: number[] = []; // Group 6 (ba[40])
  public platforms: PlatformInstance[] = []; // Group 7 (fe..fo)
  public contextTriggers: ContextTriggerInstance[] = []; // Group 8 (cp, cq, cr, cs)
  public ropeDoorTriggers: RopeDoorTriggerInstance[] = []; // Group 9 (dc, dd, db)
  public pushBoxes: PushBoxInstance[] = []; // Group 27 (eR..fa)
  public levers: LeverInstance[] = []; // Group 34 (cC..cL)
  public doors: DoorInstance[] = []; // Group 35 (cQ..da)
  public rootMotionProps: RootMotionPropInstance[] = []; // Groups 17, 21, 32, 33, 36

  // Lever-triggered door state (`hS`, `hT` in e.java)
  public pendingLeverDoorTriggerIdx: number = -1;
  public pendingLeverAnimIdx: number = -1;

  // Camera focus callback when a lever opens a door with `da == true` (`x(int)` in e.java lines 7681-7697)
  public onDoorCameraFocus?: (focusX: number, focusY: number) => void;

  constructor(scene: Phaser.Scene) {
    this.scene = scene;
  }

  public buildLevel(level: LevelData): void {
    this.clear();
    this.level = level;

    this.tilemapLayer = this.scene.add.container(0, 0).setDepth(10);
    this.bgStampsLayer = this.scene.add.container(0, 0).setDepth(15);
    this.propsLayer = this.scene.add.container(0, 0).setDepth(25);
    this.entitiesLayer = this.scene.add.container(0, 0).setDepth(30);
    this.fgStampsLayer = this.scene.add.container(0, 0).setDepth(45);
    this.debugGridGraphics = this.scene.add.graphics().setDepth(55);

    this.buildBaseTilemap();
    this.buildCollisionGridAndObjects();
  }

  private buildBaseTilemap(): void {
    const registry = AssetRegistry.get();
    const { width_tiles, height_tiles } = this.level.dimensions;
    const palVar = this.level.palette_variant;
    const tilesetKey = registry.getTextureKey(2058, palVar);

    for (let ty = 0; ty < height_tiles; ty++) {
      const row = this.level.visual_tile_grid[ty];
      if (!row) continue;
      for (let tx = 0; tx < width_tiles; tx++) {
        const tileIdx = row[tx] & 0x7f;
        const frameName = registry.getTileFrameName(tileIdx);
        const img = this.scene.add.image(tx * TILE_SIZE, ty * TILE_SIZE, tilesetKey, frameName);
        img.setOrigin(0, 0);
        this.tilemapLayer.add(img);
      }
    }
  }

  /**
   * Exact port of `e.h(int var1, int var2)` (`e.java` lines 3668-3672):
   * Returns 16-bit collision flags at tile `(tx, ty)`, or `65` (`1 | 64`, solid + hazard) if out of bounds.
   */
  public getTileFlags(tx: number, ty: number): number {
    if (!this.level) return 65;
    const { width_tiles, height_tiles } = this.level.dimensions;
    if (tx < 0 || ty < 0 || tx >= width_tiles || ty >= height_tiles) {
      return 65;
    }
    return this.collisionGrid[ty * width_tiles + tx];
  }

  /**
   * Exact port of `e.b(int var1, int var2, int var3)` (`e.java` lines 3674-3680).
   */
  public setTileFlag(tx: number, ty: number, flag: number): void {
    const { width_tiles, height_tiles } = this.level.dimensions;
    if (tx < 0 || ty < 0 || tx >= width_tiles || ty >= height_tiles) return;
    this.collisionGrid[ty * width_tiles + tx] |= flag;
  }

  /**
   * Exact port of `e.c(int var1, int var2, int var3)` (`e.java` lines 3682-3688).
   */
  public clearTileFlag(tx: number, ty: number, flag: number): void {
    const { width_tiles, height_tiles } = this.level.dimensions;
    if (tx < 0 || ty < 0 || tx >= width_tiles || ty >= height_tiles) return;
    this.collisionGrid[ty * width_tiles + tx] &= ~flag;
  }

  /**
   * Exact port of `e.e(int var1, int var2)` (`e.java` lines 3700-3707):
   * Clears trigger flag `128` in a 3x3 tile neighborhood around `(tx, ty)`.
   */
  public clearTriggerFlagsAround(tx: number, ty: number): void {
    for (let dx = -1; dx < 2; dx++) {
      for (let dy = -1; dy < 2; dy++) {
        this.clearTileFlag(tx + dx, ty + dy, 128);
      }
    }
  }

  /**
   * Exact port of `e.a(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2)` (`e.java` lines 3654-3656).
   */
  public static rectsOverlap(
    x1: number,
    y1: number,
    w1: number,
    h1: number,
    x2: number,
    y2: number,
    w2: number,
    h2: number
  ): boolean {
    return x1 + w1 >= x2 && y1 + h1 >= y2 && x2 + w2 >= x1 && y2 + h2 >= y1;
  }

  /**
   * Reconstructs static stamps (`Group 23` in `q()`), interactive props, enemy spawn previews,
   * and the 16-bit tile collision grid (`jb[]`) from `level.object_groups`.
   */
  private buildCollisionGridAndObjects(): void {
    const registry = AssetRegistry.get();
    const { width_tiles, height_tiles } = this.level.dimensions;
    const palVar = this.level.palette_variant;

    this.collisionGrid = new Uint16Array(width_tiles * height_tiles);
    const solidFlags = this.level.unique_tile_solid_flags;
    if (solidFlags) {
      // Note: In e.java lines 2652-2658, unique_tile_solid_flags is per unique visual tile or grid cell.
      // Let's check length: if length == width_tiles * height_tiles, apply directly; otherwise map via visual_tile_grid.
      if (solidFlags.length === width_tiles * height_tiles) {
        for (let i = 0; i < solidFlags.length; i++) {
          if (solidFlags[i]) this.collisionGrid[i] |= 1;
        }
      } else {
        for (let ty = 0; ty < height_tiles; ty++) {
          const row = this.level.visual_tile_grid[ty];
          if (!row) continue;
          for (let tx = 0; tx < width_tiles; tx++) {
            const tileIdx = row[tx] & 0x7f;
            if (solidFlags[tileIdx]) {
              this.collisionGrid[ty * width_tiles + tx] |= 1;
            }
          }
        }
      }
    }

    this.renderedStampsCount = 0;
    this.renderedEntitiesCount = 0;

    for (const group of this.level.object_groups) {
      const gType = group.group_type;

      // Group 3: Camera Pan Trigger Zones (e.java lines 2694-2704)
      if (gType === 3) {
        for (const inst of group.instances) {
          this.cameraTriggers.push({
            x: inst.x,
            y: inst.y,
            aI: Boolean(inst.flag0),
            aH: Boolean(inst.flag1),
          });
        }
      }

      // Group 23: Static Background & Foreground Environment Stamps (3073 anim / 3076 slice)
      else if (gType === 23) {
        const stampBank = registry.animBanks.get(3073);
        const stampSliceTable = registry.slices.get(3076);
        if (!stampBank || !stampSliceTable) continue;
        const stampTexKey = registry.getTextureKey(stampSliceTable.image_res_id, palVar);

        // Sort by z_layer 0..7 as in e.q() lines 3545-3556 + a.a(short[], ...) lines 1058-1076
        for (let zLayer = 0; zLayer <= 7; zLayer++) {
          for (let i = group.instances.length - 1; i >= 0; i--) {
            const inst = group.instances[i];
            if (inst.z_layer !== zLayer) continue;
            const seq = stampBank.animations[inst.stamp_anim_index];
            if (!seq || seq.keyframes.length === 0) continue;
            const cf = stampBank.composite_frames[seq.keyframes[0].comp_frame_index];
            if (!cf) continue;

            const targetContainer = zLayer >= 6 ? this.fgStampsLayer : this.bgStampsLayer;
            for (const part of cf.parts) {
              const slice = stampSliceTable.slices[part.slice_index];
              if (!slice || slice.w <= 0 || slice.h <= 0) continue;
              const flipFlags = part.flip_flags & 3;
              const partX = ((flipFlags & 2) === 0 ? 1 : -1) * part.offset_x;
              const partY = ((flipFlags & 1) === 0 ? 1 : -1) * part.offset_y;
              const placement = SliceLoader.computePlacement(
                slice,
                inst.x + partX,
                inst.y + partY,
                flipFlags
              );
              const img = this.scene.add.image(
                placement.drawX,
                placement.drawY,
                stampTexKey,
                registry.getSliceFrameName(3076, part.slice_index)
              );
              img.setOrigin(0, 0);
              img.setFlip(placement.flipX, placement.flipY);
              targetContainer.add(img);
              this.renderedStampsCount++;
            }
          }
        }
      }

      // Group 4: Chests (5120 anim / 5123 slice, e.java lines 2705-2744)
      else if (gType === 4) {
        for (const inst of group.instances) {
          const fx = inst.chest_type;
          let fw = inst.orb_amount;
          if (fx === 3) {
            fw = fw > 0 ? -1 : -2;
          }
          const player = this.spawnPropAnim(5120, fx, inst.x, inst.y, false, palVar);
          const bounds = player.getFrameBounds(0, 0, 0);
          this.chests.push({
            fu: inst.x,
            fv: inst.y,
            fx,
            ft: false,
            fw,
            fr: bounds.width || 24,
            fs: bounds.height || 24,
            player,
          });
        }
      }

      // Group 6: Scripted Animation & Timer Tables (e.java lines 2766-2775)
      else if (gType === 6) {
        if (group.instances.length > 0) {
          const inst = group.instances[0];
          this.scriptedAnimsA = Array.isArray(inst.aZ) ? [...inst.aZ] : [];
          this.scriptedAnimsB = Array.isArray(inst.ba) ? [...inst.ba] : [];
        }
      }

      // Group 7: Moving / Crumbling Platforms & Balance Beams (2048 anim / 2051 slice, e.java lines 2776-2818)
      else if (gType === 7) {
        for (const inst of group.instances) {
          const fe = inst.anim_index;
          const fn = Boolean(inst.flag0);
          const fo = Boolean(inst.flag1);
          const player = this.spawnPropAnim(2048, fe, inst.x, inst.y, false, palVar);
          const bounds = player.getFrameBounds(0, 0, 0);
          const fj = bounds.width;
          const fk = bounds.height;
          const fl = Math.floor(fj / 16);
          const fm = Math.floor(fk / 16);
          const ff = Math.floor(inst.x / 16);
          const fg = Math.floor(inst.y / 16);

          if (player.update(1)) {
            for (let dx = 0; dx < fl; dx++) {
              for (let dy = 1; dy <= fm; dy++) {
                this.setTileFlag(ff + dx, fg - dy, 1);
              }
            }
          }

          this.platforms.push({
            fe,
            ff,
            fg,
            fh: inst.x,
            fi: inst.y,
            fj,
            fk,
            fl,
            fm,
            fn,
            fo,
            player,
          });
        }
      }

      // Group 17: Interactive / Breakable Props (3072 anim / 3075 slice, e.java lines 2991-3004)
      else if (gType === 17) {
        for (const inst of group.instances) {
          const player = this.spawnPropAnim(3072, inst.anim_index, inst.x, inst.y, false, palVar);
          const bounds = player.getFrameBounds(0, 0, 0);
          this.rootMotionProps.push({
            groupType: 17,
            animBankId: 3072,
            animIndex: inst.anim_index,
            x: inst.x,
            y: inst.y,
            originX: inst.x,
            originY: inst.y,
            width: bounds.width,
            height: bounds.height,
            player,
          });
        }
      }

      // Group 21: Flame / VFX Emitters (5121 anim / 5124 slice, e.java lines 3017-3031)
      else if (gType === 21) {
        for (const inst of group.instances) {
          const player = this.spawnPropAnim(5121, inst.anim_index, inst.x, inst.y, false, palVar);
          const bounds = player.getFrameBounds(0, 0, 0);
          this.rootMotionProps.push({
            groupType: 21,
            animBankId: 5121,
            animIndex: inst.anim_index,
            x: inst.x,
            y: inst.y,
            originX: inst.x,
            originY: inst.y,
            width: bounds.width,
            height: bounds.height,
            player,
          });
        }
      }

      // Group 27: Pushable Boxes / Statues (6144 anim / 6146 slice, e.java lines 3102-3144)
      else if (gType === 27) {
        for (const inst of group.instances) {
          const eR = inst.anim_index;
          const fa = Boolean(inst.climbable);
          const player = this.spawnPropAnim(6144, eR, inst.x, inst.y, false, palVar);
          const bounds = player.getFrameBounds(0, 0, 0);
          const eW = bounds.width;
          const eX = bounds.height;
          const eY = Math.floor(eW / 16);
          const eZ = Math.floor(eX / 16);
          const eU = Math.floor(inst.x / 16);
          const eV = Math.floor(inst.y / 16);

          for (let dx = 0; dx < eY; dx++) {
            for (let dy = 1; dy <= eZ; dy++) {
              this.setTileFlag(eU + dx, eV - dy, 1 | 512);
              if (fa) {
                this.setTileFlag(eU + dx, eV - dy, 8);
              }
            }
          }
          this.setTileFlag(eU, eV, 1);

          this.pushBoxes.push({
            eR,
            eS: inst.x,
            eT: inst.y,
            eU,
            eV,
            eW,
            eX,
            eY,
            eZ,
            fa,
            player,
          });
        }
      }

      // Group 32: Magic / Environmental FX (5122 anim / 5125 slice, e.java lines 3209-3225)
      else if (gType === 32) {
        for (const inst of group.instances) {
          const player = this.spawnPropAnim(5122, inst.anim_index, inst.x, inst.y, false, palVar);
          const bounds = player.getFrameBounds(0, 0, 0);
          this.rootMotionProps.push({
            groupType: 32,
            animBankId: 5122,
            animIndex: inst.anim_index,
            x: inst.x,
            y: inst.y,
            originX: inst.x,
            originY: inst.y,
            width: bounds.width,
            height: bounds.height,
            flag: Boolean(inst.flag),
            player,
          });
        }
      }

      // Group 33: Pendulums / Traps (6145 anim / 6147 slice, e.java lines 3226-3244)
      else if (gType === 33) {
        for (const inst of group.instances) {
          const player = this.spawnPropAnim(6145, inst.anim_index, inst.x, inst.y, false, palVar);
          if (inst.phase_offset_ms) {
            player.update(inst.phase_offset_ms);
          }
          const bounds = player.getFrameBounds(0, 0, 0);
          this.rootMotionProps.push({
            groupType: 33,
            animBankId: 6145,
            animIndex: inst.anim_index,
            x: inst.x,
            y: inst.y,
            originX: inst.x,
            originY: inst.y,
            width: bounds.width,
            height: bounds.height,
            player,
          });
        }
      }

      // Group 34: Levers / Switches (2050 anim / 2053 slice, e.java lines 3246-3278)
      else if (gType === 34) {
        for (const inst of group.instances) {
          const cJ = Math.floor(inst.x / 16);
          const cK = Math.floor(inst.y / 16);
          const cC = cJ * 16;
          const cD = cK * 16;
          const cE = Boolean(inst.cE);
          const cI = cE ? 4 : 0;
          const player = this.spawnPropAnim(2050, cI, cC, cD, false, palVar);
          const bounds = player.getFrameBounds(0, 0, 0);
          this.levers.push({
            cC,
            cD,
            cJ,
            cK,
            cE,
            cF: bounds.width,
            cG: bounds.height,
            cH: false,
            cI,
            cL: Array.isArray(inst.targets) ? [...inst.targets] : [0, 0, 0, 0],
            player,
          });
        }
      }

      // Group 35: Doors / Gates / Bridges (2049 anim / 2052 slice, e.java lines 3280-3325)
      else if (gType === 35) {
        for (const inst of group.instances) {
          const cO = Math.floor(inst.x / 16);
          const cP = Math.floor(inst.y / 16);
          const cW = inst.door_type;
          const cY = inst.cY;
          const cZ = Boolean(inst.start_open);
          const da = Boolean(inst.da);

          // Measure closed/base bounds from sequence `(cY << 2) + 0` as in e.java line 3304-3310
          const player = this.spawnPropAnim(2049, (cY << 2) + 0, inst.x, inst.y, false, palVar);
          const bounds = player.getFrameBounds(0, 0, 0);
          const cT = bounds.width;
          const cV = bounds.height;
          const cS = Math.max(1, Math.floor(cT / 16));
          const cU = Math.max(1, Math.floor(cV / 16));

          const cX = (cY << 2) + 2 - (cZ ? 2 : 0);
          player.setAnimation(cX);

          for (let dy = 0; dy < cU; dy++) {
            for (let dx = 0; dx < cS; dx++) {
              if (cZ) {
                this.clearTileFlag(cO + dx, cP - dy, 1);
              } else {
                this.setTileFlag(cO + dx, cP - dy, 1);
              }
            }
          }

          this.doors.push({
            cQ: inst.x,
            cR: inst.y,
            cO,
            cP,
            cW,
            cY,
            cZ,
            da,
            cS,
            cU,
            cT,
            cV,
            cX,
            player,
          });
        }
      }

      // Group 36: Animated Torches / Background Props (3074 anim / 3077 slice, e.java lines 3326-3338)
      else if (gType === 36) {
        for (const inst of group.instances) {
          const player = this.spawnPropAnim(3074, inst.anim_index, inst.x, inst.y, false, palVar);
          const bounds = player.getFrameBounds(0, 0, 0);
          this.rootMotionProps.push({
            groupType: 36,
            animBankId: 3074,
            animIndex: inst.anim_index,
            x: inst.x,
            y: inst.y,
            originX: inst.x,
            originY: inst.y,
            width: bounds.width,
            height: bounds.height,
            player,
          });
        }
      }

      // Groups 11, 12, 13: Enemy & Boss Spawn Slots (rendered in idle pose for level verification)
      else if (gType === 11 || gType === 12 || gType === 13) {
        for (const inst of group.instances) {
          let bankId = 4096;
          let pal = palVar;
          if (gType === 11) {
            bankId = this.level.header.dE_enemy_set_a ? 18432 : 4096;
          } else if (gType === 12) {
            bankId = this.level.header.dF_enemy_set_b ? 16384 : 17408;
          } else if (gType === 13) {
            if (inst.is_argos_boss && !inst.is_dead_rider_boss) {
              bankId = 13312;
            } else if (inst.is_dead_rider_boss) {
              bankId = 14336;
              pal = -1; // Dead Rider uses embedded palette
            } else {
              bankId = 15360;
            }
          }
          const player = new CompositeAnimPlayer(this.scene, bankId, pal, inst.ez ? 1 : 0);
          player.setPosition(inst.x, inst.y);
          player.setFlip(!inst.facing_right, false);
          this.entitiesLayer.add(player.container);
          this.animatedProps.push(player);
          this.renderedEntitiesCount++;
        }
      }

      // Tile Collision Flag Brushes (Groups 0, 5, 8, 9, 10, 14, 18, 22, 25, 28, 29, 30, 31)
      else if (gType === 0 || gType === 10 || gType === 18 || gType === 29) {
        for (const inst of group.instances) {
          const tx = Math.floor(inst.x / 16);
          const ty = Math.floor(inst.y / 16);
          for (let k = 0; k <= inst.length_tiles; k++) {
            this.setTileFlag(tx + k, ty, inst.flag);
          }
        }
      } else if (gType === 5) {
        for (const inst of group.instances) {
          const tx = Math.floor(inst.x / 16);
          const ty = Math.floor(inst.y / 16);
          for (let k = 0; k <= inst.length_tiles; k++) {
            this.setTileFlag(tx, ty + k, inst.flag);
          }
        }
      } else if (gType === 9) {
        for (const inst of group.instances) {
          const tx = Math.floor(inst.x / 16);
          const ty = Math.floor(inst.y / 16);
          this.ropeDoorTriggers.push({
            dc: tx,
            dd: ty,
            db: inst.param3 ?? 0,
            lengthTiles: inst.length_tiles,
          });
          for (let k = 0; k <= inst.length_tiles; k++) {
            this.setTileFlag(tx, ty + k, inst.flag);
          }
        }
      } else if (gType === 8) {
        for (const inst of group.instances) {
          const cp = Math.floor(inst.x / 16);
          const cq = Math.floor(inst.y / 16);
          this.setTileFlag(cp, cq, 128);
          this.contextTriggers.push({
            cp,
            cq,
            cr: inst.string_id ?? 0,
            cs: inst.count ?? 0,
          });
        }
      } else if (gType === 25 || gType === 30 || gType === 31) {
        for (const inst of group.instances) {
          this.setTileFlag(Math.floor(inst.x / 16), Math.floor(inst.y / 16), inst.flag);
        }
      } else if (gType === 14) {
        for (const inst of group.instances) {
          const tx = Math.floor(inst.x / 16);
          const ty = Math.floor(inst.y / 16);
          for (let k = 0; k < inst.barrier_height_tiles; k++) {
            this.setTileFlag(tx, ty + k, 4);
          }
        }
      } else if (gType === 22) {
        for (const inst of group.instances) {
          const tx = Math.floor(inst.x / 16);
          const ty = Math.floor(inst.y / 16);
          if (!inst.flag) continue;
          for (let k = 0; k <= inst.up_tiles; k++) this.setTileFlag(tx, ty - k, 1);
          for (let k = 0; k <= inst.down_tiles; k++) this.setTileFlag(tx, ty + k, 1);
          for (let k = 0; k <= inst.left_tiles; k++) this.setTileFlag(tx - k, ty, 1);
          for (let k = 0; k <= inst.right_tiles; k++) this.setTileFlag(tx + k, ty, 1);
        }
      } else if (gType === 28) {
        for (const inst of group.instances) {
          const tx = Math.floor(inst.x / 16);
          const ty = Math.floor(inst.y / 16);
          for (let dx = 0; dx <= inst.width_tiles; dx++) {
            for (let dy = 0; dy <= inst.height_tiles; dy++) {
              this.setTileFlag(tx + dx, ty + dy, 32);
            }
          }
        }
      }
    }
  }

  private spawnPropAnim(
    animBankId: number,
    animIndex: number,
    x: number,
    y: number,
    flipX: boolean,
    paletteVariant: number
  ): CompositeAnimPlayer {
    const player = new CompositeAnimPlayer(this.scene, animBankId, paletteVariant, animIndex);
    player.setPosition(x, y);
    player.setFlip(flipX, false);
    this.propsLayer.add(player.container);
    this.animatedProps.push(player);
    this.renderedEntitiesCount++;
    return player;
  }

  /**
   * Exact port of `e.x(int var1)` (`e.java` lines 7681-7697):
   * Toggles all Group 35 doors whose `cW` (`door_type`) matches any non-zero target in `lever.cL[0..3]`.
   */
  public triggerLeverTargets(leverIdx: number): void {
    const lever = this.levers[leverIdx];
    if (!lever) return;
    for (let i = 0; i < 4; i++) {
      const targetType = lever.cL[i];
      if (!targetType) continue;
      for (const door of this.doors) {
        if (door.cW === targetType) {
          door.cZ = !door.cZ;
          if (door.da && this.onDoorCameraFocus) {
            this.onDoorCameraFocus(door.cQ, door.cR);
          }
        }
      }
    }
  }

  /**
   * Updates all interactive level objects (`L()`, `N()`, `O()`, `P()`, `Q()`, `R()`, `S()`, `T()`, `U()`, `k()`)
   * in `e.java` lines 5828-6228.
   */
  public updateInteractiveObjects(
    dtMs: number,
    kratosX: number,
    kratosY: number,
    kratosW: number,
    kratosH: number,
    kratosGrounded: boolean,
    kratosFacingRight: boolean,
    kratosStateId: number
  ): void {
    // 1. Chests (`L()` lines 5828-5857)
    for (const chest of this.chests) {
      if (chest.fx >= 0) {
        const playing = chest.player.update(dtMs);
        if (!playing && chest.fw !== 0 && chest.fx < 8 && chest.fx >= 4) {
          chest.fx += 4;
          chest.player.setAnimation(chest.fx);
        }
      }
    }

    // 2. Root-motion animated props (`N()`, `O()`, `P()`, `S()`, `k()`)
    for (const prop of this.rootMotionProps) {
      const playing = prop.player.update(dtMs);
      prop.x += prop.player.stepA;
      prop.y += prop.player.stepB;
      if (!playing && prop.groupType === 33) {
        // Pendulum/trap loop reset (`k()` lines 6223-6227)
        prop.x = prop.originX;
        prop.y = prop.originY;
        prop.player.setAnimation(prop.animIndex);
      }
      prop.player.setPosition(prop.x, prop.y);
    }

    // 3. Levers & Floor Pressure Switches (`Q()` lines 6028-6080)
    for (let i = this.levers.length - 1; i >= 0; i--) {
      const lever = this.levers[i];
      const offset = lever.cE ? 4 : 0;
      const playing = lever.player.update(dtMs);
      const prevAnim = lever.cI;

      // Floor switch (`!cE`) checks Kratos overlap or pushable box on tile (`(h(cJ, cK) & 1) != 0`)
      if (
        !lever.cE &&
        ((kratosGrounded &&
          LevelRenderer.rectsOverlap(
            kratosX - (kratosW >> 1),
            kratosY - kratosH,
            kratosW,
            kratosH,
            lever.cC,
            lever.cD,
            16,
            16
          )) ||
          (this.getTileFlags(lever.cJ, lever.cK) & 1) !== 0)
      ) {
        if (!lever.cH) {
          lever.cH = true;
          this.triggerLeverTargets(i);
          this.pendingLeverAnimIdx = i;
        }
      } else if (!lever.cE && lever.cH) {
        lever.cH = false;
        this.triggerLeverTargets(i);
        this.pendingLeverAnimIdx = i;
      }

      if (!playing) {
        let transitioned = false;
        if (!lever.cH) {
          if (prevAnim === 1 + offset) {
            lever.cI = 2 + offset;
            lever.player.setAnimation(lever.cI);
            transitioned = true;
          }
        } else if (prevAnim === 3 + offset) {
          lever.cI = 0 + offset;
          lever.player.setAnimation(lever.cI);
          transitioned = true;
        }

        if (
          (prevAnim === 3 + offset || prevAnim === 1 + offset) &&
          !transitioned &&
          this.pendingLeverDoorTriggerIdx >= 0
        ) {
          this.triggerLeverTargets(this.pendingLeverDoorTriggerIdx);
          this.pendingLeverDoorTriggerIdx = -1;
        }
      }

      if (this.pendingLeverAnimIdx >= 0 && this.pendingLeverAnimIdx === i) {
        this.pendingLeverAnimIdx = -1;
        if (prevAnim !== 0 + offset && prevAnim !== 3 + offset) {
          if (prevAnim === 2 + offset || prevAnim === 1 + offset) {
            lever.cI = 3 + offset;
            lever.player.setAnimation(lever.cI);
          }
        } else {
          lever.cI = 1 + offset;
          lever.player.setAnimation(lever.cI);
        }
      }
    }

    // 4. Doors / Gates / Bridges (`R()` lines 6082-6126)
    for (const door of this.doors) {
      const playing = door.player.update(dtMs);
      const relAnim = door.cX - (door.cY << 2);
      if (playing) {
        if (door.cZ) {
          if (relAnim === 2) {
            door.cX = (door.cY << 2) + 3;
            door.player.setAnimation(door.cX);
          }
        } else if (relAnim === 0) {
          door.cX = (door.cY << 2) + 1;
          door.player.setAnimation(door.cX);
        }
      } else if (!door.cZ) {
        if (relAnim === 3) {
          door.cX = (door.cY << 2) + 0;
          door.player.setAnimation(door.cX);
        } else if (relAnim === 1) {
          door.cX = (door.cY << 2) + 2;
          door.player.setAnimation(door.cX);
          for (let dy = 0; dy < door.cU; dy++) {
            for (let dx = 0; dx < door.cS; dx++) {
              this.setTileFlag(door.cO + dx, door.cP - dy, 1);
            }
          }
        }
      } else if (relAnim === 1) {
        door.cX = (door.cY << 2) + 2;
        door.player.setAnimation(door.cX);
      } else if (relAnim === 3) {
        door.cX = (door.cY << 2) + 0;
        door.player.setAnimation(door.cX);
        for (let dy = 0; dy < door.cU; dy++) {
          for (let dx = 0; dx < door.cS; dx++) {
            this.clearTileFlag(door.cO + dx, door.cP - dy, 1);
          }
        }
      }
    }

    // 5. Pushable Boxes (`T()` lines 6164-6178)
    for (const box of this.pushBoxes) {
      if (box.player.update(dtMs)) {
        const dirSign =
          (!kratosFacingRight || kratosStateId !== 22) &&
          (kratosFacingRight || kratosStateId !== 23)
            ? -1
            : 1;
        box.eS += dirSign * box.player.stepA;
        box.eT += box.player.stepB;
        box.player.setPosition(box.eS, box.eT);
      } else if (box.player.animIndex !== box.eR) {
        box.player.setAnimation(box.eR);
      }
    }

    // 6. Platforms (`U()` lines 6180-6212)
    for (const plat of this.platforms) {
      if ((plat.fe & 1) !== 0) {
        plat.player.update(dtMs);
      }
    }
  }

  public update(dtMs: number): void {
    // Also update enemy idle preview players in entitiesLayer
    for (const prop of this.animatedProps) {
      if (prop.container.parentContainer === this.entitiesLayer) {
        prop.update(dtMs);
      }
    }
  }

  /**
   * Renders the 16-bit tile collision grid (`jb[]`) as a color-coded wireframe overlay in Debug Mode.
   */
  public renderDebugCollisionGrid(visible: boolean): void {
    this.debugGridGraphics.clear();
    if (!visible || !this.level) return;

    const { width_tiles, height_tiles } = this.level.dimensions;
    for (let ty = 0; ty < height_tiles; ty++) {
      for (let tx = 0; tx < width_tiles; tx++) {
        const flags = this.collisionGrid[ty * width_tiles + tx];
        if (flags === 0) continue;

        let color = 0x00ff66; // Solid floor/wall (flag 1)
        if (flags & (16 | 64)) color = 0xff2222; // Ceiling / kill hazard
        else if (flags & 512) color = 0xffaa00; // Pushable box
        else if (flags & 8) color = 0x00ccff; // Climbable ladder / wall
        else if (flags & 16384) color = 0xff66cc; // Zipline rope
        else if (flags & 8192) color = 0xcc66ff; // Balance beam
        else if (flags & (2048 | 4096)) color = 0x66ffcc; // Stairs (left/right)
        else if (flags & 128) color = 0xffff00; // Context / @ prompt / script trigger
        else if (flags & 256) color = 0xff8800; // Rope door trigger
        else if (flags & 32) color = 0x4488ff; // Combat arena zone
        else if (flags & 1024) color = 0xffffff; // Checkpoint

        this.debugGridGraphics.lineStyle(1, color, 0.7);
        this.debugGridGraphics.strokeRect(tx * TILE_SIZE, ty * TILE_SIZE, TILE_SIZE, TILE_SIZE);
      }
    }
  }

  public clear(): void {
    for (const p of this.animatedProps) {
      p.destroy();
    }
    this.animatedProps = [];
    this.cameraTriggers = [];
    this.chests = [];
    this.scriptedAnimsA = [];
    this.scriptedAnimsB = [];
    this.platforms = [];
    this.contextTriggers = [];
    this.ropeDoorTriggers = [];
    this.pushBoxes = [];
    this.levers = [];
    this.doors = [];
    this.rootMotionProps = [];
    this.pendingLeverDoorTriggerIdx = -1;
    this.pendingLeverAnimIdx = -1;

    this.tilemapLayer?.destroy(true);
    this.bgStampsLayer?.destroy(true);
    this.propsLayer?.destroy(true);
    this.entitiesLayer?.destroy(true);
    this.fgStampsLayer?.destroy(true);
    this.debugGridGraphics?.destroy();
  }
}
