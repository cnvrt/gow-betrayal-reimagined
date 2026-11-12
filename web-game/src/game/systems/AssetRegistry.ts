import * as Phaser from "phaser";
import { LEVEL_METADATA, PNG_PALETTE_VARIANTS } from "../constants";
import type {
  AnimBank,
  AssetManifest,
  BitmapFontTable,
  LevelData,
  LocalizationTable,
  MenuStateTable,
  PaletteResource,
  SliceTable,
} from "../types/assets";

/**
 * Singleton registry that loads and indexes all original God of War: Betrayal
 * assets using `/assets/asset_manifest.json` from Phase 2.
 */
export class AssetRegistry {
  private static instance: AssetRegistry;

  public manifest!: AssetManifest;
  public slices: Map<number, SliceTable> = new Map();
  public fonts: Map<number, BitmapFontTable> = new Map();
  public animBanks: Map<number, AnimBank> = new Map();
  public levels: Map<number, LevelData> = new Map();
  public strings: Map<number, LocalizationTable> = new Map();
  public firePalette!: PaletteResource;
  public menuTable!: MenuStateTable;
  public loadedTextureKeys: Set<string> = new Set();

  public static get(): AssetRegistry {
    if (!AssetRegistry.instance) {
      AssetRegistry.instance = new AssetRegistry();
    }
    return AssetRegistry.instance;
  }

  /**
   * Resolves the Phaser texture key for a PNG resource ID and optional chapter palette variant.
   * Matches `a.c(int, byte[])` and `e.bK[chapter]`:
   *   - paletteVariant === -1 (or PNG has no palette swaps): returns `png_<pngId>_default`
   *   - paletteVariant in {0, 1, 2}: returns `png_<pngId>_pal<variant>`
   */
  public getTextureKey(pngResId: number, paletteVariant: number = -1): string {
    if (paletteVariant >= 0 && PNG_PALETTE_VARIANTS[pngResId]) {
      return `png_${pngResId}_pal${paletteVariant}`;
    }
    return `png_${pngResId}_default`;
  }

  /**
   * Returns the frame name registered on a parent PNG texture for a given slice table and slice index.
   */
  public getSliceFrameName(sliceTableId: number, sliceIndex: number): string {
    return `slice_${sliceTableId}_${sliceIndex}`;
  }

  /**
   * Returns the frame name for a 16x16 tile on master tileset PNG 2058 (tileIndex 0..127).
   */
  public getTileFrameName(tileIndex: number): string {
    return `tile_2058_${tileIndex}`;
  }

  /**
   * Queues all Phase 2 extracted assets into Phaser's LoaderPlugin using the manifest.
   */
  public queueManifestAssets(scene: Phaser.Scene, manifest: AssetManifest): void {
    this.manifest = manifest;

    // 1. Combined JSON indices for fast batch lookup
    scene.load.json("all_slices", "/assets/extracted/slices/all_slices.json");
    scene.load.json("all_fonts", "/assets/extracted/fonts/all_fonts.json");
    scene.load.json("pal_1053", "/assets/extracted/palettes/pal_1053.json");
    scene.load.json("menu_1028", "/assets/extracted/menu/menu_1028.json");
    scene.load.json("strings_10240", "/assets/extracted/strings/strings_10240_en.json");

    // 2. Queue every PNG, Animation Bank, and Level from the manifest
    for (const item of manifest.resources) {
      if (item.res_id === null) {
        scene.load.image("icon_root", `/assets/${item.extracted_paths[0]}`);
        continue;
      }

      if (item.type_u8 === 253) {
        // First path is always default PNG
        const defaultPath = `/assets/${item.extracted_paths[0]}`;
        const defaultKey = `png_${item.res_id}_default`;
        scene.load.image(defaultKey, defaultPath);
        this.loadedTextureKeys.add(defaultKey);

        // Additional paths are palette-swapped PNGs (pal0, pal1, pal2)
        for (let vIdx = 0; vIdx < item.extracted_paths.length - 1; vIdx++) {
          const palPath = `/assets/${item.extracted_paths[vIdx + 1]}`;
          const palKey = `png_${item.res_id}_pal${vIdx}`;
          scene.load.image(palKey, palPath);
          this.loadedTextureKeys.add(palKey);
        }
      } else if (item.type_u8 === 247) {
        scene.load.json(`anim_${item.res_id}`, `/assets/${item.extracted_paths[0]}`);
      } else if (item.type_u8 === 245) {
        scene.load.json(`level_${item.res_id}`, `/assets/${item.extracted_paths[0]}`);
      }
    }
  }

