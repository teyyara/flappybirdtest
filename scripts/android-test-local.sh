#!/usr/bin/env bash
set -uo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"
STAMP="$(date -u +%Y%m%dT%H%M%SZ)"
OUT="${EVIDENCE_DIR:-$ROOT/evidence/local/$STAMP}"
mkdir -p "$OUT"
printf '%s\n' "candidate_commit=$(git rev-parse HEAD 2>/dev/null || printf UNKNOWN)" "started_utc=$(date -u +%FT%TZ)" "os=$(uname -s)" > "$OUT/manifest.txt"
./gradlew --version > "$OUT/gradle-version.log" 2>&1
GRADLE_RC=$?
printf 'gradle_wrapper_version_exit=%s\n' "$GRADLE_RC" >> "$OUT/manifest.txt"
if [ "$GRADLE_RC" -ne 0 ]; then echo "Gradle wrapper bootstrap failed; see gradle-version.log" | tee -a "$OUT/status.txt"; exit "$GRADLE_RC"; fi
run_task() {
  name="$1"; shift
  printf 'Running %s\n' "$name"
  printf '$' > "$OUT/$name.log"; printf ' %q' "$@" >> "$OUT/$name.log"; printf '\n' >> "$OUT/$name.log"
  "$@" >> "$OUT/$name.log" 2>&1
  rc=$?
  printf '%s_exit=%s\n' "$name" "$rc" >> "$OUT/manifest.txt"
  if [ "$rc" -ne 0 ]; then printf '%s=FAIL\n' "$name" >> "$OUT/status.txt"; return "$rc"; fi
  printf '%s=PASS\n' "$name" >> "$OUT/status.txt"; return 0
}
run_task unit_tests ./gradlew --no-daemon testDebugUnitTest || exit $?
run_task lint ./gradlew --no-daemon lintDebug || exit $?
run_task debug_apk ./gradlew --no-daemon assembleDebug || exit $?
if command -v adb >/dev/null 2>&1 && adb devices 2>/dev/null | grep -q '[[:space:]]device$'; then
  run_task instrumented_ui_tests ./gradlew --no-daemon connectedDebugAndroidTest || exit $?
else
  printf '%s\n' "instrumented_ui_tests=NOT_RUN_NO_ONLINE_DEVICE" >> "$OUT/status.txt"
fi
find app/build/outputs/apk -type f -name '*.apk' -exec sha256sum {} \; > "$OUT/apk-sha256.txt" 2>/dev/null || true
printf 'finished_utc=%s\n' "$(date -u +%FT%TZ)" >> "$OUT/manifest.txt"
