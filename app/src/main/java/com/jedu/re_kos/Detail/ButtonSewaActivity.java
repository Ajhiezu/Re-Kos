package com.jedu.re_kos.Detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.jedu.re_kos.Adapter.FotoKosAdapter;
import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.Menu.CariFragment;
import com.jedu.re_kos.Model.DetailModel;
import com.jedu.re_kos.Model.KosModel;
import com.jedu.re_kos.Model.response.DetailResponse;
import com.jedu.re_kos.PesanActivity;
import com.jedu.re_kos.R;
import com.jedu.re_kos.databinding.ActivityButtonSewaBinding;
import com.jedu.re_kos.databinding.ActivityMainBinding;
import com.jedu.re_kos.viewmodel.DetailViewModel;
import com.jedu.re_kos.viewmodel.KosViewModel;

import java.util.ArrayList;
import java.util.List;

public class ButtonSewaActivity extends AppCompatActivity {

    ActivityButtonSewaBinding binding;
    private DetailViewModel detailViewModel;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityButtonSewaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the default fragment when the activity is created
        replaceFragment(new DetailKosFragment());

        //warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(ButtonSewaActivity.this, R.color.biru_navbar));

        // Find buttons by their IDs
        Button buttonAjukanSewa = findViewById(R.id.buttonAjukanSewa);
        Button buttonTanyaPemilik = findViewById(R.id.buttonTanyaPemilik);
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        // Ambil id_kos dari Intent
        int idKos = getIntent().getIntExtra("id_kos", -1);
        Log.d("idKos", "id kos: " + idKos);

        if (idKos != -1) {
            // Panggil detailViewModel.getDetail dengan id_kos
            detailViewModel.getDetail(idKos).observe(this, detailResponse -> {
                if (detailResponse != null && "success".equals(detailResponse.getStatus())) {
                    int idPemilik = detailResponse.getDetailModel().getId_pemilik();
                    Log.d("idPemilik", "id Pemilik: " + idPemilik);

                    // Set onClickListener setelah mendapatkan idPemilik
                    buttonTanyaPemilik.setOnClickListener(v -> {
                        Intent intent = new Intent(ButtonSewaActivity.this, PesanActivity.class);
                        intent.putExtra("id_user", String.valueOf(idPemilik)); // Kirim idPemilik sebagai String
                        startActivity(intent);
                    });
                } else {
                    Log.e("DETAIL_RESPONSE", "DetailResponse is null or failed");
                }
            });
        } else {
            Log.e("BUTTON_SEWA", "Invalid id_kos received");
            // Default behavior if idKos is invalid
            buttonTanyaPemilik.setOnClickListener(v -> {
                Log.e("BUTTON_TANYA_PEMILIK", "idPemilik is unavailable due to invalid idKos");
            });
        }


        // Set click listener for buttonAjukanSewa
        buttonAjukanSewa.setOnClickListener(view -> {
            if (idKos != -1) {
                // Buka AjukanSewaActivity dengan membawa id_kos
                Intent intent = new Intent(ButtonSewaActivity.this, AjukanSewaActivity.class);
                intent.putExtra("id_kos", idKos);
                startActivity(intent);
            } else {
                Log.e("BUTTON_SEWA", "Cannot open AjukanSewaActivity, id_kos is invalid");
            }
        });

//        // Set click listener for button2
//        buttonTanyaPemilik.setOnClickListener(view -> {
//            replaceFragment(new TanyaPemilikFragment());
//        });
    }
    // Method to replace fragments
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
}