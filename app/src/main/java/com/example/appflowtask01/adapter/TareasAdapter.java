package com.example.appflowtask01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appflowtask01.R;

import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaViewHolder> {

    private List<String> listaTareas;

    public TareasAdapter(List<String> listaTareas) {
        this.listaTareas = listaTareas;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        String tarea = listaTareas.get(position);
        holder.textViewTarea.setText(tarea);
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTarea;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTarea = itemView.findViewById(R.id.text_view_tarea);
        }
    }
}

