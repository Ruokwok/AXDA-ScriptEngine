package net.axda.se;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.LogLevel;
import cn.nukkit.utils.Logger;
import cn.nukkit.utils.Utils;
import net.axda.se.api.game.ScriptPlayer;
import net.axda.se.api.script.LL;
import net.axda.se.listen.ListenMap;

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
    private int counter = -1;
    private ScriptEngine nowEngine;

    private ScriptLoader() {
    }

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
            loadPlugin(script, file);
        } catch (IOException e) {
            logger.log(LogLevel.ERROR, "Read file failed: " + file.getName(), e);
        }
    }

    public void loadPlugin(String script) {
        loadPlugin(script, null);
    }

    public void loadPlugin(String script, File file) {
        counter++;
        ScriptExecTask task = new ScriptExecTask(counter, script, file);
//        TaskHandler taskHandler = Server.getInstance().getScheduler().scheduleAsyncTask(AXDAScriptEngine.getPlugin(), task);
//        task.setTaskId(taskHandler.getTaskId());
        this.nowEngine = task.getEngine();
        putEngine(task.getEngine());
        task.onRun();
        this.nowEngine = null;
    }

    protected void putEngine(ScriptEngine engine) {
        engines.put(engine.getUUID(), engine);
    }

    public void disablePlugins() {
        for (ScriptEngine engine : engines.values()) {
            engine.disable();
        }
        engines.clear();
    }

    public void disablePlugin(ScriptEngine engine) {
        if (engine != null) {
            engine.disable();
        }
        engines.remove(engine.getUUID());
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

    public ScriptPlayer putPlayer(Player player) {
        ScriptPlayer scriptPlayer = new ScriptPlayer(player);
        players.put(player, scriptPlayer);
        return scriptPlayer;
    }

    public ScriptPlayer getPlayer(Player player) {
        if (players.containsKey(player)) {
            return players.get(player);
        } else {
            ScriptPlayer sp = new ScriptPlayer(player);
            players.put(player, sp);
            return sp;
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<ScriptPlayer> getOnlinePlayers() {
        return new ArrayList<>(players.values());
    }

    /**
     * 向正在执行的脚本对象添加一个需要关闭的资源
     * @param c
     */
    public void putCloseable(AutoCloseable c) {
//        String threadName = Thread.currentThread().getName();
//        if (threadName.startsWith("js-")) {
//            ScriptEngine engine = engines.get(threadName);
//            if (engine == null) {
//                String[] split = threadName.split("/");
//                if (split.length == 2 && split[0].startsWith("js-")) {
//                    engine = engines.get(split[0]);
//                }
//            }
//            if (engine != null) {
//                engine.putCloseable(c);
//            }
//        } else if (ListenMap.execEngine != null) {
//            ListenMap.nowEngine.putCloseable(c);
//        }
        if (this.nowEngine != null) {
            this.nowEngine.putCloseable(c);
        } else if (ListenMap.nowEngine != null) {
            ListenMap.nowEngine.putCloseable(c);
        }
    }

    public ScriptEngine getEngine(String threadName) {
        return engines.get(threadName);
    }
}
