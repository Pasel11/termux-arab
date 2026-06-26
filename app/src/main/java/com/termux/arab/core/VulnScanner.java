package com.termux.arab.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * ماسح الثغرات الحقيقي - يفحص الجهاز فعلياً ويجد مشاكل حقيقية
 */
public class VulnScanner {

    public interface ScanListener {
        void onProgress(String step, int percent);
        void onFinding(Finding finding);
        void onComplete(ScanResult result);
    }

    public static class Finding {
        public String title;
        public String titleAr;
        public String severity; // critical, high, medium, low, info
        public String description;
        public String solution;
        public String category; // permission, network, system, app, storage

        public Finding(String title, String titleAr, String sev, String desc, String sol, String cat) {
            this.title = title; this.titleAr = titleAr; this.severity = sev;
            this.description = desc; this.solution = sol; this.category = cat;
        }
    }

    public static class ScanResult {
        public List<Finding> findings = new ArrayList<>();
        public int criticalCount = 0;
        public int highCount = 0;
        public int mediumCount = 0;
        public int lowCount = 0;
        public int infoCount = 0;
        public int totalAppsScanned = 0;
        public int totalPermissionsChecked = 0;
        public long scanDurationMs = 0;

        public int getScore() {
            return Math.max(0, 100 - (criticalCount * 25) - (highCount * 15) - (mediumCount * 8) - (lowCount * 3));
        }

        public String getScoreGrade() {
            int s = getScore();
            if (s >= 90) return "A+ ممتاز";
            if (s >= 80) return "A جيد جداً";
            if (s >= 70) return "B جيد";
            if (s >= 60) return "C مقبول";
            if (s >= 50) return "D ضعيف";
            return "F خطر!";
        }
    }

    public static void scan(Context context, ScanListener listener) {
        final ScanResult result = new ScanResult();
        final long startTime = System.currentTimeMillis();
        final Handler handler = new Handler(Looper.getMainLooper());

        new Thread(() -> {
            try {
                // 1. فحص إصدار النظام
                handler.post(() -> listener.onProgress("فحص إصدار النظام...", 10));
                Thread.sleep(300);
                checkSystemVersion(result);

                // 2. فحص الأذونات الخطيرة
                handler.post(() -> listener.onProgress("فحص الأذونات...", 25));
                Thread.sleep(300);
                checkDangerousPermissions(context, result);

                // 3. فحص التطبيقات المثبتة
                handler.post(() -> listener.onProgress("فحص التطبيقات المثبتة...", 40));
                Thread.sleep(300);
                checkInstalledApps(context, result);

                // 4. فحص الشبكة
                handler.post(() -> listener.onProgress("فحص الشبكة...", 55));
                Thread.sleep(300);
                checkNetwork(result);

                // 5. فحص التخزين
                handler.post(() -> listener.onProgress("فحص التخزين...", 70));
                Thread.sleep(300);
                checkStorage(result);

                // 6. فحص إعدادات الأمان
                handler.post(() -> listener.onProgress("فحص إعدادات الأمان...", 85));
                Thread.sleep(300);
                checkSecuritySettings(context, result);

                // 7. فحص مصادر التثبيت
                handler.post(() -> listener.onProgress("فحص مصادر التثبيت...", 95));
                Thread.sleep(300);
                checkInstallSources(context, result);

                // حساب النتائج
                for (Finding f : result.findings) {
                    switch (f.severity) {
                        case "critical": result.criticalCount++; break;
                        case "high": result.highCount++; break;
                        case "medium": result.mediumCount++; break;
                        case "low": result.lowCount++; break;
                        default: result.infoCount++; break;
                    }
                }
                result.scanDurationMs = System.currentTimeMillis() - startTime;

                handler.post(() -> listener.onProgress("اكتمل الفحص!", 100));
                Thread.sleep(500);

                // إرسال كل finding
                for (Finding f : result.findings) {
                    handler.post(() -> listener.onFinding(f));
                    Thread.sleep(100);
                }

                handler.post(() -> listener.onComplete(result));

            } catch (Exception e) {
                handler.post(() -> listener.onComplete(result));
            }
        }).start();
    }

