# God of War: Betrayal (2007 J2ME) — Binary Resource & Container Formats

This document specifies the exact byte-level and bit-level binary formats used across all 31 `/RP*` resource containers in *God of War: Betrayal* (`a.java`, `c.java`, and `e.java`). Every format documented below has been verified against 100% of the binary files in the repository with zero unparsed trailing bytes.

---

## 1. Resource ID Addressing Scheme (`a.java`)

Every asset in the game is addressed by a 16-bit integer `resId` (`int` in Java, `0x0400`–`0x8400`):

$$\text{pack\_id} = \text{resId} \gg 10 \quad (1 \dots 33)$$

$$\text{entry\_index} = \text{resId} \ \& \ 1023 \quad (0 \dots \text{count}-1)$$

* When [`a.b(int var0, int var1)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L353-L394) loads a resource:
  1. It extracts `pack_id = var0 >> 10` (`var2`) and `entry_index = var0 & 1023`.
  2. If `pack_id != p` (the currently cached pack ID in [`a.p`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L41)), [`a.e(int var0)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L433-L458) opens `"/RP" + pack_id` from the JAR classpath via `Class.getResourceAsStream("/RP" + var0)` and parses the pack into memory arrays `a.f[]` (types), `a.s[]` (sizes), and `a.t[]` (concatenated payload).
  3. It sums `s[0] + s[1] + ... + s[entry_index - 1]` to compute the byte offset (`var4`) of `entry_index` inside `a.t[]`, and slices `s[entry_index]` bytes (`var5`).

---

## 2. `/RP*` Pack Container Format ([`a.a(DataInputStream)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L396-L431))

All `/RP1`–`/RP33` files share a compact big-endian container format read by [`a.a(DataInputStream)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L396-L431):

| Offset | Type | Field | Description |
| :--- | :--- | :--- | :--- |
| `0x00` | `u8` | `header_flags` (`q`) | Bit 0 controls the byte width of entry sizes: `size_width = (1 + (header_flags & 1)) << 1`.<br>• `header_flags = 65` (`0x41`, bit 0 = `1`) in `/RP1` → `size_width = 4` (`u32` sizes).<br>• `header_flags = 64` (`0x40`, bit 0 = `0`) in `/RP2`–`/RP33` → `size_width = 2` (`u16` sizes). |
| `0x01` | `u16 BE` | `entry_count` (`r`) | Number of resources packed into this container (`1` to `41`). |
| `0x03` | `u16[r]` or `u32[r] BE` | `sizes` (`s[]`) | Unsigned big-endian byte length of each entry `0 .. r-1`. Read via loop: `for (var5 = 0; var5 < size_width; ++var5) s[i] = (s[i] << 8) + readUnsignedByte()`. |
| `0x03 + r * size_width` | `u8[r]` | `types` (`f[]`) | Unsigned byte (stored as signed `byte` in Java) identifying the format of each entry `0 .. r-1`. |
| `0x03 + r * (size_width + 1)` | `u32 BE` | `payload_size` (`var2`) | Total byte length of all entries concatenated (`sum(s[0 .. r-1])`). |
| `0x07 + r * (size_width + 1)` | `u8[payload_size]` | `payload` (`t[]`) | Raw concatenated bytes of entries `0 .. r-1` back-to-back with no padding. |

### Resource Type Tags (`f[entry_index]`)

| Unsigned (`u8`) | Signed (`byte`) | Hex | Format / Handler |
| :---: | :---: | :---: | :--- |
| `0` | `0` | `0x00` | **Standard MIDI file (`audio/midi`)** — loaded by [`d.b(int)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/d.java#L95-L120) (`1063` in `RP1`). |
| `1` | `1` | `0x01` | **RIFF WAVE PCM audio (`audio/x-wav`)** — loaded by [`d.b(int)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/d.java#L95-L120) (`1054`–`1062`, `1064` in `RP1`). |
| `245` | `-11` | `0xF5` | **Bitstream-Compressed Level Data** — decoded by [`e.a(byte[], boolean)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L7536-L8193) (`9216`, `9217`, `19456`–`27648`). |
| `247` | `-9` | `0xF7` | **Hierarchical Animation Bank** — decoded by [`a.g(int)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L646-L785). |
| `250` | `-6` | `0xFA` | **UTF-8 Localization String Table** — decoded by [`a.f(int)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L604-L624). |
| `251` | `-5` | `0xFB` | **Palette Resource** — either raw `PLTE + CRC32` replacement bytes for [`a.c(int, byte[])`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L317-L350), or the 768-byte fire RGB table (`1053`). |
| `253` | `-3` | `0xFD` | **Indexed-Color PNG Image (`colorType=3, bitDepth=8`)** — decoded via `Image.createImage` in [`a.c(int, byte[])`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L317-L350). |
| `254` | `-2` | `0xFE` | **Sprite Slice / Bitmap Font Table (`c[]`)** — decoded by [`a.b(int, byte[])`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L235-L297). |
| `255` | `-1` | `0xFF` | **Menu State Table (`1028`)** — decoded in [`e.l(int)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L7283-L7299). |

---

## 3. Indexed PNG Palette Swapping ([`a.c(int, byte[])`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L317-L350) & [`a.b(DataInputStream)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L299-L315))

