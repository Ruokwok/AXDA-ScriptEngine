package net.axda.se.api;

import cn.nukkit.Server;
import com.google.gson.Gson;
import net.axda.se.ScriptEngine;
import net.axda.se.api.game.ScriptPlayer;
import net.axda.se.api.game.data.Pos;
import net.axda.se.api.game.data.ProxyMap;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.Proxy;

import java.util.List;
import java.util.Map;

public class API {

    protected ScriptEngine engine;
    public static final Gson GSON = new Gson();

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
        return value.toString();
    }

    public static boolean isMap(Value value) {
        try {
            Map map = value.as(Map.class);
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

}
