package com.app.mobilize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.EditText;

public class Seleccionar_rutina extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_rutina);

        final  TextView tv8 = (TextView)findViewById(R.id.textView8);
        final TextView tv10 = (TextView)findViewById(R.id.textView10);

        int dificultat = getIntent().getIntExtra("dificultat",0);
        String modalitat = getIntent().getStringExtra("modalitat");



        if(dificultat == 0 && modalitat.equals("running")) {

            tv8.setText("RunningFacil");
            tv10.setText("aquesta es una rutina de nivell facil i modalitat running");

        }

        else if(dificultat == 1 && modalitat.equals("running")) {

            tv8.setText("RunningMitja");
            tv10.setText("aquesta es una rutina de nivell mitjà i modalitat running");

        }

        else if(dificultat == 2 && modalitat.equals("running")) {

            tv8.setText("RunningDificil");
            tv10.setText("aquesta es una rutina de nivell dificil i modalitat running");

        }


        else if(dificultat == 0 && modalitat.equals("cycling")) {

            tv8.setText("CyclingFacil");
            tv10.setText("aquesta es una rutina de nivell facil i modalitat cycling");

        }


        else if(dificultat == 1 && modalitat.equals("cycling")) {

            tv8.setText("CyclingMitja");
            tv10.setText("aquesta es una rutina de nivell mitjà i modalitat cycling");

        }


        else if(dificultat == 2 && modalitat.equals("cycling")) {

            tv8.setText("CycliongDificil");
            tv10.setText("aquesta es una rutina de nivell dificil i modalitat cycling ");

        }

        else if(dificultat == 0 && modalitat.equals("workout")) {

            tv8.setText("WorkoutFacil");
            tv10.setText("aquesta es una rutina de nivell facil i modalitat workout");

        }


        else if(dificultat == 1 && modalitat.equals("workout")) {

            tv8.setText("WorkoutMitja");
            tv10.setText("aquesta es una rutina de nivell mitja i modalitat workout");

        }


        else if(dificultat == 2 && modalitat.equals("workout")) {

            tv8.setText("WorkoutDificil");
            tv10.setText("aquesta es una rutina de nivell dificil i modalitat workout");

        }




    }

    public void vermas(View view) {
        Intent intent = new Intent(view.getContext(), PopUpRutina.class);
        startActivityForResult(intent, 0);
    }


}
