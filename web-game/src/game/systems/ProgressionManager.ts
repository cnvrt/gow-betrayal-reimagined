export interface ProgressionData {
  chapterIndex: number;
  maxHealth: number;
  currentHealth: number;
  maxMagic: number;
  currentMagic: number;
  redOrbs: number;
  careerOrbs: number;
  gorgonEyesBitmask: number;
  phoenixFeathersBitmask: number;
}

/**
 * Career progression, level advancement, and secret chest stat upgrades (Gorgon Eyes / Phoenix Feathers)
 * matching `e.java` (lines 5837-5851, 1250-1280).
 */
export class ProgressionManager {
  public chapterIndex: number = 0;
  public maxHealth: number = 1024;
  public currentHealth: number = 1024;
  public maxMagic: number = 1024;
  public currentMagic: number = 1024;
  public redOrbs: number = 0;
  public get totalRedOrbs(): number {
    return this.redOrbs;
  }
  public set totalRedOrbs(val: number) {
    this.redOrbs = val;
  }
  public careerOrbs: number = 0;
  public gorgonEyesCount: number = 0;
  public phoenixFeathersCount: number = 0;
  public secretChestsBitmask: number = 0; // hi in e.java

  constructor() {
    this.reset();
  }

  public reset(): void {
    this.chapterIndex = 0;
    this.maxHealth = 1024;
    this.currentHealth = 1024;
    this.maxMagic = 1024;
    this.currentMagic = 1024;
    this.redOrbs = 0;
    this.careerOrbs = 0;
    this.gorgonEyesCount = 0;
    this.phoenixFeathersCount = 0;
    this.secretChestsBitmask = 0;
  }

  public collectGorgonEye(chapterIdx: number): void {
    const bit = 1 << chapterIdx;
    if ((this.secretChestsBitmask & bit) === 0) {
      this.secretChestsBitmask |= bit;
      this.gorgonEyesCount++;
      this.maxHealth += 192;
      this.currentHealth = this.maxHealth;
    }
  }

  public collectPhoenixFeather(chapterIdx: number): void {
    const bit = 1 << (chapterIdx + 10);
    if ((this.secretChestsBitmask & bit) === 0) {
      this.secretChestsBitmask |= bit;
      this.phoenixFeathersCount++;
      this.maxMagic += 192;
      this.currentMagic = this.maxMagic;
    }
  }

  public addRedOrbs(amount: number, chapterCap: number = 99999): void {
    this.redOrbs += amount;
    this.careerOrbs += amount;
    if (this.careerOrbs > chapterCap) {
      this.careerOrbs = chapterCap;
    }
  }

  public restoreHealth(amount: number): void {
    this.currentHealth = Math.min(this.maxHealth, this.currentHealth + amount);
  }

  public restoreMagic(amount: number): void {
    this.currentMagic = Math.min(this.maxMagic, this.currentMagic + amount);
  }

  public consumeMagic(amount: number): boolean {
    if (this.currentMagic >= amount) {
      this.currentMagic -= amount;
      return true;
    }
    return false;
  }
}
