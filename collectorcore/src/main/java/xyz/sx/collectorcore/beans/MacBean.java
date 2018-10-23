package xyz.sx.collectorcore.beans;

public class MacBean {
    private String mac;
    private int rssi;

    public MacBean() {
    }

    public MacBean(String mac, int rssi) {
        this.mac = mac;
        this.rssi = rssi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String dump() {
        return mac + ", " + rssi;
    }
}
