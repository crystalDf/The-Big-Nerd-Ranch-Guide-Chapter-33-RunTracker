package com.star.runtracker;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

public class RunManager {

    private static final String TAG = "RunManager";

    public static final String ACTION_LOCATION =
            "com.star.runtracker.ACTION_LOCATION";

    private static RunManager sRunManager;

    private Context mAppContext;

    private LocationManager mLocationManager;

    private RunManager(Context appContext) {
        mAppContext = appContext;
        mLocationManager = (LocationManager) mAppContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public static RunManager getInstance(Context context) {
        if (sRunManager == null) {
            synchronized (RunManager.class) {
                if (sRunManager == null) {
                    sRunManager = new RunManager(context);
                }
            }
        }
        return sRunManager;
    }

    private PendingIntent getLocationPendingIntent(boolean shouldCreate) {
        Intent broadcast = new Intent(ACTION_LOCATION);
        int flags = shouldCreate ? 0 : PendingIntent.FLAG_NO_CREATE;

        return PendingIntent.getBroadcast(mAppContext, 0, broadcast, flags);
    }

    public void startLocationUpdates() {
        String provider = LocationManager.GPS_PROVIDER;

        PendingIntent pi = getLocationPendingIntent(true);

        mLocationManager.requestLocationUpdates(provider, 0, 0, pi);

        Location lastKnow = mLocationManager.getLastKnownLocation(provider);

        if (lastKnow != null) {
            lastKnow.setTime(System.currentTimeMillis());
            broadcastLocation(lastKnow);
        }
    }

    public void stopLocationUpdates() {
        PendingIntent pi = getLocationPendingIntent(false);

        if (pi != null) {
            mLocationManager.removeUpdates(pi);
            pi.cancel();
        }
    }

    public boolean isTrackingRun() {
        return getLocationPendingIntent(false) != null;
    }

    private void broadcastLocation(Location location) {
        Intent broadcast = new Intent(ACTION_LOCATION);
        broadcast.putExtra(LocationManager.KEY_LOCATION_CHANGED, location);
        mAppContext.sendBroadcast(broadcast);
    }
}