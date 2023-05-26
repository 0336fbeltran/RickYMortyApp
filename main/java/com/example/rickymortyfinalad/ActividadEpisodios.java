package com.example.rickymortyfinalad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import android.os.Bundle;

import org.checkerframework.checker.units.qual.A;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActividadEpisodios extends AppCompatActivity {
    private RecyclerView reciclador3;
    private RecyclerView.LayoutManager layoutManager;
    private MiAdaptadorEp adaptador3;

    private int pagina;
    ImageButton btnPrev3, btnNext3;

    ArrayList<Episode> episodios;
    String busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_episodios);
        reciclador3 = findViewById(R.id.reciclador3);

        btnPrev3 = findViewById(R.id.btnPrev3);
        btnNext3 = findViewById(R.id.btnNext3);
        pagina = 1;

        episodios = new ArrayList<>();

        adaptador3 = new MiAdaptadorEp(episodios, this);
        reciclador3.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        reciclador3.setLayoutManager(linearLayoutManager);

        reciclador3.setAdapter(adaptador3);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            Toast.makeText(this, "No hay búsqueda", Toast.LENGTH_LONG).show();
        } else {
            busqueda = extras.getString(Intent.EXTRA_TEXT);
            if (busqueda == ""){
                getTodes();
            } else {
                getPostEp();
            }

            btnPrev3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pagina--;
                    if (busqueda == ""){
                        getTodes();
                    } else {
                        getPostEp();
                    }
                }
            });

            btnNext3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pagina++;
                    if (busqueda == "") {
                        getTodes();
                    } else {
                        getPostEp();
                    }
                }
            });
        }
    }

    private void getPostEp() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados3> call = servicioApi.getPostEp(busqueda, pagina);

        call.enqueue(new Callback<Resultados3>() {
            @Override
            public void onResponse(Call<Resultados3> call, Response<Resultados3> response) {
                if (response.body() != null) {
                    Resultados3 resultados = response.body();
                    episodios = resultados.getResults();
                    adaptador3.actualizarDatos(episodios);
                }
            }

            @Override
            public void onFailure(Call<Resultados3> call, Throwable t) {
                Toast.makeText(ActividadEpisodios.this, "Fallo cargando episodios", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getTodes() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados3> call = servicioApi.getTodes(pagina);

        call.enqueue(new Callback<Resultados3>() {
            @Override
            public void onResponse(Call<Resultados3> call, Response<Resultados3> response) {
                if (response.body() != null) {
                    Resultados3 resultados = response.body();
                    episodios = resultados.getResults();
                    adaptador3.actualizarDatos(episodios);
                }
            }

            @Override
            public void onFailure(Call<Resultados3> call, Throwable t) {
                Toast.makeText(ActividadEpisodios.this, "Fallo cargando episodios", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
