package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import net.axda.se.api.game.data.FloatPos;
import net.axda.se.api.game.data.Pos;
import net.axda.se.exception.ValueTypeException;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyExecutable;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.util.HashSet;

public class ScriptPlayer extends API implements ProxyObject, Pos {

    private Player player;
    private Server server = Server.getInstance();

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
            player.sendToast(API.toString(args[0]), API.toString(args[1]));
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
    public double distanceTo(Value... args) {
        try {
            Pos pos = API.toPos(args[0]);
            if (!player.getLevel().getName().equals(pos.getLevel())) {
                return Integer.MAX_VALUE;
            } else {
                return (float) player.distance(new Vector3(pos.getX(), pos.getY(), pos.getZ()));
            }
        } catch (Exception e) {
            throw new ValueTypeException(e);
        }
    }

    @HostAccess.Export
    public boolean talkTo(Value... args) {
        try {
            String message = args[0].asString();
            Player target = args[1].getMember("..nukkit_player..").as(Player.class);
            if (target != null && target.isOnline()) {
                player.resetCraftingGridType();

                for(String msg : message.split("\n")) {
                    if (!msg.trim().isEmpty() && msg.length() < 512) {
                        PlayerChatEvent chatEvent = new PlayerChatEvent(player, msg);
                        server.getPluginManager().callEvent(chatEvent);
                        if (!chatEvent.isCancelled()) {
                            HashSet<CommandSender> set = new HashSet<>();
                            set.add(target);
                            server.broadcastMessage(
                                    server.getLanguage().translateString(chatEvent.getFormat(),
                                    new String[]{chatEvent.getPlayer().getDisplayName(), chatEvent.getMessage()}),
                                    set);
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            throw new ValueTypeException(e);
        }
    }

    @HostAccess.Export
    public boolean teleport(Value... args) {
        try {
            Pos pos;
            if (args[0].isNumber()) {
                float x = args[0].asFloat();
                float y = args[1].asFloat();
                float z = args[2].asFloat();
                int dim = args[3].asInt();
                pos = new FloatPos(x, y, z, dim);
                player.teleport(pos.getLocation());
            } else {

            }
        } catch (Exception e) {
            throw new ValueTypeException(e);
        }
        return false;
    }

    @HostAccess.Export
    public boolean kill(Value... args) {
        player.setHealth(0);
        return true;
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

    public FloatPos pos() {
        return new FloatPos(player.getX(), player.getY(), player.getZ(), player.getLevel().getDimension(), player.getLevel().getName());
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
            case "..nukkit_player..": return player;
            case "name": return player.getName();
            case "pos": return pos();
            case "realName": return player.getLoginChainData().getUsername();
            case "xuid": return player.getLoginChainData().getXUID();
            case "tell", "sendText": return (ProxyExecutable) this::tell;
            case "isOp": return (ProxyExecutable) this::isOP;
            case "kick", "disconnect": return (ProxyExecutable) this::kick;
            case "setTitle": return (ProxyExecutable) this::setTitle;
            case "sendToast": return (ProxyExecutable) this::sendToast;
            case "runcmd": return (ProxyExecutable) this::runcmd;
            case "talkAs": return (ProxyExecutable) this::talkAs;
            case "distanceTo", "distanceToSqr": return (ProxyExecutable) this::distanceTo;
            case "talkTo": return (ProxyExecutable) this::talkTo;
            case "teleport": return (ProxyExecutable) this::teleport;
            case "kill": return (ProxyExecutable) this::kill;
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

    @Override
    public double getX() {
        return player.getX();
    }

    @Override
    public double getY() {
        return player.getY();
    }

    @Override
    public double getZ() {
        return player.getZ();
    }

    @Override
    public String getLevel() {
        return player.getLevel().getName();
    }

    @Override
    public String getDim() {
        switch (player.getLevel().getDimension()) {
            case 0: return "主世界";
            case 1: return "下界";
            case 2: return "末地";
            default: return null;
        }
    }

    @Override
    public int getDimId() {
        return player.getLevel().getDimension();
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
    }
}
