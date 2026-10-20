# Plan

1. Implement native gameplay, drawing, score HUD, best-score persistence, and restart.
2. Add deterministic JVM tests for game status, flap impulse, scoring, collisions and restart.
3. Add a Compose emulator smoke test for the start-to-game journey.
4. Run JUnit, Android Lint, debug assembly, and API 37 emulator tests through the public-repository CI route.
5. Inspect all result reports and the exact candidate/artifact identity; fix failures using bounded, evidence-driven changes.
6. Certify only a validated candidate. Store/Play release is out of scope until explicitly requested.
