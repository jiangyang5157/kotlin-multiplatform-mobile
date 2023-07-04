#!/bin/sh

rm -rf .idea
./gradlew clean
rm -rf .gradle
rm -rf build
rm -rf */build

rm -rf iosSudoku/iosSudoku.xcworkspace
rm -rf iosSudoku/iosSudoku.xcodeproj/project.xcworkspace
rm -rf iosSudoku/iosSudoku.xcodeproj/xcuserdata
