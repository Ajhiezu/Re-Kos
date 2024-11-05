package com.jedu.re_kos.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.jedu.re_kos.Detail.DetailKosActivity;
import com.jedu.re_kos.Domain.kosDomain;
import com.jedu.re_kos.databinding.ViewholderCardViewBinding;

import java.util.ArrayList;

public class kosAdapter extends RecyclerView.Adapter<kosAdapter.Viewholder> {
    ArrayList<kosDomain> items;
    Context context;
    ViewholderCardViewBinding binding;

    public kosAdapter(ArrayList<kosDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewholderCardViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    binding.tagKos.setText(items.get(position).getTagkos());
    binding.namakos.setText(items.get(position).getNamaKos());
    binding.lokasi.setText(items.get(position).getLokasi());
    binding.fasilitas.setText(items.get(position).getFasilitas());
    binding.rating.setText(String.valueOf(items.get(position).getRating()));
    binding.harga.setText("IDR" + items.get(position).getHarga());

    int drawableResource = holder.itemView.getResources().getIdentifier(items.get(position).getImage(),"drawable",holder.itemView.getContext().getPackageName());
    Glide.with(context)
            .load(drawableResource)
            .transform(new GranularRoundedCorners(30,30,0,0))
            .into(binding.gambarkos);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Intent untuk pindah ke DetailActivity
            Intent intent = new Intent(context, DetailKosActivity.class);
            context.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        public Viewholder(ViewholderCardViewBinding binding) {
            super(binding.getRoot());
        }
    }
}
