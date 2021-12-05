#!/usr/bin/env bash
set -eu

tasks/installDependenciesForVlcPlayback.sh
tasks/installYtDlp.sh
tasks/build.sh
tasks/checkConfigForVlcBackend.sh

echo "Finished with success."
