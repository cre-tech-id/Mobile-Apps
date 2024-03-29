package com.example.weddingoraganizer.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weddingoraganizer.R;
import com.example.weddingoraganizer.api.ApiClient;
import com.example.weddingoraganizer.api.BodyKomentar;
import com.example.weddingoraganizer.api.BodyPesanan;
import com.example.weddingoraganizer.api.BodyRating;
import com.example.weddingoraganizer.api.PembayaranResponse;
import com.example.weddingoraganizer.api.PesananResponse;
import com.example.weddingoraganizer.api.RatingResponse;
import com.example.weddingoraganizer.api.StatusPemesanan;
import com.example.weddingoraganizer.api.StoreItem;
import com.example.weddingoraganizer.ui.pembayaran.ListBayarActivity;
import com.example.weddingoraganizer.ui.pesanan.PesananActivity;
import com.example.weddingoraganizer.ui.store.BookingActivity;
import com.example.weddingoraganizer.ui.store.DetailActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterHistoryPembayaran extends RecyclerView.Adapter<AdapterHistoryPembayaran.ViewHolder>{

    private List<StatusPemesanan.Result> results;
    private Context context;
    private AdapterListener listener;

    public AdapterHistoryPembayaran(Context context, List<StatusPemesanan.Result> results, AdapterListener listener) {
        this.results    = results;
        this.context    = context;
        this.listener    = listener;
    }
    @NonNull
    @Override
    public AdapterHistoryPembayaran.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new AdapterHistoryPembayaran.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history_pembayaran,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistoryPembayaran.ViewHolder viewHolder, int i) {
        final StatusPemesanan.Result result = results.get(i);
        viewHolder.txt_paket.setText("Nama Paket: "+result.getPaket());
        viewHolder.txt_date.setText("Tanggal Booking: "+result.getTanggal());

        if(result.getPembayaran() == 0){
            viewHolder.txt_pembayaran.setText("Pembayaran: Belum bayar");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(result);
                }
            });
        }else if (result.getPembayaran() < result.getHarga_paket()){
            viewHolder.txt_pembayaran.setText("Pembayaran: DP (Rp."+result.getPembayaran()+")");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(result);
                }
            });
        }else{
            viewHolder.txt_pembayaran.setText("Pembayaran: Lunas (Rp."+result.getPembayaran()+")");
            viewHolder.frame.setCardBackgroundColor(Color.LTGRAY);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Pembayaran sudah lunas!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressbar;
        Button btn_pembayaran;
        TextView txt_status;
        TextView txt_paket;
        TextView txt_date;
        CardView frame;
        TextView txt_pembayaran;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_paket = itemView.findViewById(R.id.txt_paket);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_pembayaran = itemView.findViewById(R.id.txt_pembayaran);
            progressbar = itemView.findViewById(R.id.progressbar);
            frame = itemView.findViewById(R.id.frame);

        }
    }

    public void setData(List<StatusPemesanan.Result> newResults) {
        results.clear();
        results.addAll(newResults);
        newResults.toString();
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onClick(StatusPemesanan.Result result);
    }
}

