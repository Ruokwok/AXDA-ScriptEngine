package net.axda.se;

import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;
import net.axda.se.api.data.KVDatabase;
import net.axda.se.api.function.LogFunction;
import net.axda.se.api.function.SetIntervalFunction;
import net.axda.se.api.game.MC;
import net.axda.se.api.script.LL;
import net.axda.se.api.script.Logger;
import net.axda.se.api.system.ScriptFileUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.Closeable;
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
    private List<Closeable> closeables = new ArrayList<>();
    private AsyncTask scriptTask;
    private int threadId;

    public ScriptEngine(String script, File file, int threadId) {
        ScriptLoader loader = ScriptLoader.getInstance();
        this.SCRIPT = script;
        this.threadId = threadId;
        this.context = Context.newBuilder("js")
                .allowAllAccess(false).build();
        Value js = this.context.getBindings("js");
        js.putMember("ll", new LL().setEngine(this));
        js.putMember("logger", new Logger().setEngine(this));
        js.putMember("KVDatabase", KVDatabase.class);
        js.putMember("File", new ScriptFileUtils().setEngine(this));
        js.putMember("mc", new MC().setEngine(this));
        js.putMember("log", new LogFunction().setEngine(this));
        js.putMember("setInterval", new SetIntervalFunction().setEngine(this));
        this.context.eval("js", loader.getRuntime());
        this.description = new ScriptDescription();
        this.description.setFile(file);
    }

    public void execute() {
        ScriptEngine engine = this;
        this.scriptTask = new AsyncTask() {
            @Override
            public void onRun() {
                Thread.currentThread().setName(getThreadName());
                context.eval("js", SCRIPT);
                Server.getInstance().getLogger().info("Loaded " + engine.getDescription().getName() + " v" + engine.getDescription().getVersionStr());
            }
        };
        Server.getInstance().getScheduler().scheduleAsyncTask(AXDAScriptEngine.getPlugin(), scriptTask);
    }

    public void disable() {
        this.context.close();
        for (int taskId: tasks) {
            Server.getInstance().getScheduler().cancelTask(taskId);
        }
        tasks.clear();
        for (Closeable closeable: closeables) {
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

    public void putCloseable(Closeable closeable) {
        closeables.add(closeable);
    }

    public String getThreadName() {
        return "Script-" + getDescription().getName() + "-" + threadId;
    }

    public AsyncTask getTask() {
        return this.scriptTask;
    }

}
