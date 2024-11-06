package com.jedu.re_kos.Notifikasi;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NotifikasiActivity extends AppCompatActivity {

    ListView listView;
    SimpleAdapter simpleAdapter;
    private ImageView back;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    private boolean[] clickedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        //warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(NotifikasiActivity.this, R.color.biru_navbar));

        back = findViewById(R.id.imageBack);
        back.setOnClickListener(v -> {
            onBackPressed();
        });

        listView = (ListView) findViewById(R.id.Notif);
        setNotif();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void setNotif(){
        int notifCount = 10;
        clickedStatus = new boolean[notifCount];
        for (int i = 0; i < notifCount; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("title", "notifikasi " + (i + 1));
            map.put("deskripsi", "Deskripsi notifikasi " + (i + 1));
            arrayList.add(map);
            clickedStatus[i] = false; // Set status belum diklik
        }

        simpleAdapter = new SimpleAdapter(NotifikasiActivity.this, arrayList, R.layout.notifikasi_adapter,
                new String[]{"title", "deskripsi"},
                new int[]{R.id.textViewTitle, R.id.textViewDeskirpsi}); // Pastikan ID-nya sesuai
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = ((TextView) view.findViewById(R.id.textViewTitle)).getText().toString();
                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();

                TextView deskirpsi = ((TextView) view.findViewById(R.id.textViewDeskirpsi));
                if (deskirpsi.getVisibility() == View.GONE){
                    deskirpsi.setVisibility(View.VISIBLE);
                    clickedStatus[position] = true;
                    view.setBackgroundColor(Color.WHITE);
                } else {
                    deskirpsi.setVisibility(View.GONE);
                }
            }
        });
    }
}