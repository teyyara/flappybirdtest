# Test execution plan

## Selected route
REMOTE-CI. The local environment has OpenJDK 21 but no Gradle executable, Android SDK, adb or emulator.

## Toolchain
- Android Gradle Plugin 9.1.1
- Gradle wrapper 9.3.1
- JDK 17 in CI
- Compile SDK / emulator API 37; target SDK 36; min SDK 24

## Commands
- ./gradlew --version
- ./gradlew --no-daemon testDebugUnitTest
- ./gradlew --no-daemon lintDebug
- ./gradlew --no-daemon assembleDebug
- ./gradlew --no-daemon connectedDebugAndroidTest on an Android 37 emulator

## Remote workflow
.github/workflows/android-validation.yml uses a public standard Ubuntu runner, installs Android SDK 37, runs JVM tests, lint, debug assembly and Android Emulator UI tests. Third-party actions are pinned to upstream commit references. No production secrets or paid service is needed.

## Evidence status
Previous run 39350819155 failed during AGP dependency metadata checks because Compose 1.12.1 requires AGP 9.1+ and compile API 37. The toolchain remediation is committed separately. Current candidate validation is NOT_RUN until a new workflow run's exact commit, results and artifact metadata are retrieved.
