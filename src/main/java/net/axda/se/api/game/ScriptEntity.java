package net.axda.se.api.game;

import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;
import net.axda.se.api.API;
import net.axda.se.api.ProxyAPI;
import net.axda.se.api.ProxyField;
import net.axda.se.api.game.data.FloatPos;
import net.axda.se.api.game.data.IntPos;
import net.axda.se.api.game.data.Pos;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

public class ScriptEntity extends API implements ProxyAPI, Pos {

    private Entity entity;

    public ScriptEntity(Entity entity) {
        this.entity = entity;
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
        return new IntPos(block.getFloorX(), block.getFloorY(), block.getFloorZ(), block.getLevel().getDimension())
    }

    @ProxyField
    public int maxHealth() {
        return entity.getMaxHealth();
    }

    @ProxyField
    public int health() {
        return (int) Math.floor(entity.getHealth());
    }

}
