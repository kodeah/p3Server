package app;

import config.ConfigPathUtil;
import config.OwnerConfig;
import org.aeonbits.owner.ConfigFactory;
import playback.INTERFACE_Playback;
import tasks.DownloadAndEnqueueTaskFactory;
import tasks.TaskInterface;
import utils.WebRequestHelper;
import utils.log.ILog;
import utils.log.Log;
import webInterface.WebInterface;

import java.io.File;


public class App {

	public final ILog LOG_INSTANCE;
	public final TaskInterface TASK_INTERFACE;
	public final INTERFACE_Playback PLAYBACK_INTERFACE;
	public final DownloadAndEnqueueTaskFactory ENQUEUE_TASK_FACTORY;

	private final OwnerConfig config;
	private WebInterface webInterface;
		//Variable unused, but must be stored as long as the web services should run or the webservice might stop?
	
	public App() throws Exception {

		config = ConfigFactory.create( OwnerConfig.class );

		final String logFilePath = ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.logFilePath() );
		System.out.println(
						"=== App started. Logs are written into: '"
						+ logFilePath
						+ "'. ===" );
		LOG_INSTANCE = new Log( logFilePath );
		LOG_INSTANCE.log("Starting up.");

		assertMpdMusicLibraryDirExists();
		assertMpdMusicDlDirExists();

		final AppParts appParts;
		final String playbackMode = config.playbackInterface();
		final String downloadTargetDir;
		switch (playbackMode) {
			case "mpd":
				downloadTargetDir = ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.mpdMusicDownloadDirectoryPath() );
				appParts = new AppPartsForMpd(
						ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.tmpDirectoryPath() ),
						ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.mpdMusicLibraryDirectoryPath() ),
						downloadTargetDir,
						LOG_INSTANCE );
				break;
			case "vlc":
				downloadTargetDir = ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.vlcMusicDownloadDirectoryPath() );
				appParts = new AppPartsForVlc(
						new WebRequestHelper(
								config.vlcHttpInterfaceHost(),
								config.vlcHttpInterfacePort(),
								"",
								config.vlcHttpInterfacePassword() ),
						LOG_INSTANCE );
				break;
			default:
				throw new Exception("Invalid plaback interface configuration (possible are 'mpd' or 'vlc'): " + playbackMode);
		}

		PLAYBACK_INTERFACE = appParts.getPlaybackInterface();
		PLAYBACK_INTERFACE.verifyIsUp();

		TASK_INTERFACE = new TaskInterface();
		ENQUEUE_TASK_FACTORY = new DownloadAndEnqueueTaskFactory(
				LOG_INSTANCE,
				config.youtubeDownloaderApplication(),
				ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.tmpDirectoryPath() ),
				downloadTargetDir,
				appParts.getSongStore(),
				PLAYBACK_INTERFACE );
		webInterface = new WebInterface( config.portListen() );
	}

	private void assertMpdMusicLibraryDirExists() {
		final String dirPath = ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.mpdMusicLibraryDirectoryPath() );
		final File f = new File( dirPath );
		if (!f.isDirectory()) {
			LOG_INSTANCE.error("Expected music library at '" + dirPath
					+ "', but there is no directory!");
			throw new RuntimeException();
		}
	}

	private void assertMpdMusicDlDirExists() {
		final String dirPath = ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.mpdMusicDownloadDirectoryPath() );
		final File f = new File( dirPath );
		if (!f.isDirectory()) {
			LOG_INSTANCE.error("Expected music download directory at '" + dirPath
					+ "', but there is no directory!");
			throw new RuntimeException();
		}
	}
	
}
