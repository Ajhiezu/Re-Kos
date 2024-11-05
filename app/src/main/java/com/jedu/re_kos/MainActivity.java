package com.jedu.re_kos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jedu.re_kos.Menu.CariFragment;
import com.jedu.re_kos.Menu.ChatFragment;
import com.jedu.re_kos.Menu.KosSayaFragment;
import com.jedu.re_kos.Menu.ProfilFragment;
import com.jedu.re_kos.databinding.ActivityMainBinding;
import com.jedu.re_kos.viewmodel.DataViewModel;


public class MainActivity extends AppCompatActivity {
    private ViewModel viewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(DataViewModel.class);

        //warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.biru_navbar));

        // Set the default fragment when the activity is created
        replaceFragment(new CariFragment());

        // Handle navigation item selection
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.Cari) {
                replaceFragment(new CariFragment());
            } else if (itemId == R.id.Chat) {
                replaceFragment(new ChatFragment());
            } else if (itemId == R.id.kosSaya) {
                replaceFragment(new KosSayaFragment());
            } else if (itemId == R.id.Profil) {
                replaceFragment(new ProfilFragment());
            }

            return true;
        });
    }

    // Method to replace fragments
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
}
