package com.app.mobilize.Vista.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.R;
import com.app.mobilize.Vista.Activities.ActivitatsUser;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.*;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.components.AxisBase;

import java.util.ArrayList;
import java.util.Calendar;

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

    Calendar data_actual = Calendar.getInstance();
    //Date d = data_actual.getTime();

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


        final int dia_semana = data_actual.get(Calendar.DAY_OF_WEEK);

        final String[] eixXsemana = getResources().getStringArray(R.array.dias_semana);
        //final String[] eixXsemana = new String[] {"lun", "mar", "mie", "jue", "vie", "sab", "dom"};
        final String[] eixXmes = getResources().getStringArray(R.array.meses_año);
        //final String[] eixXmes = new String[] {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL"};

        final YAxis Yleft = chart.getAxisLeft();
        YAxis yRight = chart.getAxisRight();
        final XAxis xAxis = chart.getXAxis();

        yRight.setDrawLabels(false); // no axis labels
        //left.setDrawAxisLine(false); // no axis line
        Yleft.setDrawGridLines(false); // no grid lines
        Yleft.setValueFormatter(new FormatterEixYDades("peso")); //Perque surti "kg" al costat dels valors de l'eix
        Yleft.setXOffset(2f);
        xAxis.setYOffset(1f);
        xAxis.setValueFormatter(new FormatterEixXSemanal(eixXsemana, dia_semana));
        chart.getAxisRight().setEnabled(false); // no right axis

        final ArrayList<Entry> valors_mensuals = new ArrayList<>();
        //valors per definicio de Pes i Setmanal
        valors_mensuals.add(new Entry(0, 78f));
        valors_mensuals.add(new Entry(1, 76.5f));
        valors_mensuals.add(new Entry(2, 76f));
        valors_mensuals.add(new Entry(3, 0f));
        valors_mensuals.add(new Entry(4, 0f));
        valors_mensuals.add(new Entry(5, 0));
        valors_mensuals.add(new Entry(6, 0));
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
                    xAxis.setValueFormatter(new FormatterEixXSemanal(eixXsemana, dia_semana));
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
                    xAxis.setValueFormatter(new FormatterEixXMensual(eixXmes));
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
                Yleft.setValueFormatter(new FormatterEixYDades("peso"));
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
                Yleft.setValueFormatter(new FormatterEixYDades("calorias"));
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
                Yleft.setValueFormatter(new FormatterEixYDades("pasos"));
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
        Intent sigIntent = new Intent (getActivity(), ActivitatsUser.class);
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
        desc = null;

        LineData data = new LineData(dataSets);
        data.setValueTextSize(9f);
        chart.setData(data);
        chart.setDescription(desc);
        chart.setBorderColor(Color.GREEN);
        chart.getXAxis().setSpaceMin(0.5f);
    }

    private class FormatterEixYDades implements IAxisValueFormatter{
        private String pes_cal_pasos;

        public FormatterEixYDades(String dades){
            if(dades == "peso"){
                this.pes_cal_pasos= " kg";
            }
            else  if(dades == "calorias"){
                this.pes_cal_pasos= " Kcl";
            }
            else  if(dades == "pasos"){
                this.pes_cal_pasos= " m";
            }
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return (int)value + pes_cal_pasos;
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    }

    private class FormatterEixXSemanal implements IAxisValueFormatter{
        private String[] valuesX;
        int dia_actual;

        public FormatterEixXSemanal(String[] values, int dia) {
            valuesX = new String[7];
            /*String[] aux = values;
            String[] primers = new String[4];
            String[] segons = new String[3];
            int iterador_today = 0;
            int cont = 0;

            for (int i = 0; i < 7; ++i){
                if(dia == i+1){
                    iterador_today = i;
                    //segons[0] = aux[i];
                }
            }

            for (int i = 0; i < 7; ++i){
                if(i < iterador_today){

                }
                else{

                }
            }*/
            this.valuesX = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return valuesX[(int)value];
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    }

    private class FormatterEixXMensual implements IAxisValueFormatter{
        private String[] valuesX;

        public FormatterEixXMensual(String[] values) {
            this.valuesX = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return valuesX[(int)value];
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    }
}
