package com.termux.arab.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.termux.arab.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * نافذة ذكاء اصطناعي عائمة - تظهر على أي شاشة
 */
public class AIFloatingWindow {

    private final Context context;
    private WindowManager windowManager;
    private View floatingView;
    private View chatView;
    private WindowManager.LayoutParams params;
    private boolean isVisible = false;
    private SharedPreferences prefs;
    private TextView chatOutput;
    private EditText chatInput;
    private ScrollView chatScroll;

    public AIFloatingWindow(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences("ai_prefs", Context.MODE_PRIVATE);
    }

    public void show() {
        if (isVisible) return;
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(context)) return;

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int layoutType = Build.VERSION.SDK_INT >= 26 ?
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
            WindowManager.LayoutParams.TYPE_PHONE;

        params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 20;
        params.y = 200;

        floatingView = LayoutInflater.from(context).inflate(R.layout.floating_ai_button, null);
        windowManager.addView(floatingView, params);

        Button fab = floatingView.findViewById(R.id.fab_ai);
        fab.setOnClickListener(v -> toggleChat());

        // السحب
        fab.setOnTouchListener(new View.OnTouchListener() {
            int initialX, initialY;
            float touchX, touchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        touchX = event.getRawX();
                        touchY = event.getRawY();
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int)(event.getRawX() - touchX);
                        params.y = initialY + (int)(event.getRawY() - touchY);
                        windowManager.updateViewLayout(floatingView, params);
                        return false;
                }
                return false;
            }
        });

        isVisible = true;
    }

    private void toggleChat() {
        if (chatView != null && chatView.isShown()) {
            windowManager.removeView(chatView);
            chatView = null;
            return;
        }

        chatView = LayoutInflater.from(context).inflate(R.layout.floating_ai_chat, null);
        WindowManager.LayoutParams chatParams = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            params.type,
            WindowManager.LayoutParams.FLAG_DIM_BEHIND,
            PixelFormat.TRANSLUCENT
        );

        chatOutput = chatView.findViewById(R.id.floating_chat_output);
        chatInput = chatView.findViewById(R.id.floating_chat_input);
        chatScroll = chatView.findViewById(R.id.floating_chat_scroll);
        Button sendBtn = chatView.findViewById(R.id.floating_btn_send);
        Button closeBtn = chatView.findViewById(R.id.floating_btn_close);
        EditText apiKeyInput = chatView.findViewById(R.id.floating_api_key);

        String savedKey = prefs.getString("api_key", "");
        if (!savedKey.isEmpty()) {
            apiKeyInput.setVisibility(View.GONE);
        }

        chatOutput.setText("🤖 مساعد تيرمكس العرب\n\nأنا متصل بالتطبيق وأستطيع:\n• شرح الثغرات والأدوات\n• اقتراح أوامر\n• تحليل نتائج\n• حلول أمنية\n\nاكتب سؤالك 👇\n\n");

        sendBtn.setOnClickListener(v -> {
            String msg = chatInput.getText().toString().trim();
            if (msg.isEmpty()) return;

            String apiKey = prefs.getString("api_key", "");
            if (apiKey.isEmpty() && apiKeyInput.getVisibility() == View.VISIBLE) {
                apiKey = apiKeyInput.getText().toString().trim();
                if (!apiKey.isEmpty()) {
                    prefs.edit().putString("api_key", apiKey).apply();
                    apiKeyInput.setVisibility(View.GONE);
                }
            }

            chatOutput.append("👤 أنت: " + msg + "\n\n");
            chatInput.setText("");
            sendBtn.setEnabled(false);

            final String finalApiKey = apiKey;
            final String finalMsg = msg;
            final Handler handler = new Handler(Looper.getMainLooper());

            new Thread(() -> {
                String response;
                if (!finalApiKey.isEmpty()) {
                    response = callAI(finalApiKey, finalMsg);
                } else {
                    response = generateLocalResponse(finalMsg);
                }
                handler.post(() -> {
                    chatOutput.append("🤖 " + response + "\n\n─────────\n\n");
                    chatScroll.post(() -> chatScroll.fullScroll(View.FOCUS_DOWN));
                    sendBtn.setEnabled(true);
                });
            }).start();
        });

        closeBtn.setOnClickListener(v -> {
            windowManager.removeView(chatView);
            chatView = null;
        });

        windowManager.addView(chatView, chatParams);
    }

    private String callAI(String apiKey, String message) {
        try {
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(60000);

            JSONObject body = new JSONObject();
            body.put("model", "gpt-3.5-turbo");
            body.put("max_tokens", 400);
            body.put("temperature", 0.7);

            JSONArray messages = new JSONArray();
            JSONObject sys = new JSONObject();
            sys.put("role", "system");
            sys.put("content", "أنت مساعد أمني خبير في اختبار الاختراق. تجيب بالعربية بإيجاز ووضوح.");
            messages.put(sys);
            JSONObject user = new JSONObject();
            user.put("role", "user");
            user.put("content", message);
            messages.put(user);
            body.put("messages", messages);

            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.toString().getBytes("UTF-8"));
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                conn.getResponseCode() < 400 ? conn.getInputStream() : conn.getErrorStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();

            JSONObject resp = new JSONObject(sb.toString());
            return resp.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        } catch (Exception e) {
            return generateLocalResponse(message);
        }
    }

    private String generateLocalResponse(String msg) {
        String m = msg.toLowerCase();
        if (m.contains("sql")) return "💉 حقن SQL:\n\n• الاستغلال: ' OR '1'='1\n• البحث: sqlmap -u URL\n• الحماية: Prepared Statements";
        if (m.contains("xss")) return "⚡ XSS:\n\n• الاستغلال: <script>alert(1)</script>\n• الحماية: HTML encoding + CSP";
        if (m.contains("nmap")) return "📡 Nmap:\n• nmap -sV target (كشف الخدمات)\n• nmap -A target (فحص شامل)\n• nmap --script vuln target";
        if (m.contains("wifi") || m.contains("واي")) return "📶 واي فاي:\n• airmon-ng start wlan0\n• airodump-ng wlan0mon\n• aircrack-ng -w wordlist.cap";
        if (m.contains("كلمة") || m.contains("password")) return "🔐 كلمات المرور:\n• john --wordlist=rockyou.txt hash\n• hashcat -m 0 hash word\n• hydra -l admin -P pass.txt ssh://target";
        if (m.contains("metasploit") || m.contains("ميتا")) return "💥 Metasploit:\n• msfconsole\n• use exploit/multi/handler\n• set PAYLOAD windows/meterpreter/reverse_tcp";
        if (m.contains("help") || m.contains("مساعدة")) return "🤖 يمكنني مساعدتك في:\n• SQL injection\n• XSS\n• Nmap\n• WiFi cracking\n• Password cracking\n• Metasploit\n• Web pentest\n\nاكتب ما تريد!";
        return "🤔 سؤال جيد!\n\nيمكنني شرح:\n• SQL injection\n• XSS\n• Nmap\n• WiFi\n• Passwords\n• Metasploit\n• Web pentest\n\nجرّب: 'اشرح SQL injection'";
    }

    public void hide() {
        if (isVisible && floatingView != null) {
            windowManager.removeView(floatingView);
            isVisible = false;
        }
        if (chatView != null) {
            windowManager.removeView(chatView);
            chatView = null;
        }
    }

    public boolean isVisible() { return isVisible; }
}
