package net.axda.se.api.game.data;


import cn.nukkit.level.Location;

public interface Pos {

    public double getX();

    public double getY();

    public double getZ();

    public String getLevel();

    public String getDim();

    public int getDimId();

    public Location getLocation();

}
