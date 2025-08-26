package net.axda.se;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import net.axda.se.listen.ListenEvent;
import org.graalvm.polyglot.Context;

import java.io.File;

public class AXDAScriptEngine extends PluginBase {

    public static final File PLUGIN_PATH = new File("plugins");
    private static AXDAScriptEngine plugin;
    private static int[] version;
    private ScriptListener listener;

    @Override
    public void onLoad() {
        plugin = this;
        System.setProperty("polyglot.engine.WarnInterpreterOnly", "false");
    }

    @Override
    public void onEnable() {
        if (testEngine()) {
            testDepends();
            getServer().getCommandMap().register("ase", new ASECommand());
            ListenEvent.getAllEvents();
            ScriptLoader.init();
            ScriptLoader.getInstance().loadPlugins(PLUGIN_PATH);
            listener = new ScriptListener();
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        ScriptLoader.getInstance().disablePlugins();
    }

    public int[] getVersion(String versionStr) {
        if (versionStr == null) {
            versionStr = getDescription().getVersion();
            if (version != null) return version;
        }
        try {
            String[] version = versionStr.split("\\.");
            int[] v = new int[3];
            for (int i = 0; i < version.length; i++) {
                v[i] = Integer.parseInt(version[i]);
            }
            return v;
        } catch (Throwable t) {
            return new int[]{0, 0, 0};
        }
    }

    public boolean testEngine() {
        getLogger().info("Testing JavaScript engine.");
        try {
            Context engine = Context.newBuilder("js").allowAllAccess(true).build();
            engine.eval("js", "var a = 1;");
            return true;
        } catch (IllegalArgumentException e) {
//            Logger.logException(e);
            getLogger().error("The js module is not installed, please install it and try again.");
            getLogger().error("If you using GraalVM, please try execute command: gu install js");
            return false;
        }
    }

    public void testDepends() {
        Plugin kernel = getServer().getPluginManager().getPlugin("AXDA-Kernel");
        for (Depends depend : Depends.values()) {
//            try {
//                Class.forName(depend.getClazz());
//            } catch (ClassNotFoundException e) {
            if (getServer().getPluginManager().getPlugin(depend.getName()) == null) {
                getLogger().warning("Plugin '" + depend.getName() + "' is not installed, some JS api is unusable.");
                if (kernel != null) {
                    getLogger().warning("You can use the command to install: §aaxda install " + depend.getIndex());
                }
            }
        }
    }

    public static AXDAScriptEngine getPlugin() {
        return plugin;
    }
}
