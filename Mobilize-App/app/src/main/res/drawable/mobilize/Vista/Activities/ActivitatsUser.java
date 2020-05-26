package com.app.mobilize.Vista.Activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.Model.ActivitatFinalitzada;
import com.app.mobilize.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivitatsUser extends AppCompatActivity {

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
        String data = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(calendar.get(Calendar.MONTH)) + "/" + String.valueOf(calendar.get(Calendar.YEAR));
        activitats.add(new ActivitatFinalitzada(data, username, 12334, 12.5, 1, 34.5));
        activitats.add(new ActivitatFinalitzada(data, username, 876, 3, 0, 50));
        activitats.add(new ActivitatFinalitzada(data, username, 1245, 4.5, 2, 25.5));
        activitats.add(new ActivitatFinalitzada(data, username, 8987654, 17, 0, 340.5));
        activitats.add(new ActivitatFinalitzada(data, username, 345, 1, 1, 12));

    }
}