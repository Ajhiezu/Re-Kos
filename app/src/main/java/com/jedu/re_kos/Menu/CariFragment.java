package com.jedu.re_kos.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jedu.re_kos.Adapter.kosAdapter;
import com.jedu.re_kos.Domain.kosDomain;
import com.jedu.re_kos.R;
import com.jedu.re_kos.databinding.FragmentCariBinding;

import java.util.ArrayList;

public class CariFragment extends Fragment {

    private FragmentCariBinding binding;

    public CariFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentCariBinding.inflate(inflater, container, false);

        // Inisialisasi RecyclerView
        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        ArrayList<kosDomain> items = new ArrayList<>();
        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));

        // Atur layoutManager dan adapter untuk RecyclerView
        binding.kosView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.kosView.setAdapter(new kosAdapter(items));

        // Atur layoutManager dan adapter untuk RecyclerView
        binding.kosFavorit.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.kosFavorit.setAdapter(new kosAdapter(items));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // Hindari kebocoran memori dengan melepaskan binding saat fragment dihancurkan
    }
}
