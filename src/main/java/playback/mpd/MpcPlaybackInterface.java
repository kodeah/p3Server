package playback.mpd;

import playback.INTERFACE_Playback;
import storage.SongStore;
import utils.log.ILog;
import utils.scripts.ScriptBuilder;


public class
MpcPlaybackInterface
implements
INTERFACE_Playback
{

	private final SongStore songStore;
	private final ILog log;
	private final MpcCommander mpcCommander;

	public MpcPlaybackInterface(
			final String temporaryBaseDirectory,
			final SongStore songStore,
			final ILog log
			) {
		this.log = log;
		this.songStore = songStore;
		this.mpcCommander = new MpcCommander(temporaryBaseDirectory, log);
	}

	@Override
	public void verifyIsUp() throws Exception {
		log.log("Check if MPD is up and reachable via http ...");
		try {
			mpcCommander.fireCommand(new ScriptBuilder()
					.appendLine("mpc")
					.toScript() );
		} catch (Exception e) {
			log.log("MPD is not up or not reachable:");
			throw e;
		}
		log.log("MPD is up and reachable.");
	}

	@Override
	public void toggleAutoplay(boolean enable) throws Exception {
		if (enable) {
			mpcCommander.fireCommand(new ScriptBuilder()
					.appendLine("mpc", "consume", "on")
					.appendLine("mpc", "play")
					.toScript() );
		} else {
			mpcCommander.fireCommand(new ScriptBuilder()
					.appendLine("mpc", "pause")
					.toScript() );
		}
	}

	@Override
	public void toggleAutoplay() throws Exception {
		mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "toggle")
				.toScript() );
	}

	@Override
	public void skip() throws Exception {
		mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "next")
				.toScript() );
	}

	@Override
	public void pullup() throws Exception {
		mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "stop")
				.appendLine("mpc", "play")
				.toScript() );
	}

	@Override
	public void insertSongLast(Long idSong) throws Exception {
		mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "update", "--wait")
				.appendLine("sleep", "1.5")
				.appendLine("mpc", "add", songStore.getSongPathWithInnerDirPrefix(idSong))
				.toScript() );
	}
	
	@Override
	public void insertSongNext(Long idSong) throws Exception {
		mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "update", "--wait")
				.appendLine("sleep", "1.5")
				.appendLine("mpc", "insert", songStore.getSongPathWithInnerDirPrefix(idSong))
				.toScript() );
	}

	@Override
	public String getPlaylistDescriptionString() throws Exception {
		return mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "playlist")
				.toScript() );
	}

	@Override
	public String getStatusString() {
		throw new RuntimeException("getStatusString not implemented");
	}

}
