package webInterface;

import app.MAIN;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import tasks.Task;

public class GetPlaylist extends ServerResource {
	
	@Get
	public String getMessage() {
		MAIN.getAppInstance().LOG_INSTANCE.log("Received command: getPlaylistString");
		return (String) MAIN.getAppInstance().TASK_INTERFACE.executeInstantTask(
				new Task("Get Playlist",
						statusStringUpdateDelegate -> MAIN.getAppInstance().PLAYBACK_INTERFACE.getPlaylistDescriptionString(),
						MAIN.getAppInstance().LOG_INSTANCE) );
	}
	
}