#!/usr/bin/env bash
# =====================================================================
#  سكريبت إنشاء منظمة GitHub ونقل المستودع إليها
#  Create GitHub Organization and Transfer Repository
# =====================================================================
#  GitHub لا يسمح بإنشاء المنظمات عبر API لحسابات الخطة المجانية،
#  لذا يجب إنشاء المنظمة يدوياً ثم نقل المستودع إليها.
#
#  الاستخدام:
#    bash scripts/create-org-and-transfer.sh ORG_NAME
#
#  مثال:
#    bash scripts/create-org-and-transfer.sh termux-arab
# =====================================================================

set -e

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

ORG_NAME="${1:-termux-arab}"
REPO="Pasel11/termux-arab"

echo -e "${GREEN}"
echo "============================================================"
echo "  إنشاء منظمة GitHub ونقل المستودع"
echo "  Create GitHub Org & Transfer Repository"
echo "============================================================"
echo -e "${NC}"

echo -e "${YELLOW}الخطوة 1: إنشاء المنظمة على GitHub${NC}"
echo -e "${BLUE}GitHub لا يسمح بإنشاء المنظمات عبر API، يجب إنشاؤها يدوياً.${NC}"
echo ""
echo -e "${YELLOW}افتح هذا الرابط في المتصفح:${NC}"
echo -e "${GREEN}  https://github.com/organizations/new${NC}"
echo ""
echo -e "${BLUE}املأ البيانات:${NC}"
echo "  - Organization name: ${ORG_NAME}"
echo "  - Contact email: بريدك الإلكتروني"
echo "  - This organization belongs to: Personal account"
echo "  - Plan: Free"
echo ""
read -p "هل أنشأت المنظمة بالاسم '${ORG_NAME}'؟ (y/N): " CREATED
if [[ ! "$CREATED" =~ ^[Yy]$ ]]; then
    echo -e "${RED}الرجاء إنشاء المنظمة أولاً ثم إعادة تشغيل هذا السكريبت.${NC}"
    exit 1
fi

echo ""
echo -e "${YELLOW}الخطوة 2: نقل المستودع إلى المنظمة${NC}"
GH_TOKEN="${GH_TOKEN:-$(gh auth token 2>/dev/null)}"
if [ -z "$GH_TOKEN" ]; then
    echo -e "${RED}GH_TOKEN غير موجود. سجّل الدخول أولاً: gh auth login${NC}"
    exit 1
fi

# Transfer the repo to the org using GitHub API
curl -s -X POST \
    -H "Authorization: token ${GH_TOKEN}" \
    -H "Accept: application/vnd.github+json" \
    "https://api.github.com/repos/${REPO}/transfer" \
    -d "{\"new_owner\": \"${ORG_NAME}\", \"new_name\": \"termux-arab\"}" \
    | head -20

echo ""
echo -e "${YELLOW}الخطوة 3: تحديث الروابط بعد النقل${NC}"
echo -e "${BLUE}بعد نقل المستودع، يجب تحديث:${NC}"
echo "  1. next.config.ts: basePath و assetPrefix"
echo "  2. src/app/layout.tsx, sitemap.ts, robots.ts: SITE_URL"
echo "  3. public/downloads/install-termux-arab.sh: GITHUB_USER"
echo ""
echo -e "${YELLOW}شغّل هذا الأمر بعد النقل:${NC}"
echo -e "${GREEN}  bash scripts/update-after-org-transfer.sh ${ORG_NAME}${NC}"

echo ""
echo -e "${GREEN}============================================================${NC}"
echo -e "${YELLOW}الروابط الجديدة بعد النقل:${NC}"
echo "  - Repository: https://github.com/${ORG_NAME}/termux-arab"
echo "  - Website:    https://${ORG_NAME}.github.io/termux-arab/"
echo -e "${GREEN}============================================================${NC}"
