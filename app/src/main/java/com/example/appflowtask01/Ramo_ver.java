package com.example.appflowtask01;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import android.view.MenuItem;
import android.widget.Toast;


public class Ramo_ver extends Fragment {

    private String nombreRamo;
    private String tipo;
    private String seccion;
    private String profesor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ramo_ver, container, false);

        // Obtener datos del Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            nombreRamo = bundle.getString("nombreRamo");
            tipo = bundle.getString("tipo");
            seccion = bundle.getString("seccion");
            profesor = bundle.getString("profesor");
        }

        // Configurar la Toolbar
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            if (nombreRamo != null && tipo != null && seccion != null && profesor != null) {
                // Mostrar el nombre del ramo, nombre del profesor y la sección en la toolbar
                String toolbarTitle = nombreRamo + " - " + profesor + " - Sección " + seccion;
                activity.getSupportActionBar().setTitle(toolbarTitle);
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Habilitar botón de "atrás"
            }
        }

        // Configurar el BottomNavigationView
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

        return view;
    }

    // Método para mostrar los fragmentos de Tarea, Evaluación o Proyecto según el tipo
    private void mostrarFragmentoColeccion(String tipoColeccion) {
        Fragment fragmentoColeccion = null;

        switch (tipoColeccion) {
            case "Tarea":
                fragmentoColeccion = new TareasFragment();  // Cargar fragmento de Tareas
                break;
            case "Evaluacion":
                fragmentoColeccion = new EvaluacionFragment();  // Cargar fragmento de Evaluaciones
                break;
            case "Proyecto":
                fragmentoColeccion = new ProyectoFragment();  // Cargar fragmento de Proyectos
                break;
        }

        // Asegurarse de que el fragmento no sea nulo
        if (fragmentoColeccion != null) {
            // Pasar los datos del ramo al nuevo fragmento
            Bundle bundle = new Bundle();
            bundle.putString("nombreRamo", nombreRamo);
            bundle.putString("tipo", tipo);
            bundle.putString("seccion", seccion);
            bundle.putString("profesor", profesor);
            fragmentoColeccion.setArguments(bundle);

            // Reemplazar el contenido actual con el fragmento seleccionado
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor2, fragmentoColeccion)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
