package webInterface;

import app.MAIN;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import tasks.Task;

public class PostSkip extends ServerResource {
	
	@Post
	public void postMessage(String s) {
		MAIN.getAppInstance().LOG_INSTANCE.log("Received command: skip.");
		MAIN.getAppInstance().TASK_INTERFACE.executeInstantTask(
				new Task("Skip",
						statusStringUpdateDelegate -> {
							MAIN.getAppInstance().PLAYBACK_INTERFACE.skip();
							return null; },
						MAIN.getAppInstance().LOG_INSTANCE) );
	}

}
