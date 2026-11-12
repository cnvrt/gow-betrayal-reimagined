# God of War: Betrayal (2007 J2ME) — Complete Asset Inventory

This document catalogs every binary resource inside the original *God of War: Betrayal* JAR (`i.png` and `/RP1` through `/RP33`). Note that `/RP11` and `/RP31` do not exist in the original JAR distribution; all 31 existing `/RP*` containers contain **197 total resource entries** (`668,786` bytes of uncompressed payload), plus `1` standalone root icon `/i.png` (`1,785` bytes) for **198 total assets**.

### Resource Count Reconciliation (`183` vs `197`)
Summing the `Entries` column across all 31 `/RP*` containers yields **197** (`41 + 23 + 18 + 6 + 25 + 12 + 13 + 3 + 2 + 1 + 1 + 6 + 3 + 6 + 6 + 6 + 6 + 9×1 + 6 + 4×1 = 197`). The earlier `183` figure in the Phase 1 summary prose was a manual summation typo that undercounted PNGs as `28` instead of `34` (each of the `34` `type = 254` slice tables has its own `type = 253` PNG), undercounted palettes as `56` instead of `75` (`1` in `RP1`, `12` in `RP2`, `9` in `RP3`, `3` in `RP4`, `16` in `RP5`, `6` in `RP6`, `10` in `RP7`, and `3×6 = 18` in `RP13,15,16,17,18,28`), and omitted the single `type = 255` menu table (`1028`).

| Type Tag (`u8`) | Signed (`i8`) | Resource Format | Exact Count | Total Bytes |
| :---: | :---: | :--- | :---: | :---: |
| `0` | `0` | Standard MIDI (`audio/midi`, `1063`) | `1` | `6,274` |
| `1` | `1` | RIFF WAVE PCM (`audio/x-wav`, `1054`–`1062`, `1064`) | `10` | `284,338` |
| `245` | `-11` | Bitstream-Compressed Level Data (`9216`, `9217`, `19456`–`27648`) | `11` | `110,267` |
| `247` | `-9` | Hierarchical Animation Banks (`1024`–`28672`) | `25` | `182,916` |
| `250` | `-6` | UTF-8 Localization String Tables (`6` languages × `293` strings) | `6` | `75,693` |
| `251` | `-5` | Palettes (`1` fire RGB table `1053` + `74` PNG `PLTE+CRC32` chunks) | `75` | `15,436` |
| `253` | `-3` | Indexed-Color PNG Images (`bitDepth=8, colorType=3`) | `34` | `117,550` |
| `254` | `-2` | Sprite Slice & Bitmap Font Tables (`31` sprite slice sets + `3` fonts) | `34` | `16,184` |
| `255` | `-1` | Menu State Table (`1028`) | `1` | `128` |
| **Total (`/RP1`–`/RP33`)** | — | **All 31 `/RP*` Containers** | **`197`** | **`668,786`** |
| *(JAR Root)* | — | Standalone MIDlet Icon `/i.png` (`16×16` RGBA PNG) | `1` | `1,785` |

---

## 1. Summary of Resource Containers (`/RP1`–`/RP33`)

