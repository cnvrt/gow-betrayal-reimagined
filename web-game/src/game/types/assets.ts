export interface ManifestPackInfo {
  pack_id: number;
  pack_name: string;
  file_size: number;
  header_flags: number;
  size_width_bytes: number;
  entry_count: number;
  payload_size: number;
}

export interface ManifestResourceEntry {
  res_id: number | null;
  res_id_hex: string;
  pack: string;
  pack_id?: number;
  entry_index: number | null;
  type_u8: number;
  type_i8?: number;
  type_name: string;
  original_size: number;
  raw_path: string;
  extracted_paths: string[];
  status: string;
  notes: string;
}

export interface AssetManifest {
  game: string;
  total_rp_containers: number;
  total_rp_entries: number;
  standalone_root_assets: number;
  total_manifest_items: number;
  total_uncompressed_rp_payload_bytes: number;
  packs: ManifestPackInfo[];
  resources: ManifestResourceEntry[];
}

export interface SpriteSlice {
  index: number;
  a: number;  // Anchor offset X
  b: number;  // Anchor offset Y
  u: number;  // Atlas source X
  v: number;  // Atlas source Y
  w: number;  // Slice width
  h: number;  // Slice height
  e: number;  // Trim / collision offset X
  f: number;  // Trim / collision offset Y
  w2: number; // Original width
  h2: number; // Original height
}

export interface SliceTable {
  res_id: number;
  image_res_id: number;
  version: number;
  flags_u16: string;
  num_slices: number;
  is_bitmap_font: boolean;
  bytes_consumed: number;
  total_bytes: number;
  status?: string;
  slices: SpriteSlice[];
}

export interface FontGlyph {
  glyph_index: number;
  codepoint: number;
  codepoint_hex: string;
  char: string;
  u: number;
  v: number;
  w: number;
  h: number;
  offset_x: number;
  offset_y: number;
  advance_width: number;
}

export interface BitmapFontTable {
  res_id: number;
  image_res_id: number;
  version: number;
  flags_u16: string;
  num_slices: number;
  is_bitmap_font: true;
  font_metrics: {
    line_height: number;
    baseline: number;
    space_width: number;
    kerning_offset: number;
    glyph_count: number;
  };
  glyphs: FontGlyph[];
}

export interface CompositePart {
  slice_index: number;
  offset_x: number;
  offset_y: number;
  flip_flags: number;
  flip_x: boolean;
  flip_y: boolean;
}

export interface AnimHitbox {
  x1: number;
  y1: number;
  x2: number;
  y2: number;
  width: number;
  height: number;
  flags: number;
  flags_hex: string;
}

export interface CompositeFrame {
  comp_frame_index: number;
  part_count: number;
  box_count: number;
  parts: CompositePart[];
  boxes: AnimHitbox[];
}

export interface AnimKeyframe {
  seq_frame_index: number;
  comp_frame_index: number;
  duration_index: number;
  duration_ms: number;
  priority: number;
  root_motion_dx: number;
  root_motion_dy: number;
  interp_mode: number;
}

export interface AnimSequence {
  anim_index: number;
  keyframe_count: number;
  loop_count: number;
  infinite_loop: boolean;
  keyframes: AnimKeyframe[];
}

export interface AnimBank {
  res_id: number;
  version: number;
  num_animations: number;
  total_seq_frames: number;
  num_composite_frames: number;
  total_parts: number;
  total_boxes: number;
  durations_ms: number[];
  bytes_consumed: number;
  total_bytes: number;
  animations: AnimSequence[];
  composite_frames: CompositeFrame[];
}

export interface LevelObjectGroup {
  group_type: number;
  instance_count: number;
  instances: Record<string, any>[];
}

export interface LevelData {
  res_id: number;
  slug: string;
  title: string;
  chapter_index: number;
  palette_variant: number;
  header: {
    hdr_bit0: number;
    hdr_version: number;
    intro_string_id: number;
    hy_flag: boolean;
    dE_enemy_set_a: boolean;
    dF_enemy_set_b: boolean;
    hdr_reserved: number;
    max_red_orbs: number;
    sep_bit: number;
    collision_header_u8: number;
  };
  dimensions: {
    width_tiles: number;
    height_tiles: number;
    width_px: number;
    height_px: number;
  };
  player_spawn: { x: number; y: number };
  unique_tiles_count: number;
  tile_palette_map: number[];
  unique_tile_solid_flags: number[];
  visual_tile_grid: number[][];
  object_group_count: number;
  object_groups: LevelObjectGroup[];
  bytes_consumed: number;
  total_bytes: number;
}

export interface PaletteColor {
  index: number;
  rgb: [number, number, number];
  hex: string;
}

export interface PaletteResource {
  res_id: number;
  format: string;
  status: string;
  target_png_id: number | null;
  num_colors: number;
  crc32_valid: boolean | null;
  colors: PaletteColor[];
}

export interface LocalizedStringEntry {
  index: number;
  raw: string;
  clean: string;
  control_type: string;
  speaker: string | null;
}

export interface LocalizationTable {
  res_id: number;
  language_code: string;
  language_name: string;
  string_count: number;
  strings: LocalizedStringEntry[];
}

export interface MenuStateEntry {
  state_index: number;
  raw_u32_hex: string;
  action: number;
  flags_hex: string;
  target_state: number;
  string_id: number;
  label_en: string;
}

export interface MenuStateTable {
  res_id: number;
  entry_count: number;
  entries: MenuStateEntry[];
}

export interface DebugTelemetry {
  activeScene: string;
  levelResId: number;
  levelTitle: string;
  chapterIndex: number;
  paletteVariant: number;
  levelSizePx: { w: number; h: number };
  levelSizeTiles: { w: number; h: number };
  cameraPos: { x: number; y: number };
  kratosPos: { x: number; y: number };
  kratosAnimBankId: number;
  kratosSliceTableId: number;
  kratosPngId: number;
  kratosAnimIndex: number;
  kratosTotalAnims: number;
  kratosKeyframeIndex: number;
  kratosCompFrameIndex: number;
  kratosPartCount: number;
  kratosBoxCount: number;
  kratosRootMotion: { dx: number; dy: number };
  kratosStateId?: number;
  kratosStateName?: string;
  kratosTilePos?: { tx: number; ty: number };
  kratosSubTile?: { hl: number; hm: number };
  kratosFlags?: string;
  activeInput?: string;
  loadedTexturesCount: number;
  loadedAnimBanksCount: number;
  loadedSliceTablesCount: number;
  loadedFontsCount: number;
  loadedLevelsCount: number;
  renderedEntitiesCount: number;
  renderedStampsCount: number;
  fps: number;
  showHitboxes: boolean;
  showCollisionGrid: boolean;
  showHud: boolean;
  showDebugOverlay: boolean;
}
