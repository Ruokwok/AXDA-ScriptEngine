package net.axda.se;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.LogLevel;
import cn.nukkit.utils.Logger;
import cn.nukkit.utils.MainLogger;
import cn.nukkit.utils.Utils;
import net.axda.se.api.API;
import net.axda.se.api.game.ScriptPlayer;
import net.axda.se.api.script.LL;
import net.axda.se.listen.ListenMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

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
        unzipAll();
        for (File file : path.listFiles()) {
            if ((file.isFile() && file.getName().endsWith(".js")) || file.isDirectory()) {
                ScriptLoader.getInstance().loadPlugin(file);
            }
        }
        ListenMap.call("onServerStarted");
    }

    public void loadPlugin(File file) {
        try {
            if (file.isFile()) {
                String script = Utils.readFile(file);
                loadPlugin(script, file);
            } else {
                File meta = new File(file + "/manifest.json");
                if (meta.exists()) {
                    String s = Utils.readFile(meta);
                    Manifest manifest = API.GSON.fromJson(s, Manifest.class);
                    String script = Utils.readFile(file + "/" + manifest.entry);
                    loadPlugin(script, new File(file + "/" + manifest.entry), manifest);
                }
            }
        } catch (Exception e) {
            logger.log(LogLevel.ERROR, "Read file failed: " + file.getName(), e);
        }
    }

    public void loadPlugin(String script) {
        loadPlugin(script, null);
    }

    public void loadPlugin(String script, File file, Manifest manifest) {
        counter++;
        ScriptExecTask task = new ScriptExecTask(counter, script, file, manifest);
//        TaskHandler taskHandler = Server.getInstance().getScheduler().scheduleAsyncTask(AXDAScriptEngine.getPlugin(), task);
//        task.setTaskId(taskHandler.getTaskId());
        this.nowEngine = task.getEngine();
        putEngine(task.getEngine());
        task.onRun();
        this.nowEngine = null;
    }

    public void loadPlugin(String script, File file) {
        loadPlugin(script, file, null);
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
        getEngineNow().putCloseable(c);
    }

    public ScriptEngine getEngine(String name) {
        for (ScriptEngine engine : engines.values()) {
            if (engine.getDescription().getName().equals(name)) {
                return engine;
            }
        }
        return null;
    }

    public ScriptEngine getEngineNow() {
        if (nowEngine == null) {
            return ListenMap.nowEngine;
        } else {
            return nowEngine;
        }
    }

    public void unzipAll() {
        File[] list = AXDAScriptEngine.PLUGIN_PATH.listFiles();
        for (File file : list) {
            if (file.isFile() && file.getName().endsWith(".zip")) {
                unzip(file);
            }
        }
    }

    public void unzip(File zipFile) {
        try (ZipFile zip = new ZipFile(zipFile)) {
            ArrayList<String> list = new ArrayList<>();
            zip.stream().forEach(entry -> {
                if (entry.getName().endsWith("/manifest.json")) {
                    String path = entry.getName().replace("/manifest.json", "/");
                    list.add(path);
                }
            });
            for (String path : list) {
                extractDirectory(zipFile, path, "plugins/" + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压ZIP文件中的指定目录
     * @param zipFile ZIP文件
     * @param sourceDirectory ZIP中的源目录（如 "scripts/"）
     * @param targetDirectory 目标目录路径
     */
    public void extractDirectory(File zipFile, String sourceDirectory, String targetDirectory) {
        try (ZipFile zip = new ZipFile(zipFile)) {
            // 标准化目录路径（确保以/结尾）
            String normalizedSourceDir = sourceDirectory;
            if (!normalizedSourceDir.endsWith("/")) {
                normalizedSourceDir += "/";
            }

            // 创建目标目录
            Path targetPath = Paths.get(targetDirectory);
            if (!Files.exists(targetPath)) {
                Files.createDirectories(targetPath);
            } else {
                return;
            }

            // 提取指定目录下的所有文件
            String finalNormalizedSourceDir = normalizedSourceDir;
            String finalNormalizedSourceDir1 = normalizedSourceDir;
            zip.stream()
                    .filter(entry -> !entry.isDirectory())
                    .filter(entry -> entry.getName().startsWith(finalNormalizedSourceDir))
                    .forEach(entry -> {
                        try {
                            // 计算相对路径
                            String relativePath = entry.getName().substring(finalNormalizedSourceDir1.length());
                            Path outputPath = targetPath.resolve(relativePath).normalize();

                            // 安全检查：确保输出路径在目标目录内
                            if (outputPath.startsWith(targetPath)) {
                                // 创建父目录
                                Files.createDirectories(outputPath.getParent());

                                // 解压文件
                                try (InputStream is = zip.getInputStream(entry)) {
                                    Files.copy(is, outputPath, StandardCopyOption.REPLACE_EXISTING);
                                }
                                AXDAScriptEngine.getPlugin().getLogger().info("Unzip: " + entry.getName());
                            } else {
                                AXDAScriptEngine.getPlugin().getLogger().warning("Security warning. Skipping unsafe path: " + entry.getName());
                            }
                        } catch (IOException e) {
                            MainLogger.getLogger().logException(e);
                        }
                    });
        } catch (Exception e) {
            MainLogger.getLogger().logException(e);
        }
    }

    public void logException(Throwable e) {
        nowEngine.logException(e);
    }

}
