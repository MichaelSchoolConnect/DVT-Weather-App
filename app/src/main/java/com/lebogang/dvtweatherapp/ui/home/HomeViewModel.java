package com.lebogang.dvtweatherapp.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lebogang.dvtweatherapp.DataRepository;
import com.lebogang.dvtweatherapp.db.AppDatabase;
import com.lebogang.dvtweatherapp.db.entity.OfflineDataEntity;
import com.lebogang.dvtweatherapp.locationservice.TrackerService;
import com.lebogang.dvtweatherapp.pojo.ForecastFivePojo;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private String TAG = HomeViewModel.class.getSimpleName();

    // Online
    @NonNull
    private LiveData<List<ForecastFivePojo>> forecastFiveLiveData;

    // Offline
    @NonNull
    private LiveData<List<OfflineDataEntity>> getAllOfflineData;

    // Location
    @NonNull
    private LiveData<String> mLocation;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "ViewModel init...");

        // Get data from the repo
        DataRepository repo = DataRepository.getInstance();
        forecastFiveLiveData = repo.getWeatherData();

        // Monitor any location changes from the service
        TrackerService trackerService = new TrackerService();
        mLocation = trackerService.getCityName();

        // Get data from the offline database.
        AppDatabase database = AppDatabase.getInstance(application);
        getAllOfflineData = database.offlineDataDao().getAllOfflineData();
    }


    public LiveData<String> getCityName() {
        Log.i(TAG, "getCityName: ");
        return mLocation;
    }

    @NonNull
    public LiveData<List<ForecastFivePojo>> getHousesData() {
        return forecastFiveLiveData;
    }

    public LiveData<List<OfflineDataEntity>> getDataFromOfflineDB() {
        Log.i(TAG, "Reading from the DataBase");
        return getAllOfflineData;
    }

    //To keep track of the lifetime of this viewModel.
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, " destroyed.");
    }
}