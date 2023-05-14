package com.example.rickymortyfinalad;

import static android.os.Bundle.EMPTY;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    FragmentContainerView fragmentContainerView;
    String nombre;
    String foto;
    String status;
    TextView seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_juego);

        nombre = "";
        foto = "";
        status = "";
        //fragmentContainerView = findViewById(R.id.fragmentContainerView);
        imageView = findViewById(R.id.charImage);
        textView = findViewById(R.id.textName);
        btnDead = findViewById(R.id.btnDead);
        btnAlive = findViewById(R.id.btnAlive);
        btnUnknown = findViewById(R.id.btnUnknown);
        btnBack = findViewById(R.id.btnBack);
        seconds = findViewById(R.id.textSeconds);
        getChar();

        Context ctx = this;

        Bundle extras = getIntent().getExtras();

        if (extras == null){
            new CountDownTimer(30000, 1000) {

                public void onTick(long millisUntilFinished) {
                    seconds.setText("" + millisUntilFinished / 1000);
                }

                public void onFinish() {

                    seconds.setText("Done!");
                    Intent i = new Intent(ActividadJuego.this, FinishScreen.class);
                    startActivity(i);
                    finish();
                    cancel();
                }
            }.start();

        } else {
            String segundos = extras.getString("CONTADOR");
            seconds.setText(segundos);
            int number = Integer.parseInt(segundos);

            new CountDownTimer(number * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    seconds.setText("" + millisUntilFinished / 1000);
                }

                public void onFinish() {

                    seconds.setText("Done!");
                    Intent i = new Intent(ActividadJuego.this, FinishScreen.class);
                    startActivity(i);
                    finish();
                    cancel();
                }
            }.start();
        }





        btnAlive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("Alive")){
                    Usuario.PUNTOS++;
                    Toast.makeText(ActividadJuego.this, "CORRECTO | Sus puntos: " + Usuario.PUNTOS, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ActividadJuego.this, ActividadJuego.class);
                    Bundle extras = new Bundle();
                    extras.putInt("PUNTOS", Usuario.PUNTOS);
                    extras.putString("CONTADOR", seconds.getText().toString());
                    i.putExtras(extras);
                    startActivity(i);
                } else {
                    Toast.makeText(ActividadJuego.this, "No es así", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("Dead")){
                    Usuario.PUNTOS++;
                    Toast.makeText(ActividadJuego.this, "CORRECTO | Sus puntos: " + Usuario.PUNTOS, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ActividadJuego.this, ActividadJuego.class);
                    Bundle extras = new Bundle();
                    extras.putInt("PUNTOS", Usuario.PUNTOS);
                    extras.putString("CONTADOR", seconds.getText().toString());
                    i.putExtras(extras);
                    startActivity(i);
                } else {
                    Toast.makeText(ActividadJuego.this, "No es así", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUnknown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("unknown")){
                    Usuario.PUNTOS++;
                    Toast.makeText(ActividadJuego.this, "CORRECTO | Sus puntos: " + Usuario.PUNTOS, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ActividadJuego.this, ActividadJuego.class);
                    Bundle extras = new Bundle();
                    extras.putInt("PUNTOS", Usuario.PUNTOS);
                    extras.putString("CONTADOR", seconds.getText().toString());
                    i.putExtras(extras);
                    startActivity(i);
                } else {
                    Toast.makeText(ActividadJuego.this, "No es así", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActividadJuego.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void getChar() {
        Context ctx = this;
        final int min = 1;
        final int max = 826;
        final int id = new Random().nextInt((max - min) + 1) + min;
        final String x = String.valueOf(id);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Character> call = servicioApi.getChar(id);

        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                if (response.body() != null) {

                    Character personaje = response.body();
                    nombre = personaje.getName();
                    status = personaje.getStatus();
                    foto = personaje.getImage();
                    //sumar 1 al id
                    textView.setText(nombre);

                    /*RequestOptions options = new RequestOptions().centerCrop()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round);*/
                    //Glide.with(ctx).load(foto).into(imageView);
                    //adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Toast.makeText(ActividadJuego.this, "Fallo cargando personaje", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
