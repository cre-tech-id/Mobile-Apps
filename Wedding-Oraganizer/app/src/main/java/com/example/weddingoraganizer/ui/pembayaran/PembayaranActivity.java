package com.example.weddingoraganizer.ui.pembayaran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.adapter.AdapterPembayaran;
import com.example.weddingoraganizer.adapter.AdapterStatus;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.StatusPemesanan;
import com.example.weddingoraganizer.ui.pesanan.PesananActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranActivity extends AppCompatActivity {
    private final String TAG = "Pembayaran";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterPembayaran adapterPembayaran;
    private ArrayList<StatusPemesanan.Result> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);

        recyclerView = findViewById(R.id.recyclerView1);
        progressBar = findViewById(R.id.progressBar1);
        setupRecyclerView();

    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading(false);
        getPembayaran();
    }

    private void setupRecyclerView (){
        adapterPembayaran = new AdapterPembayaran(PembayaranActivity.this, results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PembayaranActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterPembayaran);
    }

    public void showLoading(Boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    public void getPembayaran(){
        SharedPreferences sp1 = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        Integer id = sp1.getInt("id", 0);
        ApiClient.getService().getStatus(id).enqueue(new Callback<StatusPemesanan>() {
            @Override
            public void onResponse(Call<StatusPemesanan> call, Response<StatusPemesanan> response) {
                List<StatusPemesanan.Result> results = response.body().getResult();
                Log.d(TAG, results.toString());
                adapterPembayaran.setData(results);
            }
            @Override
            public void onFailure(Call<StatusPemesanan> call, Throwable t) {

            }
        });
    }

}
