package net.axda.se;

import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;
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
import net.axda.se.api.system.ScriptFileUtils;
import net.axda.se.api.system.ScriptSystem;
import net.axda.se.exception.ScriptExecuteException;
import net.axda.se.listen.ListenMap;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptEngine {

    private final String SCRIPT;
    private Context context;
    private ScriptDescription description;
    private Map<String, Value> listeners = new HashMap<>();
    private List<Integer> tasks = new ArrayList<>();
    private List<AutoCloseable> closeables = new ArrayList<>();
    private AsyncTask scriptTask;
    private int threadId;

    public ScriptEngine(String script, File file, int threadId, AsyncTask scriptTask) {
        this.threadId = threadId;
        Thread.currentThread().setName(getThreadName());
        this.SCRIPT = script;
        this.scriptTask = scriptTask;
        this.context = Context.newBuilder("js")
                .allowAllAccess(false)
                .allowHostAccess(HostAccess.ALL)
                .allowCreateThread(true)
                .build();
        registerAPI();
        this.description = new ScriptDescription();
        this.description.setFile(file);
    }

    public void registerAPI() {
        Value js = this.context.getBindings("js");
        //对象
        js.putMember("ll", new LL().setEngine(this));
        js.putMember("logger", new Logger().setEngine(this));
        js.putMember("File", new ScriptFileUtils().setEngine(this));
        js.putMember("system", new ScriptSystem().setEngine(this));
        js.putMember("mc", new MC().setEngine(this));
        js.putMember("log", new LogFunction().setEngine(this));

        //函数
        js.putMember("setInterval", new SetIntervalFunction().setEngine(this));
        js.putMember("setTimeout", new SetTimeoutFunction().setEngine(this));
        js.putMember("clearInterval", new ClearIntervalFunction().setEngine(this));

        //类
        js.putMember("KVDatabase", KVDatabase.class);
        js.putMember("IntPos", IntPos.class);
        js.putMember("FloatPos", FloatPos.class);
    }

    public void execute() throws ScriptExecuteException {
        try {
            context.enter();
            context.eval("js", ScriptLoader.getInstance().getRuntime());
            context.eval("js", SCRIPT);
            context.leave();
            Server.getInstance().getLogger().info("Loaded " + getDescription().getName() + " v" + getDescription().getVersionStr());
        } catch (Exception e) {
            throw new ScriptExecuteException(e, getDescription().getFile().getPath());
        }
    }

    public void disable() {
        this.context.close();
        ListenMap.remove(this);
        for (int taskId: tasks) {
            Server.getInstance().getScheduler().cancelTask(taskId);
        }
        tasks.clear();
        for (AutoCloseable closeable: closeables) {
            try {
                closeable.close();
            } catch (Exception e) {
                Server.getInstance().getLogger().logException(e);
            }
        }
        closeables.clear();
        Server.getInstance().getScheduler().cancelTask(scriptTask.getTaskId());
        Server.getInstance().getLogger().info("Unloaded " + getDescription().getName() + " v" + getDescription().getVersionStr());
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

    public String getThreadName() {
        return "js-" + threadId;
    }

    public AsyncTask getTask() {
        return this.scriptTask;
    }

}
