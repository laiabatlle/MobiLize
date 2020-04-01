package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mobilize.R;

public class TipusPlan extends AppCompatActivity {

    TextView tv;

    CheckBox cbRunning, cbCiclisme, cbWorkout;
    RadioButton rbDificil, rbMitjana, rbFacil;
    Button buttonOk, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipus_plan);

        String tipus = getIntent().getStringExtra("tipus");

        tv = findViewById(R.id.textView5);
        tv.setText("PLANNING: " + tipus);

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
                    Toast.makeText(getApplicationContext(), "SELECCIONA UNA DISCIPLINA!", Toast.LENGTH_LONG).show();
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
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
        finish();
    }
}