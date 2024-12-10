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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.jedu.re_kos.Adapter.IklanPageAdapter;
import com.jedu.re_kos.Adapter.SlideItemIklan;
import com.jedu.re_kos.Notifikasi.NotifikasiActivity;
import com.jedu.re_kos.R;
import com.jedu.re_kos.SemuaKosActivity;
import com.jedu.re_kos.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding; // ViewBinding untuk mengelola view dengan aman
    private Handler slideHandler = new Handler();
    private String[] item = {"Bondowoso", "Tamanan", "Tapen", "Sempol", "Wonosari"};

    public interface OnProfileClickListener {
        void onProfileClicked();
    }

    private OnProfileClickListener callback;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Gunakan ViewBinding untuk meng-inflate layout
        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        // Setup komponen UI
        setupViewPager();
        setupButtons();
        setupAutoCompleteTextView();

        // Kembalikan root view dari binding
        return binding.getRoot();
    }

    private void setupViewPager() {
        ViewPager2 viewPager = binding.viewPage;

        // Data dummy untuk ViewPager
        List<SlideItemIklan> slideItemIklans = new ArrayList<>();
        slideItemIklans.add(new SlideItemIklan(R.drawable.iklan));
        slideItemIklans.add(new SlideItemIklan(R.drawable.iklan));
        slideItemIklans.add(new SlideItemIklan(R.drawable.iklan));

        // Pasang adapter untuk ViewPager
        viewPager.setAdapter(new IklanPageAdapter(slideItemIklans));
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        // Tambahkan efek transformasi halaman
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager.setPageTransformer(compositePageTransformer);

        // Slider otomatis
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable, 3000);
            }
        });
    }

    private final Runnable slideRunnable = () -> {
        int currentItem = binding.viewPage.getCurrentItem();
        binding.viewPage.setCurrentItem(currentItem + 1);
    };

    private void setupButtons() {
        // Tombol profil
        binding.imageViewProfil.setOnClickListener(v -> {
            if (callback != null) {
                callback.onProfileClicked();
            }
        });

        // Tombol notifikasi
        binding.notifikasi.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SemuaKosActivity.class);
            startActivity(intent);
        });

        // Tombol lanjut

    }

    private void setupAutoCompleteTextView() {
        AutoCompleteTextView autoCompleteTextView = binding.autoCompleteTextView;
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(requireContext(), R.layout.list_items, item);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener((adapterView, view, position, id) -> {
            String selectedItem = adapterView.getItemAtPosition(position).toString();
            Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_SHORT).show();
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Hindari memory leaks
    }
}
