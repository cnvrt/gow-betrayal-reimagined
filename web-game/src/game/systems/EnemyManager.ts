import * as Phaser from "phaser";
import { CompositeAnimPlayer } from "./CompositeAnimPlayer";
import { EnemyController, type EnemyConfig } from "./EnemyController";
import { LevelRenderer } from "./LevelRenderer";
import type { LevelData } from "../types/assets";

/**
 * Manages all active enemy slots, group spawns (Groups 11, 12, 13),
 * ambush barrier triggers (Group 14), and hit registration.
 */
export class EnemyManager {
  private scene: Phaser.Scene;
  private levelRenderer: LevelRenderer;
  public enemies: EnemyController[] = [];
  public activeBoss: EnemyController | null = null;

  constructor(scene: Phaser.Scene, levelRenderer: LevelRenderer) {
    this.scene = scene;
    this.levelRenderer = levelRenderer;
  }

  public populateFromLevel(level: LevelData): void {
    this.clear();
    const palVar = level.palette_variant;

    let enemyIndex = 0;
    for (const group of level.object_groups) {
      const gType = group.group_type;
      if (gType !== 11 && gType !== 12 && gType !== 13) continue;

      for (const inst of group.instances) {
        let bankId = 4096;
        let pal = palVar;
        let archetype = 1;
        let isBoss = false;
        let isArgos = false;
        let isDeadRider = false;
        let isCeryx = false;

        if (gType === 11) {
          archetype = 1;
          bankId = level.header.dE_enemy_set_a ? 18432 : 4096;
        } else if (gType === 12) {
          archetype = 2;
          bankId = level.header.dF_enemy_set_b ? 16384 : 17408;
        } else if (gType === 13) {
          archetype = 3;
          isBoss = true;
          if (inst.is_argos_boss && !inst.is_dead_rider_boss) {
            bankId = 13312;
            isArgos = true;
          } else if (inst.is_dead_rider_boss) {
            bankId = 14336;
            pal = -1;
            isDeadRider = true;
            isCeryx = true;
          } else {
            bankId = 15360; // Cerberus / Underworld Beast
          }
        }

        const maxHp = (inst.max_hp || 100) * (isBoss ? 4 : 1);
        const config: EnemyConfig = {
          index: enemyIndex++,
          archetype,
          animBankId: bankId,
          paletteVariant: pal,
          spawnX: inst.x,
          spawnY: inst.y,
          facingRight: inst.facing_right ?? false,
          patrol: Boolean(inst.ez),
          maxHp,
          orbDrop: inst.orb_drop_value || 25,
          attackDamage: inst.attack_damage || 20,
          waveGroup: inst.wave_group_id || 0,
          respawnWaves: inst.respawn_waves || 0,
          respawnDelayMs: (inst.respawn_delay_sec || 3) * 1000,
          isBoss,
          isArgos,
          isDeadRider,
          isCeryx,
        };

        const enemy = new EnemyController(this.scene, config, this.levelRenderer);
        this.levelRenderer.entitiesLayer.add(enemy.player.container);
        this.enemies.push(enemy);

        if (isBoss && !this.activeBoss) {
          this.activeBoss = enemy;
        }
      }
    }
  }

  public getAliveEnemies(): EnemyController[] {
    return this.enemies.filter((e) => e.alive);
  }

  public update(
    dtMs: number,
    kratosX: number,
    kratosY: number,
    kratosH: number,
    onAttackKratos?: (damage: number, fromRight: boolean) => void
  ): void {
    for (const enemy of this.enemies) {
      enemy.update(dtMs, kratosX, kratosY, kratosH, onAttackKratos);
    }
  }

  public clear(): void {
    for (const e of this.enemies) {
      e.destroy();
    }
    this.enemies = [];
    this.activeBoss = null;
  }
}
