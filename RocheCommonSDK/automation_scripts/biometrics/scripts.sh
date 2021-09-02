#!/bin/bash

while true; do
  logs=$($ANDROID_SDK_ROOT/platform-tools/adb logcat -d -s FingerprintTestTag | grep "FingerprintTestTag")
  #echo "${logs}"

  if [[ "$logs" == *"Enroll Fingerprint" ]]; then
    echo "Enrolling fingerprint"
    sleep 1
    $ANDROID_SDK_ROOT/platform-tools/adb -e emu finger touch 1
    sleep 1
    $ANDROID_SDK_ROOT/platform-tools/adb -e emu finger touch 1
    sleep 1
    $ANDROID_SDK_ROOT/platform-tools/adb -e emu finger touch 1
  elif [[ "$logs" == *"Authenticate Fingerprint" ]]; then
    echo "Authenticating fingerprint"
    sleep 1
    $ANDROID_SDK_ROOT/platform-tools/adb -e emu finger touch 1
  elif [[ "$logs" == *"Authenticate Wrong Fingerprint" ]]; then
    echo "Authenticating Wrong fingerprint"
    sleep 1
    $ANDROID_SDK_ROOT/platform-tools/adb -e emu finger touch 2
  fi
  #echo "Sleeping for 2 seconds"
  sleep 2
done




