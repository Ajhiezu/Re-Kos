package com.jedu.re_kos;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SemuaKosActivity1 extends AppCompatActivity {

    // Declare the views that will be used in the layout
    private ImageView gambarkos;
    private ImageView imageView11;
    private ImageView bintang1, bintang2, bintang3, bintang4, bintang5;
    private TextView tagKos, namakos, lokasi, rating, harga, perbulan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewholder_card_view); // Use the correct layout file name

        // Initialize the views using findViewById
        gambarkos = findViewById(R.id.gambarkos);
        imageView11 = findViewById(R.id.imageView11);
        bintang1 = findViewById(R.id.bintang1);
        bintang2 = findViewById(R.id.bintang2);
        bintang3 = findViewById(R.id.bintang3);
        bintang4 = findViewById(R.id.bintang4);
        bintang5 = findViewById(R.id.bintang5);
        tagKos = findViewById(R.id.tag_kos);
        namakos = findViewById(R.id.namakos);
        lokasi = findViewById(R.id.lokasi);
        rating = findViewById(R.id.rating);
        harga = findViewById(R.id.harga);
        perbulan = findViewById(R.id.perbulan);

        // Set text for the TextViews
        tagKos.setText("Tag Kos");
        namakos.setText("Nama Kos");
        lokasi.setText("Lokasi");
        rating.setText("0.0");
        harga.setText("IDR 500,000");
        perbulan.setText("/ Bulan");

        // Example of setting a click listener (for the ImageView)
        gambarkos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle image click action
            }
        });

        // Example of manipulating the star rating (bintang1 to bintang5)
        bintang1.setVisibility(View.VISIBLE); // Show stars
        bintang2.setVisibility(View.VISIBLE);
        bintang3.setVisibility(View.VISIBLE);
        bintang4.setVisibility(View.VISIBLE);
        bintang5.setVisibility(View.VISIBLE);

        // Set ratings dynamically if needed
        // e.g., change the visibility based on a rating score
    }
}
