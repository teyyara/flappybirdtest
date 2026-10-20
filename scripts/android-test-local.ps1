$ErrorActionPreference = "Continue"
$Root = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
Set-Location $Root
$Stamp = (Get-Date).ToUniversalTime().ToString("yyyyMMddTHHmmssZ")
$Out = if ($env:EVIDENCE_DIR) { $env:EVIDENCE_DIR } else { Join-Path $Root "evidence/local/$Stamp" }
New-Item -ItemType Directory -Force -Path $Out | Out-Null
"candidate_commit=$((git rev-parse HEAD 2>$null) -join '')" | Out-File (Join-Path $Out "manifest.txt")
"started_utc=$((Get-Date).ToUniversalTime().ToString('o'))" | Add-Content (Join-Path $Out "manifest.txt")
function Invoke-Task([string]$Name, [string]$Command, [string[]]$Arguments) {
  $Log = Join-Path $Out "$Name.log"
  "$Command $($Arguments -join ' ')" | Out-File $Log
  & $Command @Arguments 2>&1 | Add-Content $Log
  $Code = $LASTEXITCODE
  "$($Name)_exit=$Code" | Add-Content (Join-Path $Out "manifest.txt")
  if ($Code -ne 0) { "$Name=FAIL" | Add-Content (Join-Path $Out "status.txt"); return $false }
  "$Name=PASS" | Add-Content (Join-Path $Out "status.txt"); return $true
}
if (-not (Invoke-Task "unit_tests" ".\gradlew.bat" @("--no-daemon", "testDebugUnitTest"))) { exit 1 }
if (-not (Invoke-Task "lint" ".\gradlew.bat" @("--no-daemon", "lintDebug"))) { exit 1 }
if (-not (Invoke-Task "debug_apk" ".\gradlew.bat" @("--no-daemon", "assembleDebug"))) { exit 1 }
$Devices = if (Get-Command adb -ErrorAction SilentlyContinue) { adb devices } else { @() }
if ($Devices -match "[0-9A-Za-z._:-]+\s+device") {
  if (-not (Invoke-Task "instrumented_ui_tests" ".\gradlew.bat" @("--no-daemon", "connectedDebugAndroidTest"))) { exit 1 }
} else { "instrumented_ui_tests=NOT_RUN_NO_ONLINE_DEVICE" | Add-Content (Join-Path $Out "status.txt") }
"finished_utc=$((Get-Date).ToUniversalTime().ToString('o'))" | Add-Content (Join-Path $Out "manifest.txt")
