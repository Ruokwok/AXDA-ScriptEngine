package net.axda.se;

import cn.nukkit.plugin.PluginBase;

import java.io.File;

public class AXDAScriptEngine extends PluginBase {

    public static final File PLUGIN_PATH = new File("plugins");

    @Override
    public void onLoad() {
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
}
