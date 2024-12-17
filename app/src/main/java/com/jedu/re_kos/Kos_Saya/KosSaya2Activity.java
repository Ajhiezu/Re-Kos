package com.jedu.re_kos.Kos_Saya;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jedu.re_kos.R;

public class KosSaya2Activity extends AppCompatActivity {

    private Button lanjut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kos_saya2);

        lanjut = findViewById(R.id.lanjut);
        lanjut.setOnClickListener(view -> {
            Intent intent = new Intent(KosSaya2Activity.this, KosSaya3Activity.class);
            startActivities(new Intent[]{intent});
            finish();
        });
    }//test push
}