    private static void checkSystemVersion(ScanResult result) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < 30) {
            result.findings.add(new Finding(
                "Outdated Android Version", "إصدار أندرويد قديم",
                "high",
                "Android " + getAndroidVersion(sdk) + " (API " + sdk + ") is outdated and missing security patches",
                "Update to Android 11+ for latest security patches",
                "system"));
        } else if (sdk < 33) {
            result.findings.add(new Finding(
                "Android Version Could Be Newer", "يمكن تحديث أندرويد",
                "medium",
                "Android " + getAndroidVersion(sdk) + " (API " + sdk + ") - consider updating for better security",
                "Update to Android 13+ when available",
                "system"));
        }
    }

    private static void checkDangerousPermissions(Context ctx, ScanResult result) {
        String[] dangerousPerms = {
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CALL_PHONE
        };

        int dangerousCount = 0;
        for (String perm : dangerousPerms) {
            if (ctx.checkCallingOrSelfPermission(perm) == PackageManager.PERMISSION_GRANTED) {
                dangerousCount++;
            }
        }
        result.totalPermissionsChecked = dangerousPerms.length;

        if (dangerousCount >= 5) {
            result.findings.add(new Finding(
                "Many Dangerous Permissions Granted", "أذونات خطيرة كثيرة ممنوحة",
                "high",
                dangerousCount + " dangerous permissions are granted. Apps can access sensitive data.",
                "Review and revoke unnecessary permissions in Settings > Apps",
                "permission"));
        } else if (dangerousCount >= 3) {
            result.findings.add(new Finding(
                "Some Dangerous Permissions", "بعض الأذونات الخطيرة",
                "medium",
                dangerousCount + " dangerous permissions granted",
                "Review permissions in Settings",
                "permission"));
        }
    }

    private static void checkInstalledApps(Context ctx, ScanResult result) {
        PackageManager pm = ctx.getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(0);
        result.totalAppsScanned = apps.size();

        int sideloadedApps = 0;
        for (PackageInfo app : apps) {
            // فحص التطبيقات من مصادر غير معروفة
            String installer = pm.getInstallerPackageName(app.packageName);
            if (installer == null || installer.isEmpty()) {
                if ((app.applicationInfo.flags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) == 0) {
                    sideloadedApps++;
                }
            }
        }

        if (sideloadedApps > 0) {
            result.findings.add(new Finding(
                sideloadedApps + " Sideloaded Apps Found", sideloadedApps + " تطبيق من مصدر غير معروف",
                sideloadedApps > 5 ? "high" : "medium",
                "Found " + sideloadedApps + " apps installed from unknown sources. These may contain malware.",
                "Review sideloaded apps and uninstall suspicious ones",
                "app"));
        }
    }

    private static void checkNetwork(ScanResult result) {
        try {
            // فحص IP المحلي
            String ip = getLocalIpAddress();
            if (ip != null && ip.startsWith("192.168")) {
                result.findings.add(new Finding(
                    "Connected to Local Network", "متصل بشبكة محلية",
                    "info",
                    "Device IP: " + ip + " - connected to a local network",
                    "Ensure your WiFi uses WPA2/WPA3 encryption",
                    "network"));
            }

            // فحص واجهات الشبكة
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            int interfaceCount = 0;
            while (interfaces.hasMoreElements()) {
                interfaces.nextElement();
                interfaceCount++;
            }
            if (interfaceCount > 3) {
                result.findings.add(new Finding(
                    "Multiple Network Interfaces", "واجهات شبكة متعددة",
                    "low",
                    "Found " + interfaceCount + " network interfaces - possible VPN or proxy",
                    "Verify all network interfaces are expected",
                    "network"));
            }
        } catch (Exception e) {
            // تجاهل
        }
    }

    private static void checkStorage(ScanResult result) {
        try {
            File external = android.os.Environment.getExternalStorageDirectory();
            if (external.exists()) {
                StatFs stat = new StatFs(external.getAbsolutePath());
                long total = stat.getTotalBytes();
                long free = stat.getFreeBytes();
                long usedPercent = ((total - free) * 100) / total;

                if (usedPercent > 90) {
                    result.findings.add(new Finding(
                        "Storage Almost Full", "التخزين ممتلئ تقريباً",
                        "medium",
                        "Storage is " + usedPercent + "% full. This can slow down the device and prevent security updates.",
                        "Free up space by deleting unnecessary files",
                        "storage"));
                }
            }
        } catch (Exception e) {
            // تجاهل
        }
    }

    private static void checkSecuritySettings(Context ctx, ScanResult result) {
        // فحص تصحيح USB
        try {
            int adbEnabled = android.provider.Settings.Global.getInt(
                ctx.getContentResolver(),
                android.provider.Settings.Global.ADB_ENABLED, 0);
            if (adbEnabled == 1) {
                result.findings.add(new Finding(
                    "USB Debugging Enabled", "تصحيح USB مفعّل",
                    "medium",
                    "USB debugging is enabled. This allows ADB access to your device.",
                    "Disable USB debugging when not needed: Settings > Developer Options",
                    "system"));
            }
        } catch (Exception e) {
            // تجاهل
        }

        // فحص مصادر غير معروفة
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                boolean unknownSources = ctx.getPackageManager().canRequestPackageInstalls();
                if (unknownSources) {
                    result.findings.add(new Finding(
                        "Install from Unknown Sources Enabled", "تثبيت من مصادر غير معروفة مفعّل",
                        "high",
                        "Your device can install apps from unknown sources. This is a security risk.",
                        "Disable in Settings > Apps > Special access",
                        "system"));
                }
            }
        } catch (Exception e) {
            // تجاهل
        }
    }

    private static void checkInstallSources(Context ctx, ScanResult result) {
        // فحص التطبيقات ذات الصلاحيات الكثيرة
        PackageManager pm = ctx.getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);

        for (PackageInfo app : apps) {
            if (app.requestedPermissions == null) continue;
            if ((app.applicationInfo.flags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0) continue;

            int dangerousPerms = 0;
            for (String perm : app.requestedPermissions) {
                if (perm != null && (perm.contains("SMS") || perm.contains("LOCATION") ||
                    perm.contains("CAMERA") || perm.contains("RECORD_AUDIO") ||
                    perm.contains("CONTACTS") || perm.contains("CALL_PHONE"))) {
                    dangerousPerms++;
                }
            }

            if (dangerousPerms >= 5) {
                String appName = pm.getApplicationLabel(app.applicationInfo).toString();
                result.findings.add(new Finding(
                    "App with Many Permissions: " + appName, "تطبيق بصلاحيات كثيرة: " + appName,
                    "medium",
                    appName + " requests " + dangerousPerms + " dangerous permissions. Verify this app is trusted.",
                    "Review app permissions in Settings > Apps > " + appName,
                    "app"));
                break; // نكتفي بواحد لتجنب التكرار
            }
        }
    }

    private static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) continue;
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLoopbackAddress() && addr.getHostAddress().contains(".")) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            // تجاهل
        }
        return null;
    }

    private static String getAndroidVersion(int sdk) {
        switch (sdk) {
            case 24: return "7.0";
            case 25: return "7.1";
            case 26: return "8.0";
            case 27: return "8.1";
            case 28: return "9";
            case 29: return "10";
            case 30: return "11";
            case 31: return "12";
            case 32: return "12L";
            case 33: return "13";
            case 34: return "14";
            default: return "API " + sdk;
        }
    }
}
