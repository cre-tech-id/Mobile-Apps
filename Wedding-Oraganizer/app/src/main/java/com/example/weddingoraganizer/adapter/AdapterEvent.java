package com.example.weddingoraganizer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.api.EventItem;
import com.example.weddingoraganizer.api.StoreItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.ViewHolder>{
    private List<EventItem.Result> results;
    private Context context;
    private AdapterListener listener;

    public AdapterEvent(Context context, List<EventItem.Result> results, AdapterListener listener) {
        this.results    = results;
        this.context    = context;
        this.listener   = listener;
    }

    @NonNull
    @Override
    public AdapterEvent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final EventItem.Result result = results.get(i);

        viewHolder.txtPenyedia.setText(result.getPenyedia());
        viewHolder.txtTanggal.setText(result.getTanggal());
        viewHolder.txtEvent.setText(result.getEvent());
        viewHolder.txtDeskripsi.setText(result.getDeskripsi());
        viewHolder.txtLokasi.setText(result.getLokasi()+", "+result.getTanggal());
        String gambar = "https://teman-wedding.cretech.id/storage/upload/event/"+result.getGambar();
        Picasso.get()
                .load( gambar )
                .fit(). centerCrop()
                .into(viewHolder.img_event);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(result);
            }
        });
    }


    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_event;
        TextView txtLokasi;
        TextView txtPenyedia;
        TextView txtDeskripsi;
        TextView txtEvent;
        TextView txtTanggal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_event = itemView.findViewById(R.id.img_event);
            txtPenyedia = itemView.findViewById(R.id.penyediaEvent);
            txtDeskripsi = itemView.findViewById(R.id.deskripsi);
            txtLokasi = itemView.findViewById(R.id.lokasi);
            txtTanggal = itemView.findViewById(R.id.tanggal);
            txtEvent = itemView.findViewById(R.id.namaEvent);

        }
    }

    public void setData(List<EventItem.Result> newResults) {
        results.clear();
        results.addAll(newResults);
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onClick(EventItem.Result result);
    }

    public void filter(List<EventItem.Result> itemList) {
        results = itemList;
        notifyDataSetChanged();
    }

}

