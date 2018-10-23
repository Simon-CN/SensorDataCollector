package xyz.sx.collectorcore.collections;

import xyz.sx.collectorcore.beans.GpsLocationBean;

public class GpsLocationCollection extends ArraySensorData<GpsLocationBean> {
    @Override
    public DataType getDataType() {
        return DataType.TYPE_GPS;
    }
}
