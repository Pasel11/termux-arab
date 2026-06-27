package com.termux.arab.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.termux.arab.R;
import com.termux.arab.core.ToolRegistry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // تثبيت معالج أخطاء عام لمنع الـ crash
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            android.util.Log.e("TermuxArab", "Uncaught exception", throwable);
        });

        try {
            setContentView(R.layout.activity_main);
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } catch (Exception e) {
            finish();
            return;
        }

        // إنشاء مجلد أساسي فقط (آمن)
        try {
            new java.io.File(getFilesDir(), "home").mkdirs();
        } catch (Exception e) {}

        // تهيئة الواجهة فقط (بدون خدمات خطيرة)
        try {
            initViews();
        } catch (Exception e) {
            Toast.makeText(this, "⚠️ خطأ: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        RecyclerView rv = findViewById(R.id.categories_grid);
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        String[] cats = ToolRegistry.getCategories();
        rv.setAdapter(new CategoryAdapter(cats, this::openCategory));

        // أزرار
        setClick(R.id.btn_terminal, () -> openActivity(TerminalActivity.class));
        setClick(R.id.btn_about, () -> openActivity(AboutActivity.class));
        setClick(R.id.btn_vuln_scanner, () -> openActivity(VulnScannerActivity.class));
        setClick(R.id.btn_vuln_db, () -> openActivity(VulnDBActivity.class));
        setClick(R.id.btn_network_scanner, () -> openActivity(NetworkScannerActivity.class));
        setClick(R.id.btn_system_monitor, () -> openActivity(SystemMonitorActivity.class));
        setClick(R.id.btn_password_tools, () -> openActivity(PasswordToolsActivity.class));
        setClick(R.id.btn_http_tester, () -> openActivity(HttpTesterActivity.class));
        setClick(R.id.btn_github, () -> openActivity(GithubActivity.class));
        setClick(R.id.btn_packages, () -> openActivity(PackageManagerActivity.class));
        setClick(R.id.btn_web_pentest, () -> openActivity(WebPentestActivity.class));
        setClick(R.id.btn_ai, () -> openActivity(AIAssistantActivity.class));
        setClick(R.id.btn_kali, () -> openActivity(KaliLinuxActivity.class));
        setClick(R.id.btn_installer, () -> openActivity(ToolInstallerActivity.class));

        // زر AI العائم — معالج آمن
        try {
            Button btnFloatingAI = findViewById(R.id.btn_floating_ai);
            if (btnFloatingAI != null) {
                btnFloatingAI.setOnClickListener(v -> {
                    Toast.makeText(this, "🤖 المساعد العائم متاح من شاشة المساعد", Toast.LENGTH_SHORT).show();
                    openActivity(AIAssistantActivity.class);
                });
            }
        } catch (Exception e) {}

        // إحصائيات
        try {
            TextView stats = findViewById(R.id.tv_stats);
            stats.setText(ToolRegistry.getAllTools().size() + " أداة في " + cats.length + " فئات");
        } catch (Exception e) {}
    }

    private void setClick(int buttonId, Runnable action) {
        try {
            Button btn = findViewById(buttonId);
            if (btn != null) {
                btn.setOnClickListener(v -> {
                    try { action.run(); }
                    catch (Exception e) { Toast.makeText(this, "⚠️ تعذّر الفتح", Toast.LENGTH_SHORT).show(); }
                });
            }
        } catch (Exception e) {}
    }

    private void openActivity(Class<?> cls) {
        try {
            startActivity(new Intent(this, cls));
        } catch (Exception e) {
            Toast.makeText(this, "⚠️ تعذّر فتح: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openCategory(String category) {
        try {
            Intent intent = new Intent(this, ToolListActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "⚠️ تعذّر فتح الفئة", Toast.LENGTH_SHORT).show();
        }
    }
}
