package app;

import playback.INTERFACE_Playback;
import playback.mpd.PLAYBACK_Mpd;
import storage.SongStore;
import utils.log.ILog;
import youtubeDl.YoutubeDownloader;
import utils.id.IdGenerator;
import utils.id.NaiveIdGenerator;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskInterface {

	private final ILog log;
	private final String dlFinalOutputDir;

	private final INTERFACE_Playback defaultPlaybackInterface;

	private YoutubeDownloader youtubeDownloader;
	private final IdGenerator defaultIdGenerator;
		//To assign unique IDs to the songs as long as the service runs.
	private final SongStore downloadStore;
	
	private final ExecutorService executor; //To schedule download+enqueue tasks.


	private void assertMusicLibraryDirExists( final String musicLibPath ) {
		File f = new File(musicLibPath);
		if (!f.isDirectory()) {
			log.error("Expected music library at '" + musicLibPath
					+ "', but there is no directory!");
			throw new RuntimeException();
		}
	}
	private void assertMusicDlDirExists( final String dlDirPath ) {
		File f = new File(dlDirPath);
		if (!f.isDirectory()) {
			log.error("Expected music download directory at '" + dlDirPath
					+ "', but there is no directory!");
			throw new RuntimeException();
		}
	}

	public TaskInterface(
			AppConfig config,
			final ILog log )
	{
		this.log = log;
		assertMusicLibraryDirExists(config.musicLibraryDirectoryPath());
		this.dlFinalOutputDir = config.musicDownloadDirectoryPath();
		assertMusicDlDirExists(dlFinalOutputDir);
		
		executor = Executors.newFixedThreadPool(5);
		defaultIdGenerator = new NaiveIdGenerator();
		downloadStore = new SongStore(config.musicDownloadDirectoryPath(), defaultIdGenerator);
		youtubeDownloader = new YoutubeDownloader(config.tmpDirectoryPath(), log);
		defaultPlaybackInterface = new PLAYBACK_Mpd(config.tmpDirectoryPath(), downloadStore, log);
	}

	
	public void downloadAndEnqueue(final String downloadUrl) {
		executor.submit(() -> {
			log.log("Starting download and enqueue task.");
			String songPath = youtubeDownloader
					.downloadFromUrl(downloadUrl, dlFinalOutputDir);
			log.log("Downloaded song. Adding it to store and enqueuing now.");
			Long songId = downloadStore.addSong(songPath);

			defaultPlaybackInterface.insertSongNext(songId);
		});
	}

	public void autoplayOn() {
		defaultPlaybackInterface.toggleAutoplay(true);
	}

	public void autoplayOff() {
		defaultPlaybackInterface.toggleAutoplay(false);
	}

	public void toggleAutoplay() {
		defaultPlaybackInterface.toggleAutoplay();
	}

	public void registerAndEnqueueLocalSong(String localSongPath) {
		log.log("Adding local song to store and enqueuing now.");
		Long songId = downloadStore.addSong(localSongPath);
		defaultPlaybackInterface.insertSongNext(songId);
	}
	
	public String getPlaylistString() {
		return defaultPlaybackInterface.getPlaylistDescriptionString();
	}

	public void skip() {
		defaultPlaybackInterface.skip();
	}

	public void pullup() {
		defaultPlaybackInterface.pullup();
	}

}
