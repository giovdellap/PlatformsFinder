package com.univaq.platformsfinder.tools;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.univaq.platformsfinder.model.PlatformTable;
import com.univaq.platformsfinder.model.PlatformsDB;

public class BundleFactory
{
    private static final String TAG = "BUNDLE";
    public Bundle mainBundle(int distance)
    {
        Bundle bundle = new Bundle();
        bundle.putString("MODE", "LOCATION");
        bundle.putString("DISTANCE", Integer.toString(distance));
        Log.d(TAG, "distance = " + Integer.toString(distance));
        return bundle;
    }

    public Bundle addressBundle(LatLng location, int distance)
    {
        Bundle bundle = new Bundle();
        bundle.putString("MODE", "ADDRESS");
        bundle.putString("LAT", Double.toString(location.latitude));
        bundle.putString("LON", Double.toString(location.longitude));
        bundle.putString("DISTANCE", Integer.toString(distance));
        Log.d(TAG, "lat = " + location.latitude);
        Log.d(TAG, "lon = " + location.longitude);
        Log.d(TAG, "distance = " + distance);
        return bundle;
    }

    public Bundle detailsBundle(final Marker marker, final Context context)
    {
        final Bundle bundle = new Bundle();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                double lat = marker.getPosition().latitude;
                double lon = marker.getPosition().longitude;
                PlatformTable table = PlatformsDB.getInstance(context).platformsDao().getPlatform(lat, lon);
                bundle.putString("ID", Integer.toString(table.id));
            }
        });
        t.run();
        return bundle;
    }

    public Bundle listDetailsBundle(PlatformTable table)
    {
        Bundle bundle = new Bundle();
        bundle.putString("ID", Integer.toString(table.id));
        return bundle;
    }

    public Bundle listBundle(Location location, int distance)
    {
        Bundle bundle = new Bundle();
        bundle.putString("LAT", Double.toString(location.getLatitude()));
        bundle.putString("LON", Double.toString(location.getLongitude()));
        bundle.putString("DISTANCE", Integer.toString(distance));
        return bundle;
    }
}
