package net.axda.se.api.script;

import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;

import java.util.Map;

public class LL extends API {

    @HostAccess.Export
    public void registerPlugin(String name, String desc, String[] ver, Map<String, Object> info) {
        engine.getDescription().name = name;
        engine.getDescription().description = desc;
        engine.getDescription().version = ver;
        engine.getDescription().info = info;
    }

}
