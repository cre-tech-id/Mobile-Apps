package com.example.weddingoraganizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.BodyLogin;
import com.example.weddingoraganizer.api.LoginResponse;
import com.example.weddingoraganizer.api.StatusPemesanan;
import com.example.weddingoraganizer.api.StoreItem;
import com.example.weddingoraganizer.ui.home.HomeFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.txt_email)
    EditText txt_email;
    @BindView(R.id.txt_password)
    EditText txt_password;
    public static String PREFS_NAME = "MyPrefsFile";
    private ArrayList<LoginResponse.Result> results = new ArrayList<>();
//    private ArrayList<LoginResponse.Hasil> hasils = new ArrayList<>();
    private final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        TextView textView = findViewById(R.id.txt_register);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        BodyLogin bodyLogin = new BodyLogin();
        bodyLogin.setEmail(txt_email.getText().toString());
        bodyLogin.setPassword(txt_password.getText().toString());
        ApiClient.getService().postLogin(bodyLogin).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(Login.this, "Welcome", Toast.LENGTH_LONG).show();
                    List<LoginResponse.Result> results = response.body().getResult();
//                    Log.d(TAG, results.toString());
                    String combine = results.toString();
                    combine = combine.replaceAll("\\[", "").replaceAll("\\]","");
                    String[]detail = combine.split(",");
                    String id = detail[0];
                    String nama = detail[1];
                    String alamat = detail[2];
                    String gambar = detail[3];
                    String no = detail[4];
                    SharedPreferences sp=getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed=sp.edit();
                    ed.putInt("id", Integer.parseInt(id));
                    ed.putString("nama",nama);
                    ed.putString("alamat",alamat);
                    ed.putString("gambar",gambar);
                    ed.putString("no",no);
                    ed.putBoolean("logged_in",true);
                    ed.commit();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(Login.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Invalid Details", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void regist(View view){
            String url="https://teman-wedding.cretech.id/login";
            Intent daftar = new Intent(Intent.ACTION_VIEW);
            daftar.setData(Uri.parse(url));
    }
}





