package com.example.medis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medis.R;
import com.example.medis.api.getData;
import com.example.medis.database.DBManager;
import com.example.medis.database.DatabaseHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterEdukasi extends RecyclerView.Adapter<AdapterEdukasi.ViewHolder>{
    private List<getData.Result> results;

    private Context context;
    private DatabaseHelper db;
    private AdapterListener listener;

    private DBManager dbManager;

    public AdapterEdukasi(Context context, List<getData.Result> results, AdapterListener listener) {
        this.results    = results;
        this.context    = context;
        this.listener    = listener;
        db = new DatabaseHelper(context);
    }
    @NonNull
    @Override
    public AdapterEdukasi.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_edukasi,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEdukasi.ViewHolder holder, int i) {
        final getData.Result result = results.get(i);
        holder.txt_title.setText(result.getTitle());
        holder.txt_desc.setText(result.getDesc());
        if (result.getImg().length() > 0){
            String gambar = "https://rsudbangil.biz.id/assets/img/content/"+result.getImg();
            Picasso.get()
                    .load( gambar )
                    .fit(). centerCrop()
                    .into(holder.img);
        }else{
            holder.img.setVisibility(View.INVISIBLE);
            setMarginTop(holder.txt_desc,140);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title, txt_desc;
        ImageView img;
        ConstraintLayout frame;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.title);
            txt_desc = itemView.findViewById(R.id.desc);
            img = itemView.findViewById(R.id.img);
        }
    }

    public interface AdapterListener {
        void onClick(getData.Result result);
    }

    public void setData(List<getData.Result> newResults) {
        results.clear();
        results.addAll(newResults);
        notifyDataSetChanged();
    }

    public static void setMarginTop(View v, int top) {
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams)v.getLayoutParams();
        params.setMargins(params.leftMargin, top,
                params.rightMargin, params.bottomMargin);
    }


}
