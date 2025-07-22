package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import net.axda.se.ScriptLoader;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MC extends API {

    private Server server = Server.getInstance();

    @HostAccess.Export
    public boolean listen(String event, Value callback) {
        engine.registerEvent(event, callback);
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

}
