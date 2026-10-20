# Plan

1. Implement native gameplay, drawing, score HUD, best-score persistence, and restart. (Complete.)
2. Add deterministic JVM tests for game status, flap impulse, scoring, collisions and restart. (Complete; inspect actual CI report.)
3. Add a Compose emulator smoke test for the start-to-game journey. (Authored; runtime pass is still unverified due hosted emulator/KVM permissions.)
4. Run JVM tests, Android Lint, debug assembly, and API 36 emulator tests through public-repository CI.
5. Apply the evidence-supported KVM permission remediation and inspect the run, reports and artifact identity.
6. Configure release signing to use environment-only credentials and add a manual signed Android App Bundle workflow. (Configured in source; awaiting test and user-owned secrets.)
7. Run the signed-release workflow only after the upload key and repository secrets are provisioned; inspect AAB signature and SHA-256 evidence.
8. Complete Play Console onboarding, store listing and declarations, internal testing, and final user acceptance before publication.
