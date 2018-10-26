package xyz.sx.collectorcore.providers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import xyz.sx.collectorcore.BaseProvider;
import android.hardware.SensorEventListener;
import xyz.sx.collectorcore.BaseSensorData;
import xyz.sx.collectorcore.datas.ArraySensorData;

public class OrientationProvider extends BaseProvider implements SensorEventListener {
    private ArraySensorData<Float> mData;

    private SensorManager mSensorManager;
    private Sensor mAccSensor;
    private Sensor mMagSensor;

    private float[] mAccValues = new float[3];
    private float[] mMagValues = new float[3];

    public OrientationProvider(Context context) {
        mSensorManager = (SensorManager) context.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        mData = new ArraySensorData<>(BaseSensorData.DataType.TYPE_ORI);
    }

    @Override
    public BaseSensorData getData() {
        return mData;
    }

    @Override
    protected void init() {
        mSensorManager.registerListener(this, mAccSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void destroy() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mAccValues = event.values;
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mMagValues = event.values;
        calculateOrientation();
    }

    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];

        SensorManager.getRotationMatrix(R, null, mAccValues, mMagValues);
        SensorManager.getOrientation(R, values);

        mData.add(values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
