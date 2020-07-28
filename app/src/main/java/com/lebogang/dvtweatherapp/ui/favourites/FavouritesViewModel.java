package com.lebogang.dvtweatherapp.ui.favourites;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.dvtweatherapp.db.AppDatabase;
import com.lebogang.dvtweatherapp.db.entity.DataEntity;

import java.util.List;

// This model is connected to the db.
public class FavouritesViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = FavouritesViewModel.class.getSimpleName();

    private LiveData<List<DataEntity>> tasks;

    private MutableLiveData<String> mText;

    public FavouritesViewModel(Application application) {
        super(application);
        Log.i(TAG, "Actively retrieving the tasks from the DataBase");
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        tasks = database.dataDao().getAllFavouritesData();
        mText = new MutableLiveData<>();
       // Log.i(TAG, mText.getValue());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<DataEntity>> getDataFromDB() {
        Log.i(TAG, "Reading from the DataBase");
        return tasks;
    }
}