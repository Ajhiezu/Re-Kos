package com.jedu.re_kos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.jedu.re_kos.R;

public class FotoKosAdapter extends PagerAdapter {

    private final Context context;

    // Menyediakan gambar, heading, dan deskripsi
    private final int[] images = {
            R.drawable.gambar_kos2,
            R.drawable.gambar_kos2,
            R.drawable.gambar_kos2,// Pastikan menambah gambar yang berbeda
            // Tambahkan lebih banyak gambar jika perlu
    };

    public FotoKosAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_foto_kos, container, false);

        ImageView slideImage = view.findViewById(R.id.imageFotoKos);

        // Mengatur gambar, heading, dan deskripsi berdasarkan posisi
        slideImage.setImageResource(images[position]);

        container.addView(view); // Menambahkan view ke container

        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object); // Menghapus view dari container
    }
}