  /**
   * Populates runtime maps and registers all sub-image slice frames and 16x16 tile frames
   * onto Phaser's TextureManager after loading completes.
   */
  public initializeFromCache(scene: Phaser.Scene): void {
    const rawSlices = scene.cache.json.get("all_slices") as Record<string, SliceTable>;
    for (const [k, table] of Object.entries(rawSlices)) {
      this.slices.set(Number(k), table);
    }

    const rawFonts = scene.cache.json.get("all_fonts") as Record<string, BitmapFontTable>;
    for (const [k, font] of Object.entries(rawFonts)) {
      this.fonts.set(Number(k), font);
    }

    this.firePalette = scene.cache.json.get("pal_1053") as PaletteResource;
    this.menuTable = scene.cache.json.get("menu_1028") as MenuStateTable;
    this.strings.set(10240, scene.cache.json.get("strings_10240") as LocalizationTable);

    for (const item of this.manifest.resources) {
      if (item.res_id === null) continue;
      if (item.type_u8 === 247) {
        const bank = scene.cache.json.get(`anim_${item.res_id}`) as AnimBank;
        if (bank) this.animBanks.set(item.res_id, bank);
      } else if (item.type_u8 === 245) {
        const lvl = scene.cache.json.get(`level_${item.res_id}`) as LevelData;
        if (lvl) this.levels.set(item.res_id, lvl);
      }
    }

    // Register sub-image slice frames (`c.java`) onto every loaded texture and its palette variants
    for (const [sliceTableId, table] of this.slices.entries()) {
      if (sliceTableId === 2054) continue; // 2054 is unused 22x22 slice table; 2058 uses 16x16 tiles
      const pngId = table.image_res_id;
      const textureKeys = [this.getTextureKey(pngId, -1)];
      if (PNG_PALETTE_VARIANTS[pngId]) {
        textureKeys.push(
          this.getTextureKey(pngId, 0),
          this.getTextureKey(pngId, 1),
          this.getTextureKey(pngId, 2)
        );
      }

      for (const texKey of textureKeys) {
        if (!scene.textures.exists(texKey)) continue;
        const tex = scene.textures.get(texKey);
        tex.setFilter(Phaser.Textures.FilterMode.NEAREST);
        for (const sl of table.slices) {
          const frameName = this.getSliceFrameName(sliceTableId, sl.index);
          if (!tex.has(frameName) && sl.w > 0 && sl.h > 0) {
            tex.add(frameName, 0, sl.u, sl.v, sl.w, sl.h);
          }
        }
      }
    }

    // Register bitmap font glyph frames (`1031`, `1032`, `1033`)
    for (const [fontId, font] of this.fonts.entries()) {
      const texKey = this.getTextureKey(font.image_res_id, -1);
      if (!scene.textures.exists(texKey)) continue;
      const tex = scene.textures.get(texKey);
      tex.setFilter(Phaser.Textures.FilterMode.NEAREST);
      for (const gl of font.glyphs) {
        const frameName = `glyph_${fontId}_${gl.codepoint}`;
        if (!tex.has(frameName) && gl.w > 0 && gl.h > 0) {
          tex.add(frameName, 0, gl.u, gl.v, gl.w, gl.h);
        }
      }
    }

    // Register 128 16x16 tiles on Master Tileset 2058 (8 columns x 16 rows on 128x256 PNG)
    const tilesetKeys = [
      this.getTextureKey(2058, -1),
      this.getTextureKey(2058, 0),
      this.getTextureKey(2058, 1),
      this.getTextureKey(2058, 2),
    ];
    for (const texKey of tilesetKeys) {
      if (!scene.textures.exists(texKey)) continue;
      const tex = scene.textures.get(texKey);
      tex.setFilter(Phaser.Textures.FilterMode.NEAREST);
      for (let tileIdx = 0; tileIdx < 128; tileIdx++) {
        const col = tileIdx % 8;
        const row = Math.floor(tileIdx / 8);
        const frameName = this.getTileFrameName(tileIdx);
        if (!tex.has(frameName)) {
          tex.add(frameName, 0, col * 16, row * 16, 16, 16);
        }
      }
    }
  }

  public getEnglishString(strId: number): string {
    const table = this.strings.get(10240);
    if (!table || strId < 0 || strId >= table.strings.length) return "";
    return table.strings[strId].clean || table.strings[strId].raw;
  }

  public getLevelList() {
    return LEVEL_METADATA;
  }
}
