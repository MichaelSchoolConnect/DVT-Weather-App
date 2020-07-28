package com.lebogang.dvtweatherapp.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lebogang.dvtweatherapp.DataRepository;
import com.lebogang.dvtweatherapp.R;
import com.lebogang.dvtweatherapp.adapter.ForecastFiveAdapter;
import com.lebogang.dvtweatherapp.db.entity.DataEntity;
import com.lebogang.dvtweatherapp.locationservice.TrackerService;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class HomeFragment extends Fragment {

    private static String TAG = HomeFragment.class.getSimpleName();
private Context context = HomeFragment.this.getContext();
    private static final int PERMISSIONS_REQUEST = 1;

    private RecyclerView recyclerView;
    private ForecastFiveAdapter forecastFiveAdapter;

    private ContentLoadingProgressBar contentLoadingProgressBar;

    private HomeViewModel model;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = null;
        try {
            root = inflater.inflate(R.layout.fragment_home, container, false);

            //Show LoadingProgressBar
            contentLoadingProgressBar = root.findViewById(R.id.loadingBar);
            contentLoadingProgressBar.show();

            recyclerView = root.findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // TODO: Monitor network changes and act accordingly.
            if(isNetworkConnected()){
                getLocation();

                setUpViewModel();
            }else{
                //read from db
                contentLoadingProgressBar.hide();
                Log.i(TAG, "No internet connection");
                //setOfflineViewModel();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated()");
        //setUpViewModel();
    }


    private void setUpViewModel(){
        Log.i(TAG, "setUpViewModel()");
        model = new ViewModelProvider(this).get(HomeViewModel.class);

        //Observe location
        model.getCityName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // Update URL.
                Log.i(TAG, "Location onChanged: " + s);
            }
        });

        // Observe any changes on Forecast and update the UI
        model.getHousesData().observe(getViewLifecycleOwner(), weatherDataList -> {
            //Log.i(TAG, "onChanged() : " + (weatherDataList));
            contentLoadingProgressBar.hide();
            forecastFiveAdapter = new ForecastFiveAdapter(getContext(), weatherDataList);
            recyclerView.setAdapter(forecastFiveAdapter);
        });
    }

    private void setOfflineViewModel(){
        Log.i(TAG, "setUpViewModel()");
        model.getDataFromOfflineDB().observe(getViewLifecycleOwner(), new Observer<List<DataEntity>>() {
            @Override
            public void onChanged(List<DataEntity> entityList) {
                Log.i(TAG, "Offline db");
            }
        });
    }

    private void getLocation(){
        Log.i(TAG, "Getting location");
        // Check GPS is enabled
        LocationManager lm = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getContext(), "Please enable location services", Toast.LENGTH_SHORT).show();
            //finish();
        }

        // Check location permission is granted - if it is, start
        // the locationservice, otherwise request the permission
        int permission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            //Get location
            startTrackerService();
            // This runs operations in the background thread.
            DataRepository.getInstance().getWeatherDataUsingRetro();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }
    }

    private void startTrackerService() {
        Log.i(TAG, "Location locationservice started");
        this.getContext().startService(new Intent(getContext(), TrackerService.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the locationservice when the permission is granted
            startTrackerService();
        } else {
            Toast.makeText(getContext(),
                    "Allow DVT Weather App to access this device's location first.",Toast.LENGTH_LONG)
                    .show();
        }
    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}