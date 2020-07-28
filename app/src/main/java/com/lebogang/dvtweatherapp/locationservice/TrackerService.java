package com.lebogang.dvtweatherapp.locationservice;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.lebogang.dvtweatherapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class TrackerService extends Service {

    private static final String TAG = TrackerService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {return null;}

    // So we can monitor location changes and adapt weather conditions accordingly.
    @NonNull
    private MutableLiveData<String> listMutableLiveData = new MutableLiveData<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
        buildNotification();
        requestLocationUpdates();
    }

    /**
     * A good practice to apply when tracking the user's location is to ensure
     * they're always aware that their location is being tracked.
     * To do this, we can use a persistent notification that shuts the app down
     * when the notification is tapped.
     **/
    private void buildNotification() {
        String stop = "stop";
        registerReceiver(stopReceiver, new IntentFilter(stop));
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);
        // Create the persistent notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_text))
                .setOngoing(true)
                .setContentIntent(broadcastIntent)
                .setSmallIcon(R.drawable.ic_action_map);
        startForeground(1, builder.build());
    }

    // The BroadcastReceiver that stops the Service.
    // NOTICE: We didnt register the reciever in the manifest file because it's an object here
    //and not a class...I dont know if you get me? But yeah...programming.
    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "received stop broadcast");
            // Stop the service when the notification is tapped
            unregisterReceiver(stopReceiver);
            stopSelf();
        }
    };

    private void requestLocationUpdates() {
        Log.i(TAG, "requestLocationUpdates()");

            LocationRequest request = new LocationRequest();
            request.setInterval(10000);
            request.setFastestInterval(5000);
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
            int permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                // Request location updates and when an update is
                // received, store the location in Firebase
                client.requestLocationUpdates(request, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        Location location = locationResult.getLastLocation();
                        if (location != null) {
                            Log.i(TAG, "location update " + location);
                            Context context;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
                                    List<Address> addressList = null;
                                    try {
                                        addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    assert addressList != null;
                                    String city = addressList.get(0).getLocality();
                                    Log.i(TAG, "city: " + city);
                                    //listMutableLiveData.setValue(city);
                                }
                            }).start();
                        }
                    }
                }, null);
            }
    }

    // Return the...you already know.
    @NonNull
    public LiveData<String> getCityName(){
        Log.i(TAG, "getCityName()");
        return listMutableLiveData;
    }

}
