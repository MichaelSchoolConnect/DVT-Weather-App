package com.lebogang.dvtweatherapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lebogang.dvtweatherapp.db.entity.FavouritesEntity;

import java.util.List;

// A Dao contains methods used for accessing the database.
@Dao
public interface FavouritesDao {

    /**
     * Because these methods each require Room to run two queries, we add the
     * @Transaction annotation to both methods to ensure that the whole operation is performed atomically.
     * */
    @Transaction
    @Query("SELECT * FROM weatherdata")
    LiveData<List<FavouritesEntity>> getAllFavouritesData();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavourites(FavouritesEntity favouritesEntity);

}
