package com.lebogang.dvtweatherapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lebogang.dvtweatherapp.R;
import com.lebogang.dvtweatherapp.db.AppDatabase;
import com.lebogang.dvtweatherapp.db.AppExecutors;
import com.lebogang.dvtweatherapp.db.entity.DataEntity;
import com.lebogang.dvtweatherapp.pojo.ForecastFivePojo;

import java.util.Date;
import java.util.List;

public class ForecastFiveAdapter extends RecyclerView.Adapter<ForecastFiveAdapter.WeatherDataViewHolder> {

    private static String TAG = ForecastFiveAdapter.class.getSimpleName();

    private Context context;
    private List<ForecastFivePojo> forecastFivePojoList;

    // Member variable for the Database
    private AppDatabase mDb;

        public ForecastFiveAdapter(Context context, List<ForecastFivePojo> forecastFivePojoList){
        this.context = context;
        this.forecastFivePojoList = forecastFivePojoList;
        this.mDb = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public WeatherDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.i(TAG, "onCreateViewHolder()");
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new WeatherDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDataViewHolder holder, int position) {
        //Log.i(TAG, "onBindViewHolder()");
        WeatherDataViewHolder weatherDataViewHolder = (WeatherDataViewHolder) holder;
        final ForecastFivePojo forecastFivePojo = forecastFivePojoList.get(position);

        int i = forecastFivePojo.temp;
        String data = forecastFivePojo.getData();
        weatherDataViewHolder.weather_textView.setText(String.valueOf(i));
        Log.i(TAG, String.valueOf(i));

        onClick(holder, data);

        //insertOfflineData();

    }

    private void onClick(WeatherDataViewHolder weatherDataViewHolder, String data){
        // When clicking on location text or re-organized fav btn.
        weatherDataViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "itemView clicked!");
                // DataEntity
                if (!data.isEmpty()) {
                    Date date = new Date();
                    double dummyLat = 65.0;
                    double dummyLng = 09.40;

                    DataEntity dataEntity = new DataEntity(data, dummyLat, dummyLng, date, 0);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "data inserted in db");
                            mDb.dataDao().insertFavourites(dataEntity);
                        }
                    });
                }else{
                    Toast.makeText(context, "Empty string", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void insertOfflineData(){
        AppDatabase database = AppDatabase.getInstance(context);
        Date date = new Date();
        DataEntity dataEntity = new DataEntity("null", 0.0, 0.0, date, 23);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "data inserted in offline db");
                database.dataDao().insertOfflineData(dataEntity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return forecastFivePojoList.size();
    }

    static class WeatherDataViewHolder extends RecyclerView.ViewHolder{

        TextView weather_textView;
        ImageButton imageButton;

        public WeatherDataViewHolder(@NonNull View itemView) {
            super(itemView);
            weather_textView = itemView.findViewById(R.id.offline_tv_1);
            imageButton = itemView.findViewById(R.id.fav_image_btn);
        }
    }
}
