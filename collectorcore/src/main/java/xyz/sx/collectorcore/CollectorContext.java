package xyz.sx.collectorcore;

import android.content.Context;
import xyz.sx.collectorcore.beans.SensorCollection;
import xyz.sx.collectorcore.providers.*;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class CollectorContext {
    private DataCollector mCollector;
    private WeakReference<Context> mContext;
    private WeakReference<OnCollectDataListener> mListener;
    private Timer mTimer;
    private Set<BaseProvider> mProviders;

    private static CollectorContext mInstance = null;

    public static CollectorContext getInstance() {
        if (mInstance == null)
            mInstance = new CollectorContext();
        return mInstance;
    }

    private CollectorContext() {
        this.mCollector = new DataCollector();
        mProviders=new HashSet<>();
    }

    public void init(Context context) throws IllegalStateException {
        this.mContext = new WeakReference<>(context.getApplicationContext());
        initProviders();
    }

    private void initProviders() throws IllegalStateException {
        Context tmp = mContext.get();
        if (tmp == null) {
            throw new IllegalStateException("Invalid Context");
        }

        mProviders.add(new WiFiScanProvider(tmp));
        mProviders.add(new AccelerationProvider(tmp));
        mProviders.add(new GpsLocationProvider(tmp));
        mProviders.add(new GyroscopeProvider(tmp));
        mProviders.add(new StepProvider(tmp));
        mProviders.add(new OrientationProvider(tmp));

        for (BaseProvider bp : mProviders)
            mCollector.addDataSource(bp.getData());
    }

    public void setOnCollectDataListener(OnCollectDataListener mListener) {
        this.mListener = new WeakReference<>(mListener);
    }

    public void start() {
        stop();
        for (BaseProvider bp : mProviders)
            bp.start();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SensorCollection res = mCollector.collect();
                if (mListener != null) {
                    OnCollectDataListener lis = mListener.get();
                    if (lis != null)
                        lis.OnCollectData(res);
                    else
                        mListener = null;
                }

            }
        }, 0, 1000);
    }

    public void stop() {
        for (BaseProvider bp : mProviders)
            bp.stop();
        if (mTimer != null) {
            mTimer.purge();
            mTimer.cancel();
            mTimer = null;
        }
    }

    public void destroy() {
        stop();
        mInstance = null;
    }
}
