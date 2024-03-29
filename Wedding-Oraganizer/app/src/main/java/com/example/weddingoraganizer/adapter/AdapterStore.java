package com.example.weddingoraganizer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.api.StoreItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterStore extends RecyclerView.Adapter<AdapterStore.ViewHolder>{
    private List<StoreItem.Result> results;
    private Context context;
    private AdapterListener listener;

    private List<StoreItem.Result> storeItems = null;
    private ArrayList<StoreItem.Result> storeItemArrayList;

    public AdapterStore(Context context, List<StoreItem.Result> results, AdapterListener listener) {
        this.results    = results ;
        this.context    = context ;
        this.listener   = listener ;
    }

    @NonNull
    @Override
    public AdapterStore.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_store,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStore.ViewHolder viewHolder, int i) {
        final StoreItem.Result result = results.get(i);
        Float ratingblt = result.getRatingbulat();
        viewHolder.textView.setText( result.getNama());
        viewHolder.txtPenyedia.setText(result.getPenyedia());
        viewHolder.txtDetail.setText(result.getDetail());
        viewHolder.txtHarga.setText("Rp. "+result.getHarga());
        viewHolder.txt_dp.setText("Rp. "+result.getDp()+"(Min. DP)");
        String blt = ""+ratingblt;
        viewHolder.totalrating.setText(result.getTotalrating());
        viewHolder.rating.setRating((float) ratingblt);
        String gambar = "https://teman-wedding.cretech.id/storage/upload/paket/"+result.getGambar();
        Picasso.get()
                .load( gambar )
                .fit(). centerCrop()
                .into(viewHolder.img_store);

        String helper_open = result.getStatus().replace("Open", "Open");
        String helper_close = result.getStatus().replace("Close", "Close");

        if(result.getStatus() != helper_open){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(result);
                }
            });
        }else if(result.getStatus() != helper_close){
            viewHolder.frame.setBackgroundColor(Color.LTGRAY);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Paket tidak bisa dipesan hari ini", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout frame;
        ImageView img_store;
        TextView textView;
        TextView txt_dp;
        TextView txtPenyedia;
        TextView txtDetail;
        TextView txtHarga;
        TextView totalrating;
        RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            frame = itemView.findViewById(R.id.frame);
            img_store = itemView.findViewById(R.id.img_store);
            textView = itemView.findViewById(R.id.textView);
            txtPenyedia = itemView.findViewById(R.id.txtPenyedia);
            txtDetail = itemView.findViewById(R.id.txtDetail);
            txtHarga = itemView.findViewById(R.id.txtHarga);
            txt_dp = itemView.findViewById(R.id.txtdp);
            rating = itemView.findViewById(R.id.rating);
            totalrating = itemView.findViewById(R.id.totalrating);
        }
    }

    public void setData(List<StoreItem.Result> newResults) {
        results.clear();
        results.addAll(newResults);
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onClick(StoreItem.Result result);
    }

    public void filter(List<StoreItem.Result> itemList) {
        results = itemList;
        notifyDataSetChanged();
    }
    }

