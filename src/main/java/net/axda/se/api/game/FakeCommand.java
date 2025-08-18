package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.SimpleCommandMap;
import net.axda.se.ScriptLoader;
import org.graalvm.polyglot.Value;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class FakeCommand extends Command implements AutoCloseable {

    private Value callback;
    private int level = -1;
    private boolean console;
    private ScriptLoader loader = ScriptLoader.getInstance();

    public FakeCommand(String name, String description, Value callback) {
        super(name, description);
        loader.putCloseable(this);
        this.callback = callback;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            if (level == 0) {
                return executeCallback(player, args);
            } else if (level == 1 && player.isOp()) {
                return executeCallback(player, args);
            } else {
                return false;
            }
        } else if (console) {
            return executeCallback(null, args);
        }
        return false;
    }

    @Override
    public void close() {
        SimpleCommandMap commandMap = Server.getInstance().getCommandMap();
        try {
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, Command> knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);
            knownCommands.remove(getName());
            knownCommands.remove(getName() + ":" + getName());
        } catch (Exception e) {
        }
        unregister(commandMap);
        Collection<Player> players = Server.getInstance().getOnlinePlayers().values();
        for (Player player : players) {
            player.sendCommandData();
        }
    }

    public boolean executeCallback(Player player, String[] args) {
        if (player == null) {
            callback.executeVoid(args);
        } else {
            callback.executeVoid(loader.getPlayer(player), args);
        }
        return true;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setConsole(boolean console) {
        this.console = console;
    }
}