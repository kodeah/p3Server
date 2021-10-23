#!/usr/bin/env bash

# Install dependency debian packages:
sudo apt install mpd mpc openjdk-16-jdk

# Install youtube-dl:
# https://ytdl-org.github.io/youtube-dl/download.html
sudo curl -L https://yt-dl.org/downloads/latest/youtube-dl -o /usr/local/bin/youtube-dl
sudo chmod a+rx /usr/local/bin/youtube-dl

# Build the app:
mvn install

# Create default directory for log files:
logDir="/var/log/partyPartyPlaylist/"
sudo mkdir "$logDir"
currentUserName="$(whoami)"
sudo chown "$currentUserName:$currentUserName" "$logDir"
