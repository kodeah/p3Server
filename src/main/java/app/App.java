package app;

import config.ConfigPathUtil;
import config.OwnerConfig;
import org.aeonbits.owner.ConfigFactory;
import playback.INTERFACE_Playback;
import playback.mpd.MpcPlaybackInterface;
import storage.SongStore;
import tasks.DownloadAndEnqueueTaskFactory;
import tasks.TaskInterface;
import utils.id.NaiveIdGenerator;
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
	private final SongStore songStore;
	
	public App() throws Exception {

		config = ConfigFactory.create( OwnerConfig.class );

		final String logFilePath = ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.logFilePath() );
		System.out.println(
						"=== App started. Logs are written into: '"
						+ logFilePath
						+ "'. ===" );
		LOG_INSTANCE = new Log( logFilePath );
		LOG_INSTANCE.log("Starting up.");

		assertMusicLibraryDirExists();
		assertMusicDlDirExists();

		songStore = new SongStore(
				ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.mpdMusicLibraryDirectoryPath() ),
				ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.mpdMusicDownloadDirectoryPath() ),
				new NaiveIdGenerator() );

		PLAYBACK_INTERFACE = new MpcPlaybackInterface(
				ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.tmpDirectoryPath() ),
				songStore,
				LOG_INSTANCE);
		PLAYBACK_INTERFACE.verifyIsUp();

		TASK_INTERFACE = new TaskInterface();
		ENQUEUE_TASK_FACTORY = new DownloadAndEnqueueTaskFactory(
				LOG_INSTANCE,
				ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.tmpDirectoryPath() ),
				songStore,
				PLAYBACK_INTERFACE,
				ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.mpdMusicDownloadDirectoryPath() ) );
		webInterface = new WebInterface( config.portListen() );

	}

	private void assertMusicLibraryDirExists() {
		final String dirPath = ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.mpdMusicLibraryDirectoryPath() );
		final File f = new File( dirPath );
		if (!f.isDirectory()) {
			LOG_INSTANCE.error("Expected music library at '" + dirPath
					+ "', but there is no directory!");
			throw new RuntimeException();
		}
	}

	private void assertMusicDlDirExists() {
		final String dirPath = ConfigPathUtil.getCompletePathFromPerhapsRelativePath( config.mpdMusicDownloadDirectoryPath() );
		final File f = new File( dirPath );
		if (!f.isDirectory()) {
			LOG_INSTANCE.error("Expected music download directory at '" + dirPath
					+ "', but there is no directory!");
			throw new RuntimeException();
		}
	}
	
}
