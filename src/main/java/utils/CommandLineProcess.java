package utils;

import utils.log.ILog;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineProcess {
	
	private Process p;
	private StringBuilder sbOutput;
	private StringBuilder sbErrors;
	private int exitValue = -1;
	public
	CommandLineProcess(
			final String[] commandWithParams,
			final String executionDirectory,
			final boolean waitFor,
			final boolean captureOutput,
			final boolean captureErrors,
			final ILog log )
	{
		ProcessBuilder pb = new ProcessBuilder(commandWithParams);
		 pb.directory(new File(executionDirectory));
		 try {
			 p = pb.start();

			 if (waitFor) {
				 p.waitFor();
				 this.exitValue = p.exitValue();
			 }
			 if (captureOutput) {
				 captureOutput();
			 }
			 if (captureErrors) {
				 captureErrors();
			 }

		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private void captureOutput() throws IOException {
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(p.getInputStream()));
		sbOutput = new StringBuilder();
		String line = null;
		while ( (line = reader.readLine()) != null) {
			sbOutput.append(line);
			sbOutput.append(System.getProperty("line.separator"));
		}
	}

	private void captureErrors() throws IOException {
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(p.getErrorStream()));
		sbErrors = new StringBuilder();
		String line = null;
		while ( (line = reader.readLine()) != null) {
			sbErrors.append(line);
			sbErrors.append(System.getProperty("line.separator"));
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
