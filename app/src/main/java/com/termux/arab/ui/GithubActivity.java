package com.termux.arab.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.termux.arab.R;
import com.termux.arab.core.GithubAuth;
import com.termux.arab.core.GithubClient;

import java.util.ArrayList;
import java.util.List;

/**
 * شاشة تكامل GitHub الكاملة
 * - تسجيل دخول بـ Token
 * - عرض البروفايل
 * - سرد المستودعات
 * - تصفح الملفات
 * - عرض محتوى الملفات
 * - إنشاء مستودع جديد
 * - إنشاء/تعديل ملف (commit)
 * - عرض سجل Commits
 */
public class GithubActivity extends AppCompatActivity {

    private GithubAuth auth;
    private GithubClient client;
    private LinearLayout loginLayout, contentLayout;
    private EditText tokenInput;
    private ProgressBar progress;
    private TextView profileName, profileLogin, profileBio, profileStats;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_github);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("🐙 GitHub");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        auth = new GithubAuth(this);
        client = new GithubClient(this);

        loginLayout = findViewById(R.id.github_login_layout);
        contentLayout = findViewById(R.id.github_content_layout);
        tokenInput = findViewById(R.id.github_token_input);
        progress = findViewById(R.id.github_progress);
        profileName = findViewById(R.id.profile_name);
        profileLogin = findViewById(R.id.profile_login);
        profileBio = findViewById(R.id.profile_bio);
        profileStats = findViewById(R.id.profile_stats);

        Button loginBtn = findViewById(R.id.btn_github_login);
        Button getTokenBtn = findViewById(R.id.btn_get_token);
        Button logoutBtn = findViewById(R.id.btn_github_logout);
        Button refreshBtn = findViewById(R.id.btn_github_refresh);
        Button createRepoBtn = findViewById(R.id.btn_create_repo);

        loginBtn.setOnClickListener(v -> doLogin());
        getTokenBtn.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://github.com/settings/tokens/new?scopes=repo,workflow,user,delete_repo,gist,notifications&description=TermuxArab")));
        });
        logoutBtn.setOnClickListener(v -> {
            auth.logout();
            showLogin();
        });
        refreshBtn.setOnClickListener(v -> loadProfile());
        createRepoBtn.setOnClickListener(v -> showCreateRepoDialog());

        if (auth.isLoggedIn()) {
            showContent();
            loadProfile();
        } else {
            showLogin();
        }
    }

    private void showLogin() {
        loginLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
    }

    private void showContent() {
        loginLayout.setVisibility(View.GONE);
        contentLayout.setVisibility(View.VISIBLE);
    }

    private void doLogin() {
        String token = tokenInput.getText().toString().trim();
        if (token.isEmpty()) { tokenInput.setError("أدخل الـ Token"); return; }
        if (token.length() < 20) { showToast("Token قصير جداً"); return; }

        progress.setVisibility(View.VISIBLE);
        auth.saveToken(token);
        client.validateToken(new GithubClient.Callback<GithubClient.GithubUser>() {
            @Override public void onSuccess(GithubClient.GithubUser user) {
                progress.setVisibility(View.GONE);
                showContent();
                displayProfile(user);
                loadRepos();
            }
            @Override public void onError(String error) {
                progress.setVisibility(View.GONE);
                auth.logout();
                showToast("❌ فشل: " + error);
            }
        });
    }

    private void loadProfile() {
        progress.setVisibility(View.VISIBLE);
        client.validateToken(new GithubClient.Callback<GithubClient.GithubUser>() {
            @Override public void onSuccess(GithubClient.GithubUser user) {
                progress.setVisibility(View.GONE);
                displayProfile(user);
                loadRepos();
            }
            @Override public void onError(String error) {
                progress.setVisibility(View.GONE);
                showToast("❌ " + error);
            }
        });
    }

    private void displayProfile(GithubClient.GithubUser user) {
        profileName.setText(user.name != null && !user.name.isEmpty() ? user.name : user.login);
        profileLogin.setText("@" + user.login);
        profileBio.setText(user.bio != null && !user.bio.isEmpty() ? user.bio : "");
        profileBio.setVisibility(user.bio != null && !user.bio.isEmpty() ? View.VISIBLE : View.GONE);
        profileStats.setText("📚 " + user.publicRepos + " مستودع  |  👥 " + user.followers + " متابع  |  ➡️ " + user.following + " يتابع");
    }

    private void loadRepos() {
        RecyclerView reposRv = findViewById(R.id.repos_recycler);
        reposRv.setLayoutManager(new LinearLayoutManager(this));
        TextView reposStatus = findViewById(R.id.repos_status);
        reposStatus.setVisibility(View.VISIBLE);
        reposStatus.setText("🔄 تحميل المستودعات...");

        client.listRepos(new GithubClient.Callback<List<GithubClient.GithubRepo>>() {
            @Override public void onSuccess(List<GithubClient.GithubRepo> repos) {
                reposStatus.setVisibility(View.GONE);
                reposRv.setAdapter(new RepoAdapter(repos, GithubActivity.this::openRepo));
            }
            @Override public void onError(String error) {
                reposStatus.setText("❌ " + error);
            }
        });
    }

    private void openRepo(GithubClient.GithubRepo repo) {
        // فتح محتويات المستودع في dialog
        showRepoContents(repo);
    }

    private void showRepoContents(GithubClient.GithubRepo repo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("📦 " + repo.name);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 30, 40, 30);

        ProgressBar pb = new ProgressBar(this);
        pb.setVisibility(View.VISIBLE);
        layout.addView(pb);

        TextView contentsTv = new TextView(this);
        contentsTv.setMovementMethod(new ScrollingMovementMethod());
        layout.addView(contentsTv);

        builder.setView(layout);
        builder.setNegativeButton("إغلاق", null);
        builder.setPositiveButton("📋 Commits", (d, w) -> showCommits(repo));

        AlertDialog dialog = builder.show();

        client.listContents(repo.owner, repo.name, "", new GithubClient.Callback<List<GithubClient.GithubFile>>() {
            @Override public void onSuccess(List<GithubClient.GithubFile> files) {
                pb.setVisibility(View.GONE);
                StringBuilder sb = new StringBuilder();
                sb.append("📁 ").append(repo.fullName).append("\n\n");
                for (GithubClient.GithubFile f : files) {
                    sb.append(f.isDir() ? "📁 " : "📄 ").append(f.name);
                    if (!f.isDir()) sb.append(" (").append(formatSize(f.size)).append(")");
                    sb.append("\n");
                }
                contentsTv.setText(sb.toString());
            }
            @Override public void onError(String error) {
                pb.setVisibility(View.GONE);
                contentsTv.setText("❌ " + error);
            }
        });
    }

    private void showCommits(GithubClient.GithubRepo repo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("📋 Commits - " + repo.name);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 30, 40, 30);

        ProgressBar pb = new ProgressBar(this);
        pb.setVisibility(View.VISIBLE);
        layout.addView(pb);

        TextView commitsTv = new TextView(this);
        commitsTv.setMovementMethod(new ScrollingMovementMethod());
        layout.addView(commitsTv);

        builder.setView(layout);
        builder.setNegativeButton("إغلاق", null);
        AlertDialog dialog = builder.show();

        client.listCommits(repo.owner, repo.name, new GithubClient.Callback<List<GithubClient.GithubCommit>>() {
            @Override public void onSuccess(List<GithubClient.GithubCommit> commits) {
                pb.setVisibility(View.GONE);
                StringBuilder sb = new StringBuilder();
                for (GithubClient.GithubCommit c : commits) {
                    sb.append("🔹 ").append(c.sha.substring(0, 7));
                    sb.append("\n   ").append(c.message.split("\n")[0]);
                    sb.append("\n   📅 ").append(c.date != null ? c.date.substring(0, 10) : "-");
                    sb.append("\n\n");
                }
                commitsTv.setText(sb.toString());
            }
            @Override public void onError(String error) {
                pb.setVisibility(View.GONE);
                commitsTv.setText("❌ " + error);
            }
        });
    }

    private void showCreateRepoDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 30, 40, 30);

        EditText nameEt = new EditText(this);
        nameEt.setHint("اسم المستودع");
        layout.addView(nameEt);

        EditText descEt = new EditText(this);
        descEt.setHint("الوصف (اختياري)");
        layout.addView(descEt);

        new AlertDialog.Builder(this)
            .setTitle("➕ مستودع جديد")
            .setView(layout)
            .setPositiveButton("إنشاء", (d, w) -> {
                String name = nameEt.getText().toString().trim();
                String desc = descEt.getText().toString().trim();
                if (name.isEmpty()) { showToast("أدخل الاسم"); return; }
                progress.setVisibility(View.VISIBLE);
                client.createRepo(name, desc, false, new GithubClient.Callback<GithubClient.GithubRepo>() {
                    @Override public void onSuccess(GithubClient.GithubRepo repo) {
                        progress.setVisibility(View.GONE);
                        showToast("✅ تم إنشاء " + repo.fullName);
                        loadRepos();
                    }
                    @Override public void onError(String error) {
                        progress.setVisibility(View.GONE);
                        showToast("❌ " + error);
                    }
                });
            })
            .setNegativeButton("إلغاء", null)
            .show();
    }

    private String formatSize(int bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1048576) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / 1048576.0);
    }

    private void showToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }

    // === Adapter ===
    static class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.VH> {
        private final List<GithubClient.GithubRepo> repos;
        private final RepoClickListener listener;

        interface RepoClickListener { void onClick(GithubClient.GithubRepo repo); }

        RepoAdapter(List<GithubClient.GithubRepo> repos, RepoClickListener listener) {
            this.repos = repos; this.listener = listener;
        }

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_github_repo, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int pos) {
            GithubClient.GithubRepo r = repos.get(pos);
            h.name.setText(r.name);
            h.desc.setText(r.description != null && !r.description.isEmpty() ? r.description : "لا يوجد وصف");
            h.meta.setText("⭐ " + r.stars + "  🔱 " + r.forks + (r.language != null ? "  🌐 " + r.language : "") + (r.isPrivate ? "  🔒 خاص" : ""));
            h.itemView.setOnClickListener(v -> listener.onClick(r));
        }

        @Override public int getItemCount() { return repos.size(); }

        static class VH extends RecyclerView.ViewHolder {
            TextView name, desc, meta;
            VH(View v) {
                super(v);
                name = v.findViewById(R.id.gh_repo_name);
                desc = v.findViewById(R.id.gh_repo_desc);
                meta = v.findViewById(R.id.gh_repo_meta);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
