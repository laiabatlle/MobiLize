package com.app.mobilize.Vista.Activities;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.R;
import com.app.sqliteopenhelper.Exercici;
import com.app.sqliteopenhelper.Rutina;

import java.util.ArrayList;

public class AdapterRutPlan extends RecyclerView.Adapter<AdapterRutPlan.ViewHolderDatos> {

        ArrayList<Rutina> Rutines;
        private AdapterRutPlan.OnNoteListener OnNoteListener;
        String crear;

    public AdapterRutPlan(ArrayList<Rutina> Rutines, AdapterRutPlan.OnNoteListener OnNoteListener, String crear) {
            this.Rutines = Rutines;
            this.OnNoteListener = OnNoteListener;
            this.crear = crear;
        }
        @NonNull
        @Override
        public AdapterRutPlan.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rut, null, false);
            return new AdapterRutPlan.ViewHolderDatos(view, OnNoteListener);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderDatos holder, final int position) {

            holder.asignarDatos(Rutines.get(position));



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

                            Create_plan.setRutina(Rutines.get(position));
                        }
                        else {
                            VeurePlanning.setRutina(Rutines.get(position));
                        }
                    }
                    else  {
                        if(crear.equals(("si"))) {
                            Create_plan.unsetRutina(Rutines.get(position));
                        }
                        else VeurePlanning.unsetRutina(Rutines.get(position));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return Rutines.size();
        }

        public class ViewHolderDatos extends RecyclerView.ViewHolder implements  View.OnClickListener{
            TextView tv1;
            TextView tv2;
            CheckBox cb;
            AdapterRutPlan.OnNoteListener OnNoteListener;
            public ViewHolderDatos(@NonNull View itemView, AdapterRutPlan.OnNoteListener OnNoteListener) {
                super(itemView);
                tv1 = (TextView) itemView.findViewById(R.id.textView41);
                tv2 = (TextView) itemView.findViewById(R.id.textView42);
                cb = (CheckBox) itemView.findViewById(R.id.checkBox2);
                if(crear.equals("no")) cb.setChecked(true);
                this.OnNoteListener = OnNoteListener;
                itemView.setOnClickListener(this);
            }

            public void asignarDatos(Rutina rutina) {
                tv1.setText(rutina.getNom());
                tv2.setText(rutina.getInfo());
                VeurePlanning.setRutina(rutina);

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
