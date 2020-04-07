package com.univaq.platformsfinder.tools;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.Marker;
import com.univaq.platformsfinder.model.PlatformTable;
import com.univaq.platformsfinder.model.PlatformsDB;

import java.util.ArrayList;
import java.util.List;

public class DBHandler
{
    private static final String TAG = "DBHandler";

    public ArrayList<PlatformTable> getAllPlatforms(final Context context)
    {
        final ArrayList<PlatformTable> toReturn = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                List<PlatformTable> list = PlatformsDB.getInstance(context).platformsDao().getPLatforms();
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
