package com.example.medis.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.medis.MainActivity;
import com.example.medis.R;
import com.example.medis.adapter.AdapterDetailCairan;
import com.example.medis.database.Cairan;
import com.example.medis.database.DatabaseHelper;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterDetailCairan adapterDetailCairan;
    private DatabaseHelper db;
    private ArrayList<Cairan> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink)));

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        setupRecyclerView();

    }

    private void setupRecyclerView (){
        db = new DatabaseHelper(this);
        data = db.getWhereCairan();
        if (data.size() > 0) {
            adapterDetailCairan = new AdapterDetailCairan(HistoryActivity.this, data, new AdapterDetailCairan.AdapterListener() {
                @Override
                public void onClick(Cairan result) {
                    Intent intent = new Intent(HistoryActivity.this, CairanActivity.class);
                    intent.putExtra("intent_id", result.getTanggal());
                    startActivity(intent);
                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HistoryActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterDetailCairan);
        }else{
            recyclerView.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "There is no contact in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
    }

    public void showLoading(Boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showLoading(false);
    }
}