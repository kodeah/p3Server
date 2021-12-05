#!/usr/bin/env bash
set -eu

echo "Install dependencies to install and run yt-dlp: curl, python, ffmpeg"
sudo apt install curl python ffmpeg

# https://ytdl-org.github.io/youtube-dl/download.html
echo ""
echo "### USER INPUT REQUIRED ###"
echo "Install yt-dlp:"
echo "This will download the latest yt-dlp executable from github an place it into /usr/local/bin. Do not proceed if you do not trust yt-dlp, github, or a curl-download via https. To cancel, hit [ctrl+c]. To proceed, hit [Enter]."
read # Wait for user input
sudo curl -L https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp -o /usr/local/bin/yt-dlp
sudo chmod a+rx /usr/local/bin/yt-dlp
