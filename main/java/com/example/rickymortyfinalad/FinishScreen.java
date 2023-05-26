package com.example.rickymortyfinalad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FinishScreen extends AppCompatActivity {
    AdaptadorPuntos adaptadorPuntos;
    TextView puntosActuales, tvPuntosMax, tvPuntosGenerales1, tvPuntosGenerales2, tvPuntosGenerales3;
    RecyclerView recyclerView;
    int puntos;
    int puntosMax;
    ArrayList<Integer> puntosGenerales;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    ImageButton back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        puntosGenerales = new ArrayList<>();
        back = findViewById(R.id.btnBack2);
        tvPuntosMax = findViewById(R.id.textPuntos2);
        puntosActuales = findViewById(R.id.textPuntos);
        tvPuntosGenerales1 = findViewById(R.id.textPuntos3);
        tvPuntosGenerales2 = findViewById(R.id.textPuntos4);
        tvPuntosGenerales3 = findViewById(R.id.textPuntos5);
        //recyclerView = findViewById(R.id.recyclerView);

        adaptadorPuntos = new AdaptadorPuntos(puntosGenerales, this);

        //recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //recyclerView.setLayoutManager(linearLayoutManager);

        //recyclerView.setAdapter(adaptadorPuntos);

        puntos = Usuario.PUNTOS;
        String s = Integer.toString(puntos);
        puntosActuales.setText(s);
        tvPuntosMax.setText(s);

        CollectionReference users = mFirestore.collection("user");
        CollectionReference puntuaciones = mFirestore.collection("puntuaciones");
        String id = mAuth.getCurrentUser().getUid();

        Map<String, Object> map = new HashMap<>();

        puntuaciones.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //if (Objects.requireNonNull(document.getData().containsKey("puntosMax"))){
                        //maps[0] = document.getData();
                        //puntosMax = (int)maps[0].get("puntosMax");
                        puntosMax = Objects.requireNonNull(document.getLong("puntosMax")).intValue();
                        String st = String.valueOf(puntosMax);
                        tvPuntosMax.setText(st);
                        map.put("puntosMax", puntosMax);
                        if (puntos > puntosMax) {
                            map.replace("puntosMax", puntos);
                            puntuaciones.document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    tvPuntosMax.setText(s);
                                    Toast.makeText(FinishScreen.this, "Puntuación máxima actualizada", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(FinishScreen.this, "Fallo actualizando puntuación máxima", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }else {
                        Toast.makeText(FinishScreen.this, "No existe el documento", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FinishScreen.this, "La tarea no ha tenido éxito", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FinishScreen.this, "Fallo accediendo a la BD de puntuaciones", Toast.LENGTH_SHORT).show();
            }
        });

        if (map.isEmpty()) {
            map.put("puntosMax", puntos);

            puntuaciones.document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //Toast.makeText(FinishScreen.this, "SE HA CARGADO LA PUNTUACIÓN POR 1ª VEZ", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(FinishScreen.this, "NO SE HA CARGADO LA PUNTUACIÓN POR 1ª VEZ", Toast.LENGTH_SHORT).show();
                }
            });
        }


        puntuaciones.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        if (Objects.requireNonNull(doc.getData()).containsKey("puntosMax")) {
                            //maps[0] = doc.getData();
                            //String idString = doc.getId();
                            int x = doc.getLong("puntosMax").intValue();
                            puntosGenerales.add(x);

                        }
                    }
                    puntosGenerales.sort(Comparator.reverseOrder());

                    //adaptadorPuntos.notifyDataSetChanged();
                    //puntosGenerales.sort(Comparator.reverseOrder());
                    tvPuntosGenerales1.setText(String.valueOf(puntosGenerales.get(0)));
                    tvPuntosGenerales2.setText(String.valueOf(puntosGenerales.get(1)));
                    tvPuntosGenerales3.setText(String.valueOf(puntosGenerales.get(2)));

                } else {
                    Toast.makeText(FinishScreen.this, "Fallo cargando puntuaciones generales", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FinishScreen.this, "Fallo accediendo a la BD de puntuaciones 2", Toast.LENGTH_SHORT).show();
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario.PUNTOS = 0;
                puntosGenerales.clear();
                startActivity(new Intent(FinishScreen.this, MainActivity.class));
            }
        });
    }
}