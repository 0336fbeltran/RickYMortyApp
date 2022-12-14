package com.example.rickymortyfinalad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    }
}