# Project: Sky Hopper

- Purpose: an offline-first, portrait Android arcade game based on one-tap flap-and-avoid mechanics.
- Package/application ID: com.teyyara.flappybirdtest.
- Stack: Kotlin, Jetpack Compose, Canvas; Android compile SDK 37, target SDK 36; minimum API 24.
- Build toolchain: Android Gradle Plugin 9.1.1, Gradle 9.3.1, JDK 17 and the AGP built-in Kotlin support. The Compose compiler plugin uses Kotlin 2.2.10.
- Architecture: GameEngine contains deterministic frame-step physics, pipe generation, score progression, and collision checks. GameCanvas paints original vector-like art. GameScreen owns Compose overlays and persists the best score with private SharedPreferences.
- External services: none. No network permissions, analytics, login, payments, or third-party runtime service.
- Distribution: debug APK only until release gates in STRATEGY.md have been met and the user explicitly accepts a release.
- Build: ./gradlew testDebugUnitTest lintDebug assembleDebug.
- Runtime test: ./gradlew connectedDebugAndroidTest on an authorized Android emulator/device; the repository Actions workflow uses Android API 37.
