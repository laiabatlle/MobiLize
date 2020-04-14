package com.app.sqliteopenhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import java.util.ArrayList;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Rutines (nom TEXT primary key, info TEXT, nivell INTEGER, modalitat TEXT)";

    private ArrayList<Rutina> cjtRutines;

    public AdminSQLiteOpenHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        insertAllRutines();
        for ( int i=0; i<cjtRutines.size(); i++ ){
            ContentValues contentValues = new ContentValues();

            contentValues.put("nom",cjtRutines.get(i).getNom());
            contentValues.put("info", cjtRutines.get(i).getInfo());
            contentValues.put("nivell", cjtRutines.get(i).getNivell());
            contentValues.put("kcal", cjtRutines.get(i).getModalitat());

            db.insert("Rutines", null, contentValues);
        }
    }

    private void insertAllRutines () {
        cjtRutines = new ArrayList<>();
        cjtRutines.add(new Rutina("Abdominals", "Abdominals per ...", 0, "ciclisme"));
        cjtRutines.add(new Rutina("Flexions", "Flexions per ...", 1, "workout"));
        cjtRutines.add(new Rutina("Flexions2", "Flexions per ...", 2, "running"));
        cjtRutines.add(new Rutina("Flexions3", "Flexions per ...", 0, "workout"));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}