package xyz.sx.collectorcore.datas;

import xyz.sx.collectorcore.BaseSensorData;

import java.util.List;

public class ArraySensorData<T> extends BaseSensorData {
    private DoubleBufferArray<T> mData;

    public ArraySensorData(DataType type) {
        super(type);
        mData = new DoubleBufferArray<>();
    }

    public ArraySensorData(DataType type, int interval) {
        super(type);
        mData = new DoubleBufferArray<>(interval);
    }

    public void add(T t) {
        mData.add(t);
    }

    @Override
    public List<T> getData() {
        return mData.getData();
    }

}
