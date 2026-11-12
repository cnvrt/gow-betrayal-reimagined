import * as Phaser from "phaser";
import {
  EVENT_VIRTUAL_KEY,
  EVENT_VIRTUAL_KEY_DOWN,
  EVENT_VIRTUAL_KEY_UP,
  GameEvents,
} from "./EventBus";

/**
 * Action codes matching `e.s` (single-tap) and `e.t` (held) in `e.i()` and `e.j()` (`e.java` lines 550-630):
 * - `0`: None
 * - `1`: Up / Jump (`KEY_NUM2`, `UP`)
 * - `2`: Left (`KEY_NUM4`, `LEFT`)
 * - `3`: Power Up Menu (`KEY_POUND` `#`)
 * - `5`: Right (`KEY_NUM6`, `RIGHT`)
 * - `6`: Down / Crouch / Interact (`KEY_NUM8`, `DOWN`)
 * - `8`: Fire / Attack / Grapple / Action (`KEY_NUM5`, `FIRE`)
 * - `10`: Cycle Weapon/Power (`KEY_STAR` `*`)
 * - `27`: Left Soft Key / Cycle Weapon (`LSK`)
 * - `29`: Right Soft Key / Pause (`RSK`)
 */
export type ActionCode = 0 | 1 | 2 | 3 | 5 | 6 | 8 | 10 | 27 | 29;

const KEY_CODE_TO_ACTION: Record<string, ActionCode> = {
  // Up / Jump (1)
  ArrowUp: 1,
  KeyW: 1,
  Numpad8: 1,
  Digit2: 1,
  // Left (2)
  ArrowLeft: 2,
  KeyA: 2,
  Numpad4: 2,
  Digit4: 2,
  // Right (5)
  ArrowRight: 5,
  KeyD: 5,
  Numpad6: 5,
  Digit6: 5,
  // Down / Crouch / Interact (6)
  ArrowDown: 6,
  KeyS: 6,
  Numpad2: 6,
  Digit8: 6,
  // Action / Attack / Grapple (8)
  Space: 8,
  Enter: 8,
  KeyZ: 8,
  KeyJ: 8,
  Numpad5: 8,
  Digit5: 8,
  // Cycle Weapon / Magic (27)
  KeyX: 27,
  KeyK: 27,
  KeyQ: 27,
  // Power Up Menu (3)
  KeyE: 3,
  // Pause / Menu (29)
  Escape: 29,
  KeyP: 29,
};

const VIRTUAL_NAME_TO_ACTION: Record<string, ActionCode> = {
  UP: 1,
  JUMP: 1,
  LEFT: 2,
  RIGHT: 5,
  DOWN: 6,
  CROUCH: 6,
  ACTION: 8,
  ATTACK: 8,
  WEAPON: 27,
  POWERUP: 3,
  PAUSE: 29,
};

export class InputManager {
  public s: ActionCode = 0; // Single-tick pressed action code (`e.s`)
  public t: ActionCode = 0; // Held action code (`e.t`)

  private heldKeys: Set<string> = new Set();
  private heldVirtual: Set<ActionCode> = new Set();
  private queuedPresses: ActionCode[] = [];
  private cleanupFns: (() => void)[] = [];

