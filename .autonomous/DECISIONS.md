# Decisions

## D-001: Kotlin and Jetpack Compose
Compose owns the overlays and interaction; Canvas provides a lightweight custom-drawn arcade scene.

## D-002: No external game engine
The one-screen 2D game does not justify a large engine dependency. Kotlin frame-step physics is small, deterministic, and testable.

## D-003: Original art and no network services
Artwork is drawn from Compose Canvas paths and shapes. There is no network/account setup, sensitive permission, or recurring service cost.

## D-004: Separate GameEngine from rendering
Physics, pipe passing, score, and collision decisions remain testable on the JVM without emulator graphics.

## D-005: Hosted Actions emulator route
This session has no Android SDK/emulator/Gradle. The repository-backed Android 36 workflow must be inspected before validation can be claimed.
