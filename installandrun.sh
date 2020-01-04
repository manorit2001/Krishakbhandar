#!/bin/bash
./gradlew assembleDebug && adb install -r -t ./app/build/outputs/apk/debug/app-debug.apk && adb shell monkey -p com.example.mapstest 1
