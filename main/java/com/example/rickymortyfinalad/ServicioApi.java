package com.example.rickymortyfinalad;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicioApi {

    @GET("character/?")
    Call<Resultados> getPost(@Query("name") String busqueda, @Query("page") int pagina);

    @GET("character/")
    Call<Resultados> getTodos(@Query("page") int pagina);

    @GET("location")
    Call<Resultados2> getTodas(@Query("page") int pagina);

    @GET("location/?")
    Call<Resultados2> getPostLoc(@Query("name") String busqueda, @Query("page") int pagina);

    @GET("episode")
    Call<Resultados3> getTodes(@Query("page") int pagina);

    @GET("episode/?")
    Call<Resultados3> getPostEp(@Query("name") String busqueda, @Query("page") int pagina);

    @GET("character/{id}")
    Call<Character> getChar(@Path("id") int id);
}
