# Handoff

## Current objective
Validate and prepare a signed Google Play App Bundle for the native offline Android game Sky Hopper (com.teyyara.flappybirdtest).

## Current implementation
Kotlin/Jetpack Compose/Canvas game; custom art, one-touch flapping, moving pipes, collision/scoring, restart, saved personal best, six JVM game-engine tests, Compose emulator smoke test, CI workflow, portable scripts and persistent repository strategy.

## Toolchain and constraints
- Android Gradle Plugin 8.13.2, Gradle wrapper 8.13, Kotlin 2.2.10, JDK 17 in CI.
- Compile/target API 36; minimum API 24.
- Local session has no Gradle executable, Android SDK, adb, emulator or sdkmanager; use GitHub Actions and inspect evidence.
- Release signing reads ANDROID_KEYSTORE_PATH, ANDROID_KEYSTORE_PASSWORD, ANDROID_KEY_ALIAS and ANDROID_KEY_PASSWORD from the environment. Release packaging tasks fail closed if these are absent.
- Never commit or print upload keystore bytes/passwords. User must back up their own key and configure repository Actions secrets.

## Latest inspected validation
Run 39353520915 on commit ac73c3a92cb8429d341df06c5649a36a68f0f93a passed SDK provisioning, testDebugUnitTest lintDebug assembleDebug, and artifact upload. The Compose emulator smoke test failed because the runner user could not access /dev/kvm; ADB never received a usable emulator and the test runner reported no compatible device. This is an infrastructure failure, not evidence of an app assertion failure.

## Release workflow
.github/workflows/android-release.yml is manually triggered and designed to run unit tests, lint, emulator smoke tests, build a signed .aab, verify its signature, and upload the bundle plus SHA-256 evidence. See docs/PLAY_RELEASE.md.

## Next action
Inspect the CI run from the commit that adds explicit KVM permission setup. Once runtime validation passes, the user must create and back up an upload keystore, add the four documented GitHub Actions secrets, and manually run Android Play release bundle. Do not claim a Play release is ready until the signed artifact and required evidence are retrieved and inspected.
