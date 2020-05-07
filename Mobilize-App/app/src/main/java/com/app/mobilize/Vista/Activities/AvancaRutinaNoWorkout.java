package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.mobilize.R;
import com.app.sqliteopenhelper.Exercici;

import java.util.ArrayList;

public class AvancaRutinaNoWorkout extends AppCompatActivity {

    ArrayList<Exercici> exercici;
    int pos;
    int puntstotals;
    double kcaltotals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avanca_rutina_no_workout);

        TextView tvnom = (TextView) findViewById(R.id.textView30);
        TextView tvkmh = (TextView) findViewById(R.id.textView31);
        TextView tvdurada = (TextView) findViewById(R.id.textView32);
        TextView tvkcal = (TextView) findViewById(R.id.textView33);
        TextView tvpendent = (TextView) findViewById(R.id.textView34);
        TextView tvpunts = (TextView) findViewById(R.id.textView35);

        exercici = getIntent().getParcelableArrayListExtra("exercici");
        pos = getIntent().getIntExtra("pos", 0);
        puntstotals = getIntent().getIntExtra("puntstotals",0);
        kcaltotals = getIntent().getDoubleExtra("kcaltotals", 0);


        tvnom.setText(exercici.get(pos).getNom());
        tvkmh.setText(exercici.get(pos).getKmh());


        String kcal = Double.toString(exercici.get(pos).getKcal());
        String durada = Integer.toString(exercici.get(pos).getDuradamin());
        String punts = Integer.toString(exercici.get(pos).getPunts());

        tvdurada.setText(durada);
        tvkcal.setText(kcal);

        puntstotals = exercici.get(pos).getPunts() + puntstotals;
        kcaltotals = exercici.get(pos).getKcal() + kcaltotals;

        if(exercici.get(pos).getPendent() == true) {
            tvpendent.setText("Ha d'incloure pendent");
        }

        else tvpendent.setText("No ha d'incloure pendent");

        tvpunts.setText(punts);
    }


    public void Track(View view) {

        Intent intent = new Intent(this, NivellEntrenament.class);  //aqui haurem de passar cap a la pestanya process
        intent.putExtra("puntstotals", puntstotals);
        intent.putExtra("kcaltotals",kcaltotals);
        intent.putParcelableArrayListExtra("exercici", exercici);
        intent.putExtra("pos", pos);
        startActivityForResult(intent, 0);



    }
}
