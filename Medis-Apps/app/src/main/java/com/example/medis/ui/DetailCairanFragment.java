package com.example.medis.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medis.R;
import com.example.medis.database.Cairan;
import com.example.medis.database.DatabaseHelper;

import java.util.ArrayList;

public class DetailCairanFragment extends Fragment {

    private DatabaseHelper db;
    private ArrayList<Cairan> data = new ArrayList<>();
    public DetailCairanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_cairan, container, false);

        db = new DatabaseHelper(getActivity());
        data = db.getWhereCairan();

        return view;
    }
}