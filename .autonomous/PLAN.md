# Plan

## Current checkpoint — Sky Hopper v1.4
Status: merged to `main` and validated by GitHub Actions. Current app/source commit: `31f13853f4b91ebfb770f16b97b4b72a2b4c37b6`.

### Completed and verified
1. Main menu with Start Flight, How to Play, saved sound toggle, personal best, and CHILL / CLASSIC / WILD difficulty.
2. Pause/resume/restart/menu return, system Back behavior, lifecycle auto-pause, and one-time continue after game over.
3. Four selectable birds (Sunny, Berry, Mint, Ember) with preview cards; locally saved choice is used in gameplay and remains fixed through a run/continue.
4. JVM engine tests, Android Emulator UI coverage, lint, and debug APK build.
5. Post-merge main run [40203844680](https://github.com/teyyara/flappybirdtest/actions/runs/40203844680) passed all steps.
6. Verified v1.4 debug APK: CI artifact `12603555379`; SHA-256 `643ba9185a89ecae9002bba67af28c23b45a333b602e64bd90bd8edd6ec44774`.

## Next bounded work unit: physical-device acceptance

**Goal:** confirm the tested v1.4 build behaves correctly on the user's Android phone before layering on new features.

**Acceptance criteria**
- APK installs and launches without crashing.
- All four characters are visible/selectable; the selected bird remains selected after closing and reopening the app.
- Difficulty and sound preferences persist after relaunch.
- A flight can start, flap, pause, resume, restart, and return to the menu.
- Game over allows the one-time continue, preserving the current score and chosen character.
- No clipping, unreachable menu controls, or unexpected input behavior on the actual phone.

**Evidence to capture:** device model, Android version/API if known, install/launch result, any repro steps, and relevant screenshots or short screen recording if a defect appears.

**Stop condition:** report the physical-device outcome and record any defects. Do not begin a new feature before the acceptance result unless the user changes priorities.

## Later backlog (not started)
- Improve visual/gameplay polish based on device feedback.
- Consider character progression or further replayability once the current controls/layout are accepted.
- Play Console distribution remains deferred.

## Release boundary
A green hosted-emulator run is not physical-device certification. This APK is for user testing only; no Play Store upload/publication is authorized.
