package webInterface;

import app.MAIN;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class PostDownloadAndEnqueue extends ServerResource {
	
	// If downloads fail ("0 files in directory" or so shown in log):
	// Probably simply yutube-dl is not up to date anymore.
	
	@Post
	public void postMessage(String s) {
		MAIN.getAppInstance().LOG_INSTANCE.log("Received command: Download and enqueue song at url '" + s + "'.");
		MAIN.getAppInstance().TASK_INTERFACE.executeBackgroundTask(
				MAIN.getAppInstance().ENQUEUE_TASK_FACTORY.create(s) );
	}
}
