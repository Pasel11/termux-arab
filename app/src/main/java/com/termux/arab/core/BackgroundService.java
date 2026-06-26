package com.termux.arab.core;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

/**
 * خدمة خلفية - تبقي التطبيق يعمل في الخلفية
 */
public class BackgroundService extends Service {

    private static final String CHANNEL_ID = "termux_arab_bg";
    private static final int NOTIF_ID = 1001;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Notification notif = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("🐉 تيرمكس العرب")
            .setContentText("يعمل في الخلفية - الطرفية نشطة")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build();
        startForeground(NOTIF_ID, notif);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY; // يعيد التشغيل تلقائياً
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // إعادة التشغيل عند الإغلاق
        Intent restart = new Intent(this, BackgroundService.class);
        if (Build.VERSION.SDK_INT >= 26) startForegroundService(restart);
        else startService(restart);
        super.onTaskRemoved(rootIntent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel ch = new NotificationChannel(CHANNEL_ID,
                "تيرمكس العرب - الخلفية", NotificationManager.IMPORTANCE_LOW);
            ch.setDescription("إشعار الخدمة الخلفية");
            NotificationManager nm = getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(ch);
        }
    }

    public static void start(Context ctx) {
        Intent i = new Intent(ctx, BackgroundService.class);
        if (Build.VERSION.SDK_INT >= 26) ctx.startForegroundService(i);
        else ctx.startService(i);
    }

    public static void stop(Context ctx) {
        ctx.stopService(new Intent(ctx, BackgroundService.class));
    }
}
