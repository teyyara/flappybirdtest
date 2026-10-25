# Plan

## Current upgrade: playable bird character selection (version 1.4)

1. Preserve the v1.3 menu, saved mute, How to Play, difficulty presets, pause/resume, Back navigation, lifecycle auto-pause, and one free continue. The v1.3 main-branch validation run 40196050574 passed.
2. Add four distinct playable bird designs: Sunny (gold), Berry (purple + sparkle), Mint (teal + leaf), and Ember (coral + crest). All share the same physics and hitbox. Implemented.
3. Add a four-choice character picker and actual Canvas previews to the launch menu. The same renderer draws menu previews and the active bird. Implemented.
4. Persist the choice locally and apply it to GameEngine/Canvas at launch; keep the selection through pause, game over, restart, and continue. Implemented.
5. Add engine tests for selection, active/paused lock, continue, and invalid-preference fallback; add UI tests for persistence and visible launch choices. Implemented.
6. Fix launch-menu reachability after the first UI run showed Start Flight was below the visible area. Move character choice/start up and compact duplicate settings UI. Implemented; final CI pending.
7. Open a fresh PR ref and run JVM tests, lint, debug build, and Android API 36 emulator UI tests on the final candidate. Pending.
8. Merge only after the exact candidate passes, inspect main-branch validation, and retrieve a verified v1.4 debug APK for phone testing. Pending.
9. Continue with visual polish or character progression only after the user tests this build. Play Console remains deferred.

## Acceptance

- Sunny, Berry, Mint, and Ember are visibly distinct by palette and signature accessory.
- All four character choices and Start Flight are visible immediately on the launch menu on the standard emulator layout.
- Selection updates a visible selected state, persists locally, and determines the bird drawn in gameplay.
- Character choice does not change hitbox, flight physics, obstacle layout, difficulty, or score.
- Existing difficulty, mute, help, pause/resume, continue, system Back, and lifecycle behaviors remain intact.
- CI tests the exact final PR head; emulator tests are not described as physical-device tests.
