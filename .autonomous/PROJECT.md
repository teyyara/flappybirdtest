# Project: Sky Hopper

- Purpose: offline-first portrait Android arcade game with one-tap flap-and-avoid gameplay.
- Package/application ID: com.teyyara.flappybirdtest.
- Stack: Kotlin, Jetpack Compose, Canvas; compile/target SDK 36; minimum API 24.
- Build toolchain: Android Gradle Plugin 8.13.2, Gradle 8.13, Kotlin 2.2.10, Compose BOM 2026.01.00, JDK 17 in CI.
- Architecture: GameEngine owns frame-step physics, pipes, score, and collisions. GameCanvas draws original art, animates the menu scene, and advances the engine only while status is PLAYING. GameScreen owns menus/overlays, local preferences, audio effects, lifecycle pause, and navigation.
- Core flows: animated menu/start, How to Play, tap-to-flap, active score HUD/mute/pause, pause/resume/restart/main-menu, and one free continue after game over that preserves score.
- Lifecycle/back: active gameplay pauses on Android ON_PAUSE; Android Back pauses an active flight, returns from pause/game over to the menu, and closes the help overlay first.
- Accessibility/layout: labeled sound and pause controls; long menu and overlay content scrolls within display-height constraints.
- Candidate version: 1.2 (versionCode 3) on feature/lifecycle-accessibility-polish.
- External services: none. No network permission, analytics, account, payment, or ad SDK.
- Distribution: no store upload or publication authorized; user explicitly deferred Play Console work.
- Build: ./gradlew testDebugUnitTest lintDebug assembleDebug.
- Runtime tests: ./gradlew connectedDebugAndroidTest; remote validation uses Android API 36 emulator.
- Local execution limitation: ChatGPT coding environment has no Android SDK, Gradle, adb, or emulator; use GitHub Actions and inspect its artifacts/logs.
