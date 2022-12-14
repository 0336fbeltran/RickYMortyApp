package com.example.rickymortyfinalad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.GetChars;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActividadJuego extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    View v;
    Button btnDead;
    Button btnAlive;
    Button btnUnknown;
    ImageButton btnBack;
    Context context;
    FragmentContainerView fragmentContainerView;
    ArrayList<String> nombre;
    ArrayList<String> foto;
    ArrayList<String> status;
    ListView list;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_juego);

        nombre = new ArrayList<>();
        foto = new ArrayList<>();
        status = new ArrayList<>();
        fragmentContainerView = findViewById(R.id.fragmentContainerView);
        imageView = findViewById(R.id.charImage);           // Comprobar si view
        textView = findViewById(R.id.textName);
        btnDead = findViewById(R.id.btnDead);
        btnAlive = findViewById(R.id.btnAlive);
        btnUnknown = findViewById(R.id.btnUnknown);
        btnBack = findViewById(R.id.btnBack);

        getChar();


    }

    private void getChar() {

        final int min = 1;
        final int max = 826;
        final int y = new Random().nextInt((max - min) + 1) + min;
        final String x = String.valueOf(y);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados> call = servicioApi.getChar(y);

        call.enqueue(new Callback<Resultados>() {
            @Override
            public void onResponse(Call<Resultados> call, Response<Resultados> response) {
                if (response.body() != null) {
                    Resultados resultados = response.body();
                    ArrayList<Character> personaje = resultados.getResults();
                    for (int i = 0; i <= personaje.size(); i++){
                        nombre.add(resultados.getResults().get(i).getName());
                        foto.add(resultados.getResults().get(i).getImage());
                        status.add(resultados.getResults().get(i).getStatus());
                    }
                    textView.setText(nombre.get(0));

                    /*RequestOptions options = new RequestOptions().centerCrop()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round);
                    Glide.with(fragmentContainerView).load(foto).transform(new CircleCrop()).into(imageView);*/
                    //adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Resultados> call, Throwable t) {
                Toast.makeText(ActividadJuego.this, "Fallo cargando personaje", Toast.LENGTH_SHORT).show();
            }

        });
    }


}
