package com.jedu.re_kos.Detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.Menu.CariFragment;
import com.jedu.re_kos.R;
import com.jedu.re_kos.databinding.ActivityButtonSewaBinding;
import com.jedu.re_kos.databinding.ActivityMainBinding;

public class ButtonSewaActivity extends AppCompatActivity {

    ActivityButtonSewaBinding binding;

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
        // Ambil id_kos dari Intent
        int idKos = getIntent().getIntExtra("id_kos", -1);

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