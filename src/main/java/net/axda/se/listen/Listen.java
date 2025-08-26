package net.axda.se.listen;

import cn.nukkit.Server;
import cn.nukkit.utils.MainLogger;
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

    public boolean callEvent(Object[] args) {
        try {
            engine.getContext().enter();
            Value execute = value.execute(args);
            engine.getContext().leave();
//            Value execute = engine.execute(value);
            if (execute.isBoolean() && !execute.asBoolean()) return false;
        } catch (Exception e) {
            engine.logException(e);
        }
        return true;
    }

}
