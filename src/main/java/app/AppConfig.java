package app;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:~/.config/partyPartyPlaylist/partyPartyPlaylistServer.config",
        "file:/etc/partyPartyPlaylist/partyPartyPlaylistServer.config"})
public interface AppConfig extends Config {
    // Usage hints:
    // http://owner.aeonbits.org/docs/usage/

    @DefaultValue("/var/log/partyPartyPlaylist/partyPartyPlaylistServer.log")
    String logFilePath();

    @DefaultValue("/tmp")
    String tmpDirectoryPath();

    @DefaultValue("/var/lib/mpd/music")
    String musicLibraryDirectoryPath();

    @DefaultValue("/var/lib/mpd/music/dl")
    String musicDownloadDirectoryPath();

    @DefaultValue("6646")
    int portListen();

}
