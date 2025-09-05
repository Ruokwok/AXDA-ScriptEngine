package net.axda.se.api.game;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.PlayerFood;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
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
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.StringTag;
import cn.nukkit.network.protocol.types.DisplaySlot;
import cn.nukkit.network.protocol.types.SortOrder;
import cn.nukkit.scoreboard.scoreboard.IScoreboard;
import cn.nukkit.scoreboard.scoreboard.Scoreboard;
import cn.nukkit.utils.BossBarColor;
import cn.nukkit.utils.DummyBossBar;
import me.onebone.economyapi.EconomyAPI;
import net.axda.se.api.ProxyAPI;
import net.axda.se.api.ProxyField;
import net.axda.se.api.game.data.DirectionAngle;
import net.axda.se.api.game.data.FloatPos;
import net.axda.se.api.game.data.IntPos;
import net.axda.se.api.game.data.Pos;
import net.axda.se.api.gui.CustomForm;
import net.axda.se.api.gui.Form;
import net.axda.se.api.gui.ModalForm;
import net.axda.se.api.gui.SimpleForm;
import net.axda.se.api.nbt.NbtCompound;
import net.axda.se.exception.UnsupportedMemberException;
import net.axda.se.exception.ValueTypeException;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;

import java.util.*;

public class ScriptPlayer extends API implements ProxyAPI, Pos {

    private Player player;
    private Server server = Server.getInstance();
    private Hashtable<Integer, Form> formCallback = new Hashtable<>();
    private int formId = -114514;
    private HashMap<Integer, Long> bossBarMap;
    private Scoreboard scoreboard;

    public ScriptPlayer(Player player) {
        this.player = player;
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

    @Override
    public Object getOrigin() {
        return player;
    }

    @ProxyField
    public String name() {
        return player.getDisplayName();
    }

    @ProxyField
    public FloatPos pos() {
        return new FloatPos(player, player.getLevel());
    }

    @ProxyField
    public IntPos blockPos() {
        return new IntPos(player.getLevelBlock(), player.getLevel());
    }

    @ProxyField
    public String realName() {
        return player.getLoginChainData().getUsername();
    }

    @ProxyField
    public String xuid() {
        return player.getLoginChainData().getXUID();
    }

    @ProxyField
    public String uuid() {
        return player.getLoginChainData().getClientUUID().toString();
    }

    @ProxyField
    public int premLevel() {
        return player.isOp() ? 1 : 0;
    }

    @ProxyField
    public int gameMode() {
        return player.getGamemode();
    }

    @ProxyField
    public boolean canFly() {
        return player.getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT);
    }

    @ProxyField
    public boolean canSleep() {
        int time = player.getLevel().getTime() % Level.TIME_FULL;
        return time >= Level.TIME_NIGHT && time < Level.TIME_SUNRISE;
    }

    @ProxyField
    public boolean canBeSeenOnMap() {
        API.printException(new UnsupportedMemberException("canBeSeenOnMap"));
        return true;
    }

    @ProxyField
    public boolean canFreeze() {
        API.printException(new UnsupportedMemberException("canFreeze"));
        return true;
    }

    @ProxyField
    public boolean canSeeDaylight() {
        Level level = player.getLevel();
        if (level.getDimension() != Level.DIMENSION_OVERWORLD) return false;
        int time = level.getTime() % Level.TIME_FULL;
        boolean isDayTime = time >= Level.TIME_DAY && time < Level.TIME_NIGHT;
        if (!isDayTime) return false;
        if (level.isRaining() || level.isThundering()) return false;
        Vector3 eyePosition = new Vector3(player.getX(), player.getEyeHeight(), player.getZ());
        return level.canBlockSeeSky(eyePosition);
    }

    @ProxyField
    public boolean canShowNameTag() {
        return player.isNameTagVisible();
    }

    @ProxyField
    public boolean canStartSleepInBed() {
        int time = player.getLevel().getTime() % Level.TIME_FULL;
        return time >= Level.TIME_NIGHT && time < Level.TIME_SUNRISE;
    }

