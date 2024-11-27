package com.jedu.re_kos.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jedu.re_kos.model.Chat;
import com.jedu.re_kos.R;

import java.util.List;

public class pesanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Chat> userList;
    private String currentUserId;

    // View Types
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    // Constructor
    public pesanAdapter(List<Chat> userList, String currentUserId) {
        this.userList = userList;
        this.currentUserId = currentUserId;
    }

    // Determine which view type to use based on sender ID
    @Override
    public int getItemViewType(int position) {
        if (userList.get(position).getSenderId().equals(currentUserId)) {
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
            return new SentMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_chat, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    // Bind data to ViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Chat user = userList.get(position);

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
        TextView name;

        public SentMessageViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }

        public void bind(Chat user) {
            name.setText(user.getDescription());
        }
    }

    // ViewHolder for received messages
    public static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }

        public void bind(Chat user) {
            name.setText(user.getDescription());
        }
    }
}
