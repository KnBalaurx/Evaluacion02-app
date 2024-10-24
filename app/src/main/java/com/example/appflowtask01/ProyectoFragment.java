package com.example.appflowtask01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appflowtask01.adapter.ProyectoAdapter;
import com.example.appflowtask01.models.Proyecto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ProyectoFragment extends Fragment {

    private String nombreRamo;
    private List<Proyecto> listaProyectos;
    private ProyectoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proyecto, container, false);

        // Obtener el nombre del ramo del Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            nombreRamo = bundle.getString("nombreRamo");
            Log.d("NombreRamo", "Nombre del ramo: " + nombreRamo); // Verificar valor de nombreRamo
        }

        // Inicializar RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_proyectos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar la lista de proyectos
        listaProyectos = new ArrayList<>();

        // Configurar el adaptador del RecyclerView
        adapter = new ProyectoAdapter(listaProyectos);
        recyclerView.setAdapter(adapter);

        // Cargar proyectos correspondientes al nombre del ramo desde Firestore
        obtenerProyectosPorRamo(nombreRamo);

        return view;
    }

    // Obtener proyectos asociados al ramo desde Firestore
    private void obtenerProyectosPorRamo(String nombreRamo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Verificación de valor nulo
        if (nombreRamo == null || nombreRamo.isEmpty()) {
            Log.d("FirestoreError", "El nombre del ramo es nulo o vacío. No se puede realizar la consulta.");
            return;
        }

        // Referencia a la colección de "Proyectos" filtrada por el campo "Ramo"
        db.collection("Proyecto")
                .whereEqualTo("Ramo", nombreRamo)  // Cambiar a "Ramo" en lugar de "Ramos" si el campo correcto es "Ramo"
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listaProyectos.clear(); // Limpiar la lista antes de agregar nuevos datos
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Convertir el documento en un objeto Proyecto
                                Proyecto proyecto = document.toObject(Proyecto.class);
                                Log.d("Firestore", "Proyecto encontrado: " + proyecto.getTitulo()); // Log para cada proyecto
                                listaProyectos.add(proyecto);
                            }
                            // Notificar al adaptador que los datos han cambiado
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("FirestoreError", "Error al obtener proyectos: " + task.getException().getMessage());
                        }
                    }
                });
    }
}
