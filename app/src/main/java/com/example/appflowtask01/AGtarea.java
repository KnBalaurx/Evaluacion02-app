package com.example.appflowtask01;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Date;

public class AGtarea extends Fragment {
    private FirebaseFirestore db;
    private Spinner spinnerRamos, spinnerTipo;
    private EditText editTextNombreTarea, editTextDescripcion, editTextFechaEntrega;
    private Button btnGuardarTarea;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_gtarea, container, false);

        // Inicializar los elementos de la interfaz de usuario
        spinnerRamos = view.findViewById(R.id.spinner_ramos);
        spinnerTipo = view.findViewById(R.id.spinnerTipo); // Inicializar el Spinner de tipo
        editTextNombreTarea = view.findViewById(R.id.tituloTr);
        editTextDescripcion = view.findViewById(R.id.descTr);
        editTextFechaEntrega = view.findViewById(R.id.FentregaTr);
        btnGuardarTarea = view.findViewById(R.id.btnIngresarTr);

        // Configurar EditText para permitir texto multilinea
        editTextDescripcion.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editTextDescripcion.setLines(5); // Ajusta el número de líneas visible
        editTextDescripcion.setVerticalScrollBarEnabled(true);

        // Cargar datos en el Spinner de Ramos
        cargarDatosRamo();

        // Configurar el botón para guardar la tarea
        btnGuardarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los elementos de la interfaz
                String nombreTarea = editTextNombreTarea.getText().toString();
                String descripcion = editTextDescripcion.getText().toString();
                String fechaString = editTextFechaEntrega.getText().toString();
                String ramoSeleccionado = spinnerRamos.getSelectedItem().toString();
                String tipoSeleccionado = spinnerTipo.getSelectedItem().toString(); // Obtener el valor del Spinner de tipo

                // Verificar que todos los campos no estén vacíos
                if (!nombreTarea.isEmpty() && !descripcion.isEmpty() && !fechaString.isEmpty() && !ramoSeleccionado.equals("Selecciona un ramo")) {
                    // Ya no es necesario convertir la fecha a Date
                    guardarTareaEnFirestore(nombreTarea, descripcion, fechaString, ramoSeleccionado, tipoSeleccionado); // Pasar fechaString como String
                } else {
                    Toast.makeText(getContext(), "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }



    private void cargarDatosRamo() {
        CollectionReference ramosRef = db.collection("Ramos");

        ramosRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot documents = task.getResult();
                    ArrayList<String> itemsSpinner = new ArrayList<>();
                    itemsSpinner.add("Selecciona un ramo");

                    if (documents.isEmpty()) {
                        Log.d("SpinnerData", "La colección Ramo está vacía o no se puede leer.");
                    } else {
                        for (DocumentSnapshot document : documents) {
                            String nombreRamo = document.getString("Nombre Ramo");
                            if (nombreRamo != null) {
                                itemsSpinner.add(nombreRamo);
                            } else {
                                Log.d("SpinnerData", "Documento sin el campo Nombre Ramo.");
                            }
                        }
                    }

                    Log.d("SpinnerData", "Datos cargados: " + itemsSpinner.toString());

                    // Configurar el Spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, itemsSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRamos.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    Log.d("Firestore", "Error al obtener documentos: ", task.getException());
                }
            }
        });
    }

    private void guardarTareaEnFirestore(String nombreTarea, String descripcion, String fechaEntrega, String ramoSeleccionado, String tipoSeleccionado) {
        // Crear un HashMap para almacenar los datos
        Map<String, Object> tarea = new HashMap<>();
        tarea.put("nombreTarea", nombreTarea);
        tarea.put("descripcion", descripcion);
        tarea.put("fechaEntrega", fechaEntrega); // Ahora es un String
        tarea.put("ramoSeleccionado", ramoSeleccionado);

        // Verificar si el tipo seleccionado es "Tarea" o "Evaluación"
        String coleccionDestino = null;

        Log.d("SpinnerTipo", "Valor seleccionado: " + tipoSeleccionado);

        if (tipoSeleccionado.trim().equalsIgnoreCase("Tarea")) {
            coleccionDestino = "Tarea";  // Guardar en la colección "Tarea"
        } else if (tipoSeleccionado.trim().equalsIgnoreCase("Evaluacion")) {  // Sin acento
            coleccionDestino = "Evaluacion";  // Guardar en la colección "Evaluacion"
        }

        if (coleccionDestino != null) {
            // Guardar la tarea en Firestore
            db.collection(coleccionDestino)
                    .add(tarea)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(), "Tarea guardada exitosamente", Toast.LENGTH_SHORT).show();
                            limpiarFormulario();  // Limpiar el formulario tras el guardado
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("FirestoreError", "Error al guardar la tarea", e);
                            Toast.makeText(getContext(), "Error al guardar la tarea", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Por favor selecciona un tipo válido", Toast.LENGTH_SHORT).show();
        }
    }



    private void limpiarFormulario() {

        editTextNombreTarea.setText("");
        editTextDescripcion.setText("");
        editTextFechaEntrega.setText("");
        spinnerRamos.setSelection(0);
    }


    private Date parseFecha(String fechaString) {
        try {

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return format.parse(fechaString);
        } catch (ParseException e) {
            Log.e("ParseFecha", "Error al parsear la fecha", e);
        }
        return null;
    }
}
