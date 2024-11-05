package com.jedu.re_kos.Detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.jedu.re_kos.Adapter.Fasilitas;
import com.jedu.re_kos.Adapter.FasilitasKosAdapter;
import com.jedu.re_kos.Adapter.FotoKosAdapter;
import com.jedu.re_kos.Adapter.IklanPageAdapter;
import com.jedu.re_kos.Adapter.SlideItemIklan;
import com.jedu.re_kos.Adapter.ViewPageAdapter;
import com.jedu.re_kos.Menu.CariFragment;
import com.jedu.re_kos.R;
import com.jedu.re_kos.Register_Login.RegisterasiActivity;

import java.util.ArrayList;
import java.util.List;

public class DetailKosFragment extends Fragment {

    private TextView textViewDeskripsiProperti1, textViewLihatSemua1;
    private TextView textViewDeskripsiProperti2, textViewLihatSemua2;
    private  TextView getTextViewLihatSemua3;
    private boolean isExpanded1 = false;
    private boolean isExpanded2 = false;
    private boolean isExpanded3 = false;
    ViewPager SlideViewPager;
    LinearLayout DotLayout;
    TextView[] dots;
    FotoKosAdapter fotoKosAdapter;
    private RecyclerView recyclerView;
    private FasilitasKosAdapter fasilitasKosAdapter;
    private List<Fasilitas> fasilitasList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_kos, container, false);

        recyclerView = view.findViewById(R.id.fasilitas_kos);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        // Isi data fasilitas
        fasilitasList = new ArrayList<>();
        fasilitasList.add(new Fasilitas("WiFi", R.drawable.baseline_bathtub_24));
        fasilitasList.add(new Fasilitas("Parkir", R.drawable.baseline_bathtub_24));
        fasilitasList.add(new Fasilitas("AC", R.drawable.baseline_bathtub_24));
        fasilitasList.add(new Fasilitas("WiFi", R.drawable.baseline_bathtub_24));
        fasilitasList.add(new Fasilitas("Parkir", R.drawable.baseline_bathtub_24));
        fasilitasList.add(new Fasilitas("AC", R.drawable.baseline_bathtub_24));
        fasilitasList.add(new Fasilitas("WiFi", R.drawable.baseline_bathtub_24));
        fasilitasList.add(new Fasilitas("Parkir", R.drawable.baseline_bathtub_24));
        fasilitasList.add(new Fasilitas("AC", R.drawable.baseline_bathtub_24));
        fasilitasList.add(new Fasilitas("WiFi", R.drawable.baseline_bathtub_24));
        fasilitasList.add(new Fasilitas("Parkir", R.drawable.baseline_bathtub_24));
        fasilitasList.add(new Fasilitas("AC", R.drawable.baseline_bathtub_24));
        // Tambahkan data lainnya sesuai kebutuhan

        fasilitasKosAdapter = new FasilitasKosAdapter(fasilitasList);
        recyclerView.setAdapter(fasilitasKosAdapter);

        SlideViewPager = view.findViewById(R.id.imageKos);
        DotLayout = view.findViewById(R.id.dotslideKos);
        fotoKosAdapter = new FotoKosAdapter(requireContext());

        SlideViewPager.setAdapter(fotoKosAdapter);

        setUpindicator(0);
        SlideViewPager.addOnPageChangeListener(viewListener);

        TextView textViewLihatSemua = view.findViewById(R.id.textView20);
        updateFasilitasList();
        textViewLihatSemua.setOnClickListener(v -> {
            isExpanded3 = !isExpanded3; // Ubah status tampilan

            // Perbarui teks "Lihat Semua" atau "Sembunyikan"
            textViewLihatSemua.setText(isExpanded3 ? "Sembunyikan" : "Lihat Semua");

            // Perbarui tampilan RecyclerView
            updateFasilitasList();
        });
        textViewDeskripsiProperti1 = view.findViewById(R.id.textViewDeskripsiProperti);
        textViewLihatSemua1 = view.findViewById(R.id.textViewLihatSemua);
        // Referensi TextView untuk Deskripsi 2 dan Lihat Semua 2
        textViewDeskripsiProperti2 = view.findViewById(R.id.textViewDeskripsiProperti2);
        textViewLihatSemua2 = view.findViewById(R.id.textViewLihatSemua2);
        // Menampilkan teks singkat saat awal
        String fullText1 = getString(R.string.deskripsi_properti1);
        String fullText2 = getString(R.string.deskripsi_properti2);
        String shortText1 = fullText1.substring(0, Math.min(fullText1.length(), 100)) + "...";
        String shortText2 = fullText2.substring(0, Math.min(fullText2.length(), 100)) + "...";// Menampilkan teks singkat
        textViewDeskripsiProperti1.setText(shortText1);
        textViewLihatSemua1.setText("Lihat Semua");
        textViewDeskripsiProperti2.setText(shortText2);
        textViewLihatSemua2.setText("Lihat Semua");
        textViewLihatSemua1.setOnClickListener(v -> {
            if (isExpanded1) {
                textViewDeskripsiProperti1.setText(shortText1);
                textViewLihatSemua1.setText("Lihat Semua");
                isExpanded1 = false;
            } else {
                textViewDeskripsiProperti1.setText(fullText1);
                textViewLihatSemua1.setText("Sembunyikan");
                isExpanded1 = true;
            }
        });
        textViewLihatSemua2.setOnClickListener(v -> {
            if (isExpanded2) {
                textViewDeskripsiProperti2.setText(shortText2);
                textViewLihatSemua2.setText("Lihat Semua");
                isExpanded2 = false;
            } else {
                textViewDeskripsiProperti2.setText(fullText2);
                textViewLihatSemua2.setText("Sembunyikan");
                isExpanded2 = true;
            }
        });

        // Temukan ImageView berdasarkan id
        ImageView imageViewShare = view.findViewById(R.id.imageViewShare);
        ImageView imageViewBack = view.findViewById(R.id.imageViewBack);

        // Set OnClickListener untuk tombol kembali
        imageViewBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        // Set OnClickListener pada ImageView untuk share
        imageViewShare.setOnClickListener(v -> {
            // Buat intent untuk share
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Bagikan ini");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Ini adalah konten yang akan dibagikan");

            // Memulai activity share
            startActivity(Intent.createChooser(shareIntent, "Bagikan melalui"));
        });

        return view;
    }

    private void updateFasilitasList() {
        // Tentukan jumlah item yang ditampilkan (4 atau semua)
        int itemCount = isExpanded3 ? fasilitasList.size() : Math.min(fasilitasList.size(), 4);

        // Buat sublist untuk menampilkan jumlah item yang sesuai
        List<Fasilitas> fasilitasToShow = fasilitasList.subList(0, itemCount);

        // Set data adapter dengan item yang sesuai
        fasilitasKosAdapter.setFasilitasList(fasilitasToShow);
        fasilitasKosAdapter.notifyDataSetChanged();
    }

    public void setUpindicator(int position){
        dots = new TextView[3];
        DotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++){

            dots[i] = new TextView(requireContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.white, requireContext().getTheme()));
            DotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.biru_navbar, requireContext().getTheme()));
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
