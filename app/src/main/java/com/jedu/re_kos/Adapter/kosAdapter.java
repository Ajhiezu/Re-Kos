package com.jedu.re_kos.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.jedu.re_kos.Detail.ButtonSewaActivity;
import com.jedu.re_kos.Domain.kosDomain;
import com.jedu.re_kos.model.KosModel;
import com.jedu.re_kos.databinding.ViewholderCardViewBinding;

import java.util.ArrayList;
import java.util.List;

public class kosAdapter extends RecyclerView.Adapter<kosAdapter.Viewholder> {
    List <KosModel.KostData> kos;
    Context context;
    ViewholderCardViewBinding binding;

    public kosAdapter(List <KosModel.KostData> kos) {
        this.kos = kos; notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewholderCardViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new Viewholder(binding);
    }
    public void setKostList(List<KosModel.KostData> kos) {
        this.kos = kos;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        KosModel.KostData kost = kos.get(position);
//    binding.tagKos.setText(kost.get().);
        Log.d("TAG", "onBindViewHolder: " + kost.getNama_kos());
    binding.namakos.setText(kost.getNama_kos());
    binding.lokasi.setText(kost.getAlamat());
    binding.fasilitas.setText(kost.getFasilitas_kamar());
    binding.rating.setText(String.valueOf(kost.getRata_rata_rating()));
    binding.harga.setText("IDR" + kost.getHarga());

//    int drawableResource = holder.itemView.getResources().getIdentifier(items.get(position).getImage(),"drawable",holder.itemView.getContext().getPackageName());
//    Glide.with(context)
//            .load(drawableResource)
//            .transform(new GranularRoundedCorners(30,30,0,0))
//            .into(binding.gambarkos);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Intent untuk pindah ke DetailActivity
            Intent intent = new Intent(context, ButtonSewaActivity.class);
            intent.putExtra("id_kos", kost.getId_kos());
            context.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return kos.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        public Viewholder(ViewholderCardViewBinding binding) {
            super(binding.getRoot());
        }
    }
}
