package com.example.weddingoraganizer.ui.pembayaran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.adapter.AdapterHistoryPembayaran;
import com.example.weddingoraganizer.adapter.AdapterStatus;
import com.example.weddingoraganizer.adapter.AdapterStore;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.StatusPemesanan;
import com.example.weddingoraganizer.api.StoreItem;
import com.example.weddingoraganizer.ui.store.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPembayaranActivity extends AppCompatActivity {
    private final String TAG = "Pesanan";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterHistoryPembayaran adapterHistoryPembayaran;

    private Integer pembayaran;
    private ArrayList<StatusPemesanan.Result> results = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_pembayaran);
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
        adapterHistoryPembayaran = new AdapterHistoryPembayaran(this, results,
                new AdapterHistoryPembayaran.AdapterListener() {
                    @Override
                    public void onClick(StatusPemesanan.Result result) {
                        Intent intent = new Intent(HistoryPembayaranActivity.this, ListBayarActivity.class);
                        intent.putExtra("intent_id_pemesanan", result.getId());
                        intent.putExtra("intent_dp", result.getDp());
                        intent.putExtra("intent_pembayaran", result.getPembayaran());
                        intent.putExtra("intent_harga", result.getHarga_paket());
                        startActivity(intent);
                    }
                });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HistoryPembayaranActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterHistoryPembayaran);
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
                    adapterHistoryPembayaran.setData(results);

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
