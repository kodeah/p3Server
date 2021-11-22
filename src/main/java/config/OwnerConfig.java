package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:~/.config/partyPartyPlaylist/p3Server.config"})
public interface OwnerConfig extends Config {
    // Usage hints:
    // http://owner.aeonbits.org/docs/usage/

    // Note that parts which do not start with "/" are treated as local paths inside the home directory of the user
    // that runs this application.
    // Example:
    //  "/tmp/xyz" is also treated as "/tmp/xyz"
    //  "tmp/xyz" is treated as "/home/<username>/tmp/xyz"

    // Global configuration:
    @DefaultValue("6646")
    int portListen();
    @DefaultValue(".log/partyPartyPlaylist/p3Server.log")
    String logFilePath();
    @DefaultValue("/tmp")
    String tmpDirectoryPath();

    // Select playback interface
    @DefaultValue("vlc")
    String playbackInterface();
        // Possible values are: "mpd", "vlc"
        // Depending on which one you set, from the following values only the ones prefixed with mpd/vlc are used

    // VLC specific configuration:
    @DefaultValue("Downloads/p3-cache")
    String vlcMusicDownloadDirectoryPath();
    @DefaultValue("localhost")
    String vlcHttpInterfaceHost();
    @DefaultValue("8080")
    int vlcHttpInterfacePort();
    @DefaultValue("0000")
    String vlcHttpInterfacePassword();

    // MPD specific configuration:
    @DefaultValue("/var/lib/mpd/music")
    String mpdMusicLibraryDirectoryPath();
    @DefaultValue("/var/lib/mpd/music/p3-cache")
    String mpdMusicDownloadDirectoryPath();

}
