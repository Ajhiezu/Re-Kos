package com.jedu.re_kos;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jedu.re_kos.Adapter.kosAdapter;
import com.jedu.re_kos.viewmodel.KosViewModel;

import java.util.ArrayList;

public class SemuaKosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_semua_kos);

        String lokasi = getIntent().getStringExtra("lokasi");
        String harga = getIntent().getStringExtra("harga");
        Log.d("test", "Lokasi: " + lokasi);
        KosViewModel kosViewModel = new KosViewModel();
        RecyclerView recycle_semuakos = findViewById(R.id.recycle_semuakos);
        recycle_semuakos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        kosAdapter adapterAllKos = new kosAdapter(new ArrayList<>());
        recycle_semuakos.setAdapter(adapterAllKos);
        kosViewModel.getDataAllKosLiveData(lokasi,harga).observe(this, data -> {
            if (data != null) {
                adapterAllKos.setKostList(data);
                Log.d("TAG", "tes ");
            }
        });

    }
}