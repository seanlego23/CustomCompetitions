package io.github.seanlego23.customcompetitions.util;

import io.github.seanlego23.customcompetitions.regions.World;
import io.github.seanlego23.customcompetitions.util.math.Vector3D;

public record Location(World world, double x, double y, double z, float yaw, float pitch) {

    public Location(World world, double x, double y, double z) {
        this(world, x, y, z, 0, 0);
    }

    public Location(World world, int x, int y, int z) {
        this(world, x, y, z, 0, 0);
    }

    public int getBlockX() {
        return (int) x;
    }

    public int getBlockY() {
        return (int) y;
    }

    public int getBlockZ() {
        return (int) z;
    }

    public Location withX(double X) {
        return new Location(world, X, y, z, yaw, pitch);
    }

    public Location withX(int X) {
        return withX((double)X);
    }

    public Location withY(double Y) {
        return new Location(world, x, Y, z, yaw, pitch);
    }

    public Location withY(int Y) {
        return withY((double) Y);
    }

    public Location withZ(double Z) {
        return new Location(world, x, y, Z, yaw, pitch);
    }

    public Location withZ(int Z) {
        return withZ((double) Z);
    }

    public Location withYaw(float Yaw) {
        return new Location(world, x, y, z, Yaw, pitch);
    }

    public Location withPitch(float Pitch) {
        return new Location(world, x, y, z, yaw, Pitch);
    }

    public Location position(double X, double Y, double Z) {
        return new Location(world, X, Y, Z, yaw, pitch);
    }

    public Location rotation(float Yaw, float Pitch) {
        return new Location(world, x, y, z, Yaw, Pitch);
    }

    public Vector3D toVector3D() {
        return new Vector3D(x, y, z);
    }

}
