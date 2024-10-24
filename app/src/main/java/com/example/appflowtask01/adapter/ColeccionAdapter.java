package com.example.appflowtask01.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.appflowtask01.R;
import com.example.appflowtask01.models.ColeccionItem; // Asegúrate de tener esta importación

// Aquí comienza tu clase ColeccionAdapter
public class ColeccionAdapter extends RecyclerView.Adapter<ColeccionAdapter.ColeccionViewHolder> {

    private List<ColeccionItem> coleccionList;

    public ColeccionAdapter(List<ColeccionItem> coleccionList) {
        this.coleccionList = coleccionList;
    }

    public static class ColeccionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;

        public ColeccionViewHolder(View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombreColeccion);
        }
    }

    @Override
    public ColeccionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coleccion, parent, false);
        return new ColeccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ColeccionViewHolder holder, int position) {
        ColeccionItem item = coleccionList.get(position);
        holder.textViewNombre.setText(item.getNombre());
    }

    @Override
    public int getItemCount() {
        return coleccionList.size();
    }
}
