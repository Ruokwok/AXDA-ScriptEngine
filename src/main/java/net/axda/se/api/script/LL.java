package net.axda.se.api.script;

import cn.nukkit.Server;
import net.axda.se.AXDAScriptEngine;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.util.Map;

public class LL extends API {

    @HostAccess.Export
    public final String language;

    @HostAccess.Export
    public final int major;

    @HostAccess.Export
    public final int minor;

    @HostAccess.Export
    public final int revision;

    @HostAccess.Export
    public final int status = 0;

    @HostAccess.Export
    public final String scriptEngineVersion = "22.3.0";

    @HostAccess.Export
    public final boolean isWine = false;

    @HostAccess.Export
    public final boolean isDebugMode = false;


    @HostAccess.Export
    public final boolean isBeta;


    @HostAccess.Export
    public final boolean isDev;


    @HostAccess.Export
    public final boolean isRelease;


    @HostAccess.Export
    public final String pluginsRoot = AXDAScriptEngine.PLUGIN_PATH.getAbsolutePath();

    public LL() {
        Server server = Server.getInstance();
        this.language = server.getLanguage().getLang();
        AXDAScriptEngine plugin = AXDAScriptEngine.getPlugin();
        int[] version = plugin.getVersion();
        this.major = version[0];
        this.minor = version[1];
        this.revision = version[2];
        this.isDev = status == 0;
        this.isBeta = status == 1;
        this.isRelease = status == 2;
    }

    @HostAccess.Export
    public void registerPlugin(String name, String desc, Object[] ver, Map<String, Object> info) {
        engine.getDescription().setName(name);
        engine.getDescription().setDescription(desc);
        engine.getDescription().setVersion(ver);
        engine.getDescription().setOthers(info);
    }

    @HostAccess.Export
    public String versionString() {
        return AXDAScriptEngine.getPlugin().getDescription().getVersion();
    }

    @HostAccess.Export
    public void onUnload(Value value) {
        engine.setUnloadFunction(value);
    }

}
