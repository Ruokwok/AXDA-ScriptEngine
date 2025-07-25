package net.axda.se;

import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import net.axda.se.api.game.ScriptPlayer;
import net.axda.se.listen.ListenEvent;
import net.axda.se.listen.ListenMap;

public class ScriptListener implements Listener {

    private ScriptLoader loader = ScriptLoader.getInstance();

    @EventHandler
    public void playerPreJoin(PlayerPreLoginEvent event) {
        ListenMap.call(ListenEvent.PlayerPreJoin.getValue(), loader.getPlayer(event.getPlayer()));
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        ListenMap.call(ListenEvent.PlayerOnJoin.getValue(), loader.getPlayer(event.getPlayer()));
    }

    @EventHandler
    public void playerQuit(PlayerJoinEvent event) {
        ScriptLoader.getInstance().removePlayer(event.getPlayer());
    }

}
