package com.app.mobilize.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.mobilize.Pojo.Usuari;
import com.app.mobilize.R;
import com.app.mobilize.activitatsUser;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgresoFragment extends Fragment {

    TextView tvWorkout, tvCycling, tvRunning, tvTotal;
    ProgressBar pbWorkout, pbCycling, pbRunning;
    RadioButton rbMensual, rbSemanal, rbPeso, rbCalorias, rbPasos;
    LineChart chart;
    Usuari user;
    Button activitatsFinalitzades;

    public ProgresoFragment( ) {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_progreso, container, false);

        activitatsFinalitzades = view.findViewById(R.id.buttonActivitats);
        activitatsFinalitzades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivitats();
            }
        });
        
        tvWorkout = view.findViewById(R.id.tvWorkout);
        tvCycling = view.findViewById(R.id.tvCycling);
        tvRunning = view.findViewById(R.id.tvRunning);
        tvTotal = view.findViewById(R.id.tvTempsTotal);

        pbWorkout = view.findViewById(R.id.pbWorkout);
        pbCycling = view.findViewById(R.id.pbCycling);
        pbRunning = view.findViewById(R.id.pbRunning);

        rbSemanal = view.findViewById(R.id.rbSemanal);
        rbMensual = view.findViewById(R.id.rbMensual);

        rbPeso = view.findViewById(R.id.rbPeso);
        rbCalorias = view.findViewById(R.id.rbCalorias);
        rbPasos = view.findViewById(R.id.rbPasos);

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


        rbPeso.setChecked(true);
        rbSemanal.setChecked(true);

        rbSemanal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbSemanal.setChecked(true);
                rbMensual.setChecked(false);
            }
        });

        rbMensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbSemanal.setChecked(false);
                rbMensual.setChecked(true);
            }
        });

        rbPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbPeso.setChecked(true);
                rbCalorias.setChecked(false);
                rbPasos.setChecked(false);
            }
        });

        rbCalorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbPeso.setChecked(false);
                rbCalorias.setChecked(true);
                rbPasos.setChecked(false);
            }
        });

        rbPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbPeso.setChecked(false);
                rbCalorias.setChecked(false);
                rbPasos.setChecked(true);
            }
        });


        chart = view.findViewById(R.id.grafic);
        ArrayList<Entry> valors = new ArrayList<>();

        valors.add(new Entry(0, 2f));
        valors.add(new Entry(1, 3f));
        valors.add(new Entry(2, 2f));
        valors.add(new Entry(3, 5f));
        valors.add(new Entry(4, 6f));
        valors.add(new Entry(5, 4f));

        LineDataSet set1 = new LineDataSet(valors, "Set 1");
        set1.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        chart.setData(data);

        return view;
    }

    public void goToActivitats(){
        Intent sigIntent = new Intent (getActivity(), activitatsUser.class);
        //sigIntent.putExtra("user", user.getUsername());
        startActivity(sigIntent);
    }

}
