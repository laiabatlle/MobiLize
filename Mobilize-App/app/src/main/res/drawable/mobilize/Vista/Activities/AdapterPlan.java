package com.app.mobilize.Vista.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.R;
import com.app.sqliteopenhelper.Planning;

import java.util.ArrayList;

public class AdapterPlan extends RecyclerView.Adapter<AdapterPlan.ViewHolderDatos> {
    ArrayList<Planning> Plannings;
    private AdapterPlan.OnNoteListener OnNoteListener;

    public AdapterPlan(ArrayList<Planning> Plannings, AdapterPlan.OnNoteListener OnNoteListener) {
        this.Plannings = Plannings;
        this.OnNoteListener = OnNoteListener;
    }
    @NonNull
    @Override
    public AdapterPlan.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, null, false);
        return new AdapterPlan.ViewHolderDatos(view, OnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPlan.ViewHolderDatos holder, final int position) {

        holder.asignar(Plannings.get(position));



       /* String nom = getItem(position).getNom();
        String info = getItem(position).getInfo();
        int nivell = getItem(position).getNivell();
        String modalitat = getItem(position).getModalitat();
        String exercicis = getItem(position).getExercicis();

        final Exercici exercici = new Exercici(nom,info,nivell,modalitat, exercicis); */
    }

    @Override
    public int getItemCount() {
        return Plannings.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView tv1;
        TextView tv2;
        AdapterPlan.OnNoteListener OnNoteListener;
        public ViewHolderDatos(@NonNull View itemView, AdapterPlan.OnNoteListener OnNoteListener) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.textView39);
            tv2 = (TextView) itemView.findViewById(R.id.textView40);
            this.OnNoteListener = OnNoteListener;
            itemView.setOnClickListener(this);
        }

        public void asignar(Planning planning) {
            tv1.setText(planning.getNom());
            tv2.setText(planning.getInfo());

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