| Container | Base ID (`pack << 10`) | Header Byte | Size Width | Entries | Payload Size (Bytes) | Primary Contents |
| :--- | :---: | :---: | :---: | :---: | :---: | :--- |
| **`/RP1`** | `1024` (`0x0400`) | `65` (`0x41`) | `4` bytes | `41` | `332,526` | UI, HUD, 3 Bitmap Fonts, Menu Logos, Fire Palette, 11 Audio Tracks/SFX |
| **`/RP2`** | `2048` (`0x0800`) | `64` (`0x40`) | `2` bytes | `23` | `35,809` | Level Tileset (`2058`, 128 tiles), Parallax Strip (`2056`), Interactive Props (`2055`, `2057`), 12 Chapter Palettes |
| **`/RP3`** | `3072` (`0x0C00`) | `64` (`0x40`) | `2` bytes | `18` | `35,407` | Static Background Stamps (`3079`, 224 slices), Gates/Chests (`3078`), Animated Torches/Hazards (`3080`), 9 Chapter Palettes |
| **`/RP4`** | `4096` (`0x1000`) | `64` (`0x40`) | `2` bytes | `6` | `18,319` | Spartan Soldier / Basic Enemy Sprite Sheet (`4098`), AnimBank (`4096`), 3 Palettes |
| **`/RP5`** | `5120` (`0x1400`) | `64` (`0x40`) | `2` bytes | `25` | `22,150` | Orbs, Weapons/Magic VFX (`5126`, `5127`, `5128`), AnimBanks (`5120`–`5122`), 16 Palettes |
| **`/RP6`** | `6144` (`0x1800`) | `64` (`0x40`) | `2` bytes | `12` | `18,097` | Undead / Skeleton Enemy & Projectile Sprite Sheets (`6148`, `6149`), AnimBanks (`6144`, `6145`), 6 Palettes |
| **`/RP7`** | `7168` (`0x1C00`) | `64` (`0x40`) | `2` bytes | `13` | `8,429` | Particle / Gore / Magic Projectile Sprite Sheet (`7170`), AnimBank (`7168`), 10 Palettes |
| **`/RP8`** | `8192` (`0x2000`) | `64` (`0x40`) | `2` bytes | `3` | `10,759` | Sony / WayForward / Javaground Splash Logos (`8194`), Slice (`8193`), AnimBank (`8192`) |
| **`/RP9`** | `9216` (`0x2400`) | `64` (`0x40`) | `2` bytes | `2` | `16,919` | Level Bitstreams: **Arena** (`9216`) and **Chapter 1: For Sparta** (`9217`) |
| **`/RP10`** | `10240` (`0x2800`) | `64` (`0x40`) | `2` bytes | `1` | `11,639` | **English** Localization String Table (`293` strings) |
| **`/RP12`** | `12288` (`0x3000`) | `64` (`0x40`) | `2` bytes | `1` | `12,661` | **French** (`Français`) Localization String Table (`293` strings) |
| **`/RP13`** | `13312` (`0x3400`) | `64` (`0x40`) | `2` bytes | `6` | `26,217` | **Argos** (Giant Boss) Sprite Sheet (`13314`), AnimBank (`13312`), 3 Palettes |
| **`/RP14`** | `14336` (`0x3800`) | `64` (`0x40`) | `2` bytes | `3` | `15,422` | **Dead Rider / Assassin** Sprite Sheet (`14338`), Slice (`14337`), AnimBank (`14336`) |
| **`/RP15`** | `15360` (`0x3C00`) | `64` (`0x40`) | `2` bytes | `6` | `24,560` | **Minotaur** Sprite Sheet (`15362`), AnimBank (`15360`), 3 Palettes |
| **`/RP16`** | `16384` (`0x4000`) | `64` (`0x40`) | `2` bytes | `6` | `9,928` | **Gorgon / Beast** Sprite Sheet (`16386`), AnimBank (`16384`), 3 Palettes |
| **`/RP17`** | `17408` (`0x4400`) | `64` (`0x40`) | `2` bytes | `6` | `20,255` | **Cerberus** (3-Headed Hound Boss) Sprite Sheet (`17410`), AnimBank (`17408`), 3 Palettes |
| **`/RP18`** | `18432` (`0x4800`) | `64` (`0x40`) | `2` bytes | `6` | `14,971` | **Ceryx** (Messenger of Olympus Boss) / Legionnaire Sprite Sheet (`18434`), AnimBank (`18432`), 3 Palettes |
| **`/RP19`** | `19456` (`0x4C00`) | `64` (`0x40`) | `2` bytes | `1` | `13,159` | Level Bitstream: **Chapter 2: Dead Rider** (`19456`) |
| **`/RP20`** | `20480` (`0x5000`) | `64` (`0x40`) | `2` bytes | `1` | `10,538` | Level Bitstream: **Chapter 3: Minotaur** (`20480`) |
| **`/RP21`** | `21504` (`0x5400`) | `64` (`0x40`) | `2` bytes | `1` | `11,349` | Level Bitstream: **Chapter 4: Garden** (`21504`) |
| **`/RP22`** | `22528` (`0x5800`) | `64` (`0x40`) | `2` bytes | `1` | `8,345` | Level Bitstream: **Chapter 5: The Beast** (`22528`) |
| **`/RP23`** | `23552` (`0x5C00`) | `64` (`0x40`) | `2` bytes | `1` | `10,841` | Level Bitstream: **Chapter 6: Ruins** (`23552`) |
| **`/RP24`** | `24576` (`0x6000`) | `64` (`0x40`) | `2` bytes | `1` | `13,573` | Level Bitstream: **Chapter 7: Catacombs** (`24576`) |
| **`/RP25`** | `25600` (`0x6400`) | `64` (`0x40`) | `2` bytes | `1` | `11,324` | Level Bitstream: **Chapter 8: Labyrinth** (`25600`) |
| **`/RP26`** | `26624` (`0x6800`) | `64` (`0x40`) | `2` bytes | `1` | `9,087` | Level Bitstream: **Chapter 9: The Tower** (`26624`) |
| **`/RP27`** | `27648` (`0x6C00`) | `64` (`0x40`) | `2` bytes | `1` | `5,132` | Level Bitstream: **Chapter 10: Messenger** (`27648`) |
| **`/RP28`** | `28672` (`0x7000`) | `64` (`0x40`) | `2` bytes | `6` | `68,646` | **Kratos** (Player Character) Sprite Sheet (`28674`), Slice (`28673`), AnimBank (`28672`), 3 Palettes |
| **`/RP29`** | `29696` (`0x7400`) | `64` (`0x40`) | `2` bytes | `1` | `13,712` | **German** (`Deutsch`) Localization String Table (`293` strings) |
| **`/RP30`** | `30720` (`0x7800`) | `64` (`0x40`) | `2` bytes | `1` | `12,431` | **Italian** (`Italiano`) Localization String Table (`293` strings) |
| **`/RP32`** | `32768` (`0x8000`) | `64` (`0x40`) | `2` bytes | `1` | `12,798` | **Portuguese** (`Português`) Localization String Table (`293` strings) |
| **`/RP33`** | `33792` (`0x8400`) | `64` (`0x40`) | `2` bytes | `1` | `12,452` | **Spanish** (`Español`) Localization String Table (`293` strings) |

