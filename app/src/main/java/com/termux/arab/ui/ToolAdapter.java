package com.termux.arab.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.termux.arab.R;
import com.termux.arab.model.Tool;

public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.VH> {
    private final java.util.List<Tool> tools;
    private final ClickListener listener;

    interface ClickListener { void onClick(Tool tool); }

    ToolAdapter(java.util.List<Tool> tools, ClickListener listener) {
        this.tools = tools; this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tool, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Tool t = tools.get(pos);
        h.icon.setText(t.icon);
        h.name.setText(t.name);
        h.nameEn.setText(t.nameEn);
        h.desc.setText(t.description);
        if (t.riskLevel >= 2) {
            h.riskBadge.setVisibility(View.VISIBLE);
            h.riskText.setText("⚠️ خطير");
            h.riskBadge.setBackgroundColor(0xFFFF5722);
        } else if (t.riskLevel == 1) {
            h.riskBadge.setVisibility(View.VISIBLE);
            h.riskText.setText("تحذير");
            h.riskBadge.setBackgroundColor(0xFFFF9800);
        } else {
            h.riskBadge.setVisibility(View.GONE);
        }
        if (t.requiresRoot) {
            h.rootBadge.setVisibility(View.VISIBLE);
        } else {
            h.rootBadge.setVisibility(View.GONE);
        }
        h.itemView.setOnClickListener(v -> listener.onClick(t));
    }

    @Override public int getItemCount() { return tools.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView icon, name, nameEn, desc, riskText;
        LinearLayout riskBadge, rootBadge;
        VH(View v) {
            super(v);
            icon = v.findViewById(R.id.tool_icon);
            name = v.findViewById(R.id.tool_name);
            nameEn = v.findViewById(R.id.tool_name_en);
            desc = v.findViewById(R.id.tool_desc);
            riskBadge = v.findViewById(R.id.risk_badge);
            riskText = v.findViewById(R.id.risk_text);
            rootBadge = v.findViewById(R.id.root_badge);
        }
    }
}
