import { AssetRegistry } from "./AssetRegistry";

export interface SoundSlot {
  resId: number;
  slug: string;
  loopCount: number;
  priority: number;
  buffer: AudioBuffer | null;
}

export interface MidiNoteEvent {
  timeSec: number;
  durationSec: number;
  midiNote: number;
  velocity: number;
  channel: number;
}

export interface AudioRuntimeStats {
  contextState: string;
  buffersLoaded: number;
  midiLoaded: boolean;
  midiTotalNotes: number;
  midiDurationSec: number;
  bgmPlaying: boolean;
  bgmCurrentTimeSec: number;
  bgmNotesTriggered: number;
  bgmLoopCount: number;
  sfxPlayCounts: Record<string, number>;
  resIdPlayCounts: Record<number, number>;
}

/**
 * Authentic Audio Manager implementing `d.java` audio pipeline:
 * - Authentic SMF Type 1 MIDI Parser for resource 1063 (`1063_music_00_title_theme.mid`)
 * - Web Audio polyphonic envelope synthesizer sequencer for BGM
 * - Web Audio PCM buffer player for SFX (`1054`..`1062`, `1064`)
 * - Live audio telemetry via `getStats()` and `(window as any).__GOW_AUDIO_STATS__`
 */
export class AudioManager {
  private static instance: AudioManager | null = null;

  private ctx: AudioContext | null = null;
  private soundSlots: Map<number, SoundSlot> = new Map();
  private activeSources: Map<number, AudioBufferSourceNode> = new Map();

  public isMuted: boolean = false;
  public masterVolume: number = 0.8;
  public isInitialized: boolean = false;

  // MIDI state & parsed notes
  private parsedMidiNotes: MidiNoteEvent[] = [];
  private midiTotalDurationSec: number = 0;
  private isMidiLoaded: boolean = false;

  // BGM Sequencer state
  private isBgmPlaying: boolean = false;
  private bgmStartTime: number = 0;
  private bgmCurrentTimeSec: number = 0;
  private bgmNotesTriggered: number = 0;
  private bgmLoopCount: number = 0;
  private bgmScheduledIndex: number = 0;
  private bgmSchedulerTimer: ReturnType<typeof setInterval> | null = null;
  private activeBgmMidiNodes: Set<{ stop: () => void; disconnect: () => void }> = new Set();

  // Telemetry play counts
  private sfxPlayCounts: Record<string, number> = {
    cursor: 0,
    confirm: 0,
    slash1: 0,
    slash2: 0,
    impact: 0,
    hit: 0,
    grunt: 0,
    orb: 0,
    fanfare: 0,
    horn: 0,
  };

  private resIdPlayCounts: Record<number, number> = {
    1054: 0,
    1055: 0,
    1056: 0,
    1057: 0,
    1058: 0,
    1059: 0,
    1060: 0,
    1061: 0,
    1062: 0,
    1063: 0,
    1064: 0,
  };

  public static get(): AudioManager {
    if (!AudioManager.instance) {
      AudioManager.instance = new AudioManager();
    }
    return AudioManager.instance;
  }

  public static getInstance(): AudioManager {
    return AudioManager.get();
  }

  constructor() {
    this.initSlots();
    this.updateGlobalTelemetry();
  }

