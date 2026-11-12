import fs from "node:fs";
import path from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const webGameRoot = path.resolve(__dirname, "..");
const resourceAnalysisRoot = path.resolve(webGameRoot, "..", "resource-analysis");
const extractedSrc = path.join(resourceAnalysisRoot, "extracted");
const manifestSrc = path.join(resourceAnalysisRoot, "asset_manifest.json");
const validationSrc = path.join(resourceAnalysisRoot, "validation_report.json");

const publicAssetsDst = path.join(webGameRoot, "public", "assets");

function copyRecursive(src, dest) {
  const stat = fs.statSync(src);
  if (stat.isDirectory()) {
    fs.mkdirSync(dest, { recursive: true });
    for (const child of fs.readdirSync(src)) {
      if (child === "raw") continue; // Skip raw binary slices in web bundle to keep public/assets lean
      copyRecursive(path.join(src, child), path.join(dest, child));
    }
  } else {
    fs.mkdirSync(path.dirname(dest), { recursive: true });
    fs.copyFileSync(src, dest);
  }
}

if (!fs.existsSync(extractedSrc) || !fs.existsSync(manifestSrc)) {
  console.error("Error: resource-analysis/extracted or asset_manifest.json not found.");
  process.exit(1);
}

fs.mkdirSync(publicAssetsDst, { recursive: true });
fs.copyFileSync(manifestSrc, path.join(publicAssetsDst, "asset_manifest.json"));
if (fs.existsSync(validationSrc)) {
  fs.copyFileSync(validationSrc, path.join(publicAssetsDst, "validation_report.json"));
}
copyRecursive(extractedSrc, path.join(publicAssetsDst, "extracted"));

console.log("[sync-assets] Synced extracted assets and manifest to web-game/public/assets/");
