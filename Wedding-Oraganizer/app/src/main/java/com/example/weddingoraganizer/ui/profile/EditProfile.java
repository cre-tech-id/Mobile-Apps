package com.example.weddingoraganizer.ui.profile;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.BodyPesanan;
import com.example.weddingoraganizer.api.BodyProfile;
import com.example.weddingoraganizer.api.GetUserId;
import com.example.weddingoraganizer.api.PostEditProfile;
import com.example.weddingoraganizer.ui.store.BookingActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edt_nama, edt_alamat, edt_no;
    TextView helper_alamat;
    Button btn_simpan, btn_simpan_gambar;
    ImageView img_profile;
    boolean isOnlyImageAllowed = true;
    private String filePath;
    private static final int PICK_PHOTO = 1958;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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
        setContentView(R.layout.activity_edit_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        edt_nama = findViewById(R.id.edt_nama);
        edt_alamat = findViewById(R.id.edt_alamat);
        edt_no = findViewById(R.id.edt_no);
        helper_alamat = findViewById(R.id.helper_alamat);
        img_profile = findViewById(R.id.img_profile);
        btn_simpan = findViewById(R.id.save);
        btn_simpan_gambar = findViewById(R.id.save_image);
        requestQueue = Volley.newRequestQueue(this);
        spinnerKota = findViewById(R.id.kota);
        spinnerKecamatan = findViewById(R.id.kecamatan);
        spinnerKelurahan = findViewById(R.id.kelurahan);

        SharedPreferences sp1 = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        Integer id = sp1.getInt("id", 0);
        String no = sp1.getString("no", "not found");
        String alamat = sp1.getString("alamat", "not found");
        String nama = sp1.getString("nama", "not found");
        verifyStoragePermissions(this);

        getuser();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String combine = helper_alamat.getText().toString();
        combine = combine.replaceAll("\\[", "").replaceAll("\\]", "");
        String[] user = combine.split(",");
        String nama = user[0];
        String old_kota = user[1];
        String old_kecamatan = user[2];
        String old_kelurahan = user[3];
        String alamat = user[4];
        String no = user[5];
        String gambar = user[6];

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
                            kecamatanAdapter = new ArrayAdapter<>(EditProfile.this,
                                    android.R.layout.simple_spinner_item, listKecamatan);
                            kecamatanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerKecamatan.setAdapter(kecamatanAdapter);
                            spinnerKecamatan.setSelection(kecamatanAdapter.getPosition(old_kecamatan));

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
                            kelurahanAdapter = new ArrayAdapter<>(EditProfile.this,
                                    android.R.layout.simple_spinner_item, listKelurahan);
                            kelurahanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerKelurahan.setAdapter(kelurahanAdapter);
                            spinnerKelurahan.setSelection(kelurahanAdapter.getPosition(old_kelurahan));

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {
            Uri imageUri = data.getData();
            filePath = getPath(imageUri);

            img_profile.setImageURI(imageUri);
            btn_simpan_gambar.setVisibility(View.VISIBLE);
            btn_simpan.setVisibility(View.GONE);
        }
    }

    public void UploadButton(View view) {
        SharedPreferences sp1 = EditProfile.this.getSharedPreferences("data", Context.MODE_PRIVATE);
        Integer id = sp1.getInt("id", 0);
        String nama = edt_nama.getText().toString();
        String kota = spinnerKota.getSelectedItem().toString();
        String kecamatan = spinnerKecamatan.getSelectedItem().toString();
        String kelurahan = spinnerKelurahan.getSelectedItem().toString();
        String alamat = edt_alamat.getText().toString();
        String no = edt_no.getText().toString();
        Update(new DataUser(id, nama,kota, kecamatan,kelurahan, alamat, no));

    }

    public void UploadButtonWGambar(View view) throws IOException {
        SharedPreferences sp1 = EditProfile.this.getSharedPreferences("data", Context.MODE_PRIVATE);
        Integer id = sp1.getInt("id", 0);
        String nama = edt_nama.getText().toString();
        String kota = spinnerKota.getSelectedItem().toString();
        String kecamatan = spinnerKecamatan.getSelectedItem().toString();
        String kelurahan = spinnerKelurahan.getSelectedItem().toString();
        String alamat = edt_alamat.getText().toString();
        String no = edt_no.getText().toString();

        UpdateWGambar(filePath, new DataUser(id, nama,kota,kecamatan,kelurahan, alamat, no));
    }

    public void getuser(){
        SharedPreferences sp1 = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        Integer id = sp1.getInt("id", 0);
        ApiClient.getService().getUserPerId(id).enqueue(new Callback<GetUserId>() {
            @Override
            public void onResponse(Call<GetUserId> call, Response<GetUserId> response) {
                if (response.isSuccessful()) {
                    List<GetUserId.Result> results = response.body().getResult();
                    Log.d("User", results.toString());
                    String combine = results.toString();
                    combine = combine.replaceAll("\\[", "").replaceAll("\\]", "");
                    String[] user = combine.split(",");
                    String nama = user[0];
                    String old_kota = user[1];
                    String old_kecamatan = user[2];
                    String old_kelurahan = user[3];
                    String alamat = user[4];
                    String no = user[5];
                    String gambar = user[6];

                    String url = "https://teman-wedding.cretech.id/api/getkota/" + "JAWA TENGAH";
                    edt_nama.setText(nama);
                    edt_alamat.setText(alamat);
                    edt_no.setText(no);
                    helper_alamat.setText(results.toString());
                    gambar = "https://teman-wedding.cretech.id/storage/upload/profile/" + gambar;
                    Picasso.get()
                            .load(gambar)
                            .fit().centerCrop()
                            .into(img_profile);

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
                                    kotaAdapter = new ArrayAdapter<>(EditProfile.this,
                                            android.R.layout.simple_spinner_item, listKota);
                                    kotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerKota.setAdapter(kotaAdapter);
                                    spinnerKota.setSelection(kotaAdapter.getPosition(old_kota));

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
                    spinnerKota.setOnItemSelectedListener(EditProfile.this);
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetUserId> call, Throwable t) {

            }
        });
    }

    public void addPhoto(View view) {
        Intent intent;

        if (isOnlyImageAllowed) {
            // only image can be selected
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            // any type of files including image can be selected
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        }

        startActivityForResult(intent, PICK_PHOTO);
    }


    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void Update(DataUser dataUser) {
        Gson gson = new Gson();
        String patientData = gson.toJson(dataUser);

        RequestBody data = RequestBody.create(okhttp3.MultipartBody.FORM, patientData);

        ApiClient.getService().update(data).enqueue(new Callback<PostEditProfile>() {
            @Override
            public void onResponse(Call<PostEditProfile> call, Response<PostEditProfile> response) {
                Toast.makeText(EditProfile.this, "Berhasil mengubah profil", Toast.LENGTH_LONG).show();
                Log.d("data", patientData);
                finish();
            }

            @Override
            public void onFailure(Call<PostEditProfile> call, Throwable t) {
                Log.d("Response", "gagal");
            }
        });
    }

    public void UpdateWGambar(String filePath, DataUser dataUser) throws IOException {
        File file = new File(filePath);
        File compressedImage = new Compressor(this)
                .setQuality(75)
                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                .compressToFile(file);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), compressedImage);
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Gson gson = new Gson();
        String patientData = gson.toJson(dataUser);

        RequestBody data = RequestBody.create(okhttp3.MultipartBody.FORM, patientData);

        ApiClient.getService().updatewgambar(data, image).enqueue(new Callback<PostEditProfile>() {
            @Override
            public void onResponse(Call<PostEditProfile> call, Response<PostEditProfile> response) {
                Toast.makeText(EditProfile.this, "Berhasil mengubah profil", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<PostEditProfile> call, Throwable t) {
                Log.d("Response", "gagal");
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}