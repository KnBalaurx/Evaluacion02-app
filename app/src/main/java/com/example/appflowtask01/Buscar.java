package com.example.appflowtask01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appflowtask01.adapter.RamoAdapter;
import com.example.appflowtask01.models.Ramo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;





public class Buscar extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Ramo> ramoList = new ArrayList<>();
    RecyclerView recyclerView;
    RamoAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_buscar, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new RamoAdapter(ramoList, ramo -> {
            // Acción al hacer clic en un ramo (mostrar detalles en otro fragmento)
            Bundle bundle = new Bundle();
            bundle.putString("nombreProfesor", ramo.getNombreProfesor());
            bundle.putString("nombreRamo", ramo.getNombreRamo());
            bundle.putString("seccion", ramo.getSeccion());

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
                            ramoList.add(ramo);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        return view;
    }
}
