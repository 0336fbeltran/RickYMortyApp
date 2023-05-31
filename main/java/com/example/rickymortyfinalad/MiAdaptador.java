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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MiAdaptador extends
        RecyclerView.Adapter<MiAdaptador.MyViewHolder> {
    private ArrayList<Character> listaPersonajes;
    private Context context;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

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
        holder.titulo.setText(listaPersonajes.get(position).getName());

        RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(context).load(listaPersonajes.get(position).getImage()).apply(options).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return (listaPersonajes != null) ? listaPersonajes.size() : 0;
    }

    public void actualizarDatos(ArrayList<Character> listaPersonajes2){
        listaPersonajes = listaPersonajes2;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView titulo, subtitulo, textNombre, textSpecies, textType, textGender, textStatus;
        public ImageView icon, icon2;
        public int globalPosition;
        int pjFavId;

        public MyViewHolder(@NonNull View v) {
            super(v);
            titulo = v.findViewById(R.id.titulo2);
            icon = v.findViewById(R.id.icono);
            mAuth = FirebaseAuth.getInstance();
            mFirestore = FirebaseFirestore.getInstance();
            CollectionReference favoritos = mFirestore.collection("favoritos");
            String id = mAuth.getCurrentUser().getUid();
            Map<String, Object> map = new HashMap<>();

            favoritos.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            pjFavId = Objects.requireNonNull(document.getLong("id")).intValue();
                            map.put("id", pjFavId);
                        } else {
                            pjFavId = 1;
                            map.put("id", pjFavId);
                        }
                    }else {
                        Toast.makeText(context, "La tarea no se ha podido completar", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Fallo accediendo a la BD de favoritos", Toast.LENGTH_SHORT).show();
                }
            });

            if (map.isEmpty()) {
                favoritos.document(id).set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "El documento NO se creó correctamente", Toast.LENGTH_SHORT).show();
                    }
                });
            }

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

                    textNombre.setText(listaPersonajes.get(globalPosition).getName());
                    textSpecies.setText(listaPersonajes.get(globalPosition).getSpecies());
                    textType.setText(listaPersonajes.get(globalPosition).getType());
                    textGender.setText(listaPersonajes.get(globalPosition).getGender());

                    textStatus.setText(listaPersonajes.get(globalPosition).getStatus());
                    RequestOptions options = new RequestOptions().centerCrop()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round);
                    Glide.with(context).load(listaPersonajes.get(globalPosition).getImage()).apply(options).into(icon2);

                    //ImageButton btnFav = dialog.findViewById(R.id.btnFav);
                    /*btnFav.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            favoritos.document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        if (documentSnapshot.contains("id")) {
                                            btnFav.setBackgroundColor(0xFF8BC34A);
                                            pjFavId = Objects.requireNonNull(documentSnapshot.getLong("id")).intValue();
                                            map.put("id", pjFavId);
                                            favoritos.document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        pjFavId = Objects.requireNonNull(documentSnapshot.getLong("id")).intValue();
                                                        map.put("id", pjFavId);
                                                        Toast.makeText(context, "Personaje añadido a favoritos", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(context, "No se pudo completar la tarea", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(context, "el documento no tiene el campo id", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(context, "El documento no existe", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });*/

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