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

import com.example.appflowtask01.adapter.EvaluacionAdapter;
import com.example.appflowtask01.models.Evaluacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EvaluacionFragment extends Fragment {

    private String nombreRamo;
    private List<Evaluacion> listaEvaluaciones;
    private EvaluacionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluacion, container, false);

        // Obtener el nombre del ramo del Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            nombreRamo = bundle.getString("nombreRamo");
        }

        // Inicializar RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_evaluaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar la lista de evaluaciones
        listaEvaluaciones = new ArrayList<>();

        // Configurar el adaptador del RecyclerView
        adapter = new EvaluacionAdapter(listaEvaluaciones);
        recyclerView.setAdapter(adapter);

        // Cargar evaluaciones correspondientes al nombre del ramo desde Firestore
        obtenerEvaluacionesPorRamo(nombreRamo);

        return view;
    }

    // Obtener evaluaciones asociadas al ramo desde Firestore
    private void obtenerEvaluacionesPorRamo(String nombreRamo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Verificación de valor nulo
        if (nombreRamo == null || nombreRamo.isEmpty()) {
            Log.d("FirestoreError", "El nombre del ramo es nulo o vacío. No se puede realizar la consulta.");
            return;
        }

        // Referencia a la colección de "Evaluacion" filtrada por el campo "ramoSeleccionado"
        db.collection("Evaluacion")
                .whereEqualTo("ramoSeleccionado", nombreRamo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listaEvaluaciones.clear(); // Limpiar la lista antes de agregar nuevos datos
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Convertir el documento en un objeto Evaluacion
                                Evaluacion evaluacion = document.toObject(Evaluacion.class);
                                Log.d("Firestore", "Evaluacion encontrada: " + evaluacion.getNombreTarea());
                                listaEvaluaciones.add(evaluacion);
                            }
                            // Notificar al adaptador que los datos han cambiado
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("FirestoreError", "Error al obtener evaluaciones: " + task.getException().getMessage());
                        }
                    }
                });
    }
}
