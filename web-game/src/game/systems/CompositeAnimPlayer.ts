import * as Phaser from "phaser";
import { ANIM_TO_SLICE_MAP } from "../constants";
import type {
  AnimBank,
  AnimHitbox,
  AnimKeyframe,
  AnimSequence,
  CompositeFrame,
  SliceTable,
} from "../types/assets";
import { AssetRegistry } from "./AssetRegistry";

/**
 * Exact implementation of `a.java` hierarchical animation playback (`a.g(int)`, `a.a(int, int, int)`,
 * `a.b(int, int)`, `a.a(c[], int, int, int, int, int[])`, and `a.a(Graphics, c[], int, int, int, int)`).
 *
 * State slots in `a.M[var1 * 8 .. +7]`:
 * - `M[+0]`: bank index
 * - `M[+1]`: animIndex (`0..num_animations-1`)
 * - `M[+2]`: keyframeIndex (`var5`, active sequence frame)
 * - `M[+3]`: remainingLoops (`var3`, `-1` = infinite, `0` = stopped, `>0` = N loops)
 * - `M[+4]`: elapsedInFrameMs (`var2`)
 * - `M[+5]`: prevCumDX (`var6`, cumulative root-motion X at previous tick)
 * - `M[+6]`: prevCumDY (`var7`, cumulative root-motion Y at previous tick)
 * - `M[+7]`: renderKeyframeIndex (`var11`, highest-priority keyframe visited this tick)
 */
export class CompositeAnimPlayer {
  private scene: Phaser.Scene;
  public container: Phaser.GameObjects.Container;
  private partPool: Phaser.GameObjects.Image[] = [];

  public animBankId: number;
  public sliceTableId: number;
  public paletteVariant: number;

  public animIndex: number = 0;
  public keyframeIndex: number = 0;       // M[+2]
  public remainingLoops: number = -1;     // M[+3]
  public elapsedInFrameMs: number = 0;    // M[+4]
  public prevCumDX: number = 0;           // M[+5]
  public prevCumDY: number = 0;           // M[+6]
  public renderKeyframeIndex: number = 0; // M[+7]
  public finished: boolean = false;
  public entityFlipFlags: number = 0; // 0 = normal, 2 = flip horizontal, 1 = flip vertical (c.java k[])

  // Raw per-tick root-motion delta matching `super.a` and `super.b` in `a.b(int, int)`
  public stepA: number = 0;
  public stepB: number = 0;
  // Direction-signed convenience aliases
  public lastStepDX: number = 0;
  public lastStepDY: number = 0;

  constructor(
    scene: Phaser.Scene,
    animBankId: number,
    paletteVariant: number = -1,
    initialAnimIndex: number = 0,
    sliceTableOverride?: number
  ) {
    this.scene = scene;
    this.animBankId = animBankId;
    this.sliceTableId = sliceTableOverride ?? ANIM_TO_SLICE_MAP[animBankId] ?? 0;
    this.paletteVariant = paletteVariant;
    this.container = scene.add.container(0, 0);
    this.setAnimation(initialAnimIndex, true);
  }

  public getBank(): AnimBank | undefined {
    return AssetRegistry.get().animBanks.get(this.animBankId);
  }

  public getSliceTable(): SliceTable | undefined {
    return AssetRegistry.get().slices.get(this.sliceTableId);
  }

  public getSequence(): AnimSequence | undefined {
    const bank = this.getBank();
    if (!bank || bank.animations.length === 0) return undefined;
    const safeIdx = ((this.animIndex % bank.animations.length) + bank.animations.length) % bank.animations.length;
    return bank.animations[safeIdx];
  }

  public getCurrentKeyframe(): AnimKeyframe | undefined {
    const seq = this.getSequence();
    if (!seq || seq.keyframes.length === 0) return undefined;
    const safeKf = Math.max(0, Math.min(this.renderKeyframeIndex, seq.keyframes.length - 1));
    return seq.keyframes[safeKf];
  }

  public getCurrentCompositeFrame(): CompositeFrame | undefined {
    const bank = this.getBank();
    const kf = this.getCurrentKeyframe();
    if (!bank || !kf) return undefined;
    return bank.composite_frames[kf.comp_frame_index];
  }

  public getActiveBoxes(): AnimHitbox[] {
    const cf = this.getCurrentCompositeFrame();
    if (!cf) return [];
    const flipX = (this.entityFlipFlags & 2) !== 0;
    const flipY = (this.entityFlipFlags & 1) !== 0;
    if (!flipX && !flipY) return cf.boxes;
    return cf.boxes.map((b) => {
      const x1 = flipX ? -(b.x1 + b.width) : b.x1;
      const y1 = flipY ? -(b.y1 + b.height) : b.y1;
      return {
        ...b,
        x1,
        y1,
        x2: x1 + b.width,
        y2: y1 + b.height,
      };
    });
  }

