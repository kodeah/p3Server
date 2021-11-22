package storage;

import utils.id.IdGenerator;

import java.util.HashMap;

public class SongStoreForVlc implements ISongStore {

	private final IdGenerator idGenerator;

	private final HashMap<Long, LocalSong> songsById = new HashMap<>();

	public SongStoreForVlc(
			final IdGenerator idGenerator )
	{
		this.idGenerator = idGenerator;
	}

	@Override
	public synchronized LocalSong getSongWithId( final Long id ) {
		return songsById.get(id);
	}

	@Override
	public synchronized Long addSong( final String path ) {
		final Long newId = idGenerator.getFreeId();
		songsById.put(newId, new LocalSong(newId, path));
		return newId;

		//TODO: check if song is there and is a mp3!!!
	}

	public String getSongPath( final Long songId ) {
		final LocalSong song = getSongWithId(songId);
		return song.localPath();
	}

}
