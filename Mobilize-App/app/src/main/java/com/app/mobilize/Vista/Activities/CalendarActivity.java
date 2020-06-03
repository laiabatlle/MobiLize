package com.app.mobilize.Vista.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.R;
import com.app.sqliteopenhelper.AdminSQLiteOpenHelper;
import com.app.sqliteopenhelper.Exercici;
import com.app.sqliteopenhelper.Planning;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarActivity extends AppCompatActivity {

    ArrayList<eventDescription> eventDescriptions;
    List<EventDay> events;
    TextView tvCalendarTitle;
    EditText etCalendarInfo;
    CalendarView calendarView;
    Button goToRutina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        goToRutina = findViewById(R.id.bGoToRutina);
        goToRutina.setVisibility(View.GONE);
        goToRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDayRutina();
            }
        });

        etCalendarInfo = findViewById(R.id.etInfo);
        etCalendarInfo.setEnabled(false);
        etCalendarInfo.setText("");

        eventDescriptions = new ArrayList<>();
        events = new ArrayList<>();

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setHeaderColor(R.color.colorPrimary);

        // ---------------------------------------------
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDades = admin.getWritableDatabase();

        String nom = "";
        String info = "";
        String rutines = "";
        String dataInici = "";

        Cursor fila = BaseDeDades.rawQuery("select nom, info, rutines, dataInici from PlanningActual", null);
        while (fila.moveToNext()) {
            nom = fila.getString(0);
            info = fila.getString(1);
            rutines = fila.getString(2);
            dataInici = fila.getString(3);
        }
        BaseDeDades.close();

        Toast.makeText(this, dataInici, Toast.LENGTH_LONG).show();

        ArrayList<String> rutinesPlanning = new ArrayList<>();
        String rutinaNom = "";
        for ( int i=0; i<rutines.length(); i++ )  {
            if ( rutines.charAt(i) == ',' ) {
                rutinesPlanning.add(rutinaNom);
                rutinaNom = "";
            }
            else {
                rutinaNom = rutinaNom + rutines.charAt(i);
            }
        }
        Calendar calendar = Calendar.getInstance();
        String año, mes, dia;
        año = mes = dia = "";
        int count = 0;
        for ( int i=0; i<dataInici.length(); i++ ) {
            if ( dataInici.charAt(i) == '/' ) count++;
            else if ( count == 0 ) {
                dia = dia + dataInici.charAt(i);
            }
            else if ( count == 1 ) mes = mes + dataInici.charAt(i);
            else año = año + dataInici.charAt(i);
        }



       if(!dataInici.isEmpty()) {
            calendar.set(Calendar.YEAR, Integer.valueOf(año));
            calendar.set(Calendar.MONTH, Integer.valueOf(mes));
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dia));
        }

       int descans = 0;
        BaseDeDades = admin.getWritableDatabase();
        for ( int i = 0; i < rutinesPlanning.size(); i++ ){
            if( i%6 == 5) descans++;
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, i+descans);
            String[] args = new String[] {rutinesPlanning.get(i)};


            Cursor fila2 = BaseDeDades.rawQuery("select info, modalitat from Rutines where nom = ?", args);
            while (fila2.moveToNext()) {
                String infoR = fila2.getString(0);
                String modalitat = fila2.getString(1);


                EventDay eventAux = null;
                eventDescription eventDescr = null;
                if (modalitat.equals("workout")) {
                    eventAux = new EventDay(calendar, R.drawable.gimnasio, Color.parseColor("#228B22"));
                     eventDescr = new eventDescription(eventAux, rutinesPlanning.get(i), infoR);

                }
                else if (modalitat.equals("cycling")) {
                     eventAux = new EventDay(calendar, R.drawable.bicicleta, Color.parseColor("#228B22"));
                     eventDescr = new eventDescription(eventAux, rutinesPlanning.get(i), infoR);
                }
                else {
                     eventAux = new EventDay(calendar, R.drawable.funcionamiento, Color.parseColor("#228B22"));
                     eventDescr = new eventDescription(eventAux, rutinesPlanning.get(i), infoR);
                }
                events.add(eventAux);
                eventDescriptions.add(eventDescr);
            }
        }
        BaseDeDades.close();
        // -------------------------------------------

        int actualDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Calendar minimumDate = Calendar.getInstance();
        minimumDate.set(minimumDate.get(Calendar.YEAR), minimumDate.get(Calendar.MONTH), 1);
        calendarView.setMinimumDate(minimumDate);
        ArrayList<Calendar> daysAnt = new ArrayList<>();

        for ( int i=1; i<actualDay; i++ ) {
            Calendar calendarAux = Calendar.getInstance();
            int year = calendarAux.get(Calendar.YEAR);
            int month = calendarAux.get(Calendar.MONTH);
            calendarAux.set(year, month, i);
            daysAnt.add(calendarAux);
        }

        calendarView.setHighlightedDays(daysAnt);

        tvCalendarTitle = findViewById(R.id.tvCalendarTitle);
        tvCalendarTitle.setText("");

        calendarView.setEvents(events);
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                boolean trobat = false;
                for ( int i=0; i<eventDescriptions.size(); i++ ) {
                    if ( eventDay == eventDescriptions.get(i).getEventDay() ) {
                        //showCustomDialog(eventDescriptions.get(i).getDescription());
                        trobat = true;
                        tvCalendarTitle.setText( "    " + eventDescriptions.get(i).getDescription());
                        etCalendarInfo.setText( eventDescriptions.get(i).getInfo());
                        Calendar calendar1 = eventDay.getCalendar();
                        Calendar calendar2 = Calendar.getInstance();
                        if ( calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                              calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)) {
                            goToRutina.setVisibility(View.VISIBLE);
                        }
                        else goToRutina.setVisibility(View.GONE);
                    }
                }
                if ( trobat == false ) {
                    tvCalendarTitle.setText("");
                    etCalendarInfo.setText("");
                    goToRutina.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showCustomDialog (String descr) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("title");
        builder.setMessage(descr);

        AlertDialog dialog = builder.create();
        dialog.show();

        /*final View customLayout = getLayoutInflater().inflate(R.layout.finish_activity, null);
        builder.setView(customLayout);*/
    }

    private void goToDayRutina() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDades = admin.getWritableDatabase();

        for ( int i=0; i<eventDescriptions.size(); i++ ) {
            Calendar calendar = eventDescriptions.get(i).getEventDay().getCalendar();
            Calendar calendar1 = Calendar.getInstance();
            if (calendar1.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                    calendar1.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                    calendar1.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                String nomRutina = eventDescriptions.get(i).getDescription();
                String[] args = new String[] {nomRutina};
                Cursor fila = BaseDeDades.rawQuery("select nivell, modalitat, exercicis from Rutines where nom = ?", args);
                while (fila.moveToNext()) {
                    int nivell = fila.getInt(0);
                    String modalitat = fila.getString(1);
                    String exercicis = fila.getString(2);
                    ArrayList<Exercici> exerciciArrayList = stringToArray(exercicis, nivell, modalitat);

                    Intent intent;
                    if ( modalitat.equals("workout") ) intent = new Intent(this, AvancaRutina.class);
                    else intent = new Intent(this, AvancaRutinaNoWorkout.class);
                    intent.putParcelableArrayListExtra("exercici", exerciciArrayList);
                    startActivity(intent);
                    this.finish();
                }
            }
        }
    }

    private ArrayList<Exercici> stringToArray(String exercicis, int dificultat, String modalitat) { // he de buscar l'exercici que tingui aquells parametres, i crear un nou exercici i afegirlo a e.
        ArrayList<Exercici> e = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDades = admin.getWritableDatabase();


        String[] args;
        Cursor fila;

        String s = "";
        for(int i = 0; i < exercicis.length(); ++i) {
            char c = exercicis.charAt(i);
            if(c == ',' ) {

                args = new String[] {s};
                fila = BaseDeDades.rawQuery("select kmh, durada_min, kcal, pendent, musculs, repeticions, series, tecnica, punts from Exercicis where nom =?", args);

                while (fila.moveToNext()) {
                    String kmh = fila.getString(0);
                    int durada_min = fila.getInt(1);
                    double kcal = fila.getDouble(2);
                    boolean pendent = false;
                    String musculs = fila.getString(4);
                    int repeticions = fila.getInt(5);
                    int series = fila.getInt(6);
                    String tecnica = fila.getString(7);
                    int punts = fila.getInt(8);
                    Exercici ex = new Exercici(s, kmh, durada_min, kcal, pendent, musculs, repeticions, series, tecnica, dificultat, modalitat, punts);
                    e.add(ex);
                }



                s = "";
            }

            else {
                s = s + c;
            }
        }
        BaseDeDades.close();
        return e;
    }

    private class eventDescription {
        private EventDay eventDay;
        private String description;
        private String info;

        public eventDescription ( EventDay eventDay, String description, String info ) {
            this.description = description;
            this.eventDay = eventDay;
            this.info = info;
        }

        public EventDay getEventDay() {
            return eventDay;
        }

        public void setEventDay(EventDay eventDay) {
            this.eventDay = eventDay;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
