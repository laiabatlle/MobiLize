package com.app.mobilize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sqliteopenhelper.AdminSQLiteOpenHelper;
import com.app.sqliteopenhelper.Rutina;

import java.util.ArrayList;

public class PopUpRutina extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_rutina);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        Rutina item = (Rutina) getIntent().getSerializableExtra("rutina");
        EditText ed1 = (EditText) findViewById(R.id.editText6);
        EditText ed2 = (EditText) findViewById(R.id.editText7);
        ed1.setText(item.getNom());
        ed2.setText(item.getInfo());



    }


    public void Elimina(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Rutina item = (Rutina) getIntent().getSerializableExtra("rutina");

        String[] args = new String[] {item.getNom()};
        BaseDeDatos.delete("Rutines", "nom=?", args);


        BaseDeDatos.close();



        Intent intent = new Intent(view.getContext(),  ModalitatEntrenament.class);
        startActivityForResult(intent, 0);



        Toast.makeText(this, R.string.EsborraRutinaCorrecte, Toast.LENGTH_SHORT).show();

    }


    public void Modifica(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        EditText ed1 = (EditText) findViewById(R.id.editText6);
        EditText ed2 = (EditText) findViewById(R.id.editText7);

        ContentValues registro = new ContentValues();
        registro.put("nom", String.valueOf(ed1.getText()));
        registro.put("info", String.valueOf(ed2.getText()));

        Rutina item = (Rutina) getIntent().getSerializableExtra("rutina");

        String[] args = new String[] {item.getNom()};
        BaseDeDatos.update("Rutines", registro, "nom=?", args);


        BaseDeDatos.close();

        Intent intent = new Intent(view.getContext(), ModalitatEntrenament.class);
        startActivityForResult(intent, 0);






        Toast.makeText(this, R.string.ModificaRutinaCorrecte, Toast.LENGTH_SHORT).show();

    }



}
