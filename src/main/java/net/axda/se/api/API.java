package net.axda.se.api;

import cn.nukkit.Server;
import net.axda.se.Engine;

public class API {

    protected Engine engine;

    public API setEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    public Engine getEngine() {
        return engine;
    }

    public void print(String a) {
        Server.getInstance().getLogger().info(a);
    }

}
