package com.example.appflowtask01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class notasNuevas extends DialogFragment {

    ImageButton btnNt;
    EditText ntDesc, nTtitulo;
    private FirebaseFirestore mfirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notas_nuevas, container, false);

        mfirestore = FirebaseFirestore.getInstance();

        nTtitulo = v.findViewById(R.id.tituloNt);
        ntDesc = v.findViewById(R.id.descNt);
        btnNt = v.findViewById(R.id.btnIngNt);

        ntDesc.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        ntDesc.setLines(300);
        ntDesc.setVerticalScrollBarEnabled(true);

        btnNt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloN = nTtitulo.getText().toString().trim();
                String descN = ntDesc.getText().toString().trim();

                if (tituloN.isEmpty() || descN.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, ingresa todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    nuevaNt(tituloN, descN);
                }
            }
        });

        return v;
    }

    private void nuevaNt(String tituloN, String descN) {
        Map<String, Object> map = new HashMap<>();
        map.put("Titulo", tituloN);
        map.put("Descripcion", descN);

        mfirestore.collection("Notas").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Nota guardada exitosamente", Toast.LENGTH_SHORT).show();

                // Cerrar el diálogo para regresar al fragmento anterior
                if (getDialog() != null) {
                    getDialog().dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al guardar la nota", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }
}
