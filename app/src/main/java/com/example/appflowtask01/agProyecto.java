package com.example.appflowtask01;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class agProyecto extends DialogFragment {

    private FirebaseFirestore db;
    private Spinner spinnerRamos;
    private EditText editTextNombreTarea, editTextDescripcion, editTextFechaEntrega, editTextCanti;
    private Button btnGuardarProyecto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("agProyecto", "Inflating layout");
        View v = inflater.inflate(R.layout.fragment_ag_proyecto, container, false);

        Log.d("agProyecto", "Layout inflated");

        db = FirebaseFirestore.getInstance();

        editTextNombreTarea = v.findViewById(R.id.tituloPr);
        editTextFechaEntrega = v.findViewById(R.id.datePr);
        spinnerRamos = v.findViewById(R.id.spinnerPr);
        editTextCanti = v.findViewById(R.id.intPr);
        editTextDescripcion = v.findViewById(R.id.descPr);
        btnGuardarProyecto = v.findViewById(R.id.btnIngPr);

        editTextDescripcion.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editTextDescripcion.setLines(5);
        editTextDescripcion.setVerticalScrollBarEnabled(true);

        cargarDatosRamo();

        btnGuardarProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloP = editTextNombreTarea.getText().toString().trim();
                String dateP = editTextFechaEntrega.getText().toString().trim();
                String ramoSeleccionado = spinnerRamos.getSelectedItem().toString();
                String intP = editTextCanti.getText().toString().trim();
                String descP = editTextDescripcion.getText().toString().trim();

                if (!tituloP.isEmpty() && !dateP.isEmpty() && !intP.isEmpty() && !descP.isEmpty() && !ramoSeleccionado.equals("Selecciona un ramo")) {
                    guardarTareaEnFirestore(tituloP, dateP, ramoSeleccionado, intP, descP);
                } else {
                    Toast.makeText(getContext(), "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                }

                // Logs para depurar valores
                Log.d("agProyecto", "Titulo: " + tituloP);
                Log.d("agProyecto", "Fecha: " + dateP);
                Log.d("agProyecto", "Ramo Seleccionado: " + ramoSeleccionado);
                Log.d("agProyecto", "Cantidad: " + intP);
                Log.d("agProyecto", "Descripción: " + descP);
            }
        });

        return v;
    }

    private void guardarTareaEnFirestore(String tituloP, String fechaEntrega, String ramoSeleccionado, String intP, String descP) {
        Map<String, Object> map = new HashMap<>();
        map.put("Titulo", tituloP);
        map.put("Fecha entrega", fechaEntrega);
        map.put("Ramo", ramoSeleccionado);
        map.put("Cantidad de integrantes", intP);
        map.put("Descripcion", descP);

        db.collection("Proyecto").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Actualizado exitosamente", Toast.LENGTH_SHORT).show();
                if (getDialog() != null) {
                    getDialog().dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
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

}
