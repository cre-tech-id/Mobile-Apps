package com.example.medis.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medis.R;
import com.example.medis.adapter.AdapterDetailCairan;
import com.example.medis.adapter.AdapterKonsumsi;
import com.example.medis.database.Cairan;
import com.example.medis.database.DatabaseHelper;
import com.example.medis.database.DetailCairan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CairanFragment extends Fragment {
    TextView txt_input_cairan;
    TextView txt_log_cairan;
    private DatabaseHelper db;
    RecyclerView recyclerView;
    Button btn_tambah_konsumsi;
    CardView cardView;

    RecyclerView recyclerViewkonsumsi;
    AdapterDetailCairan adapterDetailCairan;
    AdapterKonsumsi adapterKonsumsi;
    private ArrayList<Cairan> cairan = new ArrayList<>();
    private ArrayList<DetailCairan> cairan_perhari = new ArrayList<>();

    public CairanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new DatabaseHelper(getContext());
        cairan = db.getWhereCairan();
        cairan_perhari = db.getKonsumsiCairan();
        if (cairan.size() > 0) {
            View view = inflater.inflate(R.layout.fragment_detail_cairan, null);
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerViewkonsumsi = view.findViewById(R.id.recyclerViewKonsumsi);
            adapterDetailCairan = new AdapterDetailCairan(getContext(), cairan, new AdapterDetailCairan.AdapterListener() {
                @Override
                public void onClick(Cairan result) {
//                    Intent intent = new Intent(HistoryActivity.this, CairanActivity.class);
//                    intent.putExtra("intent_id", result.getTanggal());
//                    startActivity(intent);
                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterDetailCairan);

            String tanggal = adapterDetailCairan.getTanggal(0);
            String id = adapterDetailCairan.getId(0);

//            btn_tambah_konsumsi.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getActivity(), InputCairanPerhari.class);
//                    intent.putExtra("tanggal", tanggal);
//                    intent.putExtra("id", id);
//                    startActivity(intent);
//                }
//            });

            if (cairan_perhari.size() > 0) {
                adapterKonsumsi = new AdapterKonsumsi(getContext(), cairan_perhari, new AdapterKonsumsi.AdapterListener(){
                    @Override
                    public void onClick(DetailCairan result) {
                        Intent intent = new Intent(getContext(), EditCairanPerhariActivity.class);
                        Integer id = result.getId();
                        String id_cairan = String.valueOf(id);
                        intent.putExtra("jam", result.getJam());
                        intent.putExtra("menu", result.getMenu());
                        intent.putExtra("konsumsi", result.getKonsumsiCairan());
                        intent.putExtra("id", id_cairan);
                        intent.putExtra("tanggal", tanggal);
                        startActivity(intent);
                    }
                });
                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
                recyclerViewkonsumsi.setLayoutManager(layoutManager1);
                recyclerViewkonsumsi.setAdapter(adapterKonsumsi);
            }

            ViewGroup rootView = (ViewGroup) getView();
            rootView.removeAllViews();
            rootView.addView(view);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cairan, container, false);
        txt_input_cairan = view.findViewById(R.id.txtinputcairan);
        cardView = view.findViewById(R.id.cardView3);
//        txt_log_cairan = view.findViewById(R.id.txtlogcairan);

        txt_input_cairan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InputActivity.class);
                startActivity(intent);
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InputActivity.class);
                startActivity(intent);
            }
        });

//        txt_log_cairan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), HistoryActivity.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

    private void setupRecyclerView() {
        db = new DatabaseHelper(getContext());
        cairan = db.getWhereCairan();
        if (cairan.size() > 0) {
            adapterDetailCairan = new AdapterDetailCairan(getContext(), cairan, new AdapterDetailCairan.AdapterListener() {
                @Override
                public void onClick(Cairan result) {
//                    Intent intent = new Intent(HistoryActivity.this, CairanActivity.class);
//                    intent.putExtra("intent_id", result.getTanggal());
//                    startActivity(intent);
                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterDetailCairan);

        } else {
            recyclerView.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "There is no contact in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
    }
}