All 28 sprite sheet and tileset images stored in `RP*` (`type = 253`) are standard 8-bit indexed-color PNG files (`bitDepth = 8`, `colorType = 3`, with transparency chunk `tRNS` where index `0` is transparent `#FF00FF`).

To support chapter-specific color themes without duplicating PNG bitmaps, [`a.c(int var0, byte[] var1)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L317-L350) swaps the PNG `PLTE` chunk and its 4-byte CRC32 in-place inside `a.t[]` before calling `Image.createImage(t, offset, length)`:

1. **Chunk Scanner ([`a.b(DataInputStream)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L299-L315))**:
   - Skips the 8-byte PNG signature (`89 50 4E 47 0D 0A 1A 0A`).
   - Loops over PNG chunks:
     - Reads `u32 BE chunk_len` (`var2`) and `u32 BE chunk_type` (`var3`).
     - If `chunk_type == 1347179589` (`0x504C5445` = ASCII `"PLTE"`), returns the byte offset of the `PLTE` data payload within the PNG stream.
     - If `chunk_type == 1229278788` (`0x49444154` = ASCII `"IDAT"`), aborts and returns `-1`.
     - Otherwise skips `chunk_len + 4` bytes (payload + CRC32).
2. **Palette Resource Format (`type = 251`, except `1053`)**:
   - Every palette swap resource (`2059`–`2070`, `3081`–`3089`, `4099`–`4101`, `5129`–`5144`, `6150`–`6155`, `7171`–`7180`, `13315`–`13317`, `15363`–`15365`, `16387`–`16389`, `17411`–`17413`, `18435`–`18437`, `28675`–`28677`) has a byte length of:
     $$\text{size} = 3 \times N_{\text{colors}} + 4 \text{ bytes}$$
   - The first $3 \times N_{\text{colors}}$ bytes are the replacement RGB triplets (`PLTE` chunk payload, always starting with `FF 00 FF` for index 0 on sprites), and the **last 4 bytes** are the precomputed big-endian **PNG CRC32** of `"PLTE" + RGB_payload`!
   - Wait: one special case exists in `RP5` (`5135`–`5141`, size `769` bytes for `5127`, which only has a 96-byte `PLTE` chunk). Notice in [`e.l(int)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L7477), `5124` (VFX sprite slice using image `5127`) is loaded with `c.a(5124, null)` (no palette swap); `5135`–`5141` are unused in code.
3. **Chapter Palette Selection Table ([`e.bK`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L118))**:
   - `e.bK = {-1, -1, -1, 2, 0, 1, 1, 0, 2, 2}` indexed by chapter `e.bG` (`0..9`, or `0` in Arena mode where `bG == -1`).
   - When `bK[chapter] == -1` (Chapters 1–3 and Arena), the original embedded PNG palette (`null`) is used.
   - When `bK[chapter] >= 0` (Chapters 4–10), the game loads palette resource `base_pal_id + bK[chapter]`.

---

## 4. Sprite Slice & Bitmap Font Table Format (`type = 254`, [`a.b(int, byte[])`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L235-L297))

Resource type `254` (`0xFE`) defines an array of [`c`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/c.java#L1-L79) objects (`c[]`) representing rectangular sub-image slices (`c.java`) from an indexed PNG atlas in the same `RP*` pack.

### 4.1 Header Layout

| Offset | Type | Java Var | Description |
| :--- | :--- | :--- | :--- |
| `0x00` | `u16 BE` | `var3` | Local entry index of the target PNG image within the same `RP*` pack. Full image resource ID is `imageResId = (sliceResId & ~1023) | var3`. |
| `0x02` | `u8` | — | Format version / reserved byte (always `7` across all 34 slice tables). |
| `0x03` | `i16 BE` | `var4` | Remaining byte count + 4. The SoA data buffer size is `data_len = var4 - 4`. |
| `0x05` | `u16 BE` | `var5` | 16-bit field presence & bit-width flags (`u[0..15]`). |
| `0x07` | `u16 BE` | `var8` | Number of slices (`c[var8]`) in the table. |
| `0x09` | `u8[var4 - 4]` | `var7` | Structure-of-Arrays (SoA) field stream (`data_len` bytes). |

### 4.2 Field Presence & Bit-Width Flags (`var5`)

[`a.b(int, byte[])`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L254-L256) unpacks `var5` into boolean array `u[0..15]`:
```java
for (var6 = 0; var6 < 16; ++var6) {
    u[var6] = (var5 >> var6 & 1) == 0;
}
```
Notice the inverted polarity: **`u[bit]` is `true` when bit `bit` of `var5` is `0`!**

The 10 fields of [`c`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/c.java#L5-L15) are organized into 3 presence groups (`var9 = 0..2`, controlled by bits `0..2` of `var5`) and 10 width flags (`var10 = 0..9`, controlled by bits `5..14` of `var5`):

| Field Index (`var10`) | Group (`var9`) | Presence Bit (`u[var9]`) | Width Bit (`u[var10 + 5]`) | [`c`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/c.java) Field | Meaning in Sprite Slice | Signedness when 1-byte (`u[var10+5] == false`) |
| :---: | :---: | :---: | :---: | :---: | :--- | :--- |
| `0` | `0` (`2` fields) | Bit `0` (`0x0001`) | Bit `5` (`0x0020`) | `c.a` | Anchor / Registration X offset (or UTF-16 delta high byte in fonts) | **Signed** (`i8`: `var7[pos]`) |
| `1` | `0` (`2` fields) | Bit `0` (`0x0001`) | Bit `6` (`0x0040`) | `c.b` | Anchor / Registration Y offset (or UTF-16 delta low byte in fonts) | **Signed** (`i8`: `var7[pos]`) |
| `2` | `1` (`4` fields) | Bit `1` (`0x0002`) | Bit `7` (`0x0080`) | `c.i` | Atlas source X (`u` in pixels) | **Unsigned** (`u8`: `var7[pos] & 0xFF`) |
| `3` | `1` (`4` fields) | Bit `1` (`0x0002`) | Bit `8` (`0x0100`) | `c.j` | Atlas source Y (`v` in pixels) | **Unsigned** (`u8`: `var7[pos] & 0xFF`) |
| `4` | `1` (`4` fields) | Bit `1` (`0x0002`) | Bit `9` (`0x0200`) | `c.c` | Slice width (`w` in pixels) | **Unsigned** (`u8`: `var7[pos] & 0xFF`) |
| `5` | `1` (`4` fields) | Bit `1` (`0x0002`) | Bit `10` (`0x0400`) | `c.d` | Slice height (`h` in pixels) | **Unsigned** (`u8`: `var7[pos] & 0xFF`) |
| `6` | `2` (`4` fields) | Bit `2` (`0x0004`) | Bit `11` (`0x0800`) | `c.e` | Trim / Collision offset X (or glyph X offset in fonts) | **Signed** (`i8`: `var7[pos]`) |
| `7` | `2` (`4` fields) | Bit `2` (`0x0004`) | Bit `12` (`0x1000`) | `c.f` | Trim / Collision offset Y (or glyph Y offset in fonts) | **Signed** (`i8`: `var7[pos]`) |
| `8` | `2` (`4` fields) | Bit `2` (`0x0004`) | Bit `13` (`0x2000`) | `c.g` | Original width / Glyph advance width (`w2`) | **Unsigned** (`u8`: `var7[pos] & 0xFF`) |
| `9` | `2` (`4` fields) | Bit `2` (`0x0004`) | Bit `14` (`0x4000`) | `c.h` | Original height / Font metric tag (`h2`) | **Unsigned** (`u8`: `var7[pos] & 0xFF`) |

* **When `u[var10 + 5] == true` (bit is `0`)**: The field is stored as a 2-byte big-endian signed `short` (`(var7[pos++] << 8) | (var7[pos++] & 0xFF)`).
* **When `u[var10 + 5] == false` (bit is `1`)**: The field is stored as a 1-byte value (signed `i8` for `var10 in {0, 1, 6, 7}`, unsigned `u8` for `var10 in {2, 3, 4, 5, 8, 9}`).
* **Structure-of-Arrays (SoA) Ordering**: The outer loop iterates over fields `var10 = 0 .. 9`, and the inner loop iterates over all slices `var12 = 0 .. var8 - 1`. Thus, all `c[0..var8-1].a` values are stored contiguously, followed by all `.b` values, etc.
* **Default Fallback (`!u[2]`)**: If Group 2 (`{e, f, g, h}`) is omitted (`u[2] == false`, i.e. bit 2 of `var5` is `1`, which is true for all standard sprite sheets where `var5` ends in `4` or `c`), `c.g` defaults to `c.c` (width) and `c.h` defaults to `c.d` (height).

### 4.3 Sprite Flipping & Anchor Transformation ([`c.a(Graphics, int, int, int)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/c.java#L41-L78))

When rendering a slice at `(x, y)` with `flipFlags` (`0..3`):
* **Normal (`flipFlags == 0`)**:
  - `drawX = x + c.a + c.e`
  - `drawY = y + c.b + c.f`
* **Horizontal Flip (`(flipFlags & 1) != 0`)**:
  - `drawX = x - c.e - c.c - c.a`
  - Uses Nokia `DirectGraphics.FLIP_HORIZONTAL` (`8192` / `0x2000`) or MIDP 2.0 `Sprite.TRANS_MIRROR` (`2`).
* **Vertical Flip (`(flipFlags & 2) != 0`)**:
  - `drawY = y - c.f - c.d - c.b`
  - Uses Nokia `DirectGraphics.FLIP_VERTICAL` (`16384` / `0x4000`) or MIDP 2.0 `Sprite.TRANS_MIRROR_ROT180` (`1`).
* **Both Horizontal & Vertical Flip (`flipFlags == 3`)**:
  - Applies both coordinate inversions and `DirectGraphics` `8192 | 16384` (`24576`) or MIDP 2.0 `Sprite.TRANS_ROT180` (`3`).

### 4.4 Bitmap Font Post-Processing (`1031`, `1032`, `1033` in [`a.a(c[])`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L83-L108))

Resources `1031` (150 slices), `1032` (127 slices), and `1033` (61 slices) have `var5 = 0xFFF8` (all 10 fields present as 1-byte values) and `c[3].h == 255`, which marks them as **Bitmap Fonts**:
* **Control Slices (`c[0..3]`)**:
  - `c[0].h`: Font line height (`a.c()`).
  - `c[1].h`: Font baseline offset (`a.b()`).
  - `c[2].h`: Space character (`' '`, ASCII `32`) advance width (`c[2].g = c[2].h`).
  - `c[3].h`: Magic marker `255` (`0xFF`, reset to `0` after initialization so initialization is idempotent).
* **Glyph Slices (`c[4 .. length-1]`)**:
  - Before post-processing, `(c[i].a << 8) + (c[i].b & 0xFF)` stores the **delta UTF-16 codepoint** from glyph `i - 1` to glyph `i`.
  - [`a.a(c[])`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L89-L97) accumulates `codepoint += (c[i].a << 8) + (c[i].b & 255)`, moves the glyph rendering offsets `c[i].a = c[i].e; c[i].b = c[i].f`, and stores the resulting sorted UTF-16 `codepoint` into `c[i].e`.
  - [`a.b(char)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L110-L129) performs a binary search over `c[4 .. length-1].e` to look up any Unicode character in $O(\log N)$ time, using `c[idx].g + c[0].e` as the character advance width.

---

## 5. UTF-8 String Table Format (`type = 250`, [`a.f(int)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L604-L624))

