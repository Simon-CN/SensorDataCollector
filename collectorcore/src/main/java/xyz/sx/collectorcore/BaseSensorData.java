package xyz.sx.collectorcore;

public abstract class BaseSensorData {
    public enum DataType {
        TYPE_WIFI,
        TYPE_ACC,
        TYPE_ORI,
        TYPE_GPS,
        TYPE_GYRO
    }

    private DataType mSensorType;

    public BaseSensorData(DataType type) {
        this.mSensorType = type;
    }

    public DataType getDataType() {
        return mSensorType;
    }

    public abstract Object getData();

}
