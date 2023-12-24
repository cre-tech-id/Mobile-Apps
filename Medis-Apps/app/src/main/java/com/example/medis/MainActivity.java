package com.example.medis;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.medis.databinding.ActivityMainBinding;
import com.example.medis.ui.EdukasiActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Pesanan";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink)));

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_cairan, R.id.navigation_history, R.id.navigation_about, R.id.navigation_emergency)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
    public void switchCairan(View v) {
        binding.navView.setSelectedItemId(R.id.navigation_cairan);
    }

    public void switchHistory(View v) {
        binding.navView.setSelectedItemId(R.id.navigation_history);
    }

    public void switchEdukasi(View v) {
        Intent intent = new Intent(getApplicationContext(), EdukasiActivity.class);
        startActivity(intent);
    }

    public void switchAbout(View v) {
        binding.navView.setSelectedItemId(R.id.navigation_about);
    }

    public void switchEmergency(View v) {
        binding.navView.setSelectedItemId(R.id.navigation_emergency);
    }

    public void chat(View v) {
        final int[] checkedItem = {-1};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        // set the custom icon to the alert dialog
        alertDialog.setIcon(R.drawable.chat);

        // title of the alert dialog
        alertDialog.setTitle("Pilih siapa yang akan dihubungi");

        final String[] listItems = new String[]{"dr. Ramadi Satryo SpPD", "dr. Budi Santoso SpPD"};

        alertDialog.setSingleChoiceItems(listItems, checkedItem[0], (dialog, which) -> {
            checkedItem[0] = which;
            if (checkedItem[0] == 0){
                Uri uri = Uri.parse("https://wa.me/6285755618607 ");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }else {
                Uri uri = Uri.parse("https://wa.me/628175022272 ");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            dialog.dismiss();
        });

        // set the negative button if the user is not interested to select or change already selected item
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> {

        });

        // create and build the AlertDialog instance with the AlertDialog builder instance
        AlertDialog customAlertDialog = alertDialog.create();

        // show the alert dialog when the button is clicked
        customAlertDialog.show();
    }
}