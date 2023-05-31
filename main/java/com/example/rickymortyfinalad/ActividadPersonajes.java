package com.example.rickymortyfinalad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActividadPersonajes extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    private ArrayList<Character> listaPersonajes;
    private ArrayList<Character> lista;
    private int pjFav;

    private MiAdaptador adaptador;
    private ArrayList<String> especies;
    private ArrayList<String> generos;
    private ArrayList<String> estados;
    String especieSeleccionada;
    String generoSeleccionado;
    String estadoSeleccionado;
    private String busqueda;
    private int pagina;
    private Spinner spinnerEspecies;
    private Spinner spinnerGeneros;
    private Spinner spinnerEstados;
    private ArrayAdapter<String> especiesAdapter;
    private ArrayAdapter<String> generosAdapter;
    private ArrayAdapter<String> estadosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_personajes);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        CollectionReference favoritos = mFirestore.collection("favoritos");
        String id = mAuth.getCurrentUser().getUid();
        listaPersonajes = new ArrayList<>();
        adaptador = new MiAdaptador(listaPersonajes, this);
        RecyclerView recyclerView = findViewById(R.id.reciclador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);

        lista = new ArrayList<>();
        especies = new ArrayList<>();
        generos = new ArrayList<>();
        estados = new ArrayList<>();

        busqueda = "";
        pagina = 1;

        spinnerEspecies = findViewById(R.id.spinnerEspecies);
        spinnerGeneros = findViewById(R.id.spinnerGeneros);
        spinnerEstados = findViewById(R.id.spinnerEstados);

        especiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, especies);
        generosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generos);
        estadosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estados);

        spinnerEspecies.setAdapter(especiesAdapter);
        spinnerGeneros.setAdapter(generosAdapter);
        spinnerEstados.setAdapter(estadosAdapter);

        ImageButton btnBuscar = findViewById(R.id.btnFiltrar);
        ImageButton btnQuitarFiltros = findViewById(R.id.btnQuitarFiltros);
        ImageButton btnPrev = findViewById(R.id.btnPrev);
        ImageButton btnNext = findViewById(R.id.btnNext);
        //ImageButton btnListarFav = findViewById(R.id.btnListarFav);

        Bundle extras = getIntent().getExtras();
        busqueda = extras.getString(Intent.EXTRA_TEXT);

        rellenarSpinners();

        if (busqueda.isEmpty()) {
            getTodos();
        } else {
            getPosts(busqueda);
        }

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                especieSeleccionada = spinnerEspecies.getSelectedItem().toString();
                generoSeleccionado = spinnerGeneros.getSelectedItem().toString();
                estadoSeleccionado = spinnerEstados.getSelectedItem().toString();
                listaPersonajes = filtrarPersonajes(especieSeleccionada, generoSeleccionado, estadoSeleccionado);
                adaptador.actualizarDatos(listaPersonajes);
            }
        });

        btnQuitarFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (busqueda.isEmpty()) {
                    getTodos();
                } else {
                    getPosts(busqueda);
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagina--;
                listaPersonajes.clear();
                if (especieSeleccionada != null || generoSeleccionado != null || estadoSeleccionado != null) {
                    listaPersonajes = filtrarPersonajes(especieSeleccionada, generoSeleccionado, estadoSeleccionado);
                    adaptador.actualizarDatos(listaPersonajes);
                } else {
                    if (busqueda.isEmpty()) {
                        getTodos();
                    } else {
                        getPosts(busqueda);
                    }
                }
            }
        });

        /*btnListarFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritos.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                pjFav = Objects.requireNonNull(document.getLong("id")).intValue();
                                getFavs(pjFav);
                            } else {
                                Toast.makeText(ActividadPersonajes.this, "No existe el documento", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ActividadPersonajes.this, "La tarea no se ha podido completar", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActividadPersonajes.this, "Fallo accediendo a la BD de favoritos", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagina++;
                listaPersonajes.clear();
                if (especieSeleccionada != null || generoSeleccionado != null || estadoSeleccionado != null) {
                    listaPersonajes = filtrarPersonajes(especieSeleccionada, generoSeleccionado, estadoSeleccionado);
                    adaptador.actualizarDatos(listaPersonajes);
                } else {
                    if (busqueda.isEmpty()) {
                        getTodos();
                    } else {
                        getPosts(busqueda);
                    }
                }
            }
        });
    }

    private void getFavs(int idChar) {
        lista.clear();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);

        Call<Character> call = servicioApi.getCharFav(idChar);
        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                //if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Character personaje = response.body();
                        lista.add(personaje);
                        adaptador.actualizarDatos(lista);
                    } else {
                        Toast.makeText(ActividadPersonajes.this, "No se encontraron resultados 1", Toast.LENGTH_SHORT).show();
                    }
                //} else {
                  //  Toast.makeText(ActividadPersonajes.this, "Error en la respuesta 2", Toast.LENGTH_SHORT).show();
                //}
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Toast.makeText(ActividadPersonajes.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getPosts(String busqueda) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados> call = servicioApi.getPost(pagina, busqueda);

        call.enqueue(new Callback<Resultados>() {
            @Override
            public void onResponse(Call<Resultados> call, Response<Resultados> response) {
                if (response.isSuccessful()) {
                    Resultados resultados = response.body();
                    if (resultados != null) {
                        listaPersonajes = resultados.getResults();
                        adaptador.actualizarDatos(listaPersonajes);
                    } else {
                        Toast.makeText(ActividadPersonajes.this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActividadPersonajes.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Resultados> call, Throwable t) {
                Toast.makeText(ActividadPersonajes.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTodos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        Call<Resultados> call = servicioApi.getTodos(pagina);

        call.enqueue(new Callback<Resultados>() {
            @Override
            public void onResponse(Call<Resultados> call, Response<Resultados> response) {
                if (response.isSuccessful()) {
                    Resultados resultados = response.body();
                    if (resultados != null) {
                        listaPersonajes = resultados.getResults();
                        adaptador.actualizarDatos(listaPersonajes);
                    } else {
                        Toast.makeText(ActividadPersonajes.this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActividadPersonajes.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Resultados> call, Throwable t) {
                Toast.makeText(ActividadPersonajes.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<Character> filtrarPersonajes(String especie, String genero, String estado) {
        ArrayList<Character> personajesFiltrados = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        obtenerPersonajesPorFiltros(servicioApi, pagina, especie, genero, estado, personajesFiltrados);

        return personajesFiltrados;
    }

    private void obtenerPersonajesPorFiltros(ServicioApi servicioApi, int pagina, String especie, String genero, String estado, ArrayList<Character> personajesFiltrados) {
        Call<Resultados> call = servicioApi.getPersonajesPorFiltro(especie, genero, estado, pagina);

        call.enqueue(new Callback<Resultados>() {
            @Override
            public void onResponse(Call<Resultados> call, Response<Resultados> response) {
                if (response.isSuccessful()) {
                    Resultados resultados = response.body();
                    if (resultados != null) {
                        ArrayList<Character> personajes = resultados.getResults();

                        for (Character personaje : personajes) {
                            String especie2 = personaje.getSpecies();
                            String genero2 = personaje.getGender();
                            String estado2 = personaje.getStatus();

                            if (!especies.contains(especie2)) {
                                especies.add(especie2);
                            }
                            if (!generos.contains(genero2)) {
                                generos.add(genero2);
                            }
                            if (!estados.contains(estado2)) {
                                estados.add(estado2);
                            }

                            if ((especie == null || especie.isEmpty() || especie.equals(especie2))
                                    && (genero == null || genero.isEmpty() || genero.equals(genero2))
                                    && (estado == null || estado.isEmpty() || estado.equals(estado2))) {
                                personajesFiltrados.add(personaje);
                            }
                        }
                        adaptador.actualizarDatos(personajesFiltrados);
                    } else {
                        Toast.makeText(ActividadPersonajes.this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActividadPersonajes.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Resultados> call, Throwable t) {
                Toast.makeText(ActividadPersonajes.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rellenarSpinners() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioApi servicioApi = retrofit.create(ServicioApi.class);
        obtenerPersonajes(servicioApi, 1);
    }

    private void obtenerPersonajes(ServicioApi servicioApi, int pagina) {
        Call<Resultados> call = servicioApi.getTodos(pagina);

        call.enqueue(new Callback<Resultados>() {
            @Override
            public void onResponse(Call<Resultados> call, Response<Resultados> response) {
                if (response.isSuccessful()) {
                    Resultados resultados = response.body();
                    if (resultados != null) {
                        ArrayList<Character> personajes = resultados.getResults();

                        for (Character personaje : personajes) {
                            String especie = personaje.getSpecies();
                            String genero = personaje.getGender();
                            String estado = personaje.getStatus();

                            if (!especies.contains(especie)) {
                                especies.add(especie);
                            }
                            if (!generos.contains(genero)) {
                                generos.add(genero);
                            }
                            if (!estados.contains(estado)) {
                                estados.add(estado);
                            }
                        }

                        especiesAdapter.notifyDataSetChanged();
                        generosAdapter.notifyDataSetChanged();
                        estadosAdapter.notifyDataSetChanged();

                        // Verificar si hay más páginas disponibles
                        if (resultados.getInfo() != null && resultados.getInfo().getNext() != null) {
                            obtenerPersonajes(servicioApi, pagina + 1);
                        }
                    } else {
                        Toast.makeText(ActividadPersonajes.this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActividadPersonajes.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Resultados> call, Throwable t) {
                Toast.makeText(ActividadPersonajes.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }
}