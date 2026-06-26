package com.termux.arab.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.termux.arab.R;
import com.termux.arab.core.KaliDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * شاشة كالي لينكس الكاملة - 200+ أداة في 12 فئة
 */
public class KaliLinuxActivity extends AppCompatActivity {

    private KaliToolAdapter adapter;
    private String currentCategory = "popular";
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_kali_linux);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("🐉 كالي لينكس");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        searchInput = findViewById(R.id.kali_search);
        TextView statsText = findViewById(R.id.kali_stats);
        statsText.setText(KaliDatabase.getTotalCount() + " أداة في 12 فئة");

        RecyclerView rv = findViewById(R.id.kali_recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new KaliToolAdapter(new ArrayList<>(KaliDatabase.getPopular()));
        rv.setAdapter(adapter);

        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            filter(v.getText().toString());
            return false;
        });
        searchInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int b, int c) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) { filter(s.toString()); }
            @Override public void afterTextChanged(android.text.Editable s) {}
        });

        setupCategoryButtons();
    }

    private void filter(String query) {
        List<KaliDatabase.KaliTool> all;
        if (currentCategory.equals("popular")) {
            all = KaliDatabase.getPopular();
        } else if (currentCategory.equals("all")) {
            all = KaliDatabase.getAllTools();
        } else {
            all = KaliDatabase.getByCategory(currentCategory);
        }

        if (!query.isEmpty()) {
            List<KaliDatabase.KaliTool> filtered = new ArrayList<>();
            for (KaliDatabase.KaliTool t : all) {
                if (t.name.toLowerCase().contains(query.toLowerCase()) ||
                    t.nameAr.contains(query) ||
                    t.description.toLowerCase().contains(query.toLowerCase()) ||
                    t.descriptionAr.contains(query)) {
                    filtered.add(t);
                }
            }
            adapter.updateData(filtered);
        } else {
            adapter.updateData(all);
        }
    }

    private void setupCategoryButtons() {
        LinearLayout catLayout = findViewById(R.id.kali_categories);
        List<KaliDatabase.KaliCategory> cats = KaliDatabase.getCategories();

        // زر "المميزة"
        addCatButton(catLayout, "🔥 المميزة", "popular");
        addCatButton(catLayout, "📦 الكل", "all");

        for (KaliDatabase.KaliCategory cat : cats) {
            addCatButton(catLayout, cat.icon + " " + cat.nameAr, cat.id);
        }
    }

    private void addCatButton(LinearLayout layout, String label, String catId) {
        TextView btn = new TextView(this);
        btn.setText(label);
        btn.setPadding(40, 20, 40, 20);
        btn.setBackgroundResource(R.drawable.chip_bg);
        btn.setTextSize(13);
        btn.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        btn.setTextColor(catId.equals(currentCategory) ? 0xFFFFFFFF : 0xFF0F4C2A);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd(12);
        btn.setLayoutParams(params);

        btn.setOnClickListener(v -> {
            currentCategory = catId;
            filter(searchInput.getText().toString());
            for (int i = 0; i < layout.getChildCount(); i++) {
                ((TextView) layout.getChildAt(i)).setTextColor(0xFF0F4C2A);
            }
            btn.setTextColor(0xFFFFFFFF);
        });
        layout.addView(btn);
    }

    private void showToolDetails(KaliDatabase.KaliTool tool) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("🐉 " + tool.name);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 30);

        TextView nameAr = new TextView(this);
        nameAr.setText("📝 " + tool.nameAr);
        nameAr.setTextSize(16);
        nameAr.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        layout.addView(nameAr);

        TextView desc = new TextView(this);
        desc.setText("\n📋 " + tool.descriptionAr);
        desc.setTextSize(14);
        desc.setTextColor(0xFF444444);
        layout.addView(desc);

        TextView cmd = new TextView(this);
        cmd.setText("\n💻 الأمر: " + tool.command);
        cmd.setTextSize(14);
        cmd.setTextColor(0xFF0F4C2A);
        cmd.setTypeface(android.graphics.Typeface.MONOSPACE);
        layout.addView(cmd);

        TextView example = new TextView(this);
        example.setText("\n💡 مثال:\n" + tool.example);
        example.setTextSize(13);
        example.setTextColor(0xFF666666);
        example.setTypeface(android.graphics.Typeface.MONOSPACE);
        example.setBackgroundColor(0xFF1A1A1A);
        example.setTextColor(0xFF00FF41);
        example.setPadding(20, 16, 20, 16);
        layout.addView(example);

        builder.setView(layout);
        builder.setPositiveButton("▶ تشغيل", (d, w) -> {
            Intent intent = new Intent(this, ToolRunnerActivity.class);
            intent.putExtra("tool_id", tool.name);
            // إنشاء أداة مؤقتة
            com.termux.arab.model.Tool t = new com.termux.arab.model.Tool();
            t.id = tool.name;
            t.name = tool.nameAr;
            t.nameEn = tool.name;
            t.description = tool.descriptionAr;
            t.command = tool.command;
            t.example = tool.example;
            t.icon = "🐉";
            t.category = "pentest";
            t.needsArgs = true;

            // نمرر الأداة عبر intent extras
            intent.putExtra("tool_id", "kali_" + tool.name);
            intent.putExtra("kali_name", tool.nameAr);
            intent.putExtra("kali_command", tool.command);
            intent.putExtra("kali_example", tool.example);
            intent.putExtra("kali_desc", tool.descriptionAr);
            startActivity(intent);
        });
        builder.setNegativeButton("إغلاق", null);
        builder.show();
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }

    // === Adapter ===
    class KaliToolAdapter extends RecyclerView.Adapter<KaliToolAdapter.VH> {
        private List<KaliDatabase.KaliTool> tools;

        KaliToolAdapter(List<KaliDatabase.KaliTool> tools) { this.tools = tools; }

        void updateData(List<KaliDatabase.KaliTool> data) {
            this.tools = data;
            notifyDataSetChanged();
        }

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kali_tool, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int pos) {
            KaliDatabase.KaliTool t = tools.get(pos);
            h.name.setText((t.popular ? "⭐ " : "🔧 ") + t.name);
            h.nameAr.setText(t.nameAr);
            h.desc.setText(t.descriptionAr);
            h.itemView.setOnClickListener(v -> showToolDetails(t));
        }

        @Override public int getItemCount() { return tools.size(); }

        class VH extends RecyclerView.ViewHolder {
            TextView name, nameAr, desc;
            VH(View v) {
                super(v);
                name = v.findViewById(R.id.kali_tool_name);
                nameAr = v.findViewById(R.id.kali_tool_name_ar);
                desc = v.findViewById(R.id.kali_tool_desc);
            }
        }
    }
}
