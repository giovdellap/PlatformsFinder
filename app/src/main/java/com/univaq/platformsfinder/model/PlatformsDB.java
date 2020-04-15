package com.univaq.platformsfinder.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Manages the DB
 */
@Database(entities = {PlatformTable.class}, version = 1)
public abstract class PlatformsDB extends RoomDatabase
{
    /**
     * Platforms dao.
     *
     * @return the platforms dao
     */
    public abstract PlatformsDao platformsDao();

    private static PlatformsDB instance = null;

    /**
     * Returns DB instance.
     * If the DB dowsn't exist, creates it.
     *
     * @param context
     * @return the DB instance
     */
    public static PlatformsDB getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, PlatformsDB.class, "platformsdatabase.db").build();

        return instance;
    }
}
