package webInterface;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import app.MAIN;

public class PostAutoplayOn extends ServerResource {
	
	@Post
	public void postMessage(String s) {
		MAIN.getAppInstance().LOG_INSTANCE.log("Received command: Switch Autoplay on.");
		MAIN.getAppInstance().getTaskInterface().autoplayOn();
	}
}
