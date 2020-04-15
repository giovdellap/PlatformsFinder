package com.univaq.platformsfinder.tools;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.Marker;
import com.univaq.platformsfinder.model.PlatformTable;
import com.univaq.platformsfinder.model.PlatformsDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles accesses to DB
 */
public class DBHandler
{
    private static final String TAG = "DBHandler";

    /**
     * Returns an arraylist containing all platforms.
     *
     * @param context the context
     * @return the arraylist
     */
    public ArrayList<PlatformTable> getAllPlatforms(final Context context)
    {
        final ArrayList<PlatformTable> toReturn = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                List<PlatformTable> list = PlatformsDB.getInstance(context).platformsDao().getPlatforms();
                toReturn.addAll(list);
            }
        });
        t.start();
        try{
            t.join();
        } catch (Exception e)
        {
            Log.d(TAG, e.toString());
        }
        return toReturn;
    }

    /**
     * Injects all tables in the arraylist passed as param in DB
     *
     * @param tables  the arraylist
     * @param context the context
     */
    public void insertTables(final ArrayList<PlatformTable> tables, final Context context)
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                PlatformsDB.getInstance(context).platformsDao().insertPlatforms(tables);
            }
        });
        t.start();
        try{
            t.join();
        } catch(Exception e){
            Log.d(TAG, e.toString());
        }
    }

    /**
     * Returns the platform having the specified id
     *
     * @param id
     * @param context the context
     * @return the specific platform
     */
    public PlatformTable getPlatformByID(final int id, final Context context)
    {
        final ArrayList<PlatformTable> array = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                array.add(PlatformsDB.getInstance(context).platformsDao().getPlatformByID(id));
            }
        });
        t.start();
        try{
            t.join();
        } catch(Exception e)
        {
            Log.d(TAG, e.toString());
        }
        return array.get(0);
    }

    /**
     * Returns platform with same coordinates of the given marker
     *
     * @param marker  the marker
     * @param context the context
     * @return the specific platform
     */
    public PlatformTable getPlatformByMarker(final Marker marker, final Context context)
    {
        final Double latitude = marker.getPosition().latitude;
        final Double longitude = marker.getPosition().longitude;
        final ArrayList<PlatformTable> tables = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                tables.add(PlatformsDB.getInstance(context).platformsDao().getPlatform(latitude, longitude));
            }
        });
        t.start();
        try{
            t.join();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return tables.get(0);
    }

}
