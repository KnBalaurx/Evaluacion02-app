package com.example.appflowtask01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appflowtask01.R;
import com.example.appflowtask01.models.Nota;

import java.util.List;

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.ViewHolder> {

    private List<Nota> notaList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Nota nota);
    }

    public NotaAdapter(List<Nota> notaList, OnItemClickListener listener) {
        this.notaList = notaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nota nota = notaList.get(position);
        holder.titulo.setText(nota.getTitulo());
        holder.descripcion.setText(nota.getDescripcion());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(nota);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, descripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tituloNota);
            descripcion = itemView.findViewById(R.id.descripcionNota);
        }
    }
}

