package com.gentwo.th.spotbook.db;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

public class SpotbookDbHelper {
    private Context ctx;

    public SpotbookDbHelper(Context context) {
        ctx = context;
    }

    public void insertLocation(double lat, double lon, double date) {
        LocationEntity locationEntity = new LocationEntity(lat, lon, date);
        getDatabase().locationDao().insert(locationEntity);
    }
    public ArrayList<LocationEntity> getAllLocations(){
        ArrayList<LocationEntity> allLocations = (ArrayList<LocationEntity>) getDatabase().locationDao().getAll();


        return allLocations;
    }
    private SpotbookDatabase getDatabase() {
        SpotbookDatabase db = Room.databaseBuilder(ctx,
                SpotbookDatabase.class, "spotbook-db").build();
        return db;
    }
}
