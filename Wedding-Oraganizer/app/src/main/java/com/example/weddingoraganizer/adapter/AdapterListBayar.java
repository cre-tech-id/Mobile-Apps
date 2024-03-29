package com.example.weddingoraganizer.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.BodyKomentar;
import com.example.weddingoraganizer.api.BodyPesanan;
import com.example.weddingoraganizer.api.BodyRating;
import com.example.weddingoraganizer.api.GetListPembayaran;
import com.example.weddingoraganizer.api.GetPenyedia;
import com.example.weddingoraganizer.api.PembayaranResponse;
import com.example.weddingoraganizer.api.PesananResponse;
import com.example.weddingoraganizer.api.RatingResponse;
import com.example.weddingoraganizer.api.StatusPemesanan;
import com.example.weddingoraganizer.ui.pesanan.PesananActivity;
import com.example.weddingoraganizer.ui.store.BookingActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterListBayar extends RecyclerView.Adapter<AdapterListBayar.ViewHolder>{

    private List<GetListPembayaran.Result> results;
    private Context context;

    public AdapterListBayar(Context context, List<GetListPembayaran.Result> results) {
        this.results    = results;
        this.context    = context;
    }
    @NonNull
    @Override
    public AdapterListBayar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new AdapterListBayar.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_bayar,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListBayar.ViewHolder viewHolder, int i) {
        final GetListPembayaran.Result result = results.get(i);
        viewHolder.txt_nominal.setText("Nominal Bayar: "+result.getNominal());
        viewHolder.txt_date.setText("Tanggal Bayar: "+result.getTanggal());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressbar;
        Button btn_pembayaran;
        TextView txt_nominal;
        TextView txt_date;
        ConstraintLayout frame;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nominal = itemView.findViewById(R.id.txt_nominal);
            txt_date = itemView.findViewById(R.id.txt_date);
            progressbar = itemView.findViewById(R.id.progressbar);
            frame = itemView.findViewById(R.id.frame);

        }
    }

    public void setData(List<GetListPembayaran.Result> newResults) {
        results.clear();
        results.addAll(newResults);
        newResults.toString();
        notifyDataSetChanged();
    }
}

