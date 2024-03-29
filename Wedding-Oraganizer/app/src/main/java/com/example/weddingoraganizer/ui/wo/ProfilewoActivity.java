package com.example.weddingoraganizer.ui.wo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.weddingoraganizer.R;
import com.squareup.picasso.Picasso;

public class ProfilewoActivity extends AppCompatActivity {

    TextView txt_penyedia;
    TextView txt_alamat;
    TextView txt_no;
    TextView txt_rating;
    ImageButton btn_wa;
    ImageView img_penyedia;
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_wo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        txt_penyedia = (TextView) findViewById(R.id.txt_penyedia);
        txt_alamat = (TextView) findViewById(R.id.txt_alamat);
        txt_no = (TextView) findViewById(R.id.txt_no);
        txt_rating = (TextView) findViewById(R.id.txt_rating);
        rating = (RatingBar) findViewById(R.id.rating);
        img_penyedia = (ImageView) findViewById(R.id.img_penyedia);
        btn_wa = (ImageButton) findViewById(R.id.btn_wa);

        String penyedia  = getIntent().getStringExtra("intent_nama");
        String alamat = getIntent().getStringExtra("intent_alamat");
        String no = getIntent().getStringExtra("intent_no");
        Float rating_bulat = getIntent().getFloatExtra("intent_rating",0);
        String rating_total = getIntent().getStringExtra("intent_total");
        String gambar = getIntent().getStringExtra("intent_gambar");

        txt_penyedia.setText(penyedia);
        txt_alamat.setText(alamat);
        txt_no.setText("0"+no);
        txt_rating.setText(rating_total);
        rating.setRating((float) rating_bulat);
        Picasso.get()
                .load( gambar )
                .into( (ImageView)findViewById(R.id.img_penyedia));

        btn_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://wa.me/62"+no);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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