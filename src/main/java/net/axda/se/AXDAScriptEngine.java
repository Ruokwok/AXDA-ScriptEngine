package net.axda.se;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import net.axda.se.listen.ListenEvent;

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
        getServer().getCommandMap().register("ase", new ASECommand());
        ListenEvent.getAllEvents();
        listener = new ScriptListener();
        ScriptLoader.init();
        ScriptLoader.getInstance().loadPlugins(PLUGIN_PATH);
        getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {

    }

    public int[] getVersion() {
        if (version != null) return version;
        try {
            String[] version = getDescription().getVersion().split("\\.");
            int[] v = new int[3];
            for (int i = 0; i < version.length; i++) {
                v[i] = Integer.parseInt(version[i]);
            }
            return v;
        } catch (Throwable t) {
            return new int[]{0, 0, 0};
        }
    }

    public static AXDAScriptEngine getPlugin() {
        return plugin;
    }
}
