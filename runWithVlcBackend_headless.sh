#!/usr/bin/env bash
set -eu

# Make all background processes stop automatically, once this script finishes:
trap "exit" INT TERM
trap "kill 0" EXIT

vlc -I http --http-password 0000 &
mvn exec:java
