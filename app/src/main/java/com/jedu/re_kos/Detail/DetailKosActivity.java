package com.jedu.re_kos.Detail;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.jedu.re_kos.Adapter.DetailKosAdapter;
import com.jedu.re_kos.Domain.DetailKosDomain;
import com.jedu.re_kos.databinding.ActivityDeailKosBinding;

import java.util.ArrayList;

public class DetailKosActivity extends AppCompatActivity {

    private ActivityDeailKosBinding binding;
    private DetailKosAdapter adapter;
    private ArrayList<DetailKosDomain> kosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Inflate layout menggunakan ViewBinding
        binding = ActivityDeailKosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inisialisasi RecyclerView
       // initRecyclerView();
    }

    private void initRecyclerView() {
        // Siapkan data untuk ditampilkan
        kosList = new ArrayList<>();
        kosList.add(new DetailKosDomain("baseline_bathtub_24", "Fasilitas 1"));

        // Atur LayoutManager dan adapter untuk RecyclerView
        binding.fasilitasKos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailKosAdapter(kosList);
        binding.fasilitasKos.setAdapter(adapter);
    }
}
