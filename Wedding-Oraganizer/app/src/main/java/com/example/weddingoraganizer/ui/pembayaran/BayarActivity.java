package com.example.weddingoraganizer.ui.pembayaran;

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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weddingoraganizer.Login;
import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.BodyPesanan;
import com.example.weddingoraganizer.api.BodyProfile;
import com.example.weddingoraganizer.api.GetUserId;
import com.example.weddingoraganizer.api.LoginResponse;
import com.example.weddingoraganizer.api.PostEditProfile;
import com.example.weddingoraganizer.api.StatusPemesanan;
import com.example.weddingoraganizer.api.StoreItem;
import com.example.weddingoraganizer.helperInput;
import com.example.weddingoraganizer.ui.profile.DataUser;
import com.example.weddingoraganizer.ui.profile.EditProfile;
import com.example.weddingoraganizer.ui.store.BookingActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BayarActivity extends AppCompatActivity {

    EditText edt_nominal;
    EditText edt_pembayaran;
    EditText edt_maxbayar;

    EditText edt_mindp;
    Button btn_upload;
    ImageView img_bukti;
    boolean isOnlyImageAllowed = true;
    private String filePath;
    private static final int PICK_PHOTO = 1958;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        edt_nominal = findViewById(R.id.edt_nominal);
        edt_mindp = findViewById(R.id.edt_mindp);
        edt_maxbayar = findViewById(R.id.edt_maxbayar);
        edt_pembayaran = findViewById(R.id.edt_pembayaran);
        img_bukti = findViewById(R.id.img_bukti);
        btn_upload = findViewById(R.id.btn_upload);

        String id_pemesanan  = getIntent().getStringExtra("intent_id_pemesanan");
        String pembayaran  = getIntent().getStringExtra("intent_pembayaran");
        String dp  = getIntent().getStringExtra("intent_dp");
        String harga  = getIntent().getStringExtra("intent_harga");

        edt_pembayaran.setText(pembayaran.toString());
        edt_mindp.setText(dp.toString());
        edt_maxbayar.setText(harga.toString());

        verifyStoragePermissions(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {
            Uri imageUri = data.getData();
            filePath = getPath(imageUri);
            img_bukti.setImageURI(imageUri);
        }
    }

    public void UploadBukti(View view) {
        String id_pemesanan  = getIntent().getStringExtra("intent_id_pemesanan");
        String nominal = edt_nominal.getText().toString();
        String pembayaran = edt_pembayaran.getText().toString();
        Integer min_dp = Integer.parseInt(edt_mindp.getText().toString());
        Integer harga = Integer.parseInt(edt_maxbayar.getText().toString());
        Integer nominal_awal = Integer.parseInt(pembayaran);
        Integer nominal_bayar = Integer.parseInt(nominal);
        Integer nominal_akhir = nominal_bayar+nominal_awal;
        Integer max_bayar = harga - nominal_awal;
        Log.d("nominal awal", nominal_awal.toString());
        Log.d("harga", harga.toString());

        if(Integer.parseInt(pembayaran) == 0) {
            Log.d("pesan", "1");
            if (nominal_bayar < min_dp) {
                Toast.makeText(BayarActivity.this, "Nominal kurang dari minimal DP", Toast.LENGTH_LONG).show();
            }else if (nominal_bayar >  harga){
                Toast.makeText(BayarActivity.this, "Nominal melebihi harga paket", Toast.LENGTH_LONG).show();
            }else{
                Upload(filePath, new DataBukti(nominal,Integer.parseInt(id_pemesanan), nominal_akhir));
            }
        }else{
            if(nominal_bayar > max_bayar) {
                Toast.makeText(BayarActivity.this, "Nominal melebihi harga paket", Toast.LENGTH_LONG).show();
            }else if(Integer.parseInt(nominal) != harga - nominal_awal){
                Toast.makeText(BayarActivity.this, "Anda harus melunasi pembayaran kedua", Toast.LENGTH_LONG).show();
            }else{
                Upload(filePath, new DataBukti(nominal,Integer.parseInt(id_pemesanan), nominal_akhir));
            }
        }
        }

    public void addPhoto(View view) {
        Intent intent;

        if (isOnlyImageAllowed) {
            // only image can be selected
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            // any type of files including image can be selected
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
        }

        startActivityForResult(intent, PICK_PHOTO);
    }


    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getApplicationContext().getContentResolver().query(
                uri,
                projection,
                null,
                null,
                null
        );
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void Upload(String filePath, DataBukti dataBukti) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Gson gson = new Gson();
        String patientData = gson.toJson(dataBukti);

        RequestBody data = RequestBody.create(okhttp3.MultipartBody.FORM, patientData);

        ApiClient.getService().uploadbukti(data, image).enqueue(new Callback<PostEditProfile>() {
            @Override
            public void onResponse(Call<PostEditProfile> call, Response<PostEditProfile> response) {
                if (response.isSuccessful()) {
                    Log.d("berhasil", patientData);
                    Toast.makeText(BayarActivity.this, "Berhasil upload bukti pembayaran", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Log.d("gagal", patientData);
                    Log.d("gagal", "gagal");
                }
            }

            @Override
            public void onFailure(Call<PostEditProfile> call, Throwable t) {
                Log.d("Response", t.toString());
            }
        });
    }

//    public void getPembayaran(){
//        SharedPreferences sp1 = this.getSharedPreferences("data", Context.MODE_PRIVATE);
//        Integer id = sp1.getInt("id", 0);
//        ApiClient.getService().getStatus(id).enqueue(new Callback<StatusPemesanan>() {
//            @Override
//            public void onResponse(Call<StatusPemesanan> call, Response<StatusPemesanan> response) {
//                if (response.isSuccessful()){
//                    List<StatusPemesanan.Result> results = response.body().getResult();
//                    Log.d("pembayaran", results.toString());
//                    String combine = results.toString();
//                    combine = combine.replaceAll("\\[", "").replaceAll("\\]","");
//                    String[]detail = combine.split(";");
//                    String pembayaran = detail[7];
//                    String min_pembayaran = detail[8];
//                    String max_pembayaran = detail[9];
//                    Integer nominal_bayar = Integer.parseInt(pembayaran);
//                    edt_pembayaran.setText(pembayaran);
//                    edt_mindp.setText(min_pembayaran);
//                    edt_maxbayar.setText(max_pembayaran);
//                }else{
//
//                }
//            }
//            @Override
//            public void onFailure(Call<StatusPemesanan> call, Throwable t) {
//
//            }
//
//        });
//    }

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