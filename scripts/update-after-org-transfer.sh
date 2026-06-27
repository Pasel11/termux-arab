#!/usr/bin/env bash
# =====================================================================
#  تحديث الروابط بعد نقل المستودع إلى منظمة
#  Update URLs after transferring repo to organization
# =====================================================================

set -e

ORG_NAME="${1:-termux-arab}"
NEW_URL="https://${ORG_NAME}.github.io/termux-arab"
BASE_PATH="/termux-arab"

echo "Updating URLs for org: ${ORG_NAME}"
echo "New URL: ${NEW_URL}"
echo ""

# 1. Update install script with new GitHub username (org name)
sed -i "s|GITHUB_USER=\"[^\"]*\"|GITHUB_USER=\"${ORG_NAME}\"|g" public/downloads/install-termux-arab.sh
echo "✓ Updated install-termux-arab.sh"

# 2. Update SITE_URL in layout.tsx, sitemap.ts, robots.ts
sed -i "s|https://[a-zA-Z0-9.-]*\.github\.io/termux-arab|${NEW_URL}|g" \
    src/app/layout.tsx \
    src/app/sitemap.ts \
    src/app/robots.ts
echo "✓ Updated SITE_URL in layout.tsx, sitemap.ts, robots.ts"

# 3. Verify basePath and assetPrefix in next.config.ts
grep -E "basePath|assetPrefix" next.config.ts
echo "✓ Verified next.config.ts basePath"

# 4. Commit and push
git add -A
git commit -m "chore: update URLs for organization transfer to ${ORG_NAME}"
git push origin main

echo ""
echo "✅ Done! Site will rebuild automatically via GitHub Actions."
echo "Live URL: ${NEW_URL}"