    @ProxyField
    public boolean canPickupItems() {
        return !player.getInventory().isFull();
    }

    @ProxyField
    public int maxHealth() {
        return player.getMaxHealth();
    }

    @ProxyField
    public float health() {
        return player.getHealth();
    }

    @ProxyField
    public boolean inAir() {
        return player.getInAirTicks() > 0;
    }

    @ProxyField
    public boolean inWater() {
        return player.isSwimming();
    }

    @ProxyField
    public boolean inLava() {
        API.printException(new UnsupportedMemberException("inLava"));
        return false;
    }

    @ProxyField
    public boolean inRain() {
        return player.getLevel().isRaining() &&
                player.getLevel().canBlockSeeSky(player);
    }

    @ProxyField
    public boolean inSnow() {
        API.printException(new UnsupportedMemberException("inSnow"));
        return false;
    }

    @ProxyField
    public boolean inWall() {
        API.printException(new UnsupportedMemberException("inWall"));
        return false;
    }

    @ProxyField
    public boolean inWaterOrRain() {
        return inWater() || inRain();
    }

    @ProxyField
    public boolean inWorld() {
        return player.getLevel().getDimension() == 0;
    }

    @ProxyField
    public boolean inClouds() {
        return inWorld() && player.getY() > 128;
    }

    @ProxyField
    public float speed() {
        return player.getMovementSpeed();
    }

    @ProxyField
    public DirectionAngle direction() {
        return new DirectionAngle(player);
    }

    @ProxyField
    public String uniqueId() {
        return player.getUniqueId().toString();
    }

    @ProxyField
    public String runtimeId() {
        return String.valueOf(player.getId());
    }

    @ProxyField
    public String langCode() {
        return player.getLoginChainData().getLanguageCode();
    }

    @ProxyField
    public boolean isLoading() {
        return player.isLoaderActive();
    }

    @ProxyField
    public boolean isInvisible() {
        return player.getDataPropertyBoolean(Entity.DATA_FLAG_INVISIBLE);
    }

    @ProxyField
    public boolean isInsidePortal() {
        API.printException(new UnsupportedMemberException("isInsidePortal"));
        return false;
    }

    @ProxyField
    public boolean isHurt() {
        return health() < maxHealth();
    }

    @ProxyField
    public boolean isTrusting() {
        return false;
    }

    @ProxyField
    public boolean isTouchingDamageBlock() {
        Location footPosition = player.getLocation().subtract(0, 1, 0);
        Block block = player.getLevel().getBlock(footPosition);
        switch (block.getId()) {
            case Block.CACTUS:
            case Block.LAVA:
            case Block.STILL_LAVA:
            case Block.FIRE:
            case Block.MAGMA:
                return true;
            default:
                return false;
        }
    }

    @ProxyField
    public boolean isHungry() {
        return player.getFoodData().getLevel() < player.getFoodData().getMaxLevel();
    }

    @ProxyField
    public boolean isOnFire() {
        return player.isOnFire();
    }

    @ProxyField
    public boolean isOnGround() {
        return player.isOnGround();
    }

    @ProxyField
    public boolean isOnHotBlock() {
        API.printException(new UnsupportedMemberException("isOnHotBlock"));
        return false;
    }

    @ProxyField
    public boolean isTrading() {
        return false;
    }

    @ProxyField
    public boolean isAdventure() {
        return player.getGamemode() == 2;
    }

    @ProxyField
    public boolean isGliding() {
        return player.isGliding();
    }

    @ProxyField
    public boolean isSurvival() {
        return player.getGamemode() == 0;
    }

    @ProxyField
    public boolean isSpectator() {
        return player.getGamemode() == 3;
    }

    @ProxyField
    public boolean isRiding() {
        return player.getRiding() != null;
    }

    @ProxyField
    public boolean isDancing() {
        API.printException(new UnsupportedMemberException("isDancing"));
        return false;
    }

