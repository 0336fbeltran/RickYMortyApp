package com.example.rickymortyfinalad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    Button btnSignUp;
    EditText email, password, password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        password2 = findViewById(R.id.pass2);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = email.getText().toString().toLowerCase(Locale.ROOT).trim();
                String pass = password.getText().toString().trim();
                String pass2 = password2.getText().toString().trim();

                if (mEmail.isEmpty()) {
                    Toast.makeText(SignUp.this, "Rellene el email", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()){
                    Toast.makeText(SignUp.this, "Rellene la contraseña", Toast.LENGTH_SHORT).show();
                } else if (pass2.isEmpty()){
                    Toast.makeText(SignUp.this, "Repita la contraseña", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(pass2)){
                    Toast.makeText(SignUp.this, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(mEmail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            String id = mAuth.getCurrentUser().getUid();
                            Map<String, Object> map = new HashMap<>();
                            map.put("id", id);
                            map.put("email", mEmail);
                            map.put("password", pass);

                            mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    finish();
                                    startActivity(new Intent(SignUp.this, Login.class));
                                    Toast.makeText(SignUp.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    Toast.makeText(SignUp.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(SignUp.this, "Error generando usuario    ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}