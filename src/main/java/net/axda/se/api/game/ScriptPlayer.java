package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import net.axda.se.api.game.data.FloatPos;
import net.axda.se.api.game.data.IntPos;
import net.axda.se.api.game.data.Pos;
import net.axda.se.api.gui.Form;
import net.axda.se.exception.ValueTypeException;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyExecutable;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.util.HashSet;
import java.util.List;

public class ScriptPlayer extends API implements ProxyObject, Pos {

    private Player player;
    private Server server = Server.getInstance();
    private Value formCallback;

    @Override
    public Object getMember(String key) {
        switch (key) {
            case "..nukkit_player..": return player;
            //玩家对象属性
            case "name": return player.getName();
            case "pos": return pos();
            case "feetPos": return null;
            case "blockPos": return blockPos();
            case "lastDeathPos": return null;
            case "realName": return player.getLoginChainData().getUsername();
            case "xuid": return player.getLoginChainData().getXUID();
            case "uuid": return player.getUniqueId().toString();
            case "permLevel": return null;
            case "gameMode": return player.getGamemode();
            case "canFly": return null;
            case "canSleep": return null;
            case "canBeSeenOnMap": return null;
            case "canFreeze": return null;
            case "canSeeDaylight": return null;
            case "canShowNameTag": return null;
            case "canStartSleepInBed": return null;
            case "canPickupItems": return player.getMaxHealth();
            case "maxHealth": return player.getHealth();
            case "health": return null;
            case "inAir": return null;
            case "inWater": return null;
            case "inLava": return null;
            case "inRain": return null;
            case "inSnow": return null;
            case "inWall": return null;
            case "inWaterOrRain": return null;
            case "inWorld": return player.getLocation().getLevel().getDimension() == 1;
            case "inClouds": return null;
            case "speed": return player.getWalkSpeed();
            case "direction": return null;
            case "uniqueId": return null;
            case "runtimeId": return null;
            case "langCode": return player.getLocale().getLanguage();
            case "isLoading": return null;
            case "isInvisible": return null;
            case "isInsidePortal": return null;
            case "isHurt": return null;
            case "isTrusting": return null;
            case "isTouchingDamageBlock": return null;
            case "isHungry": return null;
            case "isOnFire": return null;
            case "isOnGround": return null;
            case "isOnHotBlock": return null;
            case "isTrading": return null;
            case "isAdventure": return player.getGamemode() == Player.ADVENTURE;
            case "isGliding": return null;
            case "isSurvival": return player.getGamemode() == Player.SURVIVAL;
            case "isSpectator": return player.getGamemode() == Player.SPECTATOR;
            case "isRiding": return null;
            case "isDancing": return null;
            case "isCreative": return player.getGamemode() == Player.CREATIVE;
            case "isFlying": return null;
            case "isSleeping": return player.isSleeping();
            case "isMoving": return null;
            case "isSneaking": return player.isSneaking();

            //玩家对象函数
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
            case "hurt": return null;
            case "heal": return null;
            case "setAbsorption": return null;
            case "setAttackDamage": return null;
            case "setMaxAttackDamage": return null;
            case "setFollowRange": return null;
            case "setKnockbackResistance": return null;
            case "setLuck": return null;
            case "setMovementSpeed": return null;
            case "setUnderwaterMovementSpeed": return null;
            case "setLavaMovementSpeed": return null;
            case "setMaxHealth": return null;
            case "setHungry": return null;
            case "setFire": return null;
            case "stopFire": return null;
            case "setScale": return null;
            case "rename": return null;
            case "getBlockStandingOn": return null;
            case "getDevice": return null;
            case "getHand": return null;
            case "getOffHand": return null;
            case "getInventory": return null;
            case "getArmor": return null;
            case "getEnderChest": return null;
            case "getRespawnPosition": return null;
            case "setRespawnPosition": return null;
            case "giveItem": return null;
            case "clearItem": return null;
            case "refreshItems": return null;
            case "refreshChunks": return null;
            case "setPermLevel": return null;
            case "setGameMode": return (ProxyExecutable) this::setGameMode;
            case "addLevel": return null;
            case "reduceLevel": return null;
            case "getLevel": return (ProxyExecutable) this::getExpLevel;
            case "setLevel": return (ProxyExecutable) this::setExpLevel;
            case "resetLevel": return (ProxyExecutable) this::resetExpLevel;
            case "getCurrentExperience": return (ProxyExecutable) this::getExp;
            case "setCurrentExperience": return (ProxyExecutable) this::setExp;
            case "getTotalExperience": return null;
            case "setTotalExperience": return null;
            case "addExperience": return (ProxyExecutable) this::addExp;
            case "reduceExperience": return (ProxyExecutable) this::reduceExp;
            case "getXpNeededForNextLevel": return null;
            case "transServer": return null;
            case "crash": return null;
            case "setSidebar": return null;
            case "removeSidebar": return null;
            case "setBossBar": return null;
            case "removeBossBar": return null;
            case "getNbt": return null;
            case "setNbt": return null;
            case "getPlayerNbt": return null;
            case "setPlayerNbt": return null;
            case "setPlayerNbtTags": return null;
            case "deletePlayerNbt": return null;
            case "addTag": return null;
            case "removeTag": return null;
            case "hasTag": return null;
            case "getAllTags": return null;
            case "getAbilities": return null;
            case "getAttributes": return null;
            case "isSprinting": return null;
            case "setSprinting": return null;
            case "getEntityFromViewVector": return null;
            case "getBlockFromViewVector": return null;
            case "sendPacket": return null;
            case "getBiomeId": return null;
            case "getBiomeName": return null;
            case "setAbility": return null;
            case "getAllEffects": return null;
            case "addEffect": return null;
            case "removeEffect": return null;
            case "toEntity": return null;
            case "isSimulatedPlayer": return null;

            //表单相关函数
            case "sendModalForm": return (ProxyExecutable) this::sendModalForm;
            case "sendSimpleForm": return (ProxyExecutable) this::sendSimpleForm;
            case "sendCustomForm": return (ProxyExecutable) this::sendCustomForm;
            case "closeForm": return (ProxyExecutable) this::closeForm;
            case "sendForm": return (ProxyExecutable) this::sendForm;
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

    public ScriptPlayer(Player player) {
        this.player = player;
    }

    public Player getNukkitPlayer() {
        return player;
    }

    public boolean isOP(Value... args) {
        return player.isOp();
    }

    public boolean kick(Value... args) {
        if (args.length > 0) {
            return player.kick(args[0].asString());
        }
        return false;
    }

    public boolean disconnect(Value... args) {
        return kick(args);
    }

    public boolean tell(Value... args) throws ValueTypeException {
        if (args.length < 1) return false;
        try {
            String msg = args[0].toString();
            int type = (args.length < 2)? 0: args[1].asInt();
            switch (type) {
                case 0: player.sendMessage(msg); return true;
                case 1: player.sendChat(msg); return true;
                case 4: player.sendPopup(msg); return true;
                case 5: player.sendTip(msg); return true;
                default: return false;
            }
        } catch (Exception e) {
            throw new ValueTypeException(e);
        }
    }

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

    public boolean runcmd(Value... args) {
        try {
            return player.getServer().dispatchCommand(player, args[0].asString());
        } catch (Exception e) {
            throw new ValueTypeException();
        }
    }

    public boolean talkAs(Value... args) {
        try {
            return player.chat(args[0].asString());
        } catch (Exception e) {
            throw new ValueTypeException();
        }
    }

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

    public boolean kill(Value... args) {
        player.setHealth(0);
        return true;
    }

    public FloatPos pos() {
        return new FloatPos(player, player.getLevel());
    }

    public IntPos blockPos() {
        return new IntPos(player.getLevelBlock(), player.getLevel());
    }

    public boolean setGameMode(Value... arg) {
        return player.setGamemode(arg[0].asInt());
    }

    public int getExpLevel(Value... args) {
        return player.getExperienceLevel();
    }

    public boolean setExpLevel(Value... args) {
        int level = args[0].asInt();
        player.setExperience(0, level);
        return player.getExperienceLevel() == level;
    }

    public boolean resetExpLevel(Value... args) {
        player.setExperience(0, 0);
        return player.getExperienceLevel() == 0;
    }

    public boolean addExp(Value... args) {
        int level = args[0].asInt();
        player.addExperience(level);
        return player.getExperienceLevel() == level;
    }

    public boolean reduceExp(Value... args) {
        int level = args[0].asInt();
        if (player.getExperience() - level < 0) {
            player.setExperience(0);
        } else {
            player.addExperience(-level);
        }
        return true;
    }

    public int getExp(Value... args) {
        return player.getExperience();
    }

    public boolean setExp(Value... args) {
        int exp = args[0].asInt();
        player.setExperience(exp);
        return true;
    }

    public int sendModalForm(Value... args) {
        String title = args[0].asString();
        String content = args[1].asString();
        String confirmButton = args[2].asString();
        String cancelButton = args[3].asString();
        this.formCallback = args[4];
        FormWindowModal form = new FormWindowModal(title, content, confirmButton, cancelButton);
        return player.showFormWindow(form);
    }

    public int sendSimpleForm(Value... args) {
        String title = args[0].asString();
        String content = args[1].asString();
        List<String> buttons = args[2].as(List.class);
        List<String> images = args[3].as(List.class);
        this.formCallback = args[4];
        FormWindowSimple form = new FormWindowSimple(title, content);
        for (int i = 0; i < buttons.size(); i++) {
            if (images != null && images.get(i) != null) {
                ElementButtonImageData imageData = new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_URL, images.get(i));
                form.addElement(new ElementButton(buttons.get(i), imageData));
            } else {
                form.addElement(new ElementButton(buttons.get(i)));
            }
        }
        return player.showFormWindow(form);
    }

    public int sendCustomForm(Value... args) {
        String json = args[0].asString();
        this.formCallback = args[1];
        return 0;
    }

    public boolean closeForm(Value... args) {
        player.closeFormWindows();
        formCallback = null;
        return true;
    }

    public int sendForm(Value... args) {
        Form form = args[0].as(Form.class);
        this.formCallback = args[1];
        return player.showFormWindow(form.getForm());
    }

    public void executeFormCallback(FormWindow window) {
        if (window instanceof FormWindowModal modal) {
            FormResponseModal response = modal.getResponse();
            Object id = null;
            if (response != null) {
                id = response.getClickedButtonId() == 0;
            }
            formCallback.execute(this, id, null);
            formCallback = null;
        } else if (window instanceof FormWindowSimple simple) {
            FormResponseSimple response = simple.getResponse();
            Object id = null;
            if (response != null) {
                id = response.getClickedButtonId();
            }
            formCallback.execute(this, id, null);
            formCallback = null;
        }
    }

}
