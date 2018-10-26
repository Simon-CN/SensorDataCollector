package xyz.sx.collectorcore.providers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import xyz.sx.collectorcore.BaseProvider;
import xyz.sx.collectorcore.BaseSensorData;
import xyz.sx.collectorcore.GlobalMsgHandler;
import xyz.sx.collectorcore.datas.ArraySensorData;
import xyz.sx.collectorcore.protobuf.Gpsinfo;

public class GpsLocationProvider extends BaseProvider implements LocationListener {
    private LocationManager mLocationManager;
    private Context mContext;
    private ArraySensorData<Gpsinfo.GpsData> mData;

    public GpsLocationProvider(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        mData = new ArraySensorData<>(BaseSensorData.DataType.TYPE_GPS,333);
    }

    @Override
    public BaseSensorData getData() {
        return mData;
    }

    @Override
    protected void init() {
        if (ActivityCompat.checkSelfPermission(mContext.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            GlobalMsgHandler.sendErrorMsg("Permission Check Failed: " + getClass().getName());
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    protected void destroy() {
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mData.add(Gpsinfo.GpsData.newBuilder().setAccuracy(location.getAccuracy()).setAltitude(location.getAltitude()).setLatitude(location.getLatitude()).setLongitude(location.getLongitude()).setTimestamp(location.getTime()).build());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
