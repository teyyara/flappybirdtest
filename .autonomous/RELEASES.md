# Releases

No Google Play release has been certified or distributed.

## Release preparation

- Source code and Gradle release signing configuration are committed.
- A manual workflow is configured to run unit tests, lint, emulator smoke tests, assemble a signed Android App Bundle, verify its signature, and upload the bundle and SHA-256 evidence.
- Release requires a user-owned upload keystore and the four GitHub Actions repository secrets documented in docs/PLAY_RELEASE.md. These secrets are not currently provisioned.
- The debug APK is a development artifact and is not a Play Store release.
- Play Console setup, store listing, policy declarations, internal testing, final acceptance and publication remain outstanding.
