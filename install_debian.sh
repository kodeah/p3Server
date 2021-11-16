#!/usr/bin/env bash

set -eu

# Install dependency debian packages:
echo "Install build dependencies: maven, openjdk-16-jdk, curl"
sudo apt install maven openjdk-16-jdk curl

echo "Install packages required to run: mpd, mpc"
sudo apt install mpd mpc

# Install youtube-dl:
# https://ytdl-org.github.io/youtube-dl/download.html
echo ""
echo "### USER INPUT REQUIRED ###"
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

# If mpd has default music directory configured, make it writable by all:
if [ $(grep ^music_directory /etc/mpd.conf | grep -F "/var/lib/mpd/music" | wc -l) == 1 ]; then
  echo ""
  echo "### USER INPUT REQUIRED ###"
	echo "Mpd's music library seems to be configured to be at /var/lib/mpd/music. To create music download folder at /var/lib/mpd/music/p3-cache and make it writable by all users (chmod 777), hit [Enter]. Otherwise, abort ([ctrl+c])."
	read # Wait for user input
	if [ ! -d /var/lib/mpd/music/p3-cache ]; then
		sudo mkdir /var/lib/mpd/music/p3-cache
		sudo chown mpd:audio /var/lib/mpd/music/p3-cache
	fi
	sudo chmod 777 /var/lib/mpd/music/p3-cache
else
  echo ""
  echo "### PLEASE READ THE FOLLOWING ###"
	echo "Setup nearly finished. To make everything work, you should create a music download folder and make it writable by the user which you intend to run this program. For more information regarding the download folder, see the README file."
fi

echo "Finished."
