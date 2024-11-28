package com.jedu.re_kos.Adapter;

// ItemAdapter.java
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.model.Chat;
import com.jedu.re_kos.PesanActivity;
import com.jedu.re_kos.R;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ViewHolder> {

    private List<Chat> itemList;

    public chatAdapter(List<Chat> itemList) {
        this.itemList = itemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_nama);
            description = itemView.findViewById(R.id.txt_chat);
            time = itemView.findViewById(R.id.txt_waktu);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.time.setText(item.getTime());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), PesanActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

