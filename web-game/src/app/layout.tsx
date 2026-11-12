import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "God of War: Betrayal (2007 J2ME) — Web Port",
  description: "Faithful Next.js + TypeScript + Phaser 3 recreation of God of War: Betrayal (176x208)",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  );
}
