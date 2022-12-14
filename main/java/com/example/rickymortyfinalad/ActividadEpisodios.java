package com.example.rickymortyfinalad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

    ArrayList<String> nombres;
    ArrayList<String> fechasEp;
    ArrayList<String> episodes;
    String busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_episodios);
        reciclador3 = findViewById(R.id.reciclador3);

        nombres = new ArrayList<>();
        fechasEp = new ArrayList<>();
        episodes = new ArrayList<>();

        adaptador3 = new MiAdaptadorEp(nombres, fechasEp, episodes, this);
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

        }

    }

    private void getPostEp() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados3> call = servicioApi.getPostEp(busqueda);

        call.enqueue(new Callback<Resultados3>() {
            @Override
            public void onResponse(Call<Resultados3> call, Response<Resultados3> response) {
                if (response.body() != null) {
                    Resultados3 resultados = response.body();
                    ArrayList<Episode> listaEpisodios = resultados.getResults();
                    for (int i = 0; i < listaEpisodios.size(); i++){
                        nombres.add(resultados.getResults().get(i).getName());
                        fechasEp.add(resultados.getResults().get(i).getAir_date());
                        episodes.add(resultados.getResults().get(i).getEpisode());
                    }
                    adaptador3.notifyDataSetChanged();
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
        Call<Resultados3> call = servicioApi.getTodes();

        call.enqueue(new Callback<Resultados3>() {
            @Override
            public void onResponse(Call<Resultados3> call, Response<Resultados3> response) {
                if (response.body() != null) {
                    Resultados3 resultados = response.body();
                    ArrayList<Episode> listaEpisodios = resultados.getResults();
                    for (int i = 0; i < listaEpisodios.size(); i++){
                        nombres.add(resultados.getResults().get(i).getName());
                        fechasEp.add(resultados.getResults().get(i).getAir_date());
                        episodes.add(resultados.getResults().get(i).getEpisode());

                    }
                    adaptador3.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Resultados3> call, Throwable t) {
                Toast.makeText(ActividadEpisodios.this, "Fallo cargando episodios", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
