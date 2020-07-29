package com.lebogang.dvtweatherapp.ui.favourites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lebogang.dvtweatherapp.R;
import com.lebogang.dvtweatherapp.adapter.FavouritesAdapter;
import com.lebogang.dvtweatherapp.db.entity.FavouritesEntity;

import java.util.List;

public class FavouritesFragment extends Fragment {

    // Constant for logging
    private static final String TAG = FavouritesFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private FavouritesAdapter mAdapter;
    private FavouritesViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        View root = inflater.inflate(R.layout.fragment_favourites, container, false);

        viewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        // Set the RecyclerView to its corresponding view
        // Member variables for the adapter and RecyclerView
        mRecyclerView = root.findViewById(R.id.recyclerViewTasks);
        mRecyclerView.setHasFixedSize(true);
        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setUpViewModel();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated()");

    }

    private void setUpViewModel() {
        Log.i(TAG, "Called ViewModelProviders");
        viewModel.getDataFromFavDB().observe(getViewLifecycleOwner(), new Observer<List<FavouritesEntity>>() {
            @Override
            public void onChanged(List<FavouritesEntity> favouritesEntities) {
                Log.i(TAG, "onChanged() : " + favouritesEntities);

                mAdapter = new FavouritesAdapter(getContext(), favouritesEntities);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }
}