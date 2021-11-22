package storage;

import utils.id.IdGenerator;
import utils.log.ILog;
import utils.scripts.Script;
import utils.scripts.ScriptBuilder;
import utils.scripts.ScriptExecutionResult;
import utils.scripts.ScriptExecutor;

import java.util.HashMap;

public class SongStoreForMpd implements ISongStore {

	private final String musicLibPath;
	private final String dlDirPath;
	private final IdGenerator idGenerator;
	private final ILog log;

	private final HashMap<Long, LocalSong> songsById = new HashMap<>();

	public SongStoreForMpd(final String musicLibPath,
						   final String dlDirPath,
						   final IdGenerator idGenerator,
						   final ILog log )
	{
		this.log = log;
		if( musicLibPath.endsWith("/") ) {
			this.musicLibPath = musicLibPath;
		} else {
			this.musicLibPath = musicLibPath + "/";
		}
		this.dlDirPath = dlDirPath;
		this.idGenerator = idGenerator;
	}

	@Override
	public synchronized LocalSong getSongWithId( final Long id ) {
		return songsById.get(id);
	}

	@Override
	public synchronized Long addSong( final String path ) throws Exception {
		final Long newId = idGenerator.getFreeId();
		songsById.put(newId, new LocalSong(newId, path));

		final Script updateScript = new ScriptBuilder()
				.appendLine("mpc", "update")
				.toScript();
		ScriptExecutionResult updateScriptResult = new ScriptExecutor(musicLibPath, log).execute(updateScript);
		if(!updateScriptResult.success()) {
			throw new Exception("'mpc update' failed.");
		}

		return newId;

		//TODO: check if song is there and is a mp3!!!
	}

	public String getSongPath( final Long songId ) throws Exception {
		final LocalSong song = getSongWithId(songId);
		final String songPathFull = song.localPath();
		if( songPathFull.indexOf( musicLibPath ) != 0 ){
			throw new Exception("Expecting song path at " + songPathFull + " to start with: " + musicLibPath);
		}
		final String songPathInLibrary = songPathFull.substring( musicLibPath.length() );
		return songPathInLibrary;
	}

}
