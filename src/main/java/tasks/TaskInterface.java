package tasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskInterface {

	private final ExecutorService executor; //To schedule download+enqueue tasks.

	public TaskInterface()
	{
		executor = Executors.newFixedThreadPool(5);
	}
	
	public void executeBackgroundTask(final Task task) {
		executor.submit(() -> {
			task.execute();
		});
	}

	public Object executeInstantTask(final Task task) {
		return task.execute();
	}

}
