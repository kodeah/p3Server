package playback;


public interface INTERFACE_Playback {

	public void verifyIsUp() throws Exception;

	public void toggleAutoplay(final boolean enable) throws Exception;
	public void toggleAutoplay() throws Exception;
	
	public void skip() throws Exception;
	public void pullup() throws Exception;	// Restarts the last started song.
	
	public void insertSongLast(final Long idSong) throws Exception;
	public void insertSongNext(final Long idSong) throws Exception;
	
	public String getPlaylistDescriptionString() throws Exception;

	public String getStatusString() throws Exception;

	
}
