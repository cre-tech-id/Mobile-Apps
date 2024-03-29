package com.example.weddingoraganizer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.api.GetPenyedia;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterWO extends RecyclerView.Adapter<AdapterWO.ViewHolder>{
    private List<GetPenyedia.Result> results;
    private Context context;
    private AdapterWO.AdapterListener listener;

    private List<GetPenyedia.Result> getPenyedia = null;
    private ArrayList<GetPenyedia.Result> penyediaArrayList;

    public AdapterWO(Context context, List<GetPenyedia.Result> results, AdapterWO.AdapterListener listener) {
        this.results    = results ;
        this.context    = context ;
        this.listener   = listener ;
    }

    @NonNull
    @Override
    public AdapterWO.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new AdapterWO.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_wo,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWO.ViewHolder viewHolder, int i) {
        final GetPenyedia.Result result = results.get(i);
        Float ratingblt = result.getRatingbulat();
        viewHolder.txtPenyedia.setText(result.getPenyedia());
        viewHolder.txtAlamat.setText(result.getAlamat());
        viewHolder.txtNo.setText("0"+result.getNo());
        String blt = ""+ratingblt;
        viewHolder.totalrating.setText(result.getTotalrating());
        viewHolder.rating.setRating((float) ratingblt);
        String gambar = "https://teman-wedding.cretech.id/storage/upload/profile/"+result.getGambar();
        Picasso.get()
                .load( gambar )
                .fit(). centerCrop()
                .into(viewHolder.img_penyedia);
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

        ImageView img_penyedia;
        TextView txtPenyedia;
        TextView txtAlamat;
        TextView txtNo;
        TextView totalrating;
        RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_penyedia = itemView.findViewById(R.id.img_penyedia);
            txtPenyedia = itemView.findViewById(R.id.txtPenyedia);
            txtAlamat = itemView.findViewById(R.id.txtAlamat);
            txtNo = itemView.findViewById(R.id.txtNo);
            rating = itemView.findViewById(R.id.rating);
            totalrating = itemView.findViewById(R.id.totalrating);
        }
    }

    public void setData(List<GetPenyedia.Result> newResults) {
        results.clear();
        results.addAll(newResults);
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onClick(GetPenyedia.Result result);
    }

    public void filter(List<GetPenyedia.Result> itemList) {
        results = itemList;
        notifyDataSetChanged();
    }
    }

