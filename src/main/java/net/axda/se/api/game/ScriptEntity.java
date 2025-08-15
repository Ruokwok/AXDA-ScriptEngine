package net.axda.se.api.game;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;
import net.axda.se.api.API;
import net.axda.se.api.game.data.FloatPos;
import net.axda.se.api.game.data.Pos;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

public class ScriptEntity extends API implements ProxyObject, Pos {

    private Entity entity;

    @Override
    public Object getMember(String key) {
        switch (key) {
            case "name": return entity.getName();
            case "id": return entity.getId();
            case "pos": return new FloatPos(entity.getX(), entity.getY(), entity.getZ(), entity.getLevel().getDimension(), entity.getLevel().getName());
            case "type": return entity.getSaveId();
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

    public ScriptEntity(Entity entity) {
        this.entity = entity;
    }
}
