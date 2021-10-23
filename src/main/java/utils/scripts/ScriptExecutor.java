package utils.scripts;

import utils.CommandLineProcess;
import utils.log.ILog;

public class ScriptExecutor {

    private final String workingDir;
    private final ILog log;

    public ScriptExecutor(
            final String workingDir,
            final ILog log )
    {
        this.workingDir = workingDir;
        this.log = log;
    }

    public ScriptExecutionResult execute( Script script ) {
        StringBuilder sbOutput = new StringBuilder();
        StringBuilder sbError = new StringBuilder();
        boolean success = true;
        for ( String[] line : script.content() ) {
            CommandLineProcess p = new CommandLineProcess(
                    line,
                    workingDir,
                    true,
                    true,
                    true,
                    log );
            sbOutput.append( p.getOutput() );
            sbError.append( p.getErrors() );
            if( !p.didTerminateNormally() ) {
                success = false;
                break;
            }
        }
        return new ScriptExecutionResult(
                success,
                sbOutput.toString(),
                sbError.toString() );
    }

}