  /**
   * Exact port of `a.a(int var1, int var2, int var3)` (`a.java` lines 917-932).
   * Note that in `a.java`, calling `a.a(slot, bankId, animIdx)` ALWAYS resets the slot (`M[+2..+7] = 0`).
   */
  public setAnimation(animIndex: number, _forceRestart: boolean = true): void {
    const bank = this.getBank();
    if (!bank || bank.animations.length === 0) return;
    const clamped = ((animIndex % bank.animations.length) + bank.animations.length) % bank.animations.length;

    this.animIndex = clamped;
    this.keyframeIndex = 0;
    this.renderKeyframeIndex = 0;
    this.elapsedInFrameMs = 0;
    this.prevCumDX = 0;
    this.prevCumDY = 0;
    this.stepA = 0;
    this.stepB = 0;
    this.lastStepDX = 0;
    this.lastStepDY = 0;

    const seq = bank.animations[clamped];
    this.remainingLoops = seq.loop_count;
    this.finished = this.remainingLoops === 0;
    this.rebuildCompositeParts();
  }

  public setPosition(x: number, y: number): void {
    this.container.setPosition(Math.round(x), Math.round(y));
  }

  public setFlip(flipX: boolean, flipY: boolean = false): void {
    const nextFlags = (flipX ? 2 : 0) | (flipY ? 1 : 0);
    if (nextFlags !== this.entityFlipFlags) {
      this.entityFlipFlags = nextFlags;
      this.rebuildCompositeParts();
    }
  }

  /**
   * Exact port of `a.b(int var1, int var2)` (`a.java` lines 941-1020).
   * Advances animation by `dtMs`, computes cumulative root-motion delta `(stepA, stepB)`,
   * updates `renderKeyframeIndex` (`var11`), and returns `true` while still playing (`var3 !== 0`).
   */
  public update(dtMs: number): boolean {
    const seq = this.getSequence();
    if (!seq || seq.keyframes.length === 0 || this.remainingLoops === 0) {
      this.stepA = 0;
      this.stepB = 0;
      this.lastStepDX = 0;
      this.lastStepDY = 0;
      this.finished = true;
      return false;
    }

    let var3 = this.remainingLoops;
    let var5 = this.keyframeIndex;
    let var2 = this.elapsedInFrameMs + Math.max(0, Math.round(dtMs));
    let var6 = this.prevCumDX;
    let var7 = this.prevCumDY;
    const var10 = seq.keyframes.length;
    let var11 = var5;
    let var13 = var5 === this.renderKeyframeIndex ? 0 : seq.keyframes[var5].priority;

    const prevRenderKf = this.renderKeyframeIndex;

    let var12 = Math.max(1, seq.keyframes[var5].duration_ms || 1);
    while (var2 >= (var12 = Math.max(1, seq.keyframes[var5].duration_ms || 1))) {
      var2 -= var12;
      ++var5;
      if (var5 === var10) {
        if (var3 > 0) {
          --var3;
          if (var3 === 0) {
            --var5;
            var2 = var12;
            break;
          }
        }

        var5 = 0;
        const lastKf = seq.keyframes[var10 - 1];
        var6 -= lastKf.root_motion_dx;
        var7 -= lastKf.root_motion_dy;
      }

      const var14 = seq.keyframes[var5].priority;
      if (var14 >= var13) {
        var13 = var14;
        var11 = var5;
      }
    }

    const curKf = seq.keyframes[var5];
    let var26 = curKf.root_motion_dx;
    let var15 = curKf.root_motion_dy;

    if (curKf.interp_mode > 0 && var3 !== 0 && (var5 + 1 !== var10 || var3 !== 1)) {
      let var16 = 0;
      let var17 = 0;
      let nextIdx = var5 + 1;
      if (var5 + 1 === var10) {
        var16 = curKf.root_motion_dx;
        var17 = curKf.root_motion_dy;
        nextIdx = 0;
      }

      const nextKf = seq.keyframes[nextIdx];
      var16 += nextKf.root_motion_dx;
      var17 += nextKf.root_motion_dy;
      const var18 = Math.trunc((var2 << 12) / var12);
      var26 += ((var16 - var26) * var18) >> 12;
      var15 += ((var17 - var15) * var18) >> 12;
    }

    this.stepA = var26 - var6;
    this.stepB = var15 - var7;
    const dirSign = (this.entityFlipFlags & 2) !== 0 ? -1 : 1;
    this.lastStepDX = this.stepA * dirSign;
    this.lastStepDY = this.stepB;

    this.keyframeIndex = var5;
    this.remainingLoops = var3;
    this.elapsedInFrameMs = var2;
    this.prevCumDX = var26;
    this.prevCumDY = var15;
    this.renderKeyframeIndex = var11;
    this.finished = var3 === 0;

    if (this.renderKeyframeIndex !== prevRenderKf) {
      this.rebuildCompositeParts();
    }

    return var3 !== 0;
  }

