package com.jedu.re_kos.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView;

import com.jedu.re_kos.Model.ChatModel;
import com.jedu.re_kos.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class pesanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatModel.ChatsData> userList;
    private int currentUserId;

    // View Types
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    // Constructor
    public pesanAdapter(List<ChatModel.ChatsData> userList, int currentUserId) {
        this.userList = userList;
        this.currentUserId = currentUserId;
    }

    public void setMessages(List<ChatModel.ChatsData> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    // Determine which view type to use based on sender ID
    @Override
    public int getItemViewType(int position) {
        Log.d("SAMA", userList.get(position).getId_sender().equals(currentUserId) + "");
        Log.d("Nilai",  currentUserId+ ""+userList.get(position).getId_sender());

        if (Integer.parseInt(userList.get(position).getId_sender()) == currentUserId) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    // Create the appropriate ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_chat, parent, false);
            Log.d("KANAN", "KANAN");

            return new SentMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_chat, parent, false);
            Log.d("KIRI", "KIRI");

            return new ReceivedMessageViewHolder(view);
        }
    }

    // Bind data to ViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatModel.ChatsData user = userList.get(position);

        if (holder instanceof SentMessageViewHolder) {
            ((SentMessageViewHolder) holder).bind(user);
        } else if (holder instanceof ReceivedMessageViewHolder) {
            ((ReceivedMessageViewHolder) holder).bind(user);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // ViewHolder for sent messages
    public static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView name,time;
        public SentMessageViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
        }

        public void bind(ChatModel.ChatsData user) {
            name.setText(user.getMessage());
            String timeVal = "";
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
            // Format yang diinginkan
            SimpleDateFormat desiredFormat = new SimpleDateFormat("HH:mm", new Locale("id", "ID"));
            // Parse dan format ulang
            timeVal = desiredFormat.format(originalFormat.parse(user.getTime()));
        } catch (Exception e) {
            timeVal = "2024/2";
        }
            time.setText(timeVal);
        }
    }

    // ViewHolder for received messages
    public static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }

        public void bind(ChatModel.ChatsData user) {
            name.setText(user.getMessage());
        }
    }
}
