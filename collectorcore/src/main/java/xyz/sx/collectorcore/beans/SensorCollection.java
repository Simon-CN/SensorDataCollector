package xyz.sx.collectorcore.beans;

import java.util.List;

public class SensorCollection {
    public List<MacScanLine> wifi;
    public List<GpsLocationBean> gps;
    public List<Vector3Bean> acc;
    public List<Vector3Bean> gyr;
    public List<Float> ori;
    public List<Long> step;

    public String dump() {
        StringBuilder sb = new StringBuilder();
        if (wifi != null)
            sb.append("wifi: ").append(wifi.size()).append("\n");
        if (gps != null)
            sb.append("gps: ").append(gps.size()).append("\n");
        if (acc != null)
            sb.append("acc: ").append(acc.size()).append("\n");
        if (gyr != null)
            sb.append("gyr: ").append(gyr.size()).append("\n");
        if (ori != null)
            sb.append("ori: ").append(ori.size()).append("\n");
        if (step != null)
            sb.append("step: ").append(step.size()).append("\n");
        return sb.toString();
    }
}
