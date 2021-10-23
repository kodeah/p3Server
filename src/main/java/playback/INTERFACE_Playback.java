package playback;


public interface INTERFACE_Playback {

	public void toggleAutoplay(final boolean enable);
	public void toggleAutoplay();
	
	public void skip();
	public void pullup();	// Restarts the last started song.
	
	public void insertSongLast(final Long idSong);
	public void insertSongNext(final Long idSong);
	
	public String getPlaylistDescriptionString();

	
}
