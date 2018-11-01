package xyz.sx.collectorcore;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import xyz.sx.collectorcore.protobuf.Samplesseq;
import xyz.sx.collectorcore.protobuf.Sensorcollection;
import xyz.sx.collectorcore.providers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.*;

public class CollectorContext {
    private DataCollector mCollector;
    private WeakReference<Context> mContext;
    private WeakReference<OnCollectDataListener> mListener;
    private Timer mTimer;
    private Set<BaseProvider> mProviders;
    private boolean needFileWrite = false;
    private List<Sensorcollection.SensorCollection> mCollections;
    private int mSeq = 0;
    private long mBeginTime;
    private boolean isRunning = false;

    private static CollectorContext mInstance = null;

    public static CollectorContext getInstance() {
        if (mInstance == null)
            mInstance = new CollectorContext();
        return mInstance;
    }

    private CollectorContext() {
        this.mCollector = new DataCollector();
        mProviders = new HashSet<>();
    }

    public void init(Context context, boolean fileWrite) throws IllegalStateException {
        this.mContext = new WeakReference<>(context.getApplicationContext());
        this.needFileWrite = fileWrite;
        initProviders();
    }

    public void init(Context context) {
        init(context, false);
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
        mProviders.add(new MagneticProvider(tmp));
        for (BaseProvider bp : mProviders)
            mCollector.addDataSource(bp.getData());
    }

    public void setOnCollectDataListener(OnCollectDataListener mListener) {
        this.mListener = new WeakReference<>(mListener);
    }

    public void start() {
        if (isRunning)
            return;
        isRunning = true;
        mBeginTime = System.currentTimeMillis();
        mSeq = 0;
        if (needFileWrite) {
            mCollections = new ArrayList<>();
            File dir = new File(Environment.getExternalStorageDirectory() + "/samples/" + mBeginTime);
            if (!dir.exists())
                dir.mkdirs();
        }
        for (BaseProvider bp : mProviders)
            bp.start();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Sensorcollection.SensorCollection res = mCollector.collect();
                Log.d("Collect Status", "Collect once: " + System.currentTimeMillis());
                if (mListener != null) {
                    OnCollectDataListener lis = mListener.get();
                    if (lis != null)
                        lis.OnCollectData(res);
                    else
                        mListener = null;
                }
                if (needFileWrite) {
                    mCollections.add(res);
                    if (mCollections.size() > 100)
                        writeToFile();
                }
            }
        }, 0, 1000);
    }

    private void writeToFile() {
        final List<Sensorcollection.SensorCollection> tmp = mCollections;
        List<Sensorcollection.SensorCollection> newBuf = new ArrayList<>();
        synchronized (this) {
            mCollections = newBuf;
        }
        if (tmp.size() > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Samplesseq.Samples sp = Samplesseq.Samples.newBuilder().setDesc(mBeginTime + "/" + mSeq++).addAllData(tmp).build();
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/samples/" + sp.getDesc() + ".pbc");
                        sp.writeTo(out);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (out != null) {
                            try {
                                out.flush();
                                out.close();
                                out = null;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }

    }

    public void stop() {
        if (!isRunning)
            return;
        isRunning = false;
        for (BaseProvider bp : mProviders)
            bp.stop();
        if (mTimer != null) {
            mTimer.purge();
            mTimer.cancel();
            mTimer = null;
        }
        if (mCollections != null && mCollections.size() > 0)
            writeToFile();
        mCollector.reset();
    }

    public void destroy() {
        stop();
        mInstance = null;
    }
}
