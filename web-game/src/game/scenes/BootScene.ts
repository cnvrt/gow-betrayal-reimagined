import * as Phaser from "phaser";
import { NATIVE_HEIGHT, NATIVE_WIDTH } from "../constants";
import { AssetRegistry } from "../systems/AssetRegistry";
import type { AssetManifest } from "../types/assets";

/**
 * BootScene: Loads `/assets/asset_manifest.json` and preloads all original
 * God of War: Betrayal extracted assets into Phaser's cache.
 */
export class BootScene extends Phaser.Scene {
  private progressBar!: Phaser.GameObjects.Graphics;
  private statusText!: Phaser.GameObjects.Text;

  constructor() {
    super({ key: "BootScene" });
  }

  preload(): void {
    this.cameras.main.setBackgroundColor("#0c0705");
    this.progressBar = this.add.graphics();
    this.statusText = this.add
      .text(NATIVE_WIDTH / 2, NATIVE_HEIGHT / 2 - 18, "LOADING RP ASSETS...", {
        fontFamily: "monospace",
        fontSize: "10px",
        color: "#e6b86a",
      })
      .setOrigin(0.5);

    this.load.on("progress", (value: number) => {
      this.progressBar.clear();
      this.progressBar.lineStyle(1, 0x8c5a2b, 1);
      this.progressBar.strokeRect(18, NATIVE_HEIGHT / 2, NATIVE_WIDTH - 36, 10);
      this.progressBar.fillStyle(0xc8321e, 1);
      this.progressBar.fillRect(20, NATIVE_HEIGHT / 2 + 2, (NATIVE_WIDTH - 40) * value, 6);
    });

    this.load.json("asset_manifest", "/assets/asset_manifest.json");
  }

  create(): void {
    const manifest = this.cache.json.get("asset_manifest") as AssetManifest;
    const registry = AssetRegistry.get();
    registry.queueManifestAssets(this, manifest);

    this.statusText.setText(`UNPACKING ${manifest.total_manifest_items} ASSETS...`);

    this.load.once("complete", () => {
      registry.initializeFromCache(this);
      this.scene.start("PlayScene", { levelResId: 9217 });
    });

    this.load.start();
  }
}
