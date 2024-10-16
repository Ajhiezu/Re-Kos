package com.jedu.re_kos.Register_Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.R;

public class RegisterasiActivity extends AppCompatActivity {

    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerasi);

        //warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(RegisterasiActivity.this, R.color.biru_navbar));

        //buttonLogin
        buttonRegister = findViewById(R.id.buttonCreateAccount);

        //setOnclick
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menuju ke registrasi
                Intent intent = new Intent(RegisterasiActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}