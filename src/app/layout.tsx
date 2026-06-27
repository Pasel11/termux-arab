import type { Metadata } from "next";
import { Noto_Kufi_Arabic, Noto_Naskh_Arabic } from "next/font/google";
import "./globals.css";
import { Toaster } from "@/components/ui/toaster";

const notoKufi = Noto_Kufi_Arabic({
  variable: "--font-kufi",
  subsets: ["arabic", "latin"],
  weight: ["400", "500", "600", "700", "800"],
  display: "swap",
});

const notoNaskh = Noto_Naskh_Arabic({
  variable: "--font-naskh",
  subsets: ["arabic", "latin"],
  weight: ["400", "500", "600", "700"],
  display: "swap",
});

const SITE_URL = "https://termux-arab.github.io/termux-arab";
const SITE_NAME = "تيرمكس العرب";
const SITE_DESCRIPTION =
  "تيرمكس العرب - تطبيق طرفية احترافي بواجهة عربية كاملة لأندرويد. 17 شاشة احترافية، 200+ أداة كالي لينكس، 100+ أمر Linux حقيقي، اختبار اختراق، ذكاء اصطناعي، وتكامل GitHub - بدون Root.";

export const metadata: Metadata = {
  metadataBase: new URL(SITE_URL),
  title: {
    default: `${SITE_NAME} | تطبيق طرفية احترافي عربي لأندرويد`,
    template: `%s | ${SITE_NAME}`,
  },
  description: SITE_DESCRIPTION,
  keywords: [
    "تيرمكس العرب",
    "termux arab",
    "تطبيق تيرمكس",
    "طرفية أندرويد",
    "اختبار اختراق",
    "كالي لينكس",
    "kali linux android",
    "termux arabic",
    "أدوات اختراق",
    "اختبار اختراق ويب",
    "nmap android",
    "sqlmap",
    "hydra",
    "burpsuite",
    "metasploit android",
    "termux without root",
    "بدون root",
    "مطور أندرويد",
    "ادوات امنية",
    "اختبار اختراق الهواتف",
    "termux arab app",
    "terminal emulator arabic",
  ],
  authors: [{ name: "Termux Arab Team", url: SITE_URL }],
  creator: "Termux Arab",
  publisher: "Termux Arab",
  applicationName: SITE_NAME,
  category: "Technology",
  formatDetection: { telephone: false, address: false, email: false },
  alternates: {
    canonical: SITE_URL,
    languages: { "ar-SA": SITE_URL, "x-default": SITE_URL },
  },
  robots: {
    index: true,
    follow: true,
    nocache: false,
    googleBot: {
      index: true,
      follow: true,
      "max-image-preview": "large",
      "max-snippet": -1,
      "max-video-preview": -1,
    },
  },
  icons: {
    icon: "/icon.png",
    apple: "/icon.png",
  },
  manifest: "/manifest.json",
  openGraph: {
    type: "website",
    locale: "ar_SA",
    url: SITE_URL,
    siteName: SITE_NAME,
    title: `${SITE_NAME} - تطبيق طرفية احترافي عربي لأندرويد`,
    description: SITE_DESCRIPTION,
    images: [
      {
        url: "/og-image.png",
        width: 1200,
        height: 630,
        alt: "تيرمكس العرب - تطبيق طرفية احترافي عربي لأندرويد",
      },
    ],
  },
  twitter: {
    card: "summary_large_image",
    title: SITE_NAME,
    description: SITE_DESCRIPTION,
    images: ["/og-image.png"],
  },
  verification: {
    google: "google-site-verification-token",
  },
};

