package xyz.sx.collectorcore.datas;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DoubleBufferArray<T> {
    private Object[] buffer1, buffer2;
    private static final int OFFSET = 5;
    private static final int DEFAULT_SIZE = 50;
    private static final int DEFAULT_PERIOD = 1000;

    private final int bufferSize;
    private volatile boolean flag, hasOverflow;
    private int length1, length2;
    private long preTime;
    private long timeInterval = 0;

    public DoubleBufferArray() {
        this(DEFAULT_SIZE, DEFAULT_PERIOD);
    }

    public DoubleBufferArray(int interval) {
        this.bufferSize = 1000 / interval + 1;
        this.timeInterval = interval;
        initValue();
    }

    /**
     * @param size   数据最大容量
     * @param period 数据保存时长 (ms)
     */
    public DoubleBufferArray(int size, int period) {
        this.bufferSize = size / 2;
        timeInterval = period / bufferSize / 2;
        initValue();
    }

    private void initValue() {
        buffer1 = new Object[bufferSize + OFFSET];
        buffer2 = new Object[bufferSize + OFFSET];
        flag = true;
        hasOverflow = false;
        length1 = 0;
        length2 = 0;
        preTime = 0;
    }

    public void add(T t) {
        long curTime = System.currentTimeMillis();
        if (curTime - preTime < timeInterval)
            return;
        preTime = curTime;
        if (flag) {
            if (length1 >= bufferSize) {
                length2 = 0;
                flag = false;
                hasOverflow = true;
            }
            buffer1[length1] = t;

            ++length1;
        } else {
            if (length2 >= bufferSize) {
                length1 = 0;
                flag = true;
                hasOverflow = true;
            }
            buffer2[length2] = t;
            ++length2;
        }

    }

    public List<T> getData() {
        List<T> res = new LinkedList<>();
        if (flag) {
            if (hasOverflow)
                copyCollection(res, 2);
            length2 = 0;
            flag = false;
            copyCollection(res, 1);
        } else {
            if (hasOverflow)
                copyCollection(res, 1);
            length1 = 0;
            flag = true;
            copyCollection(res, 2);
        }
        hasOverflow = false;
        return res;
    }

    private void copyCollection(List<T> res, int buf) {
        if (buf == 1)
            for (int i = 0; i < length1; ++i)
                res.add((T) buffer1[i]);
        else
            for (int j = 0; j < length2; ++j)
                res.add((T) buffer2[j]);
    }

    public void reset(){
        flag = true;
        hasOverflow = false;
        length1 = 0;
        length2 = 0;
        preTime = 0;
    }

}
