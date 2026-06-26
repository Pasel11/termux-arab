package com.termux.arab.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.termux.arab.R;
import com.termux.arab.core.CommandExecutor;
import com.termux.arab.core.ToolRegistry;
import com.termux.arab.model.Tool;

public class ToolRunnerActivity extends AppCompatActivity {
    private CommandExecutor executor;
    private Tool tool;
    private TextView output;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_tool_runner);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        String toolId = getIntent().getStringExtra("tool_id");

        // فحص إذا كانت أداة كالي
        if (toolId != null && toolId.startsWith("kali_")) {
            tool = new Tool();
            tool.id = toolId;
            tool.name = getIntent().getStringExtra("kali_name");
            tool.nameEn = toolId.replace("kali_", "");
            tool.description = getIntent().getStringExtra("kali_desc");
            tool.command = getIntent().getStringExtra("kali_command");
            tool.example = getIntent().getStringExtra("kali_example");
            tool.icon = "🐉";
            tool.category = "pentest";
            tool.needsArgs = true;
        } else {
            for (Tool t : ToolRegistry.getAllTools()) {
                if (t.id.equals(toolId)) { tool = t; break; }
            }
        }
        if (tool == null) { finish(); return; }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(tool.icon + " " + tool.name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        executor = new CommandExecutor();
        output = findViewById(R.id.output_text);
        scroll = findViewById(R.id.output_scroll);
        EditText argsInput = findViewById(R.id.args_input);
        Button runBtn = findViewById(R.id.btn_run);
        Button clearBtn = findViewById(R.id.btn_clear);
        ProgressBar progress = findViewById(R.id.progress);

        // عرض معلومات الأداة
        TextView nameTv = findViewById(R.id.tv_tool_name);
        nameTv.setText(tool.icon + " " + tool.name);
        TextView descTv = findViewById(R.id.tv_tool_desc);
        descTv.setText(tool.description);
        TextView exampleTv = findViewById(R.id.tv_example);
        if (tool.example != null && !tool.example.isEmpty()) {
            exampleTv.setText("💡 " + tool.example);
            exampleTv.setVisibility(View.VISIBLE);
        }

        if (!tool.needsArgs) {
            argsInput.setVisibility(View.GONE);
        }

        runBtn.setOnClickListener(v -> {
            String cmd = tool.command;
            if (tool.needsArgs) {
                String args = argsInput.getText().toString().trim();
                if (args.isEmpty()) {
                    argsInput.setError("أدخل المعطيات");
                    return;
                }
                cmd = cmd + " " + args;
            }
            output.setText("");
            progress.setVisibility(View.VISIBLE);
            appendOutput("⚡ تنفيذ: " + cmd + "\n\n");
            executor.execute(cmd, this, new CommandExecutor.Listener() {
                @Override
                public void onOutput(String line) {
                    appendOutput(line + "\n");
                }
                @Override
                public void onComplete(int code) {
                    runOnUiThread(() -> {
                        progress.setVisibility(View.GONE);
                        appendOutput("\n✅ اكتمل (exit: " + code + ")");
                    });
                }
            });
        });

        clearBtn.setOnClickListener(v -> output.setText(""));
    }

    private void appendOutput(String text) {
        runOnUiThread(() -> {
            output.append(text);
            scroll.post(() -> scroll.fullScroll(View.FOCUS_DOWN));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null) executor.shutdown();
    }

    @Override public boolean onSupportNavigateUp() { finish(); return true; }
}
