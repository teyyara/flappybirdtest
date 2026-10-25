# Plan

## Current upgrade: selectable flight difficulty (version 1.3)

1. Preserve the v1.2 menu, saved mute, How to Play guide, pause/resume, system Back, lifecycle auto-pause, and one free continue. These passed the candidate CI run 40194582794.
2. Add three clear flight presets: Chill, Classic, and Wild. Each changes obstacle speed, pipe gap height, gravity, flap strength, and spacing. (Implemented; candidate validation pending.)
3. Persist selected difficulty locally, show the active choice in the main menu, and keep a run's physics rules fixed after a flight starts. (Implemented; candidate validation pending.)
4. Ensure the one-time continue keeps the selected mode and uses that mode's gap size. (Implemented; JVM test added; candidate validation pending.)
5. Test all presets' game physics, menu persistence, and mode lock; run JVM tests, lint, debug build, and Android emulator UI tests. Fix any failure before merge. (Pending CI.)
6. Merge only after candidate checks are green, then inspect the main-branch validation and retrieve a v1.3 debug APK for real-device testing. (Pending.)
7. Continue with a distinct visual/gameplay loop after this build is verified. Play Console remains deferred.

## Acceptance for this checkpoint

- Chill has visibly slower pipes and wider gaps; Wild has faster pipes and narrower gaps; Classic retains the v1.2 baseline physics.
- Mode selection persists after process/app restart and is not changeable during active or paused play.
- Continue preserves score and current difficulty.
- Existing v1.2 menu, mute, pause, back, lifecycle and continue flows continue passing.
- Evidence distinguishes candidate CI from physical-device validation.
