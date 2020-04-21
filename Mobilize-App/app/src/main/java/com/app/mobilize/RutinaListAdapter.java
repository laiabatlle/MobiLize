package com.app.mobilize;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sqliteopenhelper.Rutina;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RutinaListAdapter extends ArrayAdapter<Rutina> {

    private static final String TAG = " RutinaListAdapter";
    private Context mContext;
    int mResource;

    public RutinaListAdapter(@NonNull Context context, int resource, ArrayList<Rutina> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nom = getItem(position).getNom();
        String info = getItem(position).getInfo();
        int nivell = getItem(position).getNivell();
        String modalitat = getItem(position).getModalitat();

        final Rutina rutina = new Rutina(nom,info,nivell,modalitat);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        final TextView infoed = (TextView)convertView.findViewById(R.id.textView8);
        final TextView nomed = (TextView)convertView.findViewById(R.id.textView10);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PopUpRutina.class);
                intent.putExtra("rutina", rutina);
                mContext.startActivity(intent);
            }
        });

        nomed.setText(nom);
        infoed.setText(info);

        return convertView;

    }
}
