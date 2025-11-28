#!/bin/bash

# Script to stop Android emulator

ANDROID_HOME="/Users/rustam/Library/Android/sdk"
export PATH="$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}⛔ Stopping Android Emulator...${NC}"

# Ensure adb is available
if ! command -v adb &> /dev/null; then
    echo -e "${RED}❌ adb command not found. Check ANDROID_HOME and PATH.${NC}"
    exit 1
fi

# Check emulator running
if ! adb devices | grep -q "emulator-.*device"; then
    echo -e "${YELLOW}No emulator is currently running.${NC}"
    exit 0
fi

# Get emulator device ID
EMULATOR_DEVICE=$(adb devices | grep "emulator-.*device" | awk '{print $1}')

if [ -z "$EMULATOR_DEVICE" ]; then
    echo -e "${YELLOW}No emulator device found.${NC}"
    exit 0
fi

echo -e "${YELLOW}📌 Found emulator: $EMULATOR_DEVICE${NC}"

# Stop emulator
echo -e "${GREEN}🛑 Stopping emulator...${NC}"
adb -s "$EMULATOR_DEVICE" emu kill

# Wait for shutdown
echo -e "${YELLOW}⏳ Waiting for emulator to stop...${NC}"
MAX_WAIT=30
WAIT_TIME=0

while [ $WAIT_TIME -lt $MAX_WAIT ]; do
    if ! adb devices | grep -q "emulator-"; then
        echo -e "${GREEN}🎉 Emulator stopped successfully!${NC}"
        exit 0
    fi
    sleep 2
    WAIT_TIME=$((WAIT_TIME + 2))
    echo -e "${YELLOW}⌛ Still stopping... (${WAIT_TIME}s)${NC}"
done

echo -e "${RED}❌ Timeout: emulator did not stop within ${MAX_WAIT}s${NC}"
echo -e "${YELLOW}➡ Try stopping manually from Android Studio${NC}"
exit 1