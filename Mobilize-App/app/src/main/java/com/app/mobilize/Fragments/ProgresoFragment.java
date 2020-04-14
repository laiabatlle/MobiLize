package com.app.mobilize.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.mobilize.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgresoFragment extends Fragment {

    TextView tvWorkout, tvCycling, tvRunning, tvTotal;
    ProgressBar pbWorkout, pbCycling, pbRunning;

    public ProgresoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_progreso, container, false);

        tvWorkout = view.findViewById(R.id.tvWorkout);
        tvCycling = view.findViewById(R.id.tvCycling);
        tvRunning = view.findViewById(R.id.tvRunning);
        tvTotal = view.findViewById(R.id.tvTempsTotal);

        pbWorkout = view.findViewById(R.id.pbWorkout);
        pbCycling = view.findViewById(R.id.pbCycling);
        pbRunning = view.findViewById(R.id.pbRunning);

        int minTotals, minRunning, minCycling, minWorkout;
        minTotals = 2000;
        minRunning = 1000;
        minCycling = 500;
        minWorkout = 500;

        tvWorkout.setText("Workout: " + String.valueOf(minWorkout) + " min");
        tvRunning.setText("Running: " + String.valueOf(minRunning) + " min");
        tvCycling.setText("Cycling: " + String.valueOf(minCycling) + " min");

        pbWorkout.setProgress(minWorkout*100/minTotals);
        pbCycling.setProgress(minCycling*100/minTotals);
        pbRunning.setProgress(minRunning*100/minTotals);

        return view;
    }
}
