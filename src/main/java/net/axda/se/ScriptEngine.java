package net.axda.se;

import cn.nukkit.Server;
import net.axda.se.api.data.KVDatabase;
import net.axda.se.api.function.LogFunction;
import net.axda.se.api.function.SetIntervalFunction;
import net.axda.se.api.game.MC;
import net.axda.se.api.script.LL;
import net.axda.se.api.script.Logger;
import net.axda.se.api.system.ScriptFileUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

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

    public ScriptEngine(String script) {
        ScriptLoader loader = ScriptLoader.getInstance();
        this.SCRIPT = script;
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
    }

    public void execute() {
        this.context.eval("js", SCRIPT);
    }

    public void disable() {
        this.context.close();
        for (int taskId: tasks) {
            Server.getInstance().getScheduler().cancelTask(taskId);
        }
        tasks.clear();
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

}
