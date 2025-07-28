package net.axda.se.api.game.data;

import cn.nukkit.Server;
import org.graalvm.polyglot.HostAccess;

public class IntPos implements Pos {

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
    public IntPos(int x, int y, int z, int dimid, String level) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimid = dimid;
        this.level = level;
        switch (dimid) {
            case 0: dim = "主世界"; break;
            case 1: dim = "下界"; break;
            case 2: dim = "末地"; break;
        }
    }

    @HostAccess.Export
    public IntPos(int x, int y, int z, int dimid) {
        this(x, y, z, dimid, Server.getInstance().getDefaultLevel().getName());
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
}
