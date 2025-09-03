package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import net.axda.se.ScriptLoader;
import net.axda.se.api.API;
import net.axda.se.api.game.data.Pos;
import net.axda.se.api.gui.CustomForm;
import net.axda.se.api.gui.SimpleForm;
import net.axda.se.exception.UnsupportedMemberException;
import net.axda.se.exception.ValueTypeException;
import net.axda.se.listen.Listen;
import net.axda.se.listen.ListenEvent;
import net.axda.se.listen.ListenMap;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;

import java.util.*;

public class MC extends API {

    private Server server = Server.getInstance();

    @HostAccess.Export
    public boolean listen(String event, Value value) {
        List<String> allEvents = ListenEvent.getAllEvents();
        if (allEvents.contains(event)) {
            engine.registerEvent(event, value);
            return ListenMap.put(event, new Listen(engine, event, value));
        }
        return false;
    }

    @HostAccess.Export
    public String getBDSVersion() {
        return "v" + server.getVersion();
    }

    @HostAccess.Export
    public int getServerProtocolVersion() {
        return 0;
    }

    @HostAccess.Export
    public boolean setMotd(String motd) {
        server.setPropertyString("motd", motd);
        return true;
    }

    @HostAccess.Export
    public boolean setMaxPlayers(int size) {
        server.setMaxPlayers(size);
        return true;
    }

    @HostAccess.Export
    public int getTime(Value value) {
        Level level = API.getLevel(value);
        if (level != null) {
            return level.getTime();
        }
        return 0;
    }

    @HostAccess.Export
    public int getTime() {
        return getTime(Value.asValue(0));
    }

    @HostAccess.Export
    public boolean setTime(int time, Value value) {
        Level level = API.getLevel(value);
        if (level == null) return false;
        level.setTime(time);
        return false;
    }

    @HostAccess.Export
    public boolean setTime(int time) {
        return setTime(time, Value.asValue(0));
    }

    @HostAccess.Export
    public int getWeather(Value value) {
        Level level = API.getLevel(value);
        if (level == null) return 0;
        if (level.isRaining()) return 1;
        if (level.isThundering()) return 2;
        return 0;
    }

    @HostAccess.Export
    public int getWeather() {
        return getWeather(Value.asValue(0));
    }

    @HostAccess.Export
    public boolean setWeather(int weather, Value value) {
        Level level = API.getLevel(value);
        if (level == null) {
            return false;
        } else {
            switch (weather) {
                case 0:
                    level.setRaining(false);
                    level.setThundering(false);
                    break;
                case 1:
                    level.setRaining(true);
                    level.setThundering(false);
                    break;
                case 2:
                    level.setRaining(true);
                    level.setThundering(true);
                    break;
                default:
                    return false;
            }
            return true;
        }
    }

    @HostAccess.Export
    public boolean setWeather(int weather) {
        return setWeather(weather, Value.asValue(0));
    }

    @HostAccess.Export
    public ScriptPlayer getPlayer(String info) {
        Player player = server.getPlayer(info);
        Map<UUID, Player> players = Server.getInstance().getOnlinePlayers();
        for (Player pl : players.values()) {
            if (pl.getLoginChainData().getXUID().equals(info)) {
                player = pl;
                break;
            }
        }
        if (player == null) {
            try {
                Optional<Player> pl = server.getPlayer(UUID.fromString(info));
                player = pl.get();
            } catch (Exception e) {
            }
        }
        if (player != null) {
            return ScriptLoader.getInstance().getPlayer(player);
        }
        return null;
    }

    @HostAccess.Export
    public ScriptPlayer getPlayer(int networkId) {
        return null;
    }

    @HostAccess.Export
    public ProxyArray getOnlinePlayers() {
        List<ScriptPlayer> players = ScriptLoader.getInstance().getOnlinePlayers();
        return ProxyArray.fromList(new ArrayList<>(players));
    }

    @HostAccess.Export
    public boolean broadcast(String msg, int type) {
        server.broadcastMessage(msg);
        return true;
    }

