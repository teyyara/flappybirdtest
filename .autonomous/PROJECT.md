# Project: Sky Hopper

- Purpose: offline-first portrait Android arcade game with one-tap flap-and-avoid gameplay.
- Package/application ID: com.teyyara.flappybirdtest.
- Stack: Kotlin, Jetpack Compose, Canvas; compile/target SDK 36; minimum API 24.
- Build toolchain: Android Gradle Plugin 8.13.2, Gradle 8.13, Kotlin 2.2.10, Compose BOM 2026.01.00, JDK 17 in CI.
- Architecture: GameEngine owns frame-step physics, pipes, score, collisions, and difficulty presets. GameCanvas draws the world and uses each pipe's actual gap geometry. GameScreen owns menu/overlays, local preferences, sound effects, lifecycle pause, navigation, and difficulty selection.
- Main menu: animated sky/bird, Start Flight, saved mute toggle, personal best, How to Play, and selectable Chill / Classic / Wild mode.
- Difficulty: each preset sets pipe speed, gap height, gravity, flap impulse, and spacing. The selected mode is persisted locally and locked for the duration of a run; continue uses the same mode.
- Core game flows: tap-to-flap, score HUD, pause/resume/restart/main-menu, one free continue after game over, How to Play guide, system Back, and auto-pause on Android ON_PAUSE.
- Candidate version: 1.3 (versionCode 4) on feature/flight-difficulty-modes.
- External services: none. No network permission, analytics, account, payment, or ad SDK.
- Distribution: no store upload/publication authorized; the user explicitly deferred Play Console work.
- Build: ./gradlew testDebugUnitTest lintDebug assembleDebug.
- Runtime tests: ./gradlew connectedDebugAndroidTest; remote validation uses Android API 36 emulator.
- Local execution limitation: this agent environment has no Android SDK, Gradle, adb or emulator; use remote CI and inspect its reports/artifacts.