  private initSlots(): void {
    // Exact slots from e.java line 1482-1484 (iR.a(slot, resId, loops, priority)):
    // Slot 0: Title theme 1063
    this.soundSlots.set(0, {
      resId: 1063,
      slug: "music_00_title_theme",
      loopCount: -1,
      priority: 0,
      buffer: null,
    });
    // Slot 1: Level intro horn 1064
    this.soundSlots.set(1, {
      resId: 1064,
      slug: "sfx_10_level_intro_horn",
      loopCount: 1,
      priority: 0,
      buffer: null,
    });
    // Slot 2: QTE finisher fanfare 1062
    this.soundSlots.set(2, {
      resId: 1062,
      slug: "sfx_09_qte_finisher_fanfare",
      loopCount: 1,
      priority: 0,
      buffer: null,
    });

    // SFX slots
    const sfxList = [
      { id: 1054, slug: "1054_sfx_01_blade_slash_1.wav", name: "slash1" },
      { id: 1055, slug: "1055_sfx_02_blade_slash_2.wav", name: "slash2" },
      { id: 1056, slug: "1056_sfx_03_heavy_impact.wav", name: "impact" },
      { id: 1057, slug: "1057_sfx_04_flesh_hit.wav", name: "hit" },
      { id: 1058, slug: "1058_sfx_05_damage_grunt.wav", name: "grunt" },
      { id: 1059, slug: "1059_sfx_06_magic_orb_pickup.wav", name: "orb" },
      { id: 1060, slug: "1060_sfx_07_menu_cursor_tick.wav", name: "cursor" },
      { id: 1061, slug: "1061_sfx_08_menu_confirm_block.wav", name: "confirm" },
    ];

    sfxList.forEach((sfx, idx) => {
      this.soundSlots.set(10 + idx, {
        resId: sfx.id,
        slug: sfx.slug,
        loopCount: 1,
        priority: 1,
        buffer: null,
      });
    });
  }

  public async initAudio(): Promise<void> {
    if (this.isInitialized || typeof window === "undefined") return;

    try {
      const AudioCtx = window.AudioContext || (window as any).webkitAudioContext;
      if (!AudioCtx) return;
      this.ctx = new AudioCtx();
      this.isInitialized = true;

      // Unlock on resume
      if (this.ctx.state === "suspended") {
        const unlock = () => {
          this.ctx?.resume().then(() => this.updateGlobalTelemetry());
          window.removeEventListener("click", unlock);
          window.removeEventListener("keydown", unlock);
          window.removeEventListener("touchstart", unlock);
        };
        window.addEventListener("click", unlock);
        window.addEventListener("keydown", unlock);
        window.addEventListener("touchstart", unlock);
      }

      await this.loadAllBuffers();
      this.updateGlobalTelemetry();
    } catch (e) {
      console.warn("[AudioManager] Web Audio init failed:", e);
    }
  }

  private async loadAllBuffers(): Promise<void> {
    if (!this.ctx) return;

    // 1. Fetch & parse MIDI 1063
    try {
      const midiRes = await fetch("/assets/extracted/audio/midi/1063_music_00_title_theme.mid");
      if (midiRes.ok) {
        const arrayBuf = await midiRes.arrayBuffer();
        const parsed = this.parseMidi(arrayBuf);
        this.parsedMidiNotes = parsed.notes;
        this.midiTotalDurationSec = parsed.totalDurationSec;
        this.isMidiLoaded = true;
      }
    } catch (err) {
      console.warn("[AudioManager] Failed to load MIDI:", err);
    }

    // 2. Load WAV SFX buffers
    for (const [slotId, slot] of this.soundSlots.entries()) {
      if (slot.resId === 1063) continue;
      try {
        let filePath = "";
        if (slot.resId === 1064) {
          filePath = "/assets/extracted/audio/wav/1064_sfx_10_level_intro_horn.wav";
        } else if (slot.resId === 1062) {
          filePath = "/assets/extracted/audio/wav/1062_sfx_09_qte_finisher_fanfare.wav";
        } else {
          filePath = `/assets/extracted/audio/wav/${slot.slug}`;
        }

        const res = await fetch(filePath);
        if (!res.ok) continue;
        const arrayBuf = await res.arrayBuffer();
        const audioBuf = await this.ctx.decodeAudioData(arrayBuf);
        slot.buffer = audioBuf;
      } catch (err) {
        // Fallback or silent fail
      }
    }

    this.updateGlobalTelemetry();
  }