---

## 2. PNG Sprite Sheets, Tilesets, Slice Tables & Animation Banks

In addition to `/i.png` (`16×16` MIDlet application icon in the JAR root), there are **28 indexed-color PNG images** (`type = 253`), **34 Sprite Slice / Font Tables** (`type = 254`), and **25 Animation Banks** (`type = 247`):

### 2.1 UI, HUD, Fonts & Splash Graphics (`RP1` & `RP8`)

| Pack | PNG ID | Dimensions | Size (B) | Slice ID | Slices | Flags | AnimBank ID | Anims / CompFrames / Parts | Role in `e.java` |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| `RP1` | `1041` | `5×20` | `198` | `1029` | `4` | `0x87FC` | — | — | Menu border / scrollbar segments (`b.s`) |
| `RP1` | `1042` | `18×18` | `543` | `1030` | `1` | `0x87E4` | `1024` | `1` / `1` / `15` | UI ornament / frame corner |
| `RP1` | `1043` | `141×52` | `3,268` | `1031` | `150` | `0xFFF8` | — | — | **Bitmap Font 0**: Primary UI / Dialogue Font (`146` glyphs) |
| `RP1` | `1044` | `84×107` | `2,604` | `1032` | `127` | `0xFFF8` | — | — | **Bitmap Font 1**: Highlighted / Menu Title Font (`123` glyphs) |
| `RP1` | `1045` | `67×83` | `2,517` | `1033` | `61` | `0xFFF8` | — | — | **Bitmap Font 2**: HUD Numeric / Red Orb / Hit Counter Font (`57` glyphs) |
| `RP1` | `1046` | `14×28` | `399` | `1034` | `16` | `0x87FC` | — | — | QTE / Interaction Arrow Indicator icons (`hZ`) |
| `RP1` | `1047` | `23×91` | `2,652` | `1035` | `9` | `0x87FC` | — | — | Speaker Portraits (Kratos, Spartan, Ceryx) & Weapon/Magic HUD icons (`ia`) |
| `RP1` | `1048` | `133×110` | `5,322` | `1036` | `60` | `0x87A4` | `1025` | `5` / `19` / `1425` | *God of War: Betrayal* Main Menu Logo & Animated Title (`iG`) |
| `RP1` | `1049` | `109×70` | `2,437` | `1037` | `2` | `0x87FC` | `1026` | `2` / `2` / `21` | Top-Left HUD Blade Emblem (Health/Magic Meter Frame) (`hW`) |
| `RP1` | `1050` | `88×41` | `1,519` | `1038` | `21` | `0x87FC` | — | — | Power-Up Menu Bars, Upgrade Orbs & UI Icons (`hY`) |
| `RP1` | `1051` | `266×75` | `7,084` | `1039` | `5` | `0x879C` | — | — | Menu Background Banner / Stone Plaque (`iH`) |
| `RP1` | `1052` | `82×21` | `933` | `1040` | `26` | `0x07E4` | `1027` | `11` / `18` / `110` | Animated HUD Effects / Context Prompts (`hX`) |
| `RP8` | `8194` | `310×93` | `10,438` | `8193` | `23` | `0x877C` | `8192` | `2` / `9` / `23` | Sony Pictures Mobile & WayForward Splash Logos (`iG`) |

