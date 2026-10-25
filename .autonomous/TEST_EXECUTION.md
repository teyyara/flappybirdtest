# Test execution plan

## Selected route
REMOTE-CI. This agent environment has no Gradle executable, Android SDK, adb, emulator, or sdkmanager.

## Toolchain
- Android Gradle Plugin 8.13.2; Gradle wrapper 8.13.
- Kotlin Android and Compose plugins 2.2.10; JDK 17 in CI.
- Compile/target SDK 36; minimum SDK 24.
- Compose BOM 2026.01.00.

## Commands
- `./gradlew --no-daemon testDebugUnitTest lintDebug assembleDebug`
- `./gradlew --no-daemon connectedDebugAndroidTest` on the configured Android 36 emulator.
- `./gradlew --no-daemon bundleRelease` only when all signing environment variables are present and the existing user-owned keystore is readable. Play release work is not part of the current goal.

## Remote validation workflow
`.github/workflows/android-validation.yml` installs SDK API 36, runs JVM tests, lint and debug APK assembly, configures hosted-runner access to `/dev/kvm`, then starts an API 36 emulator for the Compose UI tests. Evidence is uploaded even if a step fails. Third-party actions are pinned to upstream commit references.

## Latest inspected validation
- Run: [40192044874](https://github.com/teyyara/flappybirdtest/actions/runs/40192044874)
- Feature branch commit: `1bc4fc9322a3c350a632df5f775ed449c54fd3f7`.
- Tested synthetic PR merge commit (from evidence manifest): `2c7408f6e14a4942f505ec58f89df44d0135af30`.
- Status: SUCCESS. SDK setup, `testDebugUnitTest lintDebug assembleDebug`, KVM setup, emulator UI tests, and evidence upload all completed successfully.
- JVM report: 10 tests, 0 failures, 0 errors.
- Android UI report: 4 tests, 0 failures, 0 errors: menu/start/sound controls, sound preference persistence, pause/resume, and continue-after-game-over once per run.
- Lint: 0 errors, 11 warnings. Warnings concern target/dependency version advisories, portrait orientation, backup-data rules, and SharedPreferences KTX suggestions; they have not been silently suppressed.
- Debug artifact identity: versionName `1.1`, versionCode `2`, application ID `com.teyyara.flappybirdtest`, SHA-256 `2c536ec1262457b7f466f256ae83bf5ebe85b8378ea6fe36c3755e4b077c518a`.
- Downloadable CI evidence artifact ID: `12598588175`; includes APK, unit/instrumentation results, lint report, and manifest/digest.

## Important scope limits
This run validates the candidate against a hosted API 36 emulator, not every physical device. The newer candidate has not yet been tested on the user's phone in this session. Wait for the documentation checkpoint's fresh CI run and main-branch validation before calling that post-merge artifact the current main build.
