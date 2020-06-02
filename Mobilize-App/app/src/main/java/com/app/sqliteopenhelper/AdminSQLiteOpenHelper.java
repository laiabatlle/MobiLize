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
    String sqlCreate2 = "CREATE TABLE Plannings (nom TEXT primary key, info TEXT, nivell INTEGER, dies INTEGER, modalitat TEXT, rutines TEXT)";
    String sqlCreate3 = "CREATE TABLE PlanningActual (nom TEXT primary key, info TEXT, nivell INTEGER, dies INTEGER, modalitat TEXT, rutines TEXT)";

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

        db.execSQL(sqlCreate3);

    }

    private void insertAllPlannings() {
        cjtPlannings = new ArrayList<>();
        cjtPlannings.add(new Planning("Road to fit", "Planning extens de categoria alta", 2, 7, "workout","Abdominals," ));
        cjtPlannings.add(new Planning("xxx", "Planning extens de categoria alta", 1, 7, "running","Abdominals," ));
        cjtPlannings.add(new Planning("yyy", "Planning extens de categoria alta", 0, 30, "runningcyclingworkout","Abdominals," ));
        cjtPlannings.add(new Planning("zzz", "Planning extens de categoria alta", 2 , 60, "workout","Abdominals," ));
        cjtPlannings.add(new Planning("Road to xxx", "Planning extens de categoria alta", 2, 90, "cycling","Abdominals," ));
        cjtPlannings.add(new Planning("Road to yyy", "Planning extens de categoria alta", 2, 7, "workout","Abdominals," ));
        cjtPlannings.add(new Planning("Road to zzz", "Planning extens de categoria alta", 2, 7, "workout","Abdominals," ));
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
        cjtExercicis.add(new Exercici("Abdominals", null, 0, 0, false, "Abdominals", 10, 3, "abdominals", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Abdominals", null, 0, 0, false, "Abdominals", 20, 3, "abdominals", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Abdominals", null, 0, 0, false, "Abdominals", 30, 3, "abdominals", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Burpees", null, 0, 0, false, "Abdominals", 10, 3, "burpees", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Burpees", null, 0, 0, false, "Abdominals", 20, 3, "burpees", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Burpees", null, 0, 0, false, "Abdominals", 30, 3, "burpees", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Hyper Extension", null, 0, 0, false, "Abdominals", 10, 3, "hyperextension", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Hyper Extension", null, 0, 0, false, "Abdominals", 20, 3, "hyperextension", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Hyper Extension", null, 0, 0, false, "Abdominals", 30, 3, "hyperextension", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Incline Push-Ups", null, 0, 0, false, "Abdominals", 10, 3, "inclinepushupsx16", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Incline Push-Ups", null, 0, 0, false, "Abdominals", 20, 3, "inclinepushupsx16", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Incline Push-Ups", null, 0, 0, false, "Abdominals", 30, 3, "inclinepushupsx16", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Push-Ups", null, 0, 0, false, "Abdominals", 10, 3, "inclinepushups", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Push-Ups", null, 0, 0, false, "Abdominals", 20, 3, "inclinepushups", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Push-Ups", null, 0, 0, false, "Abdominals", 30, 3, "inclinepushups", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Knee Push-Ups", null, 0, 0, false, "Abdominals", 10, 3, "kneepushups", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Knee Push-Ups", null, 0, 0, false, "Abdominals", 20, 3, "kneepushups", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Knee Push-Ups", null, 0, 0, false, "Abdominals", 30, 3, "kneepushups", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Mountain Climber", null, 0, 0, false, "Abdominals", 10, 3, "mountainclimber", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Mountain Climber", null, 0, 0, false, "Abdominals", 20, 3, "mountainclimber", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Mountain Climber", null, 0, 0, false, "Abdominals", 30, 3, "mountainclimber", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Jumping Squats", null, 0, 0, false, "Abdominals", 10, 3, "jumpingsquats", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Jumping Squats", null, 0, 0, false, "Abdominals", 20, 3, "jumpingsquats", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Jumping Squats", null, 0, 0, false, "Abdominals", 30, 3, "jumpingsquats", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Sit-Ups", null, 0, 0, false, "Abdominals", 10, 3, "situps", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Sit-Ups", null, 0, 0, false, "Abdominals", 20, 3, "situps", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Sit-Ups", null, 0, 0, false, "Abdominals", 30, 3, "situps", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Squats", null, 0, 0, false, "Abdominals", 10, 3, "squats", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Squats", null, 0, 0, false, "Abdominals", 20, 3, "squats", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Squats", null, 0, 0, false, "Abdominals", 30, 3, "squats", 2, "workout", 300));


        cjtExercicis.add(new Exercici("Triceps Dips", null, 0, 0, false, "Abdominals", 10, 3, "tricepsdips", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Triceps Dips", null, 0, 0, false, "Abdominals", 20, 3, "tricepsdips", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Triceps Dips", null, 0, 0, false, "Abdominals", 30, 3, "tricepsdips", 2, "workout", 300));

        cjtExercicis.add(new Exercici("V-Up", null, 0, 0, false, "Abdominals", 10, 3, "vup", 0, "workout", 100));
        cjtExercicis.add(new Exercici("V-Up", null, 0, 0, false, "Abdominals", 20, 3, "vup", 1, "workout", 200));
        cjtExercicis.add(new Exercici("V-Up", null, 0, 0, false, "Abdominals", 30, 3, "vup", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Correr", "10", 120, 1000, true, null, 0, 0, null, 1, "running", 150));
        cjtExercicis.add(new Exercici("Bicicleta", "20", 60, 2000, true, null, 0, 0, null, 2, "cycling", 200));


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
