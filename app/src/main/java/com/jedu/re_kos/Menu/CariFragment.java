package com.jedu.re_kos.Menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.jedu.re_kos.Adapter.IklanPageAdapter;
import com.jedu.re_kos.Adapter.SlideItemIklan;
import com.jedu.re_kos.Adapter.kosAdapter;
import com.jedu.re_kos.Domain.kosDomain;
import com.jedu.re_kos.Model.KosModel;
import com.jedu.re_kos.Notifikasi.NotifikasiActivity;
import com.jedu.re_kos.R;
import com.jedu.re_kos.SemuaKosActivity;
import com.jedu.re_kos.databinding.FragmentCariBinding;
//import com.jedu.re_kos.factory.ViewModelFactory;
import com.jedu.re_kos.viewmodel.KosViewModel;
import com.jedu.re_kos.Model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class CariFragment extends Fragment {
    private kosAdapter adapter;
    private KosViewModel viewModel;
    private KosModel kosModel;
    private String selectedItemLokasi;
    private String selectedItemHarga;
    public interface OnProfileClickListener {
        void onProfileClicked();
    }

    private OnProfileClickListener callback;

    private FragmentCariBinding binding;
    private ImageView profil,notifikasi;
    String[] item_lokasi = {"Bondowoso", "Tamanan", "Tapen", "Sempol", "Wonosari"};
    String[] item_harga = {"100-200", "500-1000"};
    AutoCompleteTextView autoCompletelokasi, getAutoCompleteharga;

    ArrayAdapter<String> adapterItems;
    ViewPager2 viewPager;
    private Handler slideHandler = new Handler();

    public CariFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentCariBinding.inflate(inflater, container, false);
        DataModel dataModel = new DataModel();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);

        dataModel.setId(userId);

        viewPager = binding.viewPage;
        List<SlideItemIklan> slideItemIklans = new ArrayList<>();
        slideItemIklans.add(new SlideItemIklan(R.drawable.iklan));
        slideItemIklans.add(new SlideItemIklan(R.drawable.iklan));
        slideItemIklans.add(new SlideItemIklan(R.drawable.iklan));
        slideItemIklans.add(new SlideItemIklan(R.drawable.iklan));
        slideItemIklans.add(new SlideItemIklan(R.drawable.iklan));

        viewPager.setAdapter(new IklanPageAdapter(slideItemIklans));
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(5);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r*0.15f );
            }
        });

        viewPager.setPageTransformer(compositePageTransformer);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable,2000);
            }
        });


        // Temukan ImageView berdasarkan id
        profil = binding.imageViewProfil;
        notifikasi = binding.notifikasi;

        notifikasi.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), NotifikasiActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener untuk tombol profil
        profil.setOnClickListener(v -> {
            // Buat instance dari ProfilFragment
            if (callback != null) {
                callback.onProfileClicked();
            }
        });

        // Inisialisasi AutoCompleteTextView
        autoCompletelokasi = binding.autoCompleteTextViewLokasi;
        adapterItems = new ArrayAdapter<>(getContext(), R.layout.list_items, item_lokasi);
        autoCompletelokasi.setAdapter(adapterItems);
        getAutoCompleteharga = binding.autoCompleteTextViewHarga;
        adapterItems = new ArrayAdapter<>(getContext(), R.layout.list_items, item_harga);

        // Set adapter ke AutoCompleteTextView
        getAutoCompleteharga.setAdapter(adapterItems);

        // Set listener untuk AutoCompleteTextView
        autoCompletelokasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItemLokasi  = adapterView.getItemAtPosition(i).toString();
            }
        });
        getAutoCompleteharga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItemHarga = adapterView.getItemAtPosition(i).toString();
            }
        });


        // Inisialisasi RecyclerView
        KosViewModel kosViewModel = new KosViewModel();
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.kos_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        kosAdapter adapterPopular  = new kosAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapterPopular);
        kosViewModel.getKosLiveData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                adapterPopular.setKostList(data);
            }
        });
        RecyclerView kos_terdekat = binding.getRoot().findViewById(R.id.kos_terdekat);
        kos_terdekat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        kosAdapter adapterTerdekat = new kosAdapter(new ArrayList<>());
        kos_terdekat.setAdapter(adapterTerdekat);
        kosViewModel.getKosTerdekatLiveData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                adapterTerdekat.setKostList(data);
            }
        });
        RecyclerView bestkos = binding.getRoot().findViewById(R.id.BestKos);
        bestkos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        kosAdapter adapterBest = new kosAdapter(new ArrayList<>());
        bestkos.setAdapter(adapterBest);
        kosViewModel.getKosBestLiveData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                adapterBest.setKostList(data);
            }
        });
        Button button = binding.getRoot().findViewById(R.id.button);
        AutoCompleteTextView lokasi = binding.getRoot().findViewById(R.id.autoCompleteTextViewLokasi);
        AutoCompleteTextView harga = binding.getRoot().findViewById(R.id.autoCompleteTextViewHarga);

        button.setOnClickListener(e ->  {
            Intent search = new Intent(getContext(), SemuaKosActivity.class);
            search.putExtra("lokasi", selectedItemLokasi);
            search.putExtra("harga", selectedItemHarga);
            startActivity(search);
        });
        lokasi.setOnItemClickListener((parent, view, position, id) -> {
            selectedItemLokasi = parent.getItemAtPosition(position).toString();
Log.d("VALUE LOKASI",selectedItemLokasi);
        });
        harga.setOnItemClickListener((parent, view, position, id) -> {
          selectedItemHarga  = parent.getItemAtPosition(position).toString();
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnProfileClickListener) {
            callback = (OnProfileClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnProfileClickListener");
        }
    }


    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable, 3000);
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
//        binding.kosView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
////        binding.kosView.setAdapter(new kosAdapter(items));
//
//        // Atur layoutManager dan adapter untuk RecyclerView Favorit
//        binding.BestKos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
////        binding.kosFavorit.setAdapter(new kosAdapter(items));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // Hindari kebocoran memori dengan melepaskan binding saat fragment dihancurkan
    }
}