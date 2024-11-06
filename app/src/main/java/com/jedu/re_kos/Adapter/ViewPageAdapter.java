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

public class ViewPageAdapter extends PagerAdapter {

    private final Context context;

    // Menyediakan gambar, heading, dan deskripsi
    private final int[] images = {
            R.drawable.onboarding,
            R.drawable.onboarding,
            R.drawable.onboarding,// Pastikan menambah gambar yang berbeda
            // Tambahkan lebih banyak gambar jika perlu
    };

    private final int[] headings = {
            R.string.heading_one,
            R.string.heading_one,
            R.string.heading_one
            // Tambahkan lebih banyak heading jika perlu
    };

    private final int[] descriptions = {
            R.string.deskripsi_one,
            R.string.deskripsi_one,
            R.string.deskripsi_one
            // Tambahkan lebih banyak deskripsi jika perlu
    };

    public ViewPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length; // Mengembalikan jumlah gambar
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object; // Memastikan bahwa view berasal dari object
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImage = view.findViewById(R.id.imageViewSlide);
        TextView slideTitle = view.findViewById(R.id.textViewTitle);
        TextView slideDescription = view.findViewById(R.id.textViewDeskripsi);

        // Mengatur gambar, heading, dan deskripsi berdasarkan posisi
        slideImage.setImageResource(images[position]);
        slideTitle.setText(headings[position]);
        slideDescription.setText(descriptions[position]);

        container.addView(view); // Menambahkan view ke container

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object); // Menghapus view dari container
    }
}
