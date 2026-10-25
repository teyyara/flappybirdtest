# Requirements and acceptance

| ID | Requirement | Acceptance evidence | Risk | Status |
|---|---|---|---|---|
| GAME-001 | Launch menu presents Start Flight, help, mute, best score, and difficulty controls | Compose emulator UI test | R2 | PASS — v1.4 main run 40203844680
| GAME-002 | Tap-to-flap, pipes, collisions, and scoring work | GameEngine JVM tests plus emulator flow | R2 | PASS — v1.4 main run 40203844680
| GAME-003 | Pause/resume, system Back, and lifecycle auto-pause behave safely | Engine tests and emulator UI coverage | R2 | PASS — v1.4 main run 40203844680
| GAME-004 | Sound mute and difficulty preferences persist locally | Compose UI tests and SharedPreferences assertions | R2 | PASS — v1.4 main run 40203844680
| GAME-005 | One-time continue preserves score and selected difficulty | Engine and emulator tests | R2 | PASS — v1.4 main run 40203844680
| GAME-006 | Chill, Classic, and Wild change physics/gap geometry without mismatch | Engine tests and shared per-pipe gap geometry | R2 | v1.3 PASS |
| GAME-007 | Player can choose Sunny, Berry, Mint, or Ember on the launch menu | UI test asserts all four character controls are displayed | R2 | PASS — main run 40203844680 |
| GAME-008 | Characters have distinctive palettes and signature accessories | Shared Canvas renderer plus preview Canvas | R2 | PASS — main run 40203844680 |
| GAME-009 | Character choice is saved and the selected label updates | Compose test checks selected label and SharedPreferences | R2 | PASS — main run 40203844680 |
| GAME-010 | Selected bird appears in-flight and is retained through a one-time continue | GameEngine tests check selection lock and continue | R2 | PASS — main run 40203844680 |
| GAME-011 | Character cosmetics do not change hitbox, difficulty, or physics | One shared engine and identical BIRD_RADIUS | R1 | PASS — main run 40203844680 |
| GAME-012 | Character picker and Start Flight are displayed immediately at launch | Compose test uses assertIsDisplayed for character choices and Start Flight | R2 | PASS — main run 40203844680 |
| GAME-013 | App requires no ad SDK, account, analytics, or network service | Manifest/dependency review | R1 | No ads/network services added; Play Console deferred |
| ENG-001 | Reproducible Android build | GitHub Actions debug build | R3 | PASS — main run 40203844680 |
| ENG-002 | JVM tests, lint, and emulator tests produce retrievable evidence | Android validation workflow and artifact | R3 | PASS — main run 40203844680 |
| ENG-003 | Project memory describes tested source, failures, and remaining work accurately | Review .autonomous files at each checkpoint | R2 | PASS — project memory reconciled against run 40203844680 |
