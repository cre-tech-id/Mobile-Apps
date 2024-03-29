package com.example.weddingoraganizer.ui.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weddingoraganizer.Login;
import com.example.weddingoraganizer.MainActivity;
import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.adapter.AdapterStore;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.BodyLogin;
import com.example.weddingoraganizer.api.BodyPesanan;
import com.example.weddingoraganizer.api.PesananResponse;
import com.example.weddingoraganizer.ui.profile.EditProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edt_paket;
    EditText edt_date;
    EditText edt_detail;
    Button btn_pesan;
    Integer id_pemesan;
    String paket_id;
    Spinner spinnerKota;
    Spinner spinnerKecamatan;
    Spinner spinnerKelurahan;
    ArrayList<String> listProvinsi = new ArrayList<>();
    ArrayList<String> listKota = new ArrayList<>();
    ArrayList<String> listKecamatan = new ArrayList<>();
    ArrayList<String> listKelurahan = new ArrayList<>();
    ArrayAdapter<String> provinsiAdapter;
    ArrayAdapter<String> kotaAdapter;
    ArrayAdapter<String> kecamatanAdapter;
    ArrayAdapter<String> kelurahanAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_booking);
        requestQueue = Volley.newRequestQueue(this);
        spinnerKota = findViewById(R.id.kota);
        spinnerKecamatan = findViewById(R.id.kecamatan);
        spinnerKelurahan = findViewById(R.id.kelurahan);
        String url = "https://teman-wedding.cretech.id/api/getkota/" + "JAWA TENGAH";
        edt_paket = (EditText) findViewById(R.id.edt_paket);
        edt_date = (EditText) findViewById(R.id.edt_date);
        edt_detail = (EditText) findViewById(R.id.detailalamat);
        btn_pesan = (Button) findViewById(R.id.btn_pesan);

        paket_id = getIntent().getStringExtra("intent_id");
        String pkt = getIntent().getStringExtra("intent_paket");
        String harga = getIntent().getStringExtra("intent_harga");
        edt_paket.setText(pkt);
        edt_paket.setEnabled(false);
        SharedPreferences sp1 = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        id_pemesan = sp1.getInt("id", 0);

        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        BookingActivity.this,R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edt_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String provinsi = "JAWA TENGAH";
                String kota = spinnerKota.getSelectedItem().toString();
                String kecamatan = spinnerKecamatan.getSelectedItem().toString();
                String kelurahan = spinnerKelurahan.getSelectedItem().toString();
                BodyPesanan bodyPesanan = new BodyPesanan();
                bodyPesanan.setPaket_id(paket_id);
                bodyPesanan.setPemesan_id(id_pemesan);
                bodyPesanan.setHarga_paket(harga);
                bodyPesanan.setLokasi(provinsi+" "+kota+" "+kecamatan+" "+kelurahan+" "+edt_detail.getText().toString());
                bodyPesanan.setTanggal_book(edt_date.getText().toString());

                ApiClient.getService().postPesanan(bodyPesanan).enqueue(new Callback<PesananResponse>() {
                    @Override
                    public void onResponse(Call<PesananResponse> call, Response<PesananResponse> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(BookingActivity.this, "Berhasil membuat pesanan, silahkan menunggu konfirmasi dari penyedia", Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            Toast.makeText(BookingActivity.this, "gagal", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<PesananResponse> call, Throwable t) {
                        Toast.makeText(BookingActivity.this, "gagal", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String kota = jsonObject.optString("name");
                        listKota.add(kota);
                        kotaAdapter = new ArrayAdapter<>(BookingActivity.this,
                                android.R.layout.simple_spinner_item, listKota);
                        kotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerKota.setAdapter(kotaAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        spinnerKota.setOnItemSelectedListener(BookingActivity.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (adapterView.getId() == R.id.kota) {
            listKecamatan.clear();
            String selectedKota = adapterView.getSelectedItem().toString();
            String url = "https://teman-wedding.cretech.id/api/getkecamatan/" + selectedKota;
            requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    url, null, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String kecamatan = jsonObject.optString("name");
                            listKecamatan.add(kecamatan);
                            kecamatanAdapter = new ArrayAdapter<>(BookingActivity.this,
                                    android.R.layout.simple_spinner_item, listKecamatan);
                            kecamatanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerKecamatan.setAdapter(kecamatanAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonObjectRequest);
            spinnerKecamatan.setOnItemSelectedListener(this);
        } else if (adapterView.getId() == R.id.kecamatan) {
            listKelurahan.clear();
            String selectedKecamatan = adapterView.getSelectedItem().toString();
            String url = "https://teman-wedding.cretech.id/api/getkelurahan/" + selectedKecamatan;
            requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    url, null, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String kelurahan = jsonObject.optString("name");
                            listKelurahan.add(kelurahan);
                            kelurahanAdapter = new ArrayAdapter<>(BookingActivity.this,
                                    android.R.layout.simple_spinner_item, listKelurahan);
                            kelurahanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerKelurahan.setAdapter(kelurahanAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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