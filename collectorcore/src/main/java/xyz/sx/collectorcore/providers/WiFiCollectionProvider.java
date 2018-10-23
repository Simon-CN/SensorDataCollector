package xyz.sx.collectorcore.providers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import xyz.sx.collectorcore.BaseProvider;
import xyz.sx.collectorcore.BaseSensorData;
import xyz.sx.collectorcore.beans.MacBean;
import xyz.sx.collectorcore.beans.MacScanLine;
import xyz.sx.collectorcore.collections.WiFiCollection;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class WiFiCollectionProvider extends BaseProvider {
    private WiFiCollection mData;
    private Context mContext;
    private WifiManager mWiFiManager;
    private BroadcastReceiver mWiFiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean res = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
            if (res)
                doCollect();
            else
                Log.d(TAG, "Scan Failed..." + System.currentTimeMillis());
            if (isRunning())
                mWiFiManager.startScan();
        }
    };

    public WiFiCollectionProvider(Context context) {
        mData = new WiFiCollection();
        mContext = context;
    }

    @Override
    public BaseSensorData getData() {
        return mData;
    }

    @Override
    protected void destroy() {
        mContext.unregisterReceiver(mWiFiReceiver);
    }

    @Override
    protected void init() {
        mWiFiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mContext.registerReceiver(mWiFiReceiver, intentFilter);
        mWiFiManager.startScan();
    }


    private void doCollect() {
        List<ScanResult> results = mWiFiManager.getScanResults();
        List<MacBean> line = new ArrayList<>();
        for (ScanResult sr : results)
            line.add(new MacBean(sr.BSSID, sr.level));
        mData.add(new MacScanLine(System.currentTimeMillis(), line));
    }
}
