package com.jedu.re_kos.Menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.jedu.re_kos.Adapter.IklanPageAdapter;
import com.jedu.re_kos.Adapter.SlideItemIklan;
//import com.jedu.re_kos.Adapter.kosAdapter;
import com.jedu.re_kos.Model.KosModel;
import com.jedu.re_kos.Notifikasi.NotifikasiActivity;
import com.jedu.re_kos.R;
import com.jedu.re_kos.databinding.FragmentDashboardBinding;
import com.jedu.re_kos.viewmodel.KosViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private KosModel kosModel;
//    private kosAdapter adapter;
    private KosViewModel viewModel;

    public interface OnProfileClickListener {
        void onProfileClicked();
    }

    private OnProfileClickListener callback;

    private @NonNull FragmentDashboardBinding binding;
    private ImageView profil,notifikasi;
    String[] item = {"Bondowoso", "Tamanan", "Tapen", "Sempol", "Wonosari"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    ViewPager2 viewPager;
    private Handler slideHandler = new Handler();

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
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




        initRecyclerView();

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
//        ArrayList<kosDomain> items = new ArrayList<>();
//        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
//        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
//        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
//        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
//        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
//        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));
//        items.add(new kosDomain("gambar_kos", "Kos Putri", "Kos Asiyap", "Blindungan, Bondowoso", "Kamar Mandi Dalam", 3.0, 500000));

        // Atur layoutManager dan adapter untuk RecyclerView
//        binding.kosView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        binding.kosView.setAdapter(new kosAdapter(items));

        // Atur layoutManager dan adapter untuk RecyclerView Favorit
//        binding.kosFavorit.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        binding.kosFavorit.setAdapter(new kosAdapter(items));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // Hindari kebocoran memori dengan melepaskan binding saat fragment dihancurkan
    }
}