### 2.2 Environment Tilesets, Stamps, Props & VFX (`RP2`, `RP3`, `RP5`, `RP7`)

| Pack | PNG ID | Dimensions | Size (B) | Slice ID | Slices | AnimBank ID | Anims / CompFrames / Parts / Boxes | Palette IDs (`0, 1, 2`) | Role in `e.java` |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| `RP2` | `2055` | `55×59` | `1,647` | `2051` | `17` | `2048` | `8` / `6` / `125` / `0` | `2059, 2060, 2061` (`100` B) | Moving / Crumbling Platforms & Balance Beam (`hI`) |
| `RP2` | `2056` | `39×169` | `1,555` | `2052` | `60` | `2049` | `40` / `63` / `2677` / `0` | `2062, 2063, 2064` (`52` B) | Distant Horizon / Sky Parallax Backdrop (`hJ`) |
| `RP2` | `2057` | `26×35` | `482` | `2053` | `16` | `2050` | `8` / `5` / `35` / `0` | `2065, 2066, 2067` (`40` B) | Ropes, Grapple Swing Points & Levers (`hK`) |
| `RP2` | `2058` | `128×256` | `17,075` | `2054` | `128` | — | — (`16×16` tiles) | `2068, 2069, 2070` (`772` B) | **Master Level Tileset** (`128` `16×16` tiles, `hz`) |
| `RP3` | `3078` | `51×109` | `2,182` | `3075` | `39` | `3072` | `37` / `21` / `410` / `0` | `3081, 3082, 3083` (`100` B) | Pushable Boxes, Statues, Doors, Soul Gates & Chests (`hL`) |
| `RP3` | `3079` | `299×112` | `15,571` | `3076` | `224` | `3073` | `235` / `221` / `1313` / `0` | `3084, 3085, 3086` (`196` B) | **Static Background & Foreground Level Stamps** (`hM`) |
| `RP3` | `3080` | `57×108` | `2,213` | `3077` | `54` | `3074` | `25` / `80` / `764` / `0` | `3087, 3088, 3089` (`118` B) | Animated Torches, Flame Jets, Spike & Pendulum Traps (`hN`) |
| `RP5` | `5126` | `79×27` | `1,223` | `5123` | `33` | `5120` | `12` / `12` / `96` / `0` | `5129, 5130, 5131` (`127` B) | Orbs (Health, Magic, Red), Save Light Beam (`hE`) |
| `RP5` | `5127` | `48×43` | `1,221` | `5124` | `72` | `5121` | `22` / `89` / `1412` / `19` | `5132..5134` (`100` B), `5135..5141` (`769` B) | Blade Plumes, Slash Trails & Hit Sparks (`hF`) |
| `RP5` | `5128` | `218×34` | `3,001` | `5125` | `103` | `5122` | `28` / `74` / `776` / `7` | `5142, 5143, 5144` (`100` B) | Magic VFX (Medusa Beam, Army of Hades Souls) (`hG`) |
| `RP7` | `7170` | `15×18` | `261` | `7169` | `30` | `7168` | `32` / `142` / `1931` / `0` | `7171`–`7180` (`31` B each) | Blood Particles, Stone Debris & Gore Splatter (`hH`) |

