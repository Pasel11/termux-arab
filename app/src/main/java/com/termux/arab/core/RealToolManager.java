package com.termux.arab.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * مدير تثبيت الأدوات الحقيقية
 * ينزّل busybox وأدوات حقيقية من مصادر موثوقة ويجعلها قابلة للتنفيذ
 */
public class RealToolManager {

    private final Context ctx;
    private final File binDir;
    private final SharedPreferences prefs;
    private static final String BUSYBOX_URL = "https://github.com/meefik/busybox/releases/download/1.31.1/busybox-android-arm64";

    public interface InstallCallback {
        void onProgress(String msg, int percent);
        void onComplete(boolean success, String message);
    }

    public RealToolManager(Context ctx) {
        this.ctx = ctx;
        this.binDir = new File(ctx.getFilesDir(), "linux/usr/bin");
        this.binDir.mkdirs();
        this.prefs = ctx.getSharedPreferences("real_tools", Context.MODE_PRIVATE);
    }

    public boolean isBusyboxInstalled() {
        File bb = new File(binDir, "busybox");
        return bb.exists() && bb.canExecute();
    }

    /**
     * تثبيت busybox - يعطي 200+ أمر Linux حقيقي
     */
    public void installBusybox(InstallCallback callback) {
        if (isBusyboxInstalled()) {
            callback.onComplete(true, "✅ busybox مثبت مسبقاً");
            return;
        }

        final Handler handler = new Handler(Looper.getMainLooper());

        new Thread(() -> {
            try {
                handler.post(() -> callback.onProgress("📥 تنزيل busybox...", 10));

                HttpURLConnection conn = (HttpURLConnection) new URL(BUSYBOX_URL).openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(120000);

                int totalSize = conn.getContentLength();
                InputStream input = conn.getInputStream();
                File outputFile = new File(binDir, "busybox");
                FileOutputStream output = new FileOutputStream(outputFile);

                byte[] buffer = new byte[8192];
                int bytesRead;
                long downloaded = 0;

                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                    downloaded += bytesRead;
                    if (totalSize > 0) {
                        int pct = 10 + (int)(downloaded * 80 / totalSize);
                        final int finalPct = pct;
                        handler.post(() -> callback.onProgress("📥 تنزيل busybox... " + finalPct + "%", finalPct));
                    }
                }

                output.close();
                input.close();
                conn.disconnect();

                handler.post(() -> callback.onProgress("⚙️ تثبيت busybox...", 90));

                // جعله تنفيذياً
                outputFile.setExecutable(true, true);

                handler.post(() -> callback.onProgress("🔗 إنشاء روابط الأوامر...", 95));

                // إنشاء روابط لكل أوامر busybox
                Process p = Runtime.getRuntime().exec(new String[]{
                    outputFile.getAbsolutePath(), "--list"
                });
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    File link = new File(binDir, line.trim());
                    if (!link.exists()) {
                        try {
                            // إنشاء سكربت بدلاً من symlink (يعمل بدون root)
                            String script = "#!/system/bin/sh\n" + outputFile.getAbsolutePath() + " \"$@\"\n";
                            FileOutputStream fos = new FileOutputStream(link);
                            fos.write(script.getBytes("UTF-8"));
                            fos.close();
                            link.setExecutable(true, true);
                        } catch (Exception e) {}
                    }
                }
                reader.close();
                p.waitFor();

                prefs.edit().putBoolean("busybox_installed", true).apply();

                handler.post(() -> callback.onProgress("✅ اكتمل!", 100));
                Thread.sleep(500);
                handler.post(() -> callback.onComplete(true, "✅ تم تثبيت busybox! 200+ أمر Linux حقيقي جاهز"));

            } catch (Exception e) {
                handler.post(() -> callback.onComplete(false, "❌ فشل: " + e.getMessage()));
            }
        }).start();
    }

    /**
     * تثبيت أداة من GitHub
     */
    public void installFromUrl(String name, String url, InstallCallback callback) {
        final Handler handler = new Handler(Looper.getMainLooper());
        File target = new File(binDir, name);

        if (target.exists() && target.canExecute()) {
            callback.onComplete(true, "✅ " + name + " مثبت مسبقاً");
            return;
        }

        new Thread(() -> {
            try {
                handler.post(() -> callback.onProgress("📥 تنزيل " + name + "...", 20));

                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(120000);
                conn.setRequestProperty("User-Agent", "TermuxArab/1.0");

                InputStream input = conn.getInputStream();
                FileOutputStream output = new FileOutputStream(target);

                byte[] buffer = new byte[8192];
                int bytesRead;
                long downloaded = 0;
                int totalSize = conn.getContentLength();

                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                    downloaded += bytesRead;
                    if (totalSize > 0) {
                        final int finalPct = 20 + (int)(downloaded * 70 / totalSize);
                        final long finalDl = downloaded;
                        handler.post(() -> callback.onProgress("📥 " + finalDl/1024 + " KB...", finalPct));
                    }
                }

                output.close();
                input.close();
                conn.disconnect();

                target.setExecutable(true, true);
                prefs.edit().putBoolean("tool_" + name, true).apply();

                handler.post(() -> callback.onProgress("✅ تم تثبيت " + name, 100));
                Thread.sleep(300);
                handler.post(() -> callback.onComplete(true, "✅ " + name + " جاهز للاستخدام!"));

            } catch (Exception e) {
                handler.post(() -> callback.onComplete(false, "❌ فشل تثبيت " + name + ": " + e.getMessage()));
            }
        }).start();
    }

    /**
     * قائمة الأدوات القابلة للتثبيت من مصادر حقيقية
     */
    public static class InstallableTool {
        public String name;
        public String nameAr;
        public String url;
        public String description;
        public String category;

        public InstallableTool(String name, String nameAr, String url, String desc, String cat) {
            this.name = name; this.nameAr = nameAr; this.url = url;
            this.description = desc; this.category = cat;
        }
    }

    public static java.util.List<InstallableTool> getInstallableTools() {
        java.util.List<InstallableTool> tools = new java.util.ArrayList<>();

        // أدوات حقيقية من GitHub
        tools.add(new InstallableTool("nmap", "ماسح الشبكات Nmap",
            "https://github.com/nmap/nmap/releases/download/nmap-7.94/nmap-7.94-android-arm64",
            "ماسح شبكات حقيقي - فحص المنافذ والخدمات", "network"));

        tools.add(new InstallableTool("curl", "عميل HTTP",
            "https://github.com/curl/curl/releases/download/curl-8_4_0/curl-8.4.0-android-arm64",
            "عميل HTTP متعدد البروتوكولات", "network"));

        tools.add(new InstallableTool("wget", "منزّل الملفات",
            "https://github.com/lhrsdl/android-wget/releases/download/v1.21.3/wget-android-arm64",
            "تنزيل ملفات من الإنترنت", "network"));

        tools.add(new InstallableTool("jq", "معالج JSON",
            "https://github.com/stedolan/jq/releases/download/jq-1.6/jq-linux64",
            "معالجة وتنسيق JSON", "dev"));

        tools.add(new InstallableTool("sqlmap", "أداة حقن SQL",
            "https://github.com/sqlmapproject/sqlmap/archive/refs/tags/1.8.tar.gz",
            "أداة اختبار حقن SQL التلقائية", "pentest"));

        tools.add(new InstallableTool("masscan", "ماسح سريع",
            "https://github.com/robertdavidgraham/masscan/releases/download/1.3.2/masscan-1.3.2-android-arm64",
            "ماسح منافذ سريع جداً", "network"));

        tools.add(new InstallableTool("hydra", "هيدرا",
            "https://github.com/vanhauser-thc/thc-hydra/releases/download/v9.5/hydra-9.5-android-arm64",
            "تخمين كلمات المرور", "pentest"));

        tools.add(new InstallableTool("nikto", "نيكتو",
            "https://github.com/sullo/nikto/archive/refs/tags/2.5.0.tar.gz",
            "ماسح ثغرات خوادم الويب", "pentest"));

        tools.add(new InstallableTool("gobuster", "جو باستر",
            "https://github.com/OJ/gobuster/releases/download/v3.6.0/gobuster_3.6.0_linux_arm64.tar.gz",
            "تخمين سريع للمسارات", "pentest"));

        tools.add(new InstallableTool("radare2", "راداري 2",
            "https://github.com/radareorg/radare2/releases/download/5.8.8/radare2-5.8.8-android-arm64.tar.gz",
            "إطار هندسة عكسية", "reverse"));

        tools.add(new InstallableTool("binwalk", "بين ووك",
            "https://github.com/ReFirmLabs/binwalk/archive/refs/tags/v2.3.4.tar.gz",
            "تحليل البرامج الثابتة", "forensics"));

        tools.add(new InstallableTool("dirb", "ديرب",
            "https://github.com/v0re/dirb/archive/refs/heads/master.tar.gz",
            "تخمين المجلدات المخفية", "pentest"));

        tools.add(new InstallableTool("whatweb", "وات ويب",
            "https://github.com/urbanadventurer/WhatWeb/archive/refs/tags/v0.5.5.tar.gz",
            "تحديد تقنيات المواقع", "recon"));

        tools.add(new InstallableTool("wpscan", "ماسح ووردبريس",
            "https://github.com/wpscanner/wpscan/archive/refs/tags/v3.8.25.tar.gz",
            "ماسح ثغرات WordPress", "pentest"));

        tools.add(new InstallableTool("commix", "كوميكس",
            "https://github.com/commixproject/commix/archive/refs/tags/v3.7.tar.gz",
            "اختبار حقن الأوامر", "pentest"));

        tools.add(new InstallableTool("theharvester", "هارفيستر",
            "https://github.com/laramies/theHarvester/archive/refs/tags/4.5.1.tar.gz",
            "جمع الإيميلات والنطاقات", "recon"));

        tools.add(new InstallableTool("sublist3r", "ساب ليستر",
            "https://github.com/aboul3la/Sublist3r/archive/refs/tags/1.1.tar.gz",
            "تعداد النطاقات الفرعية", "recon"));

        tools.add(new InstallableTool("nuclei", "نوكلي",
            "https://github.com/projectdiscovery/nuclei/releases/download/v2.9.15/nuclei_2.9.15_linux_arm64.zip",
            "ماسح ثغرات سريع", "pentest"));

        tools.add(new InstallableTool("ffuf", "ف ف يو ف",
            "https://github.com/ffuf/ffuf/releases/download/v2.1.0/ffuf_2.1.0_linux_arm64.tar.gz",
            "أداة Fuzz سريعة", "pentest"));

        tools.add(new InstallableTool("httpx", "إتش تي تي بي إكس",
            "https://github.com/projectdiscovery/httpx/releases/download/v1.3.5/httpx_1.3.5_linux_arm64.zip",
            "فاحص HTTP سريع", "network"));

        return tools;
    }

    public boolean isToolInstalled(String name) {
        File f = new File(binDir, name);
        return f.exists() && f.canExecute();
    }

    public File getBinDir() { return binDir; }

    public String getPathVariable() {
        return binDir.getAbsolutePath() + ":" +
               new File(ctx.getFilesDir(), "linux/usr/bin").getAbsolutePath() + ":" +
               "/system/bin:/system/xbin";
    }

    public String[] getEnvArray() {
        return new String[] {
            "PATH=" + getPathVariable(),
            "HOME=" + new File(ctx.getFilesDir(), "linux/home").getAbsolutePath(),
            "TMPDIR=" + ctx.getCacheDir().getAbsolutePath(),
            "TERM=xterm-256color",
            "LANG=en_US.UTF-8",
            "SHELL=/system/bin/sh"
        };
    }
}
