package com.jedu.re_kos.Menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jedu.re_kos.Adapter.chatAdapter;
import com.jedu.re_kos.Model.ChatModel;
import com.jedu.re_kos.R;
import com.jedu.re_kos.viewmodel.ChatsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private static final int POLL_INTERVAL = 500; // 30 seconds for interval
    private ChatsViewModel chatModel;
    private chatAdapter adapterPopular;
    private int userId;
    private Handler handler;
    private Runnable pollingRunnable;
    private int lastLengthData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        // Get userId from SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", 0);

        // Initialize ViewModel
        chatModel = new ViewModelProvider(this).get(ChatsViewModel.class);

        // Set up RecyclerViews
        RecyclerView recyclerView = view.findViewById(R.id.recycleviewChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPopular = new chatAdapter(new ArrayList<>(), requireContext());
        recyclerView.setAdapter(adapterPopular);

        // Start periodic polling to fetch chat data
        startPeriodicPolling();

        return view;
    }

    private void startPeriodicPolling() {
        handler = new Handler();
        pollingRunnable = new Runnable() {
            @Override
            public void run() {
                // Fetch new data from the server
                fetchChatData();
                // Schedule the next polling call after the POLL_INTERVAL
                handler.postDelayed(this, POLL_INTERVAL);  // Repeat every POLL_INTERVAL (e.g., 30 seconds)
            }
        };

        // Start the first polling
        handler.post(pollingRunnable);
    }

    private void fetchChatData() {
        // Get chat data from ViewModel based on the userId
        chatModel.getListChat(userId).observe(getViewLifecycleOwner(), data -> {
//            if (data != null && lastLengthData != data.size()) {
                lastLengthData = data.size();
                adapterPopular.setChatList(data);
                adapterPopular.setUserId(userId);
//            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Stop polling when the view is destroyed to avoid memory leaks and unnecessary requests
        if (handler != null) {
            handler.removeCallbacks(pollingRunnable);
        }
    }
}
