package com.jedu.re_kos.Splash_Screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.R;
import com.jedu.re_kos.Register_Login.LoginActivity;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        //Menyembunyikan status bar untuk layanan penuh
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        //Menunda selama 6 detik sebelum pindah ke MainActivity
        new Handler(Looper.myLooper()).postDelayed(() -> {
            //pindah ke MainActivity
            Intent intent = new Intent(Splash_Screen.this, LoginActivity.class);
            startActivity(intent);
            finish();
        },3000);
    }
}