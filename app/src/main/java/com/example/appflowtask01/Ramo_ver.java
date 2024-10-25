package com.example.appflowtask01;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;


import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class Ramo_ver extends Fragment {

    private String nombreRamo;
    private String tipo;
    private String seccion;
    private String profesor;
    private androidx.appcompat.widget.Toolbar toolbar;
    private Button btnEliminar;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ramo_ver, container, false);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Obtener datos del Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            nombreRamo = bundle.getString("nombreRamo");
            tipo = bundle.getString("tipo");
            seccion = bundle.getString("seccion");
            profesor = bundle.getString("profesor");

            // Depuración para verificar los valores
            Log.d("Ramo_ver", "Nombre del ramo: " + nombreRamo);
            Log.d("Ramo_ver", "Tipo: " + tipo);
            Log.d("Ramo_ver", "Sección: " + seccion);
            Log.d("Ramo_ver", "Profesor: " + profesor);
        }

        // Configurar la Toolbar
        toolbar = view.findViewById(R.id.toolbar2);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);

            // Eliminar el botón de "volver atrás"
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            // Configurar el título de la Toolbar con el formato deseado
            String tituloToolbar = nombreRamo;
            activity.getSupportActionBar().setTitle(tituloToolbar);
        }

        // Configurar BottomNavigation y botón de eliminar
        configurarBottomNavigation(view);
        btnEliminar = view.findViewById(R.id.btn_eliminar);
        btnEliminar.setOnClickListener(v -> eliminarRamo());

        return view;
    }

    private void configurarBottomNavigation(View view) {
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_tareas) {
                    mostrarFragmentoColeccion("Tarea");
                    return true;
                } else if (id == R.id.action_evaluaciones) {
                    mostrarFragmentoColeccion("Evaluacion");
                    return true;
                } else if (id == R.id.action_proyectos) {
                    mostrarFragmentoColeccion("Proyecto");
                    return true;
                }
                return false;
            }
        });

        // Mostrar por defecto el fragmento de Tareas
        mostrarFragmentoColeccion("Tarea");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void mostrarFragmentoColeccion(String tipoColeccion) {
        Fragment fragmentoColeccion = null;

        switch (tipoColeccion) {
            case "Tarea":
                fragmentoColeccion = new TareasFragment();
                break;
            case "Evaluacion":
                fragmentoColeccion = new EvaluacionFragment();
                break;
            case "Proyecto":
                fragmentoColeccion = new ProyectoFragment();
                break;
        }

        if (fragmentoColeccion != null) {
            Bundle bundle = new Bundle();
            bundle.putString("nombreRamo", nombreRamo);
            bundle.putString("tipo", tipo);
            bundle.putString("seccion", seccion);
            bundle.putString("profesor", profesor);
            fragmentoColeccion.setArguments(bundle);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor2, fragmentoColeccion)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void eliminarRamo() {
        if (nombreRamo != null) {
            // Mostrar el valor de nombreRamo para depuración
            Log.d("Ramo_ver", "Intentando eliminar el ramo con nombre: " + nombreRamo);

            db.collection("Ramos").whereEqualTo("Nombre Ramo", nombreRamo).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Obtener el ID del documento a eliminar
                            String documentId = queryDocumentSnapshots.getDocuments().get(0).getId();
                            db.collection("Ramos").document(documentId).delete()
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getContext(), "Ramo eliminado", Toast.LENGTH_SHORT).show();
                                        // Navegar al fragmento "fragment_buscar"
                                        Fragment fragmentBuscar = new Buscar();
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.contenedor, fragmentBuscar)
                                                .addToBackStack(null)
                                                .commit();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(getContext(), "Error al eliminar el ramo", Toast.LENGTH_SHORT).show();
                                        Log.e("Ramo_ver", "Error al eliminar el ramo: " + e.getMessage());
                                    });
                        } else {
                            Toast.makeText(getContext(), "Ramo no encontrado", Toast.LENGTH_SHORT).show();
                            Log.d("Ramo_ver", "No se encontraron documentos para el nombre: " + nombreRamo);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Error al buscar el ramo", Toast.LENGTH_SHORT).show();
                        Log.e("Ramo_ver", "Error al buscar el ramo: " + e.getMessage());
                    });
        } else {
            Toast.makeText(getContext(), "El nombre del ramo es nulo", Toast.LENGTH_SHORT).show();
            Log.d("Ramo_ver", "El nombre del ramo es nulo");
        }
    }


}
