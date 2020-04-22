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

    public AdapterDatos(ArrayList<Exercici> Exercicis) {
        this.Exercicis = Exercicis;
    }
    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec, null, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, final int position) {

        holder.asignarDatos(Exercicis.get(position));


        holder.cb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()) {
                    AfegirRutina.setExercici(Exercicis.get(position));
                    PopUpRutina.unsetExercici(Exercicis.get(position));
                }
                else  {
                    AfegirRutina.unsetExercici(Exercicis.get(position));
                    PopUpRutina.setExercici(Exercicis.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Exercicis.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tv;
        CheckBox cb;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView11);
            cb = (CheckBox) itemView.findViewById(R.id.checkBox);
        }

        public void asignarDatos(Exercici exercici) {
            tv.setText(exercici.getNom());

        }
    }
}
