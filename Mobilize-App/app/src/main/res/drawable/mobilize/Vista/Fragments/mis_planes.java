package com.app.mobilize.Vista.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.app.mobilize.R;

public class mis_planes extends Fragment {
    private ImageButton addButton;
    private ListView listView;
    AlertDialog.Builder builder;
    EditText et1, et2;



    public mis_planes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_mis_planes, container, false);

      /*  builder = new AlertDialog.Builder(this);
        builder.setTitle("Titulo");
        builder.setView(R.layout.alert_dialog);
        LayoutInflater inflater = getLayoutInflater();
        View myView = inflater.inflate(R.layout.alert_dialog,null);
        Button ok;
        et1 = myView.findViewById(R.id.etDialog);
        et2 = myView.findViewById(R.id.edAlert2);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openCrearPla();
            }
        });
        //  builder.setView(R.layout.alert_dialog);
        final AlertDialog alertDialog = builder.create();*/

        listView = view.findViewById(R.id.listView);

        addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // alertDialog.show();
            }
        });
        return view;
    }

   /* public void openCrearPla(){
        Intent intent = new Intent(this, Crear_plan.class);
        startActivity(intent);
    }*/
}