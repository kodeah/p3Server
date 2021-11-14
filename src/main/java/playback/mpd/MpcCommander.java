package playback.mpd;

import utils.log.ILog;
import utils.scripts.Script;
import utils.scripts.ScriptExecutionResult;
import utils.scripts.ScriptExecutor;

public class MpcCommander {

	private final ScriptExecutor commandExecutor;

	public MpcCommander(
			final String commandExecutionDir,
			final ILog log )
	{
		this.commandExecutor = new ScriptExecutor( commandExecutionDir, log );
	}
	
	// Fires a command, blocking and waiting for return values.
	public String fireCommand (Script commandScript) throws Exception {
		ScriptExecutionResult result = commandExecutor.execute(commandScript);
		if (!result.success()) {
			StringBuilder sb = new StringBuilder();
			sb.append("Mpc command failed with error:\n");
			sb.append(result.output());
			sb.append(result.errors());
			throw new Exception(sb.toString());
		} else {
			return result.output();
		}
	}
	
}
