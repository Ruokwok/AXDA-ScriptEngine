package net.axda.se.api;

import cn.nukkit.Server;
import com.google.gson.Gson;
import net.axda.se.ScriptEngine;
import net.axda.se.api.game.ScriptPlayer;
import net.axda.se.api.game.data.Pos;
import org.graalvm.polyglot.Value;

import java.util.Map;

public class API {

    protected ScriptEngine engine;
    private static Gson gson = new Gson();

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
        if (isMap(value)) return gson.toJson(value.as(Map.class));
        return value.toString();
    }

    public static boolean isMap(Value value) {
        try {
            value.as(Map.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Pos toPos(Value value) {
        if (value.isProxyObject()) {
            return value.getMember("pos").as(Pos.class);
        } else {
            return value.as(Pos.class);
        }
    }

}
