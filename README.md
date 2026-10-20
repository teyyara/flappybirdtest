# Sky Hopper

A native Android arcade game inspired by one-tap flap-and-dodge gameplay. It uses original Canvas-drawn scenery, pipes, and bird art; no downloaded game art or external runtime service is required.

## Gameplay

- Tap anywhere to start, then tap to flap upward.
- Pass between moving pipe pairs to earn points.
- A collision with a pipe, ceiling, or ground ends the round.
- Your best score is saved locally on the device.
- Press FLY AGAIN to restart.

## Stack

- Kotlin, Android SDK 36, minimum Android API 24.
- Jetpack Compose UI and a Canvas-rendered, portrait-mode game scene.
- A deterministic, independently testable Kotlin physics/collision engine.
- No network access, analytics, account, or sensitive permissions.

## Build and test

From a machine with JDK 17 and the Android SDK installed, run: ./gradlew testDebugUnitTest, ./gradlew lintDebug, and ./gradlew assembleDebug. With an online emulator/device, run ./gradlew connectedDebugAndroidTest. On Windows use gradlew.bat. Portable environment and test scripts live in scripts/.

The public GitHub Actions workflow builds, runs JVM tests and lint, launches an Android 36 emulator, and runs the Compose smoke journey. Results are only considered verified after inspecting the corresponding workflow run and reports.

The debug APK is produced at app/build/outputs/apk/debug/app-debug.apk after a successful build. This is a development artifact, not a signed Play release.
