package net.axda.se.api.game;

import cn.nukkit.item.Item;
import net.axda.se.api.API;
import net.axda.se.api.ProxyAPI;
import net.axda.se.api.ProxyField;

public class ScriptItem extends API implements ProxyAPI {

    private Item item;

    public ScriptItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item i) {
            return this.item == i;
        } else if (obj instanceof ScriptItem si) {
            return this.item == si.item;
        }
        return false;
    }

    @ProxyField
    public String name() {
        return item.getName();
    }

    @Override
    public Object getOrigin() {
        return item;
    }

    public Item getItem() {
        return item;
    }
}
