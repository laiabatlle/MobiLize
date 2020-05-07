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

       tvnom.setText(exercici.getNom());
       tvkmh.setText(exercici.getKmh());


        String kcal = Double.toString(exercici.getKcal());
        String durada = Integer.toString(exercici.getDuradamin());

        tvdurada.setText(durada);
        tvkcal.setText(kcal);

        if(exercici.getPendent() == true) {
            tvpendent.setText("Ha d'incloure pendent");
        }

        else tvpendent.setText("No ha d'incloure pendent");

        tvpunts.setText("200");
    }
}