  /**
   * Authentic SMF Type 0/1 binary parser.
   */
  private parseMidi(buffer: ArrayBuffer): { notes: MidiNoteEvent[]; totalDurationSec: number } {
    const data = new Uint8Array(buffer);
    if (data.length < 14) return { notes: [], totalDurationSec: 0 };

    // Check MThd
    if (
      data[0] !== 0x4d ||
      data[1] !== 0x54 ||
      data[2] !== 0x68 ||
      data[3] !== 0x64
    ) {
      return { notes: [], totalDurationSec: 0 };
    }

    const division = (data[12] << 8) | data[13];
    const ppqn = division > 0 ? division : 960;

    // Collect MTrk chunks
    let offset = 14;
    const tracks: Uint8Array[] = [];
    while (offset + 8 <= data.length) {
      const isMTrk =
        data[offset] === 0x4d &&
        data[offset + 1] === 0x54 &&
        data[offset + 2] === 0x72 &&
        data[offset + 3] === 0x6b;
      const chunkLen =
        (data[offset + 4] << 24) |
        (data[offset + 5] << 16) |
        (data[offset + 6] << 8) |
        data[offset + 7];
      offset += 8;
      if (isMTrk && offset + chunkLen <= data.length) {
        tracks.push(data.subarray(offset, offset + chunkLen));
      }
      offset += chunkLen;
    }

    function readVarLen(trk: Uint8Array, pos: number): { value: number; bytesRead: number } {
      let value = 0;
      let bytesRead = 0;
      while (pos < trk.length) {
        const byte = trk[pos++];
        bytesRead++;
        value = (value << 7) | (byte & 0x7f);
        if ((byte & 0x80) === 0) break;
      }
      return { value, bytesRead };
    }

    // Pass 1: Collect tempo changes across all tracks
    const tempoChanges: { tick: number; usPerBeat: number }[] = [
      { tick: 0, usPerBeat: 500000 },
    ];

    tracks.forEach((trk) => {
      let pos = 0;
      let currentTick = 0;
      let runningStatus = 0;

      while (pos < trk.length) {
        const delta = readVarLen(trk, pos);
        pos += delta.bytesRead;
        currentTick += delta.value;

        if (pos >= trk.length) break;
        let status = trk[pos];
        if (status >= 0x80) {
          runningStatus = status;
          pos++;
        } else {
          status = runningStatus;
        }

        if (status === 0xff) {
          if (pos >= trk.length) break;
          const metaType = trk[pos++];
          const len = readVarLen(trk, pos);
          pos += len.bytesRead;
          if (metaType === 0x51 && len.value === 3 && pos + 3 <= trk.length) {
            const usPerBeat = (trk[pos] << 16) | (trk[pos + 1] << 8) | trk[pos + 2];
            tempoChanges.push({ tick: currentTick, usPerBeat });
          }
          pos += len.value;
        } else if (status === 0xf0 || status === 0xf7) {
          const len = readVarLen(trk, pos);
          pos += len.bytesRead + len.value;
        } else {
          const cmd = status & 0xf0;
          if (cmd === 0xc0 || cmd === 0xd0) {
            pos += 1;
          } else {
            pos += 2;
          }
        }
      }
    });

    tempoChanges.sort((a, b) => a.tick - b.tick);

    function tickToSeconds(targetTick: number): number {
      let sec = 0;
      let lastTick = 0;
      let currentUs = 500000;
      for (const tc of tempoChanges) {
        if (targetTick <= tc.tick) break;
        sec += ((tc.tick - lastTick) * currentUs) / (ppqn * 1000000);
        lastTick = tc.tick;
        currentUs = tc.usPerBeat;
      }
      sec += ((targetTick - lastTick) * currentUs) / (ppqn * 1000000);
      return sec;
    }

    // Pass 2: Extract notes
    const rawNotes: {
      channel: number;
      note: number;
      velocity: number;
      startTick: number;
      endTick: number;
    }[] = [];

    tracks.forEach((trk) => {
      let pos = 0;
      let currentTick = 0;
      let runningStatus = 0;
      const activeNotes = new Map<number, { tick: number; vel: number; channel: number; note: number }>();

      while (pos < trk.length) {
        const delta = readVarLen(trk, pos);
        pos += delta.bytesRead;
        currentTick += delta.value;

        if (pos >= trk.length) break;
        let status = trk[pos];
        if (status >= 0x80) {
          runningStatus = status;
          pos++;
        } else {
          status = runningStatus;
        }

        if (status === 0xff) {
          if (pos >= trk.length) break;
          const metaType = trk[pos++];
          const len = readVarLen(trk, pos);
          pos += len.bytesRead;
          if (metaType === 0x2f) {
            // End of track
            pos += len.value;
            break;
          }
          pos += len.value;
        } else if (status === 0xf0 || status === 0xf7) {
          const len = readVarLen(trk, pos);
          pos += len.bytesRead + len.value;
        } else {
          const cmd = status & 0xf0;
          const channel = status & 0x0f;
          if (cmd === 0x90) {
            const note = trk[pos++];
            const vel = trk[pos++];
            const key = (channel << 8) | note;
            if (vel > 0) {
              activeNotes.set(key, { tick: currentTick, vel, channel, note });
            } else {
              const act = activeNotes.get(key);
              if (act) {
                rawNotes.push({
                  channel,
                  note,
                  velocity: act.vel,
                  startTick: act.tick,
                  endTick: currentTick,
                });
                activeNotes.delete(key);
              }
            }
          } else if (cmd === 0x80) {
            const note = trk[pos++];
            pos++; // velocity ignored
            const key = (channel << 8) | note;
            const act = activeNotes.get(key);
            if (act) {
              rawNotes.push({
                channel,
                note,
                velocity: act.vel,
                startTick: act.tick,
                endTick: currentTick,
              });
              activeNotes.delete(key);
            }
          } else if (cmd === 0xc0 || cmd === 0xd0) {
            pos += 1;
          } else {
            pos += 2;
          }
        }
      }

      // Close dangling active notes
      for (const [, act] of activeNotes) {
        rawNotes.push({
          channel: act.channel,
          note: act.note,
          velocity: act.vel,
          startTick: act.tick,
          endTick: Math.max(act.tick + Math.round(ppqn * 0.5), currentTick),
        });
      }
    });

    const parsedNotes: MidiNoteEvent[] = rawNotes
      .map((n) => {
        const startSec = tickToSeconds(n.startTick);
        const endSec = tickToSeconds(n.endTick);
        return {
          timeSec: startSec,
          durationSec: Math.max(0.06, endSec - startSec),
          midiNote: n.note,
          velocity: n.velocity,
          channel: n.channel,
        };
      })
      .sort((a, b) => a.timeSec - b.timeSec);

    let maxEndSec = 0;
    for (const n of parsedNotes) {
      if (n.timeSec + n.durationSec > maxEndSec) {
        maxEndSec = n.timeSec + n.durationSec;
      }
    }

    return {
      notes: parsedNotes,
      totalDurationSec: maxEndSec > 0 ? maxEndSec : 28.0,
    };
  }

