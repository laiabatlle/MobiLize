package com.app.mobilize.Vista.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.R;

public class ModalitatEntrenament extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modalitat_entrenament);

        Button bRunning, bCycling, bWorkout;

        bRunning = findViewById(R.id.bRunning2);
        bRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bCycling = findViewById(R.id.bCycling2);
        bCycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bWorkout = findViewById(R.id.bWorkout);
        bWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
