import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  output: "export",
  images: { unoptimized: true },
  trailingSlash: true,
  typescript: {
    ignoreBuildErrors: true,
  },
  reactStrictMode: false,
  // For GitHub Pages at https://pasel11.github.io/termux-arab/
  basePath: "/termux-arab",
  assetPrefix: "/termux-arab/",
};

export default nextConfig;
