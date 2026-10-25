# Handoff

## Current objective
Add a launch-menu character selector with four custom playable birds. Play Console remains deferred at the user's direction; do not upload or publish.

## Candidate
- Branch: feature/bird-character-selection
- Candidate version: 1.4, versionCode 5, application ID com.teyyara.flappybirdtest.
- Base commit: 917202db9da2f0c718e47605f9bb011fb9cbf7f1 (merged v1.3).
- Pull request must target main; validate exact PR head before merging.

## Changes
- Added Sunny, Berry, Mint and Ember character definitions with different palettes and signature accessories.
- Added selection to launch menu, a 2x2 set of character cards, shared real-Canvas previews and a visible selected label.
- Selected character is saved to private SharedPreferences, applied to GameEngine/Canvas, and retained during gameplay and continue.
- The birds use the same physics/hitbox; characters are cosmetic only.
- JVM tests cover active/paused selection locking, selected character through continue, and invalid preference fallback. Compose UI test verifies all choices and preference persistence.
- Previous v1.3 candidate run https://github.com/teyyara/flappybirdtest/actions/runs/40195467256 passed 13 JVM tests and 8 emulator UI tests, with 15 non-fatal lint warnings.
- v1.3 merged to main in commit 917202db9da2f0c718e47605f9bb011fb9cbf7f1 and its main validation run 40196050574 passed.
- No ad SDK, network service, release secrets or Play Console operation added.

## Validation protocol
Run JVM tests, lint, debug build and API 36 emulator tests on the exact PR candidate. Inspect all failure/test reports, lint warnings, artifact manifest and APK SHA-256. Merge only after candidate CI succeeds. Inspect main validation after merge and retrieve the exact main v1.4 debug APK.
Physical device testing remains pending until the user installs the APK and reports feedback. Never describe emulator results as physical-device testing.

## Toolchain and constraints
- Android Gradle Plugin 8.13.2, Gradle 8.13, Kotlin 2.2.10, Compose BOM 2026.01.00, JDK 17.
- Compile/target SDK 36; minimum API 24.
- Local environment lacks Android SDK, Gradle, adb and emulator; remote CI is required.
- Release signing remains environment-only. Never commit/print keystore bytes or passwords.
- Play Console remains deferred.
