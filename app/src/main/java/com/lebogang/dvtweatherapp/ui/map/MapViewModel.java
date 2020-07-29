package com.lebogang.dvtweatherapp.ui.map;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.dvtweatherapp.db.AppDatabase;
import com.lebogang.dvtweatherapp.db.entity.FavouritesEntity;

import java.util.List;

public class MapViewModel extends AndroidViewModel {

    private static String TAG = MapViewModel.class.getSimpleName();

    @NonNull
    private LiveData<List<FavouritesEntity>> getFavLiveData;

    private MutableLiveData<String> mText;

    public MapViewModel(Application application) {
        super(application);
        Log.i(TAG, "ViewModel");
        AppDatabase database = AppDatabase.getInstance(application);
        getFavLiveData = database.favouritesDao().getAllFavouritesData();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<FavouritesEntity>> getDataFromFavDB() {
        Log.i(TAG, "Reading from the DataBase");
        return getFavLiveData;
    }
}