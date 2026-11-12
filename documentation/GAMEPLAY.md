# God of War: Betrayal — Gameplay, Physics & Combat Mechanics

## 1. Input Mapping & Control Scheme

[`a.java`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/a.java#L74-L91) maps J2ME hardware key codes via lookup table `O[0..31]` into bitmasks `g` (held keys) and `h` (newly pressed keys), which [`e.i()`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L550-L591) and [`e.j()`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L593-L630) translate into logical action codes `this.s` (just pressed this tick) and `this.t` (held this tick):

| Logical Code (`s` / `t`) | J2ME Key(s) | Bitmask in `g` / `h` | Gameplay Action |
| ---: | :--- | :--- | :--- |
| `1` | `UP` (`-1`) or `NUM2` (`50`) | `0x040002` (`262146`) | **Jump / Double Jump / Climb Up / Balance Beam Jump / Pull Lever** |
| `6` | `DOWN` (`-2`) or `NUM8` (`56`) | `0x1000040` (`16777280`) | **Block (Parry Stance) / Grab Weakened Enemy / Open Chest / Pull Lever / Drop from Ledge or Rope / Climb Down** |
| `2` | `LEFT` (`-3`) or `NUM4` (`52`) | `0x100004` (`1048580`) | **Run Left / Swim Left / Kick or Pull Block Left / QTE Left** |
| `5` | `RIGHT` (`-4`) or `NUM6` (`54`) | `0x400020` (`4194336`) | **Run Right / Swim Right / Kick or Pull Block Right / QTE Right** |
| `8` | `FIRE` (`-5`) or `NUM5` (`53`) | `0x200100` (`2097408`) | **Attack / Combo Attack / Air Combo / Cast Magic / QTE Center** |
| `10` / `27` | `*` (`42`) or `LSK` (`-6`) | `0x400` / `0x8000000` | **Cycle Equipped Weapon & Magic** (`fQ = 0..3`) / Menu Select |
| `3` | `#` (`35`) | `0x08` (`8`) | **Open Power-Up (Upgrade) Menu** (`J = 106`) |
| `29` | `RSK` (`-7`) | `0x24000000` | **Pause Menu** (`J = 102`) / Back / Skip Cutscene Text |
| `17` | `NUM1` (`49`) | `0x20000` (`131072`) | Up-Left diagonal |
| `19` | `NUM3` (`51`) | `0x80000` (`524288`) | Up-Right diagonal (also 1-hit kill cheat toggle when `ENABLECHEATS=1`) |
| `23` | `NUM7` (`55`) | `0x800000` (`8388608`) | Down-Left (cheat: instant death / `-20` orbs when `ENABLECHEATS=1`) |
| `25` | `NUM9` (`57`) | `0x2000000` (`33554432`) | Down-Right (cheat: heal full HP / `+20` orbs when `ENABLECHEATS=1`) |
| `16` | `NUM0` (`48`) | `0x10000` (`65536`) | Cheat: Invincibility toggle (`D = !D`) / Unlock all chapters in menu when `ENABLECHEATS=1` |

---

## 2. Tile Collision Grid & Environmental Flags

Each level has a tile grid of `jc × jd` cells (`16 × 16` pixels per cell). Collision and trigger flags are stored in `short[] jb` (16-bit bitmask per cell), queried via [`e.h(int x, int y)`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L4418-L4420) (returns `65` = `SOLID | INSTANT_DEATH` when out of bounds) and modified via `b(x, y, mask)` (set bits) and `c(x, y, mask)` (clear bits).

| Bitmask | Decimal | Hex | Meaning & Gameplay Behavior |
| :--- | ---: | :--- | :--- |
| `1 << 0` | `1` | `0x0001` | **Solid Wall / Floor (`SOLID`)**: Blocks horizontal and vertical movement for Kratos and enemies; supports ledge grabbing on top corners. |
| `1 << 1` | `2` | `0x0002` | **Enemy Occupancy / AI Barrier**: Dynamically set at each active enemy's tile `(ed[i], ee[i]-1)` so enemies don't stack on the same tile or walk off ledges. |
| `1 << 2` | `4` | `0x0004` | **Enemy Ambush / Reposition Trigger** (Object Group `14`): Stepping through this vertical line teleports/activates off-screen enemy waves (`dZ, ea, ew`) and clears the trigger. |
| `1 << 3` | `8` | `0x0008` | **Climbable Ladder / Vine** (Object Group `5`, or tall push-blocks `fa[i] == true`): Allows Kratos (`gr = true`, anims `33..36`) and climbing enemies (`em[i] = true`) to climb vertically. |
| `1 << 4` | `16` | `0x0010` | **Overhead Grapple / Handhold Rope** (Object Group `18`): Allows Kratos (`gu = true`, anims `45..47, 118, 119`) and hanging enemies (`el[i] = true`) to traverse hand-over-hand across ceilings. |
| `1 << 5` | `32` | `0x0020` | **Soul Barrier Zone** (Object Group `28`): Displays `"X left"` on the bottom bar (`gN`) and tracks enemy kills (`dj[i] / dk[i]`) required to unlock a linked Soul Door (`cW`). |
| `1 << 6` | `64` | `0x0040` | **Enemy Instant-Kill Pit / Trap** (Object Group `10`): Instantly kills any non-boss enemy that steps or is knocked into this tile (used e.g. to trap the Minotaur/Argos or push soldiers into pits). |
| `1 << 7` | `128` | `0x0080` | **Dialogue / Tutorial / Context Prompt Trigger** (Object Group `8`): Triggers `@` context button icons (`aS = true`) or `J = 101` dialogue/cutscene sequences (`cr[i]`). Cleared around unopened chests (`e(x, y)`). |
| `1 << 8` | `256` | `0x0100` | **Auto-Door Tripwire** (Object Group `9`): Stepping through this vertical strip automatically toggles linked doors/gates (`cW[j] == db[i]`) and clears the tripwire. |
| `1 << 9` | `512` | `0x0200` | **Pushable Block / Crush Hazard** (Object Group `27`): Marks tiles occupied by a pushable block/statue (`eQ`). Walking into it puts Kratos into push/pull grab state (`hE = 21`). Enemies caught under/inside it are crushed! |
| `1 << 10` | `1024` | `0x0400` | **Checkpoint** (Object Group `25`): Updates respawn coordinates `(gc = x * 16, gd = y * 16, hz = hy)`. |
| `1 << 11` | `2048` | `0x0800` | **Swing Rope Point (Facing Right)** (Object Group `30`, `m(1) != 0`): Enables rope/ledge swing (`s(30)`, `gw = true`) when Kratos jumps while facing right (`hy == true`). |
| `1 << 12` | `4096` | `0x1000` | **Swing Rope Point (Facing Left)** (Object Group `30`, `m(1) == 0`): Enables rope/ledge swing (`s(30)`, `gw = true`) when Kratos jumps while facing left (`hy == false`). |
| `1 << 13` | `8192` | `0x2000` | **Balance Beam** (Object Group `0`): Narrow beam (`gD = true`). Kratos uses balance idle/walk (`anims 10, 11`), can slip to hang (`anim 120`) and pull back up (`anim 121`). |
| `1 << 14` | `16384` | `0x4000` | **Water / Swim Surface** (Object Group `29`): Puts Kratos into swimming state (`gs = true`, dive `anim 48`, tread water `anim 50`, swim `anim 51`). |

---

## 3. Kratos Movement, Physics & State Machine

Kratos is rendered using animation slot `135` (`fL` sprite slices `28673` from `RP28`, animation bank `28672` containing **122 animations**).
- **Position & Sub-Tile Coordinates**:
  - `ge`, `gf`: World pixel coordinates (foot-center X, bottom-foot Y).
  - `gA = ge / 16`, `gB = gf / 16`: Current tile coordinates.
  - `hl`, `hm`: Fixed-point `24.8` sub-tile offsets (`0..4095`, where `4096 = 16 << 8` = 1 full tile).
  - `gG`: Kratos collision width (`Q[2] >> 1`), `gH`: Kratos collision height (`Q[3]`).
  - `hy`: Facing direction (`true` = Right, `false` = Left).
- **Root-Motion Stepper (`f(int var1, int var2)` lines 3858–4014)**:
  - Each tick, `b(135, M)` advances Kratos's current animation `hF` and outputs root-motion pixel displacement `(super.a, super.b)`.
  - If Kratos is in free-fall (`hE == 26`), vertical velocity `super.b` is augmented by gravity accumulator `hO`, which increases by `+4` every `200 ms` (`hP`) up to terminal velocity `+8` px/tick. If Kratos lands when `hO >= 4`, camera shake `aw = hO` is triggered.
  - `f(var1, var2)` steps `(hl, hm)` in sub-tile increments (`<= 16` px per step) along `(super.a, super.b)` and calls `g(var5, var6)` at each step to test walls, floors, ceilings, ledges, ladders, ropes, water, blocks, and triggers.

### Complete Kratos Animation Table (Bank `28672`, 122 Animations)

| Anim ID (`hF`) | State / Action Description | Weapon Variant / Notes |
| ---: | :--- | :--- |
| `0` / `1` | Idle Stance | `0` = Athena's Blades, `1` = Blade of Artemis (`hf`) |
| `2` | Unused / Alternate Idle | — |
| `3` / `4` | Block / Parry Stance (hold Down `6`) | `3` = Blades, `4` = Artemis |
| `5` / `6` | Hit Stagger (Front) | `5` = Blades, `6` = Artemis |
| `7` | Heavy Knockback / Knockdown | Triggered when single hit exceeds `50` damage |
| `8` | Hit Stagger Variant | — |
| `9` | Run (Ground) | Standard running loop |
| `10` / `11` | Balance Beam Idle (`10`) & Balance Beam Walk (`11`) | Active when `gD == true` (tile flag `8192`) |
| `12`..`14` | Soldier Grab (`12`), Struggle/Beatdown (`13`), Throw/Slam (`14`) | Triggered by pressing Down (`6`) near weakened Type 1 enemy |
| `15` / `16` | Pull Lever Forward | `15` = Blades, `16` = Artemis |
| `17` / `18` | Pull Lever Backward | `17` = Blades, `18` = Artemis |
| `19` | Unused / Interaction | — |
| `20`..`23` | Push/Pull Block Grab Idle (`21`), Pull Block (`22`/`20`), Kick/Push Block (`23`) | Active on tile flag `512` (`go == true`) |
| `24` / `25` | Single Jump (`24`) & Double Jump (`25`) | Press Up (`1`) on ground, then Up (`1`) in air |
| `26` | Falling (Airborne Descent) | Gravity acceleration `hO` applied |
| `27`..`29` | Land from Fall (`27`), Recover (`28`), Artemis Land (`29`) | Triggers camera shake if `hO >= 4` |
| `30` | Rope / Gap Swing (`gw == true`) | Triggered at tile flags `2048` / `4096` |
| `31` / `32` | Open Chest (Struggle & Pry Lid) | `31` = Blades, `32` = Artemis |
| `33`..`36` | Ladder/Vine Climb Idle (`33`), Climb Down (`34`), Climb Up (`35`), Ladder Attack (`36`) | Active on tile flag `8` (`gr == true`) |
| `37`..`40` | Ledge Hang Idle (`37`), Climb Up Ledge (`38`/`39`), Drop from Ledge (`40`) | Active when `gv == true` |
| `44` | Ladder Flip Direction | Turn left/right while on ladder |
| `45`..`47` | Overhead Rope Hang Idle (`45`), Hand-over-Hand Move (`46`), Rope Attack (`47`) | Active on tile flag `16` (`gu == true`) |
| `48`..`51` | Dive into Water (`48`), Tread Water Idle (`50`), Swim (`51`) | Active on tile flag `16384` (`gs == true`) |
| `52` / `53` | Death Collapse (`52`) & Dead Pose (`53`) | Transitions to `J = 108` ("You Are Dead") |
| `54`..`60` | **Athena's Blades Ground Combo (`54..59` + Finisher `60`)** | Up to `gR + 1` hits (`3..6` slashes + heavy slam finisher `60` with screen shake) |
| `67`..`70` | **Athena's Blades Air Combo (`67..69`) & Air Slam (`70`)** | Airborne slash chain (`gt == true`) |
| `72`..`77` | **Blade of Artemis Ground Combo (`72..76` + Finisher `77`)** | Heavy broadsword combo (`gS + 1` hits, drains magic per hit) |
| `83`..`85` | **Blade of Artemis Air Combo (`83..85`)** | Airborne broadsword slashes |
| `88`..`90` | **Type 2 Enemy (Minotaur/Brute) QTE Grab (`88`), QTE Success Kill (`89`), QTE Fail Throw (`90`)** | 2-step directional QTE (`fU = 2`) |
| `91`..`93` | **Type 3 Enemy (Cerberus/Beast) QTE Grab (`91`), QTE Success Kill (`92`), QTE Fail (`93`)** | 3-step directional QTE (`fU = 3`) |
| `94` | **Medusa's Gaze Channeling Loop** | Drains `12` magic/tick (`hp -= 12`), petrifies enemies in sight |
| `95` | **Army of Hades Cast** | Costs `256` magic (`hp -= 256`), spawns homing souls (`fI = 3`) |
| `97` / `98` | Block Impact Recoil (Guarding Hit) | `97` = Blades, `98` = Artemis |
| `99` | Ceiling Head-Bonk | Triggered when jumping into a low solid ceiling |
| `100` / `101` | Hit Stagger (From Behind) | `100` = Blades, `101` = Artemis |
| `108`..`110` | **Boss (Dead Rider / Argos) QTE Start (`108`), QTE Success (`109`), QTE Fail (`110`)** | Mid-boss / Argos QTE sequence |
| `113`..`116` | **Ceryx / Boss Finisher (`113`), Ceryx QTE Start (`114`), QTE Success (`115`), QTE Fail (`116`)** | Final boss Ceryx requires 3 QTE rounds (`dS >= 3`) |
| `117` | Shatter Petrified Minotaur/Brute | Instant finisher on frozen Type 2 enemy |
| `118` / `119` | Hit Stagger While Hanging on Overhead Rope | `118` = Front, `119` = Back |
| `120` / `121` | Slip from Balance Beam (`120`) & Pull Back Up onto Beam (`121`) | Press Up (`1`) during `120` to recover (`121`), or Down (`6`) to drop |

---

## 4. Camera System (`e.m()`)

Implemented in [`e.m()`](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L1572-L1747):
- **Target Tracking & Look-Ahead**:
  - Normally tracks Kratos `(ge, gf)` unless a cutscene/switch pans the camera to a point of interest `(az, aA)` for up to `3500 ms` (`aB`).
  - Horizontal target `var6 = var2 - 88 + var4`, where `var4 = ±44` pixels (`176 >> 2`) in Kratos's facing direction `hy` (or `0` when climbing a ladder `gr`).
  - Vertical target `var7 = var3 - 168 + (gr ? 52 : 0)` (places Kratos's feet near `y = 168`, leaving `168` px above and `40` px below).
- **Smooth Velocity Clamping (`aP`, `aQ`)**:
  - Camera coordinates `(iV, iW)` are stored in `24.8` fixed-point `(aP, aQ)` and step toward `(var6, var7)` with maximum speed clamped to `M << 8` (`1` pixel per ms elapsed).
- **Camera Offset Zones (`aF, aG, aH, aI` — Object Group `3`)**:
  - Level designers placed camera shift zones (`aE`) and destructible wall camera locks (`fn[i]`) that smoothly pan camera look-ahead offsets `(aJ, aK)` at rate `aL = 45` (`45 * M >> 8`) to reveal arenas, pits, or bosses, and decay back to `0` when leaving the zone.
- **Screen Shake (`aw`, `ax`, `ay`)**:
  - Heavy landings (`hO >= 4`), combo finishers (`hE == 60` or `77`), and boss stomps add to `aw` (shake amplitude), oscillating `iV` and `iW` and decaying by `1` each cycle until `0`.
- **Level Boundary Clamping**:
  - `iV` is clamped to `[0, aC]` (`aC = jc * 16 - 176`), and `iW` is clamped to `[0, aD]` (`aD = jd * 16 - 208`).

---

## 5. Combat, Weapons, Magic & Upgrades

### 5.1 Equipment Cycling (`fQ = 0..3`)
Pressing `*` (`10`) or Left Softkey (`27`) cycles `fQ`:
- `fQ = 0`: **Athena's Blades** (`hf = false, hg = false, hh = false`)
- `fQ = 1`: **Blade of Artemis** (`hf = true`) — unlocked from the start (`gS = 2`), consumes Magic (`hp -= (fN[gK] << 1) + fN[gK]`) on each hit; automatically reverts to Athena's Blades (`W()`) if Magic reaches `0`.
- `fQ = 2`: **Medusa's Gaze** (`hg = true`) — channels a petrifying beam (`hE = 94`) while holding Attack (`t == 8`), draining `12` Magic per tick and adding `(fO[gZ] << 5) + M` to enemy petrification counter `eb[i]`. When `eb[i] > 500`, the enemy is frozen solid in stone (`137` overlay) and can be shattered!
- `fQ = 3`: **Army of Hades** (`hh = true`) — casts homing underworld souls (`hE = 95`), costing `256` Magic (`hp -= 256`) and spawning soul projectiles (`fI = 3`) with total damage pool `fP[ha]` that home in on all on-screen enemies (`M()`).

### 5.2 Damage Tables & Upgrade Costs

| Equipment | Base Damage / Effect Table | Upgrade Multiplier | Upgrade Thresholds (`gV`, `gW`, `gX`, `gY`) | Combo / Level Progression |
| :--- | :--- | :--- | :--- | :--- |
| **Athena's Blades** (`bk = 0`) | `fM = {2, 2, 2, 4, 4, 4, 8}` per combo step `gK` | `fM[gK] << gT` | Lv1→2: `1000`, Lv2→3: `2000`, Lv3→4: `3000`, Lv4→Max: `4000` | `gT = 0..3`; combo length `gR` increases `3 → 4 → 5 → 6` (+ finisher `60`) |
| **Blade of Artemis** (`bk = 1`) | `fN = {4, 4, 10, 10, 18, 18}` per combo step `gK` | `fN[gK] << gU` | Lv1→2: `1000`, Lv2→3: `2000`, Lv3→4: `3000`, Lv4→Max: `4000` | `gU = 0..3`; combo length `gS` increases `2 → 3 → 4 → 5` (+ finisher `77`) |
| **Medusa's Gaze** (`bk = 2`) | `fO = {2, 3, 4, 5, 6}` freeze power (`<< 5` ms/hit) | Indexed by `gZ` | Lv1→2: `1000`, Lv2→3: `2000`, Lv3→4: `3000`, Lv4→Max: `4000` | `gZ = 0..4` |
| **Army of Hades** (`bk = 3`) | `fP = {128, 256, 512, 1024, 1536}` total soul damage | Indexed by `ha` | Lv1→2: `1000`, Lv2→3: `2000`, Lv3→4: `3000`, Lv4→Max: `4000` | `ha = 0..4` |

### 5.3 Hitbox / Hurtbox Collision (`hx` vs `eA`)
- Every composite animation frame in an animation bank can define up to **6 bounding boxes** (`I[]` and `J[]` in `a.java`), unpacked via `a.b(short[] out, int instanceId, int flipFlags)` into 5-short tuples `(x, y, w, h, flags)`:
  - Tuple `0` (`out[0..4]`) & Tuple `1` (`out[5..9]`): **Vulnerability Hurtboxes** (body regions where the entity can be hit; or for Kratos during attacks, active **Weapon Attack Hitboxes** `hx[0..3]` and `hx[5..8]`).
  - Tuple `2` (`out[10..14]`): **Enemy Attack Hitbox** (`eA[10..13]`), tested against Kratos's body `(ge - hB, gf - gH, gG, gH)` at [`e.java` line 7011](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L7011).
- Each Kratos attack swing can hit an enemy up to twice (`fS < 2`, `fT < 2`), spawning blood/hit sparks (`a(x, y, 0, flip, true)`), incrementing the HUD hit counter `gL` (displayed as `"X Hits"` when `gL > 1` for `gM = 2000 ms`), and playing hit sound `y(3 + (O & 1))`.

---

## 6. Quick Time Events (QTE) & Context Finishers

When an enemy's health `eo[i]` drops below 50% (`eo[i] < er[i] >> 1`, or for bosses when staggered into state `dV[i] == 12`), the spinning **Grab Arrow** (`slot 132`, bank `1027` anim `10`) appears over the enemy's head ([`e.r(Graphics)` line 2654](file:///c:/Users/Intel/Downloads/GOW/god_of_war/e.java#L2654)):
1. **Type 1 (Undead Soldier / Harvester / Gorgon)**:
   - Pressing Down (`6`) grabs the enemy (`hE = 12`, `dV = 22`).
   - From the grab:
     - Press Attack (`8`): Brutal beatdown kill (`hE = 13`, drops Red Orbs `fI = 0`).
     - Press Left (`2`) or Right (`5`): Rip/slam finisher (`u()`, `hE = 14`, drops **Green Health Orbs** `fI = 1`!).
2. **Type 2 (Minotaur / Brute)**:
   - Pressing Down (`6`) initiates a **2-input directional QTE** (`fV = true`, `hL = 1` slow-motion, `fU = 2`, `hE = 88`).
   - Random prompt `fX = 0..4` (`Up, Left, Fire/5, Right, Down`) is displayed above the enemy (`fY = {2, 4, 5, 6, 8}` in bank `1027`).
   - Completing both inputs before `hE = 88` ends executes `hE = 89` (instant kill + drops **Blue Magic Orbs** `fI = 2`). Missing or pressing a wrong key triggers `hE = 90` (enemy breaks free).
3. **Type 3 (Cerberus / Heavy Beast & Bosses — Dead Rider, Argos, Ceryx)**:
   - Initiates a **3-input directional QTE** (`fV = true`, `hL = 1`, `fU = 3`):
     - Normal Type 3 Beast (`dQ != i`): `hE = 91` → success `hE = 92` (drops **Green Health Orbs** `fI = 1`) or fail `hE = 93`.
     - Sub-Boss / Argos (`dQ == i && dR != i`): `hE = 108` → success `hE = 109` (drops both Green and Blue orbs, advances phase `14`/`15`) or fail `hE = 110`.
     - Final Boss **Ceryx** (`dR == i`): `hE = 114` → success `hE = 115` (drops Green and Blue orbs, increments `dS` counter; after `dS >= 3` successful QTE rounds, Ceryx is defeated!) or fail `hE = 116`.

---

## 7. Enemy Archetypes, AI & Bosses

Enemies are spawned from Object Groups `11`, `12`, and `13` (up to 15 active enemy slots per level, `eF`):
- **Per-Enemy Attributes (Decoded from Level Bitstream)**:
  - `dU[i]`: Archetype (`1` = Light/Medium Infantry, `2` = Minotaur/Brute, `3` = Heavy Beast / Boss).
  - `eg[i]` / `eh[i]`: Respawn count (`0..7` waves).
  - `ex[i]` / `ev[i]`: Respawn delay timer (`ms`).
  - `er[i]` / `eo[i]`: Max HP / Current HP (quadrupled `<<= 2` for bosses `dQ`).
  - `eq[i]`: Orb drop value on death.
  - `ep[i]`: Attack damage dealt to Kratos.
  - `ec[i]`: Wave trigger group ID (`0..14`, linked to Object Group `14` ambush lines).
  - `en[i]`: Facing direction (`true` = Right, `false` = Left).
  - `ez[i]`: Patrol flag (patrols `±64 px` around spawn `dZ[i]`).

| Group | `dU` | Level Flag | Sprite / Anim Bank | Enemy / Boss Identity | Special Capabilities |
| :---: | :---: | :--- | :--- | :--- | :--- |
| `11` | `1` | `dE == true` | `18433` / `18432` (`RP18`) | **Spartan / Undead Legionnaire (Variant A)** | Melee slashes, jump attacks, ladder climbing (`em`), ceiling rope traversal (`el`), grab-killable. |
| `11` | `1` | `dE == false` | `4097` / `4096` (`RP4`) | **Undead Soldier / Skeleton Warrior (Variant B)** | Melee combos, jump/drop attacks, ladder climbing (`em`), ceiling rope traversal (`el`), grab-killable. |
| `12` | `2` | `dF == true` | `16385` / `16384` (`RP16`) | **Gorgon /Beast Brute (Act II)** | Extended reach, 2-step QTE finisher (drops Blue Magic Orbs). |
| `12` | `2` | `dF == false` | `17409` / `17408` (`RP17`) | **Minotaur Warrior (Act I)** | Heavy axe/charge attacks, 2-step QTE finisher (drops Blue Magic Orbs). |
| `13` | `3` | Normal (`!var56 && !var71`) | `15361` / `15360` (`RP15`) | **Cerberus / Heavy Underworld Beast** | Pounce & claw attacks, ground shake (`aw += 6`), 3-step QTE finisher. |
| `13` | `3` | `var56 && !var71` (`dQ == i`) | `13313` / `13312` (`RP13`) | **Act Boss: Argos / Dead Rider** | `4×` HP multiplier, multi-phase QTE (`hE = 108..110`), triggers victory dialogue `266..274` on defeat. |
| `13` | `3` | `var71 == true` (`dR == i`) | `14337` / `14336` (`RP14`) | **Final Boss: Ceryx (Son of Hermes)** | `4×` HP multiplier, requires 3 QTE cycles (`dS = 1..3`) to defeat, triggers ending dialogue `243..245`. |

---

## 8. Interactive Objects, Collectibles & Level Progression

### 8.1 Chests (`fp` / `5120` / `5123` — Object Group `4`)
- `fx[i]` (`0..3`): Chest visual type (`0` = Red Orb Chest, `1` = Green Health Chest, `2` = Blue Magic Chest, `3` = Unmarked Secret Chest).
- `fw[i]`: Orb amount (`> 0`), or for unmarked secret chests (`fx == 3`):
  - `fw[i] == -1`: **Gorgon Eye** (bit `1 << bG` in `hi`). Permanently adds `+192` (`+18.75%`) to Max Health `hq` (starts at `1024`, up to `2944` with all 10 Eyes).
  - `fw[i] == -2`: **Phoenix Feather** (bit `1 << bG << 10` in `hi`). Permanently adds `+192` (`+18.75%`) to Max Magic `hr` (starts at `1024`, up to `2944` with all 10 Feathers).
  - If already collected in a previous playthrough of that chapter (`hi` bit set), the chest spawns already opened (`ft[i] = true`).

### 8.2 Orbs & Homing Physics (`M()` lines 5860–5963)
- Up to 20 active 4-point trailing particles (`fK`, arrays `fC, fD` of length 80):
  - `fI = 0`: **Red Orbs** (adds to `hu` and career total `hv`, capped at chapter cap `hw`).
  - `fI = 1`: **Green Health Orbs** (restores `hn` up to `hq`).
  - `fI = 2`: **Blue Magic Orbs** (restores `hp` up to `hr`).
  - `fI = 3`: **Army of Hades Souls** (homes in on enemy `fG[i]` instead of Kratos, dealing `fJ[i]` damage on contact).

### 8.3 Campaign Chapters (`bF[0..9]`) & Arena Mode (`9216`)

| Chapter Index (`bG`) | Level Resource ID | RP Pack | Title (String `54 + bG`) | Palette Index (`bK[bG]`) | Career Orb Cap (`hw`) | Boss / Key Feature |
| :---: | ---: | :--- | :--- | :---: | ---: | :--- |
| **Arena** (`iS = true`) | `9216` (`0x2400`) | `RP9` | **Arena** (`66`) | `-1` (Base) | `1,250` | Endless enemy waves; soul doors open upper tiers as kill count (`dM`) rises. |
| **0** | `9217` (`0x2401`) | `RP9` | **For Sparta** (`54`) | `-1` (Base) | `1,250` | Spartan battlefield, village temple roof & Pool of Worship, **Argos** encounter (`13312`). |
| **1** | `19456` (`0x4C00`) | `RP19` | **Dead Rider** (`55`) | `-1` (Base) | `2,750` | Pursuit of the Dead Rider (`13312`), balance beams & collapsible platforms. |
| **2** | `20480` (`0x5000`) | `RP20` | **Minotaur** (`56`) | `-1` (Base) | `4,500` | Minotaur release, sewer trap puzzle, **Argos** boss battle & death cutscene. |
| **3** | `21504` (`0x5400`) | `RP21` | **Garden** (`57`) | `2` (Gold/Sand) | `6,500` | Temple gardens, pushable statues, Cerberus (`15360`) ambushes. |
| **4** | `22528` (`0x5800`) | `RP22` | **The Beast** (`58`) | `0` (Blue/Night) | `8,750` | Three-headed Cerberus battles & Dead Rider showdown (`13312`). |
| **5** | `23552` (`0x5C00`) | `RP23` | **Ruins** (`59`) | `1` (Teal/Ruin) | `11,250` | Act II intro (`230`), Gorgon/Brute (`16384`) debut, sawblade/spike traps. |
| **6** | `24576` (`0x6000`) | `RP24` | **Catacombs** (`60`) | `1` (Teal/Ruin) | `14,000` | Underground catacombs, skull switches, soul barriers, multi-tier vertical shafts. |
| **7** | `25600` (`0x6400`) | `RP25` | **Labyrinth** (`61`) | `0` (Blue/Night) | `17,000` | Rope swings (`2048`/`4096`), moving traps, elevator/gate puzzles. |
| **8** | `26624` (`0x6800`) | `RP26` | **The Tower** (`62`) | `2` (Gold/Sand) | `20,250` | `656 × 2208 px` vertical tower climb (`41 × 138` tiles), intense vertical platforming. |
| **9** | `27648` (`0x6C00`) | `RP27` | **Messenger** (`63`) | `2` (Gold/Sand) | `23,750` | Final confrontation & 3-phase QTE boss fight against **Ceryx, Son of Hermes** (`14336`). |
