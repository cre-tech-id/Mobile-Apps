package com.example.weddingoraganizer.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weddingoraganizer.Login;
import com.example.weddingoraganizer.MainActivity;
import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.SplashScreen;
import com.example.weddingoraganizer.adapter.AdapterStore;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.GetUserId;
import com.example.weddingoraganizer.api.LoginResponse;
import com.example.weddingoraganizer.api.StoreItem;
import com.example.weddingoraganizer.ui.pembayaran.HistoryPembayaranActivity;
import com.example.weddingoraganizer.ui.pembayaran.PembayaranActivity;
import com.example.weddingoraganizer.ui.pesanan.PesananActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private TextView txt_nama, txt_gambar, txt_logout, txt_pesanan, txt_pembayaran;
    private ImageButton btn_pemesanan, btn_pembayaran;
    private ImageButton btn_edit_profile, btn_logout;
    private LinearLayout layout, logged_out_material;
    private ImageView img_profile;
    private AdapterStore adapterStore;
    private List<LoginResponse.Result> results;
    private ProgressBar progressBar;
    private Button btn_register, btn_login;

    @Override
    public void onStart() {
        super.onStart();
        showLoading(false);
        SharedPreferences sp1 = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        if (sp1.contains("logged_in")) {
            getUser();
        } else {

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_nama = view.findViewById(R.id.txt_nama);
        layout = view.findViewById(R.id.layout);
        txt_gambar = view.findViewById(R.id.txt_gambar);
        txt_logout = view.findViewById(R.id.txt_logout);
        txt_pesanan = view.findViewById(R.id.txt_pesanan);
        txt_pembayaran = view.findViewById(R.id.txt_pembayaran);
        btn_pemesanan = view.findViewById(R.id.btn_pemesanan);
        btn_pembayaran = view.findViewById(R.id.btn_pembayaran);
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_edit_profile = view.findViewById(R.id.btn_edit_profile);
        img_profile = view.findViewById(R.id.img_profile);
        progressBar = view.findViewById(R.id.progressBar);
        btn_login = view.findViewById(R.id.btn_login);
        btn_register = view.findViewById(R.id.btn_register);
        logged_out_material = view.findViewById(R.id.logged_out_material);
        String url = "https://teman-wedding.cretech.id/registeruser";

        SharedPreferences sp1 = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String nama = sp1.getString("nama", "User not found");
        String alamat = sp1.getString("alamat", "User not found");
        String gambar = sp1.getString("gambar", "User not found");
        Boolean logged_in = sp1.getBoolean("logged_in", false);
        if (sp1.contains("logged_in")) {
            getUser();
            layout.setVisibility(View.VISIBLE);
            logged_out_material.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.GONE);
            logged_out_material.setVisibility(View.VISIBLE);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btn_pemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PesananActivity.class);
                startActivity(intent);
            }
        });

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });

        btn_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryPembayaranActivity.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp1 = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp1.edit();
                editor.remove("id");
                editor.remove("nama");
                editor.remove("alamat");
                editor.remove("gambar");
                editor.remove("no");
                editor.remove("logged_in");
                editor.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    public void getUser(){
        SharedPreferences sp1 = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        Integer id = sp1.getInt("id", 0);

        ApiClient.getService().getUserPerId(id).enqueue(new Callback<GetUserId>() {
            @Override
            public void onResponse(Call<GetUserId> call, Response<GetUserId> response) {
                if (response.isSuccessful()){
                    List<GetUserId.Result> results = response.body().getResult();
                    Log.d("User", results.toString());
                    String combine = results.toString();
                    combine = combine.replaceAll("\\[", "").replaceAll("\\]","");
                    String[]user = combine.split(",");
                    String nama = user[0];
                    String selected_kota = user[1];
                    String selected_kecamatan = user[2];
                    String selected_kelurahan = user[3];
                    String alamat = user[4];
                    String no = user[5];
                    String gambar = user[6];
                    txt_nama.setText(nama);
                    gambar = "https://teman-wedding.cretech.id/storage/upload/profile/"+gambar;
                    Picasso.get()
                            .load( gambar )
                            .fit(). centerCrop()
                            .into(img_profile);
                }else {

                }
            }
            @Override
            public void onFailure(Call<GetUserId> call, Throwable t) {

            }
        });
    }
    public void showLoading(Boolean loading) {
//        if (loading) {
//            progressBar.setVisibility(View.VISIBLE);
//        } else {
//            progressBar.setVisibility(View.GONE);
//        }
    }
}