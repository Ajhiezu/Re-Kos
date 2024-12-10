package com.jedu.re_kos.Notifikasi;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.jedu.re_kos.Model.Notifikasi;
import com.jedu.re_kos.R;
import com.jedu.re_kos.network.ApiService;
import com.jedu.re_kos.network.RetrofitInstance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifikasiActivity extends AppCompatActivity {

    ListView listView;
    SimpleAdapter simpleAdapter;
    private ImageView back;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    private boolean[] clickedStatus;
    private ApiService apiService;

    private void setupApiService() {
        apiService = RetrofitInstance.createService(ApiService.class);
    }

    private void fetchNotifikasi() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);

        if (userId == 0) {
            Toast.makeText(NotifikasiActivity.this, "User belum login", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.getNotifikasi(userId).enqueue(new Callback<List<Notifikasi>>() {
            @Override
            public void onResponse(Call<List<Notifikasi>> call, Response<List<Notifikasi>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_RESPONSE", "Response: " + new Gson().toJson(response.body()));
                    List<Notifikasi> notifikasiList = response.body();

                    for (Notifikasi notif : notifikasiList) {
                        if (notif.getJumlahPembayaran() > 0) {
                            notif.setTipeNotifikasi("pembayaran");
                        } else if (notif.getSisaHari() <= 3 && notif.getSisaHari() > 0) {
                            notif.setTipeNotifikasi("sewa_habis");
                        } else {
                            notif.setTipeNotifikasi("lainnya");
                        }
                    }

                    tampilkanNotifikasi(notifikasiList);
                } else {
                    Toast.makeText(NotifikasiActivity.this, "Gagal memuat notifikasi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Notifikasi>> call, Throwable t) {
                Toast.makeText(NotifikasiActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        // Warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(NotifikasiActivity.this, R.color.biru_navbar));

        back = findViewById(R.id.imageBack);
        back.setOnClickListener(v -> onBackPressed());

        listView = findViewById(R.id.Notif);
        setupApiService();
        fetchNotifikasi();
    }

    private void tampilkanNotifikasi(List<Notifikasi> notifikasiList) {
        arrayList.clear();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat relativeDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (Notifikasi notif : notifikasiList) {

            if (notif.getJumlahPembayaran() > 0) {
                HashMap<String, String> map = new HashMap<>();
                map.put("title", "Pembayaran Berhasil");
                map.put("deskripsi", "Pembayaran kost sebesar Rp " + notif.getJumlahPembayaran() + " telah dikonfirmasi.");
                try {
                    map.put("date", relativeDateFormat.format(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(notif.getTanggalPembayaran())));
                } catch (Exception e) {
                    map.put("date", "Tanggal tidak valid");
                }
                arrayList.add(map);
            }

            if (notif.getSisaHari() <= 3 && notif.getSisaHari() >= 0) {
                HashMap<String, String> map = new HashMap<>();
                map.put("title", "Masa Sewa Hampir Habis");
                map.put("deskripsi", notif.getSisaHari() == 0 ? "Hari ini" : "Dalam " + notif.getSisaHari() + " hari");
                map.put("date", "Jika masa sewa Anda sudah diperpanjang, Anda dapat mengabaikan pesan ini.");
                arrayList.add(map);
            }

//            if (notif.getJumlahPembayaran() > 0) {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("title", "Pembayaran Berhasil");
//                map.put("deskripsi", "Pembayaran kost sebesar Rp " + notif.getJumlahPembayaran() + " telah dikonfirmasi.");
//                try {
//                    map.put("date", relativeDateFormat.format(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(notif.getTanggalPembayaran())));
//                } catch (Exception e) {
//                    map.put("date", "Tanggal tidak valid");
//                }
//                arrayList.add(map);
//            }

//            if (notif.getSisaHari() > 3 && notif.getJumlahPembayaran() <= 0) {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("title", "Notifikasi Lainnya");
//                map.put("deskripsi", "Ada notifikasi lain.");
//                map.put("date", ""); // Tidak ada waktu spesifik
//                arrayList.add(map);
//            }
        }

        simpleAdapter = new SimpleAdapter(
                NotifikasiActivity.this,
                arrayList,
                R.layout.notifikasi_adapter,
                new String[]{"title", "date", "deskripsi"},
                new int[]{R.id.textViewTitle, R.id.textViewDate, R.id.textViewDeskirpsi}
        );
        listView.setAdapter(simpleAdapter);


    }






}
