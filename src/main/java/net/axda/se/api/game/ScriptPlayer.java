package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
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

import java.util.*;

public class ScriptPlayer extends API implements ProxyObject, Pos {

    private Player player;
    private Server server = Server.getInstance();
    private Value formCallback;
    private int formId = -114514;

    @Override
    public Object getMember(String key) {
        return switch (key) {
            case "..nukkit_player.." -> player;
            //玩家对象属性
            case "name" -> player.getName();
            case "pos" -> pos();
            case "feetPos" -> null;
            case "blockPos" -> blockPos();
            case "lastDeathPos" -> null;
            case "realName" -> player.getLoginChainData().getUsername();
            case "xuid" -> player.getLoginChainData().getXUID();
            case "uuid" -> player.getUniqueId().toString();
            case "permLevel" -> null;
            case "gameMode" -> player.getGamemode();
            case "canFly" -> null;
            case "canSleep" -> null;
            case "canBeSeenOnMap" -> null;
            case "canFreeze" -> null;
            case "canSeeDaylight" -> null;
            case "canShowNameTag" -> null;
            case "canStartSleepInBed" -> null;
            case "canPickupItems" -> null;
            case "maxHealth" -> player.getHealth();
            case "health" -> null;
            case "inAir" -> null;
            case "inWater" -> null;
            case "inLava" -> null;
            case "inRain" -> null;
            case "inSnow" -> null;
            case "inWall" -> null;
            case "inWaterOrRain" -> null;
            case "inWorld" -> player.getLocation().getLevel().getDimension() == 1;
            case "inClouds" -> null;
            case "speed" -> player.getWalkSpeed();
            case "direction" -> null;
            case "uniqueId" -> null;
            case "runtimeId" -> null;
            case "langCode" -> player.getLocale().getLanguage();
            case "isLoading" -> null;
            case "isInvisible" -> null;
            case "isInsidePortal" -> null;
            case "isHurt" -> null;
            case "isTrusting" -> null;
            case "isTouchingDamageBlock" -> null;
            case "isHungry" -> null;
            case "isOnFire" -> null;
            case "isOnGround" -> null;
            case "isOnHotBlock" -> null;
            case "isTrading" -> null;
            case "isAdventure" -> player.getGamemode() == Player.ADVENTURE;
            case "isGliding" -> null;
            case "isSurvival" -> player.getGamemode() == Player.SURVIVAL;
            case "isSpectator" -> player.getGamemode() == Player.SPECTATOR;
            case "isRiding" -> null;
            case "isDancing" -> null;
            case "isCreative" -> player.getGamemode() == Player.CREATIVE;
            case "isFlying" -> null;
            case "isSleeping" -> player.isSleeping();
            case "isMoving" -> null;
            case "isSneaking" -> player.isSneaking();

            //玩家对象函数
            case "tell", "sendText" -> (ProxyExecutable) this::tell;
            case "isOp" -> (ProxyExecutable) this::isOP;
            case "kick", "disconnect" -> (ProxyExecutable) this::kick;
            case "setTitle" -> (ProxyExecutable) this::setTitle;
            case "sendToast" -> (ProxyExecutable) this::sendToast;
            case "runcmd" -> (ProxyExecutable) this::runcmd;
            case "talkAs" -> (ProxyExecutable) this::talkAs;
            case "distanceTo", "distanceToSqr" -> (ProxyExecutable) this::distanceTo;
            case "talkTo" -> (ProxyExecutable) this::talkTo;
            case "teleport" -> (ProxyExecutable) this::teleport;
            case "kill" -> (ProxyExecutable) this::kill;
            case "hurt" -> null;
            case "heal" -> null;
            case "setAbsorption" -> null;
            case "setAttackDamage" -> null;
            case "setMaxAttackDamage" -> null;
            case "setFollowRange" -> null;
            case "setKnockbackResistance" -> null;
            case "setLuck" -> null;
            case "setMovementSpeed" -> null;
            case "setUnderwaterMovementSpeed" -> null;
            case "setLavaMovementSpeed" -> null;
            case "setMaxHealth" -> null;
            case "setHungry" -> null;
            case "setFire" -> null;
            case "stopFire" -> null;
            case "setScale" -> null;
            case "rename" -> null;
            case "getBlockStandingOn" -> null;
            case "getDevice" -> null;
            case "getHand" -> null;
            case "getOffHand" -> null;
            case "getInventory" -> null;
            case "getArmor" -> null;
            case "getEnderChest" -> null;
            case "getRespawnPosition" -> null;
            case "setRespawnPosition" -> null;
            case "giveItem" -> null;
            case "clearItem" -> null;
            case "refreshItems" -> null;
            case "refreshChunks" -> null;
            case "setPermLevel" -> null;
            case "setGameMode" -> (ProxyExecutable) this::setGameMode;
            case "addLevel" -> null;
            case "reduceLevel" -> null;
            case "getLevel" -> (ProxyExecutable) this::getExpLevel;
            case "setLevel" -> (ProxyExecutable) this::setExpLevel;
            case "resetLevel" -> (ProxyExecutable) this::resetExpLevel;
            case "getCurrentExperience" -> (ProxyExecutable) this::getExp;
            case "setCurrentExperience" -> (ProxyExecutable) this::setExp;
            case "getTotalExperience" -> null;
            case "setTotalExperience" -> null;
            case "addExperience" -> (ProxyExecutable) this::addExp;
            case "reduceExperience" -> (ProxyExecutable) this::reduceExp;
            case "getXpNeededForNextLevel" -> null;
            case "transServer" -> null;
            case "crash" -> null;
            case "setSidebar" -> null;
            case "removeSidebar" -> null;
            case "setBossBar" -> null;
            case "removeBossBar" -> null;
            case "getNbt" -> null;
            case "setNbt" -> null;
            case "getPlayerNbt" -> null;
            case "setPlayerNbt" -> null;
            case "setPlayerNbtTags" -> null;
            case "deletePlayerNbt" -> null;
            case "addTag" -> null;
            case "removeTag" -> null;
            case "hasTag" -> null;
            case "getAllTags" -> null;
            case "getAbilities" -> null;
            case "getAttributes" -> null;
            case "isSprinting" -> null;
            case "setSprinting" -> null;
            case "getEntityFromViewVector" -> null;
            case "getBlockFromViewVector" -> null;
            case "sendPacket" -> null;
            case "getBiomeId" -> null;
            case "getBiomeName" -> null;
            case "setAbility" -> null;
            case "getAllEffects" -> null;
            case "addEffect" -> null;
            case "removeEffect" -> null;
            case "toEntity" -> null;
            case "isSimulatedPlayer" -> null;

            //表单相关函数
            case "sendModalForm" -> (ProxyExecutable) this::sendModalForm;
            case "sendSimpleForm" -> (ProxyExecutable) this::sendSimpleForm;
            case "sendCustomForm" -> (ProxyExecutable) this::sendCustomForm;
            case "closeForm" -> (ProxyExecutable) this::closeForm;
            case "sendForm" -> (ProxyExecutable) this::sendForm;
            default -> null;
        };
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
            String msg = toString(args[0]);
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
            String content = toString(args[0]);
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
        this.formId = player.showFormWindow(form);
        return formId;
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
        this.formId = player.showFormWindow(form);
        return formId;
    }

