package com.jedu.re_kos.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jedu.re_kos.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class IklanPageAdapter extends RecyclerView.Adapter<IklanPageAdapter.SlideViewHolder> {

    private List<SlideItemIklan> slideItemIklans;

    public IklanPageAdapter(List<SlideItemIklan> slideItemIklans) {
        this.slideItemIklans = slideItemIklans;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_iklan, parent, false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.setImageView(slideItemIklans.get(position));

        // Jika posisi adalah sebelum item terakhir
        if (position == slideItemIklans.size() - 2) {
            holder.itemView.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return slideItemIklans.size();
    }

    class SlideViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;

        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageIklan);
        }

        void setImageView(SlideItemIklan slideItemIklan) {
            imageView.setImageResource(slideItemIklan.getImage());
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Jika ingin menambahkan lebih banyak data, lakukan di sini
            // Misalnya, menambah elemen baru atau memuat lebih banyak dari sumber lain
            // slideItemIklans.add(newItem);
            // notifyDataSetChanged(); // Ini akan memicu pembaruan tampilan
        }
    };
}
