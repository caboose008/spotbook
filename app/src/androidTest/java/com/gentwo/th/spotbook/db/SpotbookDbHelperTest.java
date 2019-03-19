package com.gentwo.th.spotbook.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class SpotbookDbHelperTest {

    @Test
    public void insertLocation() {
        Context context = InstrumentationRegistry.getTargetContext();
        SpotbookDbHelper spotbookDbHelper = new SpotbookDbHelper(context);
        double now = System.currentTimeMillis();
        LocationEntity locationEntity = new LocationEntity(100.01, -20.22 , now);
        spotbookDbHelper.insertLocation(locationEntity.latitude, locationEntity.longitude, locationEntity.date);

        SpotbookDatabase db = Room.databaseBuilder(context,
                SpotbookDatabase.class, "spotbook-db").build();
        ArrayList<LocationEntity> allLocations = (ArrayList<LocationEntity>) db.locationDao().getByDate(now);
        assertEquals(allLocations.get(0).latitude, locationEntity.latitude, 0.01);
        assertEquals(allLocations.get(0).longitude, locationEntity.longitude, 0.01);

        db.locationDao().delete(locationEntity);
    }

    @Test
    public void getAllLocations() {
        Context context = InstrumentationRegistry.getTargetContext();
        SpotbookDbHelper spotbookDbHelper = new SpotbookDbHelper(context);
        SpotbookDatabase db = Room.databaseBuilder(context,
                SpotbookDatabase.class, "spotbook-db").build();

        LocationEntity l1 = new LocationEntity(100.01, -20.22 , System.currentTimeMillis());
        LocationEntity l2 = new LocationEntity(100.01, -20.22 , System.currentTimeMillis() + 1);
        LocationEntity l3 = new LocationEntity(100.01, -20.22 , System.currentTimeMillis() + 2);
        ArrayList<LocationEntity> arr = new ArrayList<>();
        arr.add(l1);
        db.locationDao().insert(l1);
        arr.add(l2);
        db.locationDao().insert(l2);
        arr.add(l3);
        db.locationDao().insert(l3);

        ArrayList<LocationEntity> res = spotbookDbHelper.getAllLocations();
        assertEquals(res.size(), arr.size());

        for(int i = 0; i < res.size(); i++){
            assertEquals(res.get(i).longitude, arr.get(i).longitude, 0.1);
            assertEquals(res.get(i).latitude, arr.get(i).latitude, 0.1);
            assertEquals(res.get(i).date, arr.get(i).date, 0.1);
        }
        db.locationDao().delete(l1);
        db.locationDao().delete(l2);
        db.locationDao().delete(l3);
    }
}