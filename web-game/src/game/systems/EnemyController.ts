import * as Phaser from "phaser";
import { CompositeAnimPlayer } from "./CompositeAnimPlayer";
import { LevelRenderer } from "./LevelRenderer";

export interface EnemyConfig {
  index: number;
  archetype: number; // 1 = Legionnaire/Undead, 2 = Minotaur/Gorgon, 3 = Heavy Beast / Boss
  animBankId: number;
  paletteVariant: number;
  spawnX: number;
  spawnY: number;
  facingRight: boolean;
  patrol: boolean;
  maxHp: number;
  orbDrop: number;
  attackDamage: number;
  waveGroup: number;
  respawnWaves: number;
  respawnDelayMs: number;
  isBoss: boolean;
  isArgos: boolean;
  isDeadRider: boolean;
  isCeryx: boolean;
}

/**
 * Enemy AI & Combat Controller matching `e.V()`, `e.d()`, and `e.e()` in `e.java` (lines 6248-7450):
 * - State machine `dV`:
 *   - `0`: Idle
 *   - `1`: Walk / Patrol
 *   - `2`: Hit Stun / Hurt
 *   - `3`: Windup / Prepare Attack
 *   - `4`: Melee Attack / Slash
 *   - `5`: Combo Attack
 *   - `6`: Heavy Attack / Jump Smash
 *   - `7`: Knockdown / Dead Fall
 *   - `8`: Death Fall / Vanish
 *   - `9`: Grabbed by Kratos
 *   - `10`: Stagger / Pre-Finisher Stun (Grab Arrow displayed)
 *   - `11`: Recovering from ground
 *   - `12`: Stunned in Grab (QTE prompt active)
 *   - `13`: Being finished / Brutal kill
 *   - `14`: Wall/Ground Slammed
 *   - `22`: Kratos Grabbed Hold (`hE = 12`)
 *   - `26`: Ceiling Drop / Ladder Climb
 *   - `27`: Teleport / Respawn Entrance
 */
export class EnemyController {
  public scene: Phaser.Scene;
  public config: EnemyConfig;
  public player: CompositeAnimPlayer;
  public levelRenderer: LevelRenderer;

  public x: number;
  public y: number;
  public spawnX: number;
  public spawnY: number;

  public hp: number;
  public maxHp: number;
  public dV: number = 0; // State
  public facingRight: boolean;
  public alive: boolean = true;
  public active: boolean = true;

  public frozenTimerMs: number = 0; // Petrification (Medusa's Gaze)
  public respawnTimerMs: number = 0;
  public remainingWaves: number;

  // Hurtbox metrics
  public width: number = 24;
  public height: number = 32;

  // AI timers
  private aiTickTimerMs: number = 0;
  private attackCooldownMs: number = 0;

  constructor(scene: Phaser.Scene, config: EnemyConfig, levelRenderer: LevelRenderer) {
    this.scene = scene;
    this.config = config;
    this.levelRenderer = levelRenderer;

    this.x = config.spawnX;
    this.y = config.spawnY;
    this.spawnX = config.spawnX;
    this.spawnY = config.spawnY;
    this.facingRight = config.facingRight;

    this.maxHp = config.maxHp;
    this.hp = config.maxHp;
    this.remainingWaves = config.respawnWaves;

    this.player = new CompositeAnimPlayer(
      scene,
      config.animBankId,
      config.paletteVariant,
      0
    );
    this.player.setPosition(this.x, this.y);
    this.player.setFlip(!this.facingRight, false);

    const bounds = this.player.getFrameBounds(0, 0, 0);
    this.width = bounds.width || 24;
    this.height = bounds.height || 32;
  }

  public setState(state: number): void {
    this.dV = state;
    this.player.setAnimation(state);
  }

  public takeDamage(damage: number, fromRight: boolean): boolean {
    if (!this.alive) return false;

    this.hp -= damage;
    this.facingRight = fromRight;
    this.player.setFlip(!this.facingRight, false);

    if (this.hp <= 0) {
      this.hp = 0;
      this.alive = false;
      this.setState(this.config.archetype === 1 ? 8 : 7);
      return true; // Fatal blow
    } else {
      // Light hit stun
      if (this.dV !== 10 && this.dV !== 12 && this.dV !== 13) {
        this.setState(2);
      }
      return false;
    }
  }

  public freeze(durationMs: number): void {
    if (!this.alive) return;
    this.frozenTimerMs = durationMs;
  }

  public isStaggeredForGrab(): boolean {
    return this.alive && (this.hp < (this.maxHp >> 1) || this.dV === 10 || this.dV === 12);
  }

  public update(
    dtMs: number,
    kratosX: number,
    kratosY: number,
    kratosH: number,
    onAttackKratos?: (damage: number, fromRight: boolean) => void
  ): void {
    if (!this.active) return;

    // 1. Handle Respawn Timer when dead
    if (!this.alive) {
      this.player.update(dtMs);
      if (this.remainingWaves > 0) {
        this.respawnTimerMs += dtMs;
        if (this.respawnTimerMs >= this.config.respawnDelayMs) {
          this.respawnTimerMs = 0;
          this.remainingWaves--;
          this.respawn();
        }
      }
      return;
    }

    // 2. Handle Petrification / Frozen State
    if (this.frozenTimerMs > 0) {
      this.frozenTimerMs -= dtMs;
      return;
    }

    // 3. Advance Animation & Root Motion
    const animPlaying = this.player.update(dtMs);
    const stepDir = this.facingRight ? 1 : -1;
    this.x += stepDir * this.player.stepA;
    this.y += this.player.stepB;
    this.player.setPosition(this.x, this.y);

    if (this.attackCooldownMs > 0) {
      this.attackCooldownMs -= dtMs;
    }

    // 4. State Transitions on Animation Finish
    if (!animPlaying) {
      if (this.dV === 2 || this.dV === 4 || this.dV === 5 || this.dV === 6) {
        this.setState(0); // Return to idle
      } else if (this.dV === 7 || this.dV === 8) {
        this.alive = false;
      }
    }

    // 5. Basic AI Decision Loop (lines 6300-6670)
    this.aiTickTimerMs += dtMs;
    if (this.aiTickTimerMs > 150 && this.dV === 0) {
      this.aiTickTimerMs = 0;
      const distX = kratosX - this.x;
      const distY = Math.abs(kratosY - this.y);
      const absDistX = Math.abs(distX);

      // Face Kratos
      this.facingRight = distX > 0;
      this.player.setFlip(!this.facingRight, false);

      // In melee range (< 40 px horizontal, < 24 px vertical)
      if (absDistX < 40 && distY < 24 && this.attackCooldownMs <= 0) {
        this.attackCooldownMs = 1200 + Math.random() * 800;
        const atkType = Math.random() > 0.4 ? 4 : 5;
        this.setState(atkType);
        if (onAttackKratos) {
          onAttackKratos(this.config.attackDamage, this.x > kratosX);
        }
      } else if (absDistX > 32 && absDistX < 180 && distY < 48) {
        // Move toward Kratos
        this.setState(1);
      }
    }
  }

  public respawn(): void {
    this.x = this.spawnX;
    this.y = this.spawnY;
    this.hp = this.maxHp;
    this.alive = true;
    this.setState(0);
    this.player.setPosition(this.x, this.y);
    this.player.setFlip(!this.facingRight, false);
  }

  public destroy(): void {
    this.player.destroy();
  }
}
