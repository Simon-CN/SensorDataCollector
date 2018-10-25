package xyz.sx.collectorcore.beans;

public class Vector3Bean {
    private double x;
    private double y;
    private double z;

    public Vector3Bean(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3Bean() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String dump() {
        return x + "," + y + "," + z;
    }
}
