import * as Phaser from "phaser";
import type { SpriteSlice } from "../types/assets";
import { AssetRegistry } from "./AssetRegistry";

export interface SlicePlacement {
  drawX: number;
  drawY: number;
  flipX: boolean;
  flipY: boolean;
  width: number;
  height: number;
}

/**
 * Exact implementation of `c.java` (`c.a(Graphics, int, int, int)` lines 35-44).
 * Computes the screen/world top-left draw coordinates and horizontal/vertical flip flags
 * for a sub-image slice (`c`).
 *
 * In `c.java`:
 * - `k = new short[]{0, 16384, 8192, 24576}` -> Bit 2 (`& 2`) is Horizontal Flip (`8192`), Bit 1 (`& 1`) is Vertical Flip (`16384`).
 * - `var2 += (var4 & 2) == 0 ? this.a : -(this.a + this.c);`
 * - `var3 += (var4 & 1) == 0 ? this.b : -(this.b + this.d);`
 */
export class SliceLoader {
  public static computePlacement(
    slice: SpriteSlice,
    x: number,
    y: number,
    flipFlags: number
  ): SlicePlacement {
    const flipX = (flipFlags & 2) !== 0;
    const flipY = (flipFlags & 1) !== 0;

    const drawX = flipX ? x - (slice.a + slice.w) : x + slice.a;
    const drawY = flipY ? y - (slice.b + slice.h) : y + slice.b;

    return {
      drawX: Math.round(drawX),
      drawY: Math.round(drawY),
      flipX,
      flipY,
      width: slice.w,
      height: slice.h,
    };
  }

  /**
   * Creates or configures a Phaser Image for a single slice at `(x, y)` with `flipFlags`.
   */
  public static createSliceImage(
    scene: Phaser.Scene,
    sliceTableId: number,
    sliceIndex: number,
    x: number,
    y: number,
    flipFlags: number = 0,
    paletteVariant: number = -1
  ): Phaser.GameObjects.Image | null {
    const registry = AssetRegistry.get();
    const table = registry.slices.get(sliceTableId);
    if (!table || sliceIndex < 0 || sliceIndex >= table.slices.length) return null;

    const slice = table.slices[sliceIndex];
    if (slice.w <= 0 || slice.h <= 0) return null;

    const texKey = registry.getTextureKey(table.image_res_id, paletteVariant);
    const frameName = registry.getSliceFrameName(sliceTableId, sliceIndex);
    const placement = this.computePlacement(slice, x, y, flipFlags);

    const img = scene.add.image(placement.drawX, placement.drawY, texKey, frameName);
    img.setOrigin(0, 0);
    img.setFlip(placement.flipX, placement.flipY);
    return img;
  }
}
