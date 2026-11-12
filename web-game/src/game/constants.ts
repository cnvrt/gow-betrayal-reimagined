export const NATIVE_WIDTH = 176;
export const NATIVE_HEIGHT = 208;
export const TILE_SIZE = 16;

// Clamped delta-time bounds from e.java (40ms..100ms -> 10..25 FPS tick step)
export const MIN_TICK_MS = 40;
export const MAX_TICK_MS = 100;

// Level resource IDs (e.bJ + Arena 9216)
export const LEVEL_METADATA: {
  resId: number;
  slug: string;
  title: string;
  chapterIndex: number;
  paletteVariant: number;
}[] = [
  { resId: 9217, slug: "ch01_for_sparta", title: "Ch.1: For Sparta", chapterIndex: 0, paletteVariant: -1 },
  { resId: 19456, slug: "ch02_dead_rider", title: "Ch.2: Dead Rider", chapterIndex: 1, paletteVariant: -1 },
  { resId: 20480, slug: "ch03_minotaur", title: "Ch.3: Minotaur", chapterIndex: 2, paletteVariant: -1 },
  { resId: 21504, slug: "ch04_garden", title: "Ch.4: Garden", chapterIndex: 3, paletteVariant: 2 },
  { resId: 22528, slug: "ch05_the_beast", title: "Ch.5: The Beast", chapterIndex: 4, paletteVariant: 0 },
  { resId: 23552, slug: "ch06_ruins", title: "Ch.6: Ruins", chapterIndex: 5, paletteVariant: 1 },
  { resId: 24576, slug: "ch07_catacombs", title: "Ch.7: Catacombs", chapterIndex: 6, paletteVariant: 1 },
  { resId: 25600, slug: "ch08_labyrinth", title: "Ch.8: Labyrinth", chapterIndex: 7, paletteVariant: 0 },
  { resId: 26624, slug: "ch09_the_tower", title: "Ch.9: The Tower", chapterIndex: 8, paletteVariant: 2 },
  { resId: 27648, slug: "ch10_messenger", title: "Ch.10: Messenger", chapterIndex: 9, paletteVariant: 2 },
  { resId: 9216, slug: "arena", title: "Arena Mode", chapterIndex: -1, paletteVariant: -1 },
];

// Animation Bank (type=247) -> Sprite Slice Table (type=254) pairing from e.java
export const ANIM_TO_SLICE_MAP: Record<number, number> = {
  1024: 1035,
  1025: 1036,
  1026: 1038,
  1027: 1040,
  2048: 2051,
  2049: 2052,
  2050: 2053,
  3072: 3075,
  3073: 3076,
  3074: 3077,
  4096: 4097,
  5120: 5123,
  5121: 5124,
  5122: 5125,
  6144: 6146,
  6145: 6147,
  7168: 7169,
  8192: 8193,
  13312: 13313,
  14336: 14337,
  15360: 15361,
  16384: 16385,
  17408: 17409,
  18432: 18433,
  28672: 28673,
};

// PNG resource ID -> Palette resource IDs [variant 0, variant 1, variant 2]
export const PNG_PALETTE_VARIANTS: Record<number, [number, number, number]> = {
  2055: [2059, 2060, 2061],
  2056: [2062, 2063, 2064],
  2057: [2065, 2066, 2067],
  2058: [2068, 2069, 2070],
  3078: [3081, 3082, 3083],
  3079: [3084, 3085, 3086],
  3080: [3087, 3088, 3089],
  4098: [4099, 4100, 4101],
  5126: [5129, 5130, 5131],
  5127: [5132, 5133, 5134],
  5128: [5142, 5143, 5144],
  6148: [6150, 6151, 6152],
  6149: [6153, 6154, 6155],
  13314: [13315, 13316, 13317],
  15362: [15363, 15364, 15365],
  16386: [16387, 16388, 16389],
  17410: [17411, 17412, 17413],
  18434: [18435, 18436, 18437],
  28674: [28675, 28676, 28677],
};
