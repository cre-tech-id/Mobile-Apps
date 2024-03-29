package com.example.weddingoraganizer.ui.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.BodyPesanan;
import com.example.weddingoraganizer.api.BodyProfile;
import com.example.weddingoraganizer.api.GetUserId;
import com.example.weddingoraganizer.api.PostEditProfile;
import com.example.weddingoraganizer.api.StatusPemesanan;
import com.example.weddingoraganizer.databinding.ActivityMainBinding;
import com.example.weddingoraganizer.databinding.ActivityUpdateGambarBinding;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateGambar extends AppCompatActivity {

    ActivityUpdateGambarBinding binding;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateGambarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getUser();
//        clickListeners();
//    }
//    private void clickListeners() {
//        binding.selectImage.setOnClickListener(v->{
//            if (ContextCompat.checkSelfPermission(UpdateGambar.this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, 10);
//            } else {
//                ActivityCompat.requestPermissions(UpdateGambar.this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},1);
//            }
//        });
//
//        binding.save.setOnClickListener(v->{
//            addCustomer(binding.txtNama.getText().toString(), binding.txtAlamat.getText().toString(), binding.txtNo.getText().toString());
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getData();
//            Context context = UpdateGambar.this;
//            path = FileUtils.getPath(context, uri);
//            Bitmap bitmap = BitmapFactory.decodeFile(path);
//            binding.imgProfile.setImageBitmap(bitmap);
//
//        }
//    }
//
//    public void addCustomer(String nama, String alamat, String no) {
//        SharedPreferences sp1 = this.getSharedPreferences("data", Context.MODE_PRIVATE);
//        Integer id = sp1.getInt("id", 0);
//
//        File file = new File(path);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part gambar = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//        RequestBody nama_user = RequestBody.create(MediaType.parse("multipart/form-data"),nama);
//        RequestBody alamat_user = RequestBody.create(MediaType.parse("multipart/form-data"), alamat);
//        RequestBody no_user = RequestBody.create(MediaType.parse("multipart/form-data"), no);
//
////        BodyProfile bodyProfile = new BodyProfile();
////        bodyProfile.setNama(binding.txtNama.getText().toString());
////        bodyProfile.setAlamat(binding.txtAlamat.getText().toString());
////        bodyProfile.setNo(binding.txtNo.getText().toString());
//
//        ApiClient.getService().updateProfile(id, nama_user, alamat_user, no_user, gambar).enqueue(new Callback<PostEditProfile>() {
//            @Override
//            public void onResponse(Call<PostEditProfile> call, Response<PostEditProfile> response) {
//
//                    Toast.makeText(getApplicationContext(), "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
//                    Log.d("berhasil", "berhasil");
//                    finish();
//            }
//            @Override
//            public void onFailure(Call<PostEditProfile> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
//                Log.e("Upload error:", t.getMessage());
//            }
//        });
//    }
//    public void getUser(){
//        SharedPreferences sp1 = this.getSharedPreferences("data", Context.MODE_PRIVATE);
//        Integer id = sp1.getInt("id", 0);
//        ApiClient.getService().getUserPerId(id).enqueue(new Callback<GetUserId>() {
//            @Override
//            public void onResponse(Call<GetUserId> call, Response<GetUserId> response) {
//                List<GetUserId.Result> results = response.body().getResult();
//                EditText nama = findViewById(R.id.txt_nama);
//                EditText alamat = findViewById(R.id.txt_alamat);
//                EditText no = findViewById(R.id.txt_no);
//                Log.d("hahaha", results.toString());
//
//                String user = results.toString();
//                user = user.replaceAll("\\[", "").replaceAll("\\]","");
//                String[]detail = user.split(",");
//                String nama_user = detail[0];
//                String alamat_user = detail[1];
//                String no_user = detail[2];
//                String gambar_user = detail[3];
//
//                nama.setText(nama_user);
//                alamat.setText(alamat_user);
//                no.setText(no_user);
//
//            }
//            @Override
//            public void onFailure(Call<GetUserId> call, Throwable t) {
//
//            }
//        });
//    }
    }
}