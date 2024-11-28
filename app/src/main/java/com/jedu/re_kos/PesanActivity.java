package com.jedu.re_kos;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jedu.re_kos.Adapter.pesanAdapter;
import com.jedu.re_kos.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class PesanActivity extends AppCompatActivity {

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
        RecyclerView listKontak = findViewById(R.id.ListPesan);
        listKontak.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        List<Chat> userList = new ArrayList<>();
        userList.add(new Chat("1","19.00", "Halo", "2"));
        userList.add(new Chat("2","20.00", "Test", "1"));
        userList.add(new Chat("1","19.00", "Halo", "2"));
        userList.add(new Chat("2","20.00", "Test", "1"));
        userList.add(new Chat("1","19.00", "Halo", "2"));
        userList.add(new Chat("2","20.00", "Test", "1"));
        userList.add(new Chat("1","19.00", "Halo", "2"));
        userList.add(new Chat("2","20.00", "Test", "1"));
        userList.add(new Chat("1","19.00", "Halo", "2"));
        userList.add(new Chat("2","20.00", "Test", "1"));
        userList.add(new Chat("1","19.00", "Halo", "2"));
        userList.add(new Chat("2","20.00", "Test", "1"));
        userList.add(new Chat("1","19.00", "Halo", "2"));
        userList.add(new Chat("2","20.00", "Test", "1"));
        userList.add(new Chat("1","19.00", "Halo", "2"));
        userList.add(new Chat("2","20.00", "Test", "1"));
        userList.add(new Chat("1","19.00", "Halo", "2"));
        userList.add(new Chat("2","20.00", "Test", "1"));
        userList.add(new Chat("1","19.00", "Halo", "2"));
        userList.add(new Chat("2","20.00", "Test", "1"));

        // Set adapter
        pesanAdapter adapter = new pesanAdapter(userList, "1");
        listKontak.setAdapter(adapter);
    }
}