#!/usr/bin/env bash
set -eu

tasks/setupEnvironmentForVlcPlayback.sh
tasks/installYoutubeDl.sh
tasks/build.sh

echo "Finished with success."
