package com.lebogang.dvtweatherapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "offlineDataTable")
public class OfflineDataEntity {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "temp")
    public int temp;

    public OfflineDataEntity(int temp){
        this.temp = temp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getTemp() {
        return temp;
    }
}
