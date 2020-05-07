package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.app.mobilize.R;

import com.app.sqliteopenhelper.AdminSQLiteOpenHelper;
import com.app.sqliteopenhelper.Exercici;
import com.app.sqliteopenhelper.Rutina;

import java.io.Serializable;
import java.util.ArrayList;

public class AfegirRutina extends AppCompatActivity implements AdapterDatos.OnNoteListener {


    ArrayList<Exercici> Exercicis;
    RecyclerView recycler;

    public static ArrayList<Exercici> eaux = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_rutina);

        eaux.clear();

        int dificultat = getIntent().getIntExtra("dificultat",0);
        String modalitat = getIntent().getStringExtra("modalitat");


        recycler = (RecyclerView) findViewById(R.id.Recyclerid);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDades = admin.getWritableDatabase();


        String[] args = new String[] {modalitat};


        //Cursor fila = BaseDeDades.rawQuery("select nom, info from Rutines  where nivell =" + dificultat + " and modalitat =" + modalitat ,  null);

        Cursor fila = BaseDeDades.rawQuery("select nom, kmh, durada_min, kcal, pendent, musculs, repeticions, series, tecnica from Exercicis where modalitat=? AND nivell =" + dificultat, args);


        Exercicis = new ArrayList<>();




        while (fila.moveToNext()) {
            String nom = fila.getString(0);
            String kmh = fila.getString(1);
            int durada_min = fila.getInt(2);
            double kcal = fila.getDouble(3);
            boolean pendent = false;
            String musculs = fila.getString(5);
            int repeticions = fila.getInt(6);
            int series = fila.getInt(7);
            String tecnica = fila.getString(8);
            Exercici e = new Exercici(nom, kmh, durada_min, kcal, pendent,  musculs, repeticions, series,  tecnica,  dificultat, modalitat);
            Exercicis.add(e);
       }





        AdapterDatos adapter = new AdapterDatos(Exercicis, this);
        recycler.setAdapter(adapter);

        BaseDeDades.close();



    }


    public void Registrar(View view) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        EditText nomedx = (EditText)findViewById(R.id.editText3);
        EditText infoedx = (EditText)findViewById(R.id.editText4);
        int dificultat = getIntent().getIntExtra("dificultat",0);
        String modalitat = getIntent().getStringExtra("modalitat");

        String nom = nomedx.getText().toString();
        String info = infoedx.getText().toString();

        String exer = transformaArray(eaux);

        eaux.clear();






        ContentValues registro = new ContentValues();
        registro.put("nom", nom);
        registro.put("info", info);
        registro.put("nivell", dificultat);
        registro.put("modalitat", modalitat);
        registro.put("exercicis", exer);

        BaseDeDatos.insert("Rutines", null, registro);


        BaseDeDatos.close();

        nomedx.setText("Nom");
        infoedx.setText("Info");



        Intent intent = new Intent(view.getContext(), NivellEntrenament.class);
        startActivityForResult(intent, 0);

        Toast.makeText(this, R.string.CreaRutinaCorrecte, Toast.LENGTH_SHORT).show();

    }

    private String transformaArray(ArrayList<Exercici> eaux) {
        String s = "";
        for (int i = 0; i < eaux.size(); ++i) {
            s = s + eaux.get(i).getNom() + ",";
        }
        return s;
    }

    public static void setExercici(Exercici e) {

        eaux.add(e);

    }



    public static void unsetExercici(Exercici e) {

        for (int i = 0; i < eaux.size(); ++i) {
            if(eaux.get(i) == e) eaux.remove(i);
        }

    }

    @Override
    public void onNoteClick(int position) {

        if(Exercicis.get(position).getKmh() == null) {
            Intent intent = new Intent(this, VeureExercici.class);
            intent.putExtra("exercici", Exercicis.get(position));
            startActivityForResult(intent, 0);

        }

        else {
            Intent intent = new Intent(this, VeureExerciciNoWorkout.class);
            intent.putExtra("exercici", Exercicis.get(position));
            startActivityForResult(intent, 0);
        }
    }
}
