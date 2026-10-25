# Releases

No Google Play release has been submitted, approved, or distributed. Play Console work is explicitly deferred by the user.

## Current development build — v1.4
- Main commit: `31f13853f4b91ebfb770f16b97b4b72a2b4c37b6`.
- Post-merge Android validation: [run 40203844680](https://github.com/teyyara/flappybirdtest/actions/runs/40203844680), SUCCESS.
- JVM tests: 16/16 passed; Android API 36 emulator UI tests: 9/9 passed; lint: 0 errors and 16 warnings.
- CI debug APK artifact ID: `12603555379`; APK SHA-256: `643ba9185a89ecae9002bba67af28c23b45a333b602e64bd90bd8edd6ec44774`.
- The debug APK is a development/testing artifact, not a release-signed store package. Physical-device testing is still outstanding.
- No upload, submission, or publication was performed.

## Prior signed build
- On 2026-10-25, manual workflow run `40190067246` built a signed version 1.0 App Bundle from main commit `1d4d58f0e2de798314043aad0e255d6d04f7907c`.
- The workflow verified its signature and uploaded the AAB and SHA-256 evidence. This is a prior artifact, not the current v1.4 candidate, and it has not been submitted or distributed.

## Signing safety
- Release signing uses environment-only credentials from `ANDROID_KEYSTORE_PATH`, `ANDROID_KEYSTORE_PASSWORD`, `ANDROID_KEY_ALIAS`, and `ANDROID_KEY_PASSWORD`.
- Never commit or print the user-owned upload keystore, its passwords, or the base64 repository secret.
