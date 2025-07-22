package net.axda.se.api;

import cn.nukkit.Server;
import net.axda.se.ScriptEngine;

public class API {

    protected ScriptEngine engine;

    public API setEngine(ScriptEngine engine) {
        this.engine = engine;
        return this;
    }

    public ScriptEngine getEngine() {
        return engine;
    }

    public void print(String a) {
        Server.getInstance().getLogger().info(a);
    }

}
