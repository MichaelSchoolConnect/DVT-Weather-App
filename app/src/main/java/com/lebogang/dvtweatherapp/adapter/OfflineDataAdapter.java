package com.lebogang.dvtweatherapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lebogang.dvtweatherapp.R;
import com.lebogang.dvtweatherapp.db.AppDatabase;
import com.lebogang.dvtweatherapp.db.AppExecutors;
import com.lebogang.dvtweatherapp.db.entity.FavouritesEntity;
import com.lebogang.dvtweatherapp.db.entity.OfflineDataEntity;

import java.util.Date;
import java.util.List;

public class OfflineDataAdapter extends RecyclerView.Adapter<OfflineDataAdapter.OfflineViewHolder> {

    private static String TAG = OfflineDataAdapter.class.getSimpleName();

    private Context context;
    private List<OfflineDataEntity> entityList;

    public OfflineDataAdapter(Context context, List<OfflineDataEntity> entityList){
        this.context = context;
        this.entityList = entityList;
    }

    @NonNull
    @Override
    public OfflineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offline_layout, parent, false);
        return new OfflineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineViewHolder holder, int position) {
        final OfflineDataEntity offlineDataEntity = entityList.get(position);
        int temp = offlineDataEntity.temp;
        holder.temp.setText(String.valueOf(temp));

        onClick(holder, "data", temp);

    }

    // Insert data into Fav db
    private void onClick(OfflineViewHolder weatherDataViewHolder, String data, int temp){
        // When clicking on location text or re-organized fav btn.
        weatherDataViewHolder.temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "itemView clicked!");
                // FavouritesEntity
                if (!data.isEmpty()) {
                    Date date = new Date();
                    double dummyLat = 65.0;
                    double dummyLng = 09.40;

                    AppDatabase appDatabase = AppDatabase.getInstance(context);
                    FavouritesEntity favouritesEntity = new FavouritesEntity(data, dummyLat, dummyLng, date, temp);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "data inserted in db");
                            appDatabase.favouritesDao().insertFavourites(favouritesEntity);
                        }
                    });
                }else{
                    Toast.makeText(context, "Empty string", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    static class OfflineViewHolder extends RecyclerView.ViewHolder {

        TextView temp;

        public OfflineViewHolder(@NonNull View itemView) {
            super(itemView);

            temp = itemView.findViewById(R.id.item_tv_1);
        }
    }
}
