# Sky Hopper — Google Play release preparation

This repository builds a signed Android App Bundle (.aab) for Google Play. The workflow is intentionally manual and refuses to package a release unless all signing inputs are supplied. The upload keystore is never committed to Git and is stored temporarily on the hosted runner.

## 1. Create and back up your upload key (Windows)

Install a JDK (Android Studio includes one) and open PowerShell in a private folder OUTSIDE the repository. Generate the upload keystore with this command:

    keytool -genkeypair -v -keystore skyhopper-upload.jks -alias skyhopper-upload -keyalg RSA -keysize 2048 -validity 10000

Choose strong passwords when prompted. Keep skyhopper-upload.jks and its passwords in a secure backup. Losing the upload key can interrupt updates; do not share it, email it, or commit it. This project's .gitignore excludes .jks and .keystore files as a second guard.

## 2. Add four GitHub Actions repository secrets

Open https://github.com/teyyara/flappybirdtest, then go to Settings → Secrets and variables → Actions → New repository secret. Create these secrets:

| Secret name | Value |
| --- | --- |
| ANDROID_KEYSTORE_BASE64 | Base64 representation of skyhopper-upload.jks |
| ANDROID_KEYSTORE_PASSWORD | The keystore password you chose |
| ANDROID_KEY_ALIAS | skyhopper-upload |
| ANDROID_KEY_PASSWORD | The key password you chose |

To copy the Base64 value to your clipboard from PowerShell, while in the folder containing the keystore, run:

    [Convert]::ToBase64String([IO.File]::ReadAllBytes("$PWD\skyhopper-upload.jks")) | Set-Clipboard

Paste that clipboard value into ANDROID_KEYSTORE_BASE64. Do not paste any password or the Base64 keystore into a chat, issue, source file, or workflow YAML. Only put them in the repository's Actions secrets.

## 3. Run the release workflow

1. Open Actions → Android Play release bundle.
2. Select Run workflow on the main branch.
3. Wait for the workflow to pass JVM unit tests, Android lint, and the Compose UI smoke test on an Android emulator.
4. From a successful run, download the artifact named sky-hopper-play-release-<commit>. It contains app-release.aab and release evidence including its SHA-256 digest.

The workflow uses the upload key to sign the bundle and verifies the signature before exposing the AAB artifact. Missing signing secrets, build failures, emulator failures, or signature verification failures block the release artifact.

## 4. Play Console checklist

- Create the app in Google Play Console and follow its Play App Signing onboarding. For new apps, Play App Signing is the required signing path.
- Upload the signed .aab to an internal testing track first. Check the generated device APKs and test on real devices before expanding rollout.
- Prepare the store listing (app name, short and full descriptions, high-resolution app icon, feature graphic, and screenshots), content rating, target audience, data safety and app-access declarations as applicable.
- Keep the package name com.teyyara.flappybirdtest unchanged after creating the Play Console app.
- Increment versionCode for every subsequent upload. versionName is the human-visible release label.
- Store the upload keystore and credentials in a durable, private backup. Google Play acceptance and policy compliance are not guaranteed by a successful build.

## Current known limitation

The source, debug build and JVM tests are in the repository. The latest inspected CI run passed SDK provisioning, JVM tests, lint and debug APK assembly, but the emulator smoke test failed because the hosted runner lacked permission to use /dev/kvm. The validation workflow now explicitly grants access on the ephemeral runner and checks that permission before launching the emulator. A release is not considered validated until a new run passes the UI test and the signed AAB signature check.
