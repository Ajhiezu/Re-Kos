package com.jedu.re_kos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jedu.re_kos.Adapter.chatAdapter;
import com.jedu.re_kos.Adapter.pesanAdapter;
import com.jedu.re_kos.Model.ChatModel;
import com.jedu.re_kos.Model.UserModel;
import com.jedu.re_kos.viewmodel.ChatsViewModel;
import com.jedu.re_kos.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class PesanActivity extends AppCompatActivity {
    private static final int POLL_INTERVAL = 500; // 30 seconds for interval
    private ChatsViewModel chatModel;
    private pesanAdapter adapter;
    private int userId, id_receiver;
    private Handler handler;
    private Runnable pollingRunnable;
    private int lastLengthData;
    private RecyclerView listPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pesan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        chatModel = new ViewModelProvider(this).get(ChatsViewModel.class);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", 0);
        id_receiver = Integer.parseInt(getIntent().getStringExtra("id_user"));

        listPesan = findViewById(R.id.ListPesan);
        Button kirimBtn = findViewById(R.id.btnKirim);
        Toolbar toolbar = findViewById(R.id.tollbarChats);
        listPesan.setLayoutManager(new LinearLayoutManager(this));
        adapter = new pesanAdapter(new ArrayList<>(), userId);
        listPesan.setAdapter(adapter);
        startPeriodicPolling();
        kirimBtn.setOnClickListener(e -> {
            sendMessage();
        });

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getData(id_receiver).observe(this, userResponse -> {
            if (userResponse.getStatus().equals("success")) {
                UserModel userModel = userResponse.getUserModel();
                toolbar.setTitle(userModel.getNama());
            }
        });


        toolbar.setNavigationOnClickListener(e->{
            onBackPressed();
        });
    }


    private void sendMessage() {
        String message = ((EditText) findViewById(R.id.message)).getText().toString();

        chatModel.sendMessage(userId, id_receiver, message).observe(this, data -> {
                ((EditText) findViewById(R.id.message)).setText("");
            if (data != null) {
            }
        });
    }

    private void startPeriodicPolling() {
        handler = new Handler();
        pollingRunnable = new Runnable() {
            @Override
            public void run() {
                fetchChatData();
                handler.postDelayed(this, POLL_INTERVAL);  // Repeat every POLL_INTERVAL (e.g., 30 seconds)
            }
        };

        // Start the first polling
        handler.post(pollingRunnable);
    }

    private void fetchChatData() {
        // Get chat data from ViewModel based on the userId
        chatModel.getDetailChat(userId, id_receiver).observe(this, data -> {
            if (data != null) {
                if (lastLengthData != data.size()) {
                    lastLengthData = data.size();
                    adapter.setMessages(data);
                    listPesan.scrollToPosition(data.size() - 1);
                }

            }
        });
    }


}