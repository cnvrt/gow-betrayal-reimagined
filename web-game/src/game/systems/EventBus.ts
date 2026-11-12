import type { DebugTelemetry } from "../types/assets";

type Listener<T = any> = (payload: T) => void;

class TypedEventBus {
  private listeners: Map<string, Set<Listener>> = new Map();

  on<T = any>(event: string, fn: Listener<T>): () => void {
    if (!this.listeners.has(event)) {
      this.listeners.set(event, new Set());
    }
    this.listeners.get(event)!.add(fn);
    return () => {
      this.listeners.get(event)?.delete(fn);
    };
  }

  emit<T = any>(event: string, payload?: T): void {
    const set = this.listeners.get(event);
    if (!set) return;
    for (const fn of set) {
      fn(payload);
    }
  }
}

export const GameEvents = new TypedEventBus();

export const EVENT_TELEMETRY_UPDATE = "telemetry:update";
export const EVENT_SELECT_LEVEL = "control:select-level";
export const EVENT_SELECT_KRATOS_ANIM = "control:select-kratos-anim";
export const EVENT_SELECT_SCENE = "control:select-scene";
export const EVENT_TOGGLE_DEBUG_FLAG = "control:toggle-debug-flag";
export const EVENT_CAMERA_PAN = "control:camera-pan";
export const EVENT_VIRTUAL_KEY = "input:virtual-key";
export const EVENT_VIRTUAL_KEY_DOWN = "input:virtual-key-down";
export const EVENT_VIRTUAL_KEY_UP = "input:virtual-key-up";
export type { DebugTelemetry };
