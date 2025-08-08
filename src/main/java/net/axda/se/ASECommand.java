package net.axda.se;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ASECommand extends Command {

    private Map<String, ScriptCommand> cmds = new HashMap<>();

    public ASECommand() {
        super("ase");
        cmds.put("reload", this::reload);
        cmds.put("ls", this::ls);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender.isOp()) {
            if (args.length == 0) {
                help(sender, args);
            } else {
                ScriptCommand command = cmds.get(args[0]);
                if (command == null) {
                    help(sender, args);
                } else {
                    command.execute(sender, args);
                }
            }
        }
        return false;
    }

    public boolean reload(CommandSender sender, String[] args) {
        ScriptLoader.getInstance().disablePlugins();
        ScriptLoader.getInstance().loadPlugins(AXDAScriptEngine.PLUGIN_PATH);
        return false;
    }

    public boolean help(CommandSender sender, String[] args) {
        sender.sendMessage("AXDA ScriptEngine - v" + AXDAScriptEngine.getPlugin().getDescription().getVersion());
        sender.sendMessage("Usage: /ase <command>");
        sender.sendMessage("reload    §aReload all JS plugin.");
        sender.sendMessage("ls        §aPrint loaded JS plugin list.");
        return true;
    }

    public boolean ls(CommandSender sender, String[] args) {
        ScriptLoader loader = ScriptLoader.getInstance();
        Collection<ScriptEngine> engines = loader.getScriptMap().values();
        StringBuilder sb = new StringBuilder();
        sb.append("Script Plugins (").append(engines.size()).append("):" + TextFormat.getByChar('a'));
        for (ScriptEngine engine : loader.getScriptMap().values()) {
            sb.append(" ").append(engine.getDescription().getName()).append(" v")
                    .append(engine.getDescription().getVersionStr())
                    .append(TextFormat.getByChar('r') + ",");
        }
        sender.sendMessage(sb.toString());
        return true;
    }

    public interface ScriptCommand {
        boolean execute(CommandSender sender, String[] args);
    }
}
