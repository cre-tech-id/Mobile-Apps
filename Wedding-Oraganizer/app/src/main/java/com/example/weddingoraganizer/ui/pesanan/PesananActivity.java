package com.example.weddingoraganizer.ui.pesanan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.adapter.AdapterStatus;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.PembayaranResponse;
import com.example.weddingoraganizer.api.StatusPemesanan;
import com.example.weddingoraganizer.ui.store.BookingActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananActivity extends AppCompatActivity {
    private final String TAG = "Pesanan";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterStatus adapterStatus;
    private ArrayList<StatusPemesanan.Result> results = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView1);
        progressBar = findViewById(R.id.progressBar1);
        setupRecyclerView();

    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading(false);
        getStatus();
    }

    private void setupRecyclerView (){
        adapterStatus = new AdapterStatus(PesananActivity.this, results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PesananActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterStatus);
    }

    public void showLoading(Boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    public void getStatus(){
        SharedPreferences sp1 = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        Integer id = sp1.getInt("id", 0);
        ApiClient.getService().getStatus(id).enqueue(new Callback<StatusPemesanan>() {
            @Override
            public void onResponse(Call<StatusPemesanan> call, Response<StatusPemesanan> response) {
                if (response.isSuccessful()){
                    List<StatusPemesanan.Result> results = response.body().getResult();
                    adapterStatus.setData(results);
                }else{

                }
            }
            @Override
            public void onFailure(Call<StatusPemesanan> call, Throwable t) {

            }

        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
