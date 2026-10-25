# Requirements and acceptance

| ID | Requirement | Acceptance evidence | Risk | Status |
|---|---|---|---|---|
| GAME-001 | Native portrait app opens to a usable main menu | Emulator UI test verifies title/start/sound controls | R2 | PASS on previous version 1.1 CI |
| GAME-002 | Start flight, tap-to-flap, moving pipes and score HUD | GameEngine tests plus emulator start flow | R2 | PARTIAL; game flow tested, visual gameplay needs real-device review |
| GAME-003 | Ceiling, ground and pipe collisions end the round | Deterministic JVM tests | R2 | PASS on previous version 1.1 CI |
| GAME-004 | Each pipe pass scores exactly once | Deterministic JVM test | R2 | PASS on previous version 1.1 CI |
| GAME-005 | Restart creates a fresh round and clears current score | JVM restart test; UI exposes restart actions | R2 | ENGINE PASS; continue to retain existing UI coverage |
| GAME-006 | Personal best is persisted locally | Code inspection; best score writes to private SharedPreferences | R2 | IMPLEMENTED; broader persistence review pending |
| GAME-007 | No network, account, analytics or sensitive permission is required | Manifest and dependency review | R1 | IMPLEMENTED; no ad SDK/network integration |
| GAME-008 | User can pause and resume without advancing physics while paused | JVM pause test and emulator pause/resume flow | R2 | PASS on previous version 1.1 CI |
| GAME-009 | Sound effects can be muted; choice persists | Emulator test toggles SharedPreferences-backed preference twice | R2 | PASS on previous version 1.1 CI |
| GAME-010 | Continue after game over preserves score and is available once per run | JVM continue tests and emulator continue flow | R2 | PASS on previous version 1.1 CI |
| GAME-011 | Menu sky and bird have subtle idle animation without gameplay updates | Canvas review plus READY-state emulator smoke | R2 | IMPLEMENTED; candidate CI pending |
| GAME-012 | How to Play explains tap, pipes, pause and continue; can be dismissed | Compose test howToPlayOverlayCanBeOpenedAndDismissed | R2 | IMPLEMENTED; candidate CI pending |
| GAME-013 | Android Back pauses active play, and Back from pause/game over returns to menu | Compose test systemBackPausesFlightAndReturnsToMenuFromPause | R2 | IMPLEMENTED; candidate CI pending |
| GAME-014 | Leaving the foreground pauses active play and returning requires Resume | Compose lifecycle test plus engine pause invariant | R2 | IMPLEMENTED; candidate CI pending |
| GAME-015 | Long menu and overlay content remains scrollable on short screens | Bounded scroll containers and emulator layout smoke | R2 | IMPLEMENTED; manual small-device review pending |
| ENG-001 | Reproducible Gradle build | Candidate CI run builds debug APK | R3 | PASS on previous version 1.1 CI |
| ENG-002 | Unit tests, lint and emulator UI tests run with retrievable evidence | GitHub Actions validation and uploaded artifact | R3 | Previous version 1.1 PASS; version 1.2 validation pending |
| ENG-003 | Repository memory records the current branch, tested commit, and remaining work | Review .autonomous files at each checkpoint | R2 | IN PROGRESS |