  /**
   * Exact port of `a.a(c[] var1, int var2, int var3, int var4, int var5, int[] var6)` (`a.java` lines 1022-1056).
   * Computes the bounding box `[x, y, width, height]` of the current composite frame.
   */
  public getFrameBounds(
    originX: number = 0,
    originY: number = 0,
    flipFlags: number = this.entityFlipFlags
  ): { x: number; y: number; width: number; height: number } {
    const cf = this.getCurrentCompositeFrame();
    const sliceTable = this.getSliceTable();
    if (!cf || !sliceTable || cf.parts.length === 0) {
      return { x: originX, y: originY, width: 32, height: 38 };
    }

    let minX = 0x7fffffff;
    let minY = 0x7fffffff;
    let maxX = -0x80000000;
    let maxY = -0x80000000;

    for (const part of cf.parts) {
      const slice = sliceTable.slices[part.slice_index];
      if (!slice) continue;
      const combinedFlip = (part.flip_flags & 3) ^ flipFlags;
      let px = slice.a + part.offset_x;
      if ((combinedFlip & 2) !== 0) {
        px = -(px + slice.w);
      }
      let py = slice.b + part.offset_y;
      if ((combinedFlip & 1) !== 0) {
        py = -(py + slice.h);
      }
      minX = Math.min(minX, px);
      minY = Math.min(minY, py);
      maxX = Math.max(maxX, px + slice.w);
      maxY = Math.max(maxY, py + slice.h);
    }

    if (minX > maxX || minY > maxY) {
      return { x: originX, y: originY, width: 32, height: 38 };
    }

    return {
      x: originX + minX,
      y: originY + minY,
      width: maxX - minX,
      height: maxY - minY,
    };
  }

  /**
   * Rebuilds the visible composite parts (`G[]` / `H[]`) for the current keyframe
   * using exact `a.java` lines 1089-1097 (`a.b(Graphics, c[], int, int, int, int)`):
   * temporarily adds `(part.offset_x, part.offset_y)` to `(slice.a, slice.b)` and draws with
   * `combinedFlip = (part.flip_flags & 3) ^ entityFlipFlags`.
   */
  public rebuildCompositeParts(): void {
    for (const img of this.partPool) {
      img.setVisible(false);
    }

    const cf = this.getCurrentCompositeFrame();
    const sliceTable = this.getSliceTable();
    if (!cf || !sliceTable) return;

    const registry = AssetRegistry.get();
    const texKey = registry.getTextureKey(sliceTable.image_res_id, this.paletteVariant);

    let poolIdx = 0;
    for (const part of cf.parts) {
      if (part.slice_index < 0 || part.slice_index >= sliceTable.slices.length) continue;
      const slice = sliceTable.slices[part.slice_index];
      if (slice.w <= 0 || slice.h <= 0) continue;

      const combinedFlip = (part.flip_flags & 3) ^ this.entityFlipFlags;
      const flipX = (combinedFlip & 2) !== 0;
      const flipY = (combinedFlip & 1) !== 0;

      const sumA = slice.a + part.offset_x;
      const sumB = slice.b + part.offset_y;
      const drawX = flipX ? -(sumA + slice.w) : sumA;
      const drawY = flipY ? -(sumB + slice.h) : sumB;
      const frameName = registry.getSliceFrameName(this.sliceTableId, part.slice_index);

      let img: Phaser.GameObjects.Image;
      if (poolIdx < this.partPool.length) {
        img = this.partPool[poolIdx];
        img.setTexture(texKey, frameName);
        img.setPosition(drawX, drawY);
      } else {
        img = this.scene.add.image(drawX, drawY, texKey, frameName);
        img.setOrigin(0, 0);
        this.partPool.push(img);
        this.container.add(img);
      }

      img.setFlip(flipX, flipY);
      img.setVisible(true);
      poolIdx++;
    }
  }

  public destroy(): void {
    this.container.destroy(true);
    this.partPool = [];
  }
}
