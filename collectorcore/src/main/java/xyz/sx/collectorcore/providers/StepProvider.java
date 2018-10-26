package xyz.sx.collectorcore.providers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import xyz.sx.collectorcore.BaseSensorData;
import xyz.sx.collectorcore.datas.ArraySensorData;

public class StepProvider extends SensorProvider {

    public StepProvider(Context context) {
        super(context, Sensor.TYPE_STEP_DETECTOR);
    }

    @Override
    protected void initData() {
        mData = new ArraySensorData<>(BaseSensorData.DataType.TYPE_STEP);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ((ArraySensorData)mData).add(event.timestamp);
    }
}
