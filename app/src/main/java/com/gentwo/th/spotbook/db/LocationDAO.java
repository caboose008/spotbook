package com.gentwo.th.spotbook.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface LocationDAO{
    @Query("SELECT * FROM LocationEntity")
    List<LocationEntity> getAll();

    @Query("SELECT * FROM LocationEntity WHERE date LIKE :date")
    List<LocationEntity> getByDate(double date);

    @Insert
    void insert(LocationEntity location);

    @Delete
    void delete(LocationEntity location);
}

