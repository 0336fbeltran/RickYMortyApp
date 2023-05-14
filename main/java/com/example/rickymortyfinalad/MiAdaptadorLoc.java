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
        //holder.subtitulo2.setText(tipos.get(position));
        //holder.subtitulo3.setText(dimensiones.get(position));

    }
    @Override
    public int getItemCount() {
        return (nombresLoc != null) ? nombresLoc.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo2, subtitulo2, subtitulo3, textNombreLoc, textTypeLoc, textDimension;
        public ImageView icon;
        public int globalPosition;
        public MyViewHolder(@NonNull View v) {
            super(v);
            titulo2 = v.findViewById(R.id.titulo2);
            //subtitulo2 = v.findViewById(R.id.subtitulo2);
            //subtitulo3 = v.findViewById(R.id.subtitulo3);

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

                    textNombreLoc.setText(nombresLoc.get(globalPosition));
                    textTypeLoc.setText(tipos.get(globalPosition));
                    textDimension.setText(dimensiones.get(globalPosition));

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