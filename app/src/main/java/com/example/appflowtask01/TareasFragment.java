package com.example.appflowtask01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appflowtask01.adapter.TareasAdapter;

import java.util.ArrayList;
import java.util.List;

public class TareasFragment extends Fragment {

    private String nombreRamo;
    private List<String> listaTareas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tareas, container, false);

        // Obtener el nombre del ramo del Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            nombreRamo = bundle.getString("nombreRamo");
        }

        // Inicializar RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_tareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Cargar tareas correspondientes al nombre del ramo
        listaTareas = obtenerTareasPorRamo(nombreRamo);

        // Configurar el adaptador del RecyclerView
        TareasAdapter adapter = new TareasAdapter(listaTareas);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Simular obtención de tareas asociadas al ramo
    private List<String> obtenerTareasPorRamo(String nombreRamo) {
        List<String> tareas = new ArrayList<>();
        if ("Ramo1".equals(nombreRamo)) {
            tareas.add("Tarea 1 de " + nombreRamo);
            tareas.add("Tarea 2 de " + nombreRamo);
        } else if ("Ramo2".equals(nombreRamo)) {
            tareas.add("Tarea 1 de " + nombreRamo);
            tareas.add("Tarea 2 de " + nombreRamo);
        } else {
            tareas.add("No hay tareas para este ramo.");
        }
        return tareas;
    }
}
