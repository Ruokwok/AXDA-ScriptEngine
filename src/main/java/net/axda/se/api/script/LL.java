package net.axda.se.api.script;

import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;

import java.util.Map;

public class LL extends API {

    @HostAccess.Export
    public void registerPlugin(String name, String desc, Object[] ver, Map<String, Object> info) {
        engine.getDescription().setName(name);
        engine.getDescription().setDescription(desc);
        engine.getDescription().setVersion(ver);
        engine.getDescription().setInfo(info);
    }

}