The 6 localization packs (`10240` English, `12288` French, `29696` German, `30720` Italian, `32768` Portuguese, `33792` Spanish) each contain a single `type = 250` resource with 293 UTF-8 strings:

| Offset | Type | Description |
| :--- | :--- | :--- |
| `0x00` | `u16 BE` | `string_count` (always `293` / `0x0125`). |
| `0x02+` | `Modified UTF-8[293]` | 293 sequential Java `DataInputStream.readUTF()` strings: each prefixed with a `u16 BE` byte length followed by UTF-8 bytes. |

### Embedded Control Codes & Key Placeholders

1. **Dynamic Key Token Replacement ([`e.f(String)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L8398-L8445))**:
   Strings contain single-character placeholders that are replaced at runtime with the device's key labels:
   - `[` → `"4/" + getKeyName(LEFT)` (Move Left)
   - `]` → `"6/" + getKeyName(RIGHT)` (Move Right)
   - `{` → `"2/" + getKeyName(UP)` (Jump / Up)
   - `}` → `"8/" + getKeyName(DOWN)` (Crouch / Block / Interact / Grab)
   - `\` → `"5/" + getKeyName(FIRE)` (Attack / Confirm)
   - `%` → MIDlet version string from `MANIFEST.MF` (`"1.3.0"`)
2. **Scripted Dialogue Control Prefixes ([`e.c()`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L290-L433))**:
   - `"!"`: End of dialogue sequence.
   - `"*"`: End of level-intro text banner; transitions to gameplay or next cutscene step (`"*0"` triggers special cutscene mode).
   - `"@<digits>"`: Advances cutscene script state (`ip`) to the specified integer and pauses for timer.
   - `"^<0..7><text>"`: Displays speaker portrait/icon and tutorial prompt:
     - `^0`: **Kratos** speaker portrait
     - `^1`: **Narrator / Tutorial** prompt (no speaker portrait)
     - `^2`: **Spartan Soldier** speaker portrait
     - `^3`: **Ceryx** (Messenger of Olympus) speaker portrait
     - `^4`: **Combo Attack** tutorial prompt (waits for Left Soft Key)
     - `^5`: **Blade of Artemis** unlock tutorial prompt (forces weapon switch)
     - `^6`: **Medusa's Gaze** unlock tutorial prompt (forces magic switch)
     - `^7`: **Army of Hades** unlock tutorial prompt (forces magic switch)

---

## 6. Hierarchical Animation Bank Format (`type = 247`, [`a.g(int)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L646-L785))

