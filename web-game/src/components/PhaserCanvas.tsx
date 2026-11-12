"use client";

import React, { useEffect, useRef, useState } from "react";

export default function PhaserCanvas() {
  const containerRef = useRef<HTMLDivElement | null>(null);
  const gameRef = useRef<any>(null);
  const [booted, setBooted] = useState(false);

  useEffect(() => {
    let cancelled = false;
    async function initPhaser() {
      if (!containerRef.current || gameRef.current) return;
      const PhaserModule = await import("phaser");
      const Phaser = PhaserModule.default ?? PhaserModule;
      const { createPhaserGameConfig } = await import("../game/config");
      if (cancelled || !containerRef.current) return;

      const config = createPhaserGameConfig(containerRef.current);
      gameRef.current = new Phaser.Game(config);
      setBooted(true);
    }

    initPhaser();

    return () => {
      cancelled = true;
      if (gameRef.current) {
        gameRef.current.destroy(true);
        gameRef.current = null;
      }
    };
  }, []);

  return (
    <div
      ref={containerRef}
      id="phaser-viewport"
      className="phaser-viewport-frame"
      aria-label="God of War: Betrayal 176x208 Native Canvas"
    >
      {!booted && (
        <div className="phaser-viewport-frame loading-placeholder">
          <span>INITIALIZING PHASER 3 (176×208)...</span>
        </div>
      )}
    </div>
  );
}
