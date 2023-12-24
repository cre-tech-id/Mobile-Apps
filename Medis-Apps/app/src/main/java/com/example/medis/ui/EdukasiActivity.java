package com.example.medis.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.medis.R;
import com.example.medis.adapter.AdapterEdukasi;
import com.example.medis.api.ApiClient;
import com.example.medis.api.getData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EdukasiActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<getData.Result> results = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        getData();
        showLoading(false);
    }

    AdapterEdukasi adapterEdukasi;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edukasi);
        recyclerView = findViewById(R.id.recyclerView2);
        progressBar = findViewById(R.id.progressBar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink)));

        adapterEdukasi = new AdapterEdukasi(this, results, new AdapterEdukasi.AdapterListener() {
            @Override
            public void onClick(getData.Result result) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterEdukasi);
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