package com.app.sqliteopenhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Rutines (nom TEXT primary key, info TEXT, nivell INTEGER, modalitat TEXT, exercicis TEXT)";
    String sqlCreate1 = "CREATE TABLE Exercicis (nom TEXT, modalitat TEXT, repeticions INTEGER, nivell INTEGER, kcal DOUBLE, musculs TEXT, tecnica TEXT, series INTEGER, kmh TEXT, durada_min TEXT, pendent BOOLEAN, punts INTEGER)";
    String sqlCreate2 = "CREATE TABLE Plannings (nom TEXT primary key, info TEXT, nivell INTEGER, dies INTEGER, modalitat TEXT, exercicis TEXT)";
    private ArrayList<Rutina> cjtRutines;
    private ArrayList<Exercici> cjtExercicis;
    private ArrayList<Planning> cjtPlannings;

    public AdminSQLiteOpenHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        insertAllRutines();
        insertAllExercicis();
        insertAllPlannings();
        for ( int i=0; i<cjtRutines.size(); i++ ){
            ContentValues contentValues = new ContentValues();

            contentValues.put("nom",cjtRutines.get(i).getNom());
            contentValues.put("info", cjtRutines.get(i).getInfo());
            contentValues.put("nivell", cjtRutines.get(i).getNivell());
            contentValues.put("modalitat", cjtRutines.get(i).getModalitat());
            contentValues.put("exercicis", cjtRutines.get(i).getExercicis());

            db.insert("Rutines", null, contentValues);
        }

        db.execSQL(sqlCreate1);

        for ( int i=0; i<cjtExercicis.size(); i++ ){
            ContentValues contentValues = new ContentValues();

            contentValues.put("nom",cjtExercicis.get(i).getNom());
            contentValues.put("modalitat", cjtExercicis.get(i).getModalitat());
            contentValues.put("repeticions", cjtExercicis.get(i).getRepeticions());
            contentValues.put("nivell", cjtExercicis.get(i).getNivell());
            contentValues.put("kcal", cjtExercicis.get(i).getKcal());
            contentValues.put("musculs", cjtExercicis.get(i).getMusculs());
            contentValues.put("tecnica", cjtExercicis.get(i).getTecnica());
            contentValues.put("series", cjtExercicis.get(i).getSeries());
            contentValues.put("kmh", cjtExercicis.get(i).getKmh());
            contentValues.put("durada_min", cjtExercicis.get(i).getDuradamin());
            contentValues.put("pendent", cjtExercicis.get(i).getPendent());
            contentValues.put("punts", cjtExercicis.get(i).getPunts());


            db.insert("Exercicis", null, contentValues);
        }

        db.execSQL(sqlCreate2);

        for ( int i=0; i<cjtPlannings.size(); i++ ){
            ContentValues contentValues = new ContentValues();

            contentValues.put("nom",cjtPlannings.get(i).getNom());
            contentValues.put("info", cjtPlannings.get(i).getInfo());
            contentValues.put("nivell", cjtPlannings.get(i).getNivell());
            contentValues.put("dies", cjtPlannings.get(i).getDies());
            contentValues.put("modalitat", cjtPlannings.get(i).getModalitat());
            contentValues.put("rutines", cjtPlannings.get(i).getRutines());

            db.insert("Plannings", null, contentValues);
        }

    }

    private void insertAllPlannings() {
        cjtPlannings = new ArrayList<>();
        cjtPlannings.add(new Planning("Road to fit", "Planning extens de categoria alta", 2, 14, "workout","Abdominals," ));
    }

    private void insertAllRutines () {
        cjtRutines = new ArrayList<>();
        cjtRutines.add(new Rutina("Abdominals", "Abdominals per ...", 0, "workout", "Abdominals superiors,Pectorals,"));
        cjtRutines.add(new Rutina("Flexions", "Flexions per ...", 1, "workout","global"));
        cjtRutines.add(new Rutina("Flexions2", "Flexions per ...", 2, "running", "global"));
        cjtRutines.add(new Rutina("Flexions3", "Flexions per ...", 0, "workout",  "global"));

    }

    private void insertAllExercicis () {
        cjtExercicis = new ArrayList<>();
        cjtExercicis.add(new Exercici("Abdominals superiors", null, 0, 0, false, "Abdominals", 15, 3, "abdominals", 0, "workout", 250));
        cjtExercicis.add(new Exercici("Pectorals", null, 0, 0, false, "Abdominals", 15, 3, "abdominals", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Espatlla", null, 0, 0, false, "Abdominals", 15, 3, "abdominals", 0, "workout", 150));
        cjtExercicis.add(new Exercici("Cames", null, 0, 0, false, "Abdominals", 15, 3, "Has de fer abdominals aixi: ", 0, "workout", 300));
        cjtExercicis.add(new Exercici("xxx", null, 0, 0, false, "Abdominals", 15, 3, "Has de fer abdominals aixi: ", 0, "workout", 400));
        cjtExercicis.add(new Exercici("yyy", null, 0, 0, false, "Abdominals", 15, 3, "Has de fer abdominals aixi: ", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Correr", "10", 120, 1000, true, null, 0, 0, null, 1, "running", 150));
        cjtExercicis.add(new Exercici("Bicicleta", "20", 60, 2000, true, null, 0, 0, null, 2, "cycling", 200));


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}