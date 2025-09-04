package net.axda.se;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityEffectRemoveEvent;
import cn.nukkit.event.entity.EntityEffectUpdateEvent;
import cn.nukkit.event.entity.EntityPotionEffectEvent;
import cn.nukkit.event.inventory.InventoryPickupItemEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.level.Location;
import cn.nukkit.potion.Effect;
import net.axda.se.api.game.ScriptBlock;
import net.axda.se.api.game.ScriptEntity;
import net.axda.se.api.game.ScriptItem;
import net.axda.se.api.game.ScriptPlayer;
import net.axda.se.api.game.data.FloatPos;
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

    @EventHandler
    public void playerFormResponded(PlayerFormRespondedEvent event) {
        ScriptPlayer player = loader.getPlayer(event.getPlayer());
        FormWindow window = event.getWindow();
        player.executeFormCallback(window, event.getFormID());
    }

    @EventHandler
    public void playerAttackEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            boolean c = ListenMap.call(ListenEvent.PlayerAttackEntity.getValue(), loader.getPlayer(player), new ScriptEntity(event.getEntity()));
            if (!c) event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerAttackBlock(PlayerInteractEvent event) {
        if (event.getAction() == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            boolean c = ListenMap.call(ListenEvent.PlayerAttackBlock.getValue(),
                    loader.getPlayer(event.getPlayer()),
                    new ScriptBlock(event.getBlock()),
                    new ScriptItem(event.getItem()));
            if (!c) event.setCancelled(true);
        } else if (event.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            boolean c = ListenMap.call(ListenEvent.PlayerUseItemOn.getValue(),
                    loader.getPlayer(event.getPlayer()),
                    new ScriptBlock(event.getBlock()),
                    new ScriptItem(event.getItem()),
                    event.getFace().getIndex(),
                    new FloatPos(event.getFace().getUnitVector(), event.getBlock().getLevel()));
            if (!c) event.setCancelled(true);
        } else if (event.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            boolean c = ListenMap.call(ListenEvent.PlayerUseItem.getValue(),
                    loader.getPlayer(event.getPlayer()),
                    new ScriptItem(event.getItem()));
            if (!c) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onUseBucketPlace(PlayerBucketEmptyEvent event) {
        boolean c = ListenMap.call(ListenEvent.PlayerOnUseBucketPlace.getValue(),
                loader.getPlayer(event.getPlayer()),
                new ScriptItem(event.getItem()),
                new ScriptBlock(event.getBlockClicked()),
                event.getBlockFace().getIndex(),
                new FloatPos(event.getBlockFace().getUnitVector(), event.getBlockClicked().getLevel()));
        if (!c) event.setCancelled(true);
    }

    @EventHandler
    public void onUseBucketTake(PlayerBucketFillEvent event) {
        boolean c = ListenMap.call(ListenEvent.PlayerOnUseBucketTake.getValue(),
                loader.getPlayer(event.getPlayer()),
                new ScriptItem(event.getItem()),
                new ScriptBlock(event.getBlockClicked()),
                event.getBlockFace().getIndex(),
                new FloatPos(event.getBlockFace().getUnitVector(), event.getBlockClicked().getLevel()));
        if (!c) event.setCancelled(true);
    }

    @EventHandler
    public void playerOnTakeItem(InventoryPickupItemEvent event) {
        boolean c = ListenMap.call(ListenEvent.PlayerOnTakeItem.getValue(),
                loader.getPlayer(event.getViewers()[0]),
                new ScriptEntity(event.getItem()),
                new ScriptItem(event.getItem().getItem()));
        if (!c) event.setCancelled(true);
    }

    @EventHandler
    public void playerOnDropItem(PlayerDropItemEvent event) {
        boolean c = ListenMap.call(ListenEvent.PlayerOnDropItem.getValue(),
                loader.getPlayer(event.getPlayer()),
                new ScriptItem(event.getItem()));
        if (!c) event.setCancelled(true);
    }

    @EventHandler
    public void playerOnAte(PlayerEatFoodEvent event) {
        boolean c = ListenMap.call(ListenEvent.PlayerOnAte.getValue(),
                loader.getPlayer(event.getPlayer()),
                new ScriptItem(event.getPlayer().getInventory().getItemInHand()));
        if (!c) event.setCancelled(true);
    }

    @EventHandler
    public void playerOnEffectAdded(EntityEffectUpdateEvent event) {
        Entity entity = event.getEntity();
        Effect effect = event.getNewEffect();
        if (entity instanceof Player player) {
            Effect oldEffect = event.getOldEffect();
            if (oldEffect != null) {
                boolean c = ListenMap.call(ListenEvent.PlayerOnEffectAdded.getValue(),
                        loader.getPlayer(player),
                        effect.getName(),
                        effect.getAmplifier(),
                        effect.getDuration());
                if (!c) event.setCancelled(true);
            } else {
                boolean c = ListenMap.call(ListenEvent.PlayerOnEffectUpdated.getValue(),
                        loader.getPlayer(player),
                        effect.getName(),
                        effect.getAmplifier(),
                        effect.getDuration());
                if (!c) event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerOnEffectRemoved(EntityEffectRemoveEvent event) {
        Entity entity = event.getEntity();
        Effect effect = event.getRemoveEffect();
        if (entity instanceof Player player) {
            boolean c = ListenMap.call(ListenEvent.PlayerOnEffectRemoved.getValue(),
                    loader.getPlayer(player),
                    effect.getName());
            if (!c) event.setCancelled(true);
        }
    }

}
