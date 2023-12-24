package com.example.medis.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medis.R;
import com.example.medis.adapter.AdapterDetailCairan;
import com.example.medis.adapter.AdapterHistory;
import com.example.medis.database.Cairan;
import com.example.medis.database.DatabaseHelper;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private DatabaseHelper db;
    private ArrayList<Cairan> cairan = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterHistory adapterHistory;

    public HistoryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_history, null);
        db = new DatabaseHelper(getContext());
        cairan = db.getCairan();
        recyclerView = view.findViewById(R.id.recyclerViewHistory);

        adapterHistory = new AdapterHistory(getContext(), cairan, new AdapterHistory.AdapterListener() {
            @Override
            public void onClick(Cairan result) {
                Intent intent = new Intent(getContext(), DetailCairanActivity.class);
                Integer id = result.getId();
                String id_cairan = String.valueOf(id);
                intent.putExtra("tanggal", result.getTanggal());
                intent.putExtra("urin", result.getUrin());
                intent.putExtra("batas", result.getBatascairan());
                intent.putExtra("max", result.getTotalCairan());
                intent.putExtra("berat", result.getBerat());
                intent.putExtra("id", id_cairan);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterHistory);

        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }
}