import * as Phaser from "phaser";
import { NATIVE_HEIGHT, NATIVE_WIDTH } from "./constants";
import { BootScene } from "./scenes/BootScene";
import { MenuScene } from "./scenes/MenuScene";
import { PlayScene } from "./scenes/PlayScene";
import { SplashScene } from "./scenes/SplashScene";

export function createPhaserGameConfig(parent: HTMLElement): Phaser.Types.Core.GameConfig {
  return {
    type: Phaser.CANVAS,
    parent,
    width: NATIVE_WIDTH,
    height: NATIVE_HEIGHT,
    backgroundColor: "#000000",
    pixelArt: true,
    antialias: false,
    roundPixels: true,
    scale: {
      mode: Phaser.Scale.FIT,
      autoCenter: Phaser.Scale.CENTER_BOTH,
      width: NATIVE_WIDTH,
      height: NATIVE_HEIGHT,
    },
    scene: [BootScene, PlayScene, MenuScene, SplashScene],
  };
}
