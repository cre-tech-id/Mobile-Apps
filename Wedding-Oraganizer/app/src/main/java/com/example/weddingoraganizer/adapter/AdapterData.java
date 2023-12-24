package com.example.weddingoraganizer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingoraganizer.ItemPenyedia;
import com.example.weddingoraganizer.R;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolderData>{

    public AdapterData(List<ItemPenyedia> listPenyedia) {
        ListPenyedia = listPenyedia;
    }

    private List<ItemPenyedia> ListPenyedia;

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penyedia, parent, false);
        return new ViewHolderData(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        ItemPenyedia itemPenyedia = ListPenyedia.get(position);
        holder.imgPeneydia.setImageResource(itemPenyedia.getImgPenyedia());
        holder.namaPenyedia.setText(itemPenyedia.getNamaPenyedia());
    }

    @Override
    public int getItemCount() {
        return ListPenyedia.size();
    }

    public static class ViewHolderData extends RecyclerView.ViewHolder{
        ImageView imgPeneydia;
        TextView namaPenyedia;

        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            imgPeneydia = itemView.findViewById(R.id.imgPenyedia);
            namaPenyedia = itemView.findViewById(R.id.namaPenyedia);
        }
    }

}

