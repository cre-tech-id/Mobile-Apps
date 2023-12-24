package com.medis.MedisApps;

import androidx.appcompat.app.AppCompatActivity;

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

import com.medis.MedisApps.api.ApiClient;
import com.medis.MedisApps.api.BodyLogin;
import com.medis.MedisApps.api.LoginResponse;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        TextView textView = findViewById(R.id.txt_register);
//        textView.setMovementMethod(LinkMovementMethod.getInstance());
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
                    Log.i("Response",response.body().getMessage());
                    SharedPreferences sharedPreferences = getSharedPreferences(Login.PREFS_NAME,0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("hasLoggedin", true);
                    editor.commit();
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
            String url="https://4023-114-125-125-1.ngrok-free.app/login";
            Intent daftar = new Intent(Intent.ACTION_VIEW);
            daftar.setData(Uri.parse(url));
    }
}





