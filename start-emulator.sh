#!/bin/bash

# Script to start Android emulator
# Usage: ./start-emulator.sh [AVD_NAME]

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Default AVD name
DEFAULT_AVD="Pixel_5"

# Get AVD name from parameter or use default
AVD_NAME="${1:-$DEFAULT_AVD}"

echo -e "${YELLOW}➡ Starting Android Emulator...${NC}"
echo -e "${YELLOW}- AVD: ${AVD_NAME}${NC}"

# Check if ANDROID_HOME is set
if [ -z "$ANDROID_HOME" ]; then
  echo -e "${RED}✖ ANDROID_HOME environment variable is not set${NC}"
  echo "Please set ANDROID_HOME to your Android SDK path"
  exit 1
fi

# Check if emulator executable exists
EMULATOR_PATH="$ANDROID_HOME/emulator/emulator"
if [ ! -f "$EMULATOR_PATH" ]; then
  echo -e "${RED}✖ Emulator executable not found at: $EMULATOR_PATH${NC}"
  echo "Please check your ANDROID_HOME path"
  exit 1
fi

# Check if emulator is already running
if adb devices | grep -q "emulator.*device"; then
  echo -e "${YELLOW}⚠ Emulator is already running${NC}"
  adb devices
  exit 0
fi

# List available AVDs
echo -e "${YELLOW}📌 Available AVDs:${NC}"
"$EMULATOR_PATH" -list-avds

# Check if specified AVD exists
if ! "$EMULATOR_PATH" -list-avds | grep -q "^$AVD_NAME$"; then
  echo -e "${RED}✖ AVD '$AVD_NAME' not found${NC}"
  echo "Available AVDs:"
  "$EMULATOR_PATH" -list-avds
  exit 1
fi

# Start the emulator with additional flags
echo -e "${GREEN}⚠ Starting emulator: $AVD_NAME${NC}"
"$EMULATOR_PATH" -avd "$AVD_NAME" -no-audio -no-boot-anim -no-snapshot-save -no-snapshot &

# Wait for emulator to start
echo -e "${YELLOW}⏳ Waiting for emulator to start...${NC}"
MAX_WAIT=120
WAIT_TIME=0

while [ $WAIT_TIME -lt $MAX_WAIT ]; do
  if adb devices | grep -q "emulator.*device"; then
    echo -e "${GREEN}🎉 Emulator started successfully!${NC}"
    adb devices
    exit 0
  fi

  sleep 5
  WAIT_TIME=$((WAIT_TIME + 5))
  echo -e "${YELLOW}⌛ Still waiting... (${WAIT_TIME}s)${NC}"
done

echo -e "${RED}✖ Emulator failed to start within ${MAX_WAIT} seconds${NC}"
exit 1


# Install Appium Settings APK
SETTINGS_APK="$HOME/.appium/node_modules/appium-viautomator2-driver/node_modules/io.appium.settings/apks/settings_apk
if [ -f "$SETTINGS_APK" ]; then
echo -e "${YELLOW© Installing Appium Settings APK…..${NC}"
adb -s emulator-5554 install -r "$SETTINGS_APK"

echo -e "${YELLOW} / Granting necessary permissions to Appium Settings...${NC}"
adb -s emulator-5554 shell pm grant io.appium.settings android.permission.WRITE_SECURE_SETTINGS
adb -s emulator-5554 shell pm grant io.appium.settings android.permission.SET_ANIMATION_SCALE
adb -s emulator-5554 shell pm grant io.appium.settings android.permission. CHANGE_CONFIGURATION
adb -s emulator-5554 shell pm grant io.appium.settings android.permission.ACCESS_FINE_LOCATION

echo -e "${GREEN] Appium Settings installed and permissions granted$-{NC)"
else
echo -e "${RED}X Appium Settings APK not found at $SETTINGS_APK${NC}"
fi

# Wait additional 5 seconds just to be safe
echo -e "SEVELLOW-& Waiting an eStra 5 seconds for stability...sNC?™
sleep 5