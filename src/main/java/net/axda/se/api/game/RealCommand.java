package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.axda.se.AXDAScriptEngine;
import net.axda.se.ScriptEngine;
import net.axda.se.ScriptLoader;
import net.axda.se.api.API;
import net.axda.se.api.game.data.FloatPos;
import net.axda.se.api.game.data.IntPos;
import net.axda.se.api.game.data.ProxyMap;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.util.HashMap;

public class RealCommand extends Command implements AutoCloseable {

    private int prem;
    private int flag;
    private HashMap<String, String[]> _enum = new HashMap<>();
    private HashMap<String, String[]> params = new HashMap<>();
    private ScriptLoader loader = ScriptLoader.getInstance();
    private Value callback;

    public RealCommand(String cmd, String desc, int prem, int flag, String[] alias) {
        super(cmd, desc);
        super.setAliases(alias);
        loader.putCloseable(this);
        this.prem = prem;
        this.flag = flag;
        this.loader = ScriptLoader.getInstance();
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        ProxyMap<Object> origin = new ProxyMap<>();
        origin.put("type", null);
        origin.put("name", sender.getName());
        if (sender instanceof Player player) {
            origin.put("pos", new FloatPos(player, player.getLevel()));
            origin.put("blockPos", new IntPos(player, player.getLevel()));
            origin.put("entity", loader.getPlayer(player));
            origin.put("player", loader.getPlayer(player));
        }
        return false;
    }

    @HostAccess.Export
    public boolean setAlias(String... alias) {
        super.setAliases(alias);
        return true;
    }

    @HostAccess.Export
    public String setEnum(String name, String[] values) {
        this._enum.put(name, values);
        return name;
    }

    @HostAccess.Export
    public boolean mandatory(String name, int type, String enumName, String identifier, int enumOptions) {
        String key = name;
        if (type == 14 && enumName != null) {
            if (this._enum.containsKey(enumName)) {
                key = enumName;
            } else return false;
        }
        this.params.put(key, new String[] {name, String.valueOf(type), enumName, identifier, String.valueOf(enumOptions), "false"});
        return true;
    }

    @HostAccess.Export
    public boolean optional(String name, int type, String enumName, String identifier, int enumOptions) {
        String key = name;
        if (type == 14 && enumName != null) {
            if (this._enum.containsKey(enumName)) {
                key = enumName;
            } else return false;
        }
        this.params.put(key, new String[] {name, String.valueOf(type), enumName, identifier, String.valueOf(enumOptions), "true"});
        return true;
    }

    @HostAccess.Export
    public boolean optional(String name, int type, String enumName) {
        return optional(name, type, enumName, enumName, 0);
    }

    @HostAccess.Export
    public boolean optional(String name, int type) {
        return optional(name, type, null);
    }

    @HostAccess.Export
    public boolean overload(String[] params) {
        return true;
    }

    @HostAccess.Export
    public boolean setCallback(Value callback) {
        this.callback = callback;
        return true;
    }

    @HostAccess.Export
    public boolean setup() {
        ScriptEngine engine = loader.getEngineNow();
        AXDAScriptEngine.getPlugin().getLogger().
                warning("Real Command API is unsupported. Command '" + getName() + "' is not available. (" + engine.getDescription().getName() + ")");
        return Server.getInstance().getCommandMap().register("ase", this);
    }

    @Override
    public void close() throws Exception {
        API.unregisterCommand(this);
    }

    public static class Output {

        private CommandSender sender;

        public Output(CommandSender sender) {
            this.sender = sender;
        }

        @HostAccess.Export
        public void addMessage(String message, String[] prams) {
            sender.sendMessage(message);
        }

        @HostAccess.Export
        public void addMessage(String message) {
            addMessage(message, null);
        }

        @HostAccess.Export
        public void success(String message, String[] prams) {
            addMessage(message, prams);
        }

        @HostAccess.Export
        public void success(String message) {
            addMessage(message);
        }

        @HostAccess.Export
        public void error(String message, String[] prams) {
            addMessage("§c" + message);
        }

        @HostAccess.Export
        public void error(String message) {
            addMessage("§c" + message);
        }

    }

}
