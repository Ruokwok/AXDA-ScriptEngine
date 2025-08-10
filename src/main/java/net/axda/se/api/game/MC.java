package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import net.axda.se.ScriptLoader;
import net.axda.se.api.API;
import net.axda.se.api.gui.CustomForm;
import net.axda.se.api.gui.SimpleForm;
import net.axda.se.exception.ValueTypeException;
import net.axda.se.listen.Listen;
import net.axda.se.listen.ListenEvent;
import net.axda.se.listen.ListenMap;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

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
        return false;
    }

    @HostAccess.Export
    public boolean setMaxPlayers(int size) {
        return false;
    }

    @HostAccess.Export
    public int getTime() {
        return 0;
    }

    @HostAccess.Export
    public boolean setTime(int time) {
        return false;
    }

    @HostAccess.Export
    public int getWeather(int weather) {
        return 0;
    }

    @HostAccess.Export
    public boolean setWeather(int weather) {
        return false;
    }

    @HostAccess.Export
    public ScriptPlayer getPlayer(String info) {
        Player player = server.getPlayer(info);
        if (player == null) server.getPlayer(UUID.fromString(info));
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
    public List<ScriptPlayer> getOnlinePlayers() {
        return ScriptLoader.getInstance().getOnlinePlayers();
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

}
