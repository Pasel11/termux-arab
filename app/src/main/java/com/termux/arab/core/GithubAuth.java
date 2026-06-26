package com.termux.arab.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.File;

/**
 * إدارة مصادقة GitHub - تخزين آمن للـ Token
 */
public class GithubAuth {

    private static final String PREFS = "github_auth";
    private static final String KEY_TOKEN = "enc_token";
    private static final String KEY_IV = "token_iv";
    private static final String KEY_SECRET = "secret_key";
    private static final String KEY_USER = "github_user";
    private static final String KEY_AVATAR = "github_avatar";
    private static final String KEY_LOGIN = "github_login";

    private final Context context;
    private final SharedPreferences prefs;

    public GithubAuth(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        try {
            SecretKey key = getOrCreateKey();
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] iv = cipher.getIV();
            byte[] encrypted = cipher.doFinal(token.getBytes("UTF-8"));
            String encoded = Base64.encodeToString(encrypted, Base64.NO_WRAP);
            prefs.edit()
                .putString(KEY_TOKEN, encoded)
                .putString(KEY_IV, Base64.encodeToString(iv, Base64.NO_WRAP))
                .apply();
        } catch (Exception e) {
            prefs.edit().putString(KEY_TOKEN, token).apply();
        }
    }

    public String getToken() {
        String stored = prefs.getString(KEY_TOKEN, null);
        if (stored == null) return null;
        try {
            SecretKey key = getOrCreateKey();
            String ivStr = prefs.getString(KEY_IV, null);
            if (ivStr == null) return stored;
            byte[] iv = Base64.decode(ivStr, Base64.NO_WRAP);
            byte[] encrypted = Base64.decode(stored, Base64.NO_WRAP);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            return stored;
        }
    }

    public void saveUserInfo(String login, String name, String avatarUrl) {
        prefs.edit()
            .putString(KEY_LOGIN, login)
            .putString(KEY_USER, name)
            .putString(KEY_AVATAR, avatarUrl)
            .apply();
    }

    public String getLogin() { return prefs.getString(KEY_LOGIN, null); }
    public String getUserName() { return prefs.getString(KEY_USER, null); }
    public String getAvatarUrl() { return prefs.getString(KEY_AVATAR, null); }

    public boolean isLoggedIn() {
        String token = getToken();
        return token != null && !token.isEmpty();
    }

    public void logout() {
        prefs.edit().clear().apply();
    }

    private SecretKey getOrCreateKey() throws Exception {
        String stored = prefs.getString(KEY_SECRET, null);
        if (stored != null) {
            byte[] keyBytes = Base64.decode(stored, Base64.NO_WRAP);
            return new SecretKeySpec(keyBytes, "AES");
        }
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(256);
        SecretKey newKey = gen.generateKey();
        prefs.edit().putString(KEY_SECRET,
            Base64.encodeToString(newKey.getEncoded(), Base64.NO_WRAP)).apply();
        return newKey;
    }
}
