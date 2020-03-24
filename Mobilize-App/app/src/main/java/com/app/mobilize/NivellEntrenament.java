package com.app.mobilize;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class NivellEntrenament extends AppCompatActivity {

    RadioButton rbDificil, rbMitjana, rbFacil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivell_entrenament);

        rbFacil = (RadioButton) findViewById(R.id.rbFacil);
        rbMitjana = (RadioButton) findViewById(R.id.rbMitjana);
        rbDificil = (RadioButton) findViewById(R.id.rbDificil);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Continua(View view) {
        Intent i = new Intent(this, Seleccionar_rutina.class);
        i.putExtra("facil",rbFacil.getAutofillValue());
       // i.putExtra("mitja", rbMitjana.getAutofillValue());
        // i.putExtra("dificl", rbDificil.getAutofillValue());
        startActivity(i);

    }


}
