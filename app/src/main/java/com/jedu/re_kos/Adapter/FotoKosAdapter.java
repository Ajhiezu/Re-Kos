package com.jedu.re_kos.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.jedu.re_kos.R;

import java.util.List;

// Ubah constructor pada FotoKosAdapter untuk menerima daftar gambar berdasarkan id_kos
public class FotoKosAdapter extends PagerAdapter {

    private final Context context;
    private final List<String> kosImageUrls;

    public FotoKosAdapter(Context context, List<String> kosImageUrls) {
        this.context = context;
        this.kosImageUrls = kosImageUrls;
    }

    @Override
    public int getCount() {
        return kosImageUrls.size();
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

        // Dapatkan URL gambar berdasarkan id_kos
        String imageUrl = kosImageUrls.get(position);

        // Gunakan Glide untuk memuat gambar
        Glide.with(context)
                .load(imageUrl)
                .into(slideImage);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
