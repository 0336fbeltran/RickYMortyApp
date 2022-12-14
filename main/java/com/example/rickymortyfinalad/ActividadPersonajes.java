package com.example.rickymortyfinalad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActividadPersonajes extends AppCompatActivity {
    private RecyclerView reciclador;
    private RecyclerView.LayoutManager layoutManager;
    private MiAdaptador adaptador;

    ArrayList<String> nombres;
    ArrayList<String> especie;
    ArrayList<String> fotos;
    String busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_personajes);
        reciclador = findViewById(R.id.reciclador);

        nombres = new ArrayList<>();
        especie = new ArrayList<>();
        fotos = new ArrayList<>();

        adaptador= new MiAdaptador(nombres, especie, fotos, this);
        reciclador.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        reciclador.setLayoutManager(linearLayoutManager);

        reciclador.setAdapter(adaptador);

       Bundle extras = getIntent().getExtras();
       if(extras == null) {
            Toast.makeText(this, "No hay búsqueda", Toast.LENGTH_LONG).show();
       } else {
            busqueda = extras.getString(Intent.EXTRA_TEXT);
            if (busqueda == ""){
                getTodos();
            } else {
                getPosts();
            }

       }

    }

    private void getPosts() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados> call = servicioApi.getPost(busqueda);

        call.enqueue(new Callback<Resultados>() {
            @Override
            public void onResponse(Call<Resultados> call, Response<Resultados> response) {
                if (response.body() != null) {
                    Resultados resultados = response.body();
                    ArrayList<Character> listaPersonajes = resultados.getResults();
                    for (int i = 0; i < listaPersonajes.size(); i++){
                        nombres.add(resultados.getResults().get(i).getName());
                        especie.add(resultados.getResults().get(i).getSpecies());
                        fotos.add(resultados.getResults().get(i).getImage());
                    }
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Resultados> call, Throwable t) {
                Toast.makeText(ActividadPersonajes.this, "Fallo cargando nombres", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getTodos() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados> call = servicioApi.getTodos();

        call.enqueue(new Callback<Resultados>() {
            @Override
            public void onResponse(Call<Resultados> call, Response<Resultados> response) {
                if (response.body() != null) {
                    Resultados resultados = response.body();
                    ArrayList<Character> listaPersonajes = resultados.getResults();
                    for (int i = 0; i < listaPersonajes.size(); i++){
                        nombres.add(resultados.getResults().get(i).getName());
                        especie.add(resultados.getResults().get(i).getSpecies());
                        fotos.add(resultados.getResults().get(i).getImage());

                    }
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Resultados> call, Throwable t) {
                Toast.makeText(ActividadPersonajes.this, "Fallo cargando nombres", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