    @HostAccess.Export
    public boolean broadcast(String msg) {
        return broadcast(msg, 0);
    }

    @HostAccess.Export
    public boolean runcmd(Value value) {
        try {
            String cmd = value.asString();
            return server.dispatchCommand(server.getConsoleSender(), cmd);
        } catch (Exception e) {
            throw new ValueTypeException(e);
        }
    }

    @HostAccess.Export
    public HashMap<String, Object> runcmdEx(Value value) {
        try {
            String cmd = value.asString();
            HashMap<String, Object> map = new HashMap<>();
            ConsoleCommandSender sender = new ConsoleCommandSender() {
                @Override
                public void sendMessage(String message) {
                    message = this.getServer().getLanguage().translateString(message);
                    map.put("output", message);
                }
            };
            boolean b = server.dispatchCommand(sender, cmd);
            map.put("success", b);
            return map;
        } catch (Exception e) {
            throw new ValueTypeException();
        }
    }

    @HostAccess.Export
    public SimpleForm newSimpleForm() {
        return new SimpleForm();
    }

    @HostAccess.Export
    public CustomForm newCustomForm() {
        return new CustomForm();
    }

    @HostAccess.Export
    public boolean regPlayerCmd(String cmd, String description, Value callback, int level) {
        FakeCommand command = new FakeCommand(cmd, description, callback);
        command.setLevel(level);
        return server.getCommandMap().register(cmd, command);
    }

    @HostAccess.Export
    public boolean regPlayerCmd(String cmd, String description, Value callback) {
        return regPlayerCmd(cmd, description, callback, 0);
    }

    @HostAccess.Export
    public boolean regConsoleCmd(String cmd, String description, Value callback) {
        FakeCommand command = new FakeCommand(cmd, description, callback);
        command.setConsole(true);
        return server.getCommandMap().register(cmd, command);
    }

    @HostAccess.Export
    public RealCommand newCommand(String cmd, String desc, int prem, int flag, String... alias) {
        return new RealCommand(cmd, desc, prem, flag, alias);
    }

    @HostAccess.Export
    public Object newCommand(String cmd, String desc, int prem) {
        return newCommand(cmd, desc, prem, 0x80);
    }

    @HostAccess.Export
    public Object newCommand(String cmd, String desc) {
        return newCommand(cmd, desc, 1);
    }

    @HostAccess.Export
    public boolean explode(Value... args) {
        throw new UnsupportedMemberException("explode");
    }

    @HostAccess.Export
    public Object getAllEntities() {
        ScriptLoader.getInstance().logException(new UnsupportedMemberException("getAllEntities"));
        return null;
    }

    @HostAccess.Export
    public Object getEntities() {
        throw new UnsupportedMemberException("getEntities");
    }

    @HostAccess.Export
    public ScriptBlock getBlock(Pos pos) {
        Level level = server.getLevelByName(pos.getLevel());
        if (level == null) return null;
        Block block = level.getBlock((int) pos.getX(), (int) pos.getY(), (int) pos.getZ());
        if (block == null || !block.getChunk().isLoaded()) return null;
        return new ScriptBlock(block);
    }

    @HostAccess.Export
    public ScriptBlock getBlock(int x, int y, int z, Value value) {
        String name = null;
        if (value.isString()) {
            name = value.asString();
        } else if (value.isNumber()) {
            int id = value.asInt();
            name = switch (id) {
                case 0 -> server.getDefaultLevel().getName();
                case 1 -> "nether";
                case 2 -> "the_end";
                default -> "world";
            };
        }
        Level level = server.getLevelByName(name);
        if (level == null) return null;
        Block block = level.getBlock(x, y, z);
        if (block == null || block.getChunk() == null || !block.getChunk().isLoaded()) return null;
        return new ScriptBlock(block);
    }

    @HostAccess.Export
    public ScriptItem newItem(String name, int count) {
        Item item = Item.fromString(name);
        item.setCount(count);
        return new ScriptItem(item);
    }

}
