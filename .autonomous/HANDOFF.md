# Handoff

## Current objective
Continue upgrading Sky Hopper's gameplay and UX. The current user explicitly deferred Play Console work; do not upload or publish.

## Candidate
- Branch: `feature/game-menu-mute-continue`
- Latest implementation commit before memory refresh: `1bc4fc9322a3c350a632df5f775ed449c54fd3f7`
- Open PR: [#1](https://github.com/teyyara/flappybirdtest/pull/1)
- Candidate version: `1.1`, versionCode `2`, application ID `com.teyyara.flappybirdtest`.
- Last validated synthetic PR merge commit from CI evidence: `2c7408f6e14a4942f505ec58f89df44d0135af30`.

## Current implementation
Kotlin/Jetpack Compose/Canvas; original art, one-touch flap-and-dodge gameplay, moving pipes, collision/scoring, saved personal best, explicit main menu/start button, persistent sound effects mute toggle, in-flight pause, resume/restart/main-menu actions, and a single free continue after game over that preserves score and respawns with a new pipe runway. Rewarded ads are not wired and no ad SDK/network permission was added.

## Latest validation (actual CI evidence inspected)
Run [40192044874](https://github.com/teyyara/flappybirdtest/actions/runs/40192044874) succeeded:
- JVM: 10 tests passed, 0 failures/errors.
- Emulator UI: 4 tests passed, 0 failures/errors (main menu, sound preference persistence, pause/resume, one-time continue).
- `testDebugUnitTest lintDebug assembleDebug`: success.
- API 36 Android emulator and hosted KVM setup: success.
- Lint: 0 errors, 11 warnings.
- Artifact ID `12598588175`; artifact includes `app-debug.apk`, test XML/logs, lint report, candidate manifest and APK digest.
- APK SHA-256: `2c536ec1262457b7f466f256ae83bf5ebe85b8378ea6fe36c3755e4b077c518a`.

## Toolchain and constraints
- Android Gradle Plugin 8.13.2, Gradle 8.13, Kotlin plugins 2.2.10, Compose BOM 2026.01.00, JDK 17 in CI.
- Compile/target SDK 36; minimum API 24.
- Local agent environment has no Android SDK, Gradle, adb or emulator; use GitHub Actions and inspect artifacts.
- Release signing remains environment-only. Never commit/print keystore bytes or passwords.

## Next action
Commit refreshed docs/memory on the feature branch and validate that exact candidate; merge PR #1 only after green checks. Then inspect the main-branch CI run and retrieve the resulting version 1.1 debug APK for user phone testing. Continue with lifecycle pause/adaptive UI/accessibility and polish as another bounded unit. Play Console remains deferred.
