package com.example.appflowtask01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appflowtask01.models.EstudioRutina;
import com.example.appflowtask01.R;

import java.util.List;

public class RutinasAdapter extends RecyclerView.Adapter<RutinasAdapter.RutinaViewHolder> {

    // Lista de rutinas y un listener para cuando se hace clic en una rutina
    private List<EstudioRutina> listaRutinas;
    private OnRutinaClickListener listener;

    // Constructor del adaptador
    public RutinasAdapter(List<EstudioRutina> listaRutinas, OnRutinaClickListener listener) {
        this.listaRutinas = listaRutinas;
        this.listener = listener;
    }

    // ViewHolder que describe cómo se va a ver cada rutina en la lista
    public static class RutinaViewHolder extends RecyclerView.ViewHolder {
        TextView nombreRutina;
        TextView tiempoEstudio;
        TextView tiempoDescanso;
        TextView intervaloDescanso;

        public RutinaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreRutina = itemView.findViewById(R.id.tvNombreRutina);

        }

        public void bind(final EstudioRutina rutina, final OnRutinaClickListener listener) {
            nombreRutina.setText(rutina.getNombre());

            itemView.setOnClickListener(v -> listener.onRutinaClick(rutina));
        }

    }

    // Crear nuevas vistas
    @NonNull
    @Override
    public RutinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista de un ítem de rutina
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutina, parent, false);
        return new RutinaViewHolder(view);
    }

    // Reemplazar el contenido de una vista (se llama cuando se desplaza)
    @Override
    public void onBindViewHolder(@NonNull RutinaViewHolder holder, int position) {
        // Obtener la rutina en esta posición
        EstudioRutina rutina = listaRutinas.get(position);

        // Reemplazar el contenido de la vista
        holder.bind(rutina, listener);
    }

    // Obtener el tamaño del dataset
    @Override
    public int getItemCount() {
        return listaRutinas.size();
    }

    // Interfaz para manejar los clics en las rutinas
    public interface OnRutinaClickListener {
        void onRutinaClick(EstudioRutina rutina);
    }
}

