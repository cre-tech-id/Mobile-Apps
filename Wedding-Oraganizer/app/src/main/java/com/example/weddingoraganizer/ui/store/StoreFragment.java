package com.example.weddingoraganizer.ui.store;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.adapter.AdapterStore;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.StoreItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreFragment extends Fragment {
    private final String TAG = "StoreFragment";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterStore adapterStore;
    private ArrayList<StoreItem.Result> results = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_store, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        setupRecyclerView();
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading(false);
        getDataFromApi();
    }

    private void setupRecyclerView (){
        adapterStore = new AdapterStore(getActivity(), results, new AdapterStore.AdapterListener() {
            @Override
            public void onClick(StoreItem.Result result) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("intent_title", result.getNama());
//                intent.putExtra("intent_image", result.getImage());
                startActivity( intent );
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter( adapterStore );
    }

    public void showLoading(Boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getDataFromApi(){
        ApiClient.getService().getStore().enqueue(new Callback<StoreItem>() {
            @Override
            public void onResponse(Call<StoreItem> call, Response<StoreItem> response) {
                showLoading( false );
                Log.d( TAG, "onResponse: " + response.toString());
                if (response.isSuccessful()) {
                    List<StoreItem.Result> results = response.body().getResult();
                    Log.d(TAG, results.toString());
                    adapterStore.setData( results );
                }

            }

            @Override
            public void onFailure(Call<StoreItem> call, Throwable t) {
                showLoading( false );
                Log.d( TAG, t.toString());
            }
        });
    }

}