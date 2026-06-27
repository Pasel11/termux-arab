#!/usr/bin/env bash
# =====================================================================
#  تيرمكس العرب - سكريبت تحميل وتجميع التطبيق
#  Termux Arab - APK Downloader & Assembler
# =====================================================================
#  يستخدم هذا السكريبت لتحميل أجزاء التطبيق المفرقة تلقائياً
#  وتجميعها في ملف APK واحد قابل للتثبيت.
#
#  الاستخدام:
#    bash install-termux-arab.sh
#
#  المتطلبات: bash, curl (أو wget)
# =====================================================================

set -e

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${GREEN}"
echo "============================================================"
echo "    تيرمكس العرب - APK Downloader & Assembler"
echo "    تحميل وتجميع تطبيق تيرمكس العرب"
echo "============================================================"
echo -e "${NC}"

# Configuration
# Replace USER and REPO with your actual GitHub username and repo name
GITHUB_USER="termux-arab"
GITHUB_REPO="termux-arab"
BRANCH="main"

BASE_URL="https://raw.githubusercontent.com/${GITHUB_USER}/${GITHUB_REPO}/${BRANCH}/public/downloads"
PARTS=("termux-arab-v1.7.3.apk.part0" "termux-arab-v1.7.3.apk.part1")
OUTPUT_FILE="termux-arab-v1.7.3.apk"

# Detect download tool
if command -v curl >/dev/null 2>&1; then
    DL_CMD="curl -L -o"
elif command -v wget >/dev/null 2>&1; then
    DL_CMD="wget -q -O"
else
    echo -e "${RED}خطأ: لم يتم العثور على curl أو wget. الرجاء تثبيت أحدهما.${NC}"
    echo -e "${RED}Error: Neither curl nor wget found. Please install one.${NC}"
    exit 1
fi

# Create temp directory
TMP_DIR=$(mktemp -d)
trap 'rm -rf "$TMP_DIR"' EXIT

echo -e "${BLUE}[*] تحميل أجزاء التطبيق...${NC}"
echo -e "${BLUE}[*] Downloading APK parts...${NC}"
echo ""

i=1
total=${#PARTS[@]}
for part in "${PARTS[@]}"; do
    echo -e "${YELLOW}[$i/$total] تحميل $part ...${NC}"
    echo -e "${YELLOW}[$i/$total] Downloading $part ...${NC}"
    if ! $DL_CMD "$TMP_DIR/$part" "$BASE_URL/$part"; then
        echo ""
        echo -e "${RED}خطأ: فشل تحميل $part${NC}"
        echo -e "${RED}Error: Failed to download $part${NC}"
        echo ""
        echo -e "${YELLOW}السبب المحتمل:${NC}"
        echo -e "  - لم يتم تحديث GITHUB_USER و GITHUB_REPO في السكريبت"
        echo -e "  - The script's GITHUB_USER and GITHUB_REPO not updated"
        echo -e "  - الجواب: افتح الملف وعدّل السطرين في الأعلى"
        echo -e "  - Fix: open this file and edit the two lines at top"
        exit 1
    fi
    i=$((i + 1))
done

echo ""
echo -e "${BLUE}[*] تجميع الأجزاء في ملف APK واحد...${NC}"
echo -e "${BLUE}[*] Assembling parts into a single APK file...${NC}"

cat "$TMP_DIR/${PARTS[0]}" "$TMP_DIR/${PARTS[1]}" > "$OUTPUT_FILE"

# Verify file size
FILE_SIZE=$(stat -c%s "$OUTPUT_FILE" 2>/dev/null || stat -f%z "$OUTPUT_FILE" 2>/dev/null || echo "0")
FILE_SIZE_MB=$((FILE_SIZE / 1024 / 1024))

echo ""
echo -e "${GREEN}============================================================${NC}"
echo -e "${GREEN}  ✓ تم بنجاح!${NC}"
echo -e "${GREEN}  ✓ Success!${NC}"
echo -e "${GREEN}============================================================${NC}"
echo ""
echo -e "${BLUE}الملف الناتج:${NC} $OUTPUT_FILE (${FILE_SIZE_MB} MB)"
echo -e "${BLUE}Output file:${NC} $OUTPUT_FILE (${FILE_SIZE_MB} MB)"
echo ""
echo -e "${YELLOW}الخطوات التالية:${NC}"
echo -e "${YELLOW}Next steps:${NC}"
echo ""
echo -e "  1. انقل الملف إلى هاتف أندرويد الخاص بك"
echo -e "     Transfer the APK to your Android phone"
echo ""
echo -e "  2. فعّل 'تثبيت من مصادر غير معروفة' في إعدادات الأمان"
echo -e "     Enable 'Install from unknown sources' in security settings"
echo ""
echo -e "  3. اضغط على الملف لتثبيت التطبيق"
echo -e "     Tap the file to install the app"
echo ""
echo -e "  4. لأي مشاكل، تحقق من الدليل الشامل PDF:"
echo -e "     For any issues, check the comprehensive PDF guide:"
echo -e "     ${BASE_URL}/termux-arab-guide.pdf"
echo ""