    public int sendCustomForm(Value... args) {
        String json = args[0].asString();
        FormWindowCustom form = new FormWindowCustom("");
        form.setResponse(json);
        this.formCallback = args[1];
        this.formId = this.player.showFormWindow(form);
        return formId;
    }

    public boolean closeForm(Value... args) {
        player.closeFormWindows();
        formCallback = null;
        return true;
    }

    public int sendForm(Value... args) {
        Form form = args[0].as(Form.class);
        this.formCallback = args[1];
        this.formId = player.showFormWindow(form.getForm());
        return formId;
    }

    public void executeFormCallback(FormWindow window, int formId) {
        if (formId != this.formId) return;
        if (window instanceof FormWindowModal modal) {
            FormResponseModal response = modal.getResponse();
            Object id = null;
            if (response != null) {
                id = response.getClickedButtonId() == 0;
            }
            formCallback.execute(this, id, null);
        } else if (window instanceof FormWindowSimple simple) {
            FormResponseSimple response = simple.getResponse();
            Object id = null;
            if (response != null) {
                id = response.getClickedButtonId();
            }
            formCallback.execute(this, id, null);
        } else if (window instanceof FormWindowCustom custom) {
            FormResponseCustom response = custom.getResponse();
            ArrayList<Object> data = null;
            if (response != null) {
                data = new ArrayList<>(response.getResponses().values());
            }
            formCallback.execute(this, data, null);
        }
        formCallback = null;
    }

}
