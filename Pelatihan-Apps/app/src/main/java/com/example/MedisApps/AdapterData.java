package com.example.MedisApps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MedisApps.R;
import com.example.MedisApps.DataModel;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolder>{
    private List<DataModel.Result> results;
    private Context context;
    private AdapterListener listener;

    public AdapterData(Context context, List<DataModel.Result> results, AdapterListener listener) {
        this.results    = results ;
        this.context    = context ;
        this.listener   = listener ;
    }

    @NonNull
    @Override
    public AdapterData.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hasil,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterData.ViewHolder viewHolder, int i) {
        final DataModel.Result result = results.get(i);
        viewHolder.txtNIPD.setText( result.getNipd() );
        viewHolder.txtHasil.setText(result.getH1());
        viewHolder.txtJurusan.setText(result.getH2());

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

        TextView txtNIPD;
        TextView txtHasil;
        TextView txtJurusan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.imageView);
            txtNIPD = itemView.findViewById(R.id.txtNIPD);
            txtHasil = itemView.findViewById(R.id.txtHasil);
            txtJurusan = itemView.findViewById(R.id.txtJurusan);

        }
    }

    public void setData(List<DataModel.Result> newResults) {
        results.clear();
        results.addAll(newResults);
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onClick(DataModel.Result result);
    }


}

