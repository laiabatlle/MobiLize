package com.app.mobilize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

    public void onclick(View view) {
        if(view.getId()==R.id.button2) {
            Continuar();
        }
    }

    private void Continuar() {

    }
}
