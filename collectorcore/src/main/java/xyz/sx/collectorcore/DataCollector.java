package xyz.sx.collectorcore;

import xyz.sx.collectorcore.datas.EmptySensorData;
import xyz.sx.collectorcore.protobuf.Gpsinfo;
import xyz.sx.collectorcore.protobuf.Macinfo;
import xyz.sx.collectorcore.protobuf.Sensorcollection;
import xyz.sx.collectorcore.protobuf.Vector3OuterClass;

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

    Sensorcollection.SensorCollection collect() {
        return Sensorcollection.SensorCollection.newBuilder()
                .addAllAcc((List<Vector3OuterClass.Vector3>) mData[BaseSensorData.DataType.TYPE_ACC.ordinal()].getData())
                .addAllWifi((List<Macinfo.MacScanLine>) mData[BaseSensorData.DataType.TYPE_WIFI.ordinal()].getData())
                .addAllGps((List<Gpsinfo.GpsData>) mData[BaseSensorData.DataType.TYPE_GPS.ordinal()].getData())
                .addAllGyr((List<Vector3OuterClass.Vector3>) mData[BaseSensorData.DataType.TYPE_GYRO.ordinal()].getData())
                .addAllOri((List<Float>) mData[BaseSensorData.DataType.TYPE_ORI.ordinal()].getData())
                .addAllStep((List<Long>) mData[BaseSensorData.DataType.TYPE_STEP.ordinal()].getData())
                .build();
    }

}
