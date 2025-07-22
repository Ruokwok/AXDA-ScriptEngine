package net.axda.se;

import net.axda.se.api.data.KVDatabase;
import net.axda.se.api.script.LL;
import net.axda.se.api.script.Logger;
import net.axda.se.api.system.ScriptFileUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class Engine {

    private final String SCRIPT;
    private Context context;
    private ScriptDescription description;

    public Engine(String script) {
        ScriptLoader loader = ScriptLoader.getInstance();
        this.SCRIPT = script;
        this.context = Context.newBuilder("js")
                .allowAllAccess(false).build();
        Value js = this.context.getBindings("js");
        js.putMember("ll", new LL().setEngine(this));
        js.putMember("logger", new Logger().setEngine(this));
        js.putMember("KVDatabase", KVDatabase.class);
        js.putMember("File", new ScriptFileUtils().setEngine(this));
        this.context.eval("js", loader.getRuntime());
        this.description = new ScriptDescription();
    }

    public void execute() {
        this.context.eval("js", SCRIPT);
    }

    public void stop() {
        this.context.close();
    }

    public ScriptDescription getDescription() {
        return description;
    }

}
