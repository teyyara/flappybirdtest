# Evidence index

## Latest verified candidate
- Workflow: [Android validation run 40192044874](https://github.com/teyyara/flappybirdtest/actions/runs/40192044874).
- Feature-branch commit: `1bc4fc9322a3c350a632df5f775ed449c54fd3f7`; tested PR merge commit recorded by the runner: `2c7408f6e14a4942f505ec58f89df44d0135af30`.
- Conclusion: SUCCESS. SDK setup, JVM tests, lint, debug APK build, hosted KVM setup, Android emulator UI journey, and evidence upload completed.
- JVM report: 10 tests, no failures or errors.
- Emulator UI report: 4 tests, no failures/errors—menu controls, persistent sound preference, pause/resume, and one-time continue after game over.
- Lint: 0 errors, 11 warnings. Outstanding advisories include portrait-only orientation, target/dependency updates, backup-data rules, and KTX use suggestions. None are marked fixed or suppressed here.
- Candidate app: `com.teyyara.flappybirdtest`, versionName `1.1`, versionCode `2`.
- Debug APK SHA-256: `2c536ec1262457b7f466f256ae83bf5ebe85b8378ea6fe36c3755e4b077c518a`.
- Evidence artifact: `sky-hopper-evidence-2c7408f6e14a4942f505ec58f89df44d0135af30`, artifact ID `12598588175`, includes the built APK, APK digest, manifest, JVM report, instrumentation XML/logs, and lint reports.

## Previous verified checkpoints
- Run 39435122447 on main commit `1d4d58f0e2de798314043aad0e255d6d04f7907c`: Android validation passed before the menu upgrade.
- Run 40190067246 on the same main commit: signed version 1.0 App Bundle built and signature verification passed; this is a prior artifact and was not submitted to Play Console or distributed.
- Run 40191612742 failed due to incorrect imports in the new Compose UI test source. The issue was fixed in the next branch commit; run 40192044874 validates that corrected source.

## Pending
- Add this refreshed memory to the feature branch, revalidate the exact resulting candidate, then merge PR #1 only with green checks.
- Inspect validation on resulting main commit and download the matching version 1.1 debug APK for the user to install/test on their phone.
- Physical-device validation of the new menu/continue experience remains outstanding.
