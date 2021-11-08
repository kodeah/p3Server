#!/usr/bin/env bash

set -eu

# Install dependency debian packages:
echo "Install build dependencies: maven, openjdk-16-jdk, curl"
sudo apt install maven openjdk-16-jdk curl

echo "Install packages required to run: mpd, mpc"
sudo apt install mpd mpc

# Install youtube-dl:
# https://ytdl-org.github.io/youtube-dl/download.html
echo "Install youtube-dl:"
echo "This will download the latest executable from yt-dl.org an place it into /usr/local/bin. Do not proceed if you do not trust youtube-dl or a curl-download via https. To cancel, hit [ctrl+c]. To proceed, hit [Enter]."
read # Wait for user input
sudo curl -L https://yt-dl.org/downloads/latest/youtube-dl -o /usr/local/bin/youtube-dl
sudo chmod a+rx /usr/local/bin/youtube-dl

# Build the app:
echo "Build the app ..."
mvn install

# Create default directory for log files:
echo "Create log file directory at /var/log/partyPartyPlaylist/ ..."
logDir="/var/log/partyPartyPlaylist/"
sudo mkdir -p "$logDir"
currentUserName="$(whoami)"
sudo chown "$currentUserName:$currentUserName" "$logDir"

echo "Finished."
