package xyz.sx.collectorcore.collections;

import xyz.sx.collectorcore.BaseSensorData;

import java.util.ArrayList;
import java.util.List;

public abstract class ArraySensorData<T> extends BaseSensorData {
    private List<T> mData;

    public ArraySensorData() {
        mData = new ArrayList<>();
    }

    public void add(T t) {
        mData.add(t);
    }

    @Override
    public List<T> getData() {
        List<T> tmp = mData;
        List<T> newBuf = new ArrayList<>();
        synchronized (this) {
            mData = newBuf;
        }
        return tmp;
    }

    @Override
    public boolean isEmpty() {
        return mData.isEmpty();
    }
}
