package tasks;

import playback.INTERFACE_Playback;
import storage.SongStore;
import utils.log.ILog;
import youtubeDl.YoutubeDownloader;

public class DownloadAndEnqueueTaskFactory {

    private final ILog log;
    private final YoutubeDownloader youtubeDownloader;
    private final SongStore downloadStore;
    private final INTERFACE_Playback playbackInterface;

    public DownloadAndEnqueueTaskFactory(
            final ILog log,
            final String tmpDirPath,
            final SongStore downloadStore,
            final INTERFACE_Playback playbackInterface )
    {
        this.log = log;
        this.downloadStore = downloadStore;
        this.playbackInterface = playbackInterface;
        youtubeDownloader = new YoutubeDownloader(tmpDirPath, log);
    }

    public Task create( final String downloadUrl ) {
        final Task t = new Task(
                "Download and enqueue: " + downloadUrl,
                statusStringUpdateDelegate -> {
                    log.log("Starting download and enqueue task.");
                    statusStringUpdateDelegate.updateStatusString("Downloading ...");
                    String songPath = youtubeDownloader
                            .downloadFromUrl(downloadUrl, downloadStore.getStorageDirPath());
                    log.log("Downloaded song. Adding it to store and enqueuing now. Song path: " + songPath);

                    statusStringUpdateDelegate.updateStatusString("Adding song to song store ...");
                    Long songId = downloadStore.addSong(songPath);

                    statusStringUpdateDelegate.updateStatusString("Inserting song to list ...");
                    playbackInterface.insertSongNext(songId);

                    return null;
                    },
                log);
        return t;
    }

}
