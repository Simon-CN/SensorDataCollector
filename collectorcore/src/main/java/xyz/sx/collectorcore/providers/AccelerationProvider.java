package xyz.sx.collectorcore.providers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import xyz.sx.collectorcore.BaseSensorData;
import xyz.sx.collectorcore.datas.ArraySensorData;
import xyz.sx.collectorcore.protobuf.Vector3OuterClass;

public class AccelerationProvider extends SensorProvider {

    public AccelerationProvider(Context context) {
        super(context, Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void initData() {
        mData = new ArraySensorData<Vector3OuterClass.Vector3>(BaseSensorData.DataType.TYPE_ACC,10);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ((ArraySensorData)mData).add(Vector3OuterClass.Vector3.newBuilder().setX(event.values[0]).setY(event.values[1]).setZ(event.values[2]).build());
    }

}
