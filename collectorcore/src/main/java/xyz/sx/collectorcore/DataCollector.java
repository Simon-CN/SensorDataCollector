package xyz.sx.collectorcore;

import android.content.Context;

import java.util.*;

public class DataCollector {
    private List<BaseSensorData> mData;
    private Set<BaseProvider> mProviders;

    public DataCollector() {
        mData = new ArrayList<>();
        mProviders = new HashSet<>();
    }

    public void addProvider(BaseProvider provider) {
        if (provider == null || mProviders.contains(provider))
            return;
        mProviders.add(provider);
        mData.add(provider.getData());
    }

    public Object collect() {
      return null;
    }

}
