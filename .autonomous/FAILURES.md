# Failures and blockers

## Run 39350769848 — SDK license command
- Candidate: e3a1fad3122c658a9cbbcdde13accbf40186927c.
- Failed before compilation. The SDK license pipeline's yes process exited with a broken pipe under pipefail.
- Corrected with { yes || true; } | sdkmanager --licenses, preserving sdkmanager's exit code.

## Run 39350819155 — Android dependency metadata mismatch
- Candidate: d10353a681bf38a7746ba568a19611c81872b0d8.
- SDK installation succeeded, but build metadata validation failed because Compose BOM 2026.09.00 resolved components requiring AGP 9.1+ and compile API 37 while this project had AGP 8.13.2/API 36.
- Remediation selected the stable compatible project toolchain: AGP 8.13.2 / Gradle 8.13 / Kotlin 2.2.10 / API 36 / Compose BOM 2026.01.00.

## Run 39351434704 — Compose smoke-test source compile error
- Candidate: bc23082758cbb9b221d12e371ee995fa5cc6f241.
- SDK setup, JVM tests, lint and debug APK build passed; instrumented test sources did not compile because Compose assertion member methods were incorrectly imported as top-level functions.
- Fixed the imports and synchronized the Compose test clock in commit ac73c3a92cb8429d341df06c5649a36a68f0f93a.

## Run 39353520915 — Emulator unavailable
- Candidate: ac73c3a92cb8429d341df06c5649a36a68f0f93a.
- SDK provisioning, six JVM tests, lint, debug assembly and artifact upload completed successfully.
- Emulator logs explicitly reported that the runner user did not have permission to use /dev/kvm. ADB timed out querying the device; Compose instrumentation ended with "No compatible devices connected".
- This is an execution-environment failure, not a passing runtime test and not an observed app assertion failure.
- Remediation: explicitly grant the ephemeral hosted runner access to /dev/kvm, verify read/write permissions, configure a longer emulator boot timeout, then inspect the new run's real result.

## Release blocker
- No upload key or signing secrets have been created/provisioned. A signed release bundle must not be produced or certified before the owner creates a durable private backup of the upload key and configures the GitHub Actions secrets.
- Local Android execution is unavailable because the container has no Gradle executable, Android SDK, adb, emulator, or sdkmanager.


## Run 40199740196 — launch menu controls fell below the visible viewport

- Candidate: `a68cf028d000c5a78d22f6424622947434c8065b`.
- JVM tests, lint, debug build, and artifact upload passed. The emulator test report showed four UI assertion failures after the character picker was added. The menu controls existed in the semantics tree, but the longer scrollable menu put Start Flight below the visible area; gameplay-flow tests tapped it without first scrolling it into view, so the game remained on the menu. The system-Back test then triggered the Activity's default exit behavior because no run had started.
- Remediation: move the character selector and Start Flight action to the top of the launch card and remove the redundant difficulty-heading UI to compact the menu. The main-menu test now checks that the character choices and Start Flight are actually displayed; interaction tests use direct taps for this compact launch menu.
- Resolution (2026-10-25): PR run 40203219590 and post-merge main run 40203844680 both passed the full emulator suite. The earlier menu reachability failure is resolved for the tested API 36 emulator configuration; physical-device behavior remains unverified.


## Current failure status — v1.4
- The menu-reachability failure documented above is resolved by the tested layout fix.
- Current main validation run 40203844680 completed successfully, including all 9 emulator UI tests.
- No current CI failure is active. Physical-device testing is pending and is neither treated as a failure nor a pass.
