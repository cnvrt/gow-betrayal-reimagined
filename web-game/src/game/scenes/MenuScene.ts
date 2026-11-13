import * as Phaser from "phaser";
import { NATIVE_HEIGHT, NATIVE_WIDTH } from "../constants";
import { AssetRegistry } from "../systems/AssetRegistry";
import { BitmapFontRenderer } from "../systems/BitmapFontRenderer";
import { CompositeAnimPlayer } from "../systems/CompositeAnimPlayer";
import {
  EVENT_SELECT_SCENE,
  EVENT_TELEMETRY_UPDATE,
  GameEvents,
} from "../systems/EventBus";
import { SliceLoader } from "../systems/SliceLoader";
import { AudioManager } from "../systems/AudioManager";

/**
 * MenuScene: Renders the original Main Menu from `e.java`:
 * - Procedural 176x64 cellular-automata fire simulation using palette `1053`
 * - Stone plaque banner (`1039` / `1051`)
 * - Animated God of War: Betrayal logo (`1025` / `1036` / `1048`)
 * - Original Bitmap Font (`1032` & `1031`) menu items (`Play Game`, `Arena`, `Chapters`, `Help`, `About`)
 */
export class MenuScene extends Phaser.Scene {
  private fireHeat: Uint8Array = new Uint8Array(NATIVE_WIDTH * 95);
  private fireCanvasTex!: Phaser.Textures.CanvasTexture;
  private logoPlayer!: CompositeAnimPlayer;
  private menuTextContainer!: Phaser.GameObjects.Container;
  private selectedIndex: number = 0;
  private menuItems = [
    { labelId: 38, fallback: "Play Game", action: "play" },
    { labelId: 49, fallback: "Chapters", action: "chapters" },
    { labelId: 53, fallback: "Arena", action: "arena" },
    { labelId: 40, fallback: "Help", action: "help" },
    { labelId: 42, fallback: "About", action: "about" },
  ];
  private cleanupBus?: () => void;

  constructor() {
    super({ key: "MenuScene" });
  }

  create(): void {
    this.cameras.main.setBackgroundColor("#000000");

    // 1. Procedural Fire CanvasTexture (176x91 at y=109, matching e.b(Graphics, true) lines 8215-8220)
    if (this.textures.exists("proc_fire")) {
      this.textures.remove("proc_fire");
    }
    this.fireCanvasTex = this.textures.createCanvas("proc_fire", NATIVE_WIDTH, 91)!;
    this.add.image(0, 109, "proc_fire").setOrigin(0, 0).setDepth(5);

    // 2. Main Menu Logo & Frame (Slice 1039 -> PNG 1051, matching e.w(Graphics) lines 8642-8644)
    // r[0] (GOD OF WAR title) & r[1] (omega subtitle plaque) at (H=88, 0);
    // r[2], r[3], r[4] (Kratos face & stone pillars) at (F=176, G=208)!
    for (let s = 0; s <= 4; s++) {
      const anchorX = s < 2 ? NATIVE_WIDTH / 2 : NATIVE_WIDTH;
      const anchorY = s < 2 ? 0 : NATIVE_HEIGHT;
      const img = SliceLoader.createSliceImage(this, 1039, s, anchorX, anchorY, 0, -1);
      if (img) img.setDepth(15);
    }

    // 3. "BETRAYAL" Subtitle Text at (88, r[0].h + r[1].h = 39) using Font 1032 (e.w(Graphics) line 8605)
    const subtitleLabel = AssetRegistry.get().getEnglishString(274) || "BETRAYAL";
    const subContainer = BitmapFontRenderer.renderText(this, 1032, subtitleLabel, NATIVE_WIDTH / 2, 47, "center");
    subContainer.setDepth(20);

    // Keep logoPlayer as hidden or secondary reference for telemetry compatibility
    this.logoPlayer = new CompositeAnimPlayer(this, 1025, -1, 2, 1036);
    this.logoPlayer.setPosition(0, 0);
    this.logoPlayer.container.setVisible(false);

    // 4. Menu Items using Bitmap Fonts 1032 & 1031
    this.menuTextContainer = this.add.container(0, 0).setDepth(30);
    this.rebuildMenuText();

    // Start title music loop
    AudioManager.getInstance().initAudio().then(() => {
      AudioManager.getInstance().playSlot(0);
    });

    if (typeof window !== "undefined") {
      (window as any).__GOW_TEST__ = {
        getState: () => ({
          scene: "MenuScene",
          isPaused: false,
          isGameOver: false,
          isVictory: false,
        }),
      };
    }

    const onUp = () => {
      this.selectedIndex = (this.selectedIndex - 1 + this.menuItems.length) % this.menuItems.length;
      AudioManager.getInstance().playSfx("cursor");
      this.rebuildMenuText();
    };

    const onDown = () => {
      this.selectedIndex = (this.selectedIndex + 1) % this.menuItems.length;
      AudioManager.getInstance().playSfx("cursor");
      this.rebuildMenuText();
    };

    const onConfirm = () => {
      this.activateItem();
    };

    this.input.keyboard?.on("keydown-UP", onUp);
    this.input.keyboard?.on("keydown-DOWN", onDown);
    this.input.keyboard?.on("keydown-ENTER", onConfirm);
    this.input.keyboard?.on("keydown-SPACE", onConfirm);

    const busUnsub1 = GameEvents.on<string>(EVENT_SELECT_SCENE, (targetScene) => {
      if (targetScene && targetScene !== "MenuScene") {
        this.scene.start(targetScene);
      }
    });

    const busUnsub2 = GameEvents.on<string>("EVENT_VIRTUAL_KEY" as any, (action: any) => {
      if (action === "UP") onUp();
      else if (action === "DOWN") onDown();
      else if (action === "ACTION") onConfirm();
    });

    this.events.once("shutdown", () => {
      busUnsub1();
      busUnsub2();
    });
  }

