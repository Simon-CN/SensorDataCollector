package xyz.sx.collectorcore.collections;

import xyz.sx.collectorcore.beans.MacScanLine;

public class WiFiCollection extends ArraySensorData<MacScanLine> {

    public WiFiCollection() {
        super();
    }

    @Override
    public DataType getDataType() {
        return DataType.TYPE_WIFI;
    }
}
