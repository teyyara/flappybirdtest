# Requirements and acceptance

| ID | Requirement | Acceptance evidence | Risk | Status |
|---|---|---|---|---|
| GAME-001 | App opens to a usable animated menu | Emulator test checks title/start/sound/mode controls | R2 | PASS v1.2 candidate CI |
| GAME-002 | Tap-to-flap, pipes, collisions and scoring | Engine JVM tests plus emulator flow | R2 | PASS v1.2 candidate CI |
| GAME-003 | Pause/resume does not advance physics while paused | Engine JVM test + emulator pause/resume | R2 | PASS v1.2 candidate CI |
| GAME-004 | Sound can be muted and preference persists | Compose test + SharedPreferences assertion | R2 | PASS v1.2 candidate CI |
| GAME-005 | One free continue preserves score and can be used once per run | Engine and emulator tests | R2 | PASS v1.2 candidate CI |
| GAME-006 | How To Play, system Back and lifecycle auto-pause work | Emulator UI tests | R2 | PASS v1.2 candidate CI |
| GAME-007 | No ad SDK, account, analytics, network requirement or store upload | Dependency/manifest review | R1 | PASS; rewarded ads intentionally deferred |
| GAME-008 | Chill, Classic, Wild have different physics and obstacle geometry | Engine test checks speed, gap height, flap strength and valid gaps | R2 | IMPLEMENTED; v1.3 CI pending |
| GAME-009 | Selected mode persists across launches | Compose test checks saved preference | R2 | IMPLEMENTED; v1.3 CI pending |
| GAME-010 | Difficulty is locked for an active or paused flight | Engine state test | R2 | IMPLEMENTED; v1.3 CI pending |
| GAME-011 | Continuing after a crash keeps current mode and score | Engine test checks continued pipe geometry and preserved score | R2 | IMPLEMENTED; v1.3 CI pending |
| GAME-012 | The pipe art matches the gap geometry used by collision detection | Canvas rendering uses each pipe's stored gap height | R2 | IMPLEMENTED; v1.3 CI pending |
| ENG-001 | Reproducible Android build | GitHub Actions builds debug APK | R3 | PASS v1.2 candidate CI; v1.3 pending |
| ENG-002 | JVM tests, lint and API 36 emulator tests run with evidence | Android validation workflow and uploaded artifact | R3 | v1.2 candidate PASS; v1.3 pending |
| ENG-003 | Repository memory tracks branch, run evidence and remaining work | Review .autonomous files at each checkpoint | R2 | IN PROGRESS |