  /**
   * Play sound in slot matching `e.z(int)` / `d.a(int)` in e.java line 1220.
   */
  public playSlot(slotId: number): void {
    if (this.isMuted) return;

    if (!this.ctx && typeof window !== "undefined") {
      this.initAudio().then(() => this.playSlot(slotId));
      return;
    }

    if (!this.ctx) return;

    if (this.ctx.state === "suspended") {
      this.ctx.resume().then(() => this.updateGlobalTelemetry());
    }

    const slot = this.soundSlots.get(slotId);
    if (!slot) return;

    // Track resId play count
    this.resIdPlayCounts[slot.resId] = (this.resIdPlayCounts[slot.resId] || 0) + 1;

    // Map slot to sfxName if applicable
    const slotToSfx: Record<number, string> = {
      0: "music",
      1: "horn",
      2: "fanfare",
      10: "slash1",
      11: "slash2",
      12: "impact",
      13: "hit",
      14: "grunt",
      15: "orb",
      16: "cursor",
      17: "confirm",
    };
    const sfxName = slotToSfx[slotId];
    if (sfxName && sfxName !== "music") {
      this.sfxPlayCounts[sfxName] = (this.sfxPlayCounts[sfxName] || 0) + 1;
    }

    if (slot.resId === 1063) {
      this.playSynthBGM();
      this.updateGlobalTelemetry();
      return;
    }

    if (!slot.buffer) {
      this.updateGlobalTelemetry();
      return;
    }

    // Stop existing sound in slot if playing
    this.stopSlot(slotId);

    const src = this.ctx.createBufferSource();
    src.buffer = slot.buffer;
    src.loop = slot.loopCount === -1;

    const gainNode = this.ctx.createGain();
    gainNode.gain.value = this.masterVolume;

    src.connect(gainNode);
    gainNode.connect(this.ctx.destination);

    src.start(0);
    this.activeSources.set(slotId, src);

    src.onended = () => {
      this.activeSources.delete(slotId);
    };

    this.updateGlobalTelemetry();
  }

