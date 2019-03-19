package com.gentwo.th.spotbook.db;

import android.arch.persistence.room.*;

import java.util.Date;

@Entity
public class LocationEntity {
    public LocationEntity(){}

    @ColumnInfo(name = "latitude")
    public double latitude;
    @ColumnInfo(name = "longitude")
    public double longitude;
    @ColumnInfo(name = "date")
    @PrimaryKey
    public double date;
    public LocationEntity(double lat, double lon, double d){
        latitude = lat;
        longitude = lon;
        date = d;
    }
}
