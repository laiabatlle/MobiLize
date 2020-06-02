package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.app.mobilize.R;

public class TipusPlan extends AppCompatActivity {

    TextView tv;

    CheckBox cbRunning, cbCiclisme, cbWorkout;
    RadioButton rbDificil, rbMitjana, rbFacil;
    Button buttonOk, buttonBack;
    Spinner duracio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipus_plan);

        String tipus = getIntent().getStringExtra("tipus");
        if(tipus.equals("A")) tipus = getResources().getString(R.string.adelgazarPlan);
        else if(tipus.equals("E")) tipus = getResources().getString(R.string.enformaPlan);
        else tipus = getResources().getString(R.string.masa_muscularPlan);

        duracio = findViewById(R.id.spinnerDuracio);
        buttonOk = findViewById(R.id.butOk);
        buttonBack = findViewById(R.id.butEnrere);

        cbRunning = findViewById(R.id.cbRunning);
        cbCiclisme = findViewById(R.id.cbCiclisme);
        cbWorkout = findViewById(R.id.cbWorkout);

        rbDificil = findViewById(R.id.rbDificil);
        rbMitjana = findViewById(R.id.rbMitjana);
        rbFacil = findViewById(R.id.rbFacil);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backActivity();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( cbRunning.isChecked() == false && cbCiclisme.isChecked() == false && cbWorkout.isChecked() == false ){
                    Toast.makeText(getApplicationContext(), R.string.selectDiscipline, Toast.LENGTH_LONG).show();
                }

                else {
                    String modalitat = null;
                    Intent intent = new Intent(v.getContext(), Seleccionar_planning.class);

                    if (cbRunning.isChecked() == true) {
                        modalitat = "running";
                    }

                    if (cbCiclisme.isChecked() == true) {
                        modalitat = "cycling";
                    }

                    if (cbWorkout.isChecked() == true) {
                        modalitat = "workout";
                    }

                    if (cbRunning.isChecked() == true && cbCiclisme.isChecked() == true) {
                        modalitat = "runningcycling";
                    }

                    if (cbRunning.isChecked() == true && cbWorkout.isChecked() == true) {
                        modalitat = "runningworkout";
                    }

                    if (cbCiclisme.isChecked() == true && cbWorkout.isChecked() == true) {
                        modalitat = "cyclingworkout";
                    }

                    if (cbRunning.isChecked() == true && cbCiclisme.isChecked() == true && cbWorkout.isChecked() == true) {
                        modalitat = "runningcyclingworkout";
                    }

                    intent.putExtra("modalitat", modalitat);
                    if (rbDificil.isChecked()) intent.putExtra("nivell", 2);
                    if (rbMitjana.isChecked()) intent.putExtra("nivell", 1);
                    if (rbFacil.isChecked()) intent.putExtra("nivell", 0);

                    String dur = duracio.getSelectedItem().toString();

                    intent.putExtra("duracio", dur);
                    startActivityForResult(intent, 0);
                }


            }
        });

        rbDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbDificil.setChecked(true);
                rbMitjana.setChecked(false);
                rbFacil.setChecked(false);
            }
        });

        rbMitjana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbDificil.setChecked(false);
                rbMitjana.setChecked(true);
                rbFacil.setChecked(false);
            }
        });

        rbFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbDificil.setChecked(false);
                rbMitjana.setChecked(false);
                rbFacil.setChecked(true);
            }
        });
    }

    public void backActivity () {
        finish();
    }



}