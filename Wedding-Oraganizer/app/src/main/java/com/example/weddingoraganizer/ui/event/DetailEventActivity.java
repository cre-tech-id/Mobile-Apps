package com.example.weddingoraganizer.ui.event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.weddingoraganizer.R;

public class DetailEventActivity extends AppCompatActivity {
    TextView txt_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        txt_event = (TextView)findViewById(R.id.txt_event2);
        String event = getIntent().getStringExtra("intent_event");
        txt_event.setText(event);

    }
}