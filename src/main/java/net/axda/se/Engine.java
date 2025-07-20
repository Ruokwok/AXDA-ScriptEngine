package net.axda.se;

import net.axda.se.api.script.LL;
import net.axda.se.api.script.Logger;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

public class Engine {

    private final String SCRIPT;
    private Context context;
    private ScriptDescription description;

    public Engine(String script) {
        ScriptLoader loader = ScriptLoader.getInstance();
        this.SCRIPT = script;
        this.context = Context.newBuilder("js")
                .allowAllAccess(false).build();
        this.context.getBindings("js").putMember("ll", new LL().setEngine(this));
        this.context.getBindings("js").putMember("logger", new Logger().setEngine(this));
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
