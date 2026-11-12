# Phase 5 Implementation Plan: Combat, Enemies, Bosses, Health, Damage, Orbs & Progression

We are ready to implement **Phase 5** for *God of War: Betrayal (2007 J2ME)* in `web-game/`.

---

## 1. Architecture & System Decomposition

Phase 5 introduces complete combat and progression mechanics based on reverse-engineered Java sources (`e.java` lines 2705–7697):

```
web-game/src/game/
├── systems/
│   ├── CombatManager.ts       # Attack combos, hitbox vs hurtbox queries, damage formulas, hit counter (gL), hit sparks
│   ├── EnemyController.ts     # Individual enemy state machine, AI routines, respawn waves, ledge/ceiling patrol, QTE finisher states
│   ├── EnemyManager.ts        # Manages all active enemy slots (Groups 11, 12, 13) and wave barriers (Group 14)
│   ├── BossManager.ts         # Boss logic: Argos, Dead Rider (13312), Ceryx (14336), 3-round QTE state machine
│   ├── QTEManager.ts          # Directional QTE prompt sequence (Bank 1027), timer, slow-motion (hL=1), success/fail transitions
│   ├── OrbManager.ts          # Homing orb physics (fC, fD, fE, fF, M() lines 5860-5963), Green/Blue/Red orbs & Soul particles
│   └── ProgressionManager.ts  # Career orbs (hu, hv, hw), Gorgon Eyes / Phoenix Feathers (hi), chapter transitions (0..9)
```

---

## 2. Core Subsystems & Mechanics Porting

### 2.1 Kratos Full Combat State Machine (`KratosController.ts`)
- **Combo Chains**:
  - **Blades of Chaos**: Ground 3-hit combo (`54 -> 55 -> 56`), heavy finisher (`60`), launcher (`57`), slam (`61`), airborne 3-hit combo (`67 -> 68 -> 69`).
  - **Blade of Artemis (`hf`)**: Ground 3-hit heavy slashes (`72 -> 73 -> 74`), heavy ground pound (`77`), airborne slash combo (`83 -> 84 -> 85`).
  - **Medusa's Gaze (`hg`)**: Beam freeze charge (`94`), consumes Magic (`hp`), petrifies enemies into frozen state (`eb[i]`).
  - **Army of Hades (`hh`)**: Soul summon storm (`95`), consumes Magic (`hp`), spawns homing soul projectiles (`fI = 3`).
- **Hit Detection & Hurtboxes**:
  - `hx[0..14]`: Kratos's active weapon attack boxes from composite frames.
  - Multi-hit tracking (`fS < 2, fT < 2`), combo counter `gL` (displayed on HUD as `"X Hits"` for `2000 ms`), screen shake (`aw`).
- **Health, Magic & Taking Damage**:
  - Health (`hn`, max `hq = 1024 + 192 * eyes`), Magic (`hp`, max `hr = 1024 + 192 * feathers`).
  - Damage taken (`ep[i]`), hit-stun states (`5, 100, 7`), knockdown recovery (`27 -> 28`).

### 2.2 Enemy Subsystem & AI (`EnemyController.ts` & `EnemyManager.ts`)
- **Enemy Archetypes**:
  - `dU = 1`: Light/Medium Infantry (Spartan Legionnaire `18432`, Undead Soldier `4096`).
  - `dU = 2`: Minotaur / Gorgon Brute (`17408` / `16384`).
  - `dU = 3`: Heavy Beast / Boss (Cerberus `15360`, Argos `13312`, Ceryx `14336`).
- **AI Behaviors**:
  - Distance checks to Kratos (`dK = |ge - dX|`), horizontal follow, platform edge and wall raycasts (`e(var15, var16, var3)`).
  - Melee attack triggers (`var12 < 48 && var13 < 16`), jump attacks, ladder climbing (`em`), ceiling traversal (`el`).
  - Stagger / Petrification (`eb[i]`), death animations and corpse despawn.
  - Wave respawn system (`eg[i]` remaining waves, `ev[i]` timer, Group 14 barrier triggers).

### 2.3 Grab Finishers & Quick Time Events (QTE)
- **Grab Indicator (`aS`)**:
  - When enemy HP `< 50%` (`eo[i] < er[i] >> 1`), prompt icon (`1027` anim `10`) appears over enemy.
  - Pressing Down (`6`) initiates Grab:
    - **Type 1**: Immediate beatdown (`hE = 13`, drops Red Orbs) or rip finisher (`hE = 14`, drops Green Health Orbs).
    - **Type 2**: 2-input directional QTE (`fV = true, fU = 2, hE = 88`), drops Blue Magic Orbs.
    - **Type 3 / Bosses**: 3-input directional QTE (`fV = true, fU = 3, hE = 91/108/114`). Bosses require multi-round QTEs (`dS >= 3`).

### 2.4 Homing Orbs Physics (`OrbManager.ts`)
- Port `M()` (`e.java` lines 5860–5963):
  - Trailing 4-point particles with acceleration toward Kratos (`fE, fF` velocity clamping to `±3584`).
  - Green Orbs (`fI = 1`), Blue Orbs (`fI = 2`), Red Orbs (`fI = 0`), Army of Hades Souls (`fI = 3`).

### 2.5 Bosses & Level Progression
- **Argos (Chapter 0 & 2)**: 4x HP multiplier, multi-phase encounter.
- **Dead Rider (Chapter 1 & 4)**: Balance beam arena pursuit.
- **Ceryx (Chapter 9)**: 3-phase final boss battle with Hermes staff attacks and multi-round QTE.
- **Chapter Progression**: Seamless level load, career orb caps (`hw`), secret chests containing Gorgon Eyes and Phoenix Feathers (`hi` bitmask).

---

## 3. Implementation Steps

1. **Step 1: Combat Core & Kratos Attack State Expansion**:
   - Update `KratosController.ts` with all combo chains, attack box extraction (`hx`), damage dealing, weapon switching, and taking damage.
2. **Step 2: Homing Orb System (`OrbManager.ts`)**:
   - Implement authentic orb particles, physics homing, collection sound/visual cues, and HUD updates.
3. **Step 3: Enemy Archetypes & AI (`EnemyController.ts` & `EnemyManager.ts`)**:
   - Implement enemy state machines for Group 11, 12, and 13, including hurtboxes (`eA`), attacks, patrol, and wave respawning.
4. **Step 4: QTE & Finisher System (`QTEManager.ts`)**:
   - Implement grab detection, slow-motion input window, on-screen prompt renderer (Bank 1027), and finisher execution.
5. **Step 5: Boss Logic & Chapter Progression (`BossManager.ts` & `ProgressionManager.ts`)**:
   - Implement Argos, Dead Rider, and Ceryx boss mechanics, plus Gorgon Eye / Phoenix Feather stat upgrades and chapter advancement.
6. **Step 6: UI & Telemetry Updates**:
   - Update `GameHost.tsx` with combo counters, enemy HP meters, and Phase 5 debug telemetry.
7. **Step 7: Browser Verification**:
   - Verify combat in desktop and mobile viewports, verify enemy spawns, hit registration, combos, and orbs.
