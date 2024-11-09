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

public class KosSaya3Activity extends AppCompatActivity {
    private Button lanjutkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_kos_saya3);
        lanjutkan = findViewById(R.id.lanjutkan);
        lanjutkan.setOnClickListener(view -> {
            Intent intent = new Intent(KosSaya3Activity.this, kostsaya4.class);
            startActivities(new Intent[]{intent});
            finish();
        });
    }
}