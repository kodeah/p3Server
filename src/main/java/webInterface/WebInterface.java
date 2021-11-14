package webInterface;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.service.CorsService;

import java.util.Arrays;
import java.util.HashSet;

public class WebInterface {
	
	public WebInterface(int portListen) throws Exception {
		
		CorsService corsService = new CorsService();         
		corsService.setAllowedOrigins(new HashSet<>(Arrays.asList("*")));
		corsService.setAllowedCredentials(true);
		
	    // Create a new Restlet component and add a HTTP server connector to it
	    Component component = new Component();
	    component.getServers().add(Protocol.HTTP, portListen);
	    
	    component.getServices().add(corsService);

	    // Then attach it to the local host
	    component.getDefaultHost().attach("/commands/downloadAndEnqueue", PostDownloadAndEnqueue.class);
	    component.getDefaultHost().attach("/commands/autoplayOn", PostAutoplayOn.class);
	    component.getDefaultHost().attach("/commands/autoplayOff", PostAutoplayOff.class);
	    component.getDefaultHost().attach("/commands/autoplayToggle", PostAutoplayToggle.class);
	    component.getDefaultHost().attach("/commands/pullup", PostPullup.class);
	    component.getDefaultHost().attach("/commands/skip", PostSkip.class);
	    component.getDefaultHost().attach("/info/playlist", GetPlaylist.class);

	    // Now, let's start the component!
	    // Note that the HTTP server connector is also automatically started.
	    component.start();
	}
	
}
