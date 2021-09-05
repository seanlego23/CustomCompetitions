package io.github.seanlego23.customcompetitions.util.math;

public class Quaternion {

    private final double w;
    private final double x;
    private final double y;
    private final double z;

    public Quaternion(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Quaternion(Vector3D eulerAngles) {
        eulerAngles = eulerAngles.multiply(0.5 * Math.PI / 180);

        double cosYaw = Math.cos(eulerAngles.x());
        double sinYaw = Math.sin(eulerAngles.x());
        double cosPitch = Math.cos(eulerAngles.y());
        double sinPitch = Math.sin(eulerAngles.y());
        double cosRoll = Math.cos(eulerAngles.z());
        double sinRoll = Math.sin(eulerAngles.z());

        //y and z are switched from the normal calculation because minecraft has y as the third axis
        w = cosYaw * cosPitch * cosRoll + sinYaw * sinPitch * sinRoll;
        x = cosYaw * cosPitch * sinRoll - sinYaw * sinPitch * cosRoll;
        y = sinYaw * cosPitch * cosRoll - cosYaw * sinPitch * sinRoll;
        z = cosYaw * sinPitch * cosRoll + sinYaw * cosPitch * sinRoll;
    }

    public Quaternion conjugate() {
        return new Quaternion(w, -x, -y, -z);
    }

    public Vector3D getVectorPart() {
        return new Vector3D(x, y, z);
    }

    public double getRealPart() {
        return w;
    }

    public Quaternion hamiltonProduct(Quaternion o) {
        double newW = w * o.w - x * o.x - z * o.z - y * o.y;
        double newX = w * o.x + x * o.w + z * o.y - y * o.z;
        double newY = w * o.y + x * o.z - z * o.x + y * o.w;
        double newZ = w * o.z - x * o.y + z * o.w + y * o.x;
        return new Quaternion(newW, newX, newY, newZ);
    }

}
