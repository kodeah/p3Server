package webInterface;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import app.MAIN;

public class PostAutoplayOff extends ServerResource {
	
	@Post
	public void postMessage(String s) {
		MAIN.getAppInstance().LOG_INSTANCE.log("Received command: Switch Autoplay off.");
		MAIN.getAppInstance().getTaskInterface().autoplayOff();
	}
}
