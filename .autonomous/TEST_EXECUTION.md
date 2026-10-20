# Test execution plan

## Selected route
REMOTE-CI. The local environment has OpenJDK 21 but no Gradle executable, Android SDK, adb or emulator.

## Commands
- ./gradlew --version
- ./gradlew --no-daemon testDebugUnitTest
- ./gradlew --no-daemon lintDebug
- ./gradlew --no-daemon assembleDebug
- ./gradlew --no-daemon connectedDebugAndroidTest on an Android 36 emulator

## Remote workflow
.github/workflows/android-validation.yml uses a public standard Ubuntu runner, JDK 17, Android SDK 36, JVM tests, lint, debug assembly and Android Emulator UI tests. Third-party actions are pinned to upstream commit references. No production secrets or paid service are needed.

## Evidence status
NOT_RUN until an actual workflow run ID, candidate SHA, results, and artifact metadata are retrieved and inspected. Any unexecuted or inaccessible test remains NOT_RUN/BLOCKED.
