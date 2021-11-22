package storage;

import utils.id.IdGenerator;

import java.util.HashMap;

public class SongStore {

	private final String musicLibPath;
	private final String dlDirPath;
	private final IdGenerator idGenerator;
	
	private final HashMap<Long, LocalSong> songsById = new HashMap<>();
	
	public SongStore( final String musicLibPath,
					  final String dlDirPath,
					  final IdGenerator idGenerator )
	{
		if( musicLibPath.endsWith("/") ) {
			this.musicLibPath = musicLibPath;
		} else {
			this.musicLibPath = musicLibPath + "/";
		}
		this.dlDirPath = dlDirPath;
		this.idGenerator = idGenerator;
	}
	
	public synchronized LocalSong getSongWithId(final Long id) {
		return songsById.get(id);
	}
	
	public synchronized Long addSong(final String path) {
		final Long newId = idGenerator.getFreeId();
		songsById.put(newId, new LocalSong(newId, path));
		return newId;
	
	//TODO: check if song is there and is a mp3!!!
	}

	public String getSongPathWithInnerDirPrefix(Long idSong) throws Exception {
		final LocalSong song = getSongWithId(idSong);
		final String songPathFull = song.localPath();
		if( songPathFull.indexOf( musicLibPath ) != 0 ){
			throw new Exception("Expecting song path at " + songPathFull + " to start with: " + musicLibPath);
		}
		final String songPathInLibrary = songPathFull.substring( musicLibPath.length() );
		return songPathInLibrary;
	}
	
}
