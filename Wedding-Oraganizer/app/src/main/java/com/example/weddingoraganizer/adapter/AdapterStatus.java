package com.example.weddingoraganizer.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.example.weddingoraganizer.ui.pesanan.PesananActivity;
import com.example.weddingoraganizer.ui.store.BookingActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterStatus extends RecyclerView.Adapter<AdapterStatus.ViewHolder>{

    private List<StatusPemesanan.Result> results;
    private List<PembayaranResponse.Result> results_con;
    private Context context;

    public AdapterStatus(Context context, List<StatusPemesanan.Result> results) {
        this.results    = results;
        this.context    = context;
    }
    @NonNull
    @Override
    public AdapterStatus.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new AdapterStatus.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_status,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStatus.ViewHolder viewHolder, int i) {
        final StatusPemesanan.Result result = results.get(i);
        viewHolder.txt_paket.setText("Nama Paket: "+result.getPaket());
        viewHolder.txt_date.setText("Tanggal Booking: "+result.getTanggal());
        viewHolder.txt_status.setText("Status: "+result.getStatus());

        if(result.getPembayaran() == 0){
            viewHolder.txt_pembayaran.setText("Pembayaran: Belum bayar");
            viewHolder.btn_terima.setVisibility(View.GONE);
        }else if (result.getPembayaran() < result.getHarga_paket()){
            viewHolder.txt_pembayaran.setText("Pembayaran: DP (Rp."+result.getPembayaran()+")");
            viewHolder.btn_terima.setVisibility(View.GONE);
        }else{
            viewHolder.txt_pembayaran.setText("Pembayaran: Lunas");
            viewHolder.btn_terima.setVisibility(View.VISIBLE);
        }

//        if(result.getPembayaran() != helper_lunas){
//            viewHolder.btn_terima.setVisibility(View.VISIBLE);
//        }else if(result.getPembayaran() != helper_dp){
//            viewHolder.btn_terima.setVisibility(View.INVISIBLE);
//        }else if(result.getPembayaran() != helper_belum_bayar) {
//            viewHolder.btn_terima.setVisibility(View.INVISIBLE);
//        }

        String helper_selesai = result.getSelesai().replace("Sudah selesai", "Sudah selesai");

        if(result.getSelesai() != helper_selesai){
            viewHolder.btn_terima.setEnabled(false);
            viewHolder.btn_terima.setBackgroundColor(Color.LTGRAY);
        }else{
            viewHolder.btn_terima.setEnabled(true);
        }

        viewHolder.btn_terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Berikan Rating Kamu!!!");
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View customLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.fragment_rating, viewGroup, false);
                builder.setView(customLayout);

                // add a button
                builder.setPositiveButton("OK", (dialog, which) -> {
                    // send data from the AlertDialog to the Activity
                    final RatingBar simpleRatingBar = customLayout.findViewById(R.id.rating);
                    EditText edt_komentar = customLayout.findViewById(R.id.edt_komentar);

                    Integer selesai = result.getId();
                    String userid = ""+result.getUserid();
                    String penyediaid = ""+result.getPenyediaid();
                    String rating = "" + simpleRatingBar.getRating();
                    BodyRating bodyRating = new BodyRating();
                    bodyRating.setSelesai(selesai);
                    bodyRating.setUserid(userid);
                    bodyRating.setPenyediaid(penyediaid);
                    bodyRating.setRating(rating);

                    BodyKomentar bodyKomentar = new BodyKomentar();
                    bodyKomentar.setPaket_id(result.getId_paket());
                    bodyKomentar.setUsers_id(userid);
                    bodyKomentar.setPenyedia_id(penyediaid);
                    bodyKomentar.setKomentar(edt_komentar.getText().toString());


                    ApiClient.getService().postKomentar(bodyKomentar).enqueue(new Callback<PesananResponse>() {
                        @Override
                        public void onResponse(Call<PesananResponse> call, Response<PesananResponse> response) {
                            if (response.isSuccessful()){

                            }else{

                            }
                        }

                        @Override
                        public void onFailure(Call<PesananResponse> call, Throwable t) {

                        }
                    });



                    ApiClient.getService().postRating(bodyRating).enqueue(new Callback<RatingResponse>() {
                        @Override
                        public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                            if (response.isSuccessful()){
                                viewHolder.btn_terima.setBackgroundColor(Color.LTGRAY);
                                viewHolder.btn_terima.setEnabled(false);
                                Toast.makeText(context, "Berhasil memberikan rating", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(context, "gagal", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RatingResponse> call, Throwable t) {

                        }
                    });
                });
                builder.setNegativeButton("Kembali", (dialog, which) -> {
                    dialog.dismiss();
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressbar;
        Button btn_terima;
        TextView txt_status;
        TextView txt_paket;
        TextView txt_date;
        TextView txt_pembayaran;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_paket = itemView.findViewById(R.id.txt_paket);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_pembayaran = itemView.findViewById(R.id.txt_pembayaran);
            btn_terima = itemView.findViewById(R.id.btn_terima);
            progressbar = itemView.findViewById(R.id.progressbar);

        }
    }

    public void setData(List<StatusPemesanan.Result> newResults) {
        results.clear();
        results.addAll(newResults);
        newResults.toString();
        notifyDataSetChanged();
    }

    private void sendDialogDataToActivity(String data) {
        Toast.makeText(context,  data, Toast.LENGTH_LONG).show();
    }

    private void postRating( String user, String penyedia, String rating) {
        BodyRating bodyRating = new BodyRating();
        bodyRating.setUserid(user);
        bodyRating.setPenyediaid(penyedia);
        bodyRating.setRating(rating);

        Log.d("response", String.valueOf(bodyRating));

        ApiClient.getService().postRating(bodyRating).enqueue(new Callback<RatingResponse>() {
            @Override
            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Berhasil memberikan rating", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "gagal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RatingResponse> call, Throwable t) {

            }
        });

    }
}

