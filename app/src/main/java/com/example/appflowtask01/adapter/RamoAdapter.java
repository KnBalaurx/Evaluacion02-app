package com.example.appflowtask01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appflowtask01.models.Ramo;
import com.example.appflowtask01.R;

import java.util.ArrayList;
import java.util.List;

public class RamoAdapter extends RecyclerView.Adapter<RamoAdapter.RamoViewHolder> {
    private List<Ramo> ramoList;
    private List<Ramo> ramoListFull; // Lista completa para restaurar después de la búsqueda
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Ramo ramo);
    }

    public RamoAdapter(List<Ramo> ramoList, OnItemClickListener listener) {
        this.ramoList = ramoList;
        this.ramoListFull = new ArrayList<>(ramoList); // Copia de la lista completa
        this.listener = listener;
    }

    @NonNull
    @Override
    public RamoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ramo, parent, false);
        return new RamoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RamoViewHolder holder, int position) {
        Ramo ramo = ramoList.get(position);
        holder.bind(ramo, listener);
    }

    @Override
    public int getItemCount() {
        return ramoList.size();
    }

    public static class RamoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreRamoTextView;

        public RamoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreRamoTextView = itemView.findViewById(R.id.textViewRamo);
        }

        public void bind(final Ramo ramo, final OnItemClickListener listener) {
            nombreRamoTextView.setText(ramo.getNombreRamo());
            itemView.setOnClickListener(v -> listener.onItemClick(ramo));
        }
    }

    // Método para filtrar la lista según el nombre del ramo
    public void filter(String text) {
        ramoList.clear();
        if (text.isEmpty()) {
            ramoList.addAll(ramoListFull);
        } else {
            text = text.toLowerCase();
            for (Ramo ramo : ramoListFull) {
                if (ramo.getNombreRamo().toLowerCase().contains(text)) {
                    ramoList.add(ramo);
                }
            }
        }
        notifyDataSetChanged();
    }
}