Resource type `247` (`0xF7`) encodes skeletal/modular sprite animations, root-motion velocity deltas, and combat hitboxes/hurtboxes. All 25 animation banks in `RP1`–`RP8`, `RP13`–`RP18`, and `RP28` use version `3`.

### 6.1 Variable-Length Integer Primitives

[`a.java`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L549-L561) reads compact values from the byte array `A[]` (cursor `B`) using two escape-coded readers:
* **Unsigned [`a.i()`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L549-L554)**:
  - Reads `u8 b = A[B++] & 0xFF`.
  - If `b != 255` (`0xFF`), returns `b` (`0..254`).
  - If `b == 255`, reads a big-endian `u16`: `((A[B++] & 0xFF) << 8) | (A[B++] & 0xFF)`.
* **Signed [`a.j()`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L556-L561)**:
  - Reads signed `i8 b = A[B++]`.
  - If `b != -128` (`0x80`), returns `b` (`-127..127`).
  - If `b == -128`, reads a big-endian signed `i16`: `(A[B++] << 8) | (A[B++] & 0xFF)`.

### 6.2 Helper Packing Routines

* **Count-to-Span Table Builder [`a.a(int[] table)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L626-L634)**:
  Reads `table.length` counts via `i()`, accumulating running sum `acc`:
  $$\text{table}[k] = (\text{acc} \ll 16) \mid \text{count}_k, \quad \text{acc} \mathrel{+}= \text{count}_k$$
  Returns total count `acc`.
* **32-Bit Reference + Offset Packer [`a.b(int[] table)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L636-L644)**:
  Given an `int[N]` array where bits `0..1` already hold 2-bit flags, reads three Structure-of-Arrays (SoA) streams of length `N`:
  1. **Stream 1 (`N` × `i()`)**: 10-bit unsigned index (`idx`), shifted into bits `22..31`: `table[n] |= idx << 22`.
  2. **Stream 2 (`N` × `j()`)**: 10-bit signed X offset/delta (`dx` in `-512..511`), masked and shifted into bits `12..21`: `table[n] |= (dx & 1023) << 12`.
  3. **Stream 3 (`N` × `j()`)**: 10-bit signed Y offset/delta (`dy` in `-512..511`), masked and shifted into bits `2..11`: `table[n] |= (dy & 1023) << 2`.

