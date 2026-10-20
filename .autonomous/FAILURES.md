# Failures and blockers

## Run 39350769848 — SDK license command
- Candidate: e3a1fad3122c658a9cbbcdde13accbf40186927c.
- Failed in Android SDK provisioning before compiling. `yes` exited with a broken pipe after sdkmanager stopped reading, and `pipefail` propagated that exit status.
- Corrected with `{ yes || true; } | sdkmanager --licenses`, preserving sdkmanager's own exit code.

## Run 39350819155 — Android dependency metadata mismatch
- Candidate: d10353a681bf38a7746ba568a19611c81872b0d8.
- SDK installation succeeded, but `testDebugUnitTest lintDebug assembleDebug` stopped at Android dependency metadata validation.
- Compose BOM 2026.09.00 resolves Compose 1.12.1 components requiring AGP 9.1+ and compile API 37; the project then used AGP 8.13.2/API 36.
- Remediation: update to AGP 9.1.1 / Gradle 9.3.1, use AGP's built-in Kotlin instead of the incompatible kotlin-android plugin, and compile/test in an API 37 emulator.
- Source compile, tests and runtime behavior remain unverified until the new run produces accessible results.

## Environment
- Local Android execution is unavailable because the container has no installed Gradle executable, Android SDK, adb, emulator, or sdkmanager. This is not evidence of an application defect.
