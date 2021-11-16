package utils;

import utils.log.ILog;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineProcess {

	private final static long LOG_THREAD_INTERVAL = 100;

	private final StringBuilder sbOutput = new StringBuilder();
	private final StringBuilder sbErrors = new StringBuilder();
	private final boolean logOutput;
	private final boolean logErrors;

	private Process p;
	private int exitValue = -1;
	private Thread logThread;
	private final ILog log;
	private boolean processFinished = false;

	public
	CommandLineProcess(
			final String[] commandWithParams,
			final String executionDirectory,
			final boolean waitFor,
			final boolean logOutput,
			final boolean logErrors,
			final ILog log )
	{
		this.logOutput = logOutput;
		this.logErrors = logErrors;
		this.log = log;

		final ProcessBuilder pb = new ProcessBuilder(commandWithParams);
		pb.directory(new File(executionDirectory));
		try {
			p = pb.start();
			logThread = new Thread( ()-> {
				try {
					captureOutputAndErrors();
				} catch (Exception e) {
					log.except( "CommandLineProcess: Cannot capture output and errors because of an exception.", e );
					e.printStackTrace();
				}
			});
			logThread.start();

			if (waitFor) {
				p.waitFor();
				processFinished = true;
				logThread.join();
				this.exitValue = p.exitValue();
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private void captureOutputAndErrors() throws IOException, InterruptedException {
		BufferedReader readerOutput =
				new BufferedReader(new InputStreamReader(p.getInputStream()));
		String lineOutput;
		BufferedReader readerErrors =
				new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String lineErrors;

		boolean processFinishedBefore;
		while( true ){
			processFinishedBefore = processFinished;
			while ((lineOutput = readerOutput.readLine()) != null) {
				sbOutput.append(lineOutput);
				sbOutput.append(System.getProperty("line.separator"));
				if (logOutput) {
					log.log(lineOutput);
				}
			}
			while ((lineErrors = readerErrors.readLine()) != null) {
				sbErrors.append(lineErrors);
				sbErrors.append(System.getProperty("line.separator"));
				if (logErrors) {
					log.error(lineErrors);
				}
			}
			if(processFinishedBefore) {
				break;
			}
			Thread.sleep( LOG_THREAD_INTERVAL );
		}
	}

	public String getOutput() {
		return sbOutput.toString();
	}

	public String getErrors() {
		return sbErrors.toString();
	}

	public boolean didTerminateNormally() {
		return exitValue == 0;
	}

}
