package com.example.rickymortyfinalad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MiAdaptador extends
        RecyclerView.Adapter<MiAdaptador.MyViewHolder> {
    private final ArrayList<String> nombres;
    private final ArrayList<String> especies;
    private final ArrayList<String> fotos;
    private final Context context;

    public MiAdaptador(ArrayList<String> nombres, ArrayList<String> especies, ArrayList<String> fotos, Context context) {
        this.nombres = nombres;
        this.especies = especies;
        this.fotos = fotos;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_layout, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.titulo.setText(nombres.get(position));
        holder.subtitulo.setText(especies.get(position));
        RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(context).load(fotos.get(position)).apply(options).into(holder.icon);
    }
    @Override
    public int getItemCount() {
        return (nombres != null) ? nombres.size() : 0;
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