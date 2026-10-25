# Sky Hopper

A native Android arcade game with original Canvas-drawn scenery, pipes, and bird art. It uses one-touch flap-and-dodge gameplay, has no network service, and stores the personal best locally.

## Gameplay

- Main menu: tap **START FLIGHT** to begin.
- During a flight, tap the game area to flap, use **Ⅱ** to pause, and use the speaker control to mute/unmute sound effects.
- Pause: resume the current flight, restart the run, return to the main menu, or toggle sound.
- Game over: review score and personal best, **CONTINUE RUN** once in the current run (free placeholder; rewarded ads are not integrated yet), **FLY AGAIN**, or return to the menu.
- Sound preference and personal best are saved locally on the device.

## Stack and privacy

- Kotlin, Jetpack Compose UI, and a Canvas-rendered portrait game scene.
- Android Gradle Plugin 8.13.2, Gradle wrapper 8.13, Kotlin 2.2.10, JDK 17.
- Compile/target SDK 36; minimum SDK 24.
- No analytics, accounts, payments, network permission, ad SDK, or sensitive runtime permission.

## Build and test

From a machine with JDK 17 and the Android SDK, run `./gradlew testDebugUnitTest lintDebug assembleDebug`. With an online emulator/device, run `./gradlew connectedDebugAndroidTest`. On Windows use `gradlew.bat`. Portable environment and test scripts live in `scripts/`.

Android validation workflow: [GitHub Actions](https://github.com/teyyara/flappybirdtest/actions/workflows/android-validation.yml). Check the run and reports before treating a candidate as validated.

The debug APK is produced at `app/build/outputs/apk/debug/app-debug.apk` after a successful build. It is for development/testing, not a store release.

## Distribution

Play Console work is deferred at the user's direction. Do not submit or publish anything without explicit user authorization. Release-signing setup and documentation remain in the repository for future use.
