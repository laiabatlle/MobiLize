package com.app.mobilize.Vista.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import com.app.mobilize.R;

import androidx.appcompat.app.AppCompatActivity;

public class ModalitatEntrenament extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modalitat_entrenament);


        Button bRunning, bCycling, bWorkout;

        final Boolean facil = getIntent().getBooleanExtra("facil", false);
        final Boolean mitja = getIntent().getBooleanExtra("mitja", false);
        final Boolean dificil = getIntent().getBooleanExtra("dificil", false);


        bRunning = findViewById(R.id.bRunning2);
        bRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Seleccionar_rutina.class);
                if(facil == true) {
                    intent.putExtra("dificultat", 0);
                }

                else if( mitja == true) {
                    intent.putExtra("dificultat", 1);
                }


                else if(dificil == true) {
                    intent.putExtra("dificultat", 2);
                }

                intent.putExtra("modalitat", "running");
                startActivityForResult(intent, 0);
                finish();

            }
        });

        bCycling = findViewById(R.id.bCycling2);
        bCycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Seleccionar_rutina.class);
                if(facil == true) {
                    intent.putExtra("dificultat", 0);
                }

                else if( mitja == true) {
                    intent.putExtra("dificultat", 1);
                }


                else if(dificil == true) {
                    intent.putExtra("dificultat", 2);
                }

                intent.putExtra("modalitat", "cycling");
                startActivityForResult(intent, 0);
                finish();

            }
        });

        bWorkout = findViewById(R.id.bWorkout);
        bWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Seleccionar_rutina.class);
                if(facil == true) {
                    intent.putExtra("dificultat", 0);
                }

                else if( mitja == true) {
                    intent.putExtra("dificultat", 1);
                }


                else if(dificil == true) {
                    intent.putExtra("dificultat", 2);
                }

                intent.putExtra("modalitat", "workout");
                startActivityForResult(intent, 0);
                finish();



            }
        });
    }


}
