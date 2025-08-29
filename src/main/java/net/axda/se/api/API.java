package net.axda.se.api;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.level.Level;
import com.google.gson.Gson;
import net.axda.se.AXDAScriptEngine;
import net.axda.se.ScriptEngine;
import net.axda.se.ScriptLoader;
import net.axda.se.api.game.data.Pos;
import org.graalvm.polyglot.Value;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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

    public static int msToTick(int ms) {
        return (int) ((ms / 1000) * Server.getInstance().getTicksPerSecond());
    }

    public static void unregisterCommand(Command command) {
        SimpleCommandMap commandMap = Server.getInstance().getCommandMap();
        try {
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, Command> knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);
            ArrayList<String> list = new ArrayList<>();
            for (Map.Entry<String, Command> entry : knownCommands.entrySet()) {
                if (entry.getValue() == command) {
                    list.add(entry.getKey());
                }
            }
            for (String key : list) {
                knownCommands.remove(key);
            }
        } catch (Exception e) {
        }
        command.unregister(commandMap);
        Collection<Player> players = Server.getInstance().getOnlinePlayers().values();
        for (Player player : players) {
            player.sendCommandData();
        }
    }

    public static Level getLevel(Value value) {
        if (value.isString()) {
            return Server.getInstance().getLevelByName(value.asString());
        } else if (value.isNumber()) {
            int id = value.asInt();
            switch (id) {
                case 0: return Server.getInstance().getDefaultLevel();
                case 1: return Server.getInstance().getLevelByName("nether");
                case 2: return Server.getInstance().getLevelByName("the_end");
            }
        }
        return null;
    }

    public static void printException(Throwable e) {
        ScriptEngine _engine = ScriptLoader.getInstance().getEngineNow();
        _engine.logExceptionTitle(e.getMessage());
    }

}
