#!/usr/bin/env bash
set -eu

configDirPath="$HOME/.config/partyPartyPlaylist"
configFilePath="$configDirPath/p3Server.config"
expectedConfigLine="playbackInterface = mpd"
if [ -f "$configFilePath" ];then
	echo "Configuration file at $configFilePath already exists."
	backendLinePrefix="playbackInterface"
	set +e # Workaround ot make grep not crash the program when nothing is found
	greppedForBackendLine="$(grep "$backendLinePrefix" "$configFilePath")"
	set -e
	if [ "$greppedForBackendLine" = "$expectedConfigLine" ];then
		echo "Configuration file at $configFilePath seems to properly set the playback to mpd."
	else
		echo ""
		echo "### WARNING ###"
		echo "Configuration file at $configFilePath might not properly set the playback to mpd. Please review the file manually."
		echo "Hit [Enter] to continue."
		read
	fi
else
	echo "Creating minimal configuration file at $configFilePath."
	mkdir -p "$configDirPath"
	echo "$expectedConfigLine" >> "$configFilePath"
fi

