# Project: Sky Hopper

- Purpose: offline-first portrait Android arcade game with one-tap flap-and-avoid gameplay.
- Package/application ID: com.teyyara.flappybirdtest.
- Stack: Kotlin, Jetpack Compose, Canvas; compile/target SDK 36; minimum API 24.
- Build toolchain: Android Gradle Plugin 8.13.2, Gradle 8.13, Kotlin 2.2.10, Compose BOM 2026.01.00, JDK 17 in CI.
- Architecture: GameEngine owns frame-step physics, selected difficulty, selected bird, pipes, score and collisions. GameCanvas paints the scene and shared bird renderer. GameScreen owns launch menu, character/difficulty selection, overlays, local preferences, sound effects, lifecycle pause and navigation.
- Characters: Sunny (golden default), Berry (purple with sparkle), Mint (teal with leaf), Ember (coral with crest). Each character has body/highlight/wing/tail/beak colors and a unique accessory. All share identical physics and collision hitbox.
- Launch menu: animated background/bird, 2x2 character picker with live previews, Chill/Classic/Wild difficulty picker, Start Flight, persistent sound toggle, How to Play and personal best.
- Persistence: best score, sound enabled state, selected difficulty, and selected character are stored locally in private SharedPreferences.
- Run flow: selected character and difficulty are applied before starting; the character and physics mode stay fixed during active/paused flight and one-time continue.
- Candidate version: 1.4 (versionCode 5) on feature/bird-character-selection-retry.
- External services: none. No network permission, analytics, account, payment or ad SDK.
- Distribution: no store upload/publication authorized; Play Console remains deferred by the user.
- Build: ./gradlew testDebugUnitTest lintDebug assembleDebug.
- Runtime tests: ./gradlew connectedDebugAndroidTest; remote validation uses Android API 36 emulator.
- Local execution limitation: this agent environment has no Android SDK, Gradle, adb or emulator; use GitHub Actions and inspect reports/artifacts.
