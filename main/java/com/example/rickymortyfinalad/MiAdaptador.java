package com.example.rickymortyfinalad;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MiAdaptador extends
        RecyclerView.Adapter<MiAdaptador.MyViewHolder> {
    private ArrayList<Character> listaPersonajes;
    private ArrayList<String> nombres;
    private ArrayList<String> especies;
    private ArrayList<String> tipos;
    private ArrayList<String> generos;
    private ArrayList<String> estados;
    private ArrayList<String> fotos;
    private Context context;

    public MiAdaptador(ArrayList<String> nombres, ArrayList<String> especies, ArrayList<String> tipos, ArrayList<String> generos, ArrayList<String> estados, ArrayList<String> fotos, Context context) {
        this.nombres = nombres;
        this.especies = especies;
        this.tipos = tipos;
        this.generos = generos;
        this.estados = estados;
        this.fotos = fotos;
        this.context = context;
    }

    public MiAdaptador(ArrayList<Character> listaPersonajes, Context context){
        this.listaPersonajes = listaPersonajes;
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
        //VHolder2 vHolder2 = new VHolder2(context);
        holder.titulo.setText(listaPersonajes.get(position).getName());
        //holder.subtitulo.setText(especies.get(position));
        RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(context).load(listaPersonajes.get(position).getImage()).apply(options).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return (listaPersonajes != null) ? listaPersonajes.size() : 0;
    }

    /*
    public void actualizarDatos(ArrayList<String> nombres2, ArrayList<String> especies2, ArrayList<String> tipos2, ArrayList<String> generos2, ArrayList<String> estados2, ArrayList<String> fotos2) {
        nombres.clear();
        especies.clear();
        tipos.clear();
        generos.clear();
        estados.clear();
        fotos.clear();

        nombres.addAll(nombres2);
        especies.addAll(especies2);
        tipos.addAll(tipos2);
        generos.addAll(generos2);
        estados.addAll(estados2);
        fotos.addAll(fotos2);

        notifyDataSetChanged();
    }
*/

    public void actualizarDatos(ArrayList<Character> listaPersonajes2){
        listaPersonajes = listaPersonajes2;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView titulo, subtitulo, textNombre, textSpecies, textType, textGender, textStatus;
        public ImageView icon, icon2;
        public int globalPosition;
        public Group marco;

        public MyViewHolder(@NonNull View v) {
            super(v);
            titulo = v.findViewById(R.id.titulo2);
            //subtitulo = v.findViewById(R.id.subtitulo);
            icon = v.findViewById(R.id.icono);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globalPosition = getBindingAdapterPosition();
                    //Crea dialog
                    Dialog dialog = new Dialog(v.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.marcopj);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

                    textNombre = dialog.findViewById(R.id.textNombre);
                    textSpecies = dialog.findViewById(R.id.textSpecies);
                    textType = dialog.findViewById(R.id.textType);
                    textGender = dialog.findViewById(R.id.textGender);
                    textStatus = dialog.findViewById(R.id.textStatus);
                    icon2 = dialog.findViewById(R.id.foto);
                    dialog.show();

                    //textStatus.setText(estados.get(globalPosition));
                    textNombre.setText(listaPersonajes.get(globalPosition).getName());
                    textSpecies.setText(listaPersonajes.get(globalPosition).getSpecies());
                    textType.setText(listaPersonajes.get(globalPosition).getType());
                    textGender.setText(listaPersonajes.get(globalPosition).getGender());

                    textStatus.setText(listaPersonajes.get(globalPosition).getStatus());
                    RequestOptions options = new RequestOptions().centerCrop()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round);
                    Glide.with(context).load(listaPersonajes.get(globalPosition).getImage()).apply(options).into(icon2);


                    ImageButton dialogBtn_cancelar = dialog.findViewById(R.id.botonCerrar);
                    dialogBtn_cancelar.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }
            });
        }
    }
}