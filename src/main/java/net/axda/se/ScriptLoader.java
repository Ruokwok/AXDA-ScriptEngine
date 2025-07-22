package net.axda.se;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.LogLevel;
import cn.nukkit.utils.Logger;
import cn.nukkit.utils.Utils;
import net.axda.se.api.game.ScriptPlayer;
import net.axda.se.api.script.LL;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ScriptLoader {

    private static ScriptLoader loader;
    private Logger logger;
    private String runtime;
    private LL ll;
    private Hashtable<String, ScriptEngine> engines = new Hashtable<>();
    private Hashtable<Player, ScriptPlayer> players = new Hashtable<>();

    public static ScriptLoader getInstance() {
        return loader;
    }

    protected static void init() {
        if (loader == null) {
            loader = new ScriptLoader();
            loader.logger = Server.getInstance().getLogger();
            loader.ll = new LL();
            try {
                loader.runtime = Utils.readFile(ScriptLoader.class.getResourceAsStream("/runtime.js"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadPlugins(File path) {
        for (File file : path.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".js")) {
                ScriptLoader.getInstance().loadPlugin(file);
            }
        }
    }

    public void loadPlugin(File file) {
        try {
            String script = Utils.readFile(file);
            loadPlugin(script);
        } catch (IOException e) {
            logger.log(LogLevel.ERROR, "读取失败: " + file.getName(), e);
        }
    }

    public void loadPlugin(String script) {
        try {
            ScriptEngine engine = new ScriptEngine(script);
            engine.execute();
            engines.put(engine.getDescription().getName(), engine);
            logger.info("Loaded " + engine.getDescription().getName() + " v" + engine.getDescription().getVersionStr());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void disablePlugins() {
        for (ScriptEngine engine : engines.values()) {
            engine.disable();
        }
        engines.clear();
    }

    public void disablePlugin(String name) {
        ScriptEngine engine = engines.get(name);
        if (engine != null) {
            engine.disable();
            engines.remove(name);
        }
    }

    public String getRuntime() {
        return runtime;
    }

    public LL getLL() {
        return ll;
    }

    public Map<String, ScriptEngine> getScriptMap() {
        return new HashMap<>(engines);
    }

    public void putPlayer(Player player) {
        players.put(player, new ScriptPlayer(player));
    }

    public ScriptPlayer getPlayer(Player player) {
        return players.get(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<ScriptPlayer> getOnlinePlayers() {
        return new ArrayList<>(players.values());
    }
}
