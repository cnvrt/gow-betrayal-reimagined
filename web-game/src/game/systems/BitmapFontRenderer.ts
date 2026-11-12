import * as Phaser from "phaser";
import type { BitmapFontTable, FontGlyph } from "../types/assets";
import { AssetRegistry } from "./AssetRegistry";

/**
 * Exact port of `a.java` bitmap font layout and binary-searched UTF-16 glyph rendering
 * for original fonts `1031` (Primary UI/Dialogue), `1032` (Menu Title), and `1033` (HUD Numbers).
 */
export class BitmapFontRenderer {
  private static findGlyph(font: BitmapFontTable, codepoint: number): FontGlyph | null {
    const glyphs = font.glyphs;
    let low = 0;
    let high = glyphs.length - 1;
    while (low <= high) {
      const mid = (low + high) >>> 1;
      const cp = glyphs[mid].codepoint;
      if (cp === codepoint) return glyphs[mid];
      if (cp < codepoint) low = mid + 1;
      else high = mid - 1;
    }
    return null;
  }

  public static measureTextWidth(fontId: number, text: string): number {
    const font = AssetRegistry.get().fonts.get(fontId);
    if (!font) return 0;
    const { space_width } = font.font_metrics;
    let width = 0;
    for (let i = 0; i < text.length; i++) {
      const cp = text.charCodeAt(i);
      if (cp === 32) {
        width += space_width;
        continue;
      }
      const glyph = this.findGlyph(font, cp);
      if (glyph) {
        width += glyph.advance_width;
      }
    }
    return width;
  }

  /**
   * Renders a string using the original bitmap font (`1031`, `1032`, or `1033`) into a Phaser Container.
   */
  public static renderText(
    scene: Phaser.Scene,
    fontId: number,
    text: string,
    x: number,
    y: number,
    align: "left" | "center" | "right" = "left",
    container?: Phaser.GameObjects.Container
  ): Phaser.GameObjects.Container {
    const target = container ?? scene.add.container(0, 0);
    const registry = AssetRegistry.get();
    const font = registry.fonts.get(fontId);
    if (!font) return target;

    const texKey = registry.getTextureKey(font.image_res_id, -1);
    const { space_width, baseline } = font.font_metrics;
    const totalW = this.measureTextWidth(fontId, text);

    let cursorX = Math.round(x);
    if (align === "center") cursorX = Math.round(x - totalW / 2);
    else if (align === "right") cursorX = Math.round(x - totalW);
    const baseLineY = Math.round(y);

    for (let i = 0; i < text.length; i++) {
      const cp = text.charCodeAt(i);
      if (cp === 32) {
        cursorX += space_width;
        continue;
      }
      const glyph = this.findGlyph(font, cp);
      if (!glyph) continue;

      if (glyph.w > 0 && glyph.h > 0) {
        const drawX = cursorX + glyph.offset_x;
        const drawY = baseLineY - baseline + glyph.offset_y;
        const img = scene.add.image(drawX, drawY, texKey, `glyph_${fontId}_${glyph.codepoint}`);
        img.setOrigin(0, 0);
        target.add(img);
      }
      cursorX += glyph.advance_width;
    }

    return target;
  }
}
