#!/usr/bin/env bash
set -eu

echo "Install dependencies to execute mvn install: maven, openjdk-16-jdk"
sudo apt install maven openjdk-16-jdk

echo "Build the app"
mvn install
