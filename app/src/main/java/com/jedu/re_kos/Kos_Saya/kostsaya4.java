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

public class kostsaya4 extends AppCompatActivity {
private Button lanjutkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kostsaya4);

        lanjutkan = findViewById(R.id.lanjut);
        lanjutkan.setOnClickListener(view -> {
            Intent intent = new Intent (kostsaya4.this,kostsaya5.class);
            startActivities(new Intent[]{intent});
            finish();
        });

    }
}