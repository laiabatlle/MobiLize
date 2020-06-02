package com.app.mobilize.Vista.Activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.R;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        etCalendarInfo = findViewById(R.id.etInfo);
        etCalendarInfo.setEnabled(false);
        etCalendarInfo.setText("");

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setHeaderColor(R.color.colorPrimary);

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

        eventDescriptions = new ArrayList<>();
        events = new ArrayList<>();

        EventDay eventAux = null;
        eventDescription eventDescr = null;

        for ( int i=2; i<30; i++ ) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, i);
            boolean noRest = true;

            if ( i % 4 == 0 ) {
                eventAux = new EventDay(calendar, R.drawable.funcionamiento, Color.parseColor("#228B22"));
                eventDescr = new eventDescription(eventAux, "Rutina Running", "Esta rutina te hará fortalecer cuadriceps, isquiotibiales... Bla bla bla bla bla ...");
            }
            else if ( i % 4 == 1 ) {
                eventAux = new EventDay(calendar, R.drawable.bicicleta, Color.parseColor("#228B22"));
                eventDescr = new eventDescription(eventAux, "Rutina Ciclismo", "Esta rutina te hará fortalecer cuadriceps, isquiotibiales... Bla bla bla bla bla ...");
            }
            else if ( i % 4 == 2 ) {
                eventAux = new EventDay(calendar, R.drawable.gimnasio, Color.parseColor("#228B22"));
                eventDescr = new eventDescription(eventAux, "Rutina Workout", "Esta rutina te hará fortalecer cuadriceps, isquiotibiales... Bla bla bla bla bla ...");
            }
            else noRest = false;
            if ( noRest ) {
                events.add(eventAux);
                eventDescriptions.add(eventDescr);
            }

            Log.i("SIZE EVENTS", String.valueOf(events.size()));
        }
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
                    }
                }
                if ( trobat == false ) {
                    tvCalendarTitle.setText("");
                    etCalendarInfo.setText("");
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