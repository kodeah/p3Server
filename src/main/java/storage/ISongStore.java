package storage;

public interface ISongStore {

    LocalSong getSongWithId(final Long id) throws Exception;
    Long addSong(final String path) throws Exception;
    String getSongPath(final Long songId) throws Exception;

}
