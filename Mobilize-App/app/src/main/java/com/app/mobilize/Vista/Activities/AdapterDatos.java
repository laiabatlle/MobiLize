package com.app.mobilize.Vista.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.app.mobilize.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sqliteopenhelper.Exercici;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    ArrayList<Exercici> Exercicis;
    private OnNoteListener OnNoteListener;
    String crear;

    public AdapterDatos(ArrayList<Exercici> Exercicis, OnNoteListener OnNoteListener, String crear) {
        this.Exercicis = Exercicis;
        this.OnNoteListener = OnNoteListener;
        this.crear = crear;
    }
    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec, null, false);
        return new ViewHolderDatos(view, OnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, final int position) {

        holder.asignarDatos(Exercicis.get(position));



       /* String nom = getItem(position).getNom();
        String info = getItem(position).getInfo();
        int nivell = getItem(position).getNivell();
        String modalitat = getItem(position).getModalitat();
        String exercicis = getItem(position).getExercicis();

        final Exercici exercici = new Exercici(nom,info,nivell,modalitat, exercicis); */


        holder.cb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()) {
                    if(crear.equals("si")) {
                        AfegirRutina.setExercici(Exercicis.get(position));
                    }
                    else PopUpRutina.setExercici(Exercicis.get(position));
                }
                else  {
                    if(crear.equals("si")) {
                        AfegirRutina.unsetExercici(Exercicis.get(position));
                    }
                    else PopUpRutina.unsetExercici(Exercicis.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Exercicis.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView tv;
        CheckBox cb;
        OnNoteListener OnNoteListener;
        public ViewHolderDatos(@NonNull View itemView, OnNoteListener OnNoteListener) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView11);
            cb = (CheckBox) itemView.findViewById(R.id.checkBox);
            if(crear.equals("no")) cb.setChecked(true);
            this.OnNoteListener = OnNoteListener;
            itemView.setOnClickListener(this);
        }

        public void asignarDatos(Exercici exercici) {
            tv.setText(exercici.getNom());
            PopUpRutina.setExercici(exercici);

        }


        @Override
        public void onClick(View v) {

            OnNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
