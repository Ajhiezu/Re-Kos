package com.jedu.re_kos.Detail;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.jedu.re_kos.Adapter.Fasilitas;
import com.jedu.re_kos.Adapter.FasilitasKosAdapter;
import com.jedu.re_kos.Adapter.FotoKosAdapter;
import com.jedu.re_kos.Model.DetailModel;
import com.jedu.re_kos.R;
import com.jedu.re_kos.viewmodel.DetailViewModel;
import com.jedu.re_kos.viewmodel.ImageKosViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailKosFragment extends Fragment {

    private TextView textViewDeskripsiProperti1, textViewLihatSemua1, namakos, lokasi, rating, riview, tersedia, harga,waktuPenyewaan;
    private TextView textViewDeskripsiProperti2, textViewLihatSemua2;
    private boolean isExpanded1 = false;
    private boolean isExpanded2 = false;
    private boolean isExpanded3 = false;
    private RecyclerView recyclerView;
    private FasilitasKosAdapter fasilitasKosAdapter;
    private List<Fasilitas> fasilitasList;
    private DetailViewModel detailViewModel;
    private ImageKosViewModel imageKosViewModel;
    ViewPager SlideViewPager;

    private String fullText1 = ""; // Menyimpan teks deskripsi properti 1 penuh
    private String fullText2 = ""; // Menyimpan teks deskripsi properti 2 penuh

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_kos, container, false);

        recyclerView = view.findViewById(R.id.fasilitas_kos);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        fasilitasList = new ArrayList<>();
        fasilitasKosAdapter = new FasilitasKosAdapter(new ArrayList<>());
        recyclerView.setAdapter(fasilitasKosAdapter);
        SlideViewPager = view.findViewById(R.id.imageKos);

        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        imageKosViewModel = new ViewModelProvider(this).get(ImageKosViewModel.class);
        namakos = view.findViewById(R.id.namakosdetail);
        lokasi = view.findViewById(R.id.textViewLokasi);
        rating = view.findViewById(R.id.textView13);
        riview = view.findViewById(R.id.textView15);
        tersedia = view.findViewById(R.id.textView16);
        harga = view.findViewById(R.id.textView17);
        waktuPenyewaan = view.findViewById(R.id.textView18);

        textViewDeskripsiProperti1 = view.findViewById(R.id.textViewDeskripsiProperti);
        textViewLihatSemua1 = view.findViewById(R.id.textViewLihatSemua);
        textViewDeskripsiProperti2 = view.findViewById(R.id.textViewDeskripsiProperti2);
        textViewLihatSemua2 = view.findViewById(R.id.textViewLihatSemua2);
        int idKos = isAdded() ? requireActivity().getIntent().getIntExtra("id_kos", -1) : -1;

        if (idKos != -1) {
            detailViewModel.getDetail(idKos).observe(getViewLifecycleOwner(), detailResponse -> {
                if (detailResponse != null && "success".equals(detailResponse.getStatus())) {
                    DetailModel detailModel = detailResponse.getDetailModel();
                    if (detailModel != null) {
                        namakos.setText(detailModel.getNama_kos() != null ? detailModel.getNama_kos() : "");
                        lokasi.setText(detailModel.getAlamat() != null ? detailModel.getAlamat() : "");
                        rating.setText(detailModel.getRating_kamar() != null ? String.valueOf(detailModel.getRating_kamar()) : "");
                        riview.setText(detailModel.getJumlah_rating() > 0 ? "(" + detailModel.getJumlah_rating() + " review)" : "(No reviews)");
                        harga.setText(detailModel.getHarga_bulan() > 0 ? "RP. " + detailModel.getHarga_bulan() : "(No Harga)");
                        tersedia.setText(detailModel.getKamar_tersedia() != 0 ? String.valueOf(detailModel.getKamar_tersedia()) : "Tidak tersedia");
                        waktuPenyewaan.setText(detailModel.getWaktu_penyewaan() != null ? detailModel.getWaktu_penyewaan() : "");

                        // Peraturan Kos dan Deskripsi Properti
                        fullText1 = detailModel.getPeraturan_kos() != null ? detailModel.getPeraturan_kos() : "";
                        fullText2 = detailModel.getKos_deskripsi() != null ? detailModel.getKos_deskripsi() : "";

                        updateDeskripsiProperti1();
                        updateDeskripsiProperti2();

                        fasilitasList = detailModel.getFasilitasList();
                        updateFasilitasList(); // Memperbarui daftar fasilitas di RecyclerView

                        // Siapkan daftar gambar dari detail kos
                        List<String> kosImageUrls = new ArrayList<>();
                        imageKosViewModel.getImageKos(String.valueOf(idKos)).observe(getViewLifecycleOwner(), response -> {
                            if (response != null && response.isSuccess()) {
                                kosImageUrls.clear();
                                if (response.getImages() != null && !response.getImages().isEmpty()) {
                                    kosImageUrls.addAll(response.getImages());

                                    // Set adapter untuk ViewPager
                                    FotoKosAdapter fotoKosAdapter = new FotoKosAdapter(requireActivity(), kosImageUrls);
                                    SlideViewPager.addOnPageChangeListener(viewListener);
                                    SlideViewPager.setAdapter(fotoKosAdapter);

                                    // Update indikator (jika ada)
                                    // setUpindicator(0, kosImageUrls.size());
                                } else {
                                    Log.d("ImageURL", "No images found in response");
                                }
                            } else {
                                Toast.makeText(requireActivity(), "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        } else {
            Log.e("DETAIL_MODEL", "DetailModel is null");
        }


        // Klik pada "Lihat Semua" untuk deskripsi properti 1
        textViewLihatSemua1.setOnClickListener(v -> {
            isExpanded1 = !isExpanded1;
            updateDeskripsiProperti1();
        });

        // Klik pada "Lihat Semua" untuk deskripsi properti 2
        textViewLihatSemua2.setOnClickListener(v -> {
            isExpanded2 = !isExpanded2;
            updateDeskripsiProperti2();
        });

        TextView textViewLihatSemua = view.findViewById(R.id.textView20);
        textViewLihatSemua.setOnClickListener(v -> {
            isExpanded3 = !isExpanded3;
            textViewLihatSemua.setText(isExpanded3 ? "Sembunyikan" : "Lihat Semua");
            updateFasilitasList();
        });

        return view;
    }

    private void updateDeskripsiProperti1() {
        if (fullText1 == null || fullText1.isEmpty()) {
            textViewDeskripsiProperti1.setText(""); // Atur menjadi teks kosong jika tidak ada data
            textViewLihatSemua1.setVisibility(View.GONE); // Sembunyikan tombol "Lihat Semua"
        } else {
            String shortText1 = fullText1.length() > 100 ? fullText1.substring(0, 100) + "..." : fullText1;
            textViewDeskripsiProperti1.setText(isExpanded1 ? fullText1 : shortText1);
            textViewLihatSemua1.setText(isExpanded1 ? "Sembunyikan" : "Lihat Semua");
        }
    }

    private void updateDeskripsiProperti2() {
        if (fullText2 == null || fullText2.isEmpty()) {
            textViewDeskripsiProperti2.setText(""); // Atur menjadi teks kosong jika tidak ada data
            textViewLihatSemua2.setVisibility(View.GONE); // Sembunyikan tombol "Lihat Semua"
        } else {
            String shortText2 = fullText2.length() > 100 ? fullText2.substring(0, 100) + "..." : fullText2;
            textViewDeskripsiProperti2.setText(isExpanded2 ? fullText2 : shortText2);
            textViewLihatSemua2.setText(isExpanded2 ? "Sembunyikan" : "Lihat Semua");
        }
    }


    private void updateFasilitasList() {
        if (fasilitasList == null || fasilitasList.isEmpty()) return;

        int itemCount = isExpanded3 ? fasilitasList.size() : Math.min(fasilitasList.size(), 4);
        List<Fasilitas> fasilitasToShow = new ArrayList<>(fasilitasList.subList(0, itemCount));
        fasilitasKosAdapter.setFasilitasList(fasilitasToShow);
        fasilitasKosAdapter.notifyDataSetChanged();
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //  setUpindicator(position, kosImageUrls.size());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
