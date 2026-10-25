# Plan

## Current upgrade: playable bird character selection (version 1.4)

1. Preserve the v1.3 menu, difficulty selection, saved mute, Help, pause/resume, Back navigation and lifecycle auto-pause. v1.3 candidate run 40195467256 passed 13 JVM tests and 8 emulator UI tests; merged main validation run 40196050574 also passed.
2. Add four distinct drawable characters: Sunny (gold), Berry (purple + sparkle), Mint (teal + leaf), and Ember (coral + crest). They share the same game physics and hitbox so character choice is cosmetic/fair. Implemented; v1.4 CI pending.
3. Add a 2x2 character picker and actual bird previews to the launch menu. The same renderer draws both the preview and in-flight bird. Implemented; v1.4 CI pending.
4. Persist selection in local SharedPreferences, apply it to the actual GameEngine/Canvas, and keep the chosen character fixed during a run and one-time continue. Implemented; v1.4 tests pending.
5. Add engine tests for selection/lock/continue/fallback, and emulator tests for all choices appearing plus selection persistence. Implemented; v1.4 CI pending.
6. Run JVM tests, lint, debug build, and Android API 36 emulator UI tests. Fix any failures before merge. Pending.
7. Merge only after green candidate checks, inspect main validation, and retrieve the v1.4 debug APK for real-device testing. Pending.
8. Continue with game feel, additional visual polish, or character unlock/progression only after the user tests this character selection build. Play Console remains deferred.

## Acceptance

- Sunny, Berry, Mint and Ember are visually distinct via colors and their signature accessories.
- Player can select any character from the launch menu; selection has a visible selected state and is persisted locally.
- The selected bird is drawn in the animated menu preview and during gameplay; pause, game over, restart and continue do not silently switch characters.
- Character choice does not change physics, obstacles, collision size or score.
- Existing difficulty, mute, pause/resume, continue, back-navigation and lifecycle behaviors remain intact.
- CI reports the exact tested commit; no Play Console or ad integration is introduced.
