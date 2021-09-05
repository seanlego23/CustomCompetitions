package io.github.seanlego23.customcompetitions.util.math;

public record Vector3D(double x, double y, double z) {

    public static Vector3D ZERO = new Vector3D(0, 0, 0);
    public static Vector3D ONE_X = new Vector3D(1, 0, 0);
    public static Vector3D ONE_Y = new Vector3D(0, 1, 0);
    public static Vector3D ONE_Z = new Vector3D(0, 0, 1);
    public static Vector3D ONE = new Vector3D(1, 1, 1);

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

    public Vector3D multiply(Vector3D o) {
        return new Vector3D(x * o.x, y * o.y, z * o.z);
    }

    public Vector3D divide(double s) {
        return new Vector3D(x / s, y / s, z / s);
    }

    public Vector3D divide(Vector3D o) {
        return new Vector3D(x / o.x, y / o.y, z / o.z);
    }

    public Vector3D transform(Vector3D angles, Vector3D pivot, Vector3D translate) {
        Quaternion r = new Quaternion(angles);
        Vector3D v2 = subtract(pivot);
        Quaternion v = new Quaternion(0, v2.x, v2.y, v2.z);
        Quaternion result = r.hamiltonProduct(v).hamiltonProduct(r.conjugate());
        return result.getVectorPart().add(pivot).add(translate);
    }

    public Vector2D toVector2D() {
        return new Vector2D(x, z);
    }

}
