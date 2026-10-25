# Plan

## Current upgrade: lifecycle safety, guidance, and polish (version 1.2)

1. Preserve menu/start, saved mute, pause/resume, restart, return-to-menu, and one free continue after game over. Completed in version 1.1 and previously validated.
2. Give the main menu a subtle animated sky and bird idle motion without advancing gameplay physics. Implemented; CI pending.
3. Add a first-run-friendly How to Play guide with clear tap, obstacle, pause, and continue instructions. Implemented; CI pending.
4. Pause active gameplay on Android lifecycle pause/background; never silently resume a run when the user returns. Implemented; emulator test added; CI pending.
5. Make Android Back follow game navigation: pause active play; from pause/game over return to the main menu; close the help overlay first. Implemented; emulator test added; CI pending.
6. Make long menu and overlay content scroll on smaller displays. Implemented; device layout review via emulator pending.
7. Re-run JVM tests, lint, debug build, and Android emulator tests. Fix failures before merging. Pending CI.
8. Merge only after the candidate checks are green, inspect main-branch validation, and retrieve the version 1.2 debug APK for real-device testing. Pending.
9. Continue with a new bounded gameplay/content milestone after user tests this build. Play Console remains deferred.

## Acceptance for this checkpoint

- Existing ten JVM tests still pass.
- Emulator tests cover the menu, persistent mute, pause/resume, one-time continue, How to Play open/close, system Back navigation, and lifecycle auto-pause.
- testDebugUnitTest, lintDebug, assembleDebug, and connectedDebugAndroidTest succeed.
- Release-signing secrets are never needed in ordinary validation; no ads, network service, or Play Console work is introduced.
- Candidate evidence and repository memory accurately distinguish implemented features from CI/device-verified behavior.
