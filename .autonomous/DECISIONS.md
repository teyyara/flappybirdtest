# Decisions

## D-001: Kotlin and Jetpack Compose
Compose owns the overlays and interaction; Canvas provides a lightweight custom-drawn arcade scene.

## D-002: No external game engine
The one-screen 2D game does not justify a large engine dependency. Kotlin frame-step physics is small, deterministic, and testable.

## D-003: Original art and no network services
Artwork is drawn from Compose Canvas paths and shapes. This avoids asset provenance problems, network/account setup, sensitive permissions, and recurring service costs.

## D-004: Separate GameEngine from rendering
Physics, pipe passing, score, and collision decisions remain testable on the JVM without emulator graphics.

## D-005: Hosted Actions emulator route
This session has no Android SDK/emulator/Gradle. The repository-backed emulator workflow is the validation route and must be inspected before any test is claimed as passing.

## D-006: Toolchain note superseded by the validated project configuration
An earlier exploratory note about Compose BOM 2026.09.00, AGP 9.1.1, Gradle 9.3.1 and API 37 does not describe the current main source. The validated v1.4 configuration is AGP 8.13.2, Gradle wrapper 8.13, Kotlin 2.2.10, Compose BOM 2026.01.00, and compile/target SDK 36, as declared in the build files and exercised successfully by main validation run 40203844680. Keep this as the current known-good setup; re-evaluate compatibility against official documentation before making a toolchain upgrade. Play Console submission remains deferred.
