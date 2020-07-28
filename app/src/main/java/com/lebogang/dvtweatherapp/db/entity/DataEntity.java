package com.lebogang.dvtweatherapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@androidx.room.Entity(tableName = "weatherdata")
public class DataEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "weather")
    private String text;

    @ColumnInfo(name = "lng")
    private double lng;

    @ColumnInfo(name = "lat")
    private double lat;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    // Offline db.
    @ColumnInfo(name = "temp")
    private int temp;

    @Ignore
    public DataEntity(){}

    @Ignore
    public DataEntity(int id, String text, Date updatedAt){
        this.id = id;
        this.text = text;
        this.updatedAt = updatedAt;
    }

    public DataEntity(String text, double lat, double lng, Date updatedAt, int temp){
        this.text = text;
        this.lat = lat;
        this.lng = lng;
        this.updatedAt = updatedAt;
        this.temp = temp;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public double getLat(){
        return lat;
    }

    public double getLng(){
        return lng;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public int getTemp() {
        return temp;
    }

}
