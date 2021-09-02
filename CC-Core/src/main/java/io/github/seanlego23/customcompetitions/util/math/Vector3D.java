package io.github.seanlego23.customcompetitions.util.math;

public record Vector3D(double x, double y, double z) {

    public int getBlockX() {
        return (int) x;
    }

    public int getBlockY() {
        return (int) y;
    }

    public int getBlockZ() {
        return (int) z;
    }

    public Vector3D withX(double X) {
        return new Vector3D(X, y, z);
    }

    public Vector3D withY(double Y) {
        return new Vector3D(x, Y, z);
    }

    public Vector3D withZ(double Z) {
        return new Vector3D(x, y, Z);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    public double dot(Vector3D o) {
        return x * o.x + y * o.y + z * o.z;
    }

    public Vector3D cross(Vector3D o) {
        return new Vector3D(y * o.z - o.y * z, z * o.x - o.z * x, x * o.y - o.x * y);
    }

    public Vector3D unit() {
        double len = length();
        return new Vector3D(x / len, y / len, z / len);
    }

    public Vector3D add(Vector3D o) {
        return new Vector3D(x + o.x, y + o.y, z + o.z);
    }

    public Vector3D subtract(Vector3D o) {
        return new Vector3D(x - o.x, y - o.y, z - o.z);
    }

    public Vector3D multiply(double s) {
        return new Vector3D(s * x, s * y, s * z);
    }

    public Vector3D divide(double s) {
        return new Vector3D(x / s, y / s, z / s);
    }

    public Vector2D toVector2D() {
        return new Vector2D(x, z);
    }

}
