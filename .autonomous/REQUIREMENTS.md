# Requirements and acceptance

| ID | Requirement | Acceptance evidence | Risk | Status |
|---|---|---|---|---|
| GAME-001 | Animated launch menu provides Start Flight, help, mute, best score and difficulty controls | Emulator flow test | R2 | PASS v1.3 candidate |
| GAME-002 | Tap-to-flap, pipes, collisions and scoring work | GameEngine JVM tests plus emulator game flow | R2 | PASS v1.3 candidate |
| GAME-003 | Pause/resume, system Back and lifecycle auto-pause behave safely | Engine tests and emulator UI coverage | R2 | PASS v1.3 candidate |
| GAME-004 | Sound mute and difficulty preferences persist locally | Compose UI tests | R2 | PASS v1.3 candidate |
| GAME-005 | One-time continue preserves score and selected difficulty | Engine + emulator tests | R2 | PASS v1.3 candidate |
| GAME-006 | Chill, Classic and Wild change physics/gap geometry without mismatch | Engine tests and shared per-pipe gap geometry | R2 | PASS v1.3 candidate |
| GAME-007 | Player may select Sunny, Berry, Mint, or Ember at launch | UI test asserts all four character controls exist | R2 | IMPLEMENTED; v1.4 CI pending |
| GAME-008 | Characters have distinctive palettes and signature accessories | Shared Canvas renderer plus preview Canvas | R2 | IMPLEMENTED; v1.4 CI pending |
| GAME-009 | Character selection persists between app launches and selection visibly updates | Compose test checks selected label and SharedPreferences | R2 | IMPLEMENTED; v1.4 CI pending |
| GAME-010 | The chosen character is used in the flight and remains through continue | Engine tests check active selection lock and continue | R2 | IMPLEMENTED; v1.4 CI pending |
| GAME-011 | Character cosmetics do not change hitbox or difficulty | One shared physics engine and identical BIRD_RADIUS | R1 | IMPLEMENTED; v1.4 CI pending |
| GAME-012 | App requires no ad/network/account/store integration | Manifest/dependency review | R1 | No ads or network services added |
| ENG-001 | Reproducible Android build | GitHub Actions debug build | R3 | PASS v1.3 candidate; v1.4 pending |
| ENG-002 | Unit tests, lint and emulator tests run with retrievable evidence | Android validation workflow/artifact | R3 | v1.3 pass; v1.4 pending |
| ENG-003 | Persistent repository state describes actual candidate and evidence | Review .autonomous files | R2 | IN PROGRESS |
