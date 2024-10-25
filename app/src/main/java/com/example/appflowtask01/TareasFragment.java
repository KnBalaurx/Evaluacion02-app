package com.example.appflowtask01;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appflowtask01.adapter.EvaluacionAdapter;
import com.example.appflowtask01.adapter.TareasAdapter;
import com.example.appflowtask01.models.Evaluacion;
import com.example.appflowtask01.models.Tarea;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TareasFragment extends Fragment {

    private String nombreRamo;
    private List<Tarea> listaTareas;
    private TareasAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tareas, container, false);

        // Obtener el nombre del ramo del Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            nombreRamo = bundle.getString("nombreRamo");
            Log.d("TareasFragment", "Nombre del ramo recibido: " + nombreRamo);
        } else {
            Log.d("TareasFragment", "No se recibió un nombre de ramo");
        }

        // Inicializar RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_tareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar la lista de tareas
        listaTareas = new ArrayList<>();

        // Configurar el adaptador del RecyclerView
        adapter = new TareasAdapter(listaTareas);
        recyclerView.setAdapter(adapter);

        // Cargar tareas correspondientes al nombre del ramo desde Firestore
        if (nombreRamo != null && !nombreRamo.isEmpty()) {
            obtenerTareasPorRamo(nombreRamo);
        } else {
            Log.d("TareasFragment", "Nombre del ramo es nulo o vacío");
        }

        return view;
    }

    // Obtener tareas asociadas al ramo desde Firestore
    private void obtenerTareasPorRamo(String nombreRamo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Tarea")
                .whereEqualTo("ramoSeleccionado", nombreRamo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listaTareas.clear();  // Limpiar la lista antes de agregar nuevos datos
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Convertir el documento en un objeto Tarea
                                Tarea tarea = document.toObject(Tarea.class);
                                Log.d("Firestore", "Tarea encontrada: " + tarea.getNombreTarea());
                                listaTareas.add(tarea);  // Añadir tarea a la lista
                            }
                            // Notificar al adaptador que los datos han cambiado
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("FirestoreError", "Error al obtener tareas: " + task.getException().getMessage());
                        }
                    }
                });
    }
}
