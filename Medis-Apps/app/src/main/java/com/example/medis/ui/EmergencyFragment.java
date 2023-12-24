package com.example.medis.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.medis.R;

public class EmergencyFragment extends Fragment {


    public EmergencyFragment() {
        // Required empty public constructor
    }

    Context c;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        c = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ImageButton btn_emergency;
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        btn_emergency = view.findViewById(R.id.btn_emergency);
        btn_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.rsudbangil.emergency_mobile_app");
                if (intent != null) {
                    startActivity(intent);
                } else {
                    Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.rsudbangil.emergency_mobile_app"));
                    startActivity(i);
                }
            }
        });
        return view;
    }
}



