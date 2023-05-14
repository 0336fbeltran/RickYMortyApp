package com.example.rickymortyfinalad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPuntos extends
        RecyclerView.Adapter<AdaptadorPuntos.MyViewHolder> {
    private final ArrayList<Integer> puntuaciones;

    public AdaptadorPuntos(ArrayList<Integer> puntuaciones, FinishScreen finishScreen) {
        this.puntuaciones = puntuaciones;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista_puntuaciones, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(String.valueOf(puntuaciones.get(position)));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(@NonNull View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
        }
    }
}