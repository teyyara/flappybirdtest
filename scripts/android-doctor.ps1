$ErrorActionPreference = "Continue"
$Root = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$Java = Get-Command java -ErrorAction SilentlyContinue
$Adb = Get-Command adb -ErrorAction SilentlyContinue
$Emulator = Get-Command emulator -ErrorAction SilentlyContinue
$SdkManager = Get-Command sdkmanager -ErrorAction SilentlyContinue
$Sdk = $env:ANDROID_HOME
if (-not $Sdk) { $Sdk = $env:ANDROID_SDK_ROOT }
$SdkReady = [bool]($Sdk -and (Test-Path $Sdk))
$WrapperReady = Test-Path (Join-Path $Root "gradlew.bat")
Write-Output "Project: $Root"
Write-Output "OS: $([System.Environment]::OSVersion.VersionString); architecture: $env:PROCESSOR_ARCHITECTURE"
Write-Output "Java available: $([bool]$Java); Gradle wrapper present: $WrapperReady"
Write-Output "Android SDK available: $SdkReady; adb: $([bool]$Adb); emulator: $([bool]$Emulator); sdkmanager: $([bool]$SdkManager)"
if ($Java) { java -version 2>&1 }
if ($Adb) { adb devices -l }
$Report = [pscustomobject]@{
  os = "windows"; architecture = $env:PROCESSOR_ARCHITECTURE
  java_available = [bool]$Java; gradle_wrapper_present = [bool]$WrapperReady
  android_sdk_available = [bool]$SdkReady; adb_available = [bool]$Adb
  emulator_available = [bool]$Emulator; sdkmanager_available = [bool]$SdkManager
}
$Report | ConvertTo-Json -Compress
if ($Java -and $WrapperReady -and $SdkReady) { exit 0 }
if ($Java -and $WrapperReady) { exit 2 }
exit 3
