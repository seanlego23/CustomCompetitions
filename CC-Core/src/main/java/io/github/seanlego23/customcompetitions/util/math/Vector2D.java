package io.github.seanlego23.customcompetitions.util.math;

public record Vector2D(double x, double z) {

    public static Vector2D ZERO = new Vector2D(0, 0);
    public static Vector2D ONE_X = new Vector2D(1, 0);
    public static Vector2D ONE_Z = new Vector2D(0, 1);
    public static Vector2D ONE = new Vector2D(1, 1);

    public int getBlockX() {
        return (int) x;
    }

    public int getBlockZ() {
        return (int) z;
    }

    public Vector2D withX(double X) {
        return new Vector2D(X, z);
    }

    public Vector2D withZ(double Z) {
        return new Vector2D(x, Z);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return x * x + z * z;
    }

    public double dot(Vector2D o) {
        return x * o.x + z * o.z;
    }

    public Vector3D cross(Vector2D o) {
        return new Vector3D(0, x * o.z - o.x * z, 0);
    }

    public Vector2D unit() {
        double len = length();
        return new Vector2D(x / len, z / len);
    }

    public Vector2D add(Vector2D o) {
        return new Vector2D(x + o.x, z + o.z);
    }

    public Vector2D subtract(Vector2D o) {
        return new Vector2D(x - o.x, z - o.z);
    }

    public Vector2D multiply(double s) {
        return new Vector2D(s * x, s * z);
    }

    public Vector2D divide(double s) {
        return new Vector2D(x / s, z / s);
    }

    public Vector3D toVector3D() {
        return new Vector3D(x, 0, z);
    }

    public Vector3D toVector3D(double y) {
        return new Vector3D(x, y, z);
    }

}
