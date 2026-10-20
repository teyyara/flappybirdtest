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
