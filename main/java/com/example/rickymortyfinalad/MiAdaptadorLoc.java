package com.example.rickymortyfinalad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MiAdaptadorLoc extends RecyclerView.Adapter<MiAdaptadorLoc.MyViewHolder> {
    private final ArrayList<String> nombresLoc;
    private final ArrayList<String> tipos;
    private final ArrayList<String> dimensiones;
    private final Context context;

    public MiAdaptadorLoc(ArrayList<String> nombresLoc, ArrayList<String> tipos, ArrayList<String> dimensiones, Context context) {
        this.nombresLoc = nombresLoc;
        this.tipos = tipos;
        this.dimensiones = dimensiones;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento2_layout, parent, false);
        return new MiAdaptadorLoc.MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(MiAdaptadorLoc.MyViewHolder holder, int position) {
        holder.titulo2.setText(nombresLoc.get(position));
        holder.subtitulo2.setText(tipos.get(position));
        holder.subtitulo3.setText(dimensiones.get(position));

    }
    @Override
    public int getItemCount() {
        return (nombresLoc != null) ? nombresLoc.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo2, subtitulo2, subtitulo3;
        public ImageView icon;
        public MyViewHolder(@NonNull View v) {
            super(v);
            titulo2 = v.findViewById(R.id.titulo2);
            subtitulo2 = v.findViewById(R.id.subtitulo2);
            subtitulo3 = v.findViewById(R.id.subtitulo3);
        }
    }
}