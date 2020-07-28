package com.lebogang.dvtweatherapp.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lebogang.dvtweatherapp.DataRepository;
import com.lebogang.dvtweatherapp.db.AppDatabase;
import com.lebogang.dvtweatherapp.db.entity.DataEntity;
import com.lebogang.dvtweatherapp.locationservice.TrackerService;
import com.lebogang.dvtweatherapp.pojo.ForecastFivePojo;

import org.w3c.dom.Entity;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private String TAG = HomeViewModel.class.getSimpleName();

    @NonNull
    private LiveData<List<ForecastFivePojo>> mLiveData2;

    @NonNull
    private LiveData<List<DataEntity>> liveData;

    @NonNull
    private LiveData<String> mLocation;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "ViewModel init...");

        DataRepository repo = DataRepository.getInstance();
        mLiveData2 = repo.getWeatherData();

        TrackerService trackerService = new TrackerService();
        mLocation = trackerService.getCityName();

        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        liveData = database.dataDao().getAllOfflineData();
    }


    public LiveData<String> getCityName() {
        Log.i(TAG, "getCityName: ");
        return mLocation;
    }

    @NonNull
    public LiveData<List<ForecastFivePojo>> getHousesData() {
        return mLiveData2;
    }

    public LiveData<List<DataEntity>> getDataFromOfflineDB() {
        Log.i(TAG, "Reading from the DataBase");
        return liveData;
    }

    //To keep track of the lifetime of this viewModel.
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, " destroyed.");
    }
}