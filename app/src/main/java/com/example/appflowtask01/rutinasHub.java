package com.example.appflowtask01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appflowtask01.adapter.RutinasAdapter;
import com.example.appflowtask01.models.EstudioRutina;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class rutinasHub extends Fragment {

    private RecyclerView recyclerView;
    private RutinasAdapter adapter;
    private FirebaseFirestore db;
    private Button btnNuevaRutina;  // Añadir referencia al botón

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rutinas_hub, container, false);

        recyclerView = view.findViewById(R.id.recycler_rutinas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnNuevaRutina = view.findViewById(R.id.btn_nueva_rutina); // Inicializar el botón

        // Configurar el listener del botón para cambiar de fragmento
        btnNuevaRutina.setOnClickListener(v -> {
            Fragment nuevaRutinaFragment = new rutinas_nuevas();
            getFragmentManager().beginTransaction()
                    .replace(R.id.contenedor, nuevaRutinaFragment)
                    .addToBackStack(null)
                    .commit();
        });

        db = FirebaseFirestore.getInstance();
        cargarRutinas();

        return view;
    }

    private void cargarRutinas() {
        db.collection("Rutinas")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Convertir los documentos en una lista de objetos EstudioRutina
                    List<EstudioRutina> listaRutinas = queryDocumentSnapshots.toObjects(EstudioRutina.class);

                    // Crear el adaptador con la lista de rutinas
                    adapter = new RutinasAdapter(listaRutinas, rutina -> {
                        // Obtener el nombre de la rutina seleccionada
                        String nombreRutina = rutina.getNombre();  // Obtener el nombre de la rutina

                        // Crear un Bundle para pasar el nombre de la rutina al fragmento
                        Bundle bundle = new Bundle();
                        bundle.putString("nombre_rutina", nombreRutina);  // Pasar el nombre de la rutina

                        // Crear y abrir el fragmento de ver rutina
                        Fragment fragment = new rutina_ver();
                        fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.contenedor, fragment)
                                .addToBackStack(null)
                                .commit();
                    });

                    // Asignar el adaptador al RecyclerView
                    recyclerView.setAdapter(adapter);
                });
    }
}
