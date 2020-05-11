package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.app.mobilize.R;
import com.app.sqliteopenhelper.Exercici;

public class VeureExerciciNoWorkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veure_exercici_no_workout);

        TextView tvnom = (TextView) findViewById(R.id.textView18);
        TextView tvkmh = (TextView) findViewById(R.id.textView19);
        TextView tvdurada = (TextView) findViewById(R.id.textView20);
        TextView tvkcal = (TextView) findViewById(R.id.textView21);
        TextView tvpendent = (TextView) findViewById(R.id.textView22);
        TextView tvpunts = (TextView) findViewById(R.id.textView23);

        Exercici exercici = (Exercici) getIntent().getParcelableExtra("exercici");
        String km_h = exercici.getKmh() + " " + getString(R.string.kmh);
        tvnom.setText(exercici.getNom());
        tvkmh.setText(km_h);


        String kcal = Double.toString(exercici.getKcal()) + " " + getString(R.string.Kcal);
        String durada = Integer.toString(exercici.getDuradamin()) + " " + getString(R.string.minuts);
        String punts = Integer.toString(exercici.getPunts()) + " " + getString(R.string.punts);

        tvdurada.setText(durada);
        tvkcal.setText(kcal);

        if(exercici.getPendent()) {
            tvpendent.setText(R.string.inclou_pendent);
        }

        else tvpendent.setText(R.string.sense_pendent);

        tvpunts.setText(punts);
    }
}
