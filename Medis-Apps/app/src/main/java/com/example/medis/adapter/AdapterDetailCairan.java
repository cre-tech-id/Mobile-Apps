package com.example.medis.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medis.R;
import com.example.medis.database.Cairan;
import com.example.medis.database.DatabaseHelper;
import com.example.medis.ui.EditCairanActivity;
import com.example.medis.ui.InputCairanPerhari;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterDetailCairan extends RecyclerView.Adapter<AdapterDetailCairan.ViewHolder>{
    private ArrayList<Cairan> data;

    private Context context;
    private DatabaseHelper db;
    private AdapterListener listener;
    public AdapterDetailCairan(Context context, ArrayList<Cairan> data, AdapterListener listener) {
        this.data    = data;
        this.context    = context;
        this.listener    = listener;
        db = new DatabaseHelper(context);
    }
    @NonNull
    @Override
    public AdapterDetailCairan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail_cairan,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDetailCairan.ViewHolder holder, int i) {
        final Cairan cairans = data.get(i);
        double total = Integer.parseInt(cairans.getTotalCairan());
        double konsumsi = Integer.parseInt(cairans.getKonsumsiCairan());
        double persentase = konsumsi/total*100;

        holder.txt_tanggal.setText(cairans.getTanggal());
        holder.txt_batas_cairan.setText(cairans.getBatascairan()+" ml");
        holder.txt_urin.setText(cairans.getUrin()+" ml");
        holder.txt_max_cairan.setText(cairans.getTotalCairan()+" ml");
        holder.txt_konsumsi.setText(cairans.getKonsumsiCairan()+" ml");
        holder.txt_sisa.setText(cairans.getSisaCairan()+" ml");
        holder.txt_persentase.setText(new DecimalFormat("##.##").format(persentase)+" %");

        if (persentase < 100){
            holder.txt_ss.setBackgroundResource(R.drawable.green_color);
        }else{
            holder.txt_ss.setBackgroundResource(R.drawable.red_color);
        }

        holder.btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InputCairanPerhari.class);
                intent.putExtra("tanggal", cairans.getTanggal());
                intent.putExtra("id", cairans.getId());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(cairans);
            }
        });
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditCairanActivity.class);
                Integer id = cairans.getId();
                String id_cairan = String.valueOf(id);
                intent.putExtra("id_cairan", id_cairan);
                intent.putExtra("tanggal", cairans.getTanggal());
                intent.putExtra("urin", cairans.getUrin());
                intent.putExtra("berat", cairans.getBerat());
                intent.putExtra("batas", cairans.getBatascairan());
                intent.putExtra("total", cairans.getTotalCairan());
                context.startActivity(intent);
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
        TextView txt_ss,txt_tanggal, txt_batas_cairan, txt_urin, txt_max_cairan, txt_konsumsi, txt_sisa, txt_persentase;
        ImageButton btn_edit;
        Button btn_tambah;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            txt_tanggal = itemView.findViewById(R.id.txt_tgl);
            txt_ss = itemView.findViewById(R.id.ss);
            txt_batas_cairan = itemView.findViewById(R.id.txt_batas_cairan);
            txt_urin = itemView.findViewById(R.id.txt_jml_urin);
            txt_max_cairan = itemView.findViewById(R.id.txt_max_cairan);
            txt_konsumsi = itemView.findViewById(R.id.totalkon);
            txt_sisa = itemView.findViewById(R.id.txt_sisa);
            txt_persentase = itemView.findViewById(R.id.txt_presentase);
            btn_tambah = itemView.findViewById(R.id.btn_tambah);
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

    public String getTanggal(int i){
        final Cairan cairans = data.get(i);
        String tanggal = cairans.getTanggal();

        return tanggal;

    }

    public String getId(int i){
        final Cairan cairans = data.get(i);
        String id = String.valueOf(cairans.getId());
        return id;
    }

}