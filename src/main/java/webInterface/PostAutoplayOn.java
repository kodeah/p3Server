package webInterface;

import app.MAIN;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import tasks.Task;

public class PostAutoplayOn extends ServerResource {
	
	@Post
	public void postMessage(String s) {
		MAIN.getAppInstance().LOG_INSTANCE.log("Received command: Switch Autoplay on.");
		MAIN.getAppInstance().TASK_INTERFACE.executeInstantTask(
				new Task("Switch Autoplay On",
						statusStringUpdateDelegate -> {
					MAIN.getAppInstance().PLAYBACK_INTERFACE.toggleAutoplay( true );
					return null; },
						MAIN.getAppInstance().LOG_INSTANCE) );
	}
}
