# Project: Sky Hopper

- Purpose: offline-first portrait Android arcade game based on one-tap flap-and-avoid mechanics.
- Package/application ID: `com.teyyara.flappybirdtest`.
- Stack: Kotlin, Jetpack Compose, Canvas; compile/target SDK 36; minimum API 24.
- Build toolchain: Android Gradle Plugin 8.13.2, Gradle 8.13, Kotlin Android/Compose plugins 2.2.10, JDK 17 in CI, Compose BOM 2026.01.00.
- Architecture: `GameEngine` owns frame-step physics, pipes, score and collisions. `GameCanvas` paints original art and advances the engine. `GameScreen` owns overlays, local best-score and sound preference persistence, and sound effects.
- Main flows: menu/start, active flight with score HUD/mute/pause, pause/resume/restart/main menu, game-over continue (once per run, currently free)/restart/main menu.
- External services: none. No network permission, analytics, login, payments, ad SDK or sensitive runtime permission.
- Candidate version: 1.1 (versionCode 2) on feature branch `feature/game-menu-mute-continue`.
- Distribution: no store upload or publication authorized; user explicitly deferred Play Console work.
- Build: `./gradlew testDebugUnitTest lintDebug assembleDebug`.
- Runtime tests: `./gradlew connectedDebugAndroidTest`; remote validation uses Android API 36 emulator.
- Local execution limitation: current ChatGPT container has no Android SDK, Gradle, adb or emulator; use GitHub Actions and inspect evidence.