### 6.3 Complete Binary Stream Layout of `type = 247`

| Step | Reader | Target Array | Bit Packing & Semantics |
| :--- | :--- | :--- | :--- |
| **1. Header** | `i()` (`u8`) | `var2` | Version byte (always `3`). |
| **2. Anim Count** | `u16 BE` | `numAnims` | `((i() & 0xFF) << 8) \| (i() & 0xFF)` (note: uses raw bytes `A[B++]`). Allocates `C = new int[numAnims]`. |
| **3. Seq Frame Counts** | `a(C)` | `C[0..numAnims-1]` | Reads `numAnims` counts via `i()`. Returns `totalSeqFrames`.<br>• `C[a] >>> 16`: start index in `D[]`/`E[]`.<br>• `C[a] & 0x3FF`: number of keyframes in animation `a`. |
| **4. Loop Counts** | `numAnims` × `j()` | `C[0..numAnims-1]` | Sets bits `10..15`: `C[a] \|= (63 & j()) << 10`.<br>• Extracted as signed 6-bit int `C[a] << 16 >> 26`.<br>• `-1` (`63`) = infinite loop; `0` = play once and stop; `>0` = repeat `N` times. |
| **5. Keyframe Timing & Interp** | `totalSeqFrames` × `i()` | `D[]`, `E[]` | For each keyframe `f` (`0..totalSeqFrames-1`), reads `v = i()`:<br>• `D[f] = (byte) v`: bits `0..5` = index into Duration Table `K[]`; bits `6..7` = priority (`0..3`).<br>• `E[f] = v >> 8`: bits `0..1` = root-motion velocity interpolation mode (`0` = step on tick 0, `1` = linear per ms). |
| **6. Keyframe CompIdx & Root Motion** | `b(E)` | `E[0..totalSeqFrames-1]` | Reads 3 SoA streams across `totalSeqFrames`:<br>1. `compFrameIdx` (`i()`) → bits `22..31` (`E[f] >>> 22`).<br>2. `rootMotionDX` (`j()`) → bits `12..21` (`E[f] << 10 >> 22`, signed pixels).<br>3. `rootMotionDY` (`j()`) → bits `2..11` (`E[f] << 20 >> 22`, signed pixels). |
| **7. Comp Frame Count** | `u16 BE` | `numCompFrames` | Raw 2-byte big-endian count of unique composite frames. Allocates `G = new int[numCompFrames]`. |
| **8. Part Counts** | `a(G)` | `G[0..numCompFrames-1]` | Reads `numCompFrames` part counts via `i()`. Returns `totalParts`.<br>• `G[c] >>> 16`: start index in `H[]`.<br>• `G[c] & 0xFFFF`: number of sprite slices in composite frame `c`. |
| **9. Part Flip Flags** | `ceil(totalParts / 4)` × `i()` | `H[0..totalParts-1]` | Packs 4 parts' 2-bit flip flags (`0..3`) into each byte read via `i()`:<br>`H[p] = (byteVal >> ((p & 3) << 1)) & 3`. |
| **10. Part SliceIdx & Offsets** | `b(H)` | `H[0..totalParts-1]` | Reads 3 SoA streams across `totalParts`:<br>1. `sliceIdx` (`i()`) → bits `22..31` (`H[p] >>> 22`, index into `c[]`).<br>2. `offsetX` (`j()`) → bits `12..21` (`H[p] << 10 >> 22`, signed pixels).<br>3. `offsetY` (`j()`) → bits `2..11` (`H[p] << 20 >> 22`, signed pixels). |
| **11. Hitbox/Hurtbox Counts** | `numCompFrames` × `i()` | `I[0..numCompFrames-1]` | Reads box count per composite frame via `i()`, packing `(startBoxIdx << 16) \| boxCount` into `I[c]`. Returns `totalBoxes`. |
| **12. Box Coordinates & Flags** | 5 SoA streams of `totalBoxes` | `J[0..totalBoxes*2-1]` | Even indices `2*b` store `(x1 << 24) \| (y1 << 16) \| (x2 << 8) \| y2`; odd indices `2*b + 1` store `flags`:<br>1. `x1`: `totalBoxes` × `j()` (`(x1 & 0xFF) << 24`)<br>2. `y1`: `totalBoxes` × `j()` (`(y1 & 0xFF) << 16`)<br>3. `w`: `totalBoxes` × `i()`, stored as `x2 = x1 + w` (`(x2 & 0xFF) << 8`)<br>4. `h`: `totalBoxes` × `i()`, stored as `y2 = y1 + h` (`y2 & 0xFF`)<br>5. `flags`: `totalBoxes` × `i()` stored in `J[2*b + 1]`. |
| **13. Duration Table** | `u16 BE` + `numDurations` × `i()` | `K[0..numDurations-1]` | Raw 2-byte big-endian `numDurations`, followed by `numDurations` × `i()` frame durations (in milliseconds). |

