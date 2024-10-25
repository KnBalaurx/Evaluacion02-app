package com.example.appflowtask01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appflowtask01.R;
import com.example.appflowtask01.models.Tarea;

import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaViewHolder> {

    private List<Tarea> tareaList;

    public TareasAdapter(List<Tarea> tareaList) {
        this.tareaList = tareaList;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = tareaList.get(position);
        holder.bind(tarea);
    }

    @Override
    public int getItemCount() {
        return tareaList.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNombreTarea;
        private TextView textViewDescripcion;
        private TextView textViewFechaEntrega;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreTarea = itemView.findViewById(R.id.text_view_nombre_tarea);
            textViewDescripcion = itemView.findViewById(R.id.text_view_descripcion);
            textViewFechaEntrega = itemView.findViewById(R.id.text_view_fecha_entrega);
        }

        public void bind(Tarea tarea) {
            textViewNombreTarea.setText(tarea.getNombreTarea());
            textViewDescripcion.setText(tarea.getDescripcion());
            textViewFechaEntrega.setText(tarea.getFechaEntrega());
        }
    }
}
