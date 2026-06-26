package com.termux.arab.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.termux.arab.R;
import com.termux.arab.core.VulnDatabase;

import java.util.ArrayList;
import java.util.List;

public class VulnDBActivity extends AppCompatActivity {

    private VulnAdapter adapter;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_vuln_db);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("📚 قاعدة بيانات الثغرات");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        searchInput = findViewById(R.id.vuln_search);
        RecyclerView rv = findViewById(R.id.vuln_recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<VulnDatabase.Vulnerability> all = VulnDatabase.getAll();
        adapter = new VulnAdapter(new ArrayList<>(all));
        rv.setAdapter(adapter);

        // عدّاد
        TextView countText = findViewById(R.id.vuln_count);
        countText.setText(all.size() + " ثغرة وفيروس");

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int b, int c) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {
                filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // أزرار التصنيف
        setupCategoryFilters();
    }

    private void filter(String query) {
        List<VulnDatabase.Vulnerability> all = VulnDatabase.getAll();
        List<VulnDatabase.Vulnerability> filtered = new ArrayList<>();
        for (VulnDatabase.Vulnerability v : all) {
            if (query.isEmpty() ||
                v.cveId.toLowerCase().contains(query.toLowerCase()) ||
                v.name.toLowerCase().contains(query.toLowerCase()) ||
                v.nameAr.contains(query) ||
                v.affectedSoftware.toLowerCase().contains(query.toLowerCase())) {
                filtered.add(v);
            }
        }
        adapter.updateData(filtered);
    }

    private void setupCategoryFilters() {
        LinearLayout filtersLayout = findViewById(R.id.cat_filters);
        String[] cats = VulnDatabase.getCategories();
        for (String cat : cats) {
            TextView chip = new TextView(this);
            String label = cat.equals("all") ? "📊 الكل" : VulnDatabase.getCategoryName(cat);
            chip.setText(label);
            chip.setPadding(40, 20, 40, 20);
            chip.setBackgroundResource(R.drawable.chip_bg);
            chip.setTextSize(13);
            chip.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd(12);
            chip.setLayoutParams(params);

            chip.setOnClickListener(v -> {
                if (cat.equals("all")) {
                    adapter.updateData(VulnDatabase.getAll());
                } else {
                    adapter.updateData(VulnDatabase.getByCategory(cat));
                }
            });
            filtersLayout.addView(chip);
        }
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }

    // === Adapter ===
    static class VulnAdapter extends RecyclerView.Adapter<VulnAdapter.VH> {
        private List<VulnDatabase.Vulnerability> items;

        VulnAdapter(List<VulnDatabase.Vulnerability> items) {
            this.items = items;
        }

        void updateData(List<VulnDatabase.Vulnerability> data) {
            this.items = data;
            notifyDataSetChanged();
        }

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vuln, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int pos) {
            VulnDatabase.Vulnerability v = items.get(pos);

            String sevIcon;
            switch (v.severity) {
                case "critical": sevIcon = "🔴"; break;
                case "high": sevIcon = "🟠"; break;
                case "medium": sevIcon = "🟡"; break;
                case "low": sevIcon = "🟢"; break;
                default: sevIcon = "🔵"; break;
            }

            h.cveText.setText(sevIcon + " " + v.cveId);
            h.cveText.setTextColor(VulnDatabase.getSeverityColor(v.severity));
            h.cveText.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);

            h.nameText.setText(v.nameAr);
            h.nameText.setTextSize(15);

            h.catText.setText(VulnDatabase.getCategoryName(v.category) + " • " +
                VulnDatabase.getSeverityName(v.severity) + " • " + v.year);

            h.descText.setText(v.descriptionAr);

            // تفاصيل قابلة للطي
            h.itemView.setOnClickListener(view -> {
                if (h.details.getVisibility() == View.VISIBLE) {
                    h.details.setVisibility(View.GONE);
                } else {
                    h.affectedText.setText("🎯 المتأثر: " + v.affectedSoftware);
                    h.solText.setText("💡 الحل: " + v.solution);
                    h.details.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override public int getItemCount() { return items.size(); }

        static class VH extends RecyclerView.ViewHolder {
            TextView cveText, nameText, catText, descText, affectedText, solText;
            LinearLayout details;
            VH(View v) {
                super(v);
                cveText = v.findViewById(R.id.vuln_cve);
                nameText = v.findViewById(R.id.vuln_name);
                catText = v.findViewById(R.id.vuln_cat);
                descText = v.findViewById(R.id.vuln_desc);
                affectedText = v.findViewById(R.id.vuln_affected);
                solText = v.findViewById(R.id.vuln_sol);
                details = v.findViewById(R.id.vuln_details);
            }
        }
    }
}
