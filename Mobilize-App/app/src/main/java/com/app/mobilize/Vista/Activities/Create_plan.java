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
import android.widget.TextView;
import android.widget.Toast;

import com.app.mobilize.R;
import com.app.sqliteopenhelper.AdminSQLiteOpenHelper;
import com.app.sqliteopenhelper.Exercici;
import com.app.sqliteopenhelper.Rutina;

import java.util.ArrayList;

public class Create_plan extends AppCompatActivity implements AdapterRutPlan.OnNoteListener{

    ArrayList<Rutina> Rutines;
    RecyclerView recycler;
    public static ArrayList<Rutina> eaux = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        EditText e = findViewById(R.id.editText5);

        eaux.clear();

        int dificultat = getIntent().getIntExtra("dificultat",0);
        String modalitat = getIntent().getStringExtra("modalitat");

        recycler = (RecyclerView) findViewById(R.id.recyclerView5);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDades = admin.getWritableDatabase();

        String[] args = new String[] {modalitat};


        //Cursor fila = BaseDeDades.rawQuery("select nom, info from Rutines  where nivell =" + dificultat + " and modalitat =" + modalitat ,  null);

        Cursor fila = BaseDeDades.rawQuery("select nom, info, exercicis from Rutines where modalitat=? AND nivell =" + dificultat, args);

        Rutines = new ArrayList<>();
        while (fila.moveToNext()) {
            String nom = fila.getString(0);
            String info = fila.getString(1);
            String exercicis = fila.getString(2);
            Rutina r = new Rutina(nom, info, dificultat, modalitat, exercicis);
            Rutines.add(r);
        }


        AdapterRutPlan adapter = new AdapterRutPlan(Rutines, this, "si");
        recycler.setAdapter(adapter);

        BaseDeDades.close();

    }

    public static void setRutina(Rutina r) {

        eaux.add(r);

    }



    public static void unsetRutina(Rutina r) {

        for (int i = 0; i < eaux.size(); ++i) {
            if(eaux.get(i) == r) eaux.remove(i);
        }

    }

    @Override
    public void onNoteClick(int position) {

        Intent intent = new Intent(this, PopUpRutina.class);
        intent.putExtra("rutina", Rutines.get(position));
        startActivityForResult(intent, 0);



    }

    public void Crear(View view) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        EditText nomedx = (EditText)findViewById(R.id.editText5);
        EditText infoedx = (EditText)findViewById(R.id.editText8);
        int dificultat = getIntent().getIntExtra("dificultat",0);
        String modalitat = getIntent().getStringExtra("modalitat");
        int dies = getIntent().getIntExtra("dies",0);

        String nom = nomedx.getText().toString();
        String info = infoedx.getText().toString();

        String ruts = transformaArray(eaux);

        eaux.clear();

        ContentValues registro = new ContentValues();
        registro.put("nom", nom);
        registro.put("info", info);
        registro.put("nivell", dificultat);
        registro.put("dies", dies);
        registro.put("modalitat", modalitat);
        registro.put("rutines", ruts);

        BaseDeDatos.insert("Plannings", null, registro);


        BaseDeDatos.close();

        nomedx.setText("Nom");
        infoedx.setText("Info");



        Intent intent = new Intent(view.getContext(), TipusPlan.class);
        intent.putExtra("tipus", "prova");
        startActivityForResult(intent, 0);
        this.finish();

       // Toast.makeText(this, R.string.CreaRutinaCorrecte, Toast.LENGTH_SHORT).show();

    }

    private String transformaArray(ArrayList<Rutina> eaux) {
        String s = "";
        for (int i = 0; i < eaux.size(); ++i) {
            s = s + eaux.get(i).getNom() + ",";
        }
        return s;
    }
}
