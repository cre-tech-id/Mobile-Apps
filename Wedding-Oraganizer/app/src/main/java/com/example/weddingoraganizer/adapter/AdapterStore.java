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
import com.example.weddingoraganizer.api.StoreItem;

import java.util.ArrayList;
import java.util.List;

public class AdapterStore extends RecyclerView.Adapter<AdapterStore.ViewHolder>{
    private List<StoreItem.Result> results;
    private Context context;
    private AdapterListener listener;

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
        viewHolder.textView.setText( result.getNama() );

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

        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
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


    }

