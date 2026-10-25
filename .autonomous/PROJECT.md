# Project: Sky Hopper

- Purpose: offline-first portrait Android arcade game with one-tap flap-and-avoid gameplay.
- Package/application ID: com.teyyara.flappybirdtest.
- Stack: Kotlin, Jetpack Compose, Canvas; compile/target SDK 36; minimum API 24.
- Build toolchain: Android Gradle Plugin 8.13.2, Gradle 8.13, Kotlin 2.2.10, Compose BOM 2026.01.00, JDK 17 in CI.
- Architecture: GameEngine owns frame-step physics, pipes, score, collision rules, difficulty presets, and selected bird. GameCanvas draws the world and the selected bird using the shared Canvas character renderer. GameScreen owns menu/overlays, local preferences, sound effects, lifecycle pause, navigation, difficulty selection, and character selection.
- Main menu: animated sky/bird, four selectable character previews, Start Flight, saved mute toggle, personal best, How to Play, and Chill / Classic / Wild difficulty.
- Characters: Sunny (gold, classic), Berry (purple with sparkle), Mint (teal with leaf), and Ember (coral with crest). They are cosmetics; all use the same physics and hitbox.
- Character choice is persisted in private SharedPreferences, reflected in the selected label and menu preview, used by the gameplay renderer, and retained through pause/restart/game-over continue flows.
- Candidate version: 1.4 (versionCode 5) on feature/bird-character-selection-final.
- External services: none. No network permission, analytics, account, payments, or ad SDK.
- Distribution: no store upload/publication authorized; the user explicitly deferred Play Console work.
- Build: ./gradlew testDebugUnitTest lintDebug assembleDebug.
- Runtime tests: ./gradlew connectedDebugAndroidTest; remote validation uses Android API 36 emulator.
- Local execution limitation: this agent environment has no Android SDK, Gradle, adb, or emulator; use remote CI and inspect its reports/artifacts.
