package com.example.weddingoraganizer.adapter;

import android.content.Context;
import android.graphics.Typeface;
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
import com.example.weddingoraganizer.api.GetKomentar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterKomentar extends RecyclerView.Adapter<AdapterKomentar.ViewHolder>{
    private List<GetKomentar.Result> results;
    private Context context;

    public AdapterKomentar(Context context, List<GetKomentar.Result> results) {
        this.results    = results;
        this.context    = context;
    }

    @NonNull
    @Override
    public AdapterKomentar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_komentar,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final GetKomentar.Result result = results.get(i);
        viewHolder.txt_user.setText(result.getNama());
        viewHolder.txt_user.setTypeface(null, Typeface.BOLD);
        viewHolder.txt_komentar.setText(result.getKomentar());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_user;
        TextView txt_komentar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_user = itemView.findViewById(R.id.txt_user);
            txt_komentar = itemView.findViewById(R.id.txt_komen);

        }
    }

    public void setData(List<GetKomentar.Result> newResults) {
        results.clear();
        results.addAll(newResults);
        notifyDataSetChanged();
    }

}

