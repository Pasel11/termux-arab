"use client";

import { useEffect, useRef } from "react";

interface AdComponentProps {
  slot?: string;
  format?: string;
  responsive?: boolean;
  className?: string;
  label?: string;
}

declare global {
  interface Window {
    adsbygoogle: unknown[];
  }
}

export function AdComponent({
  slot = "0000000000",
  format = "auto",
  responsive = true,
  className = "",
  label = "إعلان",
}: AdComponentProps) {
  const adRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    try {
      if (typeof window !== "undefined") {
        (window.adsbygoogle = window.adsbygoogle || []).push({});
      }
    } catch (e) {
      // AdSense not loaded yet, will retry
      console.debug("AdSense loading:", e);
    }
  }, []);

  return (
    <div className={`my-6 ${className}`}>
      <div className="text-xs text-muted-foreground mb-1 text-center">
        {label}
      </div>
      <div className="ad-container" ref={adRef}>
        <ins
          className="adsbygoogle"
          style={{ display: "block", width: "100%", height: "100%" }}
          data-ad-client="ca-pub-0000000000000000"
          data-ad-slot={slot}
          data-ad-format={format}
          data-full-width-responsive={responsive ? "true" : "false"}
        />
      </div>
    </div>
  );
}
