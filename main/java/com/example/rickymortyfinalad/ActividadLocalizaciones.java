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

public class ActividadLocalizaciones extends AppCompatActivity {
    private RecyclerView reciclador2;
    private MiAdaptadorLoc adaptador2;

    private int pagina;
    ImageButton btnPrev2, btnNext2;

    ArrayList<Location> localizaciones;
    String busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_localizaciones);
        reciclador2 = findViewById(R.id.reciclador2);

        btnPrev2 = findViewById(R.id.btnPrev2);
        btnNext2 = findViewById(R.id.btnNext2);
        pagina = 1;

        localizaciones = new ArrayList<>();

        adaptador2 = new MiAdaptadorLoc(localizaciones, this);
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

            btnPrev2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pagina--;
                    if (busqueda == ""){
                        getTodas();
                    } else {
                        getPostLoc();
                    }
                }
            });

            btnNext2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pagina++;
                    if (busqueda == "") {
                        getTodas();
                    } else {
                        getPostLoc();
                    }
                }
            });
        }
    }

    private void getPostLoc() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados2> call = servicioApi.getPostLoc(busqueda, pagina);

        call.enqueue(new Callback<Resultados2>() {
            @Override
            public void onResponse(Call<Resultados2> call, Response<Resultados2> response) {
                if (response.body() != null) {
                    Resultados2 resultados = response.body();
                    localizaciones = resultados.getResults();
                    adaptador2.actualizarDatos(localizaciones);
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
        Call<Resultados2> call = servicioApi.getTodas(pagina);

        call.enqueue(new Callback<Resultados2>() {
            @Override
            public void onResponse(Call<Resultados2> call, Response<Resultados2> response) {
                if (response.body() != null) {
                    Resultados2 resultados = response.body();
                    localizaciones = resultados.getResults();
                    adaptador2.actualizarDatos(localizaciones);
                }
            }

            @Override
            public void onFailure(Call<Resultados2> call, Throwable t) {
                Toast.makeText(ActividadLocalizaciones.this, "Fallo cargando localizaciones", Toast.LENGTH_SHORT).show();
            }
        });
    }
}