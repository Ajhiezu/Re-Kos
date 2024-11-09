package com.jedu.re_kos.Detail;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.jedu.re_kos.Adapter.FotoKosAdapter;
import com.jedu.re_kos.Menu.CariFragment;
import com.jedu.re_kos.R;
import com.jedu.re_kos.databinding.ActivityAjukanSewaBinding;

import java.util.Calendar;

public class AjukanSewaActivity extends AppCompatActivity {

    private ActivityAjukanSewaBinding binding;
    String[] item = {"Harian", "Mingguan", "Bulanan", "3 Bulan", "6 Bulan", "1 Tahun"};
    ArrayAdapter<String> adapterItems;
    private Button buttonPilihPembayaran;
    ViewPager SlideViewPager;
    LinearLayout DotLayout;
    TextView[] dots;
    private ImageView imageViewPreview;
    FotoKosAdapter fotoKosAdapter;
    private TextView editTextTanggalPemesanan;
    private static final int FILE_SELECT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(AjukanSewaActivity.this, R.color.biru_navbar));

        // Inisialisasi binding
        binding = ActivityAjukanSewaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editTextTanggalPemesanan = findViewById(R.id.editTextTanggalPemesanan);
        // Nonaktifkan fokus dan tetap dapat diklik
        editTextTanggalPemesanan.setFocusable(false);
        editTextTanggalPemesanan.setClickable(true);
        editTextTanggalPemesanan.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        SlideViewPager = findViewById(R.id.imageKos);
        DotLayout = findViewById(R.id.dotslideKos);
        fotoKosAdapter = new FotoKosAdapter(this);

        SlideViewPager.setAdapter(fotoKosAdapter);

        setUpindicator(0);
        SlideViewPager.addOnPageChangeListener(viewListener);

        //Button Pilih Pembayaran
        buttonPilihPembayaran = findViewById(R.id.buttonPilihPembayaran);
        buttonPilihPembayaran.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // Gunakan "image/*" jika hanya ingin gambar
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Pilih File"), FILE_SELECT_CODE);
        });

        // Inisialisasi AutoCompleteTextView
        AutoCompleteTextView autoCompleteTextView = binding.autoCompleteTextView;
        adapterItems = new ArrayAdapter<>(this, R.layout.list_items, item);

        // Set adapter ke AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapterItems);

        // Set listener untuk AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AjukanSewaActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        // Temukan ImageView berdasarkan id
        ImageView imageViewShare = binding.imageViewShare;
        ImageView imageViewBack = binding.imageViewBack;

        // Set OnClickListener kembali
        imageViewBack.setOnClickListener(view -> {
            onBackPressed();
        });

        // Set OnClickListener pada ImageView untuk share
        imageViewShare.setOnClickListener(view -> {
            // Buat intent untuk share
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Bagikan ini");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Ini adalah konten yang akan dibagikan");

            // Memulai activity share
            startActivity(Intent.createChooser(shareIntent, "Bagikan melalui"));
        });
    }
    private void showDatePickerDialog() {
        // Mendapatkan tanggal saat ini
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Membuat DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                // Format tanggal yang dipilih dan set ke EditText
                String formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                editTextTanggalPemesanan.setText(formattedDate);
            }
        }, year, month, day);

        // Menampilkan dialog
        datePickerDialog.show();
    }

    public void setUpindicator(int position){
        dots = new TextView[3];
        DotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.white, getApplicationContext().getTheme()));
            DotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.biru_navbar, getApplicationContext().getTheme()));
    }

    // Override onActivityResult untuk menangani hasil pilihan file
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri selectedFileUri = data.getData();

                // Tampilkan URI atau lakukan sesuatu dengan file yang dipilih
                imageViewPreview.setImageURI(selectedFileUri); // Menampilkan file jika itu gambar
            }
            }
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
