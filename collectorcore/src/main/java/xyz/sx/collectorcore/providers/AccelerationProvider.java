package xyz.sx.collectorcore.providers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import xyz.sx.collectorcore.BaseSensorData;
import xyz.sx.collectorcore.beans.Vector3Bean;
import xyz.sx.collectorcore.datas.ArraySensorData;

public class AccelerationProvider extends SensorProvider {

    public AccelerationProvider(Context context) {
        super(context, Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void initData() {
        mData = new ArraySensorData<Vector3Bean>(BaseSensorData.DataType.TYPE_ACC);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ((ArraySensorData)mData).add(new Vector3Bean(event.values[0], event.values[1], event.values[1]));
    }

}
