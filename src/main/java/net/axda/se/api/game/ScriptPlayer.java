package net.axda.se.api.game;

import cn.nukkit.Player;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;

public class ScriptPlayer extends API {

    private Player player;

    @HostAccess.Export
    public final String name;

    public Object pos;

    public Object feelPos;

    public Object blockPos;

    public Object lastDeathPos;

    public String realName;

    public String xuid;

    public String uuid;

    public int permLevel;

    public int gameMode;

    public boolean canFly;

    public boolean canSleep;

    public boolean canBeSeenOnMap;

    public boolean canFreeze;

    public boolean canSeeDaylight;

    public boolean canShowNameTag;

    public boolean canStartSleepInBed;

    public boolean canPickupItems;

    public int maxHealth;

    public int health;

    public boolean inAir;

    public boolean inWater;

    public boolean inLava;

    public boolean inRain;

    public boolean inSnow;

    public boolean inWall;

    public boolean inWaterOrRain;

    public boolean inWorld;

    public boolean inClouds;

    public float speed;

    public Object direction;

    public String uniqueId;

    public String runtimeId;

    public String langCode;

    public boolean isLoading;

    public boolean isInvisible;

    public boolean isInsidePortal;

    public boolean isHurt;

    public boolean isTrusting = false;

    public boolean isTouchingDamageBlock;

    public boolean isHungry;

    public boolean isOnFire;

    public boolean isOnGround;

    public boolean isOnHotBlock;

    public boolean isTrading;

    public boolean isAdventure;

    public boolean isGliding;

    public boolean isSurvival;

    public boolean isSpectator;

    public boolean isRiding;

    public boolean isDancing;

    public boolean isCreative;

    public boolean isFlying;

    public boolean isSleeping;

    public boolean isMoving;

    public boolean isSneaking;

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
    public boolean tell(String msg) {
        return false;
    }

    @HostAccess.Export
    public boolean sendText(String msg) {
        return tell(msg);
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
        this.name = player.getName();

    }

    public Player getNukkitPlayer() {
        return player;
    }

}
