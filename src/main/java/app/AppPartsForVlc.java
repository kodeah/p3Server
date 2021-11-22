package app;

import playback.INTERFACE_Playback;
import playback.VlcPlayback;
import storage.ISongStore;
import storage.SongStoreForVlc;
import utils.WebRequestHelper;
import utils.id.NaiveIdGenerator;
import utils.log.ILog;

public class AppPartsForVlc implements AppParts {

    public final SongStoreForVlc songStore;
    public final VlcPlayback playbackInterface;

    public AppPartsForVlc(
            final WebRequestHelper webRequestHelper,
            final ILog log )
    {
        this.songStore = new SongStoreForVlc( new NaiveIdGenerator() );
        this.playbackInterface = new VlcPlayback( webRequestHelper, songStore, log );
    }

    @Override
    public ISongStore getSongStore() {
        return songStore;
    }

    @Override
    public INTERFACE_Playback getPlaybackInterface() {
        return playbackInterface;
    }
}
