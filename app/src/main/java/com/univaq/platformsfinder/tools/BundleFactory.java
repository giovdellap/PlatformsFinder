package com.univaq.platformsfinder.tools;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.univaq.platformsfinder.model.PlatformTable;

/**
 * Creates Bundles for all the classes
 */
public class BundleFactory
{
    private static final String TAG = "BUNDLE";

    /**
     * Returns bundle passed from MainActivity to MapActivity
     * MapActivity starts in Location Mode
     *
     * @param distance
     * @return the bundle
     */
    public Bundle mainBundle(int distance)
    {
        Bundle bundle = new Bundle();
        bundle.putString("MODE", "LOCATION");
        bundle.putString("DISTANCE", Integer.toString(distance));
        Log.d(TAG, "distance = " + distance);
        return bundle;
    }

    /**
     * Returns bundle passed from MainActivity to MapActivity
     * MapActivity starts in Address Mode
     * Contains distance and location infos
     *
     * @param location location
     * @param distance distance
     * @return the bundle
     */
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

    /**
     * Returns bundle passed from MapActivity to DetailsActivity
     * Gets marker as param, extracts marker's platform and incapsulates platform id in bundle
     *
     * @param marker  the marker
     * @param context context
     * @return the bundle
     */
    public Bundle detailsBundle(final Marker marker, final Context context)
    {
        final Bundle bundle = new Bundle();
        DBHandler handler = new DBHandler();
        PlatformTable table = handler.getPlatformByMarker(marker, context);
        bundle.putString("ID", Integer.toString(table.id));
        return bundle;
    }

    /**
     * Returns bundle passed from ListAdapter to DetailsActivity
     * Contains platform id
     *
     * @param table platform table
     * @return the bundle
     */
    public Bundle listDetailsBundle(PlatformTable table)
    {
        Bundle bundle = new Bundle();
        bundle.putString("ID", Integer.toString(table.id));
        return bundle;
    }

    /**
     * Returns bundle passed from MapActivity to ListActivity
     * Contains distance and current location coordinates
     *
     * @param location location
     * @param distance distance
     * @return the bundle
     */
    public Bundle listBundle(Location location, int distance)
    {
        Bundle bundle = new Bundle();
        bundle.putString("LAT", Double.toString(location.getLatitude()));
        bundle.putString("LON", Double.toString(location.getLongitude()));
        bundle.putString("DISTANCE", Integer.toString(distance));
        return bundle;
    }
}
