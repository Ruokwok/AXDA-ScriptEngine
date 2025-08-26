package net.axda.se.api.game;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;
import net.axda.se.ScriptLoader;
import net.axda.se.api.API;
import net.axda.se.api.ProxyAPI;
import net.axda.se.api.ProxyField;
import net.axda.se.api.game.data.FloatPos;
import net.axda.se.api.game.data.IntPos;
import net.axda.se.api.game.data.Pos;
import net.axda.se.exception.UnsupportedMemberException;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

public class ScriptEntity extends API implements ProxyAPI, Pos {

    private Entity entity;

    public ScriptEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Object getOrigin() {
        return entity;
    }

    @Override
    public double getX() {
        return entity.getX();
    }

    @Override
    public double getY() {
        return entity.getY();
    }

    @Override
    public double getZ() {
        return entity.getZ();
    }

    @Override
    public String getLevel() {
        return entity.getLevel().getName();
    }

    @Override
    public String getDim() {
        return "";
    }

    @Override
    public int getDimId() {
        return entity.getLevel().getDimension();
    }

    @Override
    public Location getLocation() {
        return entity.getLocation();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScriptEntity se) {
            return this.entity == se.entity;
        }
        if (obj instanceof Entity e) {
            return this.entity == e;
        }
        return false;
    }

    @ProxyField
    public String name() {
        return entity.getName();
    }

    @ProxyField
    public String type() {
        return entity.getSaveId();
    }

    @ProxyField
    public long id() {
        return entity.getId();
    }

    @ProxyField
    public FloatPos feetPos() {
        return new FloatPos(entity.getX(), entity.getY(), entity.getZ(), entity.getLevel().getDimension());
    }

    @ProxyField
    public IntPos blockPos() {
        Block block = entity.getLevelBlock();
        return new IntPos(block.getFloorX(), block.getFloorY(), block.getFloorZ(), block.getLevel().getDimension());
    }

    @ProxyField
    public int maxHealth() {
        return entity.getMaxHealth();
    }

    @ProxyField
    public int health() {
        return (int) Math.floor(entity.getHealth());
    }

    @ProxyField
    public boolean canFly() {
        throw new UnsupportedMemberException("canFly");
    }

    @ProxyField
    public boolean canFreeze() {
        throw new UnsupportedMemberException("canFreeze");
    }

    @ProxyField
    public boolean canSeeDaylight() {
        throw new UnsupportedMemberException("canSeeDaylight");
    }

    @ProxyField
    public boolean canPickupItems() {
        throw new UnsupportedMemberException("canPickupItems");
    }

    @ProxyField
    public boolean inAir() {
        return !entity.isOnGround();
    }

    @ProxyField
    public boolean inWater() {
        return entity.isInsideOfWater();
    }

    @ProxyField
    public boolean inLava() {
        throw new UnsupportedMemberException("inLava");
    }

    @ProxyField
    public boolean inRain() {
        return entity.getLevel().isRaining() &&
                entity.getLevel().canBlockSeeSky(entity);
    }

    @ProxyField
    public boolean inSnow() {
        return entity.getLevel().getBlock(entity).getId() == Block.SNOW_LAYER;
    }

    @ProxyField
    public boolean inWall() {
        throw new UnsupportedMemberException("inWall");
    }

    @ProxyField
    public boolean inWaterOrRain() {
        return inWater() || inRain();
    }

    @ProxyField
    public boolean inWorld() {
        return entity.getLevel().getDimension() == 1;
    }

    @ProxyField
    public float speed() {
        throw new UnsupportedMemberException("speed");
    }

    @ProxyField
    public Object direction() {
        throw new UnsupportedMemberException("direction");
    }

    @ProxyField
    public String uniqueId() {
        return String.valueOf(entity.getId());
    }

    @ProxyField
    public String runtimeId() {
        throw new UnsupportedMemberException("runtimeId");
    }

    @ProxyField
    public void isInvisible() {
        throw new UnsupportedMemberException("isInvisible");
    }

    @ProxyField
    public void isInsidePortal() {
        throw new UnsupportedMemberException("isInsidePortal");
    }

    @ProxyField
    public boolean isTrusting() {
        throw new UnsupportedMemberException("isTrusting");
    }

    @ProxyField
    public boolean isTouchingDamageBlock() {
        throw new UnsupportedMemberException("isTouchingDamageBlock");
    }

    @ProxyField
    public boolean isOnFire() {
        return entity.isOnFire();
    }

    @ProxyField
    public boolean isOnGround() {
        return entity.isOnGround();
    }

    @ProxyField
    public boolean isOnHotBlock() {
        throw new UnsupportedMemberException("isOnHotBlock");
    }

    @ProxyField
    public boolean isTrading() {
        throw new UnsupportedMemberException("isTrading");
    }

    @ProxyField
    public boolean isRiding() {
        throw new UnsupportedMemberException("isRiding");
    }

    @ProxyField
    public boolean isDancing() {
        throw new UnsupportedMemberException("isDancing");
    }

    @ProxyField
    public boolean isSleeping() {
        throw new UnsupportedMemberException("isSleeping");
    }

    @ProxyField
    public boolean isAngry() {
        throw new UnsupportedMemberException("isAngry");
    }

    @ProxyField
    public boolean isBaby() {
        throw new UnsupportedMemberException("isBaby");
    }

    @ProxyField
    public boolean isMoving() {
        throw new UnsupportedMemberException("isMoving");
    }

    @HostAccess.Export
    public boolean teleport(Value... args) {
        throw new UnsupportedMemberException("teleport");
    }

    @HostAccess.Export
    public boolean kill(Value... args) {
        entity.kill();
        return true;
    }

    @HostAccess.Export
    public boolean despawn(Value... args) {
        entity.despawnFromAll();
        return true;
    }

    @HostAccess.Export
    public boolean remove(Value... args) {
        entity.close();
        return true;
    }

    @HostAccess.Export
    public boolean hurt(Value... args) {
        throw new UnsupportedMemberException("hurt");
    }

    @HostAccess.Export
    public boolean heal(Value... args) {
        throw new UnsupportedMemberException("heal");
    }

    @HostAccess.Export
    public boolean setHealth(Value... args) {
        entity.setHealth(args[0].asInt());
        return true;
    }

    @HostAccess.Export
    public boolean setAbsorption(Value... args) {
        throw new UnsupportedMemberException("setAbsorption");
    }

    @HostAccess.Export
    public boolean setAttackDamage(Value... args) {
        throw new UnsupportedMemberException("setAttackDamage");
    }

    @HostAccess.Export
    public boolean setMaxAttackDamage(Value... args) {
        throw new UnsupportedMemberException("setMaxAttackDamage");
    }

    @HostAccess.Export
    public boolean setFollowRange(Value... args) {
        throw new UnsupportedMemberException("setFollowRange");
    }

    @HostAccess.Export
    public boolean setKnockbackResistance(Value... args) {
        throw new UnsupportedMemberException("setKnockbackResistance");
    }

    @HostAccess.Export
    public boolean setLuck(Value... args) {
        throw new UnsupportedMemberException("setLuck");
    }

    @HostAccess.Export
    public boolean setMovementSpeed(Value... args) {
        throw new UnsupportedMemberException("setMovementSpeed");
    }

    @HostAccess.Export
    public boolean setUnderwaterMovementSpeed(Value... args) {
        throw new UnsupportedMemberException("setUnderwaterMovementSpeed");
    }

    @HostAccess.Export
    public boolean setLavaMovementSpeed(Value... args) {
        throw new UnsupportedMemberException("setLavaMovementSpeed");
    }

    @HostAccess.Export
    public boolean setMaxHealth(Value... args) {
        throw new UnsupportedMemberException("setMaxHealth");
    }

    @HostAccess.Export
    public boolean setFire(Value... args) {
        throw new UnsupportedMemberException("setFire");
    }

    @HostAccess.Export
    public boolean stopFire(Value... args) {
        throw new UnsupportedMemberException("stopFire");
    }

    @HostAccess.Export
    public boolean setScale(Value... args) {
        throw new UnsupportedMemberException("setScale");
    }

    @HostAccess.Export
    public boolean distanceTo(Value... args) {
        throw new UnsupportedMemberException("distanceTo");
    }

    @HostAccess.Export
    public boolean distanceToSqr(Value... args) {
        throw new UnsupportedMemberException("distanceToSqr");
    }

    @HostAccess.Export
    public boolean isPlayer(Value... args) {
        return entity instanceof Player;
    }

    @HostAccess.Export
    public ScriptPlayer toPlayer(Value... args) {
        if (entity instanceof Player player) {
            return ScriptLoader.getInstance().getPlayer(player);
        } else {
            return null;
        }
    }

    @HostAccess.Export
    public boolean isItemEntity(Value... args) {
        throw new UnsupportedMemberException("isItemEntity");
    }

    @HostAccess.Export
    public boolean toItem(Value... args) {
        throw new UnsupportedMemberException("toItem");
    }

    @HostAccess.Export
    public ScriptBlock getBlockStandingOn(Value... args) {
        throw new UnsupportedMemberException("getBlockStandingOn");
    }

    @HostAccess.Export
    public Object getArmor(Value... args) {
        throw new UnsupportedMemberException("getArmor");
    }

    @HostAccess.Export
    public boolean hasContainer(Value... args) {
        throw new UnsupportedMemberException("hasContainer");
    }

    @HostAccess.Export
    public Object getContainer(Value... args) {
        throw new UnsupportedMemberException("getContainer");
    }

    @HostAccess.Export
    public boolean refreshItems(Value... args) {
        throw new UnsupportedMemberException("refreshItems");
    }

    @HostAccess.Export
    public boolean addTag(Value... args) {
        throw new UnsupportedMemberException("addTag");
    }

    @HostAccess.Export
    public boolean removeTag(Value... args) {
        throw new UnsupportedMemberException("removeTag");
    }

    @HostAccess.Export
    public boolean hasTag(Value... args) {
        throw new UnsupportedMemberException("hasTag");
    }

    @HostAccess.Export
    public Object getAllTags(Value... args) {
        throw new UnsupportedMemberException("getAllTags");
    }

    @HostAccess.Export
    public Object getNbt(Value... args) {
        throw new UnsupportedMemberException("getNbt");
    }

    @HostAccess.Export
    public boolean setNbt(Value... args) {
        throw new UnsupportedMemberException("setNbt");
    }

    @HostAccess.Export
    public Object getEntityFromViewVector(Value... args) {
        throw new UnsupportedMemberException("getEntityFromViewVector");
    }

    @HostAccess.Export
    public Object getBlockFromViewVector(Value... args) {
        throw new UnsupportedMemberException("getBlockFromViewVector");
    }

    @HostAccess.Export
    public int getBiomeId(Value... args) {
        throw new UnsupportedMemberException("getBiomeId");
    }

    @HostAccess.Export
    public String getBiomeName(Value... args) {
        throw new UnsupportedMemberException("getBiomeName");
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
    public boolean quickEvalMolangScript(Value... args) {
        throw new UnsupportedMemberException("quickEvalMolangScript");
    }
}
