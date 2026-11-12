#!/usr/bin/env python3
"""
Deterministic Phase 2 Asset Extraction & Decoding Pipeline
for God of War: Betrayal (2007 J2ME)

Strictly read-only on original files (RP1..RP33, i.png, *.java).
Implements the exact byte-level and bit-level decoders documented in
documentation/RESOURCE_FORMAT.md.
"""

import json
import os
import shutil
import struct
import wave
import zlib
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple


WORKSPACE_ROOT = Path(__file__).resolve().parent.parent.parent
ANALYSIS_ROOT = WORKSPACE_ROOT / "resource-analysis"
EXTRACTED_ROOT = ANALYSIS_ROOT / "extracted"


TYPE_NAMES = {
    0: "MIDI_AUDIO",
    1: "WAV_AUDIO",
    245: "LEVEL_BITSTREAM",
    247: "ANIMATION_BANK",
    250: "UTF8_STRING_TABLE",
    251: "PALETTE_DATA",
    253: "INDEXED_PNG",
    254: "SPRITE_SLICE_OR_FONT_TABLE",
    255: "MENU_STATE_TABLE",
}

LANGUAGE_META = {
    10240: ("en", "English"),
    12288: ("fr", "French"),
    29696: ("de", "German"),
    30720: ("it", "Italian"),
    32768: ("pt", "Portuguese"),
    33792: ("es", "Spanish"),
}

LEVEL_META = {
    9216: ("arena", "Arena", -1),
    9217: ("ch01_for_sparta", "Chapter 1: For Sparta", 0),
    19456: ("ch02_dead_rider", "Chapter 2: Dead Rider", 1),
    20480: ("ch03_minotaur", "Chapter 3: Minotaur", 2),
    21504: ("ch04_garden", "Chapter 4: Garden", 3),
    22528: ("ch05_the_beast", "Chapter 5: The Beast", 4),
    23552: ("ch06_ruins", "Chapter 6: Ruins", 5),
    24576: ("ch07_catacombs", "Chapter 7: Catacombs", 6),
    25600: ("ch08_labyrinth", "Chapter 8: Labyrinth", 7),
    26624: ("ch09_the_tower", "Chapter 9: The Tower", 8),
    27648: ("ch10_messenger", "Chapter 10: Messenger", 9),
}

# Chapter palette selector table e.bK = {-1, -1, -1, 2, 0, 1, 1, 0, 2, 2}
CHAPTER_PALETTE_SELECTOR = [-1, -1, -1, 2, 0, 1, 1, 0, 2, 2]

# Active PNG -> base palette ID mappings from e.java
PNG_PALETTE_MAPPING = {
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
}

AUDIO_META = {
    1054: ("sfx_01_blade_slash_1", "RECOVERABLE_UNUSED_SLOT"),
    1055: ("sfx_02_blade_slash_2", "RECOVERABLE_UNUSED_SLOT"),
    1056: ("sfx_03_heavy_impact", "RECOVERABLE_UNUSED_SLOT"),
    1057: ("sfx_04_flesh_hit", "RECOVERABLE_UNUSED_SLOT"),
    1058: ("sfx_05_damage_grunt", "RECOVERABLE_UNUSED_SLOT"),
    1059: ("sfx_06_magic_orb_pickup", "RECOVERABLE_UNUSED_SLOT"),
    1060: ("sfx_07_menu_cursor_tick", "RECOVERABLE_UNUSED_SLOT"),
    1061: ("sfx_08_menu_confirm_block", "RECOVERABLE_UNUSED_SLOT"),
    1062: ("sfx_09_qte_finisher_fanfare", "ACTIVE_SLOT_2"),
    1063: ("music_00_title_theme", "ACTIVE_SLOT_0"),
    1064: ("sfx_10_level_intro_horn", "ACTIVE_SLOT_1"),
}

UI_PNG_IDS = {
    1041: "ui_menu_border",
    1042: "ui_frame_ornament",
    1043: "font_0_primary_ui",
    1044: "font_1_menu_title",
    1045: "font_2_hud_numbers",
    1046: "hud_qte_arrows",
    1047: "hud_portraits_and_weapon_icons",
    1048: "ui_main_menu_logo",
    1049: "hud_blade_emblem",
    1050: "ui_powerup_bars_and_orbs",
    1051: "ui_stone_plaque_banner",
    1052: "hud_animated_prompts",
    8194: "ui_splash_logos",
}

SPRITESHEET_PNG_IDS = {
    2055: "props_platforms_and_beams",
    2056: "props_doors_gates_bridges",
    2057: "props_levers_and_ropes",
    2058: "tileset_master_16x16",
    3078: "props_interactive_objects",
    3079: "stamps_environment_bg_fg",
    3080: "props_torches_and_hazards",
    4098: "char_spartan_and_basic_soldier",
    5126: "vfx_chests_orbs_savebeam",
    5127: "vfx_blade_trails_and_flames",
    5128: "vfx_magic_medusa_and_hades",
    6148: "props_pushable_boxes_statues",
    6149: "traps_pendulums_and_undead",
    7170: "vfx_blood_and_debris_particles",
    13314: "boss_argos_giant",
    14338: "boss_dead_rider_assassin",
    15362: "enemy_minotaur",
    16386: "enemy_gorgon_beast",
    17410: "boss_cerberus",
    18434: "boss_ceryx_and_legionnaire",
    28674: "char_kratos_player",
}


# ---------------------------------------------------------------------------
# 1. RP Pack Container Reader (a.a(DataInputStream))
# ---------------------------------------------------------------------------
def parse_rp_container(filepath: Path, pack_id: int) -> Dict[str, Any]:
    data = filepath.read_bytes()
    pos = 0
    header_flags = data[pos]
    pos += 1
    size_width = (1 + (header_flags & 1)) << 1
    entry_count = struct.unpack(">H", data[pos : pos + 2])[0]
    pos += 2

    sizes: List[int] = []
    for _ in range(entry_count):
        sz = 0
        for _ in range(size_width):
            sz = (sz << 8) | data[pos]
            pos += 1
        sizes.append(sz)

    types: List[int] = list(data[pos : pos + entry_count])
    pos += entry_count

    payload_size = struct.unpack(">I", data[pos : pos + 4])[0]
    pos += 4
    header_size = pos
    payload = data[pos : pos + payload_size]

    if len(payload) != payload_size or pos + payload_size != len(data):
        raise ValueError(
            f"RP{pack_id} size mismatch: expected {pos + payload_size}, got {len(data)}"
        )
    if sum(sizes) != payload_size:
        raise ValueError(
            f"RP{pack_id} sum(sizes)={sum(sizes)} != payload_size={payload_size}"
        )

    entries = []
    offset = 0
    for idx in range(entry_count):
        res_id = (pack_id << 10) | idx
        sz = sizes[idx]
        t_u8 = types[idx]
        t_i8 = t_u8 - 256 if t_u8 >= 128 else t_u8
        blob = payload[offset : offset + sz]
        offset += sz
        entries.append(
            {
                "res_id": res_id,
                "res_id_hex": f"0x{res_id:04X}",
                "pack_id": pack_id,
                "entry_index": idx,
                "type_u8": t_u8,
                "type_i8": t_i8,
                "type_name": TYPE_NAMES.get(t_u8, f"UNKNOWN_{t_u8}"),
                "size": sz,
                "offset_in_payload": offset - sz,
                "data": blob,
            }
        )

    return {
        "pack_id": pack_id,
        "pack_name": f"RP{pack_id}",
        "file_size": len(data),
        "header_flags": header_flags,
        "size_width_bytes": size_width,
        "entry_count": entry_count,
        "header_size": header_size,
        "payload_size": payload_size,
        "entries": entries,
    }


