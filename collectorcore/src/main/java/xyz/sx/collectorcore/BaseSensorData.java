package xyz.sx.collectorcore;

public abstract class BaseSensorData {
    public enum DataType {
        TYPE_WIFI,
        TYPE_ACC,
        TYPE_ORI
    }

    public abstract DataType getDataType();

    public abstract Object getData();

    public abstract boolean isEmpty();

}
