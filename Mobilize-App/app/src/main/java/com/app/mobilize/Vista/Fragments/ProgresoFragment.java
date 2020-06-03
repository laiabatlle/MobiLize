package com.app.mobilize.Vista.Fragments;

import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.R;
import com.app.mobilize.Vista.Activities.ActivitatsUser;
import com.app.mobilize.Vista.Activities.CalendarActivity;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgresoFragment extends Fragment {

    private Map<String, Map<String, String>> map;

    private TextView tvWorkout, tvCycling, tvRunning, tvTotal;
    private ProgressBar pbWorkout, pbCycling, pbRunning;
    private RadioButton rbCalorias, rbPasos, rbMesActual, rbMesAnterior;
    private LineChart chart;
    private Button activitatsFinalitzades, btCalendar;

    private float disGen, disFeb, disMarç, disAbril, disMaig, disJuny, disJul, disAgo, disSept, disOct, disNov, disDes;
    private float kcalGen, kcalFeb, kcalMarç, kcalAbril, kcalMaig, kcalJuny, kcalJul, kcalAgo, kcalSept, kcalOct, kcalNov, kcalDes;

    private int minTotalsAct, minRunningAct, minCyclingAct, minWorkoutAct, minTotalsAnt, minRunningAnt, minCyclingAnt, minWorkoutAnt;

    private Usuari user;

    public ProgresoFragment( Usuari user, Map<String, Map<String, String>> map ) {
        this.map = map;
        this.user = user;
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
        minTotalsAct = minCyclingAct = minRunningAct = minWorkoutAct = minTotalsAnt = minCyclingAnt = minRunningAnt = minWorkoutAnt = 0;

        disAbril = disAgo = disGen = disFeb = disOct = disDes = disJul = disJuny = disMaig = disMarç = disNov = disSept = 0;

        kcalGen = kcalFeb = kcalMarç = kcalAbril = kcalMaig = kcalJuny = kcalJul = kcalAgo = kcalSept = kcalOct = kcalNov = kcalDes = 0;

        chart = view.findViewById(R.id.grafic);

        tvWorkout = view.findViewById(R.id.tvWorkout);
        tvCycling = view.findViewById(R.id.tvCycling);
        tvRunning = view.findViewById(R.id.tvRunning);
        tvTotal = view.findViewById(R.id.tvTempsTotal);

        pbWorkout = view.findViewById(R.id.pbWorkout);
        pbCycling = view.findViewById(R.id.pbCycling);
        pbRunning = view.findViewById(R.id.pbRunning);

        rbCalorias = view.findViewById(R.id.rbCalorias);
        rbPasos = view.findViewById(R.id.rbPasos);
        rbMesActual = view.findViewById(R.id.rbMesActual);
        rbMesAnterior = view.findViewById(R.id.rbMesAnterior);

        rbMesAnterior.setText(getResources().getString(R.string.rb_mesAnterior));
        rbMesActual.setText(getResources().getString(R.string.rb_mesActual));

        rbMesActual.setChecked(true);
        rbMesAnterior.setChecked(false);

        btCalendar = view.findViewById(R.id.bCalendar);

        Log.i("TIME BEFORE", String.valueOf(SystemClock.currentThreadTimeMillis()));

        putActivitats();

        rbCalorias.setChecked(true);
        final String[] eixXmes = getResources().getStringArray(R.array.meses_año);
        //final String[] eixXmes = new String[] {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL"};

        final YAxis Yleft = chart.getAxisLeft();
        final YAxis yRight = chart.getAxisRight();
        final XAxis xAxis = chart.getXAxis();

        yRight.setDrawLabels(false); // no axis labels
        //left.setDrawAxisLine(false); // no axis line
        Yleft.setDrawGridLines(false); // no grid lines
        Yleft.setValueFormatter(new FormatterEixYDades("calorias")); //Perque surti "km" al costat dels valors de l'eix
        Yleft.setXOffset(2f);
        xAxis.setYOffset(1f);
        xAxis.setValueFormatter(new FormatterEixXMensual(eixXmes));
        chart.getAxisRight().setEnabled(false); // no right axis

        final ArrayList<Entry> valors_mensuals = new ArrayList<>();
        //valors per definicio de Pes i Setmanal
        valors_mensuals.add(new Entry(0, disGen));
        valors_mensuals.add(new Entry(1, disFeb));
        valors_mensuals.add(new Entry(2, disMarç));
        valors_mensuals.add(new Entry(3, disAbril));
        valors_mensuals.add(new Entry(4, disMaig));
        valors_mensuals.add(new Entry(5, disJuny));
        valors_mensuals.add(new Entry(6, disJul));
        valors_mensuals.add(new Entry(7, disAgo));
        valors_mensuals.add(new Entry(8, disSept));
        valors_mensuals.add(new Entry(9, disOct));
        valors_mensuals.add(new Entry(10, disNov));
        valors_mensuals.add(new Entry(11, disDes));

        final LineDataSet setM = new LineDataSet(valors_mensuals, getResources().getString(R.string.monthly));

        final ArrayList<Entry> valors_mensualsKcal = new ArrayList<>();
        //valors per definicio de Pes i Setmanal
        valors_mensualsKcal.add(new Entry(0, kcalGen));
        valors_mensualsKcal.add(new Entry(1, kcalFeb));
        valors_mensualsKcal.add(new Entry(2, kcalMarç));
        valors_mensualsKcal.add(new Entry(3, kcalAbril));
        valors_mensualsKcal.add(new Entry(4, kcalMaig));
        valors_mensualsKcal.add(new Entry(5, kcalJuny));
        valors_mensualsKcal.add(new Entry(6, kcalJul));
        valors_mensualsKcal.add(new Entry(7, kcalAgo));
        valors_mensualsKcal.add(new Entry(8, kcalSept));
        valors_mensualsKcal.add(new Entry(9, kcalOct));
        valors_mensualsKcal.add(new Entry(10, kcalNov));
        valors_mensualsKcal.add(new Entry(11, kcalDes));

        final LineDataSet setKcal = new LineDataSet(valors_mensualsKcal, getResources().getString(R.string.monthly));

        DrawGraph(setKcal, "kcal", "mensual");

        rbCalorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawGraph(setKcal, "calorias", "mensual");
                Yleft.setValueFormatter(new FormatterEixYDades("calorias"));
                chart.notifyDataSetChanged();
                chart.invalidate();

                rbCalorias.setChecked(true);
                rbPasos.setChecked(false);
            }
        });

        rbPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawGraph(setM, "pasos", "semanal");
                Yleft.setValueFormatter(new FormatterEixYDades("distancia"));
                chart.notifyDataSetChanged();
                chart.invalidate();

                rbCalorias.setChecked(false);
                rbPasos.setChecked(true);
            }
        });

        rbMesAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbMesAnterior.setChecked(true);
                rbMesActual.setChecked(false);

                tvWorkout.setText(getResources().getString(R.string.workout) + " " + String.valueOf(minWorkoutAnt) + " min");
                tvRunning.setText(getResources().getString(R.string.running) + " " + String.valueOf(minRunningAnt) + " min");
                tvCycling.setText(getResources().getString(R.string.cycling) + " " + String.valueOf(minCyclingAnt) + " min");

                if ( minTotalsAct != 0 ) {
                    pbWorkout.setProgress(minWorkoutAnt * 100 / minTotalsAnt);
                    pbCycling.setProgress(minCyclingAnt * 100 / minTotalsAnt);
                    pbRunning.setProgress(minRunningAnt * 100 / minTotalsAnt);
                }
                tvTotal.setText(getResources().getString(R.string.temps_total) + " " + String.valueOf(minTotalsAnt) + " min");

            }
        });
        rbMesActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbMesAnterior.setChecked(false);
                rbMesActual.setChecked(true);
                tvWorkout.setText(getResources().getString(R.string.workout) + " " + String.valueOf(minWorkoutAct) + " min");
                tvRunning.setText(getResources().getString(R.string.running) + " " + String.valueOf(minRunningAct) + " min");
                tvCycling.setText(getResources().getString(R.string.cycling) + " " + String.valueOf(minCyclingAct) + " min");

                pbWorkout.setProgress(minWorkoutAct);
                pbCycling.setProgress(minCyclingAct);
                pbRunning.setProgress(minRunningAct);

                tvWorkout.setText(getResources().getString(R.string.workout) + " " + String.valueOf(minWorkoutAct) + " min");
                tvRunning.setText(getResources().getString(R.string.running) + " " + String.valueOf(minRunningAct) + " min");
                tvCycling.setText(getResources().getString(R.string.cycling) + " " + String.valueOf(minCyclingAct) + " min");

                tvTotal.setText(getResources().getString(R.string.temps_total) + " " + String.valueOf(minTotalsAct) + " min");

                if ( minTotalsAct != 0 ) {
                    pbWorkout.setProgress(minWorkoutAct * 100 / minTotalsAct);
                    pbCycling.setProgress(minCyclingAct * 100 / minTotalsAct);
                    pbRunning.setProgress(minRunningAct * 100 / minTotalsAct);
                }
            }
        });

        /*
         * https://developers.google.com/api-client-library/java/
         * https://stackoverflow.com/questions/21665862/how-to-view-a-public-google-calendar-via-an-android-application
         * https://stackoverflow.com/questions/16360073/android-calendar-how-to-write-sync-adapter-for-calendar-insert
         * */
        btCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
                //goToCalendar();

                /*
                long startMillis = System.currentTimeMillis();

                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
                startActivity(intent);

                 */
            }
        });

        return view;
    }

    public void goToActivitats(){
        Intent sigIntent = new Intent (getActivity(), ActivitatsUser.class);
        sigIntent.putExtra("email", user.getEmail());
        startActivity(sigIntent);
    }

    private int mesData ( String data ) {
        int count = 0;
        String mesAux = "";
        for ( int i=0; i<data.length(); i++ ) {
            if ( count == 1 ) {
                if ( data.charAt(i) != '/' ) mesAux = mesAux + data.charAt(i);
            }
            if ( data.charAt(i) == '/' ) count ++;
        }
        return Integer.valueOf(mesAux);
    }

    private boolean anyData ( String data ) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int count = 0;
        String anyAux = "";
        for ( int i=0; i<data.length(); i++ ) {
            if ( count == 2 ) {
                if ( data.charAt(i) != '/' ) anyAux = anyAux + data.charAt(i);
            }
            if ( data.charAt(i) == '/' ) count ++;
        }
        String anyAux2 = String.valueOf(year);
        if ( anyAux.equals(anyAux2) ) return true;
        else return false;
    }

    public void putActivitats() {

        if(map != null) {
            for (int i = 0; i < map.size(); i++) {
                Map<String, String> mapAux = map.get(String.valueOf(i));
                String tipus = mapAux.get("tipus");
                String data = mapAux.get("data");
                int mesAny = mesData(data);
                boolean isAny = anyData(data);
                assert tipus != null;
                if (tipus.equals("0")) {
                    if (isAny) {
                        if (mesAny == Calendar.getInstance().get(Calendar.MONTH)) {
                            minCyclingAct = minCyclingAct + Integer.parseInt(mapAux.get("temps"));
                            Log.i("ENTROIF", "ENTRO PRIMER IF");
                            Log.i("ENTROIF", String.valueOf(minCyclingAct));
                        } else if ((mesAny + 1) == Calendar.getInstance().get(Calendar.MONTH))
                            minCyclingAnt = minCyclingAnt + Integer.parseInt(mapAux.get("temps"));

                        if (mesAny == 1) {
                            disGen += Float.parseFloat(mapAux.get("distancia"));
                            kcalGen = kcalGen + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 2) {
                            disFeb += Float.parseFloat(mapAux.get("distancia"));
                            kcalFeb = kcalFeb + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 3) {
                            disMarç = disMarç + Float.parseFloat(mapAux.get("distancia"));
                            kcalMarç = kcalMarç + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 4) {
                            disAbril = disAbril + Float.parseFloat(mapAux.get("distancia"));
                            kcalAbril = kcalAbril + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 5) {
                            disMaig = disMaig + Float.parseFloat(mapAux.get("distancia"));
                            kcalMaig = kcalMaig + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 6) {
                            disJuny = disJuny + Float.parseFloat(mapAux.get("distancia"));
                            kcalJuny = kcalJuny + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 7) {
                            disJul = disJul + Float.parseFloat(mapAux.get("distancia"));
                            kcalJul = kcalJul + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 8) {
                            disAgo = disAgo + Float.parseFloat(mapAux.get("distancia"));
                            kcalAgo = kcalAgo + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 9) {
                            disSept = disSept + Float.parseFloat(mapAux.get("distancia"));
                            kcalSept = kcalSept + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 10) {
                            disOct = disOct + Float.parseFloat(mapAux.get("distancia"));
                            kcalOct = kcalOct + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 11) {
                            disNov = disNov + Float.parseFloat(mapAux.get("distancia"));
                            kcalNov = kcalNov + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 12) {
                            disDes = disDes + Float.parseFloat(mapAux.get("distancia"));
                            kcalDes = kcalDes + Float.parseFloat(mapAux.get("kcal"));
                        }
                    }
                } else if (tipus.equals("1")) {
                    if (isAny) {
                        if (mesAny == Calendar.getInstance().get(Calendar.MONTH))
                            minRunningAct = minRunningAct + Integer.parseInt(mapAux.get("temps"));
                        else if ((mesAny + 1) == Calendar.getInstance().get(Calendar.MONTH))
                            minRunningAnt = minRunningAnt + Integer.parseInt(mapAux.get("temps"));

                        if (mesAny == 1) {
                            disGen += Float.parseFloat(mapAux.get("distancia"));
                            kcalGen = kcalGen + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 2) {
                            disFeb += Float.parseFloat(mapAux.get("distancia"));
                            kcalFeb = kcalFeb + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 3) {
                            disMarç = disMarç + Float.parseFloat(mapAux.get("distancia"));
                            kcalMarç = kcalMarç + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 4) {
                            disAbril = disAbril + Float.parseFloat(mapAux.get("distancia"));
                            kcalAbril = kcalAbril + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 5) {
                            disMaig = disMaig + Float.parseFloat(mapAux.get("distancia"));
                            kcalMaig = kcalMaig + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 6) {
                            disJuny = disJuny + Float.parseFloat(mapAux.get("distancia"));
                            kcalJuny = kcalJuny + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 7) {
                            disJul = disJul + Float.parseFloat(mapAux.get("distancia"));
                            kcalJul = kcalJul + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 8) {
                            disAgo = disAgo + Float.parseFloat(mapAux.get("distancia"));
                            kcalAgo = kcalAgo + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 9) {
                            disSept = disSept + Float.parseFloat(mapAux.get("distancia"));
                            kcalSept = kcalSept + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 10) {
                            disOct = disOct + Float.parseFloat(mapAux.get("distancia"));
                            kcalOct = kcalOct + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 11) {
                            disNov = disNov + Float.parseFloat(mapAux.get("distancia"));
                            kcalNov = kcalNov + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 12) {
                            disDes = disDes + Float.parseFloat(mapAux.get("distancia"));
                            kcalDes = kcalDes + Float.parseFloat(mapAux.get("kcal"));
                        }
                    }
                } else if (tipus.equals("2")) {
                    if (mesAny == Calendar.getInstance().get(Calendar.MONTH))
                        minWorkoutAct = minWorkoutAct + Integer.parseInt(mapAux.get("temps"));
                    else if ((mesAny + 1) == Calendar.getInstance().get(Calendar.MONTH))
                        minWorkoutAnt = minWorkoutAnt + Integer.parseInt(mapAux.get("temps"));

                    if (isAny) {
                        if (mesAny == 1) {
                            kcalGen = kcalGen + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 2) {
                            kcalFeb = kcalFeb + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 3) {
                            kcalMarç = kcalMarç + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 4) {
                            kcalAbril = kcalAbril + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 5) {
                            kcalMaig = kcalMaig + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 6) {
                            kcalJuny = kcalJuny + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 7) {
                            kcalJul = kcalJul + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 8) {
                            kcalAgo = kcalAgo + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 9) {
                            kcalSept = kcalSept + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 10) {
                            kcalOct = kcalOct + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 11) {
                            kcalNov = kcalNov + Float.parseFloat(mapAux.get("kcal"));
                        } else if (mesAny == 12) {
                            kcalDes = kcalDes + Float.parseFloat(mapAux.get("kcal"));
                        }
                    }
                }
            }

            minCyclingAnt = minCyclingAnt / 60000;
            minRunningAnt = minRunningAnt / 60000;
            minWorkoutAnt = minWorkoutAnt / 60000;

            minTotalsAnt = minCyclingAnt + minRunningAnt + minWorkoutAnt;

            minCyclingAct = minCyclingAct / 60000;
            minRunningAct = minRunningAct / 60000;
            minWorkoutAct = minWorkoutAct / 60000;

            minTotalsAct = minCyclingAct + minRunningAct + minWorkoutAct;

            tvWorkout.setText(getResources().getString(R.string.workout) + " " + String.valueOf(minWorkoutAct) + " min");
            tvRunning.setText(getResources().getString(R.string.running) + " " + String.valueOf(minRunningAct) + " min");
            tvCycling.setText(getResources().getString(R.string.cycling) + " " + String.valueOf(minCyclingAct) + " min");
            tvTotal.setText(getResources().getString(R.string.temps_total) + " " + String.valueOf(minTotalsAct) + " min");


            if (minTotalsAct != 0) {
                pbWorkout.setProgress(minWorkoutAct * 100 / minTotalsAct);
                pbCycling.setProgress(minCyclingAct * 100 / minTotalsAct);
                pbRunning.setProgress(minRunningAct * 100 / minTotalsAct);
            }
        }
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
            if(dades == "calorias"){
                this.pes_cal_pasos= " Kcal";
            }
            else if(dades == "distancia"){
                this.pes_cal_pasos= " km";
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
