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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActividadPersonajes extends AppCompatActivity {
    private RecyclerView reciclador;
    private RecyclerView.LayoutManager layoutManager;
    private MiAdaptador adaptador;
    private int pagina;
    ImageButton btnPrev, btnNext;
    ArrayList<Character> listaPersonajes;
    ArrayList<String> nombres;
    ArrayList<String> especies;
    ArrayList<String> tipos;
    ArrayList<String> generos;
    ArrayList<String> estados;
    ArrayList<String> fotos;
    String busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_personajes);
        reciclador = findViewById(R.id.reciclador);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        pagina = 1;
        listaPersonajes = new ArrayList<>();
       /* nombres = new ArrayList<>();
        especies = new ArrayList<>();
        tipos = new ArrayList<>();
        generos = new ArrayList<>();
        estados = new ArrayList<>();
        fotos = new ArrayList<>();
*/
        adaptador = new MiAdaptador(listaPersonajes, this);
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

            btnPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pagina--;
                    if (busqueda == ""){
                        getTodos();
                    } else {
                        getPosts();
                    }
                }
            });
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pagina++;
                    if (busqueda == ""){
                        getTodos();
                    } else {
                        getPosts();
                    }
                }
            });
        }
    }

    private void getPosts() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados> call = servicioApi.getPost(busqueda, pagina);

        call.enqueue(new Callback<Resultados>() {
            @Override
            public void onResponse(Call<Resultados> call, Response<Resultados> response) {
                if (response.body() != null) {
                    Resultados resultados = response.body();
                    ArrayList<Character> listaPersonajes = resultados.getResults();
                    /*for (int i = 0; i < listaPersonajes.size(); i++){
                        nombres.add(resultados.getResults().get(i).getName());
                        especies.add(resultados.getResults().get(i).getSpecies());
                        tipos.add(resultados.getResults().get(i).getType());
                        generos.add(resultados.getResults().get(i).getGender());
                        estados.add(resultados.getResults().get(i).getStatus());
                        fotos.add(resultados.getResults().get(i).getImage());
                    }*/
                    adaptador.actualizarDatos(listaPersonajes);
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
        Call<Resultados> call = servicioApi.getTodos(pagina);

        call.enqueue(new Callback<Resultados>() {
            @Override
            public void onResponse(Call<Resultados> call, Response<Resultados> response) {
                if (response.body() != null) {
                    Resultados resultados = response.body();
                    ArrayList<Character> listaPersonajes = resultados.getResults();
                    /*
                    for (int i = 0; i < listaPersonajes.size(); i++) {
                        nombres.add(resultados.getResults().get(i).getName());
                        especies.add(resultados.getResults().get(i).getSpecies());
                        tipos.add(resultados.getResults().get(i).getType());
                        generos.add(resultados.getResults().get(i).getGender());
                        estados.add(resultados.getResults().get(i).getStatus());
                        fotos.add(resultados.getResults().get(i).getImage());

                    }*/

                    adaptador.actualizarDatos(listaPersonajes);
                }
            }

            @Override
            public void onFailure(Call<Resultados> call, Throwable t) {
                Toast.makeText(ActividadPersonajes.this, "Fallo cargando nombres", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
