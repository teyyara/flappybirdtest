# Handoff

## Current objective
Finish and validate launch-menu bird character selection for Sky Hopper v1.4. Play Console remains deferred.

## Candidate
- Branch: feature/bird-character-selection-final
- Candidate version: 1.4, versionCode 5, application ID com.teyyara.flappybirdtest.
- Base commit: 917202db9da2f0c718e47605f9bb011fb9cbf7f1 (merged v1.3).
- This branch contains the latest character-selection code and tests; open a fresh PR targeting main to avoid inheriting the older PR's stalled emulator run.

## Features implemented
- Four playable birds: Sunny, Berry, Mint, and Ember, with distinct colors and signature accessories.
- Four character preview cards at launch; selected option is highlighted and named.
- Shared Canvas renderer is used for menu previews and gameplay.
- Selection is saved in SharedPreferences and applied to the engine at launch; it remains fixed through pause and one-time continue.
- All characters use identical physics and collision size.
- Character selection tests cover valid choices, persistence, invalid preference fallback, and selection lock during play.
- Launch-menu test asserts that the character choices, selected label, difficulty controls, sound toggle, help, and Start Flight are displayed, not merely present in the semantics tree.

## Validation history / failure analysis
- v1.3 main validation passed in run 40196050574.
- Character candidate run 40199740196 passed JVM tests, lint, and debug build but failed four emulator UI assertions. The main issue was that Start Flight was too low in the launch menu and test clicks did not reach a run.
- The final candidate moves character selection and Start Flight near the top and removes a redundant difficulty heading. The main-menu test checks visibility explicitly.
- Run 40202325668 remained on the emulator step without publishing a result; it is not treated as a pass. The final branch has a fresh PR ref for a conclusive validation run.
- Merge only after the fresh run's JVM tests, lint, debug build, and complete emulator suite pass.

## Toolchain and constraints
- Android Gradle Plugin 8.13.2, Gradle 8.13, Kotlin 2.2.10, Compose BOM 2026.01.00, JDK 17.
- Compile/target SDK 36; minimum API 24.
- Local environment lacks Android SDK, Gradle, adb, and emulator; remote CI is required.
- Release signing remains environment-only. Never commit or print keystore bytes or passwords.
- Physical-device testing remains pending until the user installs the APK and reports feedback.
- No ad SDK, network services, or Play Console operations are included.
