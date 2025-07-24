package net.axda.se.listen;

import net.axda.se.ScriptEngine;
import org.graalvm.polyglot.Value;

public class Listen {

    private ScriptEngine engine;
    private String event;
    private Value value;

    public Listen(ScriptEngine engine, String event, Value value) {
        this.engine = engine;
        this.event = event;
        this.value = value;
    }

    public String getEventName() {
        return event;
    }

    public ScriptEngine getEngine() {
        return engine;
    }

}
