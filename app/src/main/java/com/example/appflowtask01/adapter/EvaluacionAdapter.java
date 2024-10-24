package com.example.appflowtask01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appflowtask01.R;
import com.example.appflowtask01.models.Evaluacion;

import java.util.List;

public class EvaluacionAdapter extends RecyclerView.Adapter<EvaluacionAdapter.EvaluacionViewHolder> {

    private List<Evaluacion> evaluacionList;

    public EvaluacionAdapter(List<Evaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    @NonNull
    @Override
    public EvaluacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluacion, parent, false);
        return new EvaluacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluacionViewHolder holder, int position) {
        Evaluacion evaluacion = evaluacionList.get(position);
        holder.bind(evaluacion);
    }

    @Override
    public int getItemCount() {
        return evaluacionList.size();
    }

    public static class EvaluacionViewHolder extends RecyclerView.ViewHolder {

        // Referencias a los elementos del layout
        private TextView textViewNombreTarea;
        private TextView textViewDescripcion;
        private TextView textViewFechaEntrega;

        public EvaluacionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreTarea = itemView.findViewById(R.id.text_view_nombre_tarea);
            textViewDescripcion = itemView.findViewById(R.id.text_view_descripcion);
            textViewFechaEntrega = itemView.findViewById(R.id.text_view_fecha_entrega);
        }

        public void bind(Evaluacion evaluacion) {
            textViewNombreTarea.setText(evaluacion.getNombreTarea());
            textViewDescripcion.setText(evaluacion.getDescripcion());
            textViewFechaEntrega.setText(evaluacion.getFechaEntrega());
        }
    }
}
