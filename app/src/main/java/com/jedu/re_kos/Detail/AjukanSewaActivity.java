package com.jedu.re_kos.Detail;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.jedu.re_kos.Adapter.FotoKosAdapter;
import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.R;
import com.jedu.re_kos.databinding.ActivityAjukanSewaBinding;
import com.jedu.re_kos.Model.DetailModel;
import com.jedu.re_kos.viewmodel.DetailViewModel;
import com.jedu.re_kos.viewmodel.ImageKosViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AjukanSewaActivity extends AppCompatActivity {

    private ActivityAjukanSewaBinding binding;
    String[] item = {"Harian", "Mingguan", "Bulanan"};
    ArrayAdapter<String> adapterItems;
    private Button buttonPilihPembayaran,konfirmasi,add,remove;
    ViewPager SlideViewPager;
    LinearLayout DotLayout;
    TextView[] dots;
    private ImageView imageViewPreview;
    FotoKosAdapter fotoKosAdapter;
    private TextView editTextTanggalPemesanan,namakos,alamat,rating,review,tersedia,harga,waktuPenyewaan,rincianharga,fee,total,TextTanggalPemesananDay;
    private EditText jumlahkos,editTextTanggalPemesananDay;
    private static final int FILE_SELECT_CODE = 1;
    private Uri selectedFileUri;
    private DetailViewModel detailViewModel;
    private ImageKosViewModel imageKosViewModel;
    private int counter = 1; // Counter untuk auto-increment
    private static final int MAX_ID = 1000; // Batas maksimal angka auto-increment
    private AutoCompleteTextView durasiSewa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(AjukanSewaActivity.this, R.color.biru_navbar));

        // Inisialisasi binding
        binding = ActivityAjukanSewaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //findbyid
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        imageKosViewModel = new ViewModelProvider(this).get(ImageKosViewModel.class);
        namakos = findViewById(R.id.NamaKos);
        alamat = findViewById(R.id.lokasiajukansewa);
        rating = findViewById(R.id.ratingajukansewa);
        review = findViewById(R.id.reviewajukansewa);
        tersedia = findViewById(R.id.tersediaajukansewa);
        harga = findViewById(R.id.hargaajukansewa);
        waktuPenyewaan = findViewById(R.id.waktupenyewaanajukansewa);
        SlideViewPager = findViewById(R.id.imageKos);
        DotLayout = findViewById(R.id.dotslideKos);
        rincianharga = findViewById(R.id.rincianhargakos);
        fee = findViewById(R.id.textView28);
        total = findViewById(R.id.textView31);
        jumlahkos = findViewById(R.id.jumlahkos);
        add = findViewById(R.id.plus);
        remove = findViewById(R.id.minus);
        editTextTanggalPemesananDay = findViewById(R.id.editTextTanggalPemesananDay);
        TextTanggalPemesananDay = findViewById(R.id.textView20Day);
        durasiSewa = findViewById(R.id.autoCompleteTextView);
        int idKos = getIntent().getIntExtra("id_kos", -1);

        if (idKos != -1) {
            // Panggil detailViewModel.getDetail dengan id_kos
            detailViewModel.getDetail(idKos).observe(this, detailResponse -> {
                if (detailResponse != null) {
                    if ("success".equals(detailResponse.getStatus())) {
                        DetailModel detailModel = detailResponse.getDetailModel();
                        if (detailModel != null) {
                            namakos.setText(detailModel.getNama_kos() != null ? detailModel.getNama_kos() : "");
                            alamat.setText(detailModel.getAlamat() != null ? detailModel.getAlamat() : "");
                            rating.setText(detailModel.getRating_kamar() != null ? String.valueOf(detailModel.getRating_kamar()) : "");
                            if (detailModel != null) {
                                int jumlahRating = detailModel.getJumlah_rating();
                                review.setText(jumlahRating > 0 ? "(" + jumlahRating + " review)" : "(No reviews)");
                            } else {
                                review.setText("(No reviews)");
                            }
                            if (detailModel != null) {
                                int Tersedia = detailModel.getKamar_tersedia();
                                tersedia.setText(Tersedia > 0 ? Tersedia + " Tersedia" : "(No reviews)");
                            } else {
                                tersedia.setText("(Tidak Tersedia)");
                            }
                            final int[] value = {1}; // Gunakan array untuk menyimpan nilai, default 1

                            final int hargaSatuan = detailModel != null ? detailModel.getHarga_bulan() : 0; // Harga satuan

                            // Set nilai awal di TextView
                            jumlahkos.setText(String.valueOf(value[0])); // Set jumlah kamar ke 1
                            jumlahkos.setFocusable(false);
                            jumlahkos.setClickable(true);
                            int totalHargaAwal = value[0] * hargaSatuan; // Hitung total harga awal
                            int feeAwal = (int) (totalHargaAwal * 0.1); // Biaya tambahan awal (10%)
                            rincianharga.setText("Rp" + String.format("%,d", totalHargaAwal).replace(",", ".")); // Update rincian harga awal
                            fee.setText("Rp " + feeAwal); // Update fee awal
                            total.setText("Rp " + (totalHargaAwal + feeAwal)); // Update total awal (harga + fee)

                            // Event Listener untuk tombol Add
                            add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    value[0]++; // Tambahkan nilai
                                    jumlahkos.setText(String.valueOf(value[0])); // Update TextView jumlah kamar
                                    int totalHarga = value[0] * hargaSatuan; // Hitung total harga
                                    int feeAmount = (int) (totalHarga * 0.1); // Hitung biaya tambahan
                                    rincianharga.setText("Rp" + String.format("%,d", totalHarga).replace(",", ".")); // Update rincian harga
                                    fee.setText("Rp " + feeAmount); // Update fee
                                    total.setText("Rp " + (totalHarga + feeAmount)); // Update total
                                }
                            });

                            // Event Listener untuk tombol Remove
                            remove.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (value[0] > 1) { // Cegah nilai menjadi kurang dari 1
                                        value[0]--; // Kurangi nilai
                                        jumlahkos.setText(String.valueOf(value[0])); // Update TextView jumlah kamar
                                        int totalHarga = value[0] * hargaSatuan; // Hitung total harga
                                        int feeAmount = (int) (totalHarga * 0.1); // Hitung biaya tambahan
                                        rincianharga.setText("Rp" + String.format("%,d", totalHarga).replace(",", ".")); // Update rincian harga
                                        fee.setText("Rp " + feeAmount); // Update fee
                                        total.setText("Rp " + (totalHarga + feeAmount)); // Update total
                                    }
                                }
                            });


                            // Jika detailModel tidak null, inisialisasi tampilan awal
                            if (detailModel != null) {
                                int Harga = detailModel.getHarga_bulan();
                                harga.setText(Harga > 0 ? "Rp " + Harga : "Rp -");
                                rincianharga.setText(Harga > 0 ? "Rp " + Harga : "Rp -");
                                int amount = (int) (Harga * 0.1);
                                fee.setText("Rp " + amount);
                                int totalharga = Harga + amount;
                                total.setText("Rp " + totalharga);
                            } else {
                                harga.setText("Rp 0");
                            }

                            waktuPenyewaan.setText(detailModel.getWaktu_penyewaan() != null ? detailModel.getWaktu_penyewaan() : "");
                            // Siapkan daftar gambar dari detail kos
                            List<String> kosImageUrls = new ArrayList<>();
                            imageKosViewModel.getImageKos(String.valueOf(idKos)).observe(this, response -> {
                                if (response != null && response.isSuccess()) {
                                    // Kosongkan daftar sebelumnya
                                    kosImageUrls.clear();

                                    // Tambahkan URL gambar dari respons ke daftar
                                    kosImageUrls.addAll(response.getImages());

                                    // Jika Anda ingin menampilkan log atau debugging
                                    for (String url : kosImageUrls) {
                                        Log.d("ImageURL", url);
                                    }

                                    // Set adapter untuk ViewPager
                                    FotoKosAdapter fotoKosAdapter = new FotoKosAdapter(AjukanSewaActivity.this, kosImageUrls);
                                    SlideViewPager.addOnPageChangeListener(viewListener);
                                    SlideViewPager.setAdapter(fotoKosAdapter);

                                    // Update indikator (jika ada)
                                    //setUpindicator(0, kosImageUrls.size());
                                } else {
                                    // Jika gagal memuat data, tampilkan pesan kesalahan
                                    Toast.makeText(AjukanSewaActivity.this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
                                }
                            });



                        } else {
                            Log.e("DETAIL_MODEL", "DetailModel is null");
                        }
                    }
                }
            });
        } else {
            Log.e("BUTTON_SEWA", "Invalid id_kos received");
        }

        // Inisialisasi AutoCompleteTextView
        adapterItems = new ArrayAdapter<>(this, R.layout.list_items, item);

        // Set adapter ke AutoCompleteTextView
        durasiSewa.setAdapter(adapterItems);

        // Set listener untuk AutoCompleteTextView
        durasiSewa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AjukanSewaActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        editTextTanggalPemesanan = findViewById(R.id.editTextTanggalPemesanan);
        // Nonaktifkan fokus dan tetap dapat diklik
        editTextTanggalPemesanan.setFocusable(false);
        editTextTanggalPemesanan.setClickable(true);
        editTextTanggalPemesanan.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        editTextTanggalPemesananDay.setFocusable(false);
        editTextTanggalPemesananDay.setClickable(true);
        editTextTanggalPemesananDay.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        //Button Pilih Pembayaran
        buttonPilihPembayaran = findViewById(R.id.buttonPilihPembayaran);
        buttonPilihPembayaran.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // Gunakan "image/*" jika hanya ingin gambar
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Pilih File"), FILE_SELECT_CODE);
        });

        imageViewPreview = findViewById(R.id.imageViewPreview);
        konfirmasi = findViewById(R.id.btnkonfirmasi);
        editTextTanggalPemesananDay.setVisibility(View.GONE);
        TextTanggalPemesananDay.setVisibility(View.GONE);

        durasiSewa.setOnItemClickListener((parent, view, position, id) -> {
            String durasi = parent.getItemAtPosition(position).toString().trim();

            if (durasi.equalsIgnoreCase("Harian")) {
                // Tampilkan elemen tambahan untuk Harian
                TextTanggalPemesananDay.setVisibility(View.VISIBLE);
                editTextTanggalPemesananDay.setVisibility(View.VISIBLE);
            } else {
                // Sembunyikan elemen tambahan
                TextTanggalPemesananDay.setVisibility(View.GONE);
                editTextTanggalPemesananDay.setVisibility(View.GONE);
            }
        });

        konfirmasi.setOnClickListener(v -> {
            if (selectedFileUri != null) {
                // Pastikan semua input valid
                if (validateInputs()) {
                    // Ambil data input
                    int id_user = getUserId();
                    Log.e("id_user", String.valueOf(id_user));
                    int id_kos = getIntent().getIntExtra("id_kos", -1);
                    String harga = total.getText().toString().replace("Rp ", "").trim();
                    int hargaInt = Integer.parseInt(harga);
                    String waktu_sewa = editTextTanggalPemesanan.getText().toString().trim();
                    int jumlahKamar = Integer.parseInt(jumlahkos.getText().toString());
                    String durasi = durasiSewa.getText().toString().trim();

                    // Hitung jumlah hari berdasarkan durasi
                    int hari = durasi.equalsIgnoreCase("Harian") ? 1 :
                            durasi.equalsIgnoreCase("Mingguan") ? 7 :
                                    durasi.equalsIgnoreCase("Bulanan") ? 30 : 0;

                    // Validasi durasi
                    if (hari == 0) {
                        Toast.makeText(this, "Durasi tidak valid", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Hitung tanggal berdasarkan hari
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String tanggalAkhirString;
                    try {
                        Date dateAwal = dateFormat.parse(waktu_sewa); // Parsing dari EditText
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dateAwal);
                        calendar.add(Calendar.DAY_OF_MONTH, hari); // Tambahkan hari
                        tanggalAkhirString = dateFormat.format(calendar.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Format tanggal tidak valid", Toast.LENGTH_SHORT).show();
                        return; // Keluar jika format tanggal salah
                    }

                    // Ambil ID kamar dari ViewModel
                    detailViewModel.getDetail(id_kos).observe(this, detailResponse -> {
                        if (detailResponse != null && "success".equals(detailResponse.getStatus())) {
                            DetailModel detailModel = detailResponse.getDetailModel();
                            if (detailModel != null) {
                                int idKamar = detailModel.getId_kamar();

                                // Konversi file URI ke MultipartBody.Part
                                MultipartBody.Part buktiPembayaran = prepareFilePart("buktiPembayaran", selectedFileUri);

                                // RequestBody untuk data lainnya
                                RequestBody idUserPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_user));
                                Log.e("idUser", String.valueOf(id_user));
                                RequestBody idKamarPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idKamar));
                                Log.e("idKamar", String.valueOf(idKamar));
                                RequestBody hargaPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(hargaInt));
                                Log.e("harga", String.valueOf(harga));
                                RequestBody idKosPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_kos));
                                Log.e("idkos", String.valueOf(id_kos));
                                RequestBody totalKamar = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(jumlahKamar));
                                Log.e("totalkamar", String.valueOf(jumlahKamar));
                                RequestBody durasiPart = RequestBody.create(MediaType.parse("text/plain"), durasi);
                                Log.e("durasi", (durasi));
                                RequestBody tanggalPart = RequestBody.create(MediaType.parse("text/plain"), tanggalAkhirString);
                                Log.e("tanggalpart", (tanggalAkhirString));
                                RequestBody waktuSewaPart = RequestBody.create(MediaType.parse("text/plain"), waktu_sewa);
                                Log.e("waktusewa", (waktu_sewa));

                                // Kirim permintaan pembayaran ke server
                                detailViewModel.konfirmPay(idUserPart, idKamarPart, idKosPart, totalKamar, durasiPart, hargaPart, tanggalPart, waktuSewaPart, buktiPembayaran)
                                        .observe(this, pembayaranResponse -> {
                                            if (pembayaranResponse != null && "success".equals(pembayaranResponse.getStatus())) {
                                                // Tampilkan notifikasi sukses
                                                Toast.makeText(this, "Pembayaran berhasil dikonfirmasi!", Toast.LENGTH_SHORT).show();
                                                // Pindah ke layar utama
                                                Intent intent = new Intent(this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                // Tampilkan pesan kesalahan
                                                Toast.makeText(this, pembayaranResponse != null ? pembayaranResponse.getMessage() : "Kesalahan server", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(this, "Detail kamar tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Gagal mengambil detail kamar", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(this, "Harap pilih dan unggah bukti pembayaran terlebih dahulu!", Toast.LENGTH_SHORT).show();
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

private void toggleTanggalVisibility(boolean isVisible) {
    int visibility = isVisible ? View.VISIBLE : View.INVISIBLE;
    TextTanggalPemesananDay.setVisibility(visibility);
    editTextTanggalPemesanan.setVisibility(visibility);
}


    // Fungsi validasi input
    private boolean validateInputs() {
        if (rincianharga.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Harga tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (waktuPenyewaan.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Waktu penyewaan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (getIntent().getIntExtra("id_kos", -1) == -1) {
            Toast.makeText(this, "ID Kos tidak valid!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Fungsi untuk konversi file URI ke MultipartBody.Part
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        String filePath = getRealPathFromURI(fileUri);
        if (filePath != null) {
            File file = new File(filePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        } else {
            RequestBody requestFile = createRequestBodyFromUri(fileUri);
            return MultipartBody.Part.createFormData(partName, "upload.jpg", requestFile);
        }
    }


    // Fungsi untuk mendapatkan path file sebenarnya dari URI
    private String getRealPathFromURI(Uri uri) {
        // Periksa apakah URI adalah dokumen
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            String[] split = documentId.split(":");
            String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        }

        // URI dari MediaStore
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                try {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.moveToFirst()) {
                        return cursor.getString(columnIndex);
                    }
                } finally {
                    cursor.close();
                }
            }
        }

        // Default: gunakan getPath()
        return uri.getPath();
    }

    private RequestBody createRequestBodyFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            byte[] bytes = getBytesFromInputStream(inputStream);
            return RequestBody.create(MediaType.parse("image/*"), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }



    private int getUserId() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);
        if(userId != 0){
            return userId;
        } else {
            return 0;
        }
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
                String formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                editTextTanggalPemesanan.setText(formattedDate);
                editTextTanggalPemesananDay.setText(formattedDate);
            }
        }, year, month, day);

        // Menampilkan dialog
        datePickerDialog.show();
    }

//    public void setUpindicator(int position,  int totalImages){
//        dots = new TextView[totalImages];
//        DotLayout.removeAllViews();
//
//        for (int i = 0; i < dots.length; i++){
//
//            dots[i] = new TextView(this);
//            dots[i].setText(Html.fromHtml("&#8226;"));
//            dots[i].setTextSize(35);
//            dots[i].setTextColor(getResources().getColor(R.color.white, getApplicationContext().getTheme()));
//            DotLayout.addView(dots[i]);
//        }
//
//        if (dots.length > 0 && position < dots.length) {
//            dots[position].setTextColor(getResources().getColor(R.color.biru_navbar, getApplicationContext().getTheme()));
//        }
//    }

    // Override onActivityResult untuk menangani hasil pilihan file
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData(); // Simpan URI file yang dipilih
            if (selectedFileUri != null) {
                // Tampilkan pratinjau di ImageView jika file adalah gambar
                if (getContentResolver().getType(selectedFileUri).startsWith("image/")) {
                    imageViewPreview.setImageURI(selectedFileUri);
                }
            }
        }
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
