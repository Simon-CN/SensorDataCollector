package xyz.sx.collectorcore.datas;

import xyz.sx.collectorcore.BaseSensorData;

public class EmptySensorData extends BaseSensorData {
    public EmptySensorData() {
        super(DataType.TYPE_EMPTY);
    }

    @Override
    public Object getData() {
        return null;
    }
}
