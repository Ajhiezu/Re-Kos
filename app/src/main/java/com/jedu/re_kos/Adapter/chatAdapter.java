package com.jedu.re_kos.Adapter;

// ItemAdapter.java

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import com.bumptech.glide.Glide;
import com.jedu.re_kos.Model.ChatModel;
import com.jedu.re_kos.Model.UserModel;
import com.jedu.re_kos.PesanActivity;
import com.jedu.re_kos.R;
import com.jedu.re_kos.viewmodel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ViewHolder> {

    private List<ChatModel.ChatsData> itemList;
    private int userId;
    private Context context;

    public chatAdapter(List<ChatModel.ChatsData> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public TextView time;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_nama);
            description = itemView.findViewById(R.id.txt_chat);
            time = itemView.findViewById(R.id.txt_waktu);
            imageView = itemView.findViewById(R.id.img);

        }
    }

    public void setChatList(List<ChatModel.ChatsData> chats) {
        this.itemList = chats;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatModel.ChatsData item = itemList.get(position);
        holder.title.setText(item.getNama());
        holder.description.setText(item.getMessage());
        String time = "";
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
            // Format yang diinginkan
            SimpleDateFormat desiredFormat = new SimpleDateFormat("HH:mm", new Locale("id", "ID"));
            // Parse dan format ulang
            time = desiredFormat.format(originalFormat.parse(item.getTime()));
        } catch (Exception e) {
            time = "2024/2";
        }

        String image = item.getId_gambar() != null ? "https://rekos.kholzt.com/public/uploads/" + item.getId_user() + "/" +item.getId_gambar() : "https://rekos.kholzt.com/public/img/user.png";
        Glide.with(context)
                .load(image )
                .transform(new RoundedCornersTransformation(35, 0, RoundedCornersTransformation.CornerType.TOP)) // Melengkung hanya di sudut atas
                .into(holder.imageView);

        holder.time.setText(time);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), PesanActivity.class);
            intent.putExtra("id_user",item.getId_user());

            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

