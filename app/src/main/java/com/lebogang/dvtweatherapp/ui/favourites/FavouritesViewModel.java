package com.lebogang.dvtweatherapp.ui.favourites;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lebogang.dvtweatherapp.db.AppDatabase;
import com.lebogang.dvtweatherapp.db.entity.FavouritesEntity;

import java.util.List;

// This model is connected to the db.
public class FavouritesViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = FavouritesViewModel.class.getSimpleName();

    @NonNull
    private LiveData<List<FavouritesEntity>> getFavLiveData;

    public FavouritesViewModel(Application application) {
        super(application);
        Log.i(TAG, "Actively retrieving the tasks from the DataBase");
        AppDatabase database = AppDatabase.getInstance(application);
        getFavLiveData = database.favouritesDao().getAllFavouritesData();
    }

    public LiveData<List<FavouritesEntity>> getDataFromFavDB() {
        Log.i(TAG, "Reading from the DataBase");
        return getFavLiveData;
    }
}