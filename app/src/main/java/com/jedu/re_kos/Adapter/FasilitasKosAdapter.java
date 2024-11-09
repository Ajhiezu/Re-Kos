package com.jedu.re_kos.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jedu.re_kos.R;

import java.util.List;

public class FasilitasKosAdapter extends RecyclerView.Adapter<FasilitasKosAdapter.FasilitasViewHolder> {

    private List<Fasilitas> fasilitasList;

    public FasilitasKosAdapter(List<Fasilitas> fasilitasList) {
        this.fasilitasList = fasilitasList;
    }

    public void setFasilitasList(List<Fasilitas> fasilitasList) {
        this.fasilitasList = fasilitasList;
    }

    @NonNull
    @Override
    public FasilitasKosAdapter.FasilitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fasilitas_kos, parent, false);
        return new FasilitasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FasilitasKosAdapter.FasilitasViewHolder holder, int position) {
        Fasilitas fasilitas = fasilitasList.get(position);
        holder.textNamaFasilitas.setText(fasilitas.getNamaFasilitas());
        holder.imageFasilitas.setImageResource(fasilitas.getIconFasilitas());
    }

    @Override
    public int getItemCount() {
        return fasilitasList.size();
    }
    static class FasilitasViewHolder extends RecyclerView.ViewHolder {
        TextView textNamaFasilitas;
        ImageView imageFasilitas;

        public FasilitasViewHolder(@NonNull View itemView) {
            super(itemView);
            textNamaFasilitas = itemView.findViewById(R.id.NamaFasilitas);
            imageFasilitas = itemView.findViewById(R.id.GambarFasilitas);
        }
    }
}
