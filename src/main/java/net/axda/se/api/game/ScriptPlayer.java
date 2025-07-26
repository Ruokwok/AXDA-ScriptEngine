package net.axda.se.api.game;

import cn.nukkit.Player;
import net.axda.se.exception.ValueTypeException;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyExecutable;
import org.graalvm.polyglot.proxy.ProxyObject;

public class ScriptPlayer extends API implements ProxyObject {

    private Player player;

    @HostAccess.Export
    public boolean isOP(Value... args) {
        return player.isOp();
    }

    @HostAccess.Export
    public boolean kick(Value... args) {
        if (args.length > 0) {
            return player.kick(args[0].asString());
        }
        return false;
    }

    @HostAccess.Export
    public boolean disconnect(Value... args) {
        return kick(args);
    }

    @HostAccess.Export
    public boolean tell(Value... args) throws ValueTypeException {
        if (args.length < 1) return false;
        try {
            String msg = args[0].asString();
            int type = (args.length < 2)? 0: args[1].asInt();
            switch (type) {
                case 0: player.sendMessage(msg); return true;
                case 1: player.sendChat(msg); return true;
                case 4: player.sendPopup(msg); return true;
                case 5: player.sendTip(msg); return true;
                default: return false;
            }
        } catch (Exception e) {
            throw new ValueTypeException();
        }
    }

    @HostAccess.Export
    public boolean setTitle(Value... args) {
        if (args.length == 0) return false;
        try {
            String content = args[0].asString();
            int type = (args.length <= 2)? 2: args[1].asInt();
            int fadeInTime = (args.length <= 3)? 10: args[2].asInt();
            int stayTime = (args.length <= 4)? 70: args[3].asInt();
            int fadeOutTime = (args.length <= 5)? 20: args[4].asInt();
            switch (type) {
                case 0: player.clearTitle(); return true;
                case 1: return false;
                case 2: player.sendTitle(content, "", fadeInTime, stayTime, fadeOutTime); return true;
                case 3: player.sendTitle("", content, fadeInTime, stayTime, fadeOutTime); return true;
                case 4: player.sendActionBar(content, fadeInTime, stayTime, fadeOutTime); return true;
                default: return false;
            }
        } catch (Exception e) {
            throw new ValueTypeException();
        }
    }

    @HostAccess.Export
    public boolean broadcast(String msg, String type) {
        return false;
    }

    @HostAccess.Export
    public boolean sendToast(Value... args) {
        try {
            player.sendToast(args[0].asString(), args[1].asString());
        } catch (Exception e) {
            throw new ValueTypeException();
        }
        return false;
    }

    @HostAccess.Export
    public boolean runcmd(Value... args) {
        try {
            return player.getServer().dispatchCommand(player, args[0].asString());
        } catch (Exception e) {
            throw new ValueTypeException();
        }
    }

    @HostAccess.Export
    public boolean talkAs(Value... args) {
        try {
            return player.chat(args[0].asString());
        } catch (Exception e) {
            throw new ValueTypeException();
        }
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
            case "tell", "sendText": return (ProxyExecutable) this::tell;
            case "isOp": return (ProxyExecutable) this::isOP;
            case "kick", "disconnect": return (ProxyExecutable) this::kick;
            case "setTitle": return (ProxyExecutable) this::setTitle;
            case "sendToast": return (ProxyExecutable) this::sendToast;
            case "runcmd": return (ProxyExecutable) this::runcmd;
            case "talkAs": return (ProxyExecutable) this::talkAs;
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
