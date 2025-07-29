package net.axda.se.api.game;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;
import net.axda.se.ScriptEngine;
import net.axda.se.api.game.data.FloatPos;
import net.axda.se.api.game.data.Pos;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

public class ScriptEntity implements ProxyObject, Pos {

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
    public Object getMember(String key) {
        switch (key) {
            case "name": return entity.getName();
            case "id": return entity.getId();
            case "pos": return new FloatPos(entity.getX(), entity.getY(), entity.getZ(), entity.getLevel().getDimension(), entity.getLevel().getName());
        }
        return null;
    }

    @Override
    public Object getMemberKeys() {
        return null;
    }

    @Override
    public boolean hasMember(String key) {
        return false;
    }

    @Override
    public void putMember(String key, Value value) {

    }
}
