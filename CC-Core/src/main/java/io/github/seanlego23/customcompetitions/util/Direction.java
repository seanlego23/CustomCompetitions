package io.github.seanlego23.customcompetitions.util;

import io.github.seanlego23.customcompetitions.util.math.Vector3D;

public enum Direction {

    SOUTH(0, 0, 1),
    NORTH(0, 0, -1),
    EAST(1, 0, 0),
    WEST(-1, 0, 0);

    private final int x;
    private final int y;
    private final int z;

    Direction(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Vector3D toVector3D() {
        return new Vector3D(x, y, z);
    }

}
