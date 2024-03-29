package com.example.weddingoraganizer.ui.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weddingoraganizer.Login;
import com.example.weddingoraganizer.MainActivity;
import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.SplashScreen;
import com.example.weddingoraganizer.adapter.AdapterKomentar;
import com.example.weddingoraganizer.adapter.AdapterStatus;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.GetKomentar;
import com.example.weddingoraganizer.api.StatusPemesanan;
import com.example.weddingoraganizer.api.StoreItem;
import com.example.weddingoraganizer.ui.pesanan.PesananActivity;
import com.example.weddingoraganizer.ui.wo.ProfilewoActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    TextView txt_paket;
    TextView txt_penyedia;
    TextView txt_harga;
    TextView txt_detail;
    ImageButton btn_wa;
    ImageButton btn_pemesanan;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterKomentar adapterKomentar;
    private ArrayList<GetKomentar.Result> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        txt_paket = (TextView)findViewById(R.id.txt_paket);
        txt_penyedia = (TextView)findViewById(R.id.txt_penyedia);
        txt_harga = (TextView)findViewById(R.id.txt_harga);
        txt_detail = (TextView)findViewById(R.id.txt_detail);
        btn_wa = (ImageButton) findViewById(R.id.btn_wa);
        btn_pemesanan = (ImageButton) findViewById(R.id.btn_pemesanan);

        String id  = getIntent().getStringExtra("intent_id");
        String paket = getIntent().getStringExtra("intent_paket");
        String penyedia = getIntent().getStringExtra("intent_penyedia");
        String alamat = getIntent().getStringExtra("intent_alamat");
        Float rating = getIntent().getFloatExtra("intent_rating",0);
        String total = getIntent().getStringExtra("intent_total");
        String detail = getIntent().getStringExtra("intent_detail");
        String harga = getIntent().getStringExtra("intent_harga");
        String no = getIntent().getStringExtra("intent_noHp");
        String gambar = getIntent().getStringExtra("intent_gambar");
        String gambar_user = getIntent().getStringExtra("intent_gambaruser");
        Picasso.get()
                .load( gambar )
                .into( (ImageView)findViewById(R.id.img_detail) );
        txt_paket.setText(paket);
        txt_penyedia.setText(penyedia);
        txt_harga.setText(harga);
        txt_detail.setText(detail);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        setupRecyclerView();

        btn_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://wa.me/62"+no);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btn_pemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp1 = DetailActivity.this.getSharedPreferences("data", Context.MODE_PRIVATE);
                if (sp1.contains("logged_in")) {
                    Intent intent = new Intent(DetailActivity.this, BookingActivity.class);
                    intent.putExtra("intent_paket", paket);
                    intent.putExtra("intent_id", id);
                    intent.putExtra("intent_harga", harga);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DetailActivity.this, Login.class);
                    startActivity(intent);
                }
            }
        });

        txt_penyedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, ProfilewoActivity.class);
                intent.putExtra("intent_nama", penyedia);
                intent.putExtra("intent_alamat", alamat);
                intent.putExtra("intent_no", no);
                intent.putExtra("intent_rating", rating);
                intent.putExtra("intent_total", total);
                intent.putExtra("intent_gambar", gambar_user);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading(false);
        getKomentar();
    }

    private void setupRecyclerView (){
        adapterKomentar = new AdapterKomentar(DetailActivity.this, results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterKomentar);
    }

    public void showLoading(Boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    public void getKomentar(){
        String idd  = getIntent().getStringExtra("intent_id");
        int id = Integer.parseInt(idd);
        ApiClient.getService().getKomentar(id).enqueue(new Callback<GetKomentar>() {
            @Override
            public void onResponse(Call<GetKomentar> call, Response<GetKomentar> response) {
                if (response.isSuccessful()){
                    List<GetKomentar.Result> results = response.body().getResult();
                    adapterKomentar.setData(results);
                }else{

                }
            }
            @Override
            public void onFailure(Call<GetKomentar> call, Throwable t) {

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