### 2.3 Kratos, Enemies & Bosses (`RP4`, `RP6`, `RP13`–`RP18`, `RP28`)

| Pack | PNG ID | Dimensions | Size (B) | Slice ID | Slices | AnimBank ID | Anims | SeqFrames | CompFrames | Parts | Hit/Hurt Boxes | Palette IDs | Character / Entity |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :--- | :--- |
| **`RP28`** | `28674` | `258×55` | `6,709` | `28673` | `279` | `28672` | **`122`** | `1,051` | `699` | `15,019` | **`189`** | `28675`–`28677` (`100` B) | **Kratos (Player Character)** (`hA`) |
| **`RP4`** | `4098` | `155×33` | `2,062` | `4097` | `125` | `4096` | **`28`** | `245` | `189` | `3,714` | **`336`** | `4099`–`4101` (`67` B) | **Spartan Allies & Basic Melee Soldiers** (`hB`) |
| **`RP6`** | `6148` | `37×125` | `2,422` | `6146` | `23` | `6144` | **`9`** | `55` | `3` | `32` | **`0`** | `6150`–`6152` (`100` B) | **Enemy Projectiles / Traps / Shields** (`hC`) |
| **`RP6`** | `6149` | `58×103` | `2,987` | `6147` | `52` | `6145` | **`20`** | `204` | `151` | `2,449` | **`125`** | `6153`–`6155` (`100` B) | **Undead Legionnaires / Skeletons** (`hD`) |
| **`RP13`** | `13314` | `150×88` | `5,568` | `13313` | `194` | `13312` | **`17`** | `206` | `139` | `5,256` | **`120`** | `13315`–`13317` (`133` B) | **Argos** (Giant Multi-Eyed Boss, Ch. 1, 3, 5) |
| **`RP14`** | `14338` | `96×120` | `4,515` | `14337` | `136` | `14336` | **`18`** | `165` | `107` | `2,628` | **`109`** | — (Uses embedded PNG palette) | **Dead Rider / Mysterious Assassin** (Ch. 2, 6) |
| **`RP15`** | `15362` | `86×129` | `5,852` | `15361` | `256` | `15360` | **`19`** | `173` | `132` | `4,392` | **`131`** | `15363`–`15365` (`349` B) | **Minotaur** (Heavy Brute Enemy, Ch. 3+) |
| **`RP16`** | `16386` | `131×76` | `3,061` | `16385` | `63` | `16384` | **`15`** | `120` | `85` | `1,494` | **`92`** | `16387`–`16389` (`139` B) | **Gorgon / Beast** (Petrifying Snake Enemy, Ch. 4+) |
| **`RP17`** | `17410` | `157×64` | `3,976` | `17409` | `195` | `17408` | **`15`** | `138` | `99` | `4,135` | **`81`** | `17411`–`17413` (`139` B) | **Cerberus** (3-Headed Underworld Boss, Ch. 6–8) |
| **`RP18`** | `18434` | `178×48` | `3,693` | `18433` | `211` | `18432` | **`28`** | `176` | `117` | `2,499` | **`111`** | `18435`–`18437` (`67` B) | **Ceryx** (Messenger of Olympus Final Boss, Ch. 10) & Elite Guard |

