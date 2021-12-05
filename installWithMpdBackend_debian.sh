#!/usr/bin/env bash
set -eu

tasks/installDependenciesForMpdPlayback.sh
tasks/installYtDlp.sh
tasks/build.sh
tasks/setupMpdDefaultMusicLib.sh
tasks/writeConfigForMpdBackend.sh

echo "Finished with success."
