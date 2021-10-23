package utils.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log implements ILog {

	private final String logFilePath;

	public Log( final String logFilePath ) throws IOException {
		this.logFilePath = logFilePath;
		if( !new File(logFilePath).exists() ) {
			new File(logFilePath).createNewFile();
		}
	}
	
	@Override
	public void log(String message) {
		String log = "[LOG:] " + message;
		output(log);
	}

	@Override
	public void warn(String message) {
		String log = "[WARNING:] " + message;
		output(log);
	}

	@Override
	public void error(String message) {
		String log = "[ERROR:] " + message;
		output(log);
	}
	
	private void output(String message) {
		System.out.println(message);
		if (logFilePath != null) {
			FileWriter fw = null;
			try {
				fw = new FileWriter(
						new File(logFilePath), true
						);
				fw.write(message + System.lineSeparator());
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
