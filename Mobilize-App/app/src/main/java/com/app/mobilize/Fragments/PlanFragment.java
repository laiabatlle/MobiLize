package com.app.mobilize.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.app.mobilize.R;
import com.app.mobilize.TipusPlan;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment {

    private Button ibAdelgazar, ibEnForma, ibMasa, bPlanes;

    public PlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        bPlanes = view.findViewById(R.id.misplanes);

        bPlanes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMisPlanes();
            }
        });

        ibAdelgazar = view.findViewById(R.id.bAdelgazar);
        ibEnForma = view.findViewById(R.id.bForma);
        ibMasa = view.findViewById(R.id.bMasa);

        ibAdelgazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipusPlan("Adelgazar"); // li paso el tipus de plan clickat
            }
        });

        ibEnForma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipusPlan("En forma"); // li paso el tipus de plan clickat
            }
        });

        ibMasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipusPlan("Ganar Músculo"); // li paso el tipus de plan clickat
            }
        });
        return view;
    }

    public void openMisPlanes(){
      //  Intent plans = new Intent(this, mis_planes.class);
       // startActivity(plans);
    }

    public void tipusPlan(String tipus) {
      Intent sigIntent = new Intent (getActivity(), TipusPlan.class);
      sigIntent.putExtra("tipus", tipus); // li paso a la seguent activity el tipus de plan
      startActivity(sigIntent);
    }
}