---

## 7. Level Bitstream Format (`type = 245`, [`e.a(byte[], boolean)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L7536-L8193))

All 11 levels (`9216` Arena and `9217`, `19456`–`27648` Chapters 1–10) are compressed using an LSB-first variable-width bitstream read by [`e.m(int bits)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L8322-L8348) and [`e.n(int bits)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L8350-L8355).

### 7.1 LSB-First Bitstream Reader ([`e.m(int var0)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L8322-L8348))

State variables: byte array `jh[]`, byte cursor `ji`, current byte cache `jj`, remaining unread bits in current byte `jk` (initialized to `0`), and bitmask table `jl[b] = (1 << b) - 1`.
* Whenever `jk == 0`, loads `jj = jh[ji++] & 0xFF` and sets `jk = 8`.
* Extracts bits from the **least-significant bit (LSB)** of `jj` upward (`jj & jl[take]`, then `jj >>= take`), concatenating subsequent bytes at higher bit positions (`var1 |= ... << var2`).
* **Signed reader [`e.n(int var0)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L8350-L8355)**: Reads `v = m(var0)` and sign-extends bit `var0 - 1`:
  ```java
  if ((v & (1 << (var0 - 1))) != 0) v |= -1 << var0;
  ```
* **Min-bits helper [`e.o(int var0)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L8357-L8367)**: Returns the number of bits needed to represent `var0` (`1` if `var0 == 0`, else `floor(log2(var0)) + 1`).

### 7.2 Level Header & Tilemap Layout

Every level bitstream begins with the following header and tile grid ([`e.a(byte[], boolean)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L3342-L3422) and [`e.c(boolean)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L3564-L3576)):

| Order | Bit Width | Java Var | Description |
| :---: | :--- | :--- | :--- |
| `1` | `1` (`m(1)`) | — | Header start bit (`1`). |
| `2` | `10` (`m(10)`) | — | Header version / flags (`19`). |
| `3` | `10` (`m(10)`) | `R` | String index (`0..292`) in the localization table for the chapter intro text (`122` for Ch.1, `230` for Ch.6, `239` for Ch.10, `0` otherwise). |
| `4` | `1` (`m(1)`) | `hy` | Facing / horizon flag (`hy = m(1) != 1`). |
| `5` | `1` (`m(1)`) | `dE` | Enemy set A selector (`true` loads `18433`/`18432`, `false` loads `4097`/`4096`). |
| `6` | `1` (`m(1)`) | `dF` | Enemy set B selector (`true` loads `16385`/`16384`, `false` loads `17409`/`17408`). |
| `7` | `1` (`m(1)`) | — | Reserved header flag (`0`). |
| `8` | `16` (`m(16)`) | `hw` | Cumulative Red Orb cap unlocked at this chapter (`1250` up to `23750`). |
| `9` | `1` (`m(1)`) | — | Tile section separator bit. |
| `10` | `16` (`m(16)`) | `bA` | Number of unique tiles referenced by this level (`56` to `108`). |
| `11` | `4` (`m(4)`) | `bB` | Bit width per tile palette entry (typically `7`). |
| `12` | `bA × bB` | `var10[0..bA-1]` | Maps local tile index `0..bA-1` to global `16×16` tileset index in `2058` (`RP2`). |
| `13` | `16` (`m(16)`) | `jc` | Level width in `16×16` tiles (`41` to `232`). Pixel width `jf = jc * 16`. |
| `14` | `16` (`m(16)`) | `jd` | Level height in `16×16` tiles (`38` to `138`). Pixel height `jg = jd * 16`. |
| `15` | `(jc * jd) × l(bA)` | `ja[0..jc*jd-1]` | Row-major visual tile grid (`bC = l(bA)` bits per cell). Cell `(x, y)` displays tile `1 + var10[ja[y * jc + x] & 255]`. |
| `16` | `8` (`m(8)`) | — | Collision bitplane count header (`1`). |
| `17` | `bA × 1` | `jb[0..bA-1]` | [`e.b(int, boolean)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L3615-L3623): reads `bA` 1-bit flags into `jb[0..bA-1]`. |
| `18` | `10` (`m(10)`) | `bA` | Number of object/collision spawn groups in this level (`14` to `27`). |

### 7.3 Raster-Delta Object Group Encoding (`0..36`)

After reading the group count `bA = m(10)`, the bitstream contains `bA` object groups ([`e.a(byte[], boolean)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L3499-L3517)). Each group header has:
* `groupType = m(10)` (`var11`, values `0..36` identifying the entity or tile-collision brush class).
* `groupCount = m(16)` (`var14`, number of instances in this group).

