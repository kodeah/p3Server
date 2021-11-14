package webInterface;

import app.MAIN;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import tasks.Task;

public class PostAutoplayToggle extends ServerResource {
	
	@Post
	public void postMessage(String s) {
		MAIN.getAppInstance().LOG_INSTANCE.log("Received command: Toggle Autoplay.");
		MAIN.getAppInstance().TASK_INTERFACE.executeInstantTask(
				new Task("Toggle Autoplay",
						statusStringUpdateDelegate -> {
							MAIN.getAppInstance().PLAYBACK_INTERFACE.toggleAutoplay();
							return null; },
						MAIN.getAppInstance().LOG_INSTANCE) );
	}
}
