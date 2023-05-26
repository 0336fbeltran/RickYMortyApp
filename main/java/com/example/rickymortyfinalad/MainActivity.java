package com.example.rickymortyfinalad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText buscador = findViewById(R.id.buscador);
        Button btnPj = findViewById(R.id.btnPj);
        Button btnLoc = findViewById(R.id.btnDead);
        Button btnEp = findViewById(R.id.btnAlive);
        Button btnJuego = findViewById(R.id.btnJuego);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnPj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtBuscado = buscador.getText().toString();
                Intent i = new Intent(MainActivity.this, ActividadPersonajes.class);
                i.putExtra(Intent.EXTRA_TEXT, txtBuscado);
                startActivity(i);
            }
        });

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtBuscado = buscador.getText().toString();
                Intent i = new Intent(MainActivity.this, ActividadLocalizaciones.class);
                i.putExtra(Intent.EXTRA_TEXT, txtBuscado);
                startActivity(i);
            }
        });

        btnEp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtBuscado = buscador.getText().toString();
                Intent i = new Intent(MainActivity.this, ActividadEpisodios.class);
                i.putExtra(Intent.EXTRA_TEXT, txtBuscado);
                startActivity(i);
            }
        });

        btnJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActividadJuego.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this, Login.class);
                Toast.makeText(MainActivity.this, "Has cerrado sesión.", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
    }
}