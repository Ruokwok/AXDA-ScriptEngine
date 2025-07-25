package net.axda.se;

import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import net.axda.se.api.game.ScriptPlayer;
import net.axda.se.listen.ListenEvent;
import net.axda.se.listen.ListenMap;

public class ScriptListener implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        ScriptPlayer sp = ScriptLoader.getInstance().putPlayer(event.getPlayer());
        ListenMap.call(ListenEvent.PlayerOnJoin.getValue(), sp);
    }

    @EventHandler
    public void playerQuit(PlayerJoinEvent event) {
        ScriptLoader.getInstance().removePlayer(event.getPlayer());
    }

}
