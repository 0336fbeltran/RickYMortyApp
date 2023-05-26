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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MiAdaptadorLoc extends RecyclerView.Adapter<MiAdaptadorLoc.MyViewHolder> {
    private ArrayList<Location> listaLocalizaciones;
    private final Context context;

    public MiAdaptadorLoc(ArrayList<Location> listaLocalizaciones, Context context) {
        this.listaLocalizaciones = listaLocalizaciones;
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
        holder.titulo2.setText(listaLocalizaciones.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (listaLocalizaciones != null) ? listaLocalizaciones.size() : 0;
    }

    public void actualizarDatos(ArrayList<Location> localizaciones) {
        listaLocalizaciones = localizaciones;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo2, textNombreLoc, textTypeLoc, textDimension;
        public int globalPosition;
        public MyViewHolder(@NonNull View v) {
            super(v);
            titulo2 = v.findViewById(R.id.titulo2);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globalPosition = getBindingAdapterPosition();
                    //Crea dialog
                    Dialog dialog = new Dialog(v.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.marcoloc);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

                    textNombreLoc = dialog.findViewById(R.id.txtNombreLoc);
                    textTypeLoc = dialog.findViewById(R.id.txtTypeLoc);
                    textDimension = dialog.findViewById(R.id.txtDimension);
                    dialog.show();

                    textNombreLoc.setText(listaLocalizaciones.get(globalPosition).getName());
                    textTypeLoc.setText(listaLocalizaciones.get(globalPosition).getType());
                    textDimension.setText(listaLocalizaciones.get(globalPosition).getDimension());

                    ImageButton dialogBtn_cancelar = dialog.findViewById(R.id.botonCerrar2);
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