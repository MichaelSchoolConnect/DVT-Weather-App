package com.lebogang.dvtweatherapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lebogang.dvtweatherapp.db.entity.OfflineDataEntity;

import java.util.List;

@Dao
public interface OfflineDataDao {

    @Transaction
    @Query("SELECT * FROM offlineDataTable")
    LiveData<List<OfflineDataEntity>> getAllOfflineData();

    // (onConflict = OnConflictStrategy.REPLACE) = Replace the old data and continue the transaction.
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOfflineData(OfflineDataEntity offlineDataDataEntity);

}