#### Coordinate Encoding (`bD = l(jf * jg + 1)`)
To compress object coordinates, instances within each group are sorted in 1D pixel raster order (`pos = y * jf + x`, where `jf = jc * 16`).
* Before the instance loop, `var15 = 0`, `var16 = 0`, and `bD = l(jf * jg + 1)`.
* For each instance `i = 0 .. groupCount - 1`:
  ```java
  for (var15 += m(bD); var15 >= jf; ++var16) {
      var15 -= jf;
  }
  bw[i] = var15; // pixel X
  bx[i] = var16; // pixel Y
  ```

#### Per-Group Instance Payload Bit Schemas ([`e.d(int var1, int var2)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L2676-L3340))

| Group ID | Category | Additional Bits Read per Instance After `(bw[i], bx[i])` |
| :---: | :--- | :--- |
| **`0`** | **Horizontal Tile Flag `8192` Strip** | `len = m(10)`: stamps flag `8192` across `(x/16 .. x/16 + len, y/16)`. |
| **`3`** | **Camera Lock / Zone Point (`aF, aG`)** | `aI = m(1) == 1` (`1` bit); `aH = m(1) == 1` (`1` bit). |
| **`4`** | **Chest (`fx, fw`, Anim `5120`, Slice `5123`)** | `chestType = m(3)` (`3` bits: `0`=Green, `1`=Blue, `2`=Red, `3`=Gold/Eye/Feather); `param = m(10)` (`10` bits). |
| **`5`** | **Vertical Climb / Ladder Strip (`flag 8`)** | `len = m(10)`: stamps flag `8` down `(x/16, y/16 .. y/16 + len)`. |
| **`6`** | **Level Script Parameter Tables (`aZ, ba`)** | Reads `40 × m(10)` into `aZ[0..39]` and `40 × m(10)` into `ba[0..39]` (`800` bits total). |
| **`7`** | **Moving / Crumbling Platform (`fe`, Anim `2048`, Slice `2051`)** | `animIdx = m(6) << 1` (`6` bits); `fn = m(1) == 1` (`1` bit); `fo = m(1) == 1` (`1` bit). Stamps flag `1`. |
| **`8`** | **Grapple / Rope Swing Point (`cp, cq`, `flag 128`)** | `cr = m(10)` (`10` bits); `cs = m(3)` (`3` bits). Stamps flag `128`. |
| **`9`** | **Vertical Zipline / Wall Strip (`db`, `flag 256`)** | `len = m(10)` (`10` bits); `db = m(3)` (`3` bits). Stamps flag `256` down `0..len`. |
| **`10`** | **Horizontal Platform / Beam Strip (`flag 64`)** | `len = m(10)` (`10` bits). Stamps flag `64` across `0..len`. |
| **`11`** | **Enemy Spawn Slot 1 (`dG`: `18433` or `4097`)** | `eg = m(3)` (`3`); `ex = m(10) * 1000` (`10`); `isTarget = m(1) == 1` (`1`); `hp = m(16)` (`16`); `eq = m(16)` (`16`); `ep = m(10)` (`10`); `ec = m(10)` (`10`); `facingRight = m(1) != 1` (`1`); `ez = m(1) == 1` (`1`). (`68` bits/instance). |
| **`12`** | **Enemy Spawn Slot 2 (`dH`: `16385` Gorgon or `17409` Cerberus)** | Same `68` bits per instance as Group `11`. |
| **`13`** | **Enemy Spawn Slot 3 / Boss (`15361` Minotaur, `13313` Argos, `14337` Dead Rider)** | Same `68` bits as Group `11` + `isArgos = m(1) == 1` (`1` bit) + `isDeadRider = m(1) == 1` (`1` bit) (`70` bits/instance). |
| **`14`** | **Enemy Wave Spawner & Barrier (`eG, eH`, `flag 4`)** | `barrierHeight = m(10)` (`10` bits), followed by `15` wave entries each reading `eI = m(10)`, `eJ = m(10)`, `eK = m(1) != 1` (`10 + 15 × 21 = 325` bits/instance). |
| **`17`** | **Interactive Prop (`cj`, Anim `3072`, Slice `3075`)** | `animIdx = m(6)` (`6` bits). |
| **`18`** | **Horizontal Hazard Strip (`flag 16`)** | `len = m(10)` (`10` bits). Stamps flag `16` across `0..len`. |
| **`21`** | **Flame / VFX Emitter (`dm`, Anim `5121`, Slice `5124`)** | `animIdx = m(6) << 1` (`6` bits). |
| **`22`** | **4-Way Cross Solid Tile Brush (`flag 1`)** | `flag = m(1)` (`1` bit); `up = m(10)`; `down = m(10)`; `left = m(10)`; `right = m(10)` (`41` bits/instance). |
| **`23`** | **Static Background/Foreground Stamp (`eM`, Anim `3073`, Slice `3076`)** | Read in reverse order (`var2 - 1` down to `0`): `stampAnimIdx = m(8)` (`8` bits); `layer = m(3)` (`3` bits, `0..7`). |
| **`25`** | **Single-Tile Flag `1024` Marker** | `0` extra bits; stamps flag `1024` at `(x/16, y/16)`. |
| **`26`** | **Kratos Player Spawn (`ga, gb`)** | `0` extra bits; sets Kratos initial position `(bw[0], bx[0])`. |
| **`27`** | **Pushable Box / Statue (`eR`, Anim `6144`, Slice `6146`)** | `animIdx = m(3)` (`3` bits); `climbable = m(1) == 1` (`1` bit). Stamps flags `1`, `512`, and optionally `8`. |
| **`28`** | **Cutscene / Dialogue / Script Trigger (`de, df`, `flag 32`)** | `wTiles = m(10)` (`10`); `hTiles = m(10)` (`10`); `triggerType = m(3)` (`3`); `param = m(10)` (`10`). Stamps flag `32`. |
| **`29`** | **Horizontal Tile Flag `16384` Strip** | `len = m(10)` (`10` bits). Stamps flag `16384` across `0..len`. |
| **`30`** | **Slope / Stair Tile Flag Marker (`2048` / `4096`)** | `is2048 = m(1) != 0` (`1` bit): stamps `2048` if `1`, else `4096`. |
| **`31`** | **Single-Tile Solid Flag `1` Marker** | `flag = m(1)` (`1` bit): stamps `flag` at `(x/16, y/16)`. |
| **`32`** | **Magic / Environmental FX (`bS`, Anim `5122`, Slice `5125`)** | `animIdx = m(6) << 1` (`6` bits); `bZ = m(1) == 1` (`1` bit). |
| **`33`** | **Pendulum / Moving Trap (`dv`, Anim `6145`, Slice `6147`)** | `animIdx = m(6)` (`6` bits); `dw = m(10)` (`10` bits); `phaseMs = m(3) * 100` (`3` bits). |
| **`34`** | **Lever / Switch (`cC, cD`, Anim `2050`, Slice `2053`)** | `cE = m(1) == 1` (`1` bit); `targets[0..3] = 4 × m(3)` (`12` bits). |
| **`35`** | **Door / Gate / Soul Barrier (`cQ, cR`, Anim `2049`, Slice `2052`)** | `doorType = m(3)` (`3` bits); `cY = m(10)` (`10` bits); `startOpen = m(1) == 1` (`1` bit); `da = m(1) == 1` (`1` bit). |
| **`36`** | **Animated Torch / Prop (`cc`, Anim `3074`, Slice `3077`)** | `animIdx = m(6)` (`6` bits). |

