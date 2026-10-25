# Handoff

## Current objective
Add selectable Chill / Classic / Wild flight modes as version 1.3. Play Console remains deferred at the user's direction; do not upload or publish.

## Candidate
- Branch: feature/flight-difficulty-modes
- Candidate version: 1.3, versionCode 4, application ID com.teyyara.flappybirdtest.
- Base commit: 0ebd112331d6c93c7399ef757ec03cde609f2820 (squash merge of v1.2 UX polish into main).
- Open a pull request targeting main after code and docs are complete.

## Changes
- GameEngine defines three difficulty presets; each controls pipe speed, gap height, gravity, flap strength, and spacing.
- Pipes store their gap height so Canvas rendering and collision detection use identical geometry.
- Selected mode is persisted in SharedPreferences and displayed in the main menu.
- Mode cannot be changed during active or paused play; continue keeps score and mode.
- JVM tests cover mode physics, locking and continue geometry; UI test covers menu choice and persistence.
- v1.2 base previously passed 10/10 JVM tests and 7/7 emulator UI tests in candidate run https://github.com/teyyara/flappybirdtest/actions/runs/40194582794. Main-branch v1.2 run is separate and must be checked.
- No rewarded ads, ad SDK, network services, signing secrets or Play Console operations are added.

## Validation protocol
Run JVM tests, lint, debug APK build and API 36 emulator UI tests on the exact PR candidate. Inspect XML reports, failures, warnings, artifact manifest and SHA-256. Merge only if all required steps pass. Inspect the subsequent main-branch workflow and download its debug APK for the user.
Physical device testing is not complete until the user installs the new APK on their phone. Do not report emulator tests as phone tests.

## Toolchain and constraints
- Android Gradle Plugin 8.13.2, Gradle 8.13, Kotlin 2.2.10, Compose BOM 2026.01.00, JDK 17.
- Compile/target SDK 36; minimum API 24.
- Local environment lacks Android SDK, Gradle, adb and emulator; use remote CI.
- Release signing remains environment-only. Never commit/print keystore bytes or passwords.
