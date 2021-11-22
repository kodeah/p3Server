#!/usr/bin/env bash
set -eu

echo "Install dependencies to install and run youtube-dl: curl, python, ffmpeg"
sudo apt install curl python ffmpeg

# https://ytdl-org.github.io/youtube-dl/download.html
echo ""
echo "### USER INPUT REQUIRED ###"
echo "Install youtube-dl:"
echo "This will download the latest executable from yt-dl.org an place it into /usr/local/bin. Do not proceed if you do not trust youtube-dl or a curl-download via https. To cancel, hit [ctrl+c]. To proceed, hit [Enter]."
read # Wait for user input
sudo curl -L https://yt-dl.org/downloads/latest/youtube-dl -o /usr/local/bin/youtube-dl
sudo chmod a+rx /usr/local/bin/youtube-dl
