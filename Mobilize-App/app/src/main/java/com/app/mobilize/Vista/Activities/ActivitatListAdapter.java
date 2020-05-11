package com.app.mobilize.Vista.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.mobilize.Model.ActivitatFinalitzada;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.app.mobilize.R;
import java.util.List;

class ActivitatListAdapter extends ArrayAdapter<ActivitatFinalitzada> {

    private Context context;
    private int resource;

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public ActivitatListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ActivitatFinalitzada> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Double distancia = getItem(position).getDistancia();
        String data = getItem(position).getData();
        long temps = getItem(position).getTemps();
        String username = getItem(position).getUsername();
        int tipus = getItem(position).getTipus();
        double kcal = getItem(position).getKcalCremades();


        ActivitatFinalitzada actFi = new ActivitatFinalitzada(data, username, temps, distancia, tipus, kcal);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        ImageView imView = convertView.findViewById(R.id.ivRunningCycling);

        TextView tvDistancia, tvRitmo, tvTemps, tvData;
        tvDistancia = convertView.findViewById(R.id.tvDistancia);
        tvRitmo = convertView.findViewById(R.id.tvRitmo);
        tvTemps = convertView.findViewById(R.id.tvTemps);
        tvData = convertView.findViewById(R.id.tvData);

        if ( tipus == 0 ) imView.setImageResource(R.drawable.bicicleta);
        else if ( tipus == 2 ) imView.setImageResource(R.drawable.gimnasio);

        if ( tipus < 2 ) tvDistancia.setText(String.valueOf(distancia) + " km");
        else tvDistancia.setText("Workout");

        tvRitmo.setText(String.valueOf(df2.format(kcal)) + " kcal");
        tvTemps.setText(secToFormat(temps));
        if (tvTemps.getText().toString().length() > 15 ) tvTemps.setTextSize(15.0f);
        tvData.setText(data);

        return convertView;
    }

    private String secToFormat( long sec ){
        long horas = sec/3600;
        long minuts = (sec - (3600*horas))/60;
        long segons = sec - ((horas*3600) + (minuts*60));
        String timeFormat;
        if ( horas == 0 ) timeFormat = String.valueOf(minuts) + " min " + String.valueOf(segons) + " sec";
        else  timeFormat = String.valueOf(horas) + " h " + String.valueOf(minuts) + " min " + String.valueOf(segons) + " sec";
        return timeFormat;
    }
}