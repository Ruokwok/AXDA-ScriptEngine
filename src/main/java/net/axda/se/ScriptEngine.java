package net.axda.se;

import cn.nukkit.Server;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.MainLogger;
import net.axda.se.api.data.JsonConfigFile;
import net.axda.se.api.data.KVDatabase;
import net.axda.se.api.function.ClearIntervalFunction;
import net.axda.se.api.function.LogFunction;
import net.axda.se.api.function.SetIntervalFunction;
import net.axda.se.api.function.SetTimeoutFunction;
import net.axda.se.api.game.MC;
import net.axda.se.api.game.data.FloatPos;
import net.axda.se.api.game.data.IntPos;
import net.axda.se.api.script.LL;
import net.axda.se.api.script.Logger;
import net.axda.se.api.system.Network;
import net.axda.se.api.system.ScriptFileUtils;
import net.axda.se.api.system.ScriptSystem;
import net.axda.se.api.system.ScriptWSClient;
import net.axda.se.exception.ScriptExecuteException;
import net.axda.se.listen.ListenMap;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * 脚本插件对象
 * @author Ruok
 */
public class ScriptEngine {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ScriptEngine.class);
    private final String SCRIPT;
    private Context context;
    private ScriptDescription description;
    private Map<String, Value> listeners = new HashMap<>();
    private List<Task> tasks = new ArrayList<>();
    private List<AutoCloseable> closeables = new ArrayList<>();
    private Manifest manifest;
    private String uuid = UUID.randomUUID().toString();
    private boolean executed = false;
    private Value unloadFunction;
    private MainLogger logger = Server.getInstance().getLogger();

    public ScriptEngine(String script, File file, Manifest manifest) {
//        Thread.currentThread().setName(getThreadName());
        this.SCRIPT = script;
        this.manifest = manifest;
        this.context = Context.newBuilder("js")
                .allowAllAccess(false)
                .allowCreateThread(true)
                .build();
        registerAPI();
        this.description = new ScriptDescription();
        this.description.setFile(file);
        if (manifest != null) {
            this.description.setName(manifest.name);
        }
    }

    /**
     * 注册插件运行环境所需API
     */
    public void registerAPI() {
        Value js = this.context.getBindings("js");
        //对象
        js.putMember("ll", new LL().setEngine(this));
        js.putMember("logger", new Logger().setEngine(this));
        js.putMember("File", new ScriptFileUtils().setEngine(this));
        js.putMember("system", new ScriptSystem().setEngine(this));
        js.putMember("mc", new MC().setEngine(this));
        js.putMember("log", new LogFunction().setEngine(this));
        js.putMember("network", Network.getInstance());

        //函数
        js.putMember("setInterval", new SetIntervalFunction().setEngine(this));
        js.putMember("setTimeout", new SetTimeoutFunction().setEngine(this));
        js.putMember("clearInterval", new ClearIntervalFunction().setEngine(this));

        //类
        js.putMember("JsonConfigFile", JsonConfigFile.class);
        js.putMember("KVDatabase", KVDatabase.class);
        js.putMember("WSClient", ScriptWSClient.class);
        js.putMember("IntPos", IntPos.class);
        js.putMember("FloatPos", FloatPos.class);
    }

    public synchronized Value execute(Value value) throws ScriptExecuteException {
        try {
            if (value == null) {
                if (executed) throw new RuntimeException("JS plugin context has already been executed.");
                executed = true;
                context.enter();
                context.eval("js", ScriptLoader.getInstance().getRuntime());
                context.eval("js", SCRIPT);
                context.leave();
                Server.getInstance().getLogger().info("Loaded " + getDescription().getName() + " v" + getDescription().getVersionStr());
            } else {
                context.enter();
                Value back = value.execute();
                context.leave();
                return back;
            }
            return null;
        } catch (Exception e) {
            logException(new ScriptExecuteException(e));
            return null;
        }
    }

    public void disable() {
        if (unloadFunction != null) {
            unloadFunction.executeVoid();
        }
        this.context.close();
        ListenMap.remove(this);
        for (Task task: tasks) {
            task.cancel();
        }
        tasks.clear();
        for (AutoCloseable closeable: closeables) {
            try {
                closeable.close();
            } catch (Exception e) {
                logException(e);
            }
        }
        closeables.clear();
        Server.getInstance().getLogger().info("Unloaded " + getDescription().getName() + " v" + getDescription().getVersionStr());
    }

    public void logException(Throwable e) {
        if (e.getMessage().startsWith("net.axda.se.exception.")) {
            logExceptionTitle(e.getMessage());
            logger.logException(e);
            return;
        }
        if (e instanceof ScriptExecuteException see) {
            Throwable cause = see.getCause();
            if (cause == null) {
                cause = see;
            }
            logExceptionTitle(cause.getMessage());
            logger.logException(cause);
        } else {
            logExceptionTitle(e.getMessage());
            Server.getInstance().getLogger().logException(e);
        }
    }

    public void logExceptionTitle(String e) {
        logger.error(e + ": " + getDescription().getName() + " (" + getDescription().getFile() + ")");
    }

    public void registerEvent(String event, Value callback) {
        listeners.put(event, callback);
    }

    public void callEvent(String event, Object... args) {
        if (listeners.containsKey(event)) {
            listeners.get(event).execute(args);
        }
    }

    public ScriptDescription getDescription() {
        return description;
    }

    public Context getContext() {
        return context;
    }

    public void putCloseable(AutoCloseable closeable) {
        closeables.add(closeable);
    }

    public String getUUID() {
        return uuid;
    }

    public void putTask(Task task) {
        tasks.add(task);
    }

    public void setUnloadFunction(Value value) {
        this.unloadFunction = value;
    }

    @Override
    public String toString() {
        return "jsplugin-" + getDescription().getName() + "@" + this.hashCode();
    }
}
