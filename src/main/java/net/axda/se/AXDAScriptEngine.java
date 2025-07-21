package net.axda.se;

import cn.nukkit.plugin.PluginBase;

import java.io.File;

public class AXDAScriptEngine extends PluginBase {

    public static final File PLUGIN_PATH = new File("plugins");
    private static AXDAScriptEngine plugin;
    private static int[] version;

    @Override
    public void onLoad() {
        plugin = this;
        System.setProperty("polyglot.engine.WarnInterpreterOnly", "false");
        getServer().getCommandMap().register("ase", new ASECommand());
        ScriptLoader.init();
        ScriptLoader.getInstance().loadPlugins(PLUGIN_PATH);
    }

    @Override
    public void onEnable() {

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
