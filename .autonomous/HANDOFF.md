# Handoff — Sky Hopper v1.4

Updated: 2026-10-25

## Current objective
Get real-device acceptance of the validated v1.4 APK. The next feature unit should begin after physical-device feedback or a new explicit priority change.

## Verified repository state
- Repository: https://github.com/teyyara/flappybirdtest
- Default branch: `main`
- Application/source commit: `31f13853f4b91ebfb770f16b97b4b72a2b4c37b6`
- App identity: `com.teyyara.flappybirdtest`, versionName `1.4`, versionCode `5`, minimum API 24, compile/target API 36.
- Play Console work is explicitly deferred. No store upload or publication was performed.

## Implemented
- Native Kotlin + Jetpack Compose game UI with Canvas-rendered scenery, obstacles, and bird artwork.
- Animated launch menu; Start Flight; How to Play; saved sound toggle; personal-best display; CHILL / CLASSIC / WILD difficulty.
- Four selectable characters: Sunny (gold), Berry (purple + sparkle), Mint (teal + leaf), and Ember (coral + crest).
- Selection persists locally and drives both the menu preview and actual in-flight bird. Character cosmetics do not change hitbox or physics.
- Pause/resume/restart/menu return, one free continue after game over, system Back handling, and lifecycle auto-pause.

## Validation evidence
- Pull-request candidate run [40203219590](https://github.com/teyyara/flappybirdtest/actions/runs/40203219590): SUCCESS; all build/unit/lint/emulator/evidence steps completed on the final character-selection candidate.
- Post-merge main run [40203844680](https://github.com/teyyara/flappybirdtest/actions/runs/40203844680): SUCCESS on main commit `31f13853f4b91ebfb770f16b97b4b72a2b4c37b6`; every workflow step passed, including the Android Emulator UI suite.
- JVM tests: 16 passed, 0 failures, 0 errors.
- Android API 36 emulator UI tests: 9 passed, 0 failures, 0 errors. Covered main-menu visibility, character selection/persistence, difficulty persistence, sound preference, How to Play, start/pause/resume, background auto-pause, system Back, and one-time continue.
- Android Lint: 0 errors and 16 warnings. Warnings are retained transparently; this is not a warning-free lint run.
- Local toolchain limitation: this session has no Android SDK, Gradle, adb, or emulator. The successful build/test evidence is from GitHub-hosted CI, not a physical phone.

## Current downloadable artifact
- CI artifact ID: `12603555379` from main validation run `40203844680`.
- APK path inside artifact: `app/build/outputs/apk/debug/app-debug.apk`.
- APK SHA-256: `643ba9185a89ecae9002bba67af28c23b45a333b602e64bd90bd8edd6ec44774`.
- It is a debug/testing APK, not a signed store release. The local session copy was extracted and its SHA-256 verified against the artifact manifest.

## Known limitations and next action
- Physical-device installation/testing is still NOT RUN.
- Install the v1.4 APK on the user's Android phone. Verify first launch, selecting each bird, persistence after closing/reopening, difficulty and sound persistence, a flight, pause/resume, and the single continue after game over.
- Then record device/Android version and any issue observed; only then choose the next polish/progression unit.
- Do not add ads, network services, release signing secrets, or Play Console work without explicit authorization.
