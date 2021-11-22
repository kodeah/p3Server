package app;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:~/.config/partyPartyPlaylist/p3Server.config"})
public interface AppConfig extends Config {
    // Usage hints:
    // http://owner.aeonbits.org/docs/usage/

    @DefaultValue("/var/log/partyPartyPlaylist/p3Server.log")
    String logFilePath();

    @DefaultValue("/tmp")
    String tmpDirectoryPath();

    @DefaultValue("/var/lib/mpd/music")
    String mpdMusicLibraryDirectoryPath();

    @DefaultValue("/var/lib/mpd/music/p3-cache")
    String mpdMusicDownloadDirectoryPath();

    @DefaultValue("6646")
    int portListen();

}
