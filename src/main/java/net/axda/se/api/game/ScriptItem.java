package net.axda.se.api.game;

import cn.nukkit.item.Item;
import net.axda.se.api.API;
import net.axda.se.api.ProxyAPI;
import net.axda.se.api.ProxyField;
import net.axda.se.exception.UnsupportedMemberException;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;

import java.util.Arrays;

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

    @Override
    public Object getOrigin() {
        return item;
    }

    public Item getItem() {
        return item;
    }

    @ProxyField
    public String name() {
        return item.getCustomName();
    }

    @ProxyField
    public String type() {
        return item.getName();
    }

    @ProxyField
    public int id() {
        return item.getId();
    }

    @ProxyField
    public int count() {
        return item.getCount();
    }

    @ProxyField
    public int aux() {
        throw new UnsupportedMemberException("aux");
    }

    @ProxyField
    public int damage() {
        return item.getDamage();
    }

    @ProxyField
    public int attackDamage() {
        return item.getAttackDamage();
    }

    @ProxyField
    public int maxDamage() {
        return item.getMaxDurability();
    }

    @ProxyField
    public ProxyArray lore() {
        return ProxyArray.fromList(Arrays.asList(item.getLore()));
    }

    @ProxyField
    public boolean isArmorItem() {
        return item.isArmor();
    }

    @ProxyField
    public boolean isBlock() {
        throw new UnsupportedMemberException("isBlock");
    }

    @ProxyField
    public boolean isDamageableItem() {
        return item.isTool() || item.isArmor() || item.isShovel() ||
                item.isPickaxe() || item.isAxe() || item.isSword() || item.isHoe() || item.isShears();
    }

    @ProxyField
    public boolean isDamaged() {
        return item.getDamage() > 0;
    }

    @ProxyField
    public boolean isEnchanted() {
        return item.hasEnchantments();
    }

    @ProxyField
    public boolean isEnchantingBook() {
        return item.getId() == Item.ENCHANT_BOOK;
    }

    @ProxyField
    public boolean isFireResistant() {
        return item.isUnbreakable();
    }

    @ProxyField
    public boolean isFullStack() {
        return item.getCount() == item.getMaxStackSize();
    }

    @ProxyField
    public boolean isGlint() {
        throw new UnsupportedMemberException("isGlint");
    }

    @ProxyField
    public boolean isHorseArmorItem() {
        return item.isArmor() && item.getId() >= 416 && item.getId() <= 419;
    }

    @ProxyField
    public boolean isLiquidClipItem() {
        throw new UnsupportedMemberException("isLiquidClipItem");
    }

    @ProxyField
    public boolean isMusicDiscItem() {
        return item.getId() >= 2250 && item.getId() <= 2254;
    }

    @ProxyField
    public boolean isOffhandItem() {
        return item.allowOffhand();
    }

    @ProxyField
    public boolean isPotionItem() {
        return item.getId() == Item.POTION || item.getId() == Item.SPLASH_POTION || item.getId() == Item.LINGERING_POTION;
    }

    @ProxyField
    public boolean isStackable() {
        return item.getMaxStackSize() > 1;
    }

    @ProxyField
    public boolean isWearableItem() {
        return item.isArmor() || item.isHelmet() || item.isChestplate() || item.isLeggings() || item.isBoots();
    }

    @HostAccess.Export
    public boolean isNull(Value... args) {
        if (item == null) return true;
        return item.isNull();
    }

    @ProxyField
    public boolean setNull(Value... args) {
        throw new UnsupportedMemberException("setNull");
    }

    @HostAccess.Export
    public boolean set(Value... args) {
        throw new UnsupportedMemberException("set");
    }

    @HostAccess.Export
    public boolean setDamage(Value... args) {
        item.setDamage(args[0].asInt());
        return true;
    }

    @HostAccess.Export
    public boolean setAux(Value... args) {
        throw new UnsupportedMemberException("setAux");
    }

    @HostAccess.Export
    public boolean spawnItem(Value... args) {
        throw new UnsupportedMemberException("spawnItem");
    }

    @HostAccess.Export
    public Object getNbt(Value... args) {
        throw new UnsupportedMemberException("getNbt");
    }

    @HostAccess.Export
    public boolean setNbt(Value... args) {
        throw new UnsupportedMemberException("setNbt");
    }

    @ProxyField
    public boolean setLore(Value... args) {
        item.setLore(args[0].as(String[].class));
        return true;
    }

    @HostAccess.Export
    public boolean setDisplayName(Value... args) {
        item.setCustomName(args[0].asString());
        return true;
    }

    @HostAccess.Export
    public boolean match(Value... args) {
        throw new UnsupportedMemberException("match");
    }

    @HostAccess.Export
    public ScriptItem clone(Value... args) {
        return new ScriptItem(item.clone());
    }
}
