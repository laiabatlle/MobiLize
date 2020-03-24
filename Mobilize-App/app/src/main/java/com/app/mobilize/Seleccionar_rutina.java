package com.app.mobilize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class Seleccionar_rutina extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_rutina);
    }

    RadioButton rbd = (RadioButton)findViewById(R.id.rbDificil);
    RadioButton rbf = (RadioButton)findViewById(R.id.rbFacil);
    RadioButton rbm = (RadioButton)findViewById(R.id.rbMitjana);

    Boolean facil = getIntent().getBooleanExtra("facil", true);
   // Boolean mitja = getIntent().getBooleanExtra("mitja", true);
   //  Boolean dificil = getIntent().getBooleanExtra("dificl", true);

    public void  Seleccionar(View view) {
        if(rbf.isChecked() == true) {

        }

        if(rbd.isChecked() == true) {

        }

        if(rbm.isChecked() == true) {

        }

    }
}
