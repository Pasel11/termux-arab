package com.termux.arab.core;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * عميل GitHub API حقيقي - يتصل بـ api.github.com
 * يدعم: تسجيل دخول، مستودعات، ملفات، إنشاء مستودع، commit، حذف
 */
public class GithubClient {

    private static final String BASE = "https://api.github.com";
    private final GithubAuth auth;

    public interface Callback<T> {
        void onSuccess(T result);
        void onError(String error);
    }

    public GithubClient(Context context) {
        this.auth = new GithubAuth(context);
    }

    private void execute(String method, String endpoint, String body, Callback<String> cb) {
        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(BASE + endpoint);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(method);
                conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
                conn.setRequestProperty("User-Agent", "TermuxArab-App");
                conn.setRequestProperty("Content-Type", "application/json");
                String token = auth.getToken();
                if (token != null) conn.setRequestProperty("Authorization", "token " + token);
                if (body != null) {
                    conn.setDoOutput(true);
                    try (OutputStream os = conn.getOutputStream()) {
                        os.write(body.getBytes("UTF-8"));
                    }
                }
                int code = conn.getResponseCode();
                InputStream is = code >= 200 && code < 400 ? conn.getInputStream() : conn.getErrorStream();
                if (is == null) is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) sb.append(line).append("\n");
                reader.close();
                final String response = sb.toString();
                final int finalCode = code;
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (finalCode >= 200 && finalCode < 400) cb.onSuccess(response);
                    else cb.onError("HTTP " + finalCode + ": " + parseError(response));
                });
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> cb.onError(e.getMessage()));
            } finally {
                if (conn != null) conn.disconnect();
            }
        }).start();
    }

    private String parseError(String json) {
        try { return new JSONObject(json).optString("message", json); }
        catch (Exception e) { return json != null && json.length() > 100 ? json.substring(0, 100) : json; }
    }

    // === التحقق من الـ Token ===
    public void validateToken(Callback<GithubUser> cb) {
        execute("GET", "/user", null, new Callback<String>() {
            @Override public void onSuccess(String resp) {
                try {
                    JSONObject j = new JSONObject(resp);
                    GithubUser u = new GithubUser();
                    u.login = j.optString("login");
                    u.name = j.optString("name");
                    u.avatarUrl = j.optString("avatar_url");
                    u.bio = j.optString("bio");
                    u.publicRepos = j.optInt("public_repos");
                    u.followers = j.optInt("followers");
                    u.following = j.optInt("following");
                    auth.saveUserInfo(u.login, u.name, u.avatarUrl);
                    cb.onSuccess(u);
                } catch (Exception e) { cb.onError("Parse error"); }
            }
            @Override public void onError(String e) { cb.onError(e); }
        });
    }

    // === سرد المستودعات ===
    public void listRepos(Callback<List<GithubRepo>> cb) {
        execute("GET", "/user/repos?per_page=50&sort=updated", null, new Callback<String>() {
            @Override public void onSuccess(String resp) {
                try {
                    JSONArray arr = new JSONArray(resp);
                    List<GithubRepo> repos = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject j = arr.getJSONObject(i);
                        GithubRepo r = new GithubRepo();
                        r.name = j.optString("name");
                        r.fullName = j.optString("full_name");
                        r.description = j.optString("description");
                        r.isPrivate = j.optBoolean("private");
                        r.stars = j.optInt("stargazers_count");
                        r.forks = j.optInt("forks_count");
                        r.language = j.optString("language");
                        r.defaultBranch = j.optString("default_branch");
                        r.htmlUrl = j.optString("html_url");
                        r.updatedAt = j.optString("updated_at");
                        JSONObject owner = j.optJSONObject("owner");
                        if (owner != null) r.owner = owner.optString("login");
                        repos.add(r);
                    }
                    cb.onSuccess(repos);
                } catch (Exception e) { cb.onError("Parse error"); }
            }
            @Override public void onError(String e) { cb.onError(e); }
        });
    }

    // === محتويات مستودع ===
    public void listContents(String owner, String repo, String path, Callback<List<GithubFile>> cb) {
        String endpoint = "/repos/" + owner + "/" + repo + "/contents/" + path;
        execute("GET", endpoint, null, new Callback<String>() {
            @Override public void onSuccess(String resp) {
                try {
                    JSONArray arr = new JSONArray(resp);
                    List<GithubFile> files = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject j = arr.getJSONObject(i);
                        GithubFile f = new GithubFile();
                        f.name = j.optString("name");
                        f.path = j.optString("path");
                        f.sha = j.optString("sha");
                        f.type = j.optString("type");
                        f.size = j.optInt("size");
                        f.downloadUrl = j.optString("download_url");
                        files.add(f);
                    }
                    cb.onSuccess(files);
                } catch (Exception e) { cb.onError("Parse error");
                }
            }
            @Override public void onError(String e) { cb.onError(e); }
        });
    }

    // === قراءة ملف ===
    public void getFileContent(String owner, String repo, String path, Callback<String> cb) {
        execute("GET", "/repos/" + owner + "/" + repo + "/contents/" + path, null, new Callback<String>() {
            @Override public void onSuccess(String resp) {
                try {
                    JSONObject j = new JSONObject(resp);
                    String content = j.optString("content", "");
                    String encoding = j.optString("encoding", "");
                    if ("base64".equals(encoding)) {
                        content = content.replace("\n", "");
                        byte[] decoded = Base64.decode(content, Base64.DEFAULT);
                        cb.onSuccess(new String(decoded, StandardCharsets.UTF_8));
                    } else {
                        cb.onSuccess(content);
                    }
                } catch (Exception e) { cb.onError("Parse error"); }
            }
            @Override public void onError(String e) { cb.onError(e); }
        });
    }

    // === إنشاء مستودع ===
    public void createRepo(String name, String description, boolean isPrivate, Callback<GithubRepo> cb) {
        try {
            JSONObject body = new JSONObject();
            body.put("name", name);
            body.put("description", description);
            body.put("private", isPrivate);
            body.put("auto_init", true);
            execute("POST", "/user/repos", body.toString(), new Callback<String>() {
                @Override public void onSuccess(String resp) {
                    try {
                        JSONObject j = new JSONObject(resp);
                        GithubRepo r = new GithubRepo();
                        r.name = j.optString("name");
                        r.fullName = j.optString("full_name");
                        r.owner = j.optJSONObject("owner").optString("login");
                        r.defaultBranch = "main";
                        cb.onSuccess(r);
                    } catch (Exception e) { cb.onError("Parse error"); }
                }
                @Override public void onError(String e) { cb.onError(e); }
            });
        } catch (Exception e) { cb.onError(e.getMessage()); }
    }

    // === إنشاء/تعديل ملف ===
    public void saveFile(String owner, String repo, String path, String message, String content, String sha, Callback<Boolean> cb) {
        try {
            JSONObject body = new JSONObject();
            body.put("message", message);
            String encoded = Base64.encodeToString(content.getBytes("UTF-8"), Base64.NO_WRAP);
            body.put("content", encoded);
            if (sha != null) body.put("sha", sha);
            execute("PUT", "/repos/" + owner + "/" + repo + "/contents/" + path, body.toString(), new Callback<String>() {
                @Override public void onSuccess(String resp) { cb.onSuccess(true); }
                @Override public void onError(String e) { cb.onError(e); }
            });
        } catch (Exception e) { cb.onError(e.getMessage()); }
    }

    // === سرد commits ===
    public void listCommits(String owner, String repo, Callback<List<GithubCommit>> cb) {
        execute("GET", "/repos/" + owner + "/" + repo + "/commits?per_page=20", null, new Callback<String>() {
            @Override public void onSuccess(String resp) {
                try {
                    JSONArray arr = new JSONArray(resp);
                    List<GithubCommit> commits = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject j = arr.getJSONObject(i);
                        GithubCommit c = new GithubCommit();
                        c.sha = j.optString("sha");
                        JSONObject commit = j.optJSONObject("commit");
                        if (commit != null) {
                            c.message = commit.optString("message");
                            JSONObject author = commit.optJSONObject("author");
                            if (author != null) c.date = author.optString("date");
                        }
                        commits.add(c);
                    }
                    cb.onSuccess(commits);
                } catch (Exception e) { cb.onError("Parse error"); }
            }
            @Override public void onError(String e) { cb.onError(e); }
        });
    }

    // === Models ===
    public static class GithubUser {
        public String login, name, avatarUrl, bio;
        public int publicRepos, followers, following;
    }

    public static class GithubRepo {
        public String name, fullName, description, owner, language, defaultBranch, htmlUrl, updatedAt;
        public boolean isPrivate;
        public int stars, forks;
    }

    public static class GithubFile {
        public String name, path, sha, type, downloadUrl;
        public int size;
        public boolean isDir() { return "dir".equals(type); }
    }

    public static class GithubCommit {
        public String sha, message, date;
    }
}
