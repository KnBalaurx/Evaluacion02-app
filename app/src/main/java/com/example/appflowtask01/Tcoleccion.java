package com.example.appflowtask01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appflowtask01.adapter.ColeccionAdapter;
import com.example.appflowtask01.models.ColeccionItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class Tcoleccion extends Fragment {

    private String tipoColeccion;
    private String nombreRamo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            tipoColeccion = getArguments().getString("tipoColeccion");
            nombreRamo = getArguments().getString("nombreRamo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tcoleccion, container, false);

        // Inicializar RecyclerView y Adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewColeccion);
        List<ColeccionItem> itemList = new ArrayList<>();
        ColeccionAdapter adapter = new ColeccionAdapter(itemList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener los datos filtrados de Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(tipoColeccion)
                .whereEqualTo("nombreRamo", nombreRamo)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ColeccionItem item = document.toObject(ColeccionItem.class);
                            itemList.add(item);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        return view;
    }
}

