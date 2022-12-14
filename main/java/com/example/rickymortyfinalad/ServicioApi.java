package com.example.rickymortyfinalad;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicioApi {

    @GET("character/?")
    Call<Resultados> getPost(@Query("name") String busqueda);

    @GET("character")
    Call<Resultados> getTodos();

    @GET("location")
    Call<Resultados2> getTodas();

    @GET("location/?")
    Call<Resultados2> getPostLoc(@Query("name") String busqueda);

    @GET("episode")
    Call<Resultados3> getTodes();

    @GET("episode/?")
    Call<Resultados3> getPostEp(@Query("name") String busqueda);

    @GET("character/[x]")
    Call<Resultados> getChar(@Query("id") int x);   //@Path("x") String x);
}
