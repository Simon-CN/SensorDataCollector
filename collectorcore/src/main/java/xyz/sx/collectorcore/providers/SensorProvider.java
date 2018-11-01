package xyz.sx.collectorcore.providers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import xyz.sx.collectorcore.BaseProvider;
import android.hardware.SensorEventListener;
import xyz.sx.collectorcore.BaseSensorData;
import xyz.sx.collectorcore.datas.ArraySensorData;

public abstract class SensorProvider extends BaseProvider implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    protected BaseSensorData mData;

    SensorProvider(Context context, int type) {
        mSensorManager = (SensorManager) context.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(type);
       initData();
    }

    protected abstract void initData();

    @Override
    public BaseSensorData getData() {
        return mData;
    }

    @Override
    protected void init() {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void destroy() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
