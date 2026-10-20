# Handoff

## Current objective
Build a native offline Android game named Sky Hopper in com.teyyara.flappybirdtest.

## Baseline
The repository was empty at bootstrap. The initial README-only commit is the sole starting checkpoint; there is no known-good app build or release.

## Planned implementation
Kotlin + Jetpack Compose, a Canvas game world, a deterministic pure Kotlin engine, touch-to-flap controls, scoring, collisions, restart, and SharedPreferences personal-best persistence.

## Validation route
The connected workspace has no Gradle installation, Android SDK, adb, or emulator. Use the repository-backed GitHub Actions workflow, then retrieve its actual status and evidence. Workflow creation or a push alone does not prove test success.

## Before calling this checkpoint complete
Inspect branch and commit, build/unit/lint output, emulator boot, instrumented UI test, APK SHA-256, and uploaded reports. Update STATE.json, TEST_EXECUTION.md, EVIDENCE_INDEX.md, FAILURES.md, SESSION_LOG.md, and this file from observed results only.

## Next action
Commit the project files and inspect the actual Actions run for the resulting commit.