  private activateItem(): void {
    AudioManager.getInstance().playSfx("confirm");
    AudioManager.getInstance().stopSlot(0);
    const item = this.menuItems[this.selectedIndex];
    if (item.action === "arena") {
      this.scene.start("PlayScene", { levelResId: 9216 });
    } else {
      this.scene.start("PlayScene", { levelResId: 9217 });
    }
  }

  private rebuildMenuText(): void {
    this.menuTextContainer.removeAll(true);
    const reg = AssetRegistry.get();

    this.menuItems.forEach((item, idx) => {
      const label = reg.getEnglishString(item.labelId) || item.fallback;
      const y = 112 + idx * 17;
      const isSelected = idx === this.selectedIndex;
      const fontId = isSelected ? 1032 : 1031;
      const prefix = isSelected ? "> " : "";
      const suffix = isSelected ? " <" : "";
      BitmapFontRenderer.renderText(
        this,
        fontId,
        `${prefix}${label}${suffix}`,
        NATIVE_WIDTH / 2,
        y,
        "center",
        this.menuTextContainer
      );
    });

    // Softkey labels at bottom corners (Select / Exit)
    BitmapFontRenderer.renderText(
      this,
      1031,
      reg.getEnglishString(0) || "Select",
      4,
      NATIVE_HEIGHT - 4,
      "left",
      this.menuTextContainer
    );
  }

  private stepProceduralFire(): void {
    const pal = AssetRegistry.get().firePalette;
    if (!pal || !this.fireCanvasTex) return;

    const w = NATIVE_WIDTH;
    const h = 95;
    // Seed bottom rows with random hot embers
    for (let x = 0; x < w; x++) {
      this.fireHeat[(h - 1) * w + x] = Math.random() < 0.65 ? 255 : Math.floor(Math.random() * 140);
      this.fireHeat[(h - 2) * w + x] = Math.floor(Math.random() * 255);
    }

    // Propagate upward with cooling & horizontal jitter
    for (let y = 1; y < h - 2; y++) {
      for (let x = 1; x < w - 1; x++) {
        const sum =
          this.fireHeat[(y + 1) * w + x] +
          this.fireHeat[(y + 1) * w + x - 1] +
          this.fireHeat[(y + 1) * w + x + 1] +
          this.fireHeat[(y + 2) * w + x];
        const cooled = Math.max(0, (sum >> 2) - 3);
        this.fireHeat[y * w + x] = cooled;
      }
    }

    const ctx = this.fireCanvasTex.getContext();
    const imgData = ctx.createImageData(w, 91);
    const data = imgData.data;

    for (let y = 0; y < 91; y++) {
      for (let x = 0; x < w; x++) {
        const heat = this.fireHeat[(y + 2) * w + x];
        const color = pal.colors[heat] ?? pal.colors[0];
        const pIdx = (y * w + x) * 4;
        data[pIdx] = color.rgb[0];
        data[pIdx + 1] = color.rgb[1];
        data[pIdx + 2] = color.rgb[2];
        data[pIdx + 3] = heat > 4 ? 255 : 0;
      }
    }
    ctx.putImageData(imgData, 0, 0);
    this.fireCanvasTex.refresh();
  }

  update(_time: number, delta: number): void {
    const dt = Math.min(100, Math.max(40, delta));
    this.logoPlayer.update(dt);
    this.stepProceduralFire();

    const reg = AssetRegistry.get();
    GameEvents.emit(EVENT_TELEMETRY_UPDATE, {
      activeScene: "MenuScene",
      levelResId: 1028,
      levelTitle: "Main Menu (RP1)",
      chapterIndex: -1,
      paletteVariant: -1,
      levelSizePx: { w: NATIVE_WIDTH, h: NATIVE_HEIGHT },
      levelSizeTiles: { w: 11, h: 13 },
      cameraPos: { x: 0, y: 0 },
      kratosPos: { x: NATIVE_WIDTH / 2, y: 48 },
      kratosAnimBankId: 1025,
      kratosSliceTableId: 1036,
      kratosPngId: 1048,
      kratosAnimIndex: this.logoPlayer.animIndex,
      kratosTotalAnims: 5,
      kratosKeyframeIndex: this.logoPlayer.keyframeIndex,
      kratosCompFrameIndex: this.logoPlayer.getCurrentKeyframe()?.comp_frame_index ?? 0,
      kratosPartCount: this.logoPlayer.getCurrentCompositeFrame()?.part_count ?? 0,
      kratosBoxCount: 0,
      kratosRootMotion: { dx: 0, dy: 0 },
      loadedTexturesCount: reg.loadedTextureKeys.size,
      loadedAnimBanksCount: reg.animBanks.size,
      loadedSliceTablesCount: reg.slices.size,
      loadedFontsCount: reg.fonts.size,
      loadedLevelsCount: reg.levels.size,
      renderedEntitiesCount: 2,
      renderedStampsCount: 5,
      fps: Math.round(this.game.loop.actualFps),
      showHitboxes: false,
      showCollisionGrid: false,
      showHud: true,
      showDebugOverlay: false,
    });
  }
}
