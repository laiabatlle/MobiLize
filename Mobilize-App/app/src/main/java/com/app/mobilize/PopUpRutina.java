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
        TextView tv11 = (TextView)findViewById(R.id.textView11);
        tv11.setText(item.getNom());



    }


    public void Elimina(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Rutina item = (Rutina) getIntent().getSerializableExtra("rutina");

        String[] args = new String[] {item.getNom()};
        BaseDeDatos.delete("Rutines", "nom=?", args);


        BaseDeDatos.close();






        //Toast.makeText(this, "Borrado Exitoso", Toast.LENGTH_SHORT).show();

    }



}
