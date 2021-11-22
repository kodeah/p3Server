package app;

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

	public final AppConfig CONFIG;
	public final ILog LOG_INSTANCE;
	public final TaskInterface TASK_INTERFACE;
	public final INTERFACE_Playback PLAYBACK_INTERFACE;
	public final DownloadAndEnqueueTaskFactory ENQUEUE_TASK_FACTORY;
	
	private WebInterface webInterface;
		//Variable unused, but must be stored as long as the web services should run or the webservice might stop?
	private final SongStore songStore;
	
	public App() throws Exception {

		CONFIG = ConfigFactory.create( AppConfig.class );

		System.out.println(
						"=== App started. Logs are written into: '"
						+ CONFIG.logFilePath()
						+ "'. ===" );
		LOG_INSTANCE = new Log(CONFIG.logFilePath());
		LOG_INSTANCE.log("Starting up.");

		assertMusicLibraryDirExists();
		assertMusicDlDirExists();

		songStore = new SongStore(
				CONFIG.mpdMusicLibraryDirectoryPath(),
				CONFIG.mpdMusicDownloadDirectoryPath(),
				new NaiveIdGenerator() );

		PLAYBACK_INTERFACE = new MpcPlaybackInterface(CONFIG.tmpDirectoryPath(), songStore, LOG_INSTANCE);
		PLAYBACK_INTERFACE.verifyIsUp();

		TASK_INTERFACE = new TaskInterface();
		ENQUEUE_TASK_FACTORY = new DownloadAndEnqueueTaskFactory(LOG_INSTANCE, CONFIG.tmpDirectoryPath(), songStore, PLAYBACK_INTERFACE);
		webInterface = new WebInterface(CONFIG.portListen());
	}

	private void assertMusicLibraryDirExists() {
		final File f = new File( CONFIG.mpdMusicLibraryDirectoryPath() );
		if (!f.isDirectory()) {
			LOG_INSTANCE.error("Expected music library at '" + CONFIG.mpdMusicLibraryDirectoryPath()
					+ "', but there is no directory!");
			throw new RuntimeException();
		}
	}

	private void assertMusicDlDirExists() {
		final File f = new File( CONFIG.mpdMusicDownloadDirectoryPath() );
		if (!f.isDirectory()) {
			LOG_INSTANCE.error("Expected music download directory at '" + CONFIG.mpdMusicDownloadDirectoryPath()
					+ "', but there is no directory!");
			throw new RuntimeException();
		}
	}
	
}
