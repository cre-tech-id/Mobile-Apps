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
import android.support.v4.media.RatingCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.adapter.AdapterHistoryPembayaran;
import com.example.weddingoraganizer.adapter.AdapterListBayar;
import com.example.weddingoraganizer.adapter.AdapterStatus;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.GetListPembayaran;
import com.example.weddingoraganizer.api.GetUserId;
import com.example.weddingoraganizer.api.StatusPemesanan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBayarActivity extends AppCompatActivity {
    private final String TAG = "Pesanan";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterListBayar adapterListBayar;
    private ArrayList<GetListPembayaran.Result> results = new ArrayList<>();
    Button btn_upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bayar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView1);
        progressBar = findViewById(R.id.progressBar1);
        btn_upload = findViewById(R.id.btn_upload);
        setupRecyclerView();

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPayment();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading(false);
        getListBayar();
    }

    private void setupRecyclerView (){
        adapterListBayar = new AdapterListBayar(ListBayarActivity.this, results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListBayarActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterListBayar);
    }

    public void showLoading(Boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    public void getListBayar(){
        SharedPreferences sp1 = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        Integer id_pemesanan  = getIntent().getIntExtra("intent_id_pemesanan",0);
        ApiClient.getService().getListPembayaran(id_pemesanan).enqueue(new Callback<GetListPembayaran>() {
            @Override
            public void onResponse(Call<GetListPembayaran> call, Response<GetListPembayaran> response) {
                if (response.isSuccessful()){
                    List<GetListPembayaran.Result> results = response.body().getResult();
                    adapterListBayar.setData(results);
                }else{

                }
            }
            @Override
            public void onFailure(Call<GetListPembayaran> call, Throwable t) {

            }

        });
    }

    public void toPayment(){
        SharedPreferences sp1 = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        Integer id = sp1.getInt("id", 0);
        ApiClient.getService().getStatus(id).enqueue(new Callback<StatusPemesanan>() {
            @Override
            public void onResponse(Call<StatusPemesanan> call, Response<StatusPemesanan> response) {
                if (response.isSuccessful()){
                    List<StatusPemesanan.Result> results = response.body().getResult();
                    String combine = results.toString();
                    combine = combine.replaceAll("\\[", "").replaceAll("\\]","");
                    String[]detail = combine.split(";");
                    Intent intent = new Intent(ListBayarActivity.this, BayarActivity.class);
                    intent.putExtra("intent_id_pemesanan", detail[3]);
                    intent.putExtra("intent_dp", detail[8]);
                    intent.putExtra("intent_pembayaran", detail[7]);
                    intent.putExtra("intent_harga", detail[9]);
                    startActivity(intent);

                    Log.d("data", detail[3] +"-"+ detail[7]+"-"+detail[8]+"-"+detail[9]);
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
