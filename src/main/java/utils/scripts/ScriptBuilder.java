package utils.scripts;

import java.util.LinkedList;

public class ScriptBuilder {

    private final LinkedList< String[] > lines = new LinkedList<>();

    public ScriptBuilder appendLine( String... commandAndParameters ) {
        lines.addLast( commandAndParameters );
        return this;
    }

    public Script toScript() {
        return new Script( lines );
    }

}