---

## 3. Level Inventory (`type = 245`)

All 11 levels (`9216` Arena + 10 campaign chapters) decode with 100% bit-level accuracy:

| Chapter / Mode | Level Res ID | Pack | Title (English String) | Tile Grid (`jc × jd`) | Pixel Size (`jf × jg`) | Unique Tiles | Palette Variant (`bK`) | Intro String ID | Flags (`hy, dE, dF`) | Max Red Orbs (`hs`) | Kratos Spawn `(x, y)` | Object Groups (`bA`) |
| :--- | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Arena Mode** | `9216` | `RP9` | `[53]` *"Arena"* | `86 × 38` | `1376 × 608` | `56` | `-1` (Default) | `0` | `T, F, F` | `1,250` | `(624, 272)` | `14` |
| **Chapter 1** | `9217` | `RP9` | `[54]` *"For Sparta"* | `232 × 39` | `3712 × 624` | `107` | `-1` (Default) | `122` | `T, T, F` | `1,250` | `(2896, 144)` | `27` |
| **Chapter 2** | `19456` | `RP19` | `[55]` *"Dead Rider"* | `204 × 46` | `3264 × 736` | `108` | `-1` (Default) | `0` | `T, T, F` | `2,750` | `(224, 160)` | `25` |
| **Chapter 3** | `20480` | `RP20` | `[56]` *"Minotaur"* | `112 × 59` | `1792 × 944` | `101` | `-1` (Default) | `0` | `F, T, F` | `4,500` | `(1232, 128)` | `27` |
| **Chapter 4** | `21504` | `RP21` | `[57]` *"Garden"* | `113 × 67` | `1808 × 1072` | `87` | `2` (Golden/Sunset) | `0` | `F, T, F` | `6,500` | `(1088, 176)` | `20` |
| **Chapter 5** | `22528` | `RP22` | `[58]` *"The Beast"* | `139 × 43` | `2224 × 688` | `93` | `0` (Night/Blue) | `0` | `T, T, F` | `8,750` | `(256, 352)` | `27` |
| **Chapter 6** | `23552` | `RP23` | `[59]` *"Ruins"* | `178 × 38` | `2848 × 608` | `101` | `1` (Deep Cyan) | `230` | `T, F, T` | `11,250` | `(1856, 112)` | `25` |
| **Chapter 7** | `24576` | `RP24` | `[60]` *"Catacombs"* | `116 × 74` | `1856 × 1184` | `74` | `1` (Deep Cyan) | `0` | `T, F, T` | `14,000` | `(1344, 144)` | `23` |
| **Chapter 8** | `25600` | `RP25` | `[61]` *"Labyrinth"* | `113 × 72` | `1808 × 1152` | `92` | `0` (Night/Blue) | `0` | `F, F, T` | `17,000` | `(464, 0)` | `23` |
| **Chapter 9** | `26624` | `RP26` | `[62]` *"The Tower"* | `41 × 138` | `656 × 2208` | `65` | `2` (Sepia/Earth) | `0` | `F, F, T` | `20,250` | `(112, 160)` | `24` |
| **Chapter 10** | `27648` | `RP27` | `[63]` *"Messenger"* | `54 × 57` | `864 × 912` | `72` | `2` (Sepia/Earth) | `239` | `F, F, T` | `23,750` | `(112, 160)` | `22` |

---

## 4. Audio Inventory (`RP1`, `1054`–`1064`)

All 11 audio resources reside in `/RP1` (entries `30`–`40`):

