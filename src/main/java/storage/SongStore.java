package storage;

import data.LocalSong;
import utils.id.IdGenerator;

import java.util.HashMap;

public class SongStore {
	//threadsafe
	
	private final String storageDirPath;
	private final IdGenerator idGenerator;
	
	private final HashMap<Long, LocalSong> songsById = new HashMap<>();
	
	public SongStore(final String storageDirPath, final IdGenerator idGenerator) {
		this.storageDirPath = storageDirPath;
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
	
	public synchronized String getStorageDirPath() {
		return storageDirPath;
	}
	
}
