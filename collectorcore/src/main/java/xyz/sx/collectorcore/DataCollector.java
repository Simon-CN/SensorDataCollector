package xyz.sx.collectorcore;

import xyz.sx.collectorcore.beans.GpsLocationBean;
import xyz.sx.collectorcore.beans.MacScanLine;
import xyz.sx.collectorcore.beans.SensorCollection;
import xyz.sx.collectorcore.beans.Vector3Bean;
import xyz.sx.collectorcore.datas.EmptySensorData;

import java.util.*;

class DataCollector {
    private BaseSensorData[] mData;

    DataCollector() {
        int len = BaseSensorData.DataType.values().length;
        mData = new BaseSensorData[len];
        BaseSensorData defaultData = new EmptySensorData();
        for (int i = 0; i < len; ++i)
            mData[i] = defaultData;
    }

    void addDataSource(BaseSensorData data) {
        if (data != null)
            mData[data.getDataType().ordinal()] = data;
    }

    SensorCollection collect() {
        SensorCollection res = new SensorCollection();
        res.wifi = (List<MacScanLine>) mData[BaseSensorData.DataType.TYPE_WIFI.ordinal()].getData();
        res.acc = (List<Vector3Bean>) mData[BaseSensorData.DataType.TYPE_ACC.ordinal()].getData();
        res.gps = (List<GpsLocationBean>) mData[BaseSensorData.DataType.TYPE_GPS.ordinal()].getData();
        res.ori = (List<Float>) mData[BaseSensorData.DataType.TYPE_ORI.ordinal()].getData();
        res.gyr = (List<Vector3Bean>) mData[BaseSensorData.DataType.TYPE_GYRO.ordinal()].getData();
        res.step = (List<Long>) mData[BaseSensorData.DataType.TYPE_STEP.ordinal()].getData();
        return res;
    }

}
