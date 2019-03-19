package com.gentwo.th.spotbook.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpotbookDatabaseTest {

    @Test
    public void locationDao() {
        Context context = InstrumentationRegistry.getTargetContext();
        SpotbookDatabase db = Room.databaseBuilder(context,
                SpotbookDatabase.class, "spotbook-db").build();
        assertTrue(db.locationDao() != null);
    }
}