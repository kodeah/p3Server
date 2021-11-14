package webInterface;

import app.MAIN;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import tasks.Task;

public class PostPullup extends ServerResource {
	
	@Post
	public void postMessage(String s) {
		MAIN.getAppInstance().LOG_INSTANCE.log("Received command: Pullup.");
		MAIN.getAppInstance().TASK_INTERFACE.executeInstantTask(
				new Task("Pullup",
						statusStringUpdateDelegate -> {
							MAIN.getAppInstance().PLAYBACK_INTERFACE.pullup();
							return null; },
						MAIN.getAppInstance().LOG_INSTANCE) );
	}

}
