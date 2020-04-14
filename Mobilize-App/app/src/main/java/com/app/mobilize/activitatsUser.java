package com.app.mobilize;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.app.mobilize.Pojo.ActivitatFinalitzada;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class activitatsUser extends AppCompatActivity {

    String username;
    ArrayList<ActivitatFinalitzada> activitats;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitats_user);

        username = this.getIntent().getStringExtra("user");
        getActivitats();

        listView = findViewById(R.id.listViewActivitats);

        ActivitatListAdapter adapter = new ActivitatListAdapter(this, R.layout.fila_activitat, activitats);
        listView.setAdapter(adapter);


    }

    public void getActivitats(){
        activitats = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        activitats.add(new ActivitatFinalitzada(calendar, username, 12334, 12.5, 1, 34.5));
        activitats.add(new ActivitatFinalitzada(calendar, username, 876, 3, 0, 50));
        activitats.add(new ActivitatFinalitzada(calendar, username, 1245, 4.5, 2, 25.5));
        activitats.add(new ActivitatFinalitzada(calendar, username, 8987654, 17, 0, 340.5));
        activitats.add(new ActivitatFinalitzada(calendar, username, 345, 1, 1, 12));

    }
}
