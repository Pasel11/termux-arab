package com.termux.arab.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
        try {
            setContentView(R.layout.activity_main);
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } catch (Exception e) {
            // إذا فشل تحميل الواجهة، نخرج بهدوء
            finish();
            return;
        }

        // إنشاء مجلد home (آمن)
        try {
            new java.io.File(getFilesDir(), "home").mkdirs();
        } catch (Exception e) {}

        // تهيئة الواجهة
        try {
            initViews();
        } catch (Exception e) {
            Toast.makeText(this, "⚠️ خطأ في التحميل: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // تهيئة بيئة Linux في الخلفية (بدون تعطيل الواجهة)
        new Thread(() -> {
            try {
                new com.termux.arab.core.LinuxEnv(this).init();
            } catch (Exception e) {
                // تجاهل - لن يكسر التطبيق
            }
        }).start();

        // بدء خدمة الخلفية بتأخير (لتجنب crash)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try {
                com.termux.arab.core.BackgroundService.start(this);
            } catch (Exception e) {
                // تجاهل - الخدمة اختيارية
            }
        }, 2000);
    }

    private void initViews() {
        RecyclerView rv = findViewById(R.id.categories_grid);
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        String[] cats = ToolRegistry.getCategories();
        rv.setAdapter(new CategoryAdapter(cats, this::openCategory));

        // أزرار
        setClick(R.id.btn_terminal, () -> startActivity(new Intent(this, TerminalActivity.class)));
        setClick(R.id.btn_about, () -> startActivity(new Intent(this, AboutActivity.class)));
        setClick(R.id.btn_vuln_scanner, () -> startActivity(new Intent(this, VulnScannerActivity.class)));
        setClick(R.id.btn_vuln_db, () -> startActivity(new Intent(this, VulnDBActivity.class)));
        setClick(R.id.btn_network_scanner, () -> startActivity(new Intent(this, NetworkScannerActivity.class)));
        setClick(R.id.btn_system_monitor, () -> startActivity(new Intent(this, SystemMonitorActivity.class)));
        setClick(R.id.btn_password_tools, () -> startActivity(new Intent(this, PasswordToolsActivity.class)));
        setClick(R.id.btn_http_tester, () -> startActivity(new Intent(this, HttpTesterActivity.class)));
        setClick(R.id.btn_github, () -> startActivity(new Intent(this, GithubActivity.class)));
        setClick(R.id.btn_packages, () -> startActivity(new Intent(this, PackageManagerActivity.class)));
        setClick(R.id.btn_web_pentest, () -> startActivity(new Intent(this, WebPentestActivity.class)));
        setClick(R.id.btn_ai, () -> startActivity(new Intent(this, AIAssistantActivity.class)));
        setClick(R.id.btn_kali, () -> startActivity(new Intent(this, KaliLinuxActivity.class)));
        setClick(R.id.btn_installer, () -> startActivity(new Intent(this, ToolInstallerActivity.class)));

        // نافذة AI العائمة (آمنة)
        try {
            AIFloatingWindow aiWindow = new AIFloatingWindow(this);
            Button btnFloatingAI = findViewById(R.id.btn_floating_ai);
            btnFloatingAI.setOnClickListener(v -> {
                try {
                    if (aiWindow.isVisible()) {
                        aiWindow.hide();
                        btnFloatingAI.setText("🤖 تفعيل المساعد");
                    } else {
                        aiWindow.show();
                        btnFloatingAI.setText("🤖 إخفاء المساعد");
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "⚠️ يحتاج إذن العرض فوق التطبيقات", Toast.LENGTH_SHORT).show();
                }
            });
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
