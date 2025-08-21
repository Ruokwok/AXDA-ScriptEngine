package net.axda.se.api;

import cn.nukkit.Server;
import com.google.gson.Gson;
import net.axda.se.ScriptEngine;
import net.axda.se.api.game.data.Pos;
import org.graalvm.polyglot.Value;

public class API {

    protected ScriptEngine engine;
    public static final Gson GSON = new Gson();
    private static int threadCounter = 0;

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
        if (isArray(value)) return GSON.toJson(value.as(Object[].class));
        if (isPos(value)) return value.as(Pos.class).toString();
        return value.toString();
    }

    public static boolean isPos(Value value) {
        try {
            Pos pos = value.as(Pos.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isArray(Value value) {
        try {
            Object[] objects = value.as(Object[].class);
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

    public static void setThreadName() {
        Thread.currentThread().setName("js-" + ++threadCounter);
    }

}
