package com.lebogang.dvtweatherapp.ui.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lebogang.dvtweatherapp.R;
import com.lebogang.dvtweatherapp.adapter.FavouritesAdapter;
import com.lebogang.dvtweatherapp.adapter.OfflineDataAdapter;
import com.lebogang.dvtweatherapp.db.entity.FavouritesEntity;
import com.lebogang.dvtweatherapp.ui.favourites.FavouritesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static String TAG = MapFragment.class.getSimpleName();

    private List<FavouritesEntity> favouritesEntities;

    private GoogleMap mMap;
    private MapView mapView;
    private MapViewModel mapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_map, container, false);

        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);

        mapView = root.findViewById(R.id.mapView);

        assert mapView != null;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        setUpViewModel();

        return root;
    }

    private void setUpViewModel() {
        Log.i(TAG, "Called ViewModelProviders");

        mapViewModel.getDataFromFavDB().observe(getViewLifecycleOwner(), new Observer<List<FavouritesEntity>>() {
            @Override
            public void onChanged(List<FavouritesEntity> weatherDataEntities) {
                Log.i(TAG, "onChanged() : " + weatherDataEntities);
                favouritesEntities = weatherDataEntities;
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //check internet connection for wifi
        Log.i(TAG, "onMapReady: " + favouritesEntities);
        try {
            for (int i = 0; i < favouritesEntities.size(); i++) {
                createMarker(favouritesEntities.get(i).getLat(), favouritesEntities.get(i).getLng(),
                        favouritesEntities.get(i).getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    protected void createMarker(double latitude, double longitude, String title) {

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet("")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_map)));
    }

    @Override
    public void onStart(){
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }
}