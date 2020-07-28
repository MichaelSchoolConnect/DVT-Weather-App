package com.lebogang.dvtweatherapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lebogang.dvtweatherapp.db.entity.DataEntity;

import java.util.List;

@Dao
public interface DataDao {

    /**
     * Because these methods each require Room to run two queries, we add the
     * @Transaction annotation to both methods to ensure that the whole operation is performed atomically.
     * */
    @Transaction
    @Query("SELECT * FROM weatherdata")
    LiveData<List<DataEntity>> getAllFavouritesData();

    @Transaction
    @Query("SELECT * FROM weatherdata")
    LiveData<List<DataEntity>> getAllOfflineData();

    @Insert
    void insertFavourites(DataEntity dataEntity);

    @Insert
    void insertOfflineData(DataEntity offlineDataDataEntity);

}
