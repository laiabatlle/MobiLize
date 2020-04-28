package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import com.app.mobilize.R;

public class NivellEntrenament extends AppCompatActivity {

    RadioButton rbDificil, rbMitjana, rbFacil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivell_entrenament);

        rbFacil = (RadioButton) findViewById(R.id.rbFacil);
        rbMitjana = (RadioButton) findViewById(R.id.rbMitjana);
        rbDificil = (RadioButton) findViewById(R.id.rbDificil);

        Button continua;

        continua = findViewById(R.id.button2);
        continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( rbFacil.isChecked() == false && rbMitjana.isChecked() == false && rbDificil.isChecked() == false ){
                    Toast.makeText(getApplicationContext(), R.string.escollirDificultat, Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(v.getContext(), ModalitatEntrenament.class);
                    intent.putExtra("facil", rbFacil.isChecked());
                    intent.putExtra("mitja", rbMitjana.isChecked());
                    intent.putExtra("dificil", rbDificil.isChecked());
                    startActivityForResult(intent, 0);
                }
            }
        });
    }
}
