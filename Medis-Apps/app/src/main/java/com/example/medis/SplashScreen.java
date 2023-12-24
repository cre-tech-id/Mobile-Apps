package com.example.medis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        View Splash = getWindow().getDecorView();

        int uiOption = View.SYSTEM_UI_FLAG_FULLSCREEN;
        Splash.setSystemUiVisibility(uiOption);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp1 = SplashScreen.this.getSharedPreferences("data", Context.MODE_PRIVATE);
                    if (sp1.contains("logged_in")) {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();
                    }
            }
        }, 2000);
    }
}

