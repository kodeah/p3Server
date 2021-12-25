
# = Introduction =

The p3Server (party party playlist server) is an extension to [MPD, the music player daemon](https://www.musicpd.org/), and/or the [VLC media player](https://www.videolan.org/). While MPD as well as VLC already are quite good applications for music playback, the p3Server adds the ability to **append music from soundcloud, youtube and other (music) streaming platforms to your play queue**.

This application is intended to be executed parallel to a running MPD or VLC instance.

At the moment, there is only an Android client available([gitlab](https://gitlab.com/kodeah/p3AndroidClient)/[github](https://github.com/kodeah/p3AndroidClient)). However, you can use any HTTP client (httpie, curl, ...) to talk to the web interface (see below). Also feel free to write your own clients. I plan to release a desktop client as well soon.


# = Build / Installation =

## Easy Setup

To setup the environment, install dependencies and build the project, you can simply run one (or both) of the following scripts. Which you use depends on if you want to use MPD/mpc or VLC as your playback backend. The default is VLC:

* ``installWithVlcBackend_debian.sh``
* ``installWithMpdBackend_debian.sh``

Note that these scripts execute commands with sudo as well as download and install yt-dlp, which is **NOT part of the official debian repositories**. Only do this at your own risk!

## Run the Application

If you use VLC, you can either:
* Execute the ``runWithVlcBackend_headless.sh`` script, if you do not want VLC to open its graphical user interface.
* Execute the ``runWithVlcBackend_withGui.sh`` script, if you want to have the VLC GUI available.
However, these scripts only work when you use the default confiiguration values for the VLC http interface port and password.

If you use MPD instead, or already have a running VLC instance whith properly configured HTTP interface, you can directly start the application running: ``mvn exec:java``

## Info: Prerequisites

This program is for Linux only. It has been tested on Debian as well as Raspberry Pi OS.

Java 16 is required to build and run this application.

In addition, the following programs are required for this application to work properly:
* Either [MPD](https://www.musicpd.org/download.html) and [mpc](https://www.musicpd.org/clients/mpc/)
* Or [VLC](https://www.videolan.org/)
* [yt-dlp](https://github.com/yt-dlp/yt-dlp)

yt-dlp (and, if using the MPD/mpc backend, also mpc) has to be accessible via your PATH variable.

Note that further dependencies are downloaded via maven on building the application.

## Info: Build using Maven

To build this app, run ``mvn install``. A runnable jar file is created in the target directory. You can execute this file using ``java -jar target/playlistServer-0.0.1-SNAPSHOT-jar-with-dependencies.jar``, or instead simply run:  
``mvn exec:java``

## Info: Logging

Logs are written into ``<home>/.log/partyPartyPlaylist/p3Server.log`` by default. The logfile path is configurable, see below.


# = Configuration =

You can create a custom configuration file at the following path:  
``~/.config/partyPartyPlaylist/p3Server.config``

Entries in this file overwrite the default values.

## Available Configuration Keys and Default Values

To change any of these values, simply create a custom configuration file at one of the paths stated above and add key-value pairs for all values that you want to overwrite.  
Note that all configured paths that do not start with "/" are treated as paths inside your user home directory.

General configuration:  
``portListen = 6646``  
``logFilePath = .log/partyPartyPlaylist/p3Server.log``  
``tmpDirectoryPath = /tmp``

``playbackInterface = vlc`` (possible values are "vlc" and "mpd"; Depending on which one you set, from the following values only the ones prefixed with mpd/vlc are used.)  

VLC specific configuration:  
``vlcMusicDownloadDirectoryPath = Downloads/p3-cache``  
``vlcHttpInterfaceHost = localhost`` (Can be a hostname or IP-address; Do NOT prefix with "http://" or similar.)  
``vlcHttpInterfacePort = 8080`` (VLC default)  
``vlcHttpInterfacePassword = 0000``  
Note that you have to configure the http interface in the VLC settings manually, if you do not use one of the ``runWithVlcBackend...`` scripts from this repository.

MPD specific configuration:  
``mpdMusicLibraryDirectoryPath = /var/lib/mpd/music``  
``mpdMusicLibraryDirectoryPath = /var/lib/mpd/music/p3-cache``


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

``GET /info/playlist`` *(Deprecated)*  
Returns a string containing the current play queue.


# = Testing / Example Usage =

To test the functionality of your installation, run for example the commands below. For that, [httpie](https://httpie.io/) has to be installed on your machine (``sudo apt install httpie``). 

``http GET localhost:6646/info/playlist``  
``http POST localhost:6646/commands/skip``  
``echo https://soundcloud.com/vague003/bobby-raps-and-corbin-welcome-to-the-hell-zone-vague003-remix | http POST localhost:6646/commands/downloadAndEnqueue``


# = Regarding Security =

This application does not support https, and thus should only be run in the local (trusted) network.

Remote code execution might be possible by exploiting the download-and-enqueue command. At least I did not particularly take care that it is not. So once again, TAKE CARE THAT THIS SERVER IS NOT REACHABLE FROM AN UNTRUSTED NETWORK.
