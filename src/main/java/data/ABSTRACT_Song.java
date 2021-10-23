package data;

public abstract class ABSTRACT_Song {

	private final Long id;
	private final String description;

	public ABSTRACT_Song(Long id, String description) {
		this.description = description;
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Long getId() {
		return id;
	}

}
