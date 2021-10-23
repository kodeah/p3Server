package webInterface;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import app.MAIN;

public class GetPlaylist extends ServerResource {
	
	@Get
	public String getMessage() {
		MAIN.getAppInstance().LOG_INSTANCE.log("Received command: getPlaylistString");
		return MAIN.getAppInstance().getTaskInterface().getPlaylistString();
	}
	
}