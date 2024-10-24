package com.example.appflowtask01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appflowtask01.models.Ramo;
import com.example.appflowtask01.R;

import java.util.List;

public class RamoAdapter extends RecyclerView.Adapter<RamoAdapter.RamoViewHolder> {

    private List<Ramo> ramoList;
    private OnRamoClickListener onRamoClickListener;

    public interface OnRamoClickListener {
        void onRamoClick(Ramo ramo);
    }

    public RamoAdapter(List<Ramo> ramoList, OnRamoClickListener listener) {
        this.ramoList = ramoList;
        this.onRamoClickListener = listener;
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
        holder.textViewRamo.setText(ramo.getNombreRamo());
        holder.itemView.setOnClickListener(v -> onRamoClickListener.onRamoClick(ramo));
    }

    @Override
    public int getItemCount() {
        return ramoList.size();
    }

    public static class RamoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRamo;

        public RamoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRamo = itemView.findViewById(R.id.textViewRamo);
        }
    }
}

