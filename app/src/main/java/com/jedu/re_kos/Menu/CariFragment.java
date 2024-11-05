package com.jedu.re_kos.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.jedu.re_kos.Adapter.kosAdapter;
import com.jedu.re_kos.Domain.kosDomain;
import com.jedu.re_kos.R;
import com.jedu.re_kos.databinding.FragmentCariBinding;

import java.util.ArrayList;

public class CariFragment extends Fragment {

    private FragmentCariBinding binding;
    String[] item = {"Bondowoso", "Tamanan", "Tapen", "Sempol", "Wonosari"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    public CariFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentCariBinding.inflate(inflater, container, false);

        // Inisialisasi AutoCompleteTextView
        autoCompleteTextView = binding.autoCompleteTextView;
        adapterItems = new ArrayAdapter<>(getContext(), R.layout.list_items, item);

        // Set adapter ke AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapterItems);

        // Set listener untuk AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

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

        // Atur layoutManager dan adapter untuk RecyclerView Favorit
        binding.kosFavorit.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.kosFavorit.setAdapter(new kosAdapter(items));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // Hindari kebocoran memori dengan melepaskan binding saat fragment dihancurkan
    }
}
