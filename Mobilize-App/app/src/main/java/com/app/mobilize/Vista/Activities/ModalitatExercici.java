package com.app.mobilize.Vista.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.R;

public class ModalitatExercici extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modalitat_exercici);

        Button bRunning, bCycling;

        bRunning = findViewById(R.id.bRunning);
        bRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bCycling = findViewById(R.id.bCycling);
        bCycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
