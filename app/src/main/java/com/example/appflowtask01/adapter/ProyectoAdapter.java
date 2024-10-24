package com.example.appflowtask01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appflowtask01.R;
import com.example.appflowtask01.models.Proyecto;

import java.util.List;

public class ProyectoAdapter extends RecyclerView.Adapter<ProyectoAdapter.ProyectoViewHolder> {

    private final List<Proyecto> listaProyectos;

    public ProyectoAdapter(List<Proyecto> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    @NonNull
    @Override
    public ProyectoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyecto, parent, false);
        return new ProyectoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProyectoViewHolder holder, int position) {
        Proyecto proyecto = listaProyectos.get(position);
        holder.bind(proyecto);
    }

    @Override
    public int getItemCount() {
        return listaProyectos.size();
    }

    class ProyectoViewHolder extends RecyclerView.ViewHolder {
        private final TextView titulo, descripcion, fechaEntrega, cantidadIntegrantes, ramo;

        public ProyectoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_proyecto);
            descripcion = itemView.findViewById(R.id.descripcion_proyecto);
            fechaEntrega = itemView.findViewById(R.id.fecha_entrega_proyecto);
            cantidadIntegrantes = itemView.findViewById(R.id.cantidad_integrantes_proyecto);
            ramo = itemView.findViewById(R.id.ramo_proyecto);
        }

        public void bind(Proyecto proyecto) {
            titulo.setText(proyecto.getTitulo() != null ? proyecto.getTitulo() : "");
            descripcion.setText(proyecto.getDescripcion() != null ? proyecto.getDescripcion() : "");
            fechaEntrega.setText(proyecto.getFechaEntrega() != null ? proyecto.getFechaEntrega() : "");
            cantidadIntegrantes.setText(proyecto.getCantidadIntegrantes() != null ? proyecto.getCantidadIntegrantes() : "");
            ramo.setText(proyecto.getRamo() != null ? proyecto.getRamo() : "");
        }
    }
}
