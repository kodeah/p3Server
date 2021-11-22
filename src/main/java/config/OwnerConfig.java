package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:~/.config/partyPartyPlaylist/p3Server.config"})
public interface OwnerConfig extends Config {
    // Usage hints:
    // http://owner.aeonbits.org/docs/usage/

    // Global configuration:
    @DefaultValue("6646")
    int portListen();
    @DefaultValue("/var/log/partyPartyPlaylist/p3Server.log")
    String logFilePath();
    @DefaultValue("/tmp")
    String tmpDirectoryPath();

    // MPD specific configuration:
    @DefaultValue("/var/lib/mpd/music")
    String mpdMusicLibraryDirectoryPath();
    @DefaultValue("/var/lib/mpd/music/p3-cache")
    String mpdMusicDownloadDirectoryPath();

}
