#!/usr/bin/env bash
set -uo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
has_cmd() { command -v "$1" >/dev/null 2>&1 && printf true || printf false; }
has_path() { test -e "$1" && printf true || printf false; }
OS="$(uname -s | tr '[:upper:]' '[:lower:]')"
ARCH="$(uname -m)"
JAVA_OK="$(has_cmd java)"
WRAPPER_OK="$(has_path "$ROOT/gradlew")"
SDK_PATH="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-}}"
SDK_OK=false
if [ -n "$SDK_PATH" ] && [ -d "$SDK_PATH" ]; then SDK_OK=true; fi
ADB_OK="$(has_cmd adb)"
EMU_OK="$(has_cmd emulator)"
SDKMANAGER_OK="$(has_cmd sdkmanager)"
printf '%s\n' "Project: $ROOT" "OS/architecture: $OS / $ARCH"
printf '%s\n' "Java available: $JAVA_OK; Gradle wrapper present: $WRAPPER_OK"
printf '%s\n' "Android SDK available: $SDK_OK; adb: $ADB_OK; emulator: $EMU_OK; sdkmanager: $SDKMANAGER_OK"
if [ "$JAVA_OK" = true ]; then java -version 2>&1 | head -n 3; fi
if [ "$ADB_OK" = true ]; then adb devices -l; fi
df -h "$ROOT" 2>/dev/null | tail -n 1 || true
printf '{"os":"%s","arch":"%s","java_available":%s,"gradle_wrapper_present":%s,"android_sdk_available":%s,"adb_available":%s,"emulator_available":%s,"sdkmanager_available":%s}\n' "$OS" "$ARCH" "$JAVA_OK" "$WRAPPER_OK" "$SDK_OK" "$ADB_OK" "$EMU_OK" "$SDKMANAGER_OK"
if [ "$JAVA_OK" = true ] && [ "$WRAPPER_OK" = true ] && [ "$SDK_OK" = true ]; then exit 0; fi
if [ "$JAVA_OK" = true ] && [ "$WRAPPER_OK" = true ]; then exit 2; fi
exit 3