---

## 8. Other Specialized Binary Formats

### 8.1 Menu State Table (`1028`, `type = 255`, `128` bytes)
Loaded in [`e.l(int)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L7283-L7299) into `iM[32]`:
* Consists of `32` big-endian 32-bit integers (`32 × 4 = 128` bytes).
* Each `iM[state]` encodes menu layout flags, title string ID, softkey labels, and item count for menu state `state` (`0..31`).

### 8.2 Procedural Fire Palette (`1053`, `type = 251`, `768` bytes)
Loaded in [`e.t(int)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L8590-L8606) into `iD[256]`:
* 256 raw RGB triplets (`256 × 3 = 768` bytes).
* Packed into `0xFFRRGGBB` 32-bit ARGB integers in `iD[0..255]` to colorize the `176×64` cellular-automata fire buffer `iB[]` rendered behind the Main Menu.

### 8.3 RMS Save Game Record (`"GodOfWar"`, `76` bytes)
Managed by [`e.w()`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L8447-L8468), [`e.x()`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L8470-L8536), and [`e.y()`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L8538-L8588) inside `iu[0..75]`:
* Stores unlocked chapter progression, current checkpoint state, Kratos's max HP (`ca`), max Magic (`cm`), Gorgon Eyes (`cb`), Phoenix Feathers (`co`), Red Orbs (`cp`), cumulative Red Orbs (`cq`), weapon/magic upgrade levels (`cr[0..3]`), Arena high score (`bI`), and audio preferences.
