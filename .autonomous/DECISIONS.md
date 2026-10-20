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

## D-006: Align to current Compose requirements
The observed Compose BOM 2026.09.00 resolves Compose 1.12.1, which explicitly requires AGP 9.1+ and compile API 37. The toolchain has been aligned to AGP 9.1.1, Gradle 9.3.1, API 37, and AGP built-in Kotlin support using the official migration guidance.
