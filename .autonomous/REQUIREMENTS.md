# Requirements and acceptance

| ID | Requirement | Acceptance evidence | Risk | Status |
|---|---|---|---|---|
| GAME-001 | Native portrait app opens to a usable main menu | Emulator UI test verifies title/start/sound controls | R2 | PASS on CI emulator |
| GAME-002 | Start flight, tap-to-flap, moving pipes and score HUD | GameEngine tests plus emulator start flow | R2 | PARTIAL; controls/start tested, full visual gameplay not manually reviewed in this session |
| GAME-003 | Ceiling, ground and pipe collisions end the round | Deterministic JVM tests | R2 | PASS in latest CI |
| GAME-004 | Each pipe pass scores exactly once | Deterministic JVM test | R2 | PASS in latest CI |
| GAME-005 | Restart creates a fresh round and clears current score | JVM restart test; UI exposes restart actions | R2 | ENGINE PASS; UI click path not yet separately asserted |
| GAME-006 | Personal best is persisted locally | Code inspection; best score writes to private SharedPreferences | R2 | IMPLEMENTED; full persistence UI test pending |
| GAME-007 | No network, account, analytics or sensitive permission is required | Manifest and dependency review | R1 | IMPLEMENTED; no ad SDK/network integration added |
| GAME-008 | User can pause and resume without advancing physics while paused | JVM pause test and emulator pause/resume flow | R2 | PASS in latest CI |
| GAME-009 | Sound effects can be muted; choice persists | Emulator test toggles SharedPreferences-backed preference twice | R2 | PASS in latest CI |
| GAME-010 | Continue after game over preserves score and is available once per run | JVM continue tests and emulator run-to-game-over/continue/run-to-game-over flow | R2 | PASS in latest CI |
| ENG-001 | Reproducible Gradle build | Candidate CI run builds debug APK | R3 | PASS in latest CI |
| ENG-002 | Unit tests, lint and emulator UI tests run with retrievable evidence | CI run 40192044874, test results and artifact | R3 | PASS; lint has 0 errors and 11 warnings |
| ENG-003 | Project can be resumed from persistent repository memory | Reconcile memory files after each checkpoint | R2 | IN PROGRESS; this checkpoint refreshes memory |
