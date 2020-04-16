package com.app.mobilize.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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

        chart = view.findViewById(R.id.grafic);

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

        //https://github.com/PhilJay/MPAndroidChart/wiki/The-Axis
        YAxis left = chart.getAxisLeft();
        left.setDrawLabels(false); // no axis labels
        //left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        chart.getAxisRight().setEnabled(false); // no right axis

        final ArrayList<Entry> valors_mensuals = new ArrayList<>();
        //valors per definicio de Pes i Setmanal
        valors_mensuals.add(new Entry(0, 78f));
        valors_mensuals.add(new Entry(1, 76.5f));
        valors_mensuals.add(new Entry(2, 77f));
        valors_mensuals.add(new Entry(3, 74f));
        final LineDataSet setM = new LineDataSet(valors_mensuals, "Mensual");

        final ArrayList<Entry> valors_setmanals = new ArrayList<>();
        //valors per definicio de Pes i Setmanal
        valors_setmanals.add(new Entry(0, 77f));
        valors_setmanals.add(new Entry(1, 77.9f));
        valors_setmanals.add(new Entry(2, 77.4f));
        valors_setmanals.add(new Entry(3, 76.8f));
        valors_setmanals.add(new Entry(4, 77.2f));
        valors_setmanals.add(new Entry(5, 76.5f));
        valors_setmanals.add(new Entry(6, 76.4f));
        final LineDataSet setS = new LineDataSet(valors_setmanals, "Semanal");
        DrawGraph(setS, "peso", "semanal");

        rbSemanal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbMensual.isChecked()){
                    if(rbPeso.isChecked()){
                        DrawGraph(setS, "peso", "semanal");
                    }
                    if(rbCalorias.isChecked()){
                        DrawGraph(setS, "calorias", "semanal");
                    }
                    if(rbPasos.isChecked()){
                        DrawGraph(setS, "pasos", "semanal");
                    }
                    chart.notifyDataSetChanged();
                    chart.invalidate();
                }
                rbSemanal.setChecked(true);
                rbMensual.setChecked(false);
            }
        });

        rbMensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbSemanal.isChecked()){
                    if(rbPeso.isChecked()){
                        DrawGraph(setM, "peso", "mensual");
                    }
                    if(rbCalorias.isChecked()){
                        DrawGraph(setM, "calorias", "mensual");
                    }
                    if(rbPasos.isChecked()){
                        DrawGraph(setM, "pasos", "mensual");
                    }
                    chart.notifyDataSetChanged();
                    chart.invalidate();
                }
                rbSemanal.setChecked(false);
                rbMensual.setChecked(true);
            }
        });

        rbPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbSemanal.isChecked()){
                    DrawGraph(setS, "peso", "semanal");
                }
                if(rbMensual.isChecked()){
                    DrawGraph(setM, "peso", "mensual");

                }
                chart.notifyDataSetChanged();
                chart.invalidate();

                rbPeso.setChecked(true);
                rbCalorias.setChecked(false);
                rbPasos.setChecked(false);
            }
        });

        rbCalorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbSemanal.isChecked()){
                    DrawGraph(setS, "calorias", "semanal");
                }
                if(rbMensual.isChecked()){
                    DrawGraph(setM, "calorias", "mensual");
                }
                chart.notifyDataSetChanged();
                chart.invalidate();

                rbPeso.setChecked(false);
                rbCalorias.setChecked(true);
                rbPasos.setChecked(false);
            }
        });

        rbPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbSemanal.isChecked()){
                    DrawGraph(setS, "pasos", "semanal");
                }
                if(rbMensual.isChecked()){
                    DrawGraph(setM, "pasos", "mensual");
                }
                chart.notifyDataSetChanged();
                chart.invalidate();

                rbPeso.setChecked(false);
                rbCalorias.setChecked(false);
                rbPasos.setChecked(true);
            }
        });

        return view;
    }

    public void goToActivitats(){
        Intent sigIntent = new Intent (getActivity(), activitatsUser.class);
        //sigIntent.putExtra("user", user.getUsername());
        startActivity(sigIntent);
    }

    public void DrawGraph(LineDataSet set1, String tipusdades, String temps){

        set1.setFillAlpha(110);
        set1.setLineWidth(2f);
        set1.setColor(Color.parseColor("#50E33F"));
        set1.setValueTextColor(Color.BLACK);
        set1.setValueTextSize(6f);
        set1.setDrawHorizontalHighlightIndicator(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        Description desc = new Description();
        if(tipusdades == "peso"){
            desc.setText("Peso - KG");
        }
        else  if(tipusdades == "calorias"){
            desc.setText("Cal - Kcal");
        }
        else  if(tipusdades == "pasos"){
            desc.setText("Pasos - m");
        }
        desc.setTextSize(12f);

        LineData data = new LineData(dataSets);
        data.setValueTextSize(11f);
        chart.setData(data);
        chart.setDescription(desc);
        chart.setBorderColor(Color.GREEN);
    }
}
