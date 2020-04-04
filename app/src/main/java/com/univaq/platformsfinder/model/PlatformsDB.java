package com.univaq.platformsfinder.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PlatformTable.class}, version = 1)
public abstract class PlatformsDB extends RoomDatabase
{
    public abstract PlatformsDao platformsDao();

    private static PlatformsDB instance = null;

    public static PlatformsDB getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, PlatformsDB.class, "platformsdatabase.db").build();

        return instance;
    }
}
