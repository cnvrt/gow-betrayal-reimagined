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

/**
 * SplashScene: Displays the original Sony Pictures Mobile & WayForward splash
 * screens (`8192` / `8193` / `8194`) and the initial audio prompt (`1031` font).
 */
export class SplashScene extends Phaser.Scene {
  private splashPlayer!: CompositeAnimPlayer;
  private currentLogoAnim: number = 0;
  private timerMs: number = 0;
  private cleanupBus?: () => void;

  constructor() {
    super({ key: "SplashScene" });
  }

  create(): void {
    this.cameras.main.setBackgroundColor("#000000");
    this.currentLogoAnim = 0;
    this.timerMs = 0;

    // AnimBank 8192 -> Slice 8193 -> PNG 8194 (Splash Logos)
    this.splashPlayer = new CompositeAnimPlayer(this, 8192, -1, 0, 8193);
    this.splashPlayer.setPosition(NATIVE_WIDTH / 2, NATIVE_HEIGHT / 2 - 16);

    const promptContainer = this.add.container(0, 0);
    BitmapFontRenderer.renderText(
      this,
      1031,
      "God of War: Betrayal",
      NATIVE_WIDTH / 2,
      NATIVE_HEIGHT - 44,
      "center",
      promptContainer
    );
    BitmapFontRenderer.renderText(
      this,
      1031,
      "Press SPACE / Click for Menu",
      NATIVE_WIDTH / 2,
      NATIVE_HEIGHT - 22,
      "center",
      promptContainer
    );

    this.input.keyboard?.on("keydown-SPACE", () => {
      this.scene.start("MenuScene");
    });
    this.input.on("pointerdown", () => {
      this.scene.start("MenuScene");
    });

    this.cleanupBus = GameEvents.on<string>(EVENT_SELECT_SCENE, (targetScene) => {
      if (targetScene && targetScene !== "SplashScene") {
        this.scene.start(targetScene);
      }
    });
    this.events.once("shutdown", () => this.cleanupBus?.());
  }

  update(_time: number, delta: number): void {
    const dt = Math.min(100, Math.max(40, delta));
    this.splashPlayer.update(dt);
    this.timerMs += delta;
    if (this.timerMs > 2800) {
      this.timerMs = 0;
      this.currentLogoAnim = (this.currentLogoAnim + 1) % 2;
      this.splashPlayer.setAnimation(this.currentLogoAnim, true);
    }

    const reg = AssetRegistry.get();
    GameEvents.emit(EVENT_TELEMETRY_UPDATE, {
      activeScene: "SplashScene",
      levelResId: 8192,
      levelTitle: "Splash Logos (RP8)",
      chapterIndex: -1,
      paletteVariant: -1,
      levelSizePx: { w: NATIVE_WIDTH, h: NATIVE_HEIGHT },
      levelSizeTiles: { w: 11, h: 13 },
      cameraPos: { x: 0, y: 0 },
      kratosPos: { x: NATIVE_WIDTH / 2, y: NATIVE_HEIGHT / 2 - 16 },
      kratosAnimBankId: 8192,
      kratosSliceTableId: 8193,
      kratosPngId: 8194,
      kratosAnimIndex: this.currentLogoAnim,
      kratosTotalAnims: 2,
      kratosKeyframeIndex: this.splashPlayer.keyframeIndex,
      kratosCompFrameIndex: this.splashPlayer.getCurrentKeyframe()?.comp_frame_index ?? 0,
      kratosPartCount: this.splashPlayer.getCurrentCompositeFrame()?.part_count ?? 0,
      kratosBoxCount: 0,
      kratosRootMotion: { dx: 0, dy: 0 },
      loadedTexturesCount: reg.loadedTextureKeys.size,
      loadedAnimBanksCount: reg.animBanks.size,
      loadedSliceTablesCount: reg.slices.size,
      loadedFontsCount: reg.fonts.size,
      loadedLevelsCount: reg.levels.size,
      renderedEntitiesCount: 1,
      renderedStampsCount: 0,
      fps: Math.round(this.game.loop.actualFps),
      showHitboxes: false,
      showCollisionGrid: false,
      showHud: true,
      showDebugOverlay: false,
    });
  }
}
