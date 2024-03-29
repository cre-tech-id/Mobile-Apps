package com.example.weddingoraganizer.ui.event;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.adapter.AdapterEvent;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.EventItem;
import com.example.weddingoraganizer.api.GetPenyedia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends Fragment {
    private final String TAG = "EventFragment";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterEvent adapterEvent;
    private ArrayList<EventItem.Result> results = new ArrayList<>();

    private List<EventItem.Result> resultList = null;

    SearchView search_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_event, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        search_bar = view.findViewById(R.id.search_bar);
        setupRecyclerView();

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<EventItem.Result> filterList = new ArrayList<>();
        for (EventItem.Result item : results  ){
            if(item.getEvent().toLowerCase().contains(newText.toLowerCase())){
                filterList.add(item);
            }
        }
        adapterEvent.filter(filterList);
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading(false);
        getDataFromApi();
    }

    private void setupRecyclerView (){
        adapterEvent = new AdapterEvent(getActivity(), results, new AdapterEvent.AdapterListener() {
            @Override
            public void onClick(EventItem.Result result) {
//                Intent intent = new Intent(getActivity(), DetailEventActivity.class);
//                intent.putExtra("intent_event", result.getEvent());
//                intent.putExtra("intent_penyedia", result.getPenyedia());
//                intent.putExtra("intent_deskripsi", result.getDeskripsi());
//                intent.putExtra("intent_tanggal", result.getTanggal());
//                intent.putExtra("intent_lokasi", result.getLokasi());
//                startActivity( intent );
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter(adapterEvent);

    }

    public void showLoading(Boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getDataFromApi(){
        ApiClient.getService().getEvent().enqueue(new Callback<EventItem>() {
            @Override
            public void onResponse(Call<EventItem> call, Response<EventItem> response) {
                showLoading( false );
                Log.d( TAG, "onResponse: " + response.toString());
                if (response.isSuccessful()) {
                    List<EventItem.Result> results = response.body().getResult();
                    Log.d(TAG, results.toString());
                    adapterEvent.setData(results);
                }
            }

            @Override
            public void onFailure(Call<EventItem> call, Throwable t) {
                showLoading( false );
                Log.d( TAG, t.toString());
            }

        });
    }

}