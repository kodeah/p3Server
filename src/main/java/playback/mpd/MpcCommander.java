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
	public ScriptExecutionResult fireCommand (Script commandScript) {
		return commandExecutor.execute(commandScript);
	}
	
}
