package com.lebogang.dvtweatherapp.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.lebogang.dvtweatherapp.db.dao.FavouritesDao;
import com.lebogang.dvtweatherapp.db.dao.OfflineDataDao;
import com.lebogang.dvtweatherapp.db.dateconverter.DateConverter;
import com.lebogang.dvtweatherapp.db.entity.FavouritesEntity;
import com.lebogang.dvtweatherapp.db.entity.OfflineDataEntity;

@Database(entities = {FavouritesEntity.class, OfflineDataEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "weatherappdb";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract FavouritesDao favouritesDao();

    public abstract OfflineDataDao offlineDataDao();

}
