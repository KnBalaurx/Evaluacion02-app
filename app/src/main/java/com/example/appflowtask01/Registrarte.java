package com.example.appflowtask01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registrarte extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private EditText nameR, apelR, emailR, contrasenaR;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarte); // Verifica que el layout sea el correcto

        // Inicializar Firebase
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Vincular componentes del layout
        nameR = findViewById(R.id.nomReg);
        apelR = findViewById(R.id.apellidoReg);
        emailR = findViewById(R.id.mailReg);
        contrasenaR = findViewById(R.id.contrasenaReg);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Verificación si el botón es nulo
        if (btnRegistrar == null) {
            Log.e("Registrarte", "btnRegistrar es nulo. Verifica el ID en el archivo de diseño.");
            return;
        }


        btnRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String nameUser = nameR.getText().toString().trim();
                String apellidoUser = apelR.getText().toString().trim();
                String mailUser = emailR.getText().toString().trim();
                String contrasenaUser = contrasenaR.getText().toString().trim();


                if (nameUser.isEmpty() || apellidoUser.isEmpty() || mailUser.isEmpty() || contrasenaUser.isEmpty()) {
                    Toast.makeText(Registrarte.this, "Ingrese todos sus datos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mailUser).matches()) {
                    Toast.makeText(Registrarte.this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (contrasenaUser.length() < 6) {
                    Toast.makeText(Registrarte.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerUser(nameUser, apellidoUser, mailUser, contrasenaUser);
            }
        });

    }

    private void registerUser(String nameUser, String apellidoUser, String mailUser, String contrasenaUser) {
        mAuth.createUserWithEmailAndPassword(mailUser, contrasenaUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("nombre", nameUser);
                    map.put("apellido", apellidoUser);
                    map.put("correo", mailUser);
                    map.put("contrasena", contrasenaUser);

                    mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Registrarte.this, "Usuario registrado!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(Registrarte.this, MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registrarte.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(Registrarte.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registrarte.this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}






