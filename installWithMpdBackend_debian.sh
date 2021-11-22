#!/usr/bin/env bash
set -eu

tasks/setupEnvironmentForMpdPlayback.sh
tasks/installYoutubeDl.sh
tasks/build.sh
tasks/setupMpdDefaultMusicLib.sh

echo "Finished with success."
