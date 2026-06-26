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
