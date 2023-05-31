package com.example.rickymortyfinalad;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicioApi {

    @GET("character/?")
    Call<Resultados> getPost(@Query("page") int pagina, @Query("name") String busqueda);

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

    @GET("character")
    Call<Resultados> getPersonajesPorFiltro(
            @Query("species") String especie,
            @Query("gender") String genero,
            @Query("status") String estado,
            @Query("page") int pagina
    );

    @GET("character/{id}")
    Call<Character> getChar(@Path("id") int id);

    @GET("character/{id}")
    Call<Character> getCharFav(@Path("id") int id);
}
