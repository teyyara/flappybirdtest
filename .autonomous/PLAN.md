# Plan

## Current upgrade: menu and run controls

1. Add a main menu with an explicit Start Flight button and personal-best display. (Implemented and emulator-tested.)
2. Add a persistent sound-effects toggle on the menu and in-flight/overlay controls. (Implemented; emulator preference test passes.)
3. Add pause/resume, restart, and return-to-main-menu flows. (Implemented; JVM and emulator pause/resume tests pass.)
4. Add one continue action after game over per run, preserving score and giving a fresh obstacle runway. Keep it free until a rewarded-ad integration is explicitly planned and tested. (Implemented; JVM and emulator flow tests pass; no ad SDK.)
5. Fix the instrumentation assertion imports after a failed candidate run, then re-run CI. (Complete; latest candidate CI passed.)
6. Refresh README and persistent project memory/evidence to reflect the actual code and toolchain. (In progress.)
7. Validate the documentation checkpoint, merge PR #1 only after required checks are green, then inspect main-branch CI and retrieve the updated debug APK for device testing. (Pending.)
8. Continue UX polish based on a bounded new unit: lifecycle auto-pause, accessibility/device layout review, and improved original sound/animation polish. (Pending; keep changes test-backed.)
9. Play Console/store submission is explicitly deferred by the user. Do not spend this session on store onboarding, upload, or publication.

## Acceptance for this checkpoint

- JVM unit tests and lint complete successfully.
- Emulator UI flow covers main menu controls, mute persistence, play/pause/resume, and the one-time continue path.
- CI reports the correct candidate identity and produces retrievable APK/test evidence.
- Repository memory accurately records what passed, known warnings, and remaining work.
