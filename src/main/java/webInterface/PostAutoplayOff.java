package webInterface;

import app.MAIN;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import tasks.Task;

public class PostAutoplayOff extends ServerResource {
	
	@Post
	public void postMessage(String s) {
		MAIN.getAppInstance().LOG_INSTANCE.log("Received command: Switch Autoplay off.");
		MAIN.getAppInstance().TASK_INTERFACE.executeInstantTask(
				new Task("Switch Autoplay Off",
						statusStringUpdateDelegate -> {
							MAIN.getAppInstance().PLAYBACK_INTERFACE.toggleAutoplay( false );
							return null; },
						MAIN.getAppInstance().LOG_INSTANCE) );
	}
}
