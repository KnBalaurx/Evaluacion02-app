package com.example.appflowtask01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.appflowtask01.adapter.RamoAdapter;
import com.example.appflowtask01.models.Ramo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class Buscar extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Ramo> ramoList = new ArrayList<>();
    List<Ramo> filteredRamoList = new ArrayList<>(); // Lista filtrada
    RecyclerView recyclerView;
    RamoAdapter adapter;
    SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        // Inicializar RecyclerView y SearchView
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);

        adapter = new RamoAdapter(filteredRamoList, ramo -> {
            // Acción al hacer clic en un ramo (mostrar detalles en otro fragmento)
            Bundle bundle = new Bundle();
            bundle.putString("nombreRamo", ramo.getNombreRamo());
            bundle.putString("seccion", ramo.getSeccion());
            bundle.putString("profesor", ramo.getNombreProfesor());

            Fragment ramoVer = new Ramo_ver();
            ramoVer.setArguments(bundle);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor, ramoVer)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener datos desde Firestore
        db.collection("Ramos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Ramo ramo = document.toObject(Ramo.class);
                            ramoList.add(ramo); // Agregar a la lista principal
                        }
                        filteredRamoList.addAll(ramoList); // Copiar la lista original a la lista filtrada
                        adapter.notifyDataSetChanged();
                    }
                });

        // Configurar el SearchView para filtrar resultados
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // No hacemos nada al enviar la búsqueda
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrar la lista cuando cambia el texto de búsqueda
                filterRamoList(newText);
                return true;
            }
        });

        return view;
    }

    // Método para filtrar la lista de ramos
    private void filterRamoList(String query) {
        filteredRamoList.clear(); // Limpiar la lista filtrada
        if (query.isEmpty()) {
            // Si no hay texto, mostrar todos los ramos
            filteredRamoList.addAll(ramoList);
        } else {
            // Si hay texto, filtrar la lista original
            for (Ramo ramo : ramoList) {
                if (ramo.getNombreRamo().toLowerCase().contains(query.toLowerCase())) {
                    filteredRamoList.add(ramo);
                }
            }
        }
        adapter.notifyDataSetChanged(); // Notificar cambios al adaptador
    }
}