# ---------------------------------------------------------------------------
# 2. PNG Parser & Palette Swapper (a.c(int, byte[]) & a.b(DataInputStream))
# ---------------------------------------------------------------------------
def inspect_png(data: bytes) -> Dict[str, Any]:
    if data[:8] != b"\x89PNG\r\n\x1a\n":
        raise ValueError("Invalid PNG signature")
    pos = 8
    chunks = []
    width = height = bit_depth = color_type = 0
    plte_offset = -1
    plte_length = 0
    plte_colors = []
    trns_data = b""
    idat_compressed = bytearray()

    while pos < len(data):
        length, ctype_bytes = struct.unpack(">I4s", data[pos : pos + 8])
        ctype = ctype_bytes.decode("ascii", errors="replace")
        chunk_data = data[pos + 8 : pos + 8 + length]
        stored_crc = struct.unpack(">I", data[pos + 8 + length : pos + 12 + length])[0]
        calc_crc = zlib.crc32(ctype_bytes + chunk_data) & 0xFFFFFFFF
        if stored_crc != calc_crc:
            raise ValueError(
                f"PNG chunk {ctype} CRC mismatch: stored=0x{stored_crc:08X} calc=0x{calc_crc:08X}"
            )
        chunks.append({"type": ctype, "length": length, "crc": f"0x{stored_crc:08X}"})

        if ctype == "IHDR":
            width, height, bit_depth, color_type, comp, filt, interlace = struct.unpack(
                ">IIBBBBB", chunk_data
            )
        elif ctype == "PLTE":
            plte_offset = pos + 8
            plte_length = length
            for i in range(0, length, 3):
                r, g, b = chunk_data[i : i + 3]
                plte_colors.append(f"#{r:02X}{g:02X}{b:02X}")
        elif ctype == "tRNS":
            trns_data = chunk_data
        elif ctype == "IDAT":
            idat_compressed.extend(chunk_data)

        pos += 12 + length

    raw_scanlines = zlib.decompress(bytes(idat_compressed))
    return {
        "width": width,
        "height": height,
        "bit_depth": bit_depth,
        "color_type": color_type,
        "plte_offset": plte_offset,
        "plte_length": plte_length,
        "num_palette_entries": len(plte_colors),
        "palette": plte_colors,
        "has_trns": len(trns_data) > 0,
        "trns_hex": trns_data.hex(),
        "decompressed_idat_bytes": len(raw_scanlines),
        "chunks": chunks,
    }


def swap_png_palette(png_bytes: bytes, pal_bytes: bytes) -> bytes:
    """Exact implementation of a.c(int, byte[]) & a.b(DataInputStream)."""
    info = inspect_png(png_bytes)
    plte_offset = info["plte_offset"]
    plte_length = info["plte_length"]
    if plte_offset < 0:
        raise ValueError("PNG has no PLTE chunk")
    if len(pal_bytes) != plte_length + 4:
        raise ValueError(
            f"Palette swap size ({len(pal_bytes)}) != PLTE length + 4 ({plte_length + 4})"
        )
    out = bytearray(png_bytes)
    out[plte_offset : plte_offset + len(pal_bytes)] = pal_bytes
    # Verify the resulting PNG has a valid CRC32
    inspect_png(bytes(out))
    return bytes(out)


