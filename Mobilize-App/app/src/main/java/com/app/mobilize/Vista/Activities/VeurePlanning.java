package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mobilize.R;
import com.app.sqliteopenhelper.AdminSQLiteOpenHelper;
import com.app.sqliteopenhelper.Exercici;
import com.app.sqliteopenhelper.Rutina;

import java.util.ArrayList;
import java.util.Calendar;

public class VeurePlanning extends AppCompatActivity implements AdapterRutPlan.OnNoteListener{

    EditText tvnom;
    EditText tvinfo;
    int dificultat;
    String modalitat;
    String nom;
    String info;
    int dies;
    String rutines;
    RecyclerView recycler;
    ArrayList<Rutina> rut;
    public static ArrayList<Rutina> eaux1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veure_planning);

        tvnom = findViewById(R.id.editText9);
        tvinfo = findViewById(R.id.editText10);

        rutines = getIntent().getStringExtra("rutines");
        nom = getIntent().getStringExtra("nom");
        info = getIntent().getStringExtra("info");
        modalitat = getIntent().getStringExtra("modalitat");
        dificultat = getIntent().getIntExtra("dificultat", 0);
        dies = getIntent().getIntExtra("dies", -1);
        tvnom.setText(nom);
        tvinfo.setText(info);

        eaux1.clear();

        recycler = (RecyclerView) findViewById(R.id.recyclerView4);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rut = new ArrayList<>();
        rut = stringToArray(rutines, dificultat, modalitat);

        AdapterRutPlan adapter = new AdapterRutPlan(rut, this, "no");
        recycler.setAdapter(adapter);





    }

    private ArrayList<Rutina> stringToArray(String rutines, int dificultat, String modalitat) {
        ArrayList<Rutina> r = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDades = admin.getWritableDatabase();


        String[] args;
        Cursor fila;

        String s = "";
        for(int i = 0; i < rutines.length(); ++i) {
            char c = rutines.charAt(i);
            if(c == ',' ) {

                args = new String[] {s};
                fila = BaseDeDades.rawQuery("select info, exercicis from Rutines where nom =?", args);

                while (fila.moveToNext()) {
                    String info = fila.getString(0);
                    String exercicis = fila.getString(1);
                    Rutina ru = new Rutina(s,info,dificultat,modalitat,exercicis);
                    r.add(ru);
                }



                s = "";
            }

            else {
                s = s + c;
            }
        }
        BaseDeDades.close();
        return r;
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, PopUpRutina.class);
        intent.putExtra("rutina", rut.get(position));
        startActivityForResult(intent, 0);
    }

    public void Comença(View view){
        // ficar el planning actual en sqlite
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        BaseDeDatos.execSQL("DELETE FROM PlanningActual");

        ContentValues contentValues = new ContentValues();

        contentValues.put("nom",nom);
        contentValues.put("info", info);
        contentValues.put("nivell", dificultat);
        contentValues.put("dies", dies);
        contentValues.put("modalitat", modalitat);
        contentValues.put("rutines", rutines);

        BaseDeDatos.insert("PlanningActual", null, contentValues);

        Intent intent = new Intent(this, CalendarActivity.class);
        startActivityForResult(intent, 0);
    }

    public void Elimina(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String[] args = new String[] {nom};
        BaseDeDatos.delete("Plannings", "nom=?", args);


        BaseDeDatos.close();

        this.finish();

        Toast.makeText(this, R.string.EsborraPlaCorrecte, Toast.LENGTH_SHORT).show();

    }

    public void Modifica(View view) {

        if(String.valueOf(tvnom.getText()).isEmpty()){
            Toast.makeText(this, R.string.CreaPlaIncorrecte, Toast.LENGTH_SHORT).show();
        }
        else {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


            String s = transformaArray(eaux1);

            if(s.isEmpty()){
                Toast.makeText(this, R.string.SelectRoutine, Toast.LENGTH_SHORT).show();
            }
            else {

                ContentValues registro = new ContentValues();
                registro.put("nom", String.valueOf(tvnom.getText()));
                registro.put("info", String.valueOf(tvinfo.getText()));
                registro.put("rutines", s);

                String[] args = new String[]{nom};
                BaseDeDatos.update("Plannings", registro, "nom=?", args);


                BaseDeDatos.close();

                this.finish();
            }
        }




       // Toast.makeText(this, R.string.ModificaRutinaCorrecte, Toast.LENGTH_SHORT).show();

    }

    private String transformaArray(ArrayList<Rutina> eaux) {
        String s = "";
        for (int i = 0; i < eaux.size(); ++i) {
            s = s + eaux.get(i).getNom() + ",";
        }
        return s;
    }

    public static void unsetRutina(Rutina r) {

        for (int i = 0; i < eaux1.size(); ++i) {
            if(eaux1.get(i) == r) eaux1.remove(i);
        }

    }

    public static void setRutina(Rutina r) {

        eaux1.add(r);
    }
}
