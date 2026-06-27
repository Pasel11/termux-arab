"use client";

import { useState, useEffect } from "react";
import {
  Terminal,
  Shield,
  Database,
  Network,
  Activity,
  Lock,
  Globe,
  Github,
  Package,
  Bug,
  Bot,
  Skull,
  Download,
  FileText,
  ChevronDown,
  Menu,
  X,
  CheckCircle2,
  Zap,
  Smartphone,
  Code2,
  Wifi,
  Search,
  Cpu,
  HardDrive,
  Battery,
  Eye,
  AlertTriangle,
  Star,
  Users,
  Award,
  Sparkles,
  BookOpen,
  ArrowLeft,
  Terminal as TerminalIcon,
} from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { AdComponent } from "@/components/AdComponent";

export default function HomePage() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [scrolled, setScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => setScrolled(window.scrollY > 20);
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  const navLinks = [
    { href: "#features", label: "المميزات" },
    { href: "#screens", label: "الشاشات" },
    { href: "#tools", label: "الأدوات" },
    { href: "#kali", label: "كالي لينكس" },
    { href: "#download", label: "تحميل" },
    { href: "#faq", label: "الأسئلة" },
  ];

  const stats = [
    { icon: Smartphone, value: "17", label: "شاشة احترافية" },
    { icon: Skull, value: "+200", label: "أداة كالي لينكس" },
    { icon: TerminalIcon, value: "+100", label: "أمر Linux حقيقي" },
    { icon: Star, value: "4.8", label: "تقييم المستخدمين" },
  ];

  const features = [
    {
      icon: Terminal,
      title: "طرفية تفاعلية حقيقية",
      desc: "طرفية تعمل بـ /system/bin/sh وتدعم تنفيذ أوامر Linux مباشرة على هاتفك مع نتائج فعلية.",
      color: "from-emerald-500 to-green-600",
    },
    {
      icon: Shield,
      title: "مكتشف الثغرات الأمنية",
      desc: "يفحص جهازك فعلياً بحثاً عن ثغرات في 7 جوانب، مع درجة أمان (0-100) وتقييم من A+ إلى F.",
      color: "from-red-500 to-rose-600",
    },
    {
      icon: Network,
      title: "ماسح شبكات حقيقي",
      desc: "ماسح منافذ يستخدم Java Sockets لفحص المنافذ المفتوحة على أي مضيف، مع 20 مسار متوازي.",
      color: "from-blue-500 to-cyan-600",
    },
    {
      icon: Activity,
      title: "مراقب نظام في الوقت الفعلي",
      desc: "مراقبة CPU, RAM, تخزين، بطارية، شبكة، ووقت التشغيل بقراءات حقيقية من نظام التشغيل.",
      color: "from-purple-500 to-indigo-600",
    },
    {
      icon: Lock,
      title: "أدوات كلمات المرور",
      desc: "تحليل قوة كلمات المرور، توليد كلمات قوية، وحساب تجزئة (MD5, SHA-1, SHA-256, SHA-512).",
      color: "from-amber-500 to-orange-600",
    },
    {
      icon: Globe,
      title: "اختبار HTTP / API",
      desc: "أداة اختبار HTTP حقيقية ترسل طلبات GET, POST, PUT, DELETE, HEAD, PATCH لأي خادم أو API.",
      color: "from-cyan-500 to-teal-600",
    },
    {
      icon: Github,
      title: "تكامل GitHub الكامل",
      desc: "تصفح مستودعاتك، إنشاء مستودعات، عرض Commits والملفات عبر Personal Access Token مشفّر بـ AES-256.",
      color: "from-gray-600 to-slate-700",
    },
    {
      icon: Bug,
      title: "اختبار اختراق الويب",
      desc: "منصة فحص ويب حقيقية بـ 5 مراحل متكاملة من الاستطلاع حتى التقرير النهائي مع درجة أمان.",
      color: "from-rose-500 to-pink-600",
    },
    {
      icon: Bot,
      title: "مساعد الذكاء الاصطناعي",
      desc: "مساعد ذكي يشرح الثغرات والأدوات ويقدم نصائح أمنية. يعمل بدون API أو مع OpenAI API.",
      color: "from-violet-500 to-purple-600",
    },
    {
      icon: Package,
      title: "مدير الحزم",
      desc: "50+ حزمة جاهزة للتثبيت في 11 فئة: لغات، محررات، أدوات شبكة، اختراق، وتطوير.",
      color: "from-teal-500 to-emerald-600",
    },
    {
      icon: Database,
      title: "قاعدة بيانات الثغرات",
      desc: "35+ ثغرة وفيروس معروف مع تفاصيل كاملة: رقم CVE، الوصف، البرمجيات المتأثرة، والحل المقترح.",
      color: "from-orange-500 to-red-600",
    },
    {
      icon: Download,
      title: "تثبيت أدوات حقيقية",
      desc: "تثبيت BusyBox (200+ أمر Linux) و20+ أداة حقيقية مثل nmap, sqlmap, hydra مباشرة من GitHub.",
      color: "from-green-500 to-emerald-700",
    },
  ];

  const screens = [
    { name: "الشاشة الرئيسية", icon: Smartphone, desc: "لوحة تحكم شاملة بـ 16 زراً للوصول السريع" },
    { name: "الطرفية التفاعلية", icon: Terminal, desc: "طرفية حقيقية بنتائج فعلية ودعم تاريخ الأوامر" },
    { name: "قائمة الأدوات", icon: Package, desc: "8 فئات بأكثر من 50 أداة احترافية" },
    { name: "مشغّل الأدوات", icon: Zap, desc: "تشغيل أي أداة مع إدخال المعطيات وعرض النتائج" },
    { name: "مكتشف الثغرات", icon: Shield, desc: "فحص أمني شامل للجهاز في 7 جوانب" },
    { name: "قاعدة الثغرات", icon: Database, desc: "35+ ثغرة وفيروس معروف بالتفاصيل" },
    { name: "ماسح الشبكات", icon: Network, desc: "فحص المنافذ المفتوحة على أي مضيف" },
    { name: "مراقب النظام", icon: Activity, desc: "CPU, RAM, تخزين، بطارية، شبكة" },
    { name: "أدوات كلمات المرور", icon: Lock, desc: "تحليل، توليد، تجزئة" },
    { name: "اختبار HTTP", icon: Globe, desc: "إرسال طلبات HTTP حقيقية بأي طريقة" },
    { name: "تكامل GitHub", icon: Github, desc: "تصفح مستودعاتك وإنشاء أخرى" },
    { name: "مدير الحزم", icon: Package, desc: "50+ حزمة للتثبيت بضغطة زر" },
    { name: "اختبار اختراق الويب", icon: Bug, desc: "5 مراحل فحص شامل للمواقع" },
    { name: "مساعد AI", icon: Bot, desc: "ذكاء اصطناعي يساعدك في الأمن" },
    { name: "كالي لينكس", icon: Skull, desc: "200+ أداة في 12 فئة" },
    { name: "تثبيت أدوات", icon: Download, desc: "تنزيل أدوات حقيقية من GitHub" },
    { name: "المساعد العائم", icon: Eye, desc: "AI على أي شاشة" },
  ];

  const toolCategories = [
    { name: "اختبار الاختراق", count: 10, tools: "nmap, sqlmap, hydra, john, metasploit", color: "bg-red-500", icon: Bug },
    { name: "الشبكات", count: 9, tools: "ping, traceroute, curl, wget, netstat", color: "bg-blue-500", icon: Network },
    { name: "الويب", count: 6, tools: "nikto, gobuster, wpscan, dirb, whatweb", color: "bg-purple-500", icon: Globe },
    { name: "كلمات المرور", count: 4, tools: "john, hashcat, hydra, crunch, cewl", color: "bg-orange-500", icon: Lock },
    { name: "الاستطلاع", count: 5, tools: "theharvester, sublist3r, shodan, recon-ng", color: "bg-green-500", icon: Search },
    { name: "الواي فاي", count: 3, tools: "aircrack-ng, reaver, wifite, kismet", color: "bg-amber-700", icon: Wifi },
    { name: "التطوير", count: 7, tools: "python, nodejs, git, vim, nano, clang, make", color: "bg-indigo-500", icon: Code2 },
    { name: "التحليل الجنائي", count: 4, tools: "binwalk, testdisk, volatility, autopsy", color: "bg-slate-500", icon: Eye },
  ];

  const kaliCategories = [
    { name: "جمع المعلومات", count: 30, tools: "nmap, masscan, theharvester, shodan" },
    { name: "تحليل الثغرات", count: 12, tools: "nikto, nuclei, lynis" },
    { name: "تحليل الويب", count: 26, tools: "burpsuite, sqlmap, wpscan, gobuster" },
    { name: "قواعد البيانات", count: 10, tools: "sqlmap, redis-cli, mysql" },
    { name: "كلمات المرور", count: 24, tools: "john, hashcat, hydra, crunch" },
    { name: "هجمات لاسلكية", count: 15, tools: "aircrack-ng, reaver, wifite" },
    { name: "هندسة عكسية", count: 15, tools: "ghidra, radare2, gdb, binwalk" },
    { name: "أدوات الاستغلال", count: 20, tools: "metasploit, searchsploit, msfvenom" },
    { name: "التقاط وانتحال", count: 15, tools: "wireshark, ettercap, bettercap, scapy" },
    { name: "ما بعد الاختراق", count: 15, tools: "mimikatz, linpeas, winpeas" },
    { name: "التحليل الجنائي", count: 15, tools: "autopsy, volatility, testdisk" },
    { name: "أدوات التقارير", count: 10, tools: "cherrytree, dradis, faraday" },
  ];

  const faqs = [
    {
      q: "هل يحتاج تيرمكس العرب إلى صلاحيات Root؟",
      a: "لا، التطبيق يعمل بدون Root على أي جهاز أندرويد 7.0 أو أحدث. كل الأدوات تعمل في بيئة محاكاة خاصة بالتطبيق بدون الحاجة لصلاحيات إدارية.",
    },
    {
      q: "هل التطبيق مجاني؟",
      a: "نعم، تيرمكس العرب تطبيق مجاني بالكامل تحت رخصة GPLv3. لا توجد عمليات شراء داخل التطبيق ولا اشتراكات.",
    },
    {
      q: "كم عدد الأدوات المتوفرة في التطبيق؟",
      a: "يحتوي التطبيق على أكثر من 200 أداة من كالي لينكس في 12 فئة مختلفة، بالإضافة إلى 100+ أمر Linux حقيقي قابل للتنفيذ في الطرفية.",
    },
    {
      q: "ما هي متطلبات تشغيل التطبيق؟",
      a: "جهاز أندرويد 7.0 أو أحدث، مساحة تخزين 50MB على الأقل للتطبيق، واتصال بالإنترنت لتحميل الأدوات الإضافية. يُفضل أن يكون الجهاز بـ 2GB RAM أو أكثر.",
    },
    {
      q: "هل يمكنني استخدام التطبيق لاختبار اختراق المواقع؟",
      a: "نعم، يحتوي التطبيق على منصة فحص ويب بـ 5 مراحل متكاملة. لكن يجب استخدام التطبيق فقط على المواقع التي تملك صلاحية اختبارها، أو على مواقع الاختبار الآمنة مثل testphp.vulnweb.com.",
    },
    {
      q: "كيف أثبّت أدوات إضافية حقيقية؟",
      a: "من شاشة 'تثبيت أدوات' يمكنك تثبيت BusyBox (يعطيك 200+ أمر Linux) و20+ أداة حقيقية مثل nmap, sqlmap, hydra مباشرة من GitHub بضغطة زر.",
    },
    {
      q: "هل GitHub Token آمن في التطبيق؟",
      a: "نعم، التطبيق يشفر الـ Token بـ AES-256-GCM ويخزنه محلياً على جهازك فقط. لا يتم إرساله لأي خادم خارجي.",
    },
    {
      q: "هل مساعد الذكاء الاصطناعي يحتاج مفتاح API؟",
      a: "لا، يعمل المساعد بدون مفتاح API بردود ذكية محلية احترافية. وإذا أردت ردود أكثر تقدماً، يمكنك إدخال مفتاح OpenAI API.",
    },
    {
      q: "ما الفرق بين تيرمكس العرب وتطبيق Termux الأصلي؟",
      a: "تيرمكس العرب يقدم واجهة عربية كاملة RTL، 17 شاشة احترافية جاهزة، أكثر من 200 أداة منظمة في فئات، ومكتشف ثغرات وقاعدة بيانات ثغرات. كل ذلك بواجهة سهلة الاستخدام بدلاً من سطر الأوامر فقط.",
    },
    {
      q: "كيف أبدأ باستخدام التطبيق؟",
      a: "بعد تحميل التطبيق، افتحه وستظهر لك الشاشة الرئيسية بكل الأزرار. ابدأ بتجربة الطرفية، ثم ثبّت BusyBox من شاشة 'تثبيت أدوات'، واستخدم 'help' في الطرفية لعرض كل الأوامر المتاحة. يمكنك أيضاً تحميل الدليل الشامل PDF من هذه الصفحة.",
    },
  ];

  return (
    <div className="min-h-screen flex flex-col bg-background">
      {/* ===== Header / Navbar ===== */}
      <header
        className={`sticky top-0 z-50 w-full transition-all ${
          scrolled
            ? "bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/80 shadow-md"
            : "bg-transparent"
        }`}
      >
        <nav className="container mx-auto px-4 lg:px-6 h-16 flex items-center justify-between">
          {/* Logo */}
          <a href="#" className="flex items-center gap-2">
            <div className="w-10 h-10 rounded-lg bg-brand-gradient flex items-center justify-center text-white">
              <Terminal className="w-6 h-6" />
            </div>
            <div className="flex flex-col">
              <span className="font-bold text-lg leading-tight">تيرمكس العرب</span>
              <span className="text-xs text-muted-foreground">v1.7.3</span>
            </div>
          </a>

          {/* Desktop Nav */}
          <div className="hidden md:flex items-center gap-1">
            {navLinks.map((link) => (
              <a
                key={link.href}
                href={link.href}
                className="px-3 py-2 rounded-md text-sm font-medium hover:bg-accent hover:text-accent-foreground transition-colors"
              >
                {link.label}
              </a>
            ))}
          </div>

          {/* CTA Buttons */}
          <div className="hidden md:flex items-center gap-2">
            <a href="/termux-arab/downloads/termux-arab-guide.pdf" target="_blank" rel="noopener">
              <Button variant="outline" size="sm">
                <FileText className="w-4 h-4 ml-1" />
                الدليل
              </Button>
            </a>
            <a href="#download">
              <Button size="sm" className="bg-brand-gradient">
                <Download className="w-4 h-4 ml-1" />
                تحميل التطبيق
              </Button>
            </a>
          </div>

          {/* Mobile menu button */}
          <button
            className="md:hidden p-2"
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            aria-label="القائمة"
          >
            {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
          </button>
        </nav>

        {/* Mobile Nav */}
        {mobileMenuOpen && (
          <div className="md:hidden border-t bg-background">
            <div className="container mx-auto px-4 py-3 flex flex-col gap-1">
              {navLinks.map((link) => (
                <a
                  key={link.href}
                  href={link.href}
                  onClick={() => setMobileMenuOpen(false)}
                  className="px-3 py-2 rounded-md text-sm font-medium hover:bg-accent"
                >
                  {link.label}
                </a>
              ))}
              <div className="flex gap-2 mt-2">
                <a href="/termux-arab/downloads/termux-arab-guide.pdf" target="_blank" rel="noopener" className="flex-1">
                  <Button variant="outline" size="sm" className="w-full">
                    <FileText className="w-4 h-4 ml-1" />
                    الدليل
                  </Button>
                </a>
                <a href="#download" className="flex-1" onClick={() => setMobileMenuOpen(false)}>
                  <Button size="sm" className="w-full bg-brand-gradient">
                    <Download className="w-4 h-4 ml-1" />
                    تحميل
                  </Button>
                </a>
              </div>
            </div>
          </div>
        )}
      </header>

      {/* ===== Hero Section ===== */}
      <section className="relative overflow-hidden bg-gradient-to-br from-emerald-950 via-emerald-900 to-green-950 text-white">
        <div className="absolute inset-0 opacity-20">
          <div className="absolute top-20 right-10 w-72 h-72 bg-amber-500 rounded-full blur-3xl"></div>
          <div className="absolute bottom-20 left-10 w-96 h-96 bg-emerald-400 rounded-full blur-3xl"></div>
        </div>

        <div className="container mx-auto px-4 lg:px-6 py-20 lg:py-32 relative z-10">
          <div className="max-w-4xl mx-auto text-center">
            <Badge className="mb-6 bg-amber-500/20 text-amber-300 border-amber-500/30 hover:bg-amber-500/30">
              <Sparkles className="w-3 h-3 ml-1" />
              الإصدار 1.7.3 - 17 شاشة احترافية
            </Badge>

            <h1 className="text-4xl md:text-6xl lg:text-7xl font-extrabold mb-6 leading-tight">
              تيرمكس العرب
            </h1>
            <p className="text-xl md:text-2xl text-emerald-200 mb-4 font-medium">
              تطبيق طرفية احترافي بواجهة عربية كاملة لأندرويد
            </p>
            <p className="text-base md:text-lg text-emerald-300/80 mb-10 max-w-3xl mx-auto leading-relaxed">
              200+ أداة من كالي لينكس • 100+ أمر Linux حقيقي • اختبار اختراق • ذكاء اصطناعي •
              تكامل GitHub — كل ذلك بدون Root على جهازك
            </p>

            {/* CTA Buttons */}
            <div className="flex flex-col sm:flex-row gap-4 justify-center mb-12">
              <a href="#download">
                <Button size="lg" className="bg-amber-500 hover:bg-amber-600 text-emerald-950 font-bold text-lg px-8 h-14 animate-pulse-glow">
                  <Download className="w-5 h-5 ml-2" />
                  تحميل التطبيق مجاناً
                </Button>
              </a>
              <a href="/termux-arab/downloads/termux-arab-guide.pdf" target="_blank" rel="noopener">
                <Button
                  size="lg"
                  variant="outline"
                  className="bg-white/10 border-white/30 text-white hover:bg-white/20 backdrop-blur text-lg px-8 h-14"
                >
                  <FileText className="w-5 h-5 ml-2" />
                  الدليل الشامل PDF
                </Button>
              </a>
            </div>

            {/* Stats */}
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4 max-w-4xl mx-auto">
              {stats.map((stat, i) => (
                <div
                  key={i}
                  className="bg-white/10 backdrop-blur rounded-xl p-4 border border-white/10"
                >
                  <stat.icon className="w-6 h-6 mx-auto mb-2 text-amber-300" />
                  <div className="text-2xl md:text-3xl font-bold text-white">{stat.value}</div>
                  <div className="text-xs text-emerald-200">{stat.label}</div>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Wave divider */}
        <div className="absolute bottom-0 left-0 right-0">
          <svg viewBox="0 0 1440 80" className="w-full h-auto" preserveAspectRatio="none">
            <path
              fill="oklch(0.99 0.005 145)"
              d="M0,32L80,37.3C160,43,320,53,480,53.3C640,53,800,43,960,42.7C1120,43,1280,53,1360,58.7L1440,64L1440,80L0,80Z"
            />
          </svg>
        </div>
      </section>

      {/* ===== Top Banner Ad ===== */}
      <section className="container mx-auto px-4 lg:px-6 py-6">
        <AdComponent slot="1111111111" label="إعلان" className="max-w-5xl mx-auto" />
      </section>

      {/* ===== About Section ===== */}
      <section id="about" className="py-16 lg:py-24 bg-background">
        <div className="container mx-auto px-4 lg:px-6">
          <div className="max-w-4xl mx-auto text-center mb-12">
            <Badge variant="outline" className="mb-4">
              <BookOpen className="w-3 h-3 ml-1" />
              عن التطبيق
            </Badge>
            <h2 className="text-3xl md:text-4xl lg:text-5xl font-bold mb-6 text-foreground">
              بيئة اختبار اختراق وتطوير كاملة في جيبك
            </h2>
            <p className="text-lg text-muted-foreground leading-relaxed">
              تيرمكس العرب هو تطبيق طرفية احترافي بواجهة عربية كاملة، مصمم ليعمل على أجهزة أندرويد
              بدون صلاحيات Root. التطبيق مصمم خصيصاً للمستخدمين العرب الذين يحتاجون إلى بيئة اختبار
              اختراق وتطوير على هواتفهم. كل الأوامر والواجهة بالعربية مع دعم كامل للكتابة من اليمين
              لليسار (RTL).
            </p>
          </div>

          {/* Quick highlights */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 max-w-5xl mx-auto">
            {[
              {
                icon: Smartphone,
                title: "يعمل على أي جهاز أندرويد",
                desc: "أندرويد 7.0 أو أحدث — بدون Root، بدون متطلبات معقدة",
              },
              {
                icon: Award,
                title: "مجاني بالكامل",
                desc: "تطبيق مجاني تحت رخصة GPLv3 — بدون إعلانات داخل التطبيق",
              },
              {
                icon: Users,
                title: "مصمم للعرب",
                desc: "واجهة عربية كاملة بدعم RTL وخطوط عربية احترافية",
              },
            ].map((item, i) => (
              <Card key={i} className="p-6 text-center hover:shadow-lg transition-shadow">
                <div className="w-14 h-14 mx-auto mb-4 rounded-2xl bg-brand-gradient flex items-center justify-center text-white">
                  <item.icon className="w-7 h-7" />
                </div>
                <h3 className="font-bold text-lg mb-2">{item.title}</h3>
                <p className="text-sm text-muted-foreground">{item.desc}</p>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* ===== Features Section ===== */}
      <section id="features" className="py-16 lg:py-24 bg-secondary/30">
        <div className="container mx-auto px-4 lg:px-6">
          <div className="max-w-4xl mx-auto text-center mb-12">
            <Badge variant="outline" className="mb-4">
              <Zap className="w-3 h-3 ml-1" />
              المميزات الرئيسية
            </Badge>
            <h2 className="text-3xl md:text-4xl lg:text-5xl font-bold mb-6">
              12 ميزة احترافية متكاملة
            </h2>
            <p className="text-lg text-muted-foreground">
              كل ما تحتاجه لاختبار الاختراق والتطوير على هاتفك في تطبيق واحد
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {features.map((feature, i) => (
              <Card
                key={i}
                className="p-6 hover:shadow-xl transition-all hover:-translate-y-1 group"
              >
                <div
                  className={`w-12 h-12 rounded-xl bg-gradient-to-br ${feature.color} flex items-center justify-center text-white mb-4 group-hover:scale-110 transition-transform`}
                >
                  <feature.icon className="w-6 h-6" />
                </div>
                <h3 className="font-bold text-lg mb-2">{feature.title}</h3>
                <p className="text-sm text-muted-foreground leading-relaxed">{feature.desc}</p>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* In-content Ad */}
      <section className="container mx-auto px-4 lg:px-6 py-6">
        <AdComponent slot="2222222222" label="إعلان" className="max-w-5xl mx-auto" />
      </section>

      {/* ===== Screens Section ===== */}
      <section id="screens" className="py-16 lg:py-24 bg-background">
        <div className="container mx-auto px-4 lg:px-6">
          <div className="max-w-4xl mx-auto text-center mb-12">
            <Badge variant="outline" className="mb-4">
              <Smartphone className="w-3 h-3 ml-1" />
              الشاشات
            </Badge>
            <h2 className="text-3xl md:text-4xl lg:text-5xl font-bold mb-6">
              17 شاشة احترافية متكاملة
            </h2>
            <p className="text-lg text-muted-foreground">
              كل شاشة مصممة بعناية لوظيفة محددة
            </p>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 max-w-6xl mx-auto">
            {screens.map((screen, i) => (
              <Card
                key={i}
                className="p-5 hover:shadow-md transition-all flex items-start gap-4"
              >
                <div className="w-10 h-10 rounded-lg bg-primary/10 flex items-center justify-center text-primary flex-shrink-0">
                  <screen.icon className="w-5 h-5" />
                </div>
                <div className="flex-1">
                  <div className="flex items-center gap-2 mb-1">
                    <span className="text-xs font-mono text-muted-foreground">
                      {String(i + 1).padStart(2, "0")}
                    </span>
                    <h3 className="font-bold text-sm">{screen.name}</h3>
                  </div>
                  <p className="text-xs text-muted-foreground leading-relaxed">{screen.desc}</p>
                </div>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* ===== Tools Categories Section ===== */}
      <section id="tools" className="py-16 lg:py-24 bg-secondary/30">
        <div className="container mx-auto px-4 lg:px-6">
          <div className="max-w-4xl mx-auto text-center mb-12">
            <Badge variant="outline" className="mb-4">
              <Package className="w-3 h-3 ml-1" />
              فئات الأدوات
            </Badge>
            <h2 className="text-3xl md:text-4xl lg:text-5xl font-bold mb-6">
              8 فئات بأكثر من 50 أداة
            </h2>
            <p className="text-lg text-muted-foreground">
              كل ما تحتاجه من أدوات الاختراق والتطوير
            </p>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 max-w-6xl mx-auto">
            {toolCategories.map((cat, i) => (
              <Card key={i} className="p-5 hover:shadow-md transition-all">
                <div className="flex items-center justify-between mb-3">
                  <div className={`w-10 h-10 rounded-lg ${cat.color} flex items-center justify-center text-white`}>
                    <cat.icon className="w-5 h-5" />
                  </div>
                  <Badge variant="secondary">{cat.count} أدوات</Badge>
                </div>
                <h3 className="font-bold text-base mb-2">{cat.name}</h3>
                <p className="text-xs text-muted-foreground font-mono leading-relaxed">{cat.tools}</p>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* ===== Kali Linux Section ===== */}
      <section id="kali" className="py-16 lg:py-24 bg-background relative overflow-hidden">
        <div className="absolute inset-0 opacity-5">
          <div className="absolute top-0 left-0 w-full h-full bg-gradient-to-br from-red-900 to-black"></div>
        </div>
        <div className="container mx-auto px-4 lg:px-6 relative z-10">
          <div className="max-w-4xl mx-auto text-center mb-12">
            <Badge className="mb-4 bg-red-500/10 text-red-600 border-red-500/20">
              <Skull className="w-3 h-3 ml-1" />
              كالي لينكس
            </Badge>
            <h2 className="text-3xl md:text-4xl lg:text-5xl font-bold mb-6">
              200+ أداة من كالي لينكس
            </h2>
            <p className="text-lg text-muted-foreground">
              منظومة أدوات كاملة من توزيعة كالي لينكس الشهيرة في 12 فئة
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 max-w-6xl mx-auto">
            {kaliCategories.map((cat, i) => (
              <Card key={i} className="p-5 hover:shadow-md transition-all border-r-4 border-r-red-500">
                <div className="flex items-center justify-between mb-2">
                  <h3 className="font-bold text-base">{cat.name}</h3>
                  <Badge variant="outline" className="font-mono">{cat.count}</Badge>
                </div>
                <p className="text-xs text-muted-foreground font-mono leading-relaxed">{cat.tools}</p>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Middle Ad */}
      <section className="container mx-auto px-4 lg:px-6 py-6">
        <AdComponent slot="3333333333" label="إعلان" className="max-w-5xl mx-auto" />
      </section>

      {/* ===== Terminal Demo Section ===== */}
      <section className="py-16 lg:py-24 bg-emerald-950 text-white">
        <div className="container mx-auto px-4 lg:px-6">
          <div className="max-w-4xl mx-auto">
            <div className="text-center mb-10">
              <Badge className="mb-4 bg-amber-500/20 text-amber-300 border-amber-500/30">
                <Terminal className="w-3 h-3 ml-1" />
                الطرفية التفاعلية
              </Badge>
              <h2 className="text-3xl md:text-4xl font-bold mb-4">
                طرفية حقيقية بنتائج فعلية
              </h2>
              <p className="text-lg text-emerald-200">
                تعمل بـ /system/bin/sh وتدعم 100+ أمر Linux
              </p>
            </div>

            {/* Terminal Mockup */}
            <div className="bg-black/80 rounded-xl overflow-hidden shadow-2xl border border-emerald-500/30">
              <div className="bg-emerald-900/50 px-4 py-2 flex items-center gap-2 border-b border-emerald-500/30">
                <div className="flex gap-1.5">
                  <div className="w-3 h-3 rounded-full bg-red-500"></div>
                  <div className="w-3 h-3 rounded-full bg-amber-500"></div>
                  <div className="w-3 h-3 rounded-full bg-green-500"></div>
                </div>
                <span className="text-xs text-emerald-300 mr-auto terminal-text">termux-arab: ~</span>
              </div>
              <div className="p-4 terminal-text text-sm space-y-2 max-h-80 overflow-y-auto">
                <div><span className="text-emerald-400">$</span> <span className="text-white">help</span></div>
                <div className="text-emerald-300">الأوامر المتاحة:</div>
                <div className="text-emerald-300">ls, cd, pwd, cat, echo, grep, find</div>
                <div className="text-emerald-300">ps, top, df, free, date, whoami</div>
                <div className="text-emerald-300">ping, curl, clear, exit</div>
                <div className="mt-3"><span className="text-emerald-400">$</span> <span className="text-white">whoami</span></div>
                <div className="text-amber-300">root</div>
                <div className="mt-3"><span className="text-emerald-400">$</span> <span className="text-white">ls</span></div>
                <div className="text-cyan-300">home  usr  bin  tmp  etc  var</div>
                <div className="mt-3"><span className="text-emerald-400">$</span> <span className="text-white">echo 'مرحبا بالعالم'</span></div>
                <div className="text-white">مرحبا بالعالم</div>
                <div className="mt-3"><span className="text-emerald-400">$</span> <span className="text-white">df -h</span></div>
                <div className="text-emerald-300">Filesystem    Size  Used  Avail  Use%</div>
                <div className="text-emerald-300">/dev/sda1     128G   64G   64G    50%</div>
                <div className="mt-3"><span className="text-emerald-400">$</span> <span className="text-emerald-300 animate-pulse">_</span></div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* ===== Download Section ===== */}
      <section id="download" className="py-16 lg:py-24 bg-background">
        <div className="container mx-auto px-4 lg:px-6">
          <div className="max-w-5xl mx-auto">
            <div className="text-center mb-12">
              <Badge variant="outline" className="mb-4">
                <Download className="w-3 h-3 ml-1" />
                تحميل
              </Badge>
              <h2 className="text-3xl md:text-4xl lg:text-5xl font-bold mb-6">
                حمّل التطبيق والدليل مجاناً
              </h2>
              <p className="text-lg text-muted-foreground">
                التطبيق مجاني بالكامل — بدون اشتراكات أو إعلانات داخلية
              </p>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {/* App Download */}
              <Card className="p-8 text-center bg-brand-gradient text-white border-0">
                <div className="w-20 h-20 mx-auto mb-6 rounded-2xl bg-white/20 backdrop-blur flex items-center justify-center animate-float">
                  <Smartphone className="w-10 h-10" />
                </div>
                <h3 className="text-2xl font-bold mb-2">تطبيق تيرمكس العرب</h3>
                <p className="text-emerald-100 mb-1">الإصدار 1.7.3</p>
                <p className="text-sm text-emerald-200 mb-6">حجم الملف: ~120 ميجابايت (APK Universal)</p>

                <div className="space-y-2 mb-6 text-sm text-right">
                  <div className="flex items-center gap-2 justify-end">
                    <span>أندرويد 7.0 أو أحدث</span>
                    <CheckCircle2 className="w-4 h-4 text-amber-300" />
                  </div>
                  <div className="flex items-center gap-2 justify-end">
                    <span>بدون Root</span>
                    <CheckCircle2 className="w-4 h-4 text-amber-300" />
                  </div>
                  <div className="flex items-center gap-2 justify-end">
                    <span>مجاني بالكامل</span>
                    <CheckCircle2 className="w-4 h-4 text-amber-300" />
                  </div>
                  <div className="flex items-center gap-2 justify-end">
                    <span>يدعم جميع المعالجات (Universal)</span>
                    <CheckCircle2 className="w-4 h-4 text-amber-300" />
                  </div>
                </div>

                <a href="/termux-arab/downloads/install-termux-arab.sh" download>
                  <Button size="lg" className="w-full bg-amber-500 hover:bg-amber-600 text-emerald-950 font-bold h-14 text-lg">
                    <Download className="w-5 h-5 ml-2" />
                    تحميل سكريبت التثبيت
                  </Button>
                </a>
                <a href="/termux-arab/downloads/termux-arab-v1.7.3.apk.part0" download>
                  <Button size="sm" variant="outline" className="w-full mt-2 bg-white/10 border-white/30 text-white hover:bg-white/20">
                    <Download className="w-4 h-4 ml-2" />
                    تحميل يدوي: الجزء 1 (95MB)
                  </Button>
                </a>
                <a href="/termux-arab/downloads/termux-arab-v1.7.3.apk.part1" download>
                  <Button size="sm" variant="outline" className="w-full mt-2 bg-white/10 border-white/30 text-white hover:bg-white/20">
                    <Download className="w-4 h-4 ml-2" />
                    تحميل يدوي: الجزء 2 (25MB)
                  </Button>
                </a>
                <p className="text-xs text-emerald-200 mt-3">
                  الطريقة الأسهل: حمّل سكريبت التثبيت وشغّله على Linux/Mac.<br/>
                  أو حمّل الجزئين يدوياً وادمجهما: <code className="bg-black/30 px-1 rounded terminal-text">cat part0 part1 &gt; app.apk</code>
                </p>
              </Card>

              {/* PDF Guide Download */}
              <Card className="p-8 text-center bg-gradient-to-br from-amber-50 to-orange-50 border-2 border-amber-200">
                <div className="w-20 h-20 mx-auto mb-6 rounded-2xl bg-amber-500 flex items-center justify-center animate-float" style={{ animationDelay: "0.5s" }}>
                  <FileText className="w-10 h-10 text-white" />
                </div>
                <h3 className="text-2xl font-bold mb-2 text-foreground">الدليل الشامل PDF</h3>
                <p className="text-muted-foreground mb-1">21 صفحة شرح كامل</p>
                <p className="text-sm text-muted-foreground mb-6">حجم الملف: ~70 كيلوبايت</p>

                <div className="space-y-2 mb-6 text-sm text-right">
                  <div className="flex items-center gap-2 justify-end">
                    <span>شرح كل ميزة بالتفصيل</span>
                    <CheckCircle2 className="w-4 h-4 text-emerald-600" />
                  </div>
                  <div className="flex items-center gap-2 justify-end">
                    <span>18 قسماً شاملاً</span>
                    <CheckCircle2 className="w-4 h-4 text-emerald-600" />
                  </div>
                  <div className="flex items-center gap-2 justify-end">
                    <span>أمثلة عملية وصور</span>
                    <CheckCircle2 className="w-4 h-4 text-emerald-600" />
                  </div>
                </div>

                <a href="/termux-arab/downloads/termux-arab-guide.pdf" target="_blank" rel="noopener">
                  <Button size="lg" variant="outline" className="w-full border-amber-500 text-amber-700 hover:bg-amber-500 hover:text-white font-bold h-14 text-lg">
                    <FileText className="w-5 h-5 ml-2" />
                    تحميل الدليل PDF
                  </Button>
                </a>
                <p className="text-xs text-muted-foreground mt-3">
                  ملف PDF عربي بدعم RTL كامل
                </p>
              </Card>
            </div>

            {/* Installation steps */}
            <div className="mt-12">
              <h3 className="text-2xl font-bold mb-6 text-center">طريقة التثبيت</h3>
              <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                {[
                  { step: 1, title: "حمّل الملف", desc: "حمّل سكريبت التثبيت أو الجزئين" },
                  { step: 2, title: "اجمع الملف", desc: "شغّل السكريبت أو ادمج الجزئين في APK" },
                  { step: 3, title: "ثبّت التطبيق", desc: "فعّل 'تثبيت من مصادر غير معروفة' وثبّت" },
                ].map((s) => (
                  <Card key={s.step} className="p-6 text-center">
                    <div className="w-12 h-12 mx-auto mb-4 rounded-full bg-brand-gradient flex items-center justify-center text-white font-bold text-lg">
                      {s.step}
                    </div>
                    <h4 className="font-bold mb-2">{s.title}</h4>
                    <p className="text-sm text-muted-foreground">{s.desc}</p>
                  </Card>
                ))}
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Bottom Ad */}
      <section className="container mx-auto px-4 lg:px-6 py-6">
        <AdComponent slot="4444444444" label="إعلان" className="max-w-5xl mx-auto" />
      </section>

      {/* ===== FAQ Section ===== */}
      <section id="faq" className="py-16 lg:py-24 bg-secondary/30">
        <div className="container mx-auto px-4 lg:px-6">
          <div className="max-w-3xl mx-auto">
            <div className="text-center mb-12">
              <Badge variant="outline" className="mb-4">
                <AlertTriangle className="w-3 h-3 ml-1" />
                الأسئلة الشائعة
              </Badge>
              <h2 className="text-3xl md:text-4xl lg:text-5xl font-bold mb-6">
                أسئلة يطرحها المستخدمون
              </h2>
              <p className="text-lg text-muted-foreground">
                كل ما تحتاج معرفته عن تيرمكس العرب
              </p>
            </div>

            <Accordion type="single" collapsible className="w-full">
              {faqs.map((faq, i) => (
                <AccordionItem key={i} value={`item-${i}`}>
                  <AccordionTrigger className="text-right text-base md:text-lg font-semibold">
                    {faq.q}
                  </AccordionTrigger>
                  <AccordionContent className="text-base text-muted-foreground leading-relaxed">
                    {faq.a}
                  </AccordionContent>
                </AccordionItem>
              ))}
            </Accordion>
          </div>
        </div>
      </section>

      {/* ===== System Monitor Preview Section ===== */}
      <section className="py-16 lg:py-24 bg-background">
        <div className="container mx-auto px-4 lg:px-6">
          <div className="max-w-5xl mx-auto">
            <div className="text-center mb-12">
              <Badge variant="outline" className="mb-4">
                <Activity className="w-3 h-3 ml-1" />
                مراقب النظام
              </Badge>
              <h2 className="text-3xl md:text-4xl lg:text-5xl font-bold mb-6">
                مراقبة جهازك في الوقت الفعلي
              </h2>
              <p className="text-lg text-muted-foreground">
                قراءات حقيقية من نظام التشغيل تتحدث كل ثانيتين
              </p>
            </div>

            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
              {[
                { icon: Cpu, label: "المعالج", value: "32%", color: "text-emerald-600" },
                { icon: HardDrive, label: "الذاكرة", value: "4.2/8 GB", color: "text-blue-600" },
                { icon: Battery, label: "البطارية", value: "87%", color: "text-amber-600" },
                { icon: Network, label: "الشبكة", value: "192.168.1.5", color: "text-purple-600" },
              ].map((item, i) => (
                <Card key={i} className="p-6 text-center">
                  <item.icon className={`w-8 h-8 mx-auto mb-3 ${item.color}`} />
                  <div className="text-xs text-muted-foreground mb-1">{item.label}</div>
                  <div className="text-lg font-bold">{item.value}</div>
                </Card>
              ))}
            </div>
          </div>
        </div>
      </section>

      {/* ===== Final CTA Section ===== */}
      <section className="py-16 lg:py-24 bg-gradient-to-br from-emerald-900 to-green-900 text-white">
        <div className="container mx-auto px-4 lg:px-6">
          <div className="max-w-3xl mx-auto text-center">
            <Sparkles className="w-12 h-12 mx-auto mb-6 text-amber-300" />
            <h2 className="text-3xl md:text-4xl lg:text-5xl font-bold mb-6">
              جاهز للبدء؟
            </h2>
            <p className="text-lg text-emerald-100 mb-8">
              حمّل تيرمكس العرب الآن واحصل على بيئة اختبار اختراق وتطوير كاملة على هاتفك
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <a href="#download">
                <Button size="lg" className="bg-amber-500 hover:bg-amber-600 text-emerald-950 font-bold text-lg px-8 h-14">
                  <Download className="w-5 h-5 ml-2" />
                  تحميل التطبيق
                </Button>
              </a>
              <a href="/termux-arab/downloads/termux-arab-guide.pdf" target="_blank" rel="noopener">
                <Button
                  size="lg"
                  variant="outline"
                  className="bg-white/10 border-white/30 text-white hover:bg-white/20 text-lg px-8 h-14"
                >
                  <FileText className="w-5 h-5 ml-2" />
                  الدليل الشامل
                </Button>
              </a>
            </div>
          </div>
        </div>
      </section>

      {/* ===== Footer ===== */}
      <footer className="bg-emerald-950 text-emerald-100 py-12 mt-auto">
        <div className="container mx-auto px-4 lg:px-6">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-8 mb-8">
            {/* Brand */}
            <div className="md:col-span-2">
              <div className="flex items-center gap-2 mb-4">
                <div className="w-10 h-10 rounded-lg bg-amber-500 flex items-center justify-center text-emerald-950">
                  <Terminal className="w-6 h-6" />
                </div>
                <div>
                  <div className="font-bold text-lg">تيرمكس العرب</div>
                  <div className="text-xs text-emerald-300">v1.7.3 — GPLv3</div>
                </div>
              </div>
              <p className="text-sm text-emerald-200 leading-relaxed mb-4">
                تطبيق طرفية احترافي بواجهة عربية كاملة لأندرويد. 17 شاشة احترافية، 200+ أداة كالي
                لينكس، 100+ أمر Linux حقيقي — بدون Root.
              </p>
            </div>

            {/* Links */}
            <div>
              <h4 className="font-bold mb-3 text-amber-300">روابط سريعة</h4>
              <ul className="space-y-2 text-sm">
                <li><a href="#features" className="hover:text-amber-300 transition-colors">المميزات</a></li>
                <li><a href="#screens" className="hover:text-amber-300 transition-colors">الشاشات</a></li>
                <li><a href="#tools" className="hover:text-amber-300 transition-colors">الأدوات</a></li>
                <li><a href="#kali" className="hover:text-amber-300 transition-colors">كالي لينكس</a></li>
                <li><a href="#faq" className="hover:text-amber-300 transition-colors">الأسئلة الشائعة</a></li>
              </ul>
            </div>

            {/* Downloads */}
            <div>
              <h4 className="font-bold mb-3 text-amber-300">التحميلات</h4>
              <ul className="space-y-2 text-sm">
                <li>
                  <a href="/termux-arab/downloads/termux-arab-v1.7.3.apk" className="hover:text-amber-300 transition-colors flex items-center gap-2">
                    <Download className="w-4 h-4" />
                    تطبيق APK
                  </a>
                </li>
                <li>
                  <a href="/termux-arab/downloads/termux-arab-guide.pdf" className="hover:text-amber-300 transition-colors flex items-center gap-2">
                    <FileText className="w-4 h-4" />
                    الدليل PDF
                  </a>
                </li>
              </ul>
            </div>
          </div>

          <div className="border-t border-emerald-800 pt-6 flex flex-col md:flex-row items-center justify-between gap-4 text-sm text-emerald-300">
            <p>© 2026 تيرمكس العرب — جميع الحقوق محفوظة</p>
            <p className="flex items-center gap-2">
              <span>صنع بحب للمستخدمين العرب</span>
              <span className="text-amber-300">♥</span>
            </p>
          </div>
        </div>
      </footer>
    </div>
  );
}
