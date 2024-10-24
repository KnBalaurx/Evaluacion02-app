package com.example.appflowtask01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.appflowtask01.models.EstudioRutina;
import com.google.firebase.firestore.FirebaseFirestore;

public class rutina_ver extends Fragment {

    private TextView tvNombreRutina, tvTiempoEstudio, tvTiempoDescanso, tvIntervaloDescanso;
    private Chronometer chronometer;
    private Button btnIniciar;
    private boolean isRunning = false;
    private long pauseOffset = 0;

    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rutina_ver, container, false);

        tvNombreRutina = view.findViewById(R.id.tv_nombre_rutina);
        tvTiempoEstudio = view.findViewById(R.id.tv_tiempo_estudio);
        tvTiempoDescanso = view.findViewById(R.id.tv_tiempo_descanso);
        tvIntervaloDescanso = view.findViewById(R.id.tv_intervalo_descanso);
        chronometer = view.findViewById(R.id.chronometer);
        btnIniciar = view.findViewById(R.id.btn_iniciar);

        db = FirebaseFirestore.getInstance();

        // Obtener el nombre de la rutina del bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String nombreRutina = bundle.getString("nombre_rutina");
            cargarRutina(nombreRutina);
        }

        // Listener para iniciar el cronómetro
        btnIniciar.setOnClickListener(v -> {
            if (!isRunning) {
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset); // Retoma el tiempo desde la pausa
                chronometer.start();
                isRunning = true;
                btnIniciar.setText("Pausar");
            } else {
                chronometer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase(); // Guardar tiempo pausado
                isRunning = false;
                btnIniciar.setText("Iniciar");
            }
        });

        return view;
    }

    // Método para cargar la rutina desde Firestore por nombre
    private void cargarRutina(String nombreRutina) {
        db.collection("Rutinas").whereEqualTo("nombre", nombreRutina).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        EstudioRutina rutina = queryDocumentSnapshots.getDocuments().get(0).toObject(EstudioRutina.class);
                        if (rutina != null) {
                            tvNombreRutina.setText(rutina.getNombre());
                            tvTiempoEstudio.setText("Tiempo de estudio: " + rutina.getTiempoEstudio() + " min");
                            tvTiempoDescanso.setText("Tiempo de descanso: " + rutina.getTiempoDescanso() + " min");
                            tvIntervaloDescanso.setText("Intervalo de descanso: " + rutina.getIntervaloDescanso() + " min");
                        }
                    }
                });
    }
}