# ---------------------------------------------------------------------------
# 3. Palette Decoder (type = 251)
# ---------------------------------------------------------------------------
def decode_palette(res_id: int, data: bytes, target_png_map: Dict[int, int]) -> Dict[str, Any]:
    if res_id == 1053:
        # 768-byte Fire RGB palette (256 * 3 bytes)
        colors = []
        for i in range(0, len(data), 3):
            r, g, b = data[i : i + 3]
            colors.append({"index": i // 3, "rgb": [r, g, b], "hex": f"#{r:02X}{g:02X}{b:02X}"})
        return {
            "res_id": res_id,
            "format": "RAW_RGB_FIRE_PALETTE",
            "status": "ACTIVE_FIRE_PALETTE",
            "target_png_id": None,
            "num_colors": len(colors),
            "crc32_valid": None,
            "colors": colors,
        }

    rgb_bytes = data[:-4]
    stored_crc = struct.unpack(">I", data[-4:])[0]
    calc_crc = zlib.crc32(b"PLTE" + rgb_bytes) & 0xFFFFFFFF
    crc_valid = stored_crc == calc_crc
    if len(rgb_bytes) % 3 != 0 or not crc_valid:
        raise ValueError(f"Palette {res_id} invalid PLTE+CRC structure")

    colors = []
    for i in range(0, len(rgb_bytes), 3):
        r, g, b = rgb_bytes[i : i + 3]
        colors.append({"index": i // 3, "rgb": [r, g, b], "hex": f"#{r:02X}{g:02X}{b:02X}"})

    target_png = target_png_map.get(res_id)
    status = "ACTIVE_PLTE_SWAP" if target_png is not None else "UNUSED_ORPHAN_PLTE"
    return {
        "res_id": res_id,
        "format": "PNG_PLTE_CHUNK_WITH_CRC32",
        "status": status,
        "target_png_id": target_png,
        "num_colors": len(colors),
        "stored_crc32": f"0x{stored_crc:08X}",
        "calculated_crc32": f"0x{calc_crc:08X}",
        "crc32_valid": crc_valid,
        "colors": colors,
    }


# ---------------------------------------------------------------------------
# 4. Sprite Slice & Bitmap Font Decoder (type = 254, a.b(int, byte[]) & a.a(c[]))
# ---------------------------------------------------------------------------
def decode_slice_or_font_table(res_id: int, data: bytes) -> Dict[str, Any]:
    pack_base = res_id & ~1023
    img_local_idx = struct.unpack(">H", data[0:2])[0]
    image_res_id = pack_base | img_local_idx
    version_byte = data[2]
    var4 = struct.unpack(">h", data[3:5])[0]
    var5 = struct.unpack(">H", data[5:7])[0]
    num_slices = struct.unpack(">H", data[7:9])[0]
    var7 = data[9:]

    if len(var7) != var4 - 4:
        raise ValueError(
            f"Slice table {res_id}: expected {var4 - 4} SoA bytes, got {len(var7)}"
        )

    u = [((var5 >> bit) & 1) == 0 for bit in range(16)]
    raw_slices = [
        {"a": 0, "b": 0, "u": 0, "v": 0, "w": 0, "h": 0, "e": 0, "f": 0, "w2": 0, "h2": 0}
        for _ in range(num_slices)
    ]

    pos = 0
    field_idx = 0
    field_names = ["a", "b", "u", "v", "w", "h", "e", "f", "w2", "h2"]
    signed_1byte = {0, 1, 6, 7}

    for group in range(3):
        group_len = 2 if group == 0 else 4
        if not u[group]:
            field_idx += group_len
            continue
        for _ in range(group_len):
            fname = field_names[field_idx]
            is_2byte = u[field_idx + 5]
            for s_idx in range(num_slices):
                if is_2byte:
                    val = struct.unpack(">h", var7[pos : pos + 2])[0]
                    pos += 2
                else:
                    if field_idx in signed_1byte:
                        val = struct.unpack(">b", var7[pos : pos + 1])[0]
                    else:
                        val = var7[pos] & 0xFF
                    pos += 1
                raw_slices[s_idx][fname] = val
            field_idx += 1

    if pos != len(var7):
        raise ValueError(f"Slice table {res_id}: consumed {pos}/{len(var7)} bytes")

    if not u[2]:
        for s in raw_slices:
            s["w2"] = s["w"]
            s["h2"] = s["h"]

    is_bitmap_font = num_slices >= 4 and raw_slices[3]["h2"] == 255
    result: Dict[str, Any] = {
        "res_id": res_id,
        "image_res_id": image_res_id,
        "version": version_byte,
        "flags_u16": f"0x{var5:04X}",
        "num_slices": num_slices,
        "is_bitmap_font": is_bitmap_font,
        "bytes_consumed": 9 + pos,
        "total_bytes": len(data),
    }

    if is_bitmap_font:
        # Apply exact post-processing from a.a(c[]) lines 479-493
        font_height = raw_slices[0]["h2"]
        baseline = raw_slices[1]["h2"]
        space_width = raw_slices[2]["h2"]
        kerning_offset = raw_slices[0]["e"]
        glyphs = []
        codepoint = 0
        for idx in range(0, num_slices):
            s = raw_slices[idx]
            delta = (s["a"] << 8) + (s["b"] & 0xFF)
            codepoint = (codepoint + delta) & 0xFFFF
            if codepoint >= 0x8000:
                codepoint -= 0x10000
            ch = chr(codepoint) if 0 <= codepoint <= 0x10FFFF else ""
            glyphs.append(
                {
                    "glyph_index": idx,
                    "codepoint": codepoint,
                    "codepoint_hex": f"U+{codepoint:04X}",
                    "char": ch,
                    "u": s["u"],
                    "v": s["v"],
                    "w": s["w"],
                    "h": s["h"],
                    "offset_x": s["e"],
                    "offset_y": s["f"],
                    "advance_width": s["w2"],
                }
            )
        result["font_metrics"] = {
            "line_height": font_height,
            "baseline": baseline,
            "space_width": space_width,
            "kerning_offset": kerning_offset,
            "glyph_count": len(glyphs),
        }
        result["glyphs"] = glyphs
    else:
        result["slices"] = [
            {"index": idx, **s} for idx, s in enumerate(raw_slices)
        ]

    return result


# ---------------------------------------------------------------------------
# 5. Hierarchical Animation Bank Decoder (type = 247, a.g(int))
# ---------------------------------------------------------------------------
class AnimStreamReader:
    def __init__(self, data: bytes):
        self.data = data
        self.pos = 0

    def read_u8_raw(self) -> int:
        v = self.data[self.pos] & 0xFF
        self.pos += 1
        return v

    def read_i8_raw(self) -> int:
        v = self.data[self.pos]
        self.pos += 1
        return v - 256 if v >= 128 else v

    def i(self) -> int:
        """a.i(): unsigned varint (255 -> u16 BE)."""
        b = self.read_u8_raw()
        if b == 255:
            return (self.read_u8_raw() << 8) | self.read_u8_raw()
        return b

    def j(self) -> int:
        """a.j(): signed varint (-128 -> i16 BE)."""
        b = self.read_i8_raw()
        if b == -128:
            hi = self.read_i8_raw()
            lo = self.read_u8_raw()
            return (hi << 8) | lo
        return b

    def a_counts(self, length: int) -> Tuple[List[int], int]:
        table = [0] * length
        acc = 0
        for k in range(length):
            cnt = self.i()
            table[k] = (acc << 16) | cnt
            acc += cnt
        return table, acc

    def b_pack(self, table: List[int]) -> None:
        n = len(table)
        for k in range(n):
            table[k] |= self.i() << 22
        for k in range(n):
            table[k] |= (self.j() & 1023) << 12
        for k in range(n):
            table[k] |= (self.j() & 1023) << 2


def sign_extend_10bit(val: int) -> int:
    val &= 0x3FF
    return val - 1024 if (val & 0x200) else val


def sign_extend_6bit(val: int) -> int:
    val &= 0x3F
    return val - 64 if (val & 0x20) else val


def decode_animation_bank(res_id: int, data: bytes) -> Dict[str, Any]:
    sr = AnimStreamReader(data)
    version = sr.i()
    num_anims = (sr.read_u8_raw() << 8) | sr.read_u8_raw()
    C, total_seq_frames = sr.a_counts(num_anims)
    for a_idx in range(num_anims):
        C[a_idx] |= (63 & sr.j()) << 10

    D = [0] * total_seq_frames
    E = [0] * total_seq_frames
    for f_idx in range(total_seq_frames):
        v = sr.i()
        D[f_idx] = v & 0xFF
        E[f_idx] = v >> 8
    sr.b_pack(E)

    num_comp_frames = (sr.read_u8_raw() << 8) | sr.read_u8_raw()
    G, total_parts = sr.a_counts(num_comp_frames)
    H = [0] * total_parts
    byte_val = 0
    for p_idx in range(total_parts):
        if (p_idx & 3) == 0:
            byte_val = sr.i()
        H[p_idx] = (byte_val >> ((p_idx & 3) << 1)) & 3
    sr.b_pack(H)

    I = [0] * num_comp_frames
    total_boxes = 0
    for c_idx in range(num_comp_frames):
        cnt = sr.i()
        I[c_idx] = (total_boxes << 16) | cnt
        total_boxes += cnt

    box_x = [sr.j() for _ in range(total_boxes)]
    box_y = [sr.j() for _ in range(total_boxes)]
    box_w = [sr.i() for _ in range(total_boxes)]
    box_h = [sr.i() for _ in range(total_boxes)]
    box_flags = [sr.i() for _ in range(total_boxes)]

    num_durations = (sr.read_u8_raw() << 8) | sr.read_u8_raw()
    K = [sr.i() for _ in range(num_durations)]

    if sr.pos != len(data):
        raise ValueError(
            f"AnimBank {res_id}: consumed {sr.pos}/{len(data)} bytes"
        )

    # Unpack structured composite frames
    composite_frames = []
    for c_idx in range(num_comp_frames):
        part_start = G[c_idx] >> 16
        part_count = G[c_idx] & 0xFFFF
        parts = []
        for p_idx in range(part_start, part_start + part_count):
            h_val = H[p_idx]
            slice_idx = (h_val >> 22) & 0x3FF
            ox = sign_extend_10bit(h_val >> 12)
            oy = sign_extend_10bit(h_val >> 2)
            flip = h_val & 3
            parts.append(
                {
                    "slice_index": slice_idx,
                    "offset_x": ox,
                    "offset_y": oy,
                    "flip_flags": flip,
                    "flip_x": bool(flip & 2),
                    "flip_y": bool(flip & 1),
                }
            )

        box_start = I[c_idx] >> 16
        box_count = I[c_idx] & 0xFFFF
        boxes = []
        for b_idx in range(box_start, box_start + box_count):
            x1 = box_x[b_idx]
            y1 = box_y[b_idx]
            w = box_w[b_idx]
            h = box_h[b_idx]
            b_flags = box_flags[b_idx]
            boxes.append(
                {
                    "x1": x1,
                    "y1": y1,
                    "x2": x1 + w,
                    "y2": y1 + h,
                    "width": w,
                    "height": h,
                    "flags": b_flags,
                    "flags_hex": f"0x{b_flags:02X}",
                }
            )

        composite_frames.append(
            {
                "comp_frame_index": c_idx,
                "part_count": part_count,
                "box_count": box_count,
                "parts": parts,
                "boxes": boxes,
            }
        )

    # Unpack structured animation sequences
    animations = []
    for a_idx in range(num_anims):
        c_val = C[a_idx]
        kf_start = c_val >> 16
        kf_count = c_val & 0x3FF
        loop_count = sign_extend_6bit(c_val >> 10)
        keyframes = []
        for f_idx in range(kf_start, kf_start + kf_count):
            d_val = D[f_idx]
            e_val = E[f_idx]
            dur_idx = d_val & 0x3F
            priority = (d_val >> 6) & 0x03
            comp_idx = (e_val >> 22) & 0x3FF
            dx = sign_extend_10bit(e_val >> 12)
            dy = sign_extend_10bit(e_val >> 2)
            interp_mode = e_val & 0x03
            keyframes.append(
                {
                    "seq_frame_index": f_idx,
                    "comp_frame_index": comp_idx,
                    "duration_index": dur_idx,
                    "duration_ms": K[dur_idx] if dur_idx < len(K) else 0,
                    "priority": priority,
                    "root_motion_dx": dx,
                    "root_motion_dy": dy,
                    "interp_mode": interp_mode,
                }
            )
        animations.append(
            {
                "anim_index": a_idx,
                "keyframe_count": kf_count,
                "loop_count": loop_count,
                "infinite_loop": loop_count == -1,
                "keyframes": keyframes,
            }
        )

    return {
        "res_id": res_id,
        "version": version,
        "num_animations": num_anims,
        "total_seq_frames": total_seq_frames,
        "num_composite_frames": num_comp_frames,
        "total_parts": total_parts,
        "total_boxes": total_boxes,
        "durations_ms": K,
        "bytes_consumed": sr.pos,
        "total_bytes": len(data),
        "animations": animations,
        "composite_frames": composite_frames,
    }


# ---------------------------------------------------------------------------
# 6. Level Bitstream Decoder (type = 245, e.a(byte[], boolean) & e.d(int, int))
# ---------------------------------------------------------------------------
class LSBriefBitReader:
    def __init__(self, data: bytes):
        self.data = data
        self.ji = 0
        self.jj = 0
        self.jk = 0

    def m(self, n: int) -> int:
        var2 = 0
        var3 = n
        while var3 > 0:
            if self.jk == 0:
                self.jj = self.data[self.ji] & 0xFF
                self.ji += 1
                self.jk = 8
            var4 = min(var3, self.jk)
            var2 |= ((self.jj >> (8 - self.jk)) & ((1 << var4) - 1)) << (n - var3)
            self.jk -= var4
            var3 -= var4
        return var2


def bit_length_l(v: int) -> int:
    var1 = -1
    while True:
        var1 += 1
        if ((v - 1) >> var1) <= 0:
            break
    return var1


def decode_level_bitstream(res_id: int, data: bytes) -> Dict[str, Any]:
    slug, title, chapter_idx = LEVEL_META[res_id]
    br = LSBriefBitReader(data)

    hdr_bit0 = br.m(1)
    hdr_ver = br.m(10)
    intro_str_id = br.m(10)
    hy_flag = br.m(1) != 1
    dE_flag = br.m(1) == 1
    dF_flag = br.m(1) == 1
    hdr_reserved = br.m(1)
    max_orbs = br.m(16)
    sep_bit = br.m(1)

    unique_tiles = br.m(16)
    tile_pal_bits = br.m(4)
    tile_palette_map = [br.m(tile_pal_bits) for _ in range(unique_tiles)]
    cell_bits = bit_length_l(unique_tiles)
    width_tiles = br.m(16)
    height_tiles = br.m(16)
    total_cells = width_tiles * height_tiles
    ja_local_grid = [br.m(cell_bits) for _ in range(total_cells)]

    # Visual 1-based tile indices in 2058 (0..127 -> 1..128 in b.java)
    visual_grid_2d = []
    for y in range(height_tiles):
        row = []
        for x in range(width_tiles):
            local_idx = ja_local_grid[y * width_tiles + x]
            row.append(tile_palette_map[local_idx])
        visual_grid_2d.append(row)

    collision_header_u8 = br.m(8)
    unique_tile_solid_flags = [br.m(1) for _ in range(unique_tiles)]

    num_obj_groups = br.m(10)
    width_px = width_tiles * 16
    height_px = height_tiles * 16
    coord_step_bits = bit_length_l(width_px * height_px + 1)

    groups = []
    player_spawn = {"x": 0, "y": 0}

    for _ in range(num_obj_groups):
        group_type = br.m(10)
        group_count = br.m(16)
        var15 = 0
        var16 = 0
        bw_x = []
        bx_y = []
        for _ in range(group_count):
            var15 += br.m(coord_step_bits)
            while var15 >= width_px:
                var15 -= width_px
                var16 += 1
            bw_x.append(var15)
            bx_y.append(var16)

        instances = []
        if group_type == 0:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "length_tiles": br.m(10), "flag": 8192})
        elif group_type == 3:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "aI": br.m(1) == 1, "aH": br.m(1) == 1})
        elif group_type == 4:
            for i in range(group_count):
                ctype = br.m(3)
                param = br.m(10)
                instances.append({"x": bw_x[i], "y": bx_y[i], "chest_type": ctype, "param": param})
        elif group_type == 5:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "length_tiles": br.m(10), "flag": 8})
        elif group_type == 6:
            aZ = [br.m(10) for _ in range(40)]
            ba = [br.m(10) for _ in range(40)]
            instances.append({"x": bw_x[0] if bw_x else 0, "y": bx_y[0] if bx_y else 0, "aZ": aZ, "ba": ba})
        elif group_type == 7:
            for i in range(group_count):
                instances.append({
                    "x": bw_x[i],
                    "y": bx_y[i],
                    "anim_index": br.m(6) << 1,
                    "fn": br.m(1) == 1,
                    "fo": br.m(1) == 1,
                })
        elif group_type == 8:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "cr": br.m(10), "cs": br.m(3), "flag": 128})
        elif group_type == 9:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "length_tiles": br.m(10), "db": br.m(3), "flag": 256})
        elif group_type == 10:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "length_tiles": br.m(10), "flag": 64})
        elif group_type in (11, 12, 13):
            for i in range(group_count):
                inst = {
                    "x": bw_x[i],
                    "y": bx_y[i],
                    "slot_class": group_type - 10,
                    "eg": br.m(3),
                    "spawn_delay_ms": br.m(10) * 1000,
                    "is_camera_target": br.m(1) == 1,
                    "max_hp": br.m(16),
                    "eq": br.m(16),
                    "ep": br.m(10),
                    "ec": br.m(10),
                    "facing_right": br.m(1) != 1,
                    "ez": br.m(1) == 1,
                }
                if group_type == 13:
                    inst["is_argos_boss"] = br.m(1) == 1
                    inst["is_dead_rider_boss"] = br.m(1) == 1
                instances.append(inst)
        elif group_type == 14:
            for i in range(group_count):
                barrier_h = br.m(10)
                waves = []
                for _ in range(15):
                    waves.append({"eI": br.m(10), "eJ": br.m(10), "eK": br.m(1) != 1})
                instances.append({"x": bw_x[i], "y": bx_y[i], "barrier_height_tiles": barrier_h, "waves": waves})
        elif group_type == 17:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "anim_index": br.m(6)})
        elif group_type == 18:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "length_tiles": br.m(10), "flag": 16})
        elif group_type == 21:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "anim_index": br.m(6) << 1})
        elif group_type == 22:
            for i in range(group_count):
                instances.append({
                    "x": bw_x[i],
                    "y": bx_y[i],
                    "flag": 1 if br.m(1) == 1 else 0,
                    "up_tiles": br.m(10),
                    "down_tiles": br.m(10),
                    "left_tiles": br.m(10),
                    "right_tiles": br.m(10),
                })
        elif group_type == 23:
            for i in range(group_count - 1, -1, -1):
                stamp_anim = br.m(8)
                layer = br.m(3)
                instances.append({
                    "x": bw_x[group_count - i - 1],
                    "y": bx_y[group_count - i - 1],
                    "stamp_anim_index": stamp_anim,
                    "z_layer": layer,
                })
        elif group_type == 25:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "flag": 1024})
        elif group_type == 26:
            if group_count > 0:
                player_spawn = {"x": bw_x[0], "y": bx_y[0]}
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i]})
        elif group_type == 27:
            for i in range(group_count):
                instances.append({
                    "x": bw_x[i],
                    "y": bx_y[i],
                    "anim_index": br.m(3),
                    "climbable": br.m(1) == 1,
                })
        elif group_type == 28:
            for i in range(group_count):
                instances.append({
                    "x": bw_x[i],
                    "y": bx_y[i],
                    "width_tiles": br.m(10),
                    "height_tiles": br.m(10),
                    "trigger_type": br.m(3),
                    "param": br.m(10),
                })
        elif group_type == 29:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "length_tiles": br.m(10), "flag": 16384})
        elif group_type == 30:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "flag": 2048 if br.m(1) != 0 else 4096})
        elif group_type == 31:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "flag": 1 if br.m(1) == 1 else 0})
        elif group_type == 32:
            for i in range(group_count):
                instances.append({
                    "x": bw_x[i],
                    "y": bx_y[i],
                    "anim_index": br.m(6) << 1,
                    "bZ": br.m(1) == 1,
                })
        elif group_type == 33:
            for i in range(group_count):
                instances.append({
                    "x": bw_x[i],
                    "y": bx_y[i],
                    "anim_index": br.m(6),
                    "dw": br.m(10),
                    "phase_ms": br.m(3) * 100,
                })
        elif group_type == 34:
            for i in range(group_count):
                is_alt = br.m(1) == 1
                targets = [br.m(3) for _ in range(4)]
                instances.append({"x": bw_x[i], "y": bx_y[i], "cE": is_alt, "targets": targets})
        elif group_type == 35:
            for i in range(group_count):
                instances.append({
                    "x": bw_x[i],
                    "y": bx_y[i],
                    "door_type": br.m(3),
                    "cY": br.m(10),
                    "start_open": br.m(1) == 1,
                    "da": br.m(1) == 1,
                })
        elif group_type == 36:
            for i in range(group_count):
                instances.append({"x": bw_x[i], "y": bx_y[i], "anim_index": br.m(6)})
        else:
            raise ValueError(f"Level {res_id}: unsupported object group type {group_type}")

        groups.append(
            {
                "group_type": group_type,
                "instance_count": group_count,
                "instances": instances,
            }
        )

    if br.ji != len(data):
        raise ValueError(f"Level {res_id}: consumed {br.ji}/{len(data)} bytes")

    pal_variant = -1 if chapter_idx < 0 else CHAPTER_PALETTE_SELECTOR[chapter_idx]
    return {
        "res_id": res_id,
        "slug": slug,
        "title": title,
        "chapter_index": chapter_idx,
        "palette_variant": pal_variant,
        "header": {
            "hdr_bit0": hdr_bit0,
            "hdr_version": hdr_ver,
            "intro_string_id": intro_str_id,
            "hy_flag": hy_flag,
            "dE_enemy_set_a": dE_flag,
            "dF_enemy_set_b": dF_flag,
            "hdr_reserved": hdr_reserved,
            "max_red_orbs": max_orbs,
            "sep_bit": sep_bit,
            "collision_header_u8": collision_header_u8,
        },
        "dimensions": {
            "width_tiles": width_tiles,
            "height_tiles": height_tiles,
            "width_px": width_px,
            "height_px": height_px,
        },
        "player_spawn": player_spawn,
        "unique_tiles_count": unique_tiles,
        "tile_palette_map": tile_palette_map,
        "unique_tile_solid_flags": unique_tile_solid_flags,
        "visual_tile_grid": visual_grid_2d,
        "object_group_count": num_obj_groups,
        "object_groups": groups,
        "bytes_consumed": br.ji,
        "total_bytes": len(data),
    }


