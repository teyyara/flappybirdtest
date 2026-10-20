# Test execution plan

## Selected route
REMOTE-CI. The local environment has no Gradle executable, Android SDK, adb, emulator or sdkmanager.

## Toolchain
- Android Gradle Plugin 8.13.2
- Gradle wrapper 8.13
- JDK 17 in CI
- Compile/target SDK 36; minimum SDK 24
- Android Compose BOM 2026.01.00

## Commands
- ./gradlew --no-daemon testDebugUnitTest
- ./gradlew --no-daemon lintDebug
- ./gradlew --no-daemon assembleDebug
- ./gradlew --no-daemon connectedDebugAndroidTest on an Android 36 emulator
- ./gradlew --no-daemon bundleRelease with all four signing environment variables and a readable existing keystore file

## Remote validation workflow
.github/workflows/android-validation.yml installs SDK API 36, runs JVM tests/lint/debug assembly, explicitly enables hosted-runner access to /dev/kvm, then starts an API 36 emulator to run the Compose smoke test. Third-party actions remain pinned to upstream commit references.

## Play release workflow
.github/workflows/android-release.yml is manually triggered. It refuses to continue without repository secrets ANDROID_KEYSTORE_BASE64, ANDROID_KEYSTORE_PASSWORD, ANDROID_KEY_ALIAS, and ANDROID_KEY_PASSWORD. It runs unit tests/lint and the emulator smoke test before building, verifying and uploading the signed AAB.

## Evidence status
Run 39351434704 verified SDK setup, six passing JVM tests, lint and debug assembly, but the Compose test source did not compile. Run 39353520915 fixed the test source and again passed SDK setup, JVM tests/lint/debug assembly, but runtime instrumentation failed because the emulator could not use /dev/kvm and ADB reported no compatible device. A KVM permission fix is now in the workflow; its success remains UNVERIFIED until the next CI run completes.
