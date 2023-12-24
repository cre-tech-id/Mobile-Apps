package com.example.medis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medis.R;
import com.example.medis.database.Cairan;
import com.example.medis.database.DatabaseHelper;
import com.example.medis.database.DetailCairan;

import java.util.ArrayList;

public class AdapterKonsumsi extends RecyclerView.Adapter<AdapterKonsumsi.ViewHolder>{
    private ArrayList<DetailCairan> data;
    private Context context;
    private DatabaseHelper db;
    private AdapterListener listener;

    public AdapterKonsumsi(Context context, ArrayList<DetailCairan> data, AdapterListener listener) {
        this.data    = data;
        this.context    = context;
        this.listener = listener;
        db = new DatabaseHelper(context);
    }
    @NonNull
    @Override
    public AdapterKonsumsi.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_konsumsi,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKonsumsi.ViewHolder holder, int i) {
        final DetailCairan detailCairan = data.get(i);
        holder.txt_konsumsi.setText(detailCairan.getKonsumsiCairan());
        holder.txt_jam.setText(detailCairan.getJam());
        holder.txt_menu.setText(detailCairan.getMenu());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(detailCairan);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_jam, txt_menu, txt_konsumsi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_jam = itemView.findViewById(R.id.txt_jam);
            txt_menu = itemView.findViewById(R.id.txt_menu);
            txt_konsumsi = itemView.findViewById(R.id.txt_konsumsi);
        }
    }

    public interface AdapterListener {
        void onClick(DetailCairan result);
    }
}
