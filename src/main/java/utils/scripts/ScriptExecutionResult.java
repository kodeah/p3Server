package utils.scripts;

public record ScriptExecutionResult(
        boolean success,
        String output,
        String errors
) {
}