| Res ID | Entry | Type Tag | Format | Channels | Bit Depth | Sample Rate | Samples / Tracks | Duration | Size (Bytes) | Usage in Original Code & Migration Plan |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| **`1054`** | `30` | `1` | RIFF WAV PCM | Mono (`1`) | `16-bit` | `44,100 Hz` | `20,828` | `0.472 s` | `41,700` | Blade slash / whip whoosh SFX 1 (packed in `RP1`, slot unused in J2ME `d.k` due to heap limit; **recoverable** for web port) |
| **`1055`** | `31` | `1` | RIFF WAV PCM | Mono (`1`) | `16-bit` | `44,100 Hz` | `20,762` | `0.471 s` | `41,568` | Blade slash / whip whoosh SFX 2 (**recoverable**) |
| **`1056`** | `32` | `1` | RIFF WAV PCM | Mono (`1`) | `16-bit` | `44,100 Hz` | `20,762` | `0.471 s` | `41,568` | Heavy impact / hit SFX 3 (**recoverable**) |
| **`1057`** | `33` | `1` | RIFF WAV PCM | Mono (`1`) | `16-bit` | `44,100 Hz` | `20,762` | `0.471 s` | `41,568` | Enemy hit / flesh slash SFX 4 (**recoverable**) |
| **`1058`** | `34` | `1` | RIFF WAV PCM | Mono (`1`) | `16-bit` | `8,000 Hz` | `3,426` | `0.428 s` | `6,896` | Grunt / damage vocal SFX 5 (**recoverable**) |
| **`1059`** | `35` | `1` | RIFF WAV PCM | Mono (`1`) | `16-bit` | `8,000 Hz` | `3,227` | `0.403 s` | `6,498` | Magic / orb pickup / chest SFX 6 (**recoverable**) |
| **`1060`** | `36` | `1` | RIFF WAV PCM | Mono (`1`) | `16-bit` | `8,000 Hz` | `1,271` | `0.159 s` | `2,586` | UI cursor move / menu tick SFX 7 (**recoverable**) |
| **`1061`** | `37` | `1` | RIFF WAV PCM | Mono (`1`) | `16-bit` | `8,000 Hz` | `1,506` | `0.188 s` | `3,056` | UI confirm / block clang SFX 8 (**recoverable**) |
| **`1062`** | `38` | `1` | RIFF WAV PCM | Mono (`1`) | `16-bit` | `44,100 Hz` | `35,314` | `0.801 s` | `70,672` | **Active Slot `2`**: QTE Finisher / Boss Kill Fanfare (`d.a(2, 1)`) |
| **`1063`** | `39` | `0` | Standard MIDI | SMF Type 1 | — | `960 PPQN` | `11` tracks | Loop | `6,274` | **Active Slot `0`**: Main Menu & Orchestral Title Theme (`d.a(0, 0)`) |
| **`1064`** | `40` | `1` | RIFF WAV PCM | Mono (`1`) | `16-bit` | `8,000 Hz` | `14,048` | `1.756 s` | `28,226` | **Active Slot `1`**: Level Intro Horn / Chest Open Stinger (`d.a(1, 1)`) |

---

## 5. Localization String Packs (`type = 250`)

All 6 language packs contain `293` UTF-8 strings (`0..292`) indexed identically:

| Language Index | Language Name | Pack | Resource ID | Size (Bytes) | String Count |
| :---: | :--- | :---: | :---: | :---: | :---: |
| `0` | **Português** (Portuguese) | `/RP32` | `32768` (`0x8000`) | `12,798` | `293` |
| `1` | **Deutsch** (German) | `/RP29` | `29696` (`0x7400`) | `13,712` | `293` |
| `2` | **Español** (Spanish) | `/RP33` | `33792` (`0x8400`) | `12,452` | `293` |
| `3` | **Italiano** (Italian) | `/RP30` | `30720` (`0x7800`) | `12,431` | `293` |
| `4` | **English** (Default) | `/RP10` | `10240` (`0x2800`) | `11,639` | `293` |
| `5` | **Français** (French) | `/RP12` | `12288` (`0x3000`) | `12,661` | `293` |
