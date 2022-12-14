package com.example.rickymortyfinalad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MiAdaptadorJuego {

    private final int id;
    private final String nombre;
    private final String foto;
    private final String status;
    private final Context context;

    public MiAdaptadorJuego(int id, String nombre, String foto, String status, Context context) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.status = status;
        this.context = context;
    }
    @NonNull
    public MiAdaptadorJuego.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_layout, parent, false);
        return new MiAdaptadorJuego.MyViewHolder(v);
    }

    public void onBindViewHolder(MiAdaptadorJuego.MyViewHolder holder, int position) {
        holder.titulo.setText(nombre);
        holder.subtitulo.setText(status);
        RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(context).load(foto).apply(options).into(holder.icon);
    }

    public int getItemCount() {
        int i = 1;
        return i;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo, subtitulo;
        public ImageView icon;

        public MyViewHolder(@NonNull View v) {
            super(v);
            titulo = v.findViewById(R.id.titulo2);
            subtitulo = v.findViewById(R.id.subtitulo);
            icon = v.findViewById(R.id.icono);
        }
    }
}
