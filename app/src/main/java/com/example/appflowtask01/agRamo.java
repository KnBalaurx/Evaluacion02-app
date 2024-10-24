package com.example.appflowtask01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class agRamo extends DialogFragment {
    Button btnAgRamo;
    EditText nomRamo, prRamo, scRamo;
    private FirebaseFirestore mfirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ag_ramo, container, false);

        mfirestore = FirebaseFirestore.getInstance();

        nomRamo = v.findViewById(R.id.nomRamo);
        prRamo = v.findViewById(R.id.prRamo);
        scRamo = v.findViewById(R.id.secRamo);
        btnAgRamo = v.findViewById(R.id.btnAgRamo);

        btnAgRamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameR = nomRamo.getText().toString().trim();
                String profeR = prRamo.getText().toString().trim();
                String seccionR = scRamo.getText().toString().trim();

                if (nameR.isEmpty() && profeR.isEmpty() && seccionR.isEmpty()) {
                    Toast.makeText(getContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    updateRamo(nameR, profeR, seccionR);
                }
            }
        });

        return v;
    }

    private void updateRamo(String nameR, String profeR, String seccionR) {
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre Ramo", nameR);
        map.put("Nombre Profesor", profeR);
        map.put("Seccion", seccionR);

        mfirestore.collection("Ramos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
}
