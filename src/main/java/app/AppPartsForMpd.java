package app;

import playback.INTERFACE_Playback;
import playback.mpd.MpcPlaybackInterface;
import storage.ISongStore;
import storage.SongStoreForMpd;
import utils.log.ILog;

public class AppPartsForMpd implements AppParts {

    public final SongStoreForMpd songStore;
    public final MpcPlaybackInterface playback;

    public AppPartsForMpd(
            final String scriptExecutionDir,
            final String musicLibDir,
            final String musicDlDir,
            final ILog log )
    {
        songStore = new SongStoreForMpd( musicLibDir, musicDlDir, log );
        playback = new MpcPlaybackInterface( scriptExecutionDir, songStore, log );
    }

    @Override
    public ISongStore getSongStore() {
        return songStore;
    }

    @Override
    public INTERFACE_Playback getPlaybackInterface() {
        return playback;
    }
}
