# Requirements and acceptance

| ID | Requirement | Acceptance evidence | Risk | Initial status |
|---|---|---|---|---|
| GAME-001 | Native portrait Android app launches to a playable start screen | Install/launch on emulator; UI report | R2 | NOT_RUN |
| GAME-002 | Tap starts game and flaps the bird; pipes scroll | Compose UI flow and gameplay inspection | R2 | NOT_RUN |
| GAME-003 | Ceiling, ground and pipe collisions end the round | GameEngine JVM tests; emulator flow | R2 | NOT_RUN |
| GAME-004 | Passing each pipe pair adds exactly one point | GameEngine score unit test | R2 | NOT_RUN |
| GAME-005 | Restart creates a fresh round and zeroes current score | GameEngine tests | R2 | NOT_RUN |
| GAME-006 | Best score is saved locally | Code inspection and emulator check | R2 | NOT_RUN |
| GAME-007 | No network, account, analytics, or sensitive permission is needed | Manifest/dependency review | R1 | IMPLEMENTED_NOT_SCANNED |
| ENG-001 | Reproducible Gradle wrapper and Android project build | Successful CI build for candidate SHA | R3 | NOT_RUN |
| ENG-002 | Unit tests, lint and emulator UI test run and evidence is retrievable | Actual workflow result and reports | R3 | NOT_RUN |
| ENG-003 | Repository can be resumed from memory files | Memory integrity/cold-start check | R2 | IN_PROGRESS |