# ---------------------------------------------------------------------------
# 7. UTF-8 String Table Decoder (type = 250, a.f(int))
# ---------------------------------------------------------------------------
def decode_string_table(res_id: int, data: bytes) -> Dict[str, Any]:
    lang_code, lang_name = LANGUAGE_META[res_id]
    count = struct.unpack(">H", data[0:2])[0]
    pos = 2
    strings = []
    speaker_map = {
        "^0": "KRATOS",
        "^1": "NARRATOR_TUTORIAL",
        "^2": "SPARTAN_SOLDIER",
        "^3": "CERYX",
        "^4": "TUTORIAL_COMBO",
        "^5": "TUTORIAL_BLADE_OF_ARTEMIS",
        "^6": "TUTORIAL_MEDUSAS_GAZE",
        "^7": "TUTORIAL_ARMY_OF_HADES",
    }

    for idx in range(count):
        byte_len = struct.unpack(">H", data[pos : pos + 2])[0]
        pos += 2
        text = data[pos : pos + byte_len].decode("utf-8")
        pos += byte_len

        control_type = "TEXT"
        speaker = None
        clean_text = text
        if text == "!":
            control_type = "END_DIALOGUE"
        elif text.startswith("*"):
            control_type = "END_BANNER_OR_CUTSCENE"
            clean_text = text[1:]
        elif text.startswith("@") and text[1:].isdigit():
            control_type = "SCRIPT_STATE_JUMP"
        elif len(text) >= 2 and text[:2] in speaker_map:
            control_type = "DIALOGUE_OR_PROMPT"
            speaker = speaker_map[text[:2]]
            clean_text = text[2:]

        strings.append(
            {
                "index": idx,
                "raw": text,
                "clean": clean_text,
                "control_type": control_type,
                "speaker": speaker,
            }
        )

    if pos != len(data):
        raise ValueError(f"String table {res_id}: consumed {pos}/{len(data)} bytes")

    return {
        "res_id": res_id,
        "language_code": lang_code,
        "language_name": lang_name,
        "string_count": count,
        "bytes_consumed": pos,
        "total_bytes": len(data),
        "strings": strings,
    }


