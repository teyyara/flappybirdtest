# Handoff

## Current objective
Upgrade Sky Hopper beyond the first menu milestone. The user explicitly deferred Play Console work; do not upload or publish.

## Candidate
- Branch: feature/lifecycle-accessibility-polish
- Candidate version: 1.2, versionCode 3, application ID com.teyyara.flappybirdtest.
- Base / last known-good main commit: 35c28a14345251afc3bea909931254e6963fea5f (version 1.1 main-menu upgrade; validation run https://github.com/teyyara/flappybirdtest/actions/runs/40193187905 passed).
- Open a pull request for this branch and validate it before merging.

## New candidate changes
- The idle menu sky and bird animate without advancing gameplay physics.
- Added a How to Play guide.
- Gameplay pauses automatically on Android lifecycle ON_PAUSE, including when leaving the app or locking the phone.
- Android Back pauses gameplay, returns to the menu from paused/game-over screens, and dismisses help.
- The main menu and overlays use bounded vertical scrolling on short displays.
- Candidate UI tests cover the How to Play control, Android Back navigation, and lifecycle pause.
- Version bumped to 1.2 / versionCode 3.
- No rewarded ad SDK, network permission, Play Console or release-secret access added.

## Validation protocol
Run JVM tests, lint, debug build and Android emulator instrumentation tests in CI. Inspect the actual run for the candidate branch/commit; a successful older run does not validate version 1.2. Review lint warnings without overstating a warning-free status. Only merge if every required step passes.
After merge, inspect the main-branch validation run and retrieve its debug APK plus manifest/test evidence. The user should install the new APK to test features; the previously shared v1.0 APK is not this candidate.

## Toolchain and constraints
- Android Gradle Plugin 8.13.2, Gradle 8.13, Kotlin 2.2.10, Compose BOM 2026.01.00, JDK 17.
- Compile/target SDK 36; minimum API 24.
- Local agent environment has no Android SDK, Gradle, adb or emulator; remote CI is the execution route.
- Release signing remains environment-only. Never commit/print keystore bytes or passwords.
- Play Console remains deferred at the user's direction.
