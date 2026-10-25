# Evidence index

## Current verified main candidate — Sky Hopper v1.4
- Source commit: `31f13853f4b91ebfb770f16b97b4b72a2b4c37b6`.
- Workflow: [Android validation run 40203844680](https://github.com/teyyara/flappybirdtest/actions/runs/40203844680), run #35 on `main`.
- Conclusion: SUCCESS. SDK setup, JVM tests, lint, debug APK build, KVM setup, Android API 36 emulator UI tests, and artifact upload completed.
- JVM tests: 16, 0 failures/errors.
- Emulator UI tests: 9, 0 failures/errors, including character selection persistence and visible launch-menu controls.
- Lint: 0 errors, 16 warnings; advisories remain visible and unsuppressed.
- Package/version: `com.teyyara.flappybirdtest`, v1.4, versionCode 5, min API 24, target API 36.
- CI artifact: `sky-hopper-evidence-31f13853f4b91ebfb770f16b97b4b72a2b4c37b6`, ID `12603555379`, GitHub-reported digest `sha256:45a3098870e0d7c6dacd0087fbc6034593abec3fa273890d2523b247e425be78`.
- APK SHA-256: `643ba9185a89ecae9002bba67af28c23b45a333b602e64bd90bd8edd6ec44774`.

## Final character-selection PR validation
- Workflow: [run 40203219590](https://github.com/teyyara/flappybirdtest/actions/runs/40203219590), PR candidate head `d2ed9d011e3b9e38ef52aa1c201ab74858cdb913`.
- Conclusion: SUCCESS; all build/unit/lint/emulator/evidence steps completed.
- The CI manifest for this PR workflow records synthetic pull-request merge commit `15c2b9cf7710d3aca956480525dc44493737201f`.

## Resolved failure history
- Run `40199740196` exposed launch-menu reachability failures after character choices increased menu height.
- The launch menu was compacted, character selection and Start Flight were moved near the top, and visibility assertions were strengthened.
- Run `40202325668` was cancelled before the emulator suite could finish and is not a pass.
- Final PR run `40203219590` and post-merge main run `40203844680` both passed the emulator suite. The earlier layout issue is resolved for the tested configuration.

## Previous checkpoint
- Version 1.1 validation run `40192044874` passed its then-applicable 10 JVM and 4 emulator tests. This is retained as historical evidence and does not replace v1.4 evidence.

## Limits
- Emulator results do not certify every physical Android phone.
- Physical-device acceptance remains NOT RUN.
- No Play Store release or publication was performed.
