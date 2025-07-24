package net.axda.se;

import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerEvent;
import cn.nukkit.event.player.PlayerJoinEvent;

public class ScriptListener implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        ScriptLoader.getInstance().putPlayer(event.getPlayer());
    }

    @EventHandler
    public void playerQuit(PlayerJoinEvent event) {
        ScriptLoader.getInstance().removePlayer(event.getPlayer());
    }

}
