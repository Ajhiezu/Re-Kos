package com.jedu.re_kos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.jedu.re_kos.Domain.DetailKosDomain;
import com.jedu.re_kos.R;
import com.jedu.re_kos.databinding.FasilitasKosBinding;
import com.jedu.re_kos.databinding.ViewholderCardViewBinding;

import java.util.ArrayList;

public class DetailKosAdapter extends RecyclerView.Adapter<DetailKosAdapter.Viewholder> {
    ArrayList<DetailKosDomain> items;
    Context context;
    ViewholderCardViewBinding binding;

    public DetailKosAdapter(ArrayList<DetailKosDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCardViewBinding binding = ViewholderCardViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.binding.NamaFasilitas.setText(items.get(position).getNamaFasilitas());

        int drawableResource = holder.itemView.getResources().getIdentifier(items.get(position).getImageDetailKos(),"drawable",holder.itemView.getContext().getPackageName());
        holder.binding.GambarFasilitas.setImageResource(R.drawable.fasilitas_kos);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent untuk pindah ke DetailActivity
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public FasilitasKosBinding binding;

        public Viewholder(ViewholderCardViewBinding binding) {
            super(binding.getRoot());
        }
    }
}
