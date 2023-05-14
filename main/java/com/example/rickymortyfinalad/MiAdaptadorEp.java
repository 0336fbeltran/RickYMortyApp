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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
        holder.titulo3.setText(codigoEp.get(position));
    }

    @Override
    public int getItemCount() {
        return (nombresEp != null) ? nombresEp.size() : 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo3, textCode, textNombre, textDate;
        public int globalPosition;
        public MyViewHolder(@NonNull View v) {
            super(v);
            titulo3 = v.findViewById(R.id.titulo3);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globalPosition = getBindingAdapterPosition();
                    //Crea dialog
                    Dialog dialog = new Dialog(v.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.marcoep);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

                    textNombre = dialog.findViewById(R.id.txtNombreEp);
                    textCode = dialog.findViewById(R.id.txtCode);
                    textDate = dialog.findViewById(R.id.txtDate);

                    dialog.show();

                    textNombre.setText(nombresEp.get(globalPosition));
                    textCode.setText(codigoEp.get(globalPosition));
                    textDate.setText(fechaAire.get(globalPosition));

                    ImageButton dialogBtn_cancelar = dialog.findViewById(R.id.botonCerrar3);
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