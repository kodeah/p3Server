
# = Introduction =

The partyPartyPlaylistServer is an extension to [MPD, the music player daemon](https://www.musicpd.org/), a "flexible, powerful, server-side application for playing music". While MPD is a quite good application for local music playback, the partyPartyPlaylistServer adds the ability to **append music from soundcloud, youtube and other (music) streaming platforms to your play queue**.

This application is intended to be executed parallel to a running MPD instance.

Note that there is no client available yet. However, you can use any HTTP client (httpie, curl, ...) to talk to the web interface (see below). Also feel free to write your own clients.

(I plan to release a desktop client as well as an Android application soon.)

# = Build / Installation =

## Easy Setup

To install the prerequisites, build the project, as well as create a directory for logfiles inside ``/var/log``, you can simply run the ``installDebian.sh`` script. Note that it executes commands with sudo as well as downloads youtube-dl from the official youtube-dl site, which is **NOT part of the official debian repositories**. Only do this at your own risk!

Now you can use ``mvn exec:java`` to start the application.

## Info: Prerequisites

This program is for Linux only. It has been tested on Debian as well as Raspberry Pi OS.

Java 16 is required to build and run this application.

In addition, the following programs are required for this application to work properly:
* [MPD](https://www.musicpd.org/download.html)
* [mpc](https://www.musicpd.org/clients/mpc/)
* [youtube-dl](https://ytdl-org.github.io/youtube-dl/)

Both mpc and youtube-dl need to be accessible via your PATH variable.

Note that further dependencies are downloaded via maven on building the application.

## Info: Build using Maven

To build this app, run ``mvn install``. A runnable jar file is created in the target directory. You can execute this file using ``java -jar target/playlistServer-0.0.1-SNAPSHOT-jar-with-dependencies.jar``, or instead simply run:  
``mvn exec:java``

## Info: Logging

Logs are written into ``/var/log/partyPartyPlaylist/partyPartyPlaylistServer.log`` by default. The directory has to exist and be writable by the user running this application, otherwise the application might crash. The logfile path is configurable, see below.

# = Configuration =

You can create a custom configuration file at one or both of the following paths:  
``~/.config/partyPartyPlaylist/partyPartyPlaylistServer.config``  
``/etc/partyPartyPlaylist/partyPartyPlaylistServer.config``

If you create both files, the values in the user configuration (first path) have a higher priority than the ones in the global configuration (second path).

Note that no configuration file is created automatically.

## Available Configuration Keys And Default Values

``logFilePath=/var/log/partyPartyPlaylist/partyPartyPlaylistServer.log``  
``tmpDirectoryPath=/tmp``  
``musicLibraryDirectoryPath=/var/lib/mpd/music``  
``musicDownloadDirectoryPath=/var/lib/mpd/music/dl``  
``portListen=6646``

To change any of these values, simply create a custom configuration file at one of the paths stated above and add key-value pairs for all values that you want to overwrite.


# = The Web Interface =

To talk to this server application, use the web interface, which receives commands via HTTP. 

## Available Endpoints

``POST /commands/downloadAndEnqueue <URL-to-song>`` (The URL has to be sent as raw data, not a named parameter.)  
The song is downloaded and added to the MPD play queue. It is added to be played as next song right after the download has finished.

``POST /commands/autoplayOn``  
Tell MPD to start/continue playback, using the consume mode.

``POST /commands/autoplayOff``  
Tell MPD to pause playback.

``POST /commands/autoplayToggle``  
Toggles autoplay using mpc's toggle command.

``POST /commands/pullup``  
Stops and then starts playback again, resulting in the current song being played again from its beginning.

``POST /commands/skip``  
The currently playing song is skipped.

``GET /info/playlist``  
Returns a string containing the current play queue.

# Example usage with httpie:

``http GET localhost:6646/info/playlist``  
``http POST localhost:6646/commands/skip``  
``echo https://soundcloud.com/vague003/bobby-raps-and-corbin-welcome-to-the-hell-zone-vague003-remix | http POST localhost:6646/commands/downloadAndEnqueue``