# ---------------------------------------------------------------------------
# 8. Menu State Table Decoder (type = 255, 1028)
# ---------------------------------------------------------------------------
def decode_menu_table(res_id: int, data: bytes, en_strings: List[str]) -> Dict[str, Any]:
    if len(data) % 4 != 0:
        raise ValueError(f"Menu table {res_id} size {len(data)} not a multiple of 4")
    count = len(data) // 4
    entries = []
    for idx in range(count):
        val = struct.unpack(">I", data[idx * 4 : (idx + 1) * 4])[0]
        action = (val >> 22) & 0x3F
        flags = (val >> 16) & 0x3FF
        target = (val >> 8) & 0xFF
        str_id = val & 0xFF
        entries.append(
            {
                "state_index": idx,
                "raw_u32_hex": f"0x{val:08X}",
                "action": action,
                "flags_hex": f"0x{flags:03X}",
                "target_state": target,
                "string_id": str_id,
                "label_en": en_strings[str_id] if str_id < len(en_strings) else "",
            }
        )
    return {
        "res_id": res_id,
        "entry_count": count,
        "bytes_consumed": len(data),
        "total_bytes": len(data),
        "entries": entries,
    }


# ---------------------------------------------------------------------------
# 9. MIDI & WAV Audio Inspector (types 0 & 1)
# ---------------------------------------------------------------------------
def inspect_wav_bytes(res_id: int, data: bytes, out_path: Path) -> Dict[str, Any]:
    out_path.parent.mkdir(parents=True, exist_ok=True)
    out_path.write_bytes(data)
    with wave.open(str(out_path), "rb") as wf:
        channels = wf.getnchannels()
        sampwidth = wf.getsampwidth()
        framerate = wf.getframerate()
        nframes = wf.getnframes()
        comptype = wf.getcomptype()
    slug, usage = AUDIO_META[res_id]
    return {
        "res_id": res_id,
        "slug": slug,
        "usage_status": usage,
        "format": "WAV_PCM",
        "channels": channels,
        "bit_depth": sampwidth * 8,
        "sample_rate_hz": framerate,
        "num_frames": nframes,
        "duration_sec": round(nframes / framerate, 4) if framerate else 0,
        "compression_type": comptype,
        "file_size": len(data),
    }


