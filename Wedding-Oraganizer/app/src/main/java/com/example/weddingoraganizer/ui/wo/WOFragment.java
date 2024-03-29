package com.example.weddingoraganizer.ui.wo;

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
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.adapter.AdapterStore;
import com.example.weddingoraganizer.adapter.AdapterWO;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.GetPenyedia;
import com.example.weddingoraganizer.api.StoreItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WOFragment extends Fragment {
    private final String TAG = "response";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterWO adapterWO;
    private ArrayList<GetPenyedia.Result> results = new ArrayList<>();

    private List<GetPenyedia.Result> resultList = null;

    SearchView search_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_wo, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView2);
        progressBar = view.findViewById(R.id.progressBar2);
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
        List<GetPenyedia.Result> filterList = new ArrayList<>();
        for (GetPenyedia.Result item : results  ){
            if(item.getPenyedia().toLowerCase().contains(newText.toLowerCase())){
                filterList.add(item);
            }
        }
        adapterWO.filter(filterList);
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading(false);
        getDataFromApi();
    }

    private void setupRecyclerView (){
        adapterWO = new AdapterWO(getActivity(), results, new AdapterWO.AdapterListener() {
            @Override
            public void onClick(GetPenyedia.Result result) {
                String gambar = "https://teman-wedding.cretech.id/storage/upload/profile/"+result.getGambar();
                Intent intent = new Intent(getActivity(), ProfilewoActivity.class);
                intent.putExtra("intent_nama", result.getPenyedia());
                intent.putExtra("intent_alamat", result.getAlamat());
                intent.putExtra("intent_no", result.getNo());
                intent.putExtra("intent_rating", result.getRatingbulat());
                intent.putExtra("intent_total", result.getTotalrating());
                intent.putExtra("intent_gambar", gambar);
                startActivity(intent);

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter( adapterWO );


    }

    public void showLoading(Boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getDataFromApi(){
        ApiClient.getService().getPenyedia().enqueue(new Callback<GetPenyedia>() {
            @Override
            public void onResponse(Call<GetPenyedia> call, Response<GetPenyedia> response) {
                showLoading( false );
                Log.d( TAG, "onResponse: " + response.toString());
                if (response.isSuccessful()) {
                    List<GetPenyedia.Result> results = response.body().getResult();
                    Log.d(TAG, results.toString());
                    adapterWO.setData( results );
                }
            }

            @Override
            public void onFailure(Call<GetPenyedia> call, Throwable t) {
                showLoading( false );
                Log.d( TAG, t.toString());
            }
        });
    }


}