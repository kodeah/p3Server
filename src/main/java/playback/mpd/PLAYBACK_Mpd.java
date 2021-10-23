package playback.mpd;

import data.LocalSong;
import playback.INTERFACE_Playback;
import storage.SongStore;
import utils.log.ILog;
import utils.scripts.ScriptBuilder;
import utils.scripts.ScriptExecutionResult;


public class
PLAYBACK_Mpd
implements
INTERFACE_Playback
{

	private final SongStore songStore;
	private final ILog log;
	private final MpcCommander mpcCommander;

	public PLAYBACK_Mpd(
			final String temporaryBaseDirectory,
			final SongStore songStore,
			final ILog log
			) {
		this.log = log;
		this.songStore = songStore;
		this.mpcCommander = new MpcCommander(temporaryBaseDirectory, log);
	}


	@Override
	public void toggleAutoplay(boolean enable) {
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
	public void toggleAutoplay() {
		mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "toggle")
				.toScript() );
	}

	@Override
	public void skip() {
		mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "next")
				.toScript() );
	}

	@Override
	public void pullup() {
		mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "stop")
				.appendLine("mpc", "play")
				.toScript() );
	}

	@Override
	public void insertSongLast(Long idSong) {
		mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "update", "--wait")
				.appendLine("sleep", "1.5")
				.appendLine("mpc", "add", util_songPathFromId(idSong))
				.toScript() );
	}
	
	@Override
	public void insertSongNext(Long idSong) {
		mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "update", "--wait")
				.appendLine("sleep", "1.5")
				.appendLine("mpc", "insert", util_songPathFromId(idSong))
				.toScript() );
	}

	@Override
	public String getPlaylistDescriptionString() {
		final ScriptExecutionResult result = mpcCommander.fireCommand(new ScriptBuilder()
				.appendLine("mpc", "playlist")
				.toScript() );
		if( result.success() ) {
			return result.output();
		} else {
			log.error("Error fetching playlist:");
			log.error(result.errors());
			return "";
		}
	}


	private String util_songPathFromId(Long idSong) {
		LocalSong song = songStore.getSongWithId(idSong);
		String songPath = "file://" + song.getLocalPath();
		return songPath;
	}

}
