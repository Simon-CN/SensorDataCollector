package xyz.sx.collectorcore;


import xyz.sx.collectorcore.protobuf.Sensorcollection;

public interface OnCollectDataListener {
    void OnCollectData(Sensorcollection.SensorCollection collection);
}
