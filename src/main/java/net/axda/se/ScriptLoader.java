package net.axda.se;

import cn.nukkit.Server;
import cn.nukkit.utils.LogLevel;
import cn.nukkit.utils.Logger;
import cn.nukkit.utils.Utils;
import net.axda.se.api.script.LL;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ScriptLoader {

    private static ScriptLoader loader;
    private Logger logger;
    private String runtime;
    private LL ll;
    private Hashtable<String, Engine> engines = new Hashtable<>();

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
            Engine engine = new Engine(script);
            engine.execute();
            engines.put(engine.getDescription().getName(), engine);
            logger.info("Loaded " + engine.getDescription().getName() + " v" + engine.getDescription().getVersionStr());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void disablePlugins() {
        for (Engine engine : engines.values()) {
            engine.stop();
        }
        engines.clear();
    }

    public void disablePlugin(String name) {
        Engine engine = engines.get(name);
        if (engine != null) {
            engine.stop();
            engines.remove(name);
        }
    }

    public String getRuntime() {
        return runtime;
    }

    public LL getLL() {
        return ll;
    }

    public Map<String, Engine> getScriptMap() {
        return new HashMap<>(engines);
    }
}
