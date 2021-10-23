package utils.id;

public class NaiveIdGenerator implements IdGenerator {

	private long nextFreeId = 0;
	
	@Override
	public synchronized Long getFreeId() {
		Long id = Long.valueOf(nextFreeId);
		nextFreeId ++;
		return id;
	}

}
