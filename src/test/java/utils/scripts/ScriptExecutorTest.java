package utils.scripts;

import org.junit.jupiter.api.Test;
import utils.log.PrintLog;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScriptExecutorTest {

    @Test
    public void testSimple() {
        final ScriptBuilder sb = new ScriptBuilder();
        sb.appendLine("echo", "test0");
        sb.appendLine("echo", "test1");

        final ScriptExecutor exec = new ScriptExecutor("/tmp", new PrintLog());
        final ScriptExecutionResult result = exec.execute( sb.toScript() );

        assertEquals( true, result.success() );
        assertEquals( "", result.errors() );
        assertEquals( "test0\ntest1\n", result.output() );
    }

}
