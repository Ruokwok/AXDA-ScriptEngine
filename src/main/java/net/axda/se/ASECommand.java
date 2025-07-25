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
            if (cmds.containsKey(args[0])) {
                return cmds.get(args[0]).execute(sender, args);
            }
        }
        return false;
    }

    public boolean reload(CommandSender sender, String[] args) {
        ScriptLoader.getInstance().disablePlugins();
        ScriptLoader.getInstance().loadPlugins(AXDAScriptEngine.PLUGIN_PATH);
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
