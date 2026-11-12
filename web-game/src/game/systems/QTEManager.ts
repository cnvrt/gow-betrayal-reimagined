import * as Phaser from "phaser";
import { CompositeAnimPlayer } from "./CompositeAnimPlayer";

export type QTEStage = "IDLE" | "PROMPT" | "SUCCESS" | "FAIL";

/**
 * Directional Quick Time Event & Finisher System matching `e.java` (lines 5227-5278, 5570-5620):
 * - Prompts: Up (1), Left (2), Fire (8), Right (5), Down (6)
 * - Displayed using AnimBank `1027` (icons: `fY = [2, 4, 5, 6, 8]`)
 * - Slow-motion window (`hL = 1`)
 * - Multi-step sequences (Type 2 = 2 inputs, Type 3 = 3 inputs)
 */
export class QTEManager {
  private scene: Phaser.Scene;
  private promptPlayer: CompositeAnimPlayer;
  public stage: QTEStage = "IDLE";

  public activePromptAction: number = 0; // Action code required
  public promptAnimIndex: number = 0;
  public totalSteps: number = 2;
  public currentStep: number = 0;
  public timerMs: number = 0;
  public maxTimeMs: number = 2000;

  public targetEnemyIdx: number = -1;

  // Key codes mapping from fY = [2, 4, 5, 6, 8] in Bank 1027
  private static readonly PROMPT_ACTIONS = [1, 2, 8, 5, 6]; // Up, Left, Action, Right, Down
  private static readonly PROMPT_ANIMS = [2, 4, 5, 6, 8];

  constructor(scene: Phaser.Scene) {
    this.scene = scene;
    this.promptPlayer = new CompositeAnimPlayer(scene, 1027, -1, 0, 1040);
    this.promptPlayer.container.setDepth(65);
    this.promptPlayer.container.setVisible(false);
  }

  public startQTE(enemyIdx: number, steps: number, x: number, y: number): void {
    this.targetEnemyIdx = enemyIdx;
    this.totalSteps = steps;
    this.currentStep = 0;
    this.stage = "PROMPT";
    this.timerMs = 2500;
    this.promptPlayer.setPosition(x, y - 48);
    this.nextPrompt();
  }

  private nextPrompt(): void {
    const randIdx = Math.floor(Math.random() * QTEManager.PROMPT_ACTIONS.length);
    this.activePromptAction = QTEManager.PROMPT_ACTIONS[randIdx];
    this.promptAnimIndex = QTEManager.PROMPT_ANIMS[randIdx];
    this.promptPlayer.setAnimation(this.promptAnimIndex);
    this.promptPlayer.container.setVisible(true);
  }

  public handleInput(actionCode: number): "CORRECT" | "WRONG" | "NONE" {
    if (this.stage !== "PROMPT") return "NONE";

    if (actionCode === this.activePromptAction) {
      this.currentStep++;
      if (this.currentStep >= this.totalSteps) {
        this.stage = "SUCCESS";
        this.promptPlayer.container.setVisible(false);
        return "CORRECT";
      } else {
        this.timerMs = 2000;
        this.nextPrompt();
        return "CORRECT";
      }
    } else if (actionCode !== 0 && actionCode !== 27 && actionCode !== 29) {
      this.stage = "FAIL";
      this.promptPlayer.container.setVisible(false);
      return "WRONG";
    }

    return "NONE";
  }

  public update(dtMs: number): void {
    if (this.stage === "PROMPT") {
      this.promptPlayer.update(dtMs);
      this.timerMs -= dtMs;
      if (this.timerMs <= 0) {
        this.stage = "FAIL";
        this.promptPlayer.container.setVisible(false);
      }
    }
  }

  public cancel(): void {
    this.stage = "IDLE";
    this.targetEnemyIdx = -1;
    this.promptPlayer.container.setVisible(false);
  }

  public destroy(): void {
    this.promptPlayer.destroy();
  }
}
