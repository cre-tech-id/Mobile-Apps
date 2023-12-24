package com.example.medis.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medis.R;
import com.example.medis.database.Cairan;
import com.example.medis.database.DBManager;
import com.example.medis.database.DatabaseHelper;
import com.example.medis.ui.EditCairanActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder>{
    private ArrayList<Cairan> data;

    private Context context;
    private DatabaseHelper db;
    private AdapterListener listener;

    private DBManager dbManager;

    public AdapterHistory(Context context, ArrayList<Cairan> data, AdapterListener listener) {
        this.data    = data;
        this.context    = context;
        this.listener    = listener;
        db = new DatabaseHelper(context);
    }
    @NonNull
    @Override
    public AdapterHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistory.ViewHolder holder, int i) {
        final Cairan cairans = data.get(i);
        holder.txt_tanggal.setText(cairans.getTanggal());
        holder.txt_max_cairan.setText(cairans.getTotalCairan()+" ml");
        holder.txt_konsumsi.setText(cairans.getKonsumsiCairan()+" ml");

        Integer konsumsi = Integer.parseInt(cairans.getKonsumsiCairan());
        Integer max_cairan = Integer.parseInt(cairans.getTotalCairan());
        String id_cairan = String.valueOf(cairans.getId());

        if (konsumsi > max_cairan){
            holder.frame.setBackgroundResource(R.drawable.roundcorner);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(cairans);
            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tanggal, txt_max_cairan, txt_konsumsi;
        CardView frame;
        CardView drawable ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            frame = itemView.findViewById(R.id.cardView4);
            txt_tanggal = itemView.findViewById(R.id.txt_tgl);
            txt_max_cairan = itemView.findViewById(R.id.txt_max_cairan);
            txt_konsumsi = itemView.findViewById(R.id.txt_konsumsi);
        }
    }

    public interface AdapterListener {
        void onClick(Cairan result);
    }

    public void setData(List<Cairan> newResults) {
        data.clear();
        data.addAll(newResults);
        notifyDataSetChanged();
    }

}
