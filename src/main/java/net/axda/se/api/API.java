package net.axda.se.api;

import cn.nukkit.Server;
import net.axda.se.ScriptEngine;
import net.axda.se.api.game.ScriptPlayer;
import net.axda.se.api.game.data.Pos;
import org.graalvm.polyglot.Value;

import java.util.Map;

public class API {

    protected ScriptEngine engine;

    public API setEngine(ScriptEngine engine) {
        this.engine = engine;
        return this;
    }

    public ScriptEngine getEngine() {
        return engine;
    }

    public void print(String a) {
        Server.getInstance().getLogger().info(a);
    }

    public static String toString(Value value) {
        if (value.isString()) return value.asString();
        return value.toString();
    }

    public static Pos toPos(Value value) {
        if (value.isProxyObject()) {
            return value.getMember("pos").as(Pos.class);
        } else {
            return value.as(Pos.class);
        }
    }

}
