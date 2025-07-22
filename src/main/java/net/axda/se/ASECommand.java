package net.axda.se;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import java.util.Collection;

public class ASECommand extends Command {

    public ASECommand() {
        super("ase");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender.isOp()) {
            if (args[0].equals("reload")) {
                reload();
            } else if (args[0].equals("ls")) {
                ls(sender);
            }
        }
        return false;
    }

    public void reload() {
        ScriptLoader.getInstance().loadPlugins(AXDAScriptEngine.PLUGIN_PATH);
    }

    public void ls(CommandSender sender) {
        ScriptLoader loader = ScriptLoader.getInstance();
        Collection<ScriptEngine> engines = loader.getScriptMap().values();
        StringBuilder sb = new StringBuilder();
        sb.append("Script Plugins (").append(engines.size()).append("):");
        for (ScriptEngine engine : loader.getScriptMap().values()) {
            sb.append(" ").append(engine.getDescription().getName()).append(" v")
                    .append(engine.getDescription().getVersionStr())
                    .append(",");
        }
        sender.sendMessage(sb.toString());
    }
}
