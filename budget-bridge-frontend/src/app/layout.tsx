import type { Metadata } from "next";
import "./globals.css";
import { Providers } from "./Providers";
import SessionGuard from "@/components/SessionGuard";

export const metadata: Metadata = {
  title: "Budget Bridge",
  description: "Budget Bridge",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="pt-BR">
      <body>
        <Providers>
          <SessionGuard>{children}</SessionGuard>
        </Providers>
      </body>
    </html>
  );
}
