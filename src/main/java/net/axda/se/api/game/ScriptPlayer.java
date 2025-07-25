package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyExecutable;
import org.graalvm.polyglot.proxy.ProxyObject;

public class ScriptPlayer extends API implements ProxyObject {

    private Player player;

    @HostAccess.Export
    public boolean isOP() {
        return false;
    }

    @HostAccess.Export
    public boolean kick(String msg) {
        return false;
    }

    @HostAccess.Export
    public boolean disconnect(String msg) {
        return kick(msg);
    }

    @HostAccess.Export
    public boolean tell(Value... args) {
        if (args.length < 1) return false;
        try {
            String msg = args[0].asString();
            int type = (args.length < 2)? 0: args[1].asInt();
            switch (type) {
                case 0: player.sendMessage(msg); return true;
                case 1: player.chat(msg); return true;
                case 4: player.sendPopup(msg); return true;
                case 5: player.sendTip(msg); return true;
                default: return false;
            }
        } catch (Exception e) {
            Server.getInstance().getLogger().logException(e);
        }
        return false;
    }

    @HostAccess.Export
    public boolean setTitle(String msg, String type, int fadeInTime, int stayTime, int fadeOutTime) {
        return false;
    }

    @HostAccess.Export
    public boolean broadcast(String msg, String type) {
        return false;
    }

    @HostAccess.Export
    public boolean sendToast(String title, String msg) {
        return false;
    }

    @HostAccess.Export
    public boolean runcmd(String cmd) {
        return false;
    }

    @HostAccess.Export
    public boolean talkAs(String msg) {
        return false;
    }

    @HostAccess.Export
    public int distanceTo(Object pos) {
        return 0;
    }

    @HostAccess.Export
    public int distanceToSqr(Object pos) {
        return distanceTo(pos);
    }

    @HostAccess.Export
    public boolean talkTo(String msg, ScriptPlayer player) {
        return false;
    }

    @HostAccess.Export
    public boolean teleport(Object pos, Object rot) {
        return false;
    }

    @HostAccess.Export
    public boolean kill() {
        return false;
    }

    @HostAccess.Export
    public boolean hurt(float damage, int type, Object source) {
        return false;
    }

    @HostAccess.Export
    public boolean heal(int health) {
        return false;
    }

    @HostAccess.Export
    public boolean setHealth(int health) {
        return false;
    }

    @HostAccess.Export
    public boolean setAbsorption(int value) {
        return false;
    }

    @HostAccess.Export
    public boolean setAttackDamage(int value) {
        return false;
    }

    @HostAccess.Export
    public boolean setMaxAttackDamage(int value) {
        return false;
    }

    @HostAccess.Export
    public boolean setFollowRange(int value) {
        return false;
    }

    @HostAccess.Export
    public boolean setKnockbackResistance(int value) {
        return false;
    }

    @HostAccess.Export
    public boolean setLuck(int value) {
        return false;
    }

    @HostAccess.Export
    public boolean setMovementSpeed(int value) {
        return false;
    }

    @HostAccess.Export
    public boolean setUnderwaterMovementSpeed(int value) {
        return false;
    }

    @HostAccess.Export
    public boolean setLavaMovementSpeed(int value) {
        return false;
    }

    @HostAccess.Export
    public boolean setMaxHealth(int health) {
        return false;
    }

    @HostAccess.Export
    public boolean setHungry(int hunger) {
        return false;
    }

    @HostAccess.Export
    public boolean setFire(int time, boolean isEffect) {
        return false;
    }

    @HostAccess.Export
    public boolean stopFire() {
        return false;
    }

    @HostAccess.Export
    public boolean setScale(int scale) {
        return false;
    }

    @HostAccess.Export
    public boolean rename(String newname) {
        return false;
    }

    @HostAccess.Export
    public Object getBlockStandingOn() {
        return null;
    }

    @HostAccess.Export
    public Object getDevice() {
        return null;
    }

    @HostAccess.Export
    public Object getHand() {
        return null;
    }

    @HostAccess.Export
    public Object getOffHand() {
        return null;
    }

    @HostAccess.Export
    public Object getInventory() {
        return null;
    }

    @HostAccess.Export
    public Object getArmor() {
        return null;
    }

    @HostAccess.Export
    public Object getEnderChest() {
        return null;
    }

    @HostAccess.Export
    public Object getRespawnPosition() {
        return null;
    }

    @HostAccess.Export
    public boolean setRespawnPosition(Object pos) {
        return false;
    }

    @HostAccess.Export
    public boolean giveItem(Object item, int amount) {
        return false;
    }

    @HostAccess.Export
    public boolean clearItem(String type, int amount) {
        return false;
    }

    @HostAccess.Export
    public boolean refreshItems() {
        return false;
    }

    @HostAccess.Export
    public boolean refreshChunks() {
        return false;
    }

    @HostAccess.Export
    public boolean setPermLevel(int level) {
        return false;
    }

    @HostAccess.Export
    public boolean setGameMode(int mode) {
        return false;
    }

    @HostAccess.Export
    public boolean addLevel(int count) {
        return false;
    }

    @HostAccess.Export
    public boolean reduceLevel(int count) {
        return false;
    }

    @HostAccess.Export
    public int getLevel() {
        return 0;
    }

    @HostAccess.Export
    public boolean setLevel(int level) {
        return false;
    }

    @HostAccess.Export
    public boolean resetLevel() {
        return false;
    }

    @HostAccess.Export
    public int getCurrentExperience() {
        return 0;
    }

    @HostAccess.Export
    public boolean setCurrentExperience(int count) {
        return false;
    }

    @HostAccess.Export
    public int getTotalExperience() {
        return 0;
    }

    @HostAccess.Export
    public boolean setTotalExperience(int count) {
        return false;
    }

    @HostAccess.Export
    public boolean addExperience(int count) {
        return false;
    }

    @HostAccess.Export
    public boolean reduceExperience(int count) {
        return false;
    }

    @HostAccess.Export
    public int getXpNeededForNextLevel() {
        return 0;
    }

    @HostAccess.Export
    public boolean transServer(String server, int port) {
        return false;
    }

    @HostAccess.Export
    public boolean crash() {
        return false;
    }


    public ScriptPlayer(Player player) {
        this.player = player;
    }

    public Player getNukkitPlayer() {
        return player;
    }

    @Override
    public Object getMember(String key) {
        switch (key) {
            case "name": return player.getName();
            case "realName": return player.getLoginChainData().getUsername();
            case "xuid": return player.getLoginChainData().getXUID();
            case "tell": return (ProxyExecutable) this::tell;
        }
        return null;
    }

    @Override
    public Object getMemberKeys() {
        return null;
    }

    @Override
    public boolean hasMember(String key) {
        return true;
    }

    @Override
    public void putMember(String key, Value value) {

    }
}
