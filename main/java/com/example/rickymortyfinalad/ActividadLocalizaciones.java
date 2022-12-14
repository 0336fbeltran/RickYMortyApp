package com.example.rickymortyfinalad;

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

public class ActividadLocalizaciones extends AppCompatActivity {
    private RecyclerView reciclador2;
    private RecyclerView.LayoutManager layoutManager2;
    private MiAdaptadorLoc adaptador2;

    ArrayList<String> nombresLoc;
    ArrayList<String> tipos;
    ArrayList<String> dimensiones;
    String busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_localizaciones);
        reciclador2 = findViewById(R.id.reciclador2);

        nombresLoc = new ArrayList<>();
        tipos = new ArrayList<>();
        dimensiones = new ArrayList<>();

        adaptador2 = new MiAdaptadorLoc(nombresLoc, tipos, dimensiones, this);
        reciclador2.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        reciclador2.setLayoutManager(linearLayoutManager);

        reciclador2.setAdapter(adaptador2);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            Toast.makeText(this, "No hay búsqueda", Toast.LENGTH_LONG).show();
        } else {
            busqueda = extras.getString(Intent.EXTRA_TEXT);
            if (busqueda == ""){
                getTodas();
            } else {
                getPostLoc();
            }

        }
    }

    private void getPostLoc() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados2> call = servicioApi.getPostLoc(busqueda);

        call.enqueue(new Callback<Resultados2>() {
            @Override
            public void onResponse(Call<Resultados2> call, Response<Resultados2> response) {
                if (response.body() != null) {
                    Resultados2 resultados = response.body();
                    ArrayList<Location> listaLocalizaciones = resultados.getResults();
                    for (int i = 0; i < listaLocalizaciones.size(); i++){
                        nombresLoc.add(resultados.getResults().get(i).getName());
                        tipos.add(resultados.getResults().get(i).getType());
                        dimensiones.add(resultados.getResults().get(i).getDimension());
                    }
                    adaptador2.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Resultados2> call, Throwable t) {
                Toast.makeText(ActividadLocalizaciones.this, "Fallo cargando localizaciones", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getTodas() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados2> call = servicioApi.getTodas();

        call.enqueue(new Callback<Resultados2>() {
            @Override
            public void onResponse(Call<Resultados2> call, Response<Resultados2> response) {
                if (response.body() != null) {
                    Resultados2 resultados = response.body();
                    ArrayList<Location> listaLocalizaciones = resultados.getResults();
                    for (int i = 0; i < listaLocalizaciones.size(); i++){
                        nombresLoc.add(resultados.getResults().get(i).getName());
                        tipos.add(resultados.getResults().get(i).getType());
                        dimensiones.add(resultados.getResults().get(i).getDimension());

                    }
                    adaptador2.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Resultados2> call, Throwable t) {
                Toast.makeText(ActividadLocalizaciones.this, "Fallo cargando localizaciones", Toast.LENGTH_SHORT).show();
            }

        });
    }
}