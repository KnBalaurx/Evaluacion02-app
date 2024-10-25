package com.example.appflowtask01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appflowtask01.adapter.NotaAdapter;
import com.example.appflowtask01.models.Nota;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class notasHub extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private List<Nota> notaList;
    private NotaAdapter notaAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notas_hub, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewNotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firestore = FirebaseFirestore.getInstance();
        notaList = new ArrayList<>();
        notaAdapter = new NotaAdapter(notaList, new NotaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota) {
                // Al hacer clic en una nota, abrir el formulario con los datos precargados
                Bundle bundle = new Bundle();
                bundle.putString("id", nota.getId());
                bundle.putString("titulo", nota.getTitulo());
                bundle.putString("descripcion", nota.getDescripcion());

                notasNuevas fragment = new notasNuevas();
                fragment.setArguments(bundle);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        recyclerView.setAdapter(notaAdapter);

        cargarNotas();

        view.findViewById(R.id.btnNotanNueva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, new notasNuevas());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void cargarNotas() {
        firestore.collection("Notas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    notaList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Nota nota = new Nota(document.getId(),
                                document.getString("Titulo"),
                                document.getString("Descripcion"));
                        notaList.add(nota);
                    }
                    notaAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Error al cargar las notas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
