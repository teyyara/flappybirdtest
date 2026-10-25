# Test execution

## Selected route
REMOTE-CI. This environment has no Gradle executable, Android SDK, adb, emulator, or sdkmanager. The repository's GitHub Actions workflow is the verified build/runtime execution route.

## Toolchain used by the validated candidate
- Android Gradle Plugin 8.13.2; Gradle wrapper 8.13; Kotlin 2.2.10; JDK 17.
- Compose BOM 2026.01.00.
- Compile/target SDK 36; minimum SDK 24.
- Package: `com.teyyara.flappybirdtest`; versionName `1.4`, versionCode `5`.

## Commands executed in CI
- `./gradlew --no-daemon testDebugUnitTest lintDebug assembleDebug`
- `./gradlew --no-daemon connectedDebugAndroidTest` on the configured Android API 36 emulator.

## Latest verified main run
- Workflow: [Android validation run 40203844680](https://github.com/teyyara/flappybirdtest/actions/runs/40203844680), run #35.
- Event: push to `main`; tested application/source commit: `31f13853f4b91ebfb770f16b97b4b72a2b4c37b6`.
- Status: SUCCESS. SDK provisioning, JVM tests, lint, debug APK build, digest recording, KVM setup, Android Emulator UI suite, and artifact upload all completed.
- JVM report: 16 tests, 0 failures, 0 errors, 0 skipped.
- Emulator instrumentation report: 9 tests, 0 failures, 0 errors, 0 skipped, on Android API 36 emulator.
- Lint report: 0 errors, 16 warnings. Do not call this warning-free. Warnings include target/dependency update advisories and orientation/backup guidance.
- Artifact ID: `12603555379`; artifact digest reported by GitHub: `sha256:45a3098870e0d7c6dacd0087fbc6034593abec3fa273890d2523b247e425be78`.
- APK metadata: `com.teyyara.flappybirdtest`, debug variant, versionName `1.4`, versionCode `5`, minimum API 24.
- APK SHA-256: `643ba9185a89ecae9002bba67af28c23b45a333b602e64bd90bd8edd6ec44774`.

## Candidate validation history
- Pull-request candidate run [40203219590](https://github.com/teyyara/flappybirdtest/actions/runs/40203219590) also succeeded, including the emulator step.
- Earlier candidate [40199740196](https://github.com/teyyara/flappybirdtest/actions/runs/40199740196) exposed launch-menu reachability failures; the layout was corrected and the final PR + main runs passed.
- Run [40202325668](https://github.com/teyyara/flappybirdtest/actions/runs/40202325668) was cancelled during emulator execution and is not counted as passing evidence.

## Results matrix
- Source editing / repository reads and writes: AVAILABLE.
- Local build: BLOCKED (missing local Android toolchain).
- JVM tests: PASS (16/16).
- Lint/static analysis: PASS with 16 warnings and 0 errors.
- Debug APK build: PASS.
- Android Emulator boot: PASS as evidenced by the completed instrumentation job.
- Instrumented UI tests: PASS (9/9, API 36 emulator).
- Physical-device test: NOT RUN.
- CI logs/reports/artifact: RETRIEVABLE.
- Play Store distribution: NOT AUTHORIZED / DEFERRED.

## Physical-device follow-up
After the user installs the debug APK, record device/OS, launch/install behavior, character and difficulty persistence, sound control, pause/resume, game over, and one-time continue results before starting the next feature unit.
