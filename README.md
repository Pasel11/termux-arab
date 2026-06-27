# تيرمكس العرب - الموقع الرسمي

<div dir="rtl">

الموقع الرسمي لتطبيق **تيرمكس العرب** - تطبيق طرفية احترافي بواجهة عربية كاملة لأندرويد.

## ✨ المميزات

- 🎨 واجهة عربية كاملة بدعم RTL
- 📱 17 شاشة احترافية متكاملة
- 🔧 200+ أداة من كالي لينكس
- 💻 100+ أمر Linux حقيقي
- 🛡️ اختبار اختراق ويب بـ 5 مراحل
- 🤖 مساعد ذكاء اصطناعي
- 🐙 تكامل GitHub
- 📲 بدون Root

## 🚀 النشر على GitHub Pages

هذا الموقع جاهز للنشر على GitHub Pages تلقائياً عبر GitHub Actions.

### خطوات النشر:

1. **أنشئ مستودعاً على GitHub** (public)
2. **ارفع الكود** إلى المستودع:

```bash
git init
git add .
git commit -m "Initial commit - Termux Arab website"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/termux-arab.git
git push -u origin main
```

3. **فعّل GitHub Pages**:
   - اذهب إلى Settings → Pages
   - Source: **GitHub Actions**
   - سيتم تشغيل الـ workflow تلقائياً عند كل push

4. **انتظر النشر** (2-3 دقائق)
   - الموقع سيتاح على: `https://YOUR_USERNAME.github.io/termux-arab/`

### ⚠️ خطوة مهمة قبل النشر:

افتح ملف `public/downloads/install-termux-arab.sh` وعدّل السطرين:
```bash
GITHUB_USER="YOUR_GITHUB_USERNAME"  # ← ضع اسم المستخدم في GitHub
GITHUB_REPO="termux-arab"            # ← اسم المستودع
```

## 📥 محتويات مجلد التنزيلات

| الملف | الحجم | الوصف |
|------|------|------|
| `termux-arab-guide.pdf` | 70KB | الدليل الشامل (21 صفحة) |
| `termux-arab-v1.7.3.apk.part0` | 95MB | الجزء الأول من APK |
| `termux-arab-v1.7.3.apk.part1` | 25MB | الجزء الثاني من APK |
| `install-termux-arab.sh` | 4KB | سكريبت تحميل وتجميع APK |

**ملاحظة**: تم تقسيم ملف APK (120MB) إلى جزأين لأن حد الملفات في GitHub هو 100MB.

## 🛠️ التطوير محلياً

```bash
# تثبيت dependencies
bun install

# تشغيل خادم التطوير
bun run dev

# بناء الإنتاج (static export)
bun run build

# الناتج في مجلد ./out
```

## 📝 الترخيص

GNU General Public License v3.0

---

صنع بحب للمستخدمين العرب ❤️

</div>
