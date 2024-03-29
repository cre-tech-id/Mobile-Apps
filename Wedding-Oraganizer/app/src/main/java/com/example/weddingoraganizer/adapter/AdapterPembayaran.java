package com.example.weddingoraganizer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.api.StatusPemesanan;

import java.util.List;

public class AdapterPembayaran extends RecyclerView.Adapter<AdapterPembayaran.ViewHolder>{

    private List<StatusPemesanan.Result> results;
    private Context context;

    public AdapterPembayaran(Context context, List<StatusPemesanan.Result> results) {
        this.results    = results;
        this.context    = context;
    }
    @NonNull
    @Override
    public AdapterPembayaran.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new AdapterPembayaran.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pembayaran,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPembayaran.ViewHolder viewHolder, int i) {
        final StatusPemesanan.Result result = results.get(i);
        viewHolder.txt_paket.setText("Nama Paket: "+result.getPaket());
        viewHolder.txt_date.setText("Tanggal Booking: "+result.getTanggal());
        viewHolder.txt_pembayaran.setText("Status: "+result.getPembayaran());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_pembayaran;
        TextView txt_paket;
        TextView txt_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_paket = itemView.findViewById(R.id.txt_paket);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_pembayaran = itemView.findViewById(R.id.txt_pembayaran);
        }
    }

    public void setData(List<StatusPemesanan.Result> newResults) {
        results.clear();
        results.addAll(newResults);
        notifyDataSetChanged();
    }

}

