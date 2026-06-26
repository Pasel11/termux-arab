package com.termux.arab.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.termux.arab.R;
import com.termux.arab.core.VulnScanner;

import java.util.ArrayList;
import java.util.List;

public class VulnScannerActivity extends AppCompatActivity {

    private ProgressBar progress;
    private TextView progressText;
    private TextView scoreText;
    private TextView gradeText;
    private LinearLayout resultsLayout;
    private CardView scoreCard;
    private Button scanBtn;
    private List<VulnScanner.Finding> findings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_vuln_scanner);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("🛡️ مكتشف الثغرات");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progress = findViewById(R.id.scan_progress);
        progressText = findViewById(R.id.scan_progress_text);
        scoreText = findViewById(R.id.scan_score);
        gradeText = findViewById(R.id.scan_grade);
        resultsLayout = findViewById(R.id.results_layout);
        scoreCard = findViewById(R.id.score_card);
        scanBtn = findViewById(R.id.btn_scan);

        scanBtn.setOnClickListener(v -> startScan());
    }

    private void startScan() {
        findings.clear();
        resultsLayout.removeAllViews();
        scoreCard.setVisibility(View.GONE);
        scanBtn.setEnabled(false);
        scanBtn.setText("جارٍ الفحص...");
        progress.setVisibility(View.VISIBLE);
        progress.setProgress(0);
        progressText.setVisibility(View.VISIBLE);

        VulnScanner.scan(this, new VulnScanner.ScanListener() {
            @Override
            public void onProgress(String step, int percent) {
                runOnUiThread(() -> {
                    progress.setProgress(percent);
                    progressText.setText(step);
                });
            }

            @Override
            public void onFinding(VulnScanner.Finding finding) {
                runOnUiThread(() -> addFindingCard(finding));
            }

            @Override
            public void onComplete(VulnScanner.ScanResult result) {
                runOnUiThread(() -> {
                    progress.setVisibility(View.GONE);
                    progressText.setVisibility(View.GONE);
                    scanBtn.setEnabled(true);
                    scanBtn.setText("🔄 إعادة الفحص");
                    showScore(result);
                });
            }
        });
    }

    private void addFindingCard(VulnScanner.Finding f) {
        CardView card = new CardView(this);
        card.setRadius(16);
        card.setCardElevation(4);
        card.setUseCompatPadding(true);

        int bgColor;
        switch (f.severity) {
            case "critical": bgColor = 0xFFFFEBEE; break;
            case "high": bgColor = 0xFFFFF3E0; break;
            case "medium": bgColor = 0xFFFFF8E1; break;
            case "low": bgColor = 0xFFE8F5E9; break;
            default: bgColor = 0xFFE3F2FD; break;
        }
        card.setCardBackgroundColor(bgColor);

        LinearLayout inner = new LinearLayout(this);
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.setPadding(32, 24, 32, 24);

        // العنوان
        TextView title = new TextView(this);
        String sevIcon;
        switch (f.severity) {
            case "critical": sevIcon = "🔴"; break;
            case "high": sevIcon = "🟠"; break;
            case "medium": sevIcon = "🟡"; break;
            case "low": sevIcon = "🟢"; break;
            default: sevIcon = "🔵"; break;
        }
        title.setText(sevIcon + " " + f.titleAr);
        title.setTextSize(16);
        title.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        title.setTextColor(0xFF1A1A1A);
        inner.addView(title);

        // الوصف
        TextView desc = new TextView(this);
        desc.setText(f.description);
        desc.setTextSize(13);
        desc.setTextColor(0xFF444444);
        desc.setPadding(0, 8, 0, 0);
        inner.addView(desc);

        // الحل
        TextView sol = new TextView(this);
        sol.setText("💡 " + f.solution);
        sol.setTextSize(12);
        sol.setTextColor(0xFF0F4C2A);
        sol.setPadding(0, 8, 0, 0);
        inner.addView(sol);

        card.addView(inner);
        resultsLayout.addView(card);
    }

    private void showScore(VulnScanner.ScanResult result) {
        scoreCard.setVisibility(View.VISIBLE);
        int score = result.getScore();
        scoreText.setText(score + "/100");
        gradeText.setText(result.getScoreGrade());

        int color;
        if (score >= 80) color = 0xFF4CAF50;
        else if (score >= 60) color = 0xFFFF9800;
        else color = 0xFFFF5722;
        scoreText.setTextColor(color);

        TextView summary = findViewById(R.id.scan_summary);
        summary.setText(
            "📊 نتائج الفحص:\n\n" +
            "🔴 حرج: " + result.criticalCount + "\n" +
            "🟠 عالي: " + result.highCount + "\n" +
            "🟡 متوسط: " + result.mediumCount + "\n" +
            "🟢 منخفض: " + result.lowCount + "\n" +
            "🔵 معلومات: " + result.infoCount + "\n\n" +
            "📱 تطبيقات تم فحصها: " + result.totalAppsScanned + "\n" +
            "🔐 أذونات تم فحصها: " + result.totalPermissionsChecked + "\n" +
            "⏱️ زمن الفحص: " + (result.scanDurationMs / 1000) + " ثانية"
        );
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
