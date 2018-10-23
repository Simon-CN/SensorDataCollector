package xyz.sx.collectorcore.beans;

public class GpsLocationBean {
    private double latitude;
    private double longitude;
    private double altitude;
    private double accuracy;
    private long timestamp;

    public GpsLocationBean(double latitude, double longitude, double altitude, double accuracy, long timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.accuracy = accuracy;
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String dupe() {
        return timestamp + "####" + longitude + ", " + latitude + ", " + altitude + ", " + accuracy;
    }
}
