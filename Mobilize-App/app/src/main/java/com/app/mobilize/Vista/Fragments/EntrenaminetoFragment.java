package com.app.mobilize.Vista.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.app.mobilize.Vista.Activities.ModalitatExercici;
import com.app.mobilize.Vista.Activities.NivellEntrenament;
import com.app.mobilize.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntrenaminetoFragment extends Fragment {

    public EntrenaminetoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entrenamineto, container, false);

        Button bLibre, bRutina;

        bLibre = view.findViewById(R.id.bLibre);
        bLibre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ModalitatExercici.class);
                startActivityForResult(intent, 0);
            }
        });

        bRutina = view.findViewById(R.id.bRutina);
        bRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), NivellEntrenament.class);
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }

}