def inspect_midi_bytes(res_id: int, data: bytes, out_path: Path) -> Dict[str, Any]:
    out_path.parent.mkdir(parents=True, exist_ok=True)
    out_path.write_bytes(data)
    if data[:4] != b"MThd":
        raise ValueError("Invalid MIDI MThd header")
    hdr_len, fmt, ntracks, division = struct.unpack(">IHhH", data[4:14])
    pos = 8 + hdr_len
    tracks = []
    for t_idx in range(ntracks):
        if data[pos : pos + 4] != b"MTrk":
            raise ValueError(f"Invalid MTrk at offset {pos}")
        t_len = struct.unpack(">I", data[pos + 4 : pos + 8])[0]
        tracks.append({"track_index": t_idx, "byte_length": t_len})
        pos += 8 + t_len
    if pos != len(data):
        raise ValueError(f"MIDI size mismatch: {pos} != {len(data)}")
    slug, usage = AUDIO_META[res_id]
    return {
        "res_id": res_id,
        "slug": slug,
        "usage_status": usage,
        "format": f"SMF_TYPE_{fmt}",
        "num_tracks": ntracks,
        "ppqn_division": division,
        "tracks": tracks,
        "file_size": len(data),
    }


# ---------------------------------------------------------------------------
# Main Extraction Pipeline
# ---------------------------------------------------------------------------
def main() -> None:
    if EXTRACTED_ROOT.exists():
        shutil.rmtree(EXTRACTED_ROOT)
    EXTRACTED_ROOT.mkdir(parents=True, exist_ok=True)

    # Build reverse palette -> target PNG lookup
    pal_to_png: Dict[int, int] = {}
    for png_id, pal_ids in PNG_PALETTE_MAPPING.items():
        for pal_id in pal_ids:
            pal_to_png[pal_id] = png_id

    # 1. Discover and parse all RP1..RP33 packs in numerical order
    rp_files = []
    for item in WORKSPACE_ROOT.iterdir():
        if item.is_file() and item.name.startswith("RP") and item.name[2:].isdigit():
            rp_files.append((int(item.name[2:]), item))
    rp_files.sort(key=lambda x: x[0])

    packs_meta = []
    all_entries: Dict[int, Dict[str, Any]] = {}

    for pack_id, rp_path in rp_files:
        p_info = parse_rp_container(rp_path, pack_id)
        packs_meta.append(
            {
                "pack_id": pack_id,
                "pack_name": p_info["pack_name"],
                "file_size": p_info["file_size"],
                "header_flags": p_info["header_flags"],
                "size_width_bytes": p_info["size_width_bytes"],
                "entry_count": p_info["entry_count"],
                "payload_size": p_info["payload_size"],
            }
        )
        for entry in p_info["entries"]:
            all_entries[entry["res_id"]] = entry

    # Pre-decode English strings so Menu Table 1028 can include English labels
    en_table = decode_string_table(10240, all_entries[10240]["data"])
    en_strings = [s["raw"] for s in en_table["strings"]]

    manifest_items: List[Dict[str, Any]] = []
    all_palettes: Dict[int, Any] = {}
    all_slices: Dict[int, Any] = {}
    all_fonts: Dict[int, Any] = {}
    all_anim_summaries: List[Dict[str, Any]] = []
    all_image_meta: Dict[str, Any] = {}
    all_audio_meta: List[Dict[str, Any]] = []

    # 2. Extract standalone /i.png MIDlet icon
    icon_src = WORKSPACE_ROOT / "i.png"
    if icon_src.exists():
        icon_bytes = icon_src.read_bytes()
        icon_info = inspect_png(icon_bytes)
        icon_dst = EXTRACTED_ROOT / "images" / "icon" / "i.png"
        icon_dst.parent.mkdir(parents=True, exist_ok=True)
        icon_dst.write_bytes(icon_bytes)
        all_image_meta["i.png"] = icon_info
        manifest_items.append(
            {
                "res_id": None,
                "res_id_hex": "ROOT_ICON",
                "pack": "JAR_ROOT",
                "entry_index": None,
                "type_u8": 253,
                "type_name": "STANDALONE_PNG_ICON",
                "original_size": len(icon_bytes),
                "raw_path": "i.png",
                "extracted_paths": [str(icon_dst.relative_to(ANALYSIS_ROOT)).replace("\\", "/")],
                "status": "DECODED_OK",
                "notes": f"Standalone MIDlet icon ({icon_info['width']}x{icon_info['height']})",
            }
        )

    # 3. First pass: decode all palettes so we can also generate palette-swapped PNGs
    for res_id, entry in sorted(all_entries.items()):
        if entry["type_u8"] == 251:
            pal_decoded = decode_palette(res_id, entry["data"], pal_to_png)
            all_palettes[res_id] = pal_decoded

    # 4. Process every resource entry across RP1..RP33
    for res_id, entry in sorted(all_entries.items()):
        pack_id = entry["pack_id"]
        idx = entry["entry_index"]
        t_u8 = entry["type_u8"]
        blob = entry["data"]

        # Save untouched raw slice
        raw_dir = EXTRACTED_ROOT / "raw" / f"RP{pack_id}"
        raw_dir.mkdir(parents=True, exist_ok=True)
        raw_file = raw_dir / f"res_{res_id}_entry_{idx:02d}_type_{t_u8}.bin"
        raw_file.write_bytes(blob)

        extracted_paths: List[str] = []
        status = "DECODED_OK"
        notes = ""

        if t_u8 == 253:
            png_info = inspect_png(blob)
            if res_id in UI_PNG_IDS:
                slug = UI_PNG_IDS[res_id]
                out_file = EXTRACTED_ROOT / "images" / "ui" / f"{res_id}_{slug}.png"
            elif res_id == 2058:
                slug = SPRITESHEET_PNG_IDS[res_id]
                out_file = EXTRACTED_ROOT / "images" / "tilesets" / f"{res_id}_{slug}_default.png"
            else:
                slug = SPRITESHEET_PNG_IDS.get(res_id, f"sheet_{res_id}")
                out_file = EXTRACTED_ROOT / "images" / "spritesheets" / f"{res_id}_{slug}_default.png"

            out_file.parent.mkdir(parents=True, exist_ok=True)
            out_file.write_bytes(blob)
            extracted_paths.append(str(out_file.relative_to(ANALYSIS_ROOT)).replace("\\", "/"))
            all_image_meta[str(res_id)] = {
                "res_id": res_id,
                "slug": slug,
                **png_info,
                "palette_variants_generated": [],
            }

            # Generate palette-swapped PNGs if this PNG has chapter palettes
            if res_id in PNG_PALETTE_MAPPING:
                for variant_idx, pal_id in enumerate(PNG_PALETTE_MAPPING[res_id]):
                    pal_blob = all_entries[pal_id]["data"]
                    swapped_bytes = swap_png_palette(blob, pal_blob)
                    var_file = out_file.parent / f"{res_id}_{slug}_pal{variant_idx}_{pal_id}.png"
                    var_file.write_bytes(swapped_bytes)
                    rel_var = str(var_file.relative_to(ANALYSIS_ROOT)).replace("\\", "/")
                    extracted_paths.append(rel_var)
                    all_image_meta[str(res_id)]["palette_variants_generated"].append(
                        {"variant_index": variant_idx, "palette_res_id": pal_id, "path": rel_var}
                    )
            notes = (
                f"PNG {png_info['width']}x{png_info['height']} "
                f"({png_info['num_palette_entries']} colors, "
                f"{len(PNG_PALETTE_MAPPING.get(res_id, []))} palette swaps)"
            )

        elif t_u8 == 251:
            pal_obj = all_palettes[res_id]
            out_file = EXTRACTED_ROOT / "palettes" / f"pal_{res_id}.json"
            out_file.parent.mkdir(parents=True, exist_ok=True)
            out_file.write_text(json.dumps(pal_obj, indent=2), encoding="utf-8")
            extracted_paths.append(str(out_file.relative_to(ANALYSIS_ROOT)).replace("\\", "/"))
            status = (
                "DECODED_OK"
                if pal_obj["status"] != "UNUSED_ORPHAN_PLTE"
                else "DECODED_UNUSED_IN_GAME"
            )
            notes = f"{pal_obj['format']} ({pal_obj['num_colors']} colors, {pal_obj['status']})"

        elif t_u8 == 254:
            decoded_sf = decode_slice_or_font_table(res_id, blob)
            if decoded_sf["is_bitmap_font"]:
                out_file = EXTRACTED_ROOT / "fonts" / f"font_{res_id}.json"
                all_fonts[res_id] = decoded_sf
                notes = (
                    f"Bitmap Font -> PNG {decoded_sf['image_res_id']} "
                    f"({decoded_sf['font_metrics']['glyph_count']} glyphs, "
                    f"height={decoded_sf['font_metrics']['line_height']}px)"
                )
            else:
                out_file = EXTRACTED_ROOT / "slices" / f"slice_{res_id}.json"
                if res_id == 2054:
                    decoded_sf["status"] = "DECODED_UNUSED_IN_GAME"
                    decoded_sf["notes"] = (
                        "Unused 22x22 tile slice table from 240x320 build; "
                        "176x208 runtime slices PNG 2058 directly as an 8x16 grid of 16x16 tiles (e.java:3399)"
                    )
                    status = "DECODED_UNUSED_IN_GAME"
                    notes = (
                        f"Unused 22x22 Tile Slice Table -> PNG {decoded_sf['image_res_id']} "
                        f"(runtime uses 16x16 grid on PNG 2058)"
                    )
                else:
                    decoded_sf["status"] = "DECODED_OK"
                    notes = (
                        f"Sprite Slice Table -> PNG {decoded_sf['image_res_id']} "
                        f"({decoded_sf['num_slices']} slices, flags={decoded_sf['flags_u16']})"
                    )
                all_slices[res_id] = decoded_sf
            out_file.parent.mkdir(parents=True, exist_ok=True)
            out_file.write_text(json.dumps(decoded_sf, indent=2), encoding="utf-8")
            extracted_paths.append(str(out_file.relative_to(ANALYSIS_ROOT)).replace("\\", "/"))

        elif t_u8 == 247:
            decoded_anim = decode_animation_bank(res_id, blob)
            out_file = EXTRACTED_ROOT / "animations" / f"anim_{res_id}.json"
            out_file.parent.mkdir(parents=True, exist_ok=True)
            out_file.write_text(json.dumps(decoded_anim, indent=2), encoding="utf-8")
            extracted_paths.append(str(out_file.relative_to(ANALYSIS_ROOT)).replace("\\", "/"))
            all_anim_summaries.append(
                {
                    "res_id": res_id,
                    "num_animations": decoded_anim["num_animations"],
                    "total_seq_frames": decoded_anim["total_seq_frames"],
                    "num_composite_frames": decoded_anim["num_composite_frames"],
                    "total_parts": decoded_anim["total_parts"],
                    "total_boxes": decoded_anim["total_boxes"],
                    "num_durations": len(decoded_anim["durations_ms"]),
                    "bytes_consumed": decoded_anim["bytes_consumed"],
                }
            )
            notes = (
                f"AnimBank v{decoded_anim['version']}: {decoded_anim['num_animations']} anims, "
                f"{decoded_anim['num_composite_frames']} compFrames, "
                f"{decoded_anim['total_parts']} parts, {decoded_anim['total_boxes']} hitboxes"
            )

        elif t_u8 == 245:
            decoded_lvl = decode_level_bitstream(res_id, blob)
            slug = decoded_lvl["slug"]
            out_file = EXTRACTED_ROOT / "levels" / f"level_{res_id}_{slug}.json"
            out_file.parent.mkdir(parents=True, exist_ok=True)
            out_file.write_text(json.dumps(decoded_lvl, indent=2), encoding="utf-8")
            extracted_paths.append(str(out_file.relative_to(ANALYSIS_ROOT)).replace("\\", "/"))
            notes = (
                f"{decoded_lvl['title']} ({decoded_lvl['dimensions']['width_tiles']}x"
                f"{decoded_lvl['dimensions']['height_tiles']} tiles, "
                f"{decoded_lvl['object_group_count']} object groups)"
            )

        elif t_u8 == 250:
            decoded_str = decode_string_table(res_id, blob)
            lang_code = decoded_str["language_code"]
            out_file = EXTRACTED_ROOT / "strings" / f"strings_{res_id}_{lang_code}.json"
            out_file.parent.mkdir(parents=True, exist_ok=True)
            out_file.write_text(json.dumps(decoded_str, indent=2, ensure_ascii=False), encoding="utf-8")
            extracted_paths.append(str(out_file.relative_to(ANALYSIS_ROOT)).replace("\\", "/"))
            notes = f"{decoded_str['language_name']} localization ({decoded_str['string_count']} UTF-8 strings)"

        elif t_u8 == 255:
            decoded_menu = decode_menu_table(res_id, blob, en_strings)
            out_file = EXTRACTED_ROOT / "menu" / f"menu_{res_id}.json"
            out_file.parent.mkdir(parents=True, exist_ok=True)
            out_file.write_text(json.dumps(decoded_menu, indent=2, ensure_ascii=False), encoding="utf-8")
            extracted_paths.append(str(out_file.relative_to(ANALYSIS_ROOT)).replace("\\", "/"))
            notes = f"Menu State Table ({decoded_menu['entry_count']} states)"

        elif t_u8 == 1:
            slug, usage = AUDIO_META[res_id]
            out_file = EXTRACTED_ROOT / "audio" / "wav" / f"{res_id}_{slug}.wav"
            wav_meta = inspect_wav_bytes(res_id, blob, out_file)
            all_audio_meta.append(wav_meta)
            extracted_paths.append(str(out_file.relative_to(ANALYSIS_ROOT)).replace("\\", "/"))
            status = "DECODED_OK" if "ACTIVE" in usage else "DECODED_RECOVERABLE_AUDIO"
            notes = (
                f"WAV PCM {wav_meta['sample_rate_hz']}Hz {wav_meta['bit_depth']}bit "
                f"({wav_meta['duration_sec']}s, {usage})"
            )

        elif t_u8 == 0:
            slug, usage = AUDIO_META[res_id]
            out_file = EXTRACTED_ROOT / "audio" / "midi" / f"{res_id}_{slug}.mid"
            midi_meta = inspect_midi_bytes(res_id, blob, out_file)
            all_audio_meta.append(midi_meta)
            extracted_paths.append(str(out_file.relative_to(ANALYSIS_ROOT)).replace("\\", "/"))
            notes = (
                f"MIDI Type 1 ({midi_meta['num_tracks']} tracks, "
                f"{midi_meta['ppqn_division']} PPQN, {usage})"
            )

        else:
            status = "UNSUPPORTED_TYPE"
            notes = f"Unrecognized resource type {t_u8}"

        manifest_items.append(
            {
                "res_id": res_id,
                "res_id_hex": entry["res_id_hex"],
                "pack": f"RP{pack_id}",
                "pack_id": pack_id,
                "entry_index": idx,
                "type_u8": t_u8,
                "type_i8": entry["type_i8"],
                "type_name": entry["type_name"],
                "original_size": entry["size"],
                "raw_path": str(raw_file.relative_to(ANALYSIS_ROOT)).replace("\\", "/"),
                "extracted_paths": extracted_paths,
                "status": status,
                "notes": notes,
            }
        )

    # Write combined index JSON files
    (EXTRACTED_ROOT / "palettes" / "all_palettes.json").write_text(
        json.dumps(all_palettes, indent=2), encoding="utf-8"
    )
    (EXTRACTED_ROOT / "slices" / "all_slices.json").write_text(
        json.dumps(all_slices, indent=2), encoding="utf-8"
    )
    (EXTRACTED_ROOT / "fonts" / "all_fonts.json").write_text(
        json.dumps(all_fonts, indent=2, ensure_ascii=False), encoding="utf-8"
    )
    (EXTRACTED_ROOT / "animations" / "animations_summary.json").write_text(
        json.dumps(all_anim_summaries, indent=2), encoding="utf-8"
    )
    (EXTRACTED_ROOT / "images" / "image_metadata.json").write_text(
        json.dumps(all_image_meta, indent=2), encoding="utf-8"
    )
    (EXTRACTED_ROOT / "audio" / "audio_metadata.json").write_text(
        json.dumps(all_audio_meta, indent=2), encoding="utf-8"
    )

    # Write master asset_manifest.json
    manifest_doc = {
        "game": "God of War: Betrayal (2007 J2ME)",
        "total_rp_containers": len(packs_meta),
        "total_rp_entries": len(all_entries),
        "standalone_root_assets": 1,
        "total_manifest_items": len(manifest_items),
        "total_uncompressed_rp_payload_bytes": sum(p["payload_size"] for p in packs_meta),
        "packs": packs_meta,
        "resources": manifest_items,
    }
    manifest_json_path = ANALYSIS_ROOT / "asset_manifest.json"
    manifest_json_path.write_text(json.dumps(manifest_doc, indent=2), encoding="utf-8")

    # Write human-readable ASSET_MANIFEST.md
    md_lines = [
        "# God of War: Betrayal — Extracted Asset Manifest",
        "",
        f"- **RP Containers Parsed**: `{len(packs_meta)}` (`RP1`–`RP10`, `RP12`–`RP30`, `RP32`–`RP33`)",
        f"- **Total RP Resource Entries**: `{len(all_entries)}` (`668,786` bytes)",
        f"- **Standalone JAR Root Assets**: `1` (`i.png`, `1,785` bytes)",
        f"- **Total Cataloged Assets**: `{len(manifest_items)}`",
        "",
        "| Res ID | Hex | Pack | Idx | Type | Size (B) | Status | Primary Extracted Path | Notes |",
        "| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :--- | :--- |",
    ]
    for item in manifest_items:
        rid_str = str(item["res_id"]) if item["res_id"] is not None else "—"
        idx_str = str(item["entry_index"]) if item["entry_index"] is not None else "—"
        primary_path = item["extracted_paths"][0] if item["extracted_paths"] else item["raw_path"]
        extra_cnt = len(item["extracted_paths"]) - 1
        path_label = f"`{primary_path}`" + (f" (+{extra_cnt} swaps)" if extra_cnt > 0 else "")
        md_lines.append(
            f"| `{rid_str}` | `{item['res_id_hex']}` | `{item['pack']}` | `{idx_str}` | "
            f"`{item['type_u8']}` ({item['type_name']}) | `{item['original_size']}` | "
            f"`{item['status']}` | {path_label} | {item['notes']} |"
        )

    manifest_md_path = ANALYSIS_ROOT / "ASSET_MANIFEST.md"
    manifest_md_path.write_text("\n".join(md_lines) + "\n", encoding="utf-8")

    print(
        f"Extraction complete: {len(all_entries)} RP resources + 1 icon = "
        f"{len(manifest_items)} total items written to {EXTRACTED_ROOT}"
    )


if __name__ == "__main__":
    main()
