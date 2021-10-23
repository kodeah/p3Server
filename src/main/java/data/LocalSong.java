package data;

import java.io.File;

public class LocalSong extends ABSTRACT_Song {
	
	private String localPath;
	
	public LocalSong(int id, String localPath) {
		this(Long.valueOf(id), localPath);
	}
	
	public LocalSong(int id, String localPath, String description) {
		this(Long.valueOf(id), localPath, description);
	}
	
	public LocalSong(Long id, String localPath) {
		this(id, localPath, new File(localPath).getName());
	}
	
	public LocalSong(Long id, String localPath, String description) {
		super(id, description);
		this.localPath = localPath;
	}

	public String getLocalPath() {
		return localPath;
	}
	
}
