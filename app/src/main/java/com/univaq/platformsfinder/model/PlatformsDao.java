package com.univaq.platformsfinder.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PlatformsDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertPlatforms(List<PlatformTable> platformsList);

    @Query("SELECT * FROM platformtable")
    public ArrayList<PlatformTable> getPLatforms();

    @Query("SELECT * FROM platformtable WHERE latitudine LIKE :mylat AND longitudine LIKE :mylon")
    public PlatformTable getPlatform(double mylat, double mylon);

    @Query("SELECT * FROM platformtable WHERE id LIKE :id")
    public PlatformTable getPlatformByID(int id);
}
