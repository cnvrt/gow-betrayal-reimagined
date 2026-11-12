import * as Phaser from "phaser";
import { NATIVE_HEIGHT, NATIVE_WIDTH } from "../constants";

export interface OrbParticle {
  active: boolean;
  type: number; // 0 = Red, 1 = Green, 2 = Blue, 3 = Soul
  x: number;
  y: number;
  vx: number; // 8-bit fixed point velocity (fE)
  vy: number; // 8-bit fixed point velocity (fF)
  trailX: number[];
  trailY: number[];
  ageMs: number;
  value: number;
  targetEnemyIdx: number; // -1 for Kratos, >= 0 for enemy index
}

/**
 * Authentic Orb Simulation & Homing Physics matching `e.M()` (`e.java` lines 5860-5963):
 * - Max 20 active orbs (`fK < 20`)
 * - Trailing 4-point particle rendering
 * - Velocity step: 9 * dtMs, clamped to `±3584` (`±14` px/frame)
 * - Types:
 *   - `0`: Red Orbs (Career XP / upgrades)
 *   - `1`: Green Orbs (Health restoration `hn`)
 *   - `2`: Blue Orbs (Magic restoration `hp`)
 *   - `3`: Army of Hades Souls (Homes in on enemies to deal damage)
 */
export class OrbManager {
  private scene: Phaser.Scene;
  private graphics: Phaser.GameObjects.Graphics;
  public orbs: OrbParticle[] = [];

  constructor(scene: Phaser.Scene) {
    this.scene = scene;
    this.graphics = scene.add.graphics().setDepth(48);
  }

  public spawnOrb(
    type: number,
    x: number,
    y: number,
    value: number,
    targetEnemyIdx: number = -1
  ): void {
    if (this.orbs.length >= 20) {
      // Find oldest inactive orb or discard
      const inactive = this.orbs.find((o) => !o.active);
      if (inactive) {
        this.initOrb(inactive, type, x, y, value, targetEnemyIdx);
        return;
      }
      return;
    }

    const orb: OrbParticle = {
      active: true,
      type,
      x,
      y,
      vx: (Math.random() * 2000 - 1000) | 0,
      vy: -1500 - (Math.random() * 1500) | 0,
      trailX: [x, x, x, x],
      trailY: [y, y, y, y],
      ageMs: 0,
      value,
      targetEnemyIdx,
    };
    this.orbs.push(orb);
  }

  private initOrb(
    orb: OrbParticle,
    type: number,
    x: number,
    y: number,
    value: number,
    targetEnemyIdx: number
  ): void {
    orb.active = true;
    orb.type = type;
    orb.x = x;
    orb.y = y;
    orb.vx = (Math.random() * 2000 - 1000) | 0;
    orb.vy = -1500 - (Math.random() * 1500) | 0;
    orb.trailX = [x, x, x, x];
    orb.trailY = [y, y, y, y];
    orb.ageMs = 0;
    orb.value = value;
    orb.targetEnemyIdx = targetEnemyIdx;
  }

  public spawnChestOrbs(type: number, x: number, y: number, totalValue: number): void {
    const count = Math.min(8, Math.max(3, Math.floor(totalValue / 15)));
    const perOrb = Math.max(1, Math.floor(totalValue / count));
    for (let i = 0; i < count; i++) {
      this.spawnOrb(type, x + (Math.random() * 16 - 8), y - 10 - i * 3, perOrb);
    }
  }

  public update(
    dtMs: number,
    kratosX: number,
    kratosY: number,
    kratosH: number,
    onCollect: (type: number, value: number) => void,
    enemyQuery?: (idx: number) => { x: number; y: number; h: number; alive: boolean } | null,
    onSoulHit?: (enemyIdx: number, damage: number) => void
  ): void {
    this.graphics.clear();
    const stepAccel = 9 * dtMs;

    for (let i = 0; i < this.orbs.length; i++) {
      const orb = this.orbs[i];
      if (!orb.active) continue;

      orb.ageMs += dtMs;

      // Determine target coordinates
      let targetX = kratosX;
      let targetY = kratosY - (kratosH >> 1);

      if (orb.type === 3 && orb.targetEnemyIdx >= 0 && enemyQuery) {
        const enemy = enemyQuery(orb.targetEnemyIdx);
        if (enemy && enemy.alive) {
          targetX = enemy.x;
          targetY = enemy.y - (enemy.h >> 1);
        } else {
          // Fall back to nearest enemy or Kratos
          targetX = kratosX;
          targetY = kratosY - (kratosH >> 1);
        }
      }

      // Exact acceleration logic from e.M() (lines 5866-5885)
      if (targetX < orb.x && orb.vx > -3584) {
        orb.vx -= stepAccel;
      } else if (targetX > orb.x && orb.vx < 3584) {
        orb.vx += stepAccel;
      } else {
        orb.vx >>= 2;
      }

      if (targetY < orb.y && orb.vy > -3584) {
        orb.vy -= stepAccel;
      } else if (targetY > orb.y && orb.vy < 3584) {
        orb.vy += stepAccel;
      } else {
        orb.vy -= orb.vy >> 3;
      }

      // Update positions
      orb.x += orb.vx >> 8;
      orb.y += orb.vy >> 8;

      // Update trail array (4-step history)
      orb.trailX.pop();
      orb.trailX.unshift(orb.x);
      orb.trailY.pop();
      orb.trailY.unshift(orb.y);

      // Hit / Collection check (lines 5887-5948: age > 75ms)
      const dist = Math.hypot(orb.x - targetX, orb.y - targetY);
      if (dist < 18 && orb.ageMs > 75) {
        orb.active = false;
        if (orb.type === 3 && orb.targetEnemyIdx >= 0 && onSoulHit) {
          onSoulHit(orb.targetEnemyIdx, orb.value);
        } else {
          onCollect(orb.type, orb.value);
        }
        continue;
      }

      // Render 4-point trailing orb
      let color = 0xff2222; // Red Orb (0)
      if (orb.type === 1) color = 0x22ff44; // Green Health (1)
      else if (orb.type === 2) color = 0x22aaff; // Blue Magic (2)
      else if (orb.type === 3) color = 0xaa44ff; // Soul (3)

      // Main head
      this.graphics.fillStyle(color, 0.95);
      this.graphics.fillCircle(orb.x, orb.y, 2.5);
      this.graphics.fillStyle(0xffffff, 0.8);
      this.graphics.fillCircle(orb.x, orb.y, 1);

      // Trail
      for (let t = 1; t < orb.trailX.length; t++) {
        const alpha = 0.6 - t * 0.15;
        const radius = Math.max(1, 2.2 - t * 0.5);
        this.graphics.fillStyle(color, alpha);
        this.graphics.fillCircle(orb.trailX[t], orb.trailY[t], radius);
      }
    }
  }

  public clear(): void {
    this.orbs = [];
    this.graphics.clear();
  }

  public destroy(): void {
    this.graphics.destroy();
    this.orbs = [];
  }
}
