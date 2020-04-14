package com.app.mobilize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.sqliteopenhelper.Rutina;

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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nom = getItem(position).getNom();
        String info = getItem(position).getInfo();
        int nivell = getItem(position).getNivell();
        String modalitat = getItem(position).getModalitat();

        Rutina rutina = new Rutina(nom,info,nivell,modalitat);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        EditText nomed = (EditText) convertView.findViewById(R.id.editText);
        EditText infoed = (EditText) convertView.findViewById(R.id.editText2);

        nomed.setText(nom);
        infoed.setText(info);

        return convertView;

    }
}
