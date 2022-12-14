package com.example.rickymortyfinalad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MiAdaptadorEp extends RecyclerView.Adapter<MiAdaptadorEp.MyViewHolder> {
    private final ArrayList<String> nombresEp;
    private final ArrayList<String> fechaAire;
    private final ArrayList<String> codigoEp;
    private final Context context;


    public MiAdaptadorEp(ArrayList<String> nombresEp, ArrayList<String> fechaAire, ArrayList<String> codigoEp, Context context) {
        this.nombresEp = nombresEp;
        this.fechaAire = fechaAire;
        this.codigoEp = codigoEp;
        this.context = context;
    }

    @NonNull
    @Override
    public MiAdaptadorEp.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento3_layout, parent, false);
        return new MiAdaptadorEp.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MiAdaptadorEp.MyViewHolder holder, int position) {
        holder.titulo3.setText(nombresEp.get(position));
        holder.subtitulo3.setText(fechaAire.get(position));
        holder.subtitulo4.setText(codigoEp.get(position));

    }
    @Override
    public int getItemCount() {
        return (nombresEp != null) ? nombresEp.size() : 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo3, subtitulo3, subtitulo4;
        public MyViewHolder(@NonNull View v) {
            super(v);
            titulo3 = v.findViewById(R.id.titulo3);
            subtitulo3 = v.findViewById(R.id.subtitulo3);
            subtitulo4 = v.findViewById(R.id.subtitulo4);
        }
    }
}