  constructor(_scene: Phaser.Scene) {
    if (typeof window !== "undefined") {
      const onKeyDown = (ev: KeyboardEvent) => {
        // Do not intercept if focus is inside an input/select element
        const tag = (ev.target as HTMLElement | null)?.tagName;
        if (tag === "INPUT" || tag === "SELECT" || tag === "TEXTAREA") return;

        const action = KEY_CODE_TO_ACTION[ev.code];
        if (!action) return;

        if (
          ev.code === "ArrowUp" ||
          ev.code === "ArrowDown" ||
          ev.code === "ArrowLeft" ||
          ev.code === "ArrowRight" ||
          ev.code === "Space"
        ) {
          ev.preventDefault();
        }

        if (!this.heldKeys.has(ev.code)) {
          this.heldKeys.add(ev.code);
          this.queuedPresses.push(action);
        }
      };

      const onKeyUp = (ev: KeyboardEvent) => {
        this.heldKeys.delete(ev.code);
      };

      const onBlur = () => {
        this.heldKeys.clear();
        this.heldVirtual.clear();
      };

      window.addEventListener("keydown", onKeyDown);
      window.addEventListener("keyup", onKeyUp);
      window.addEventListener("blur", onBlur);

      this.cleanupFns.push(() => {
        window.removeEventListener("keydown", onKeyDown);
        window.removeEventListener("keyup", onKeyUp);
        window.removeEventListener("blur", onBlur);
      });
    }

    // Virtual keypad events from React UI / Mobile Touch Controls
    this.cleanupFns.push(
      GameEvents.on<string>(EVENT_VIRTUAL_KEY_DOWN, (name) => {
        const action = VIRTUAL_NAME_TO_ACTION[name];
        if (action) {
          if (!this.heldVirtual.has(action)) {
            this.heldVirtual.add(action);
            this.queuedPresses.push(action);
          }
        }
      }),
      GameEvents.on<string>(EVENT_VIRTUAL_KEY_UP, (name) => {
        const action = VIRTUAL_NAME_TO_ACTION[name];
        if (action) {
          this.heldVirtual.delete(action);
        }
      }),
      GameEvents.on<string>(EVENT_VIRTUAL_KEY, (name) => {
        const action = VIRTUAL_NAME_TO_ACTION[name];
        if (action) {
          this.queuedPresses.push(action);
        }
      })
    );
  }

  /**
   * Computes `this.s` and `this.t` for the current game tick using the exact priority order
   * of `e.i()` and `e.j()` (`e.java` lines 550-630):
   * Priority: `8 (Action) > 5 (Right) > 2 (Left) > 1 (Up) > 6 (Down) > 10/27 (Weapon) > 3 (Power) > 29 (Pause)`
   */
  public poll(): void {
    // 1. Single-frame press `s`
    if (this.queuedPresses.length > 0) {
      this.s = this.queuedPresses.shift()!;
    } else {
      this.s = 0;
    }

    // 2. Held state `t`
    const activeActions = new Set<ActionCode>(this.heldVirtual);
    for (const code of this.heldKeys) {
      const act = KEY_CODE_TO_ACTION[code];
      if (act) activeActions.add(act);
    }

    // Priority order from e.j() (lines 596-625)
    if (activeActions.has(8)) this.t = 8;
    else if (activeActions.has(5)) this.t = 5;
    else if (activeActions.has(2)) this.t = 2;
    else if (activeActions.has(1)) this.t = 1;
    else if (activeActions.has(6)) this.t = 6;
    else if (activeActions.has(10)) this.t = 10;
    else if (activeActions.has(3)) this.t = 3;
    else if (activeActions.has(27)) this.t = 27;
    else if (activeActions.has(29)) this.t = 29;
    else this.t = 0;
  }

  public update(): void {
    this.poll();
  }

  public setVirtualKeyDown(name: string): void {
    const action = VIRTUAL_NAME_TO_ACTION[name];
    if (action && !this.heldVirtual.has(action)) {
      this.heldVirtual.add(action);
      this.queuedPresses.push(action);
    }
  }

  public setVirtualKeyUp(name: string): void {
    const action = VIRTUAL_NAME_TO_ACTION[name];
    if (action) {
      this.heldVirtual.delete(action);
    }
  }

  public pulseVirtualKey(name: string): void {
    const action = VIRTUAL_NAME_TO_ACTION[name];
    if (action) {
      this.queuedPresses.push(action);
    }
  }

  public getActiveSummary(): string {
    const names: Record<number, string> = {
      0: "NONE",
      1: "UP",
      2: "LEFT",
      3: "PWR",
      5: "RIGHT",
      6: "DOWN",
      8: "ACT",
      10: "WPN",
      27: "WPN",
      29: "PAUSE",
    };
    return `s:${this.s}(${names[this.s] || "?"}) t:${this.t}(${names[this.t] || "?"})`;
  }

  public clearAll(): void {
    this.s = 0;
    this.t = 0;
    this.queuedPresses = [];
    this.heldKeys.clear();
    this.heldVirtual.clear();
  }

  public destroy(): void {
    for (const fn of this.cleanupFns) fn();
    this.cleanupFns = [];
    this.clearAll();
  }
}