    @ProxyField
    public boolean isCreative() {
        return player.getGamemode() == 1;
    }

    @ProxyField
    public boolean isFlying() {
        return player.getAdventureSettings().get(AdventureSettings.Type.FLYING);
    }

    @ProxyField
    public boolean isSleeping() {
        return player.isSleeping();
    }

    @ProxyField
    public boolean isMoving() {
        return player.getMovementSpeed() > 0;
    }

    @ProxyField
    public boolean isSneaking() {
        return player.isSneaking();
    }

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
        String msg = toString(args[0]);
        int type = (args.length < 2)? 0: args[1].asInt();
        switch (type) {
            case 0:
                player.sendMessage(msg);
                return true;
            case 1:
                player.sendChat(msg);
                return true;
            case 4:
                player.sendPopup(msg);
                return true;
            case 5:
                player.sendTip(msg);
                return true;
            default:
                return false;
        }
    }

    @HostAccess.Export
    public boolean sendText(Value... args) {
        return tell(args);
    }

    @HostAccess.Export
    public boolean setTitle(Value... args) {
        if (args.length == 0) return false;
        String content = toString(args[0]);
        int type = (args.length <= 2)? 2: args[1].asInt();
        int fadeInTime = (args.length <= 3)? 10: args[2].asInt();
        int stayTime = (args.length <= 4)? 70: args[3].asInt();
        int fadeOutTime = (args.length <= 5)? 20: args[4].asInt();
        switch (type) {
            case 0: player.clearTitle(); return true;
            case 1: player.resetTitleSettings(); return true;
            case 2: player.sendTitle(content, "", fadeInTime, stayTime, fadeOutTime); return true;
            case 3: player.sendTitle("", content, fadeInTime, stayTime, fadeOutTime); return true;
            case 4: player.sendActionBar(content, fadeInTime, stayTime, fadeOutTime); return true;
            default: return false;
        }
    }

    @HostAccess.Export
    public boolean sendToast(Value... args) {
        player.sendToast(API.toString(args[0]), API.toString(args[1]));
        return true;
    }

    @HostAccess.Export
    public boolean runcmd(Value... args) {
        return player.getServer().dispatchCommand(player, args[0].asString());
    }

    @HostAccess.Export
    public boolean talkAs(Value... args) {
        return player.chat(args[0].asString());
    }

    @HostAccess.Export
    public double distanceTo(Value... args) {
        Pos pos = API.toPos(args[0]);
        if (!player.getLevel().getName().equals(pos.getLevel())) {
            return Integer.MAX_VALUE;
        } else {
            return (float) player.distance(new Vector3(pos.getX(), pos.getY(), pos.getZ()));
        }
    }

    @HostAccess.Export
    public double distanceToSqr(Value... args) {
        return distanceTo(args);
    }

    @HostAccess.Export
    public boolean talkTo(Value... args) {
        String message = args[0].asString();
        Player target = (Player) args[1].as(ProxyAPI.class).getOrigin();
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
    }

    @HostAccess.Export
    public boolean teleport(Value... args) {
        Pos pos;
        if (args[0].isNumber()) {
            float x = args[0].asFloat();
            float y = args[1].asFloat();
            float z = args[2].asFloat();
            pos = new FloatPos(x, y, z, args[3]);
            player.teleport(pos.getLocation());
            if (args.length == 5) {
                //TODO 设置玩家朝向
            }
        } else {
            pos = args[0].as(Pos.class);
            player.teleport(pos.getLocation());
        }
        return false;
    }

    @HostAccess.Export
    public boolean kill(Value... args) {
        player.setHealth(0);
        return true;
    }

    @HostAccess.Export
    public boolean setGameMode(Value... arg) {
        return player.setGamemode(arg[0].asInt());
    }

    @HostAccess.Export
    public boolean hurt(Value... args) {
        float damage = args[0].asFloat();
        int type = args[1].asInt();
        return player.attack(new EntityDamageByEntityEvent(player, player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage));
    }

    @HostAccess.Export
    public boolean heal(Value... args) {
        player.heal(args[0].asInt());
        return true;
    }

    @HostAccess.Export
    public boolean setHealth(Value... args) {
        player.setHealth(args[0].asInt());
        return true;
    }

    @HostAccess.Export
    public boolean setAbsorption(Value... args) {
        player.setAbsorption(args[0].asInt());
        return true;
    }

    @HostAccess.Export
    public boolean setAttackDamage(Value... args) {
        API.printException(new UnsupportedMemberException("setAttackDamage"));
        return false;
    }

    @HostAccess.Export
    public boolean setMaxAttackDamage(Value... args) {
        API.printException(new UnsupportedMemberException("setMaxAttackDamage"));
        return false;
    }

    @HostAccess.Export
    public boolean setFollowRange(Value... args) {
        API.printException(new UnsupportedMemberException("setFollowRange"));
        return false;
    }

    @HostAccess.Export
    public boolean setKnockbackResistance(Value... args) {
        API.printException(new UnsupportedMemberException("setKnockbackResistance"));
        return false;
    }

    @ProxyField
    public boolean setLuck(Value... args) {
        API.printException(new UnsupportedMemberException("setLuck"));
        return false;
    }

    @HostAccess.Export
    public boolean setMovementSpeed(Value... args) {
        player.setMovementSpeed(args[0].asFloat());
        return true;
    }

    @HostAccess.Export
    public boolean setUnderwaterMovementSpeed(Value... args) {
        API.printException(new UnsupportedMemberException("setUnderwaterMovementSpeed"));
        return false;
    }

    @HostAccess.Export
    public boolean setLavaMovementSpeed(Value... args) {
        API.printException(new UnsupportedMemberException("setLavaMovementSpeed"));
        return false;
    }

    @HostAccess.Export
    public boolean setMaxHealth(Value... args) {
        player.setMaxHealth(args[0].asInt());
        return true;
    }

    @HostAccess.Export
    public boolean setHungry(Value... args) {
        PlayerFood data = player.getFoodData();
        if (data != null) {
            data.setLevel(args[1].asInt());
            return true;
        } else {
            return false;
        }
    }

    @HostAccess.Export
    public boolean setFire(Value... args) {
        player.setOnFire(args[0].asInt());
        return true;
    }

    @HostAccess.Export
    public boolean stopFire(Value... args) {
        player.setOnFire(0);
        player.extinguish();
        return true;
    }

    @HostAccess.Export
    public boolean setScale(Value... args) {
        player.setScale(args[0].asFloat());
        return true;
    }

    @HostAccess.Export
    public boolean rename(Value... args) {
        player.setDisplayName(args[0].asString());
        return true;
    }

    @HostAccess.Export
    public ScriptBlock getBlockStandingOn(Value... args) {
        return new ScriptBlock(player.getPosition().add(0, -0.1).getLevelBlock());
    }

    @HostAccess.Export
    public Object getDevice(Value... args) {
        return new Device(player);
    }

    @HostAccess.Export
    public ScriptItem getHand(Value... args) {
        return new ScriptItem(player.getInventory().getItemInHand());
    }

    @HostAccess.Export
    public ScriptItem getOffHand(Value... args) {
        return new ScriptItem(player.getInventory().getItemInHandFast());
    }

    @HostAccess.Export
    public Object getInventory(Value... args) {
        return new Container(player.getInventory());
    }

    @HostAccess.Export
    public Container getArmor(Value... args) {
        return new ArmorContainer(player);
    }

    @HostAccess.Export
    public Object getEnderChest(Value... args) {
        API.printException(new UnsupportedMemberException("getEnderChest"));
        return null;
    }

    @HostAccess.Export
    public IntPos getRespawnPosition(Value... args) {
        return new IntPos(player.getSpawn(), player.getSpawn().level);
    }

    @HostAccess.Export
    public boolean setRespawnPosition(Value... args) {
        try {
            Pos pos = args[0].as(Pos.class);
            player.setSpawn(pos.getLocation());
        } catch (Exception e) {
            float x = args[0].asFloat();
            float y = args[1].asFloat();
            float z = args[2].asFloat();
            player.setSpawn(new FloatPos(x, y, z, args[3]).getLocation());
        }
        return true;
    }

    @HostAccess.Export
    public boolean giveItem(Value... args) {
        ScriptItem item = args[0].getMember("..origin..").as(ScriptItem.class);
        int count = 1;
        if (args.length > 1) {
            count = args[1].asInt();
        }
        item.getItem().setCount(count);
        player.getInventory().addItem(item.getItem());
        return true;
    }

    @HostAccess.Export
    public int clearItem(Value... args) {
        throw new UnsupportedMemberException("clearItem");
    }

    @HostAccess.Export
    public boolean refreshItems(Value... args) {
        throw new UnsupportedMemberException("refreshItems");
    }

    @HostAccess.Export
    public boolean refreshChunks(Value... args) {
        throw new UnsupportedMemberException("refreshChunks");
    }

    @HostAccess.Export
    public boolean setPermLevel(Value... args) {
        throw new UnsupportedMemberException("setPermLevel");
    }

    @HostAccess.Export
    public boolean addLevel(Value... args) {
        throw new UnsupportedMemberException("addLevel");
    }

    @HostAccess.Export
    public boolean reduceLevel(Value... args) {
        throw new UnsupportedMemberException("addLevel");
    }

    @HostAccess.Export
    public int getLevel(Value... args) {
        return player.getExperienceLevel();
    }

    @HostAccess.Export
    public boolean setLevel(Value... args) {
        int level = args[0].asInt();
        player.setExperience(0, level);
        return player.getExperienceLevel() == level;
    }

    @HostAccess.Export
    public boolean resetLevel(Value... args) {
        player.setExperience(0, 0);
        return player.getExperienceLevel() == 0;
    }

    @HostAccess.Export
    public boolean addExperience(Value... args) {
        int level = args[0].asInt();
        player.addExperience(level);
        return player.getExperienceLevel() == level;
    }

    @HostAccess.Export
    public boolean reduceExperience(Value... args) {
        int level = args[0].asInt();
        if (player.getExperience() - level < 0) {
            player.setExperience(0);
        } else {
            player.addExperience(-level);
        }
        return true;
    }

    @HostAccess.Export
    public int getCurrentExperience(Value... args) {
        return player.getExperience();
    }

    @HostAccess.Export
    public boolean setCurrentExperience(Value... args) {
        int exp = args[0].asInt();
        player.setExperience(exp);
        return true;
    }

    @HostAccess.Export
    public int getTotalExperience(Value... args) {
        throw new UnsupportedMemberException("getTotalExperience");
    }

    @HostAccess.Export
    public boolean setTotalExperience(Value... args) {
        throw new UnsupportedMemberException("setTotalExperience");
    }

    @HostAccess.Export
    public int getXpNeededForNextLevel(Value... args) {
        throw new UnsupportedMemberException("getXpNeededForNextLevel");
    }

    @HostAccess.Export
    public boolean transServer(Value... args) {
        player.transfer(args[0].asString(), args[1].asInt());
        return true;
    }

    @HostAccess.Export
    public boolean crash(Value... args) {
        API.printException(new UnsupportedMemberException("crash"));
        return false;
    }

    @HostAccess.Export
    public boolean setSidebar(Value... args) {
        String title = args[0].asString();
        Map<String, Integer> map = args[1].as(Map.class);
        int sort = 0;
        if (args.length > 2) {
            sort = args[2].asInt();
        }
        if (sort == 1) {
            scoreboard = new Scoreboard(title, title, "dummy", SortOrder.DESCENDING);
        } else if (sort == 0) {
            scoreboard = new Scoreboard(title, title, "dummy", SortOrder.ASCENDING);
        }
        if (scoreboard == null) return false;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            scoreboard.addLine(entry.getKey(), entry.getValue());
        }
        scoreboard.addViewer(player, DisplaySlot.SIDEBAR);
        return true;
    }

    @HostAccess.Export
    public boolean removeSidebar(Value... args) {
        if (scoreboard == null) return false;
        player.removeScoreboard(scoreboard);
        return true;
    }

    @HostAccess.Export
    public boolean setBossBar(Value... args) {
        int uid = args[0].asInt();
        String title = args[1].asString();
        int percent = args[2].asInt();
        int color = 2;
        if (args.length == 4) color = args[3].asInt();
        DummyBossBar.Builder builder = new DummyBossBar.Builder(player);
        DummyBossBar bar = builder.text(title).length(percent).color(BossBarColor.values()[color]).build();
        long id = player.createBossBar(bar);
        if (bossBarMap == null) bossBarMap = new HashMap<>();
        bossBarMap.put(uid, id);
        return true;
    }

    @HostAccess.Export
    public boolean removeBossBar(Value... args) {
        if (bossBarMap == null) {
            return false;
        }
        player.removeBossBar(bossBarMap.get(args[0].asInt()));
        return true;
    }

    @HostAccess.Export
    public Object getNbt(Value... args) {
        return new NbtCompound(player.namedTag);
    }

    @HostAccess.Export
    public boolean setNbt(Value... args) {
        NbtCompound nbt = args[0].as(NbtCompound.class);
        player.namedTag = nbt.getNbt();
        return true;
    }

    @HostAccess.Export
    public boolean addTag(Value... args) {
        String tag = args[0].asString();
        if (player.containTag(tag)) return false;
        player.addTag(tag);
        return true;
    }

    @HostAccess.Export
    public boolean removeTag(Value... args) {
        player.removeTag(args[0].asString());
        return true;
    }

    @HostAccess.Export
    public boolean hasTag(Value... args) {
        return player.containTag(args[0].asString());
    }

    @HostAccess.Export
    public ProxyArray getAllTags(Value... args) {
        List<StringTag> allTags = player.getAllTags();
        ArrayList<Object> list = new ArrayList<>();
        for (StringTag tag : allTags) {
            list.add(tag.data);
        }
        return ProxyArray.fromList(list);
    }

    @HostAccess.Export
    public Object getAbilities(Value... args) {
        throw new UnsupportedMemberException("getAbilities");
    }

    @HostAccess.Export
    public Object getAttributes(Value... args) {
        throw new UnsupportedMemberException("getAttributes");
    }

    @HostAccess.Export
    public boolean isSprinting(Value... args) {
        return player.isSprinting();
    }

    @HostAccess.Export
    public boolean setSprinting(Value... args) {
        player.setSprinting(args[0].asBoolean());
        return true;
    }

    @HostAccess.Export
    public ScriptEntity getEntityFromViewVector(Value... args) {
        throw new UnsupportedMemberException("getEntityFromViewVector");
    }

    @HostAccess.Export
    public ScriptBlock getBlockFromViewVector(Value... args) {
        throw new UnsupportedMemberException("getBlockFromViewVector");
    }

    @HostAccess.Export
    public boolean sendPacket(Value... args) {
        throw new UnsupportedMemberException("sendPacket");
    }

    @HostAccess.Export
    public int getBiomeId(Value... args) {
        return player.getLevel().getBiomeId(player.getFloorX(), player.getFloorZ());
    }

    @HostAccess.Export
    public String getBiomeName(Value... args) {
        return Biome.getBiome(getBiomeId(null)).getName();
    }

    @HostAccess.Export
    public boolean setAbility(Value... args) {
        throw new UnsupportedMemberException("setAbility");
    }

    @HostAccess.Export
    public Object getAllEffects(Value... args) {
        throw new UnsupportedMemberException("getAllEffects");
    }

    @HostAccess.Export
    public boolean addEffect(Value... args) {
        throw new UnsupportedMemberException("addEffect");
    }

    @HostAccess.Export
    public boolean removeEffect(Value... args) {
        throw new UnsupportedMemberException("removeEffect");
    }

    @HostAccess.Export
    public ScriptEntity toEntity(Value... args) {
        return new ScriptEntity(player);
    }

    @HostAccess.Export
    public boolean isSimulatedPlayer(Value... args) {
        return false;
    }

    @HostAccess.Export
    public int sendModalForm(Value... args) {
        String title = args[0].asString();
        String content = args[1].asString();
        String confirmButton = args[2].asString();
        String cancelButton = args[3].asString();
        FormWindowModal form = new FormWindowModal(title, content, confirmButton, cancelButton);
        this.formId = player.showFormWindow(form);
        ModalForm modalForm = new ModalForm();
        modalForm.setCallback(args[4]);
        this.formCallback.put(formId, modalForm);
        return formId;
    }

    @HostAccess.Export
    public int sendSimpleForm(Value... args) {
        String title = args[0].asString();
        String content = args[1].asString();
        List<String> buttons = args[2].as(List.class);
        List<String> images = args[3].as(List.class);
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
        SimpleForm simpleForm = new SimpleForm();
        simpleForm.setCallback(args[4]);
        this.formCallback.put(formId, simpleForm);
        return formId;
    }

    @HostAccess.Export
    public int sendCustomForm(Value... args) {
        throw new UnsupportedMemberException("sendCustomForm");
    }

    @HostAccess.Export
    public boolean closeForm(Value... args) {
        player.closeFormWindows();
        formCallback.clear();
        return true;
    }

    @HostAccess.Export
    public int sendForm(Value... args) {
        Form form = args[0].as(Form.class);
        this.formId = player.showFormWindow(form.getForm());
        form.setCallback(args[1]);
        this.formCallback.put(formId, form);
        return formId;
    }

    public void executeFormCallback(FormWindow window, int formId) {
        if (!formCallback.containsKey(formId)) return;
        if (window instanceof FormWindowModal modal) {
            FormResponseModal response = modal.getResponse();
            Object id = null;
            if (response != null) {
                id = response.getClickedButtonId() == 0;
            }
            formCallback.get(formId).getCallback().execute(this, id, null);
        } else if (window instanceof FormWindowSimple simple) {
            FormResponseSimple response = simple.getResponse();
            Object id = null;
            if (response != null) {
                id = response.getClickedButtonId();
            }
            formCallback.get(formId).getCallback().execute(this, id, null);
        } else if (window instanceof FormWindowCustom custom) {
            FormResponseCustom response = custom.getResponse();
            ProxyArray data = null;
            if (response != null) {
//                data = ProxyArray.fromList(new ArrayList<>(response.getResponses().values()));
//                HashMap<Integer, Object> responses = response.getResponses();
                CustomForm form = (CustomForm) formCallback.get(formId);
                List<Object> list = form.getResponse(response);
                data = ProxyArray.fromList(list);
            }
            formCallback.get(formId).getCallback().execute(this, data, null);
        }
        formCallback.remove(formId);
    }

    @HostAccess.Export
    public boolean setMoney(Value[] args) {
        double value = args[0].asDouble();
        int i = EconomyAPI.getInstance().setMoney(player, value);
        return i == 1;
    }

    @HostAccess.Export
    public double getMoney(Value[] args) {
        double value = args[0].asDouble();
        return EconomyAPI.getInstance().myMoney(player);
    }

    @HostAccess.Export
    public boolean addMoney(Value[] args) {
        double value = args[0].asDouble();
        int i = EconomyAPI.getInstance().addMoney(player, value);
        return i == 1;
    }

    @HostAccess.Export
    public boolean reduceMoney(Value[] args) {
        double value = args[0].asDouble();
        int i = EconomyAPI.getInstance().reduceMoney(player, value);
        return i == 1;
    }

}
