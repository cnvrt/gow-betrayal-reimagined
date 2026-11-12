"use client";

import React, { useEffect, useState } from "react";
import { LEVEL_METADATA, NATIVE_HEIGHT, NATIVE_WIDTH } from "../game/constants";
import {
  EVENT_CAMERA_PAN,
  EVENT_SELECT_KRATOS_ANIM,
  EVENT_SELECT_LEVEL,
  EVENT_SELECT_SCENE,
  EVENT_TELEMETRY_UPDATE,
  EVENT_TOGGLE_DEBUG_FLAG,
  EVENT_VIRTUAL_KEY,
  EVENT_VIRTUAL_KEY_DOWN,
  EVENT_VIRTUAL_KEY_UP,
  GameEvents,
} from "../game/systems/EventBus";
import type { DebugTelemetry } from "../game/types/assets";
import PhaserCanvas from "./PhaserCanvas";

export default function GameHost() {
  const [telemetry, setTelemetry] = useState<DebugTelemetry | null>(null);

  useEffect(() => {
    return GameEvents.on<DebugTelemetry>(EVENT_TELEMETRY_UPDATE, (data) => {
      setTelemetry(data);
    });
  }, []);

  const currentLevelId = telemetry?.levelResId ?? 9217;
  const currentAnimIdx = telemetry?.kratosAnimIndex ?? 0;
  const maxAnims = telemetry?.kratosTotalAnims ?? 122;

  const bindHoldKey = (action: string) => ({
    onPointerDown: (e: React.PointerEvent) => {
      e.preventDefault();
      GameEvents.emit(EVENT_VIRTUAL_KEY_DOWN, action);
    },
    onPointerUp: () => GameEvents.emit(EVENT_VIRTUAL_KEY_UP, action),
    onPointerLeave: () => GameEvents.emit(EVENT_VIRTUAL_KEY_UP, action),
    onPointerCancel: () => GameEvents.emit(EVENT_VIRTUAL_KEY_UP, action),
  });

  return (
    <main className="app-shell">
      <header className="top-banner">
        <div className="brand-block">
          <span className="badge-phase">PHASE 6 AUDIO, MENUS &amp; TOUCH</span>
          <h1>GOD OF WAR: BETRAYAL (2007 J2ME)</h1>
        </div>
        <div className="specs-pill">
          Native Viewport: <strong>{NATIVE_WIDTH} × {NATIVE_HEIGHT}</strong> (11:13 Aspect Locked • Pixel-Art Nearest)
        </div>
      </header>

      <section className="workspace-grid">
        {/* Left / Center: Handset Frame + Virtual Keypad */}
        <div className="handset-column">
          <div className="handset-bezel">
            <div className="handset-speaker" />
            <div className="canvas-aspect-box">
              <PhaserCanvas />
            </div>
            <div className="handset-label">SONY ERICSSON / NOKIA S60 • 176×208</div>
          </div>

          {/* Virtual D-Pad & Action Controls for Mobile & Desktop */}
          <div className="virtual-pad-bar">
            <div className="dpad-cluster">
              <button
                type="button"
                className="pad-btn"
                {...bindHoldKey("UP")}
                title="Jump / Double Jump / Climb Up (W / Up / 2)"
              >
                ▲ JUMP (2)
              </button>
              <div className="dpad-mid-row">
                <button
                  type="button"
                  className="pad-btn"
                  {...bindHoldKey("LEFT")}
                  title="Run Left / Climb Turn (A / Left / 4)"
                >
                  ◄ LEFT (4)
                </button>
                <button
                  type="button"
                  className="pad-btn action-btn"
                  {...bindHoldKey("ACTION")}
                  title="Attack / Action (Space / Z / J / 5)"
                >
                  ⚔ ACT (5)
                </button>
                <button
                  type="button"
                  className="pad-btn"
                  {...bindHoldKey("RIGHT")}
                  title="Run Right / Climb Turn (D / Right / 6)"
                >
                  ► RIGHT (6)
                </button>
              </div>
              <div className="dpad-mid-row">
                <button
                  type="button"
                  className="pad-btn"
                  {...bindHoldKey("DOWN")}
                  title="Crouch / Open Chest / Pull Lever / Drop (S / Down / 8)"
                >
                  ▼ DOWN (8)
                </button>
                <button
                  type="button"
                  className="pad-btn"
                  onClick={() => GameEvents.emit(EVENT_VIRTUAL_KEY, "WEAPON")}
                  title="Cycle Weapon / Magic (X / K / *)"
                >
                  ★ WPN (*)
                </button>
              </div>
            </div>
          </div>
        </div>

        {/* Right: Phase 4 State Machine, Physics & Asset Inspector Panel */}
        <aside className="debug-panel">
          <div className="panel-card">
            <h2>SCENE &amp; LEVEL SELECTOR</h2>
            <div className="scene-tabs">
              {(["PlayScene", "MenuScene", "SplashScene"] as const).map((sc) => (
                <button
                  key={sc}
                  type="button"
                  className={`tab-btn ${telemetry?.activeScene === sc ? "active" : ""}`}
                  onClick={() => GameEvents.emit(EVENT_SELECT_SCENE, sc)}
                >
                  {sc.replace("Scene", "")}
                </button>
              ))}
            </div>

            <label className="control-label" htmlFor="level-select">
              Decoded Level Bitstream (type=245):
            </label>
            <select
              id="level-select"
              className="control-select"
              value={currentLevelId}
              onChange={(e) => {
                GameEvents.emit(EVENT_SELECT_SCENE, "PlayScene");
                GameEvents.emit(EVENT_SELECT_LEVEL, Number(e.target.value));
              }}
            >
              {LEVEL_METADATA.map((lvl) => (
                <option key={lvl.resId} value={lvl.resId}>
                  [{lvl.resId}] {lvl.title} (Pal: {lvl.paletteVariant === -1 ? "Default" : lvl.paletteVariant})
                </option>
              ))}
            </select>
          </div>

          <div className="panel-card">
            <h2>KRATOS ANIMATION INSPECTOR (28672)</h2>
            <div className="anim-stepper">
              <button
                type="button"
                className="ctrl-btn"
                onClick={() => GameEvents.emit(EVENT_SELECT_KRATOS_ANIM, currentAnimIdx - 1)}
              >
                [ Prev
              </button>
              <span className="anim-counter">
                Anim <strong>#{currentAnimIdx}</strong> / {maxAnims - 1}
              </span>
              <button
                type="button"
                className="ctrl-btn"
                onClick={() => GameEvents.emit(EVENT_SELECT_KRATOS_ANIM, currentAnimIdx + 1)}
              >
                Next ]
              </button>
            </div>
            <input
              type="range"
              min={0}
              max={Math.max(1, maxAnims - 1)}
              value={currentAnimIdx}
              onChange={(e) => GameEvents.emit(EVENT_SELECT_KRATOS_ANIM, Number(e.target.value))}
              className="anim-slider"
            />
          </div>

          <div className="panel-card">
            <h2>CAMERA &amp; LAYER TOGGLES</h2>
            <div className="toggle-grid">
              <button
                type="button"
                className={`ctrl-btn ${telemetry?.showHitboxes ? "active" : ""}`}
                onClick={() => GameEvents.emit(EVENT_TOGGLE_DEBUG_FLAG, "hitboxes")}
              >
                Hitboxes (H)
              </button>
              <button
                type="button"
                className={`ctrl-btn ${telemetry?.showCollisionGrid ? "active" : ""}`}
                onClick={() => GameEvents.emit(EVENT_TOGGLE_DEBUG_FLAG, "collision")}
              >
                Collision Grid (G)
              </button>
              <button
                type="button"
                className={`ctrl-btn ${telemetry?.showHud ? "active" : ""}`}
                onClick={() => GameEvents.emit(EVENT_TOGGLE_DEBUG_FLAG, "hud")}
              >
                HUD Layer
              </button>
              <button
                type="button"
                className={`ctrl-btn ${telemetry?.showDebugOverlay ? "active" : ""}`}
                onClick={() => GameEvents.emit(EVENT_TOGGLE_DEBUG_FLAG, "debugOverlay")}
              >
                Canvas Telemetry
              </button>
            </div>

            <div className="cam-pan-row">
              <button
                type="button"
                className="ctrl-btn"
                onClick={() => GameEvents.emit(EVENT_CAMERA_PAN, { dx: -64, dy: 0 })}
              >
                Cam ◄
              </button>
              <button
                type="button"
                className="ctrl-btn"
                onClick={() => GameEvents.emit(EVENT_CAMERA_PAN, { dx: 0, dy: -64 })}
              >
                Cam ▲
              </button>
              <button
                type="button"
                className="ctrl-btn"
                onClick={() => GameEvents.emit(EVENT_TOGGLE_DEBUG_FLAG, "resetCamera")}
              >
                Track Kratos
              </button>
              <button
                type="button"
                className="ctrl-btn"
                onClick={() => GameEvents.emit(EVENT_CAMERA_PAN, { dx: 0, dy: 64 })}
              >
                Cam ▼
              </button>
              <button
                type="button"
                className="ctrl-btn"
                onClick={() => GameEvents.emit(EVENT_CAMERA_PAN, { dx: 64, dy: 0 })}
              >
                Cam ►
              </button>
            </div>
          </div>

          <div className="panel-card telemetry-card">
            <h2>LIVE PHYSICS &amp; RENDER TELEMETRY</h2>
            {telemetry ? (
              <dl className="telemetry-list">
                <div>
                  <dt>Active Scene</dt>
                  <dd>{telemetry.activeScene}</dd>
                </div>
                <div>
                  <dt>Kratos State (e.hE)</dt>
                  <dd>
                    <strong>
                      #{telemetry.kratosStateId ?? 0} ({telemetry.kratosStateName ?? "IDLE"})
                    </strong>
                  </dd>
                </div>
                <div>
                  <dt>Sensor &amp; Posture Flags</dt>
                  <dd>{telemetry.kratosFlags ?? "GROUND"}</dd>
                </div>
                <div>
                  <dt>Input State (e.s / e.t)</dt>
                  <dd>{telemetry.activeInput ?? "s:0(NONE) t:0(NONE)"}</dd>
                </div>
                <div>
                  <dt>Kratos World &amp; Tile</dt>
                  <dd>
                    px({telemetry.kratosPos.x}, {telemetry.kratosPos.y}) • tile(
                    {telemetry.kratosTilePos?.tx ?? 0}, {telemetry.kratosTilePos?.ty ?? 0}) • sub(
                    {telemetry.kratosSubTile?.hl ?? 0}, {telemetry.kratosSubTile?.hm ?? 0})
                  </dd>
                </div>
                <div>
                  <dt>Root Motion Step (a, b)</dt>
                  <dd>
                    dx={telemetry.kratosRootMotion.dx}, dy={telemetry.kratosRootMotion.dy}
                  </dd>
                </div>
                <div>
                  <dt>Camera Scroll (iV, iW)</dt>
                  <dd>
                    ({telemetry.cameraPos.x}, {telemetry.cameraPos.y})
                  </dd>
                </div>
                <div>
                  <dt>Level Resource ID</dt>
                  <dd>
                    {telemetry.levelResId} ({telemetry.levelTitle})
                  </dd>
                </div>
                <div>
                  <dt>Active Anim / KF / CF</dt>
                  <dd>
                    Seq #{telemetry.kratosAnimIndex} • KF #{telemetry.kratosKeyframeIndex} → CF #
                    {telemetry.kratosCompFrameIndex} ({telemetry.kratosPartCount} parts,{" "}
                    {telemetry.kratosBoxCount} boxes)
                  </dd>
                </div>
                <div>
                  <dt>Rendered Stamps / Entities</dt>
                  <dd>
                    {telemetry.renderedStampsCount} stamp parts • {telemetry.renderedEntitiesCount} anim players
                  </dd>
                </div>
              </dl>
            ) : (
              <p className="muted-status">Waiting for PhaserBootScene telemetry...</p>
            )}
          </div>
        </aside>
      </section>
    </main>
  );
}
