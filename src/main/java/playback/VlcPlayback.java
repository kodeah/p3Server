package playback;

import storage.ISongStore;
import utils.WebRequestHelper;
import utils.log.ILog;

public class VlcPlayback implements INTERFACE_Playback {

    private final WebRequestHelper webRequestHelper;
    private final ISongStore songStore;
    private final ILog log;

    public VlcPlayback(
            final WebRequestHelper webRequestHelper,
            final ISongStore songStore,
            final ILog log )
    {
        this.webRequestHelper = webRequestHelper;
        this.songStore = songStore;
        this.log = log;
    }

    @Override
    public void verifyIsUp() throws Exception {
        log.log("Check if VLC is up and reachable via http ...");
        try {
            getStatusString();
        } catch (Exception e) {
            log.log("VLC is not up or not reachable:");
            throw e;
        }
        log.log("VLC is up and reachable.");
    }

    @Override
    public void toggleAutoplay( final boolean enable ) throws Exception {
        if (enable) {
            webRequestHelper.get("/requests/status.xml?command=pl_play");
            webRequestHelper.get("/requests/status.xml?command=pl_loop");
        } else {
            webRequestHelper.get("/requests/status.xml?command=pl_pause");
        }
    }

    @Override
    public void toggleAutoplay() throws Exception {
        throw new Exception("VlcPlayback - toggleAutoplay: Not implemented.");
        // TODO implement
    }

    @Override
    public void skip() throws Exception {
        webRequestHelper.get("/requests/status.xml?command=pl_next");
    }

    @Override
    public void pullup() throws Exception {
        webRequestHelper.get("/requests/status.xml?command=pl_stop");
        webRequestHelper.get("/requests/status.xml?command=pl_play");
    }

    @Override
    public void insertSongLast( final Long idSong ) throws Exception {
        String songPath = songStore.getSongPath( idSong );
        log.log("Adding local file: " + songPath);
        webRequestHelper.get("/requests/status.xml?command=in_enqueue&input=" + songPath);
    }

    @Override
    public void insertSongNext(Long idSong) throws Exception {
        log.warn("VlcPlayback - insertSongNext: Not implemented. Using insertSongLast instead.");
        insertSongLast(idSong);
    }

    @Override
    public String getPlaylistDescriptionString()
            throws Exception
    {
        return webRequestHelper.get("/requests/playlist.xml");
    }


    public String getStatusString()
            throws Exception
    {
        return webRequestHelper.get("/requests/status.xml");
    }

}