  public stopSlot(slotId: number): void {
    if (slotId === 0) {
      this.stopBGM();
      return;
    }

    const src = this.activeSources.get(slotId);
    if (src) {
      try {
        src.stop();
        src.disconnect();
      } catch (e) {}
      this.activeSources.delete(slotId);
    }
    this.updateGlobalTelemetry();
  }

  public stopAll(): void {
    this.stopBGM();
    for (const [slotId] of this.activeSources) {
      this.stopSlot(slotId);
    }
    this.updateGlobalTelemetry();
  }

  /**
   * Synthesize God of War Title Theme BGM using parsed SMF Type 1 MIDI events.
   * Uses lookahead scheduling window (150ms) via setInterval.
   */
  public playSynthBGM(): void {
    if (this.isMuted || !this.ctx) return;

    if (this.ctx.state === "suspended") {
      this.ctx.resume().then(() => this.updateGlobalTelemetry());
    }

    if (this.isBgmPlaying) return;

    this.isBgmPlaying = true;
    this.bgmStartTime = this.ctx.currentTime;
    this.bgmCurrentTimeSec = 0;
    this.bgmScheduledIndex = 0;

    // Start lookahead scheduler (runs every 40ms, schedules 150ms in advance)
    if (this.bgmSchedulerTimer) {
      clearInterval(this.bgmSchedulerTimer);
    }

    this.bgmSchedulerTimer = setInterval(() => {
      this.tickBgmScheduler();
    }, 40);

    // Initial tick immediately
    this.tickBgmScheduler();
    this.updateGlobalTelemetry();
  }

  private stopBGM(): void {
    if (this.bgmSchedulerTimer) {
      clearInterval(this.bgmSchedulerTimer);
      this.bgmSchedulerTimer = null;
    }
    for (const node of this.activeBgmMidiNodes) {
      try {
        node.stop();
        node.disconnect();
      } catch (e) {}
    }
    this.activeBgmMidiNodes.clear();
    this.isBgmPlaying = false;
    this.updateGlobalTelemetry();
  }

  private tickBgmScheduler(): void {
    if (!this.ctx || !this.isBgmPlaying) return;

    const now = this.ctx.currentTime;
    let elapsed = now - this.bgmStartTime;

    // Loop check
    if (this.midiTotalDurationSec > 0 && elapsed >= this.midiTotalDurationSec) {
      this.bgmStartTime = now;
      elapsed = 0;
      this.bgmScheduledIndex = 0;
      this.bgmLoopCount++;
    }

    this.bgmCurrentTimeSec = elapsed;

    if (this.parsedMidiNotes.length === 0) {
      // Fallback drone if MIDI failed to load
      if (this.activeBgmMidiNodes.size === 0) {
        this.playFallbackDrone();
      }
      this.updateGlobalTelemetry();
      return;
    }

    // Schedule notes in window [elapsed, elapsed + 0.150]
    const lookaheadSec = 0.15;
    const windowEnd = elapsed + lookaheadSec;

    while (this.bgmScheduledIndex < this.parsedMidiNotes.length) {
      const note = this.parsedMidiNotes[this.bgmScheduledIndex];
      if (note.timeSec > windowEnd) {
        break;
      }

      if (note.timeSec >= elapsed - 0.05) {
        const noteAudioStartTime = this.bgmStartTime + note.timeSec;
        this.scheduleMidiNote(note, noteAudioStartTime);
        this.bgmNotesTriggered++;
      }
      this.bgmScheduledIndex++;
    }

    this.updateGlobalTelemetry();
  }

