# Evidence index

## Verified
- CI run 39351434704, candidate bc23082758cbb9b221d12e371ee995fa5cc6f241: SDK provisioning succeeded; six JVM game-engine tests passed; lint completed with 0 errors and 10 warnings; debug APK was built and artifact uploaded. The emulator test source failed compilation in this run.
- CI run 39353520915, candidate ac73c3a92cb8429d341df06c5649a36a68f0f93a: SDK provisioning, JVM tests, lint, debug APK assembly, and evidence artifact upload succeeded. Emulator smoke test did NOT pass: the hosted runner lacked /dev/kvm permissions and no compatible device was connected.
- Debug APK SHA-256 from run 39351434704: d6dc1cca64c74c849774d008529b2e612200bd3b6efb25639b5e4936426c8061.

## Pending
- Verify the CI run triggered by the explicit KVM permission change.
- Inspect JUnit XML, lint report, emulator instrumentation results, APK/AAB digest and exact commit before changing evidence status.
- Signed Play AAB does not exist yet; owner upload key and Actions secrets are required first.
