package tasks;

import playback.INTERFACE_Playback;
import storage.ISongStore;
import utils.log.ILog;
import youtubeDl.YoutubeDownloader;

public class DownloadAndEnqueueTaskFactory {

    private final ILog log;
    private final YoutubeDownloader youtubeDownloader;
    private final ISongStore downloadStore;
    private final INTERFACE_Playback playbackInterface;
    private final String downloadDirPath;

    public DownloadAndEnqueueTaskFactory(
            final ILog log,
            final String ytdlProgramName,
            final String tmpDirPath,
            final String downloadDirPath,
            final ISongStore downloadStore,
            final INTERFACE_Playback playbackInterface )
    {
        this.log = log;
        this.downloadStore = downloadStore;
        this.downloadDirPath = downloadDirPath;
        this.playbackInterface = playbackInterface;
        youtubeDownloader = new YoutubeDownloader( ytdlProgramName, tmpDirPath, downloadDirPath, log );
    }

    public Task create( final String downloadUrl ) {
        final Task t = new Task(
                "Download and enqueue: " + downloadUrl,
                statusStringUpdateDelegate -> {
                    log.log("Starting download and enqueue task.");
                    statusStringUpdateDelegate.updateStatusString("Downloading ...");
                    String songPath = youtubeDownloader
                            .downloadFromUrl( downloadUrl );
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