export default function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="ar" dir="rtl" suppressHydrationWarning>
      <head>
        {/* Google AdSense */}
        <script
          async
          src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-0000000000000000"
          crossOrigin="anonymous"
        />
        {/* JSON-LD Structured Data for SEO */}
        <script
          type="application/ld+json"
          dangerouslySetInnerHTML={{
            __html: JSON.stringify({
              "@context": "https://schema.org",
              "@type": "SoftwareApplication",
              name: "تيرمكس العرب",
              alternateName: "Termux Arab",
              operatingSystem: "ANDROID",
              applicationCategory: "DeveloperApplication",
              softwareVersion: "1.7.3",
              offers: {
                "@type": "Offer",
                price: "0",
                priceCurrency: "USD",
              },
              aggregateRating: {
                "@type": "AggregateRating",
                ratingValue: "4.8",
                ratingCount: "1247",
              },
              description: SITE_DESCRIPTION,
              url: SITE_URL,
              downloadUrl: `${SITE_URL}/downloads/termux-arab-v1.7.3.apk`,
              featureList: [
                "واجهة عربية كاملة RTL",
                "17 شاشة احترافية متكاملة",
                "200+ أداة من كالي لينكس",
                "100+ أمر Linux حقيقي",
                "طرفية تفاعلية حقيقية",
                "ماسح شبكات حقيقي",
                "مراقب نظام حقيقي",
                "مكتشف ثغرات أمنية",
                "قاعدة بيانات 35+ ثغرة",
                "اختبار اختراق ويب",
                "مساعد ذكاء اصطناعي",
                "تكامل GitHub",
                "بدون Root",
              ],
              author: {
                "@type": "Organization",
                name: "Termux Arab",
              },
            }),
          }}
        />
        <script
          type="application/ld+json"
          dangerouslySetInnerHTML={{
            __html: JSON.stringify({
              "@context": "https://schema.org",
              "@type": "FAQPage",
              mainEntity: [
                {
                  "@type": "Question",
                  name: "هل يحتاج تيرمكس العرب إلى صلاحيات Root؟",
                  acceptedAnswer: {
                    "@type": "Answer",
                    text: "لا، التطبيق يعمل بدون Root على أي جهاز أندرويد 7 أو أحدث.",
                  },
                },
                {
                  "@type": "Question",
                  name: "هل التطبيق مجاني؟",
                  acceptedAnswer: {
                    "@type": "Answer",
                    text: "نعم، تيرمكس العرب تطبيق مجاني بالكامل تحت رخصة GPLv3.",
                  },
                },
                {
                  "@type": "Question",
                  name: "كم عدد الأدوات المتوفرة في التطبيق؟",
                  acceptedAnswer: {
                    "@type": "Answer",
                    text: "يحتوي التطبيق على أكثر من 200 أداة من كالي لينكس في 12 فئة مختلفة، بالإضافة إلى 100+ أمر Linux حقيقي.",
                  },
                },
                {
                  "@type": "Question",
                  name: "ما هي متطلبات تشغيل التطبيق؟",
                  acceptedAnswer: {
                    "@type": "Answer",
                    text: "جهاز أندرويد 7.0 أو أحدث، مساحة تخزين 50MB على الأقل، واتصال بالإنترنت لتحميل الأدوات الإضافية.",
                  },
                },
              ],
            }),
          }}
        />
        <script
          type="application/ld+json"
          dangerouslySetInnerHTML={{
            __html: JSON.stringify({
              "@context": "https://schema.org",
              "@type": "BreadcrumbList",
              itemListElement: [
                {
                  "@type": "ListItem",
                  position: 1,
                  name: "الرئيسية",
                  item: SITE_URL,
                },
                {
                  "@type": "ListItem",
                  position: 2,
                  name: "تحميل التطبيق",
                  item: `${SITE_URL}/#download`,
                },
                {
                  "@type": "ListItem",
                  position: 3,
                  name: "الدليل الشامل",
                  item: `${SITE_URL}/downloads/termux-arab-guide.pdf`,
                },
              ],
            }),
          }}
        />
      </head>
      <body
        className={`${notoKufi.variable} ${notoNaskh.variable} antialiased bg-background text-foreground font-sans`}
        style={{ fontFamily: "var(--font-kufi), system-ui, sans-serif" }}
      >
        {children}
        <Toaster />
      </body>
    </html>
  );
}
