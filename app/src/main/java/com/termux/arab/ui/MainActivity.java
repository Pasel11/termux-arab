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
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        // إنشاء مجلد home
        getFilesDir();
        new java.io.File(getFilesDir(), "home").mkdirs();

        RecyclerView rv = findViewById(R.id.categories_grid);
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        String[] cats = ToolRegistry.getCategories();
        rv.setAdapter(new CategoryAdapter(cats, this::openCategory));

        // أزرار سريعة
        Button btnTerminal = findViewById(R.id.btn_terminal);
        btnTerminal.setOnClickListener(v -> {
            startActivity(new Intent(this, TerminalActivity.class));
        });

        Button btnAbout = findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(v -> {
            startActivity(new Intent(this, AboutActivity.class));
        });

        Button btnVulnScanner = findViewById(R.id.btn_vuln_scanner);
        btnVulnScanner.setOnClickListener(v -> {
            startActivity(new Intent(this, VulnScannerActivity.class));
        });

        Button btnVulnDB = findViewById(R.id.btn_vuln_db);
        btnVulnDB.setOnClickListener(v -> {
            startActivity(new Intent(this, VulnDBActivity.class));
        });

        Button btnNetworkScanner = findViewById(R.id.btn_network_scanner);
        btnNetworkScanner.setOnClickListener(v -> {
            startActivity(new Intent(this, NetworkScannerActivity.class));
        });

        Button btnSystemMonitor = findViewById(R.id.btn_system_monitor);
        btnSystemMonitor.setOnClickListener(v -> {
            startActivity(new Intent(this, SystemMonitorActivity.class));
        });

        Button btnPasswordTools = findViewById(R.id.btn_password_tools);
        btnPasswordTools.setOnClickListener(v -> {
            startActivity(new Intent(this, PasswordToolsActivity.class));
        });

        Button btnHttpTester = findViewById(R.id.btn_http_tester);
        btnHttpTester.setOnClickListener(v -> {
            startActivity(new Intent(this, HttpTesterActivity.class));
        });

        Button btnGithub = findViewById(R.id.btn_github);
        btnGithub.setOnClickListener(v -> {
            startActivity(new Intent(this, GithubActivity.class));
        });

        Button btnPackages = findViewById(R.id.btn_packages);
        btnPackages.setOnClickListener(v -> {
            startActivity(new Intent(this, PackageManagerActivity.class));
        });

        Button btnWebPentest = findViewById(R.id.btn_web_pentest);
        btnWebPentest.setOnClickListener(v -> {
            startActivity(new Intent(this, WebPentestActivity.class));
        });

        Button btnAI = findViewById(R.id.btn_ai);
        btnAI.setOnClickListener(v -> {
            startActivity(new Intent(this, AIAssistantActivity.class));
        });

        Button btnKali = findViewById(R.id.btn_kali);
        btnKali.setOnClickListener(v -> {
            startActivity(new Intent(this, KaliLinuxActivity.class));
        });

        // تهيئة بيئة Linux
        new com.termux.arab.core.LinuxEnv(this).init();

        // إحصائيات
        TextView stats = findViewById(R.id.tv_stats);
        stats.setText(ToolRegistry.getAllTools().size() + " أداة في " + cats.length + " فئات");
    }

    private void openCategory(String category) {
        Intent intent = new Intent(this, ToolListActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}
