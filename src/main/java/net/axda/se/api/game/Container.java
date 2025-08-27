package net.axda.se.api.game;

import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.proxy.ProxyArray;

import java.util.ArrayList;
import java.util.Map;

public class Container {

    public Container(Inventory inventory) {
        this.inventory = inventory;
        this.size = inventory.getSize();
        this.type = inventory.getType().name();
    }

    private final Inventory inventory;

    @HostAccess.Export
    public final int size;

    @HostAccess.Export
    public final String type;

    @HostAccess.Export
    public boolean addItem(ScriptItem si, int count) {
        Item item = si.getItem();
        item.setCount(count);
        return inventory.addItem(item) == null;
    }

    @HostAccess.Export
    public boolean addItem(ScriptItem si) {
        return addItem(si, si.getItem().getCount());
    }

    @HostAccess.Export
    public boolean addItemToFirstEmptySlot(ScriptItem si) {
        return inventory.addItem(si.getItem()) == null;
    }

    @HostAccess.Export
    public boolean hasRoomFor(ScriptItem si) {
        return inventory.canAddItem(si.getItem());
    }

    @HostAccess.Export
    public boolean removeItem(int index, int count) {
        Item item = inventory.getItem(index);
        item.setCount(count);
        return inventory.removeItem(item) == null;
    }

    @HostAccess.Export
    public ScriptItem getItem(int index) {
        return new ScriptItem(inventory.getItem(index));
    }

    @HostAccess.Export
    public boolean setItem(int index, ScriptItem si) {
        return inventory.setItem(index, si.getItem());
    }

    @HostAccess.Export
    public ProxyArray getAllItems() {
        Map<Integer, Item> contents = inventory.getContents();
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            Item item = contents.get(i);
            list.add(new ScriptItem(item));
        }
        return ProxyArray.fromList(list);
    }

    @HostAccess.Export
    public boolean removeAllItems() {
        inventory.clearAll();
        return true;
    }

    @HostAccess.Export
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

}
