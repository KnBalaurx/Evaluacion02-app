package com.example.appflowtask01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appflowtask01.models.EstudioRutina;
import com.google.firebase.firestore.FirebaseFirestore;


public class rutinas_nuevas extends Fragment {
    private EditText etNombreRutina, etTiempoEstudio, etTiempoDescanso, etIntervaloDescanso;
    private Button btnGuardarRutina;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rutinas_nueva, container, false);

        etNombreRutina = view.findViewById(R.id.et_nombre_rutina);
        etTiempoEstudio = view.findViewById(R.id.et_tiempo_estudio);
        etTiempoDescanso = view.findViewById(R.id.et_tiempo_descanso);
        etIntervaloDescanso = view.findViewById(R.id.et_intervalo_descanso);
        btnGuardarRutina = view.findViewById(R.id.btn_guardar_rutina);

        db = FirebaseFirestore.getInstance();

        btnGuardarRutina.setOnClickListener(v -> guardarRutina());

        return view;
    }

    private void guardarRutina() {
        String nombre = etNombreRutina.getText().toString();
        int tiempoEstudio = Integer.parseInt(etTiempoEstudio.getText().toString());
        int tiempoDescanso = Integer.parseInt(etTiempoDescanso.getText().toString());
        int intervaloDescanso = Integer.parseInt(etIntervaloDescanso.getText().toString());

        // Crear objeto de la rutina
        EstudioRutina rutina = new EstudioRutina(nombre, tiempoEstudio, tiempoDescanso, intervaloDescanso);

        // Guardar la rutina en la colección "Rutinas"
        db.collection("Rutinas").add(rutina)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getContext(), "Rutina guardada exitosamente", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack(); // Vuelve al fragmento anterior
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al guardar la rutina", Toast.LENGTH_SHORT).show();
                });
    }
}