  private scheduleMidiNote(note: MidiNoteEvent, startTime: number): void {
    if (!this.ctx || this.isMuted) return;

    try {
      const osc = this.ctx.createOscillator();
      const gain = this.ctx.createGain();

      const freq = 440 * Math.pow(2, (note.midiNote - 69) / 12);
      osc.frequency.setValueAtTime(freq, startTime);

      if (note.channel === 9) {
        // Percussion channel
        osc.type = "sine";
        osc.frequency.setValueAtTime(freq * 0.5, startTime);
      } else if (note.midiNote < 48) {
        // Bass / low horn
        osc.type = "sawtooth";
      } else {
        // Melodic strings / brass
        osc.type = "triangle";
      }

      const noteVol = (note.velocity / 127) * this.masterVolume * 0.12;
      const attackTime = 0.01;
      const duration = Math.min(Math.max(note.durationSec, 0.08), 2.5);
      const stopTime = startTime + duration;

      gain.gain.setValueAtTime(0.0001, startTime);
      gain.gain.linearRampToValueAtTime(noteVol, startTime + attackTime);
      gain.gain.exponentialRampToValueAtTime(0.0001, stopTime);

      osc.connect(gain);
      gain.connect(this.ctx.destination);

      osc.start(startTime);
      osc.stop(stopTime);

      const nodeRef = {
        stop: () => {
          try {
            osc.stop();
          } catch (e) {}
        },
        disconnect: () => {
          try {
            osc.disconnect();
            gain.disconnect();
          } catch (e) {}
        },
      };

      this.activeBgmMidiNodes.add(nodeRef);
      osc.onended = () => {
        this.activeBgmMidiNodes.delete(nodeRef);
        nodeRef.disconnect();
      };
    } catch (e) {
      // Audio node scheduling guard
    }
  }

  private playFallbackDrone(): void {
    if (!this.ctx) return;
    const osc = this.ctx.createOscillator();
    const gain = this.ctx.createGain();
    osc.type = "sawtooth";
    osc.frequency.setValueAtTime(55, this.ctx.currentTime);
    gain.gain.setValueAtTime(this.masterVolume * 0.1, this.ctx.currentTime);
    osc.connect(gain);
    gain.connect(this.ctx.destination);
    osc.start();
    const nodeRef = {
      stop: () => osc.stop(),
      disconnect: () => {
        osc.disconnect();
        gain.disconnect();
      },
    };
    this.activeBgmMidiNodes.add(nodeRef);
  }

  public playSfx(sfxName: string): void {
    const mapping: Record<string, number> = {
      slash1: 10,
      slash2: 11,
      impact: 12,
      hit: 13,
      grunt: 14,
      orb: 15,
      cursor: 16,
      confirm: 17,
      fanfare: 2,
      horn: 1,
    };

    const slot = mapping[sfxName];
    if (slot !== undefined) {
      this.playSlot(slot);
    }
  }

  public toggleMute(): boolean {
    this.isMuted = !this.isMuted;
    if (this.isMuted) {
      this.stopAll();
    }
    this.updateGlobalTelemetry();
    return this.isMuted;
  }

  private getBuffersLoadedCount(): number {
    let count = 0;
    for (const slot of this.soundSlots.values()) {
      if (slot.buffer !== null) count++;
    }
    return count;
  }

  public getStats(): AudioRuntimeStats {
    return {
      contextState: this.ctx ? this.ctx.state : "uninitialized",
      buffersLoaded: this.getBuffersLoadedCount(),
      midiLoaded: this.isMidiLoaded,
      midiTotalNotes: this.parsedMidiNotes.length,
      midiDurationSec: Number(this.midiTotalDurationSec.toFixed(2)),
      bgmPlaying: this.isBgmPlaying,
      bgmCurrentTimeSec: Number(this.bgmCurrentTimeSec.toFixed(2)),
      bgmNotesTriggered: this.bgmNotesTriggered,
      bgmLoopCount: this.bgmLoopCount,
      sfxPlayCounts: { ...this.sfxPlayCounts },
      resIdPlayCounts: { ...this.resIdPlayCounts },
    };
  }

  private updateGlobalTelemetry(): void {
    if (typeof window !== "undefined") {
      (window as any).__GOW_AUDIO_STATS__ = this.getStats();
    }
  }
}
