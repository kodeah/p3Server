#!/usr/bin/env bash
set -eu

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
	echo "Done setting up music library/download folder."
	echo ""
else
	echo ""
	echo "### PLEASE READ THE FOLLOWING ###"
	echo "Setup nearly finished. To make everything work, you should create a music download folder and make it writable by the user which you intend to run this program. For more information regarding the download folder, see the README file."
fi
