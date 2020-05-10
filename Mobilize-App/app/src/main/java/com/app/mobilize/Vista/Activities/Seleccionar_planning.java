package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.app.mobilize.R;
import com.app.sqliteopenhelper.AdminSQLiteOpenHelper;
import com.app.sqliteopenhelper.Planning;
import com.app.sqliteopenhelper.Rutina;

import java.util.ArrayList;

public class Seleccionar_planning extends AppCompatActivity implements AdapterPlan.OnNoteListener{

    TextView tv1;
    TextView tv2;
    TextView tv3;
    RecyclerView recycler;
   ArrayList<Planning>  PlanningArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_planning);

        recycler = (RecyclerView) findViewById(R.id.Recyclerid1);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        String modalitat = getIntent().getStringExtra("modalitat");
        int dificultat =  getIntent().getIntExtra("nivell", 0);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDades = admin.getWritableDatabase();


        String[] args = new String[] {modalitat};
        String duracio = getIntent().getStringExtra("duracio");
        int dies = 0;
        if(duracio.equals("1 week"))  dies = 7;
        else if (duracio.equals("15 days")) dies = 15;
        else if (duracio.equals("1 month")) dies = 30;
        else if (duracio.equals("2 months")) dies = 60;
        else if (duracio.equals("3 months")) dies = 90;

       Cursor fila = BaseDeDades.rawQuery("select nom, info, rutines from Plannings where modalitat=? AND nivell =" + dificultat + " AND dies =" +dies, args);
       // Cursor fila = BaseDeDades.rawQuery("select nom, info, dies, rutines from Plannings where modalitat =?", args);
        PlanningArrayList = new ArrayList<>();
        while (fila.moveToNext()) {
            String nom = fila.getString(0);
            String info = fila.getString(1);
            String rutines = fila.getString(2);
            Planning p = new Planning(nom, info, dificultat, dies, modalitat, rutines);
            PlanningArrayList.add(p);
        }

        AdapterPlan adapter = new AdapterPlan(PlanningArrayList, this);
        recycler.setAdapter(adapter);

        BaseDeDades.close();

        tv1 = findViewById(R.id.textView36);
        tv2 = findViewById(R.id.textView37);
        tv3 = findViewById(R.id.textView38);

        int a = getIntent().getIntExtra("nivell", 0);
        String s = Integer.toString(a);


        tv1.setText(modalitat);

        tv2.setText(duracio);
        tv3.setText(s);

    }

    @Override
    public void onNoteClick(int position) {


            Intent intent = new Intent(this, NivellEntrenament.class);
            startActivityForResult(intent, 0);


    }
}
