package net.axda.se.api.game.data;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.io.Serializable;

public class IntPos implements Pos, Serializable {

    @HostAccess.Export
    public int x;

    @HostAccess.Export
    public int y;

    @HostAccess.Export
    public int z;

    @HostAccess.Export
    public String dim;

    @HostAccess.Export
    public int dimid;

    @HostAccess.Export
    public String level;

    @HostAccess.Export
    public IntPos(int x, int y, int z, Value value) {
        Level l = API.getLevel(value);
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimid = l.getDimension();
        this.level = l.getName();
        switch (dimid) {
            case 0: dim = "主世界"; break;
            case 1: dim = "下界"; break;
            case 2: dim = "末地"; break;
        }
    }

    public IntPos(Vector3 vector3, Level level) {
        this(vector3.getFloorX(), vector3.getFloorY(), vector3.getFloorZ(), level);
    }

    @HostAccess.Export
    public IntPos(int x, int y, int z, Level level) {
        this(x, y, z, Value.asValue(level));
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public String getDim() {
        return dim;
    }

    @Override
    public int getDimId() {
        return dimid;
    }

    @Override
    public Location getLocation() {
        return new Location(x, y, z);
    }

    @Override
    public String toString() {
        return API.GSON.toJson(this);
    }
}
