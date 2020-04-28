package com.app.mobilize.Vista.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.app.mobilize.R;

import androidx.appcompat.app.AppCompatActivity;

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
                sigIntent("running");
            }
        });

        bCycling = findViewById(R.id.bCycling);
        bCycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sigIntent("cylcing");
            }
        });
    }


    private void sigIntent ( String tipus ) {
        Intent intent = new Intent(this, TrackActivity.class);
        intent.putExtra("tipus", tipus);
        startActivity(intent);
    }

}
