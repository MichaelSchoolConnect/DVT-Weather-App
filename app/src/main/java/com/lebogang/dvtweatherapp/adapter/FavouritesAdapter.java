/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lebogang.dvtweatherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lebogang.dvtweatherapp.R;
import com.lebogang.dvtweatherapp.db.entity.FavouritesEntity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * This FavouritesAdapter creates and binds ViewHolders, that hold the description and priority of a task,
 * to a RecyclerView to efficiently display data.
 */
public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    private static String TAG = FavouritesAdapter.class.getSimpleName();

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Class variables for the List that holds task data and the Context
    private List<FavouritesEntity> mTaskEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the FavouritesAdapter that initializes the Context.
     *
     * @param context  the current Context
     */
    public FavouritesAdapter(Context context, List<FavouritesEntity> favouritesEntityList) {
        Log.i(TAG, "Initialized...");
        mContext = context;
        mTaskEntries = favouritesEntityList;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new FavouritesViewHolder that holds the view for each task
     */
    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder()");
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fav_item_layout, parent, false);

        return new FavouritesViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(FavouritesViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder()");

        final FavouritesEntity taskEntry = mTaskEntries.get(position);

        String description = taskEntry.getText();
        String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());

        //Set values
        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText("Updated at: " + updatedAt);
    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        return mTaskEntries.size();
    }

    // Inner class for creating ViewHolders
    static class FavouritesViewHolder extends RecyclerView.ViewHolder{

        // Class variables for the task description and priority TextViews
        TextView taskDescriptionView;
        TextView updatedAtView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public FavouritesViewHolder(View itemView) {
            super(itemView);
            Log.i(TAG, "FavouritesViewHolder()");
            taskDescriptionView = itemView.findViewById(R.id.fav_textview);
            updatedAtView = itemView.findViewById(R.id.fav_textview_updated);
        }
    }
}