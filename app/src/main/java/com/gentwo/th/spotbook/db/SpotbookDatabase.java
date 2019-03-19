package com.gentwo.th.spotbook.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {LocationEntity.class}, version = 2)
public abstract class SpotbookDatabase extends RoomDatabase {
    public abstract LocationDAO locationDao();
}

