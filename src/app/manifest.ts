import type { MetadataRoute } from "next";

export const dynamic = "force-static";

export default function manifest(): MetadataRoute.Manifest {
  return {
    name: "تيرمكس العرب - Termux Arab",
    short_name: "تيرمكس العرب",
    description:
      "تطبيق طرفية احترافي بواجهة عربية كاملة لأندرويد - 200+ أداة كالي لينكس، 100+ أمر Linux حقيقي",
    start_url: "/",
    display: "standalone",
    background_color: "#073018",
    theme_color: "#0F4C2A",
    orientation: "portrait-primary",
    dir: "rtl",
    lang: "ar",
    categories: ["developer", "tools", "utilities"],
    icons: [
      {
        src: "/icon.png",
        sizes: "192x192",
        type: "image/png",
      },
      {
        src: "/icon.png",
        sizes: "512x512",
        type: "image/png",
      },
    ],
  };
}
