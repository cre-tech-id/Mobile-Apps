package com.example.medis.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medis.R;
import com.example.medis.adapter.AdapterEdukasi;
import com.example.medis.api.ApiClient;
import com.example.medis.api.getData;
import com.example.medis.databinding.FragmentEdukasiBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class EdukasiFragment extends Fragment {

    private FragmentEdukasiBinding binding;
    RecyclerView recyclerView;
    private ArrayList<getData.Result> results = new ArrayList<>();
    AdapterEdukasi adapterEdukasi;
    private ProgressBar progressBar;


    public EdukasiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
        showLoading(false);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edukasi, container, false);
        recyclerView = view.findViewById(R.id.recyclerView2);
        progressBar = view.findViewById(R.id.progressBar);

        adapterEdukasi = new AdapterEdukasi(getActivity(), results, new AdapterEdukasi.AdapterListener() {
            @Override
            public void onClick(getData.Result result) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterEdukasi);
        return view;
    }

    public void getData(){
        ApiClient.getService().getData().enqueue(new Callback<getData>() {
            @Override
            public void onResponse(Call<getData> call, Response<getData> response) {
                Log.d( "TAG", "onResponse: " + response.toString());
                if (response.isSuccessful()) {
                    List<getData.Result> results = response.body().getResult();
                    Log.d("TAG", results.toString());
                    adapterEdukasi.setData(results);
                }
            }

            @Override
            public void onFailure(Call<getData> call, Throwable t) {
                Log.d( "TAG", t.toString());
            }

        });
    }

    public void showLoading(Boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}