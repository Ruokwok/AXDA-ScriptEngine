package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.proxy.ProxyArray;

import java.util.ArrayList;

public class ArmorContainer extends Container {

    private Player player;

    public ArmorContainer(Player player) {
        super(null);
        this.player = player;
        this.size = player.getInventory().getArmorContents().length;
    }

    @HostAccess.Export
    public final int size;

    @HostAccess.Export
    public final String type = "Armor";

    @Override
    @HostAccess.Export
    public boolean addItem(ScriptItem si, int count) {
        Item item = si.getItem();
        Item[] armors = player.getInventory().getArmorContents();
        if (item.isArmor()) {
            if (item.isHelmet() && armors[0].isNull()) player.getInventory().setArmorItem(0, item);
            if (item.isChestplate() && armors[1].isNull()) player.getInventory().setArmorItem(1, item);
            if (item.isLeggings() && armors[2].isNull()) player.getInventory().setArmorItem(2, item);
            if (item.isBoots() && armors[3].isNull()) player.getInventory().setArmorItem(3, item);
            return true;
        }
        return false;
    }

    @Override
    @HostAccess.Export
    public boolean addItem(ScriptItem si) {
        return this.addItem(si, 1);
    }

    @Override
    @HostAccess.Export
    public boolean addItemToFirstEmptySlot(ScriptItem si) {
        return this.addItem(si);
    }

    @Override
    @HostAccess.Export
    public boolean hasRoomFor(ScriptItem si) {
        return false;
    }

    @Override
    @HostAccess.Export
    public boolean removeItem(int index, int count) {
        Item[] items = player.getInventory().getArmorContents();
        player.getInventory().remove(items[index]);
        return true;
    }

    @Override
    @HostAccess.Export
    public ScriptItem getItem(int index) {
        return new ScriptItem(player.getInventory().getArmorItem(index));
    }

    @Override
    @HostAccess.Export
    public boolean setItem(int index, ScriptItem si) {
        return player.getInventory().setArmorItem(index, si.getItem());
    }

    @Override
    @HostAccess.Export
    public ProxyArray getAllItems() {
        ArrayList<Object> list = new ArrayList<>();
        for (Item item : player.getInventory().getArmorContents()) {
            if (item == null || item.isNull()) {
                list.add(null);
            } else {
                list.add(new ScriptItem(item));
            }
        }
        return ProxyArray.fromList(list);
    }

    @Override
    @HostAccess.Export
    public boolean removeAllItems() {
        Item[] items = new Item[4];
        player.getInventory().setArmorContents(items);
        return true;
    }

    @Override
    @HostAccess.Export
    public boolean isEmpty() {
        for (Item item : player.getInventory().getArmorContents()) {
            if (item != null && !item.isNull()) {
                return false;
            }
        }
        return true;
    }
}
