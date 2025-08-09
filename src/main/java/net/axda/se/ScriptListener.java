package net.axda.se;

import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.level.Location;
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
    public void playerQuit(PlayerQuitEvent event) {
        ListenMap.call(ListenEvent.PlayerOnLeft.getValue(), loader.getPlayer(event.getPlayer()));
        ScriptLoader.getInstance().removePlayer(event.getPlayer());
    }

    @EventHandler
    public void playerJump(PlayerJumpEvent event) {
        ListenMap.call(ListenEvent.PlayerJump.getValue(), loader.getPlayer(event.getPlayer()));
    }

    @EventHandler
    public void playerRespawn(PlayerRespawnEvent event) {
        ListenMap.call(ListenEvent.PlayerRespawn.getValue(), loader.getPlayer(event.getPlayer()));
    }

    @EventHandler
    public void playerRespawn(PlayerDeathEvent event) {
        ListenMap.call(ListenEvent.PlayerDeath.getValue(), loader.getPlayer(event.getEntity()));
    }

    @EventHandler
    public void playerCmd(PlayerCommandPreprocessEvent event) {
        boolean c = ListenMap.call(ListenEvent.PlayerCmd.getValue(), loader.getPlayer(event.getPlayer()), event.getMessage().trim());
        if (!c) event.setCancelled(true);
    }

    @EventHandler
    public void playerChat(PlayerChatEvent event) {
        boolean c = ListenMap.call(ListenEvent.PlayerChat.getValue(), loader.getPlayer(event.getPlayer()), event.getMessage().trim());
        if (!c) event.setCancelled(true);
    }

    @EventHandler
    public void playerLevel(PlayerTeleportEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.getLevel().getDimension() != to.getLevel().getDimension()) {
            ListenMap.call(ListenEvent.PlayerChangeDim.getValue(), loader.getPlayer(event.getPlayer()), to.getLevel().getDimension());
        }
    }

    @EventHandler
    public void playerSneak(PlayerToggleSneakEvent event) {
        ListenMap.call(ListenEvent.PlayerSneak.getValue(), loader.getPlayer(event.getPlayer()), event.isSneaking());
    }

}
