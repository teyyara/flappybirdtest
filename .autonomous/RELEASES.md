# Releases

No Google Play release has been submitted, approved, or distributed. Play Console work is explicitly deferred by the user.

## Prior signed build
- On 2026-10-25, manual workflow run [40190067246](https://github.com/teyyara/flappybirdtest/actions/runs/40190067246) built a signed version 1.0 App Bundle from main commit `1d4d58f0e2de798314043aad0e255d6d04f7907c`.
- The workflow verified its signature and uploaded the AAB and SHA-256 evidence. This is a prior build, not the current 1.1 candidate, and it has not been submitted or distributed.

## Current development candidate
- Version 1.1 / versionCode 2 is being developed on `feature/game-menu-mute-continue` in PR #1.
- Validation run [40192044874](https://github.com/teyyara/flappybirdtest/actions/runs/40192044874) passed against its tested PR merge candidate, including 10 JVM tests and 4 emulator UI tests.
- A debug APK was built as validation evidence; it is a development/test artifact, not a signed Play release.
- Play Console onboarding, store listing, policy declarations, internal testing and publication remain out of scope until the user explicitly resumes that work.

## Signing safety
- Release signing uses environment-only credentials from `ANDROID_KEYSTORE_PATH`, `ANDROID_KEYSTORE_PASSWORD`, `ANDROID_KEY_ALIAS`, and `ANDROID_KEY_PASSWORD`.
- Never commit or print the user-owned upload keystore, its passwords, or the base64 repository secret.
