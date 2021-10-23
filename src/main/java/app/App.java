package app;

import org.aeonbits.owner.ConfigFactory;
import utils.log.ILog;
import utils.log.Log;
import webInterface.WebInterface;


public class App {

	public final ILog LOG_INSTANCE;
	
	private WebInterface webInterface;
	//Variable unused, but must be stored as long as the web services should run or the webservice might stop?
	
	private TaskInterface taskInterface;
	
	public App() throws Exception {

		AppConfig config = ConfigFactory.create( AppConfig.class );

		System.out.println(
						"=== App started. Logs are written into: '"
						+ config.logFilePath()
						+ "'. ===" );
		LOG_INSTANCE = new Log(config.logFilePath());
		LOG_INSTANCE.log("Starting up.");

		taskInterface = new TaskInterface(config, LOG_INSTANCE);
		webInterface = new WebInterface(config.portListen());
	}
	
	public TaskInterface getTaskInterface() {
		return taskInterface;
	}
	
}
