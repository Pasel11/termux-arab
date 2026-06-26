package com.termux.arab.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.termux.arab.R;
import com.termux.arab.core.RealToolManager;
import java.util.List;

/**
 * شاشة تثبيت الأدوات الحقيقية
 */
public class ToolInstallerActivity extends AppCompatActivity {

    private RealToolManager toolManager;
    private LinearLayout toolsList;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_tool_installer);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("⬇ تثبيت أدوات حقيقية");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolManager = new RealToolManager(this);
        toolsList = findViewById(R.id.installer_tools_list);

        // زر تثبيت busybox
        Button busyboxBtn = findViewById(R.id.btn_install_busybox);
        TextView busyboxStatus = findViewById(R.id.busybox_status);

        if (toolManager.isBusyboxInstalled()) {
            busyboxStatus.setText("✅ مثبت - 200+ أمر جاهز");
            busyboxBtn.setText("✅ busybox جاهز");
            busyboxBtn.setEnabled(false);
        }

        busyboxBtn.setOnClickListener(v -> {
            busyboxBtn.setEnabled(false);
            busyboxBtn.setText("جارٍ التثبيت...");
            toolManager.installBusybox(new RealToolManager.InstallCallback() {
                @Override public void onProgress(String msg, int pct) {
                    runOnUiThread(() -> busyboxStatus.setText(msg));
                }
                @Override public void onComplete(boolean success, String msg) {
                    runOnUiThread(() -> {
                        busyboxStatus.setText(msg);
                        if (success) { busyboxBtn.setText("✅ busybox جاهز"); }
                        else { busyboxBtn.setEnabled(true); busyboxBtn.setText("⬇ إعادة المحاولة"); }
                    });
                }
            });
        });

        // عرض الأدوات القابلة للتثبيت
        showInstallableTools();
    }

    private void showInstallableTools() {
        List<RealToolManager.InstallableTool> tools = RealToolManager.getInstallableTools();
        for (RealToolManager.InstallableTool tool : tools) {
            addToolCard(tool);
        }
    }

    private void addToolCard(RealToolManager.InstallableTool tool) {
        CardView card = new CardView(this);
        card.setRadius(12); card.setCardElevation(3); card.setUseCompatPadding(true);

        LinearLayout inner = new LinearLayout(this);
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.setPadding(32, 20, 32, 20);

        TextView name = new TextView(this);
        name.setText("📦 " + tool.name + " - " + tool.nameAr);
        name.setTextSize(15); name.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        inner.addView(name);

        TextView desc = new TextView(this);
        desc.setText(tool.description);
        desc.setTextSize(12); desc.setTextColor(0xFF666666);
        desc.setPadding(0, 4, 0, 8);
        inner.addView(desc);

        Button btn = new Button(this);
        boolean installed = toolManager.isToolInstalled(tool.name);
        if (installed) {
            btn.setText("✅ مثبت");
            btn.setBackgroundColor(0xFF4CAF50);
            btn.setEnabled(false);
        } else {
            btn.setText("⬇ تثبيت من المصدر");
            btn.setBackgroundColor(0xFF1976D2);
        }
        btn.setTextColor(0xFFFFFFFF);

        btn.setOnClickListener(v -> {
            btn.setEnabled(false);
            btn.setText("جارٍ التنزيل...");
            toolManager.installFromUrl(tool.name, tool.url, new RealToolManager.InstallCallback() {
                @Override public void onProgress(String msg, int pct) {
                    runOnUiThread(() -> btn.setText(msg));
                }
                @Override public void onComplete(boolean success, String msg) {
                    runOnUiThread(() -> {
                        if (success) { btn.setText("✅ مثبت"); btn.setBackgroundColor(0xFF4CAF50); }
                        else { btn.setText("⬇ إعادة المحاولة"); btn.setEnabled(true); }
                    });
                }
            });
        });

        inner.addView(btn);
        card.addView(inner);
        toolsList.addView(card);
    }

    @Override public boolean onSupportNavigateUp() { finish(); return true; }
}
