package com.example.medis.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.medis.R;
import com.example.medis.database.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditCairanPerhariActivity extends AppCompatActivity {

    EditText edt_tanggal,edt_jam,edt_menu,edt_cairan;
    Button btn_input;
    private DBManager dbManager;
    Button btn_hapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String jam = getIntent().getStringExtra("jam");
        String tanggal = getIntent().getStringExtra("tanggal");
        String menu = getIntent().getStringExtra("menu");
        String konsumsi = getIntent().getStringExtra("konsumsi");
        String id_cairan = getIntent().getStringExtra("id");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink)));
        getSupportActionBar().setTitle("EDIT KONSUMSI | KORAN - HD");
        setContentView(R.layout.activity_edit_cairan_perhari);
        btn_hapus = findViewById(R.id.btn_hapus);
        dbManager = new DBManager(this);
        dbManager.open();

        edt_tanggal = findViewById(R.id.edttanggal);
        edt_cairan = findViewById(R.id.edtcairan);
        edt_jam = findViewById(R.id.edtjam);
        edt_menu = findViewById(R.id.edtmenu);
        btn_input = findViewById(R.id.btninput);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String time = formatter.format(date).toString();

        edt_tanggal.setGravity(Gravity.RIGHT);
        edt_cairan.setGravity(Gravity.RIGHT);
        edt_jam.setGravity(Gravity.RIGHT);
        edt_menu.setGravity(Gravity.RIGHT);

        edt_tanggal.setEnabled(false);
        edt_jam.setEnabled(false);

        edt_tanggal.setText(tanggal);
        edt_jam.setText(jam);
        edt_cairan.setText(konsumsi);
        edt_menu.setText(menu);

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long id= Integer.parseInt(id_cairan);
                final String tgl = tanggal;
                final String jam = edt_jam.getText().toString();
                final Integer cairan = Integer.parseInt(edt_cairan.getText().toString());
                final String menu = edt_menu.getText().toString();
                dbManager.updateCairanPerhari(id, menu,tgl, jam, cairan);
                finish();
            }
        });

        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long id= Integer.parseInt(id_cairan);
                dbManager.deleteCairanPerhari(id);
                finish();
            }
        });
    }

    }
