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
        cjtRutines.add(new Rutina("Workout fàcil", "Un conjunt d'exercicis de workout fàcil per a posar-te en forma", 0, "workout", "Abdominals Fàcil,Burpees Fàcil,Hyper Extension Fàcil,Incline Push-Ups Fàcil,Push-Ups Fàcil,Knee Push-Ups Fàcil,Mountain Climber Fàcil,Jumping Squats Fàcil,Squats Fàcil,Triceps Dips Fàcil,V-Up Fàcil,"));
        cjtRutines.add(new Rutina("Workout normal", "Un conjunt d'exercicis de workout normal per a tonificar el teu cos", 1, "workout", "Abdominals Normal,Burpees Normal,Hyper Extension Normal,Incline Push-Ups Normal,Push-Ups Normal,Knee Push-Ups Normal,Mountain Climber Normal,Jumping Squats Normal,Squats Normal,Triceps Dips Normal,V-Up Normal,"));
        cjtRutines.add(new Rutina("Workout difícil", "Un conjunt d'exercicis de workout difícil per a demostrar el que vals", 2, "workout", "Abdominals Difícil,Burpees Difícil,Hyper Extension Difícil,Incline Push-Ups Difícil,Push-Ups Difícil,Knee Push-Ups Difícil,Mountain Climber Difícil,Jumping Squats Difícil,Squats Difícil,Triceps Dips Difícil,V-Up Difícil,"));

        cjtRutines.add(new Rutina("Flexions", "Flexions per ...", 1, "workout","global"));
        cjtRutines.add(new Rutina("Flexions2", "Flexions per ...", 2, "running", "global"));
        cjtRutines.add(new Rutina("Flexions3", "Flexions per ...", 0, "workout",  "global"));

    }

    private void insertAllExercicis () {
        cjtExercicis = new ArrayList<>();
        cjtExercicis.add(new Exercici("Abdominals Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "abdominals", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Abdominals Normal", null, 0, 0, false, "Abdominals", 20, 3, "abdominals", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Abdominals Difícil", null, 0, 0, false, "Abdominals", 30, 3, "abdominals", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Burpees Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "burpees", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Burpees Normal", null, 0, 0, false, "Abdominals", 20, 3, "burpees", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Burpees Difícil", null, 0, 0, false, "Abdominals", 30, 3, "burpees", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Hyper Extension Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "hyperextension", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Hyper Extension Normal", null, 0, 0, false, "Abdominals", 20, 3, "hyperextension", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Hyper Extension Difícil", null, 0, 0, false, "Abdominals", 30, 3, "hyperextension", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Incline Push-Ups Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "inclinepushupsx16", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Incline Push-Ups Normal", null, 0, 0, false, "Abdominals", 20, 3, "inclinepushupsx16", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Incline Push-Ups Difícil", null, 0, 0, false, "Abdominals", 30, 3, "inclinepushupsx16", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Push-Ups Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "inclinepushups", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Push-Ups Normal", null, 0, 0, false, "Abdominals", 20, 3, "inclinepushups", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Push-Ups Difícil", null, 0, 0, false, "Abdominals", 30, 3, "inclinepushups", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Knee Push-Ups Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "kneepushups", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Knee Push-Ups Normal", null, 0, 0, false, "Abdominals", 20, 3, "kneepushups", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Knee Push-Ups Difícil", null, 0, 0, false, "Abdominals", 30, 3, "kneepushups", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Mountain Climber Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "mountainclimber", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Mountain Climber Normal", null, 0, 0, false, "Abdominals", 20, 3, "mountainclimber", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Mountain Climber Difícil", null, 0, 0, false, "Abdominals", 30, 3, "mountainclimber", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Jumping Squats Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "jumpingsquats", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Jumping Squats Normal", null, 0, 0, false, "Abdominals", 20, 3, "jumpingsquats", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Jumping Squats Difícil", null, 0, 0, false, "Abdominals", 30, 3, "jumpingsquats", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Sit-Ups Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "situps", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Sit-Ups Normal", null, 0, 0, false, "Abdominals", 20, 3, "situps", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Sit-Ups Difícil", null, 0, 0, false, "Abdominals", 30, 3, "situps", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Squats Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "squats", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Squats Normal", null, 0, 0, false, "Abdominals", 20, 3, "squats", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Squats Difícil", null, 0, 0, false, "Abdominals", 30, 3, "squats", 2, "workout", 300));


        cjtExercicis.add(new Exercici("Triceps Dips Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "tricepsdips", 0, "workout", 100));
        cjtExercicis.add(new Exercici("Triceps Dips Normal", null, 0, 0, false, "Abdominals", 20, 3, "tricepsdips", 1, "workout", 200));
        cjtExercicis.add(new Exercici("Triceps Dips Difícil", null, 0, 0, false, "Abdominals", 30, 3, "tricepsdips", 2, "workout", 300));

        cjtExercicis.add(new Exercici("V-Up Fàcil", null, 0, 0, false, "Abdominals", 10, 3, "vup", 0, "workout", 100));
        cjtExercicis.add(new Exercici("V-Up Normal", null, 0, 0, false, "Abdominals", 20, 3, "vup", 1, "workout", 200));
        cjtExercicis.add(new Exercici("V-Up Difícil", null, 0, 0, false, "Abdominals", 30, 3, "vup", 2, "workout", 300));

        cjtExercicis.add(new Exercici("Correr Fàcil 1 km", "1", 120, 1000, true, null, 0, 0, null, 0, "running", 100));
        cjtExercicis.add(new Exercici("Correr Fàcil 3 km", "3", 120, 1000, true, null, 0, 0, null, 0, "running", 125));
        cjtExercicis.add(new Exercici("Correr Fàcil 5 km", "5", 120, 1000, true, null, 0, 0, null, 0, "running", 150));

        cjtExercicis.add(new Exercici("Correr Normal 6 km", "6", 120, 1000, true, null, 0, 0, null, 1, "running", 200));
        cjtExercicis.add(new Exercici("Correr Normal 8 km", "8", 120, 1000, true, null, 0, 0, null, 1, "running", 225));
        cjtExercicis.add(new Exercici("Correr Normal 10 km", "10", 120, 1000, true, null, 0, 0, null, 1, "running", 250));

        cjtExercicis.add(new Exercici("Correr Difícil 11 km", "11", 120, 1000, true, null, 0, 0, null, 2, "running", 300));
        cjtExercicis.add(new Exercici("Correr Difícil 13 km", "13", 120, 1000, true, null, 0, 0, null, 2, "running", 325));
        cjtExercicis.add(new Exercici("Correr Difícil 15 km", "15", 120, 1000, true, null, 0, 0, null, 2, "running", 350));

        cjtExercicis.add(new Exercici("Bicicleta Fàcil 1 km", "1", 120, 1000, true, null, 0, 0, null, 0, "cycling", 100));
        cjtExercicis.add(new Exercici("Bicicleta Fàcil 3 km", "3", 120, 1000, true, null, 0, 0, null, 0, "cycling", 125));
        cjtExercicis.add(new Exercici("Bicicleta Fàcil 5 km", "5", 120, 1000, true, null, 0, 0, null, 0, "cycling", 150));

        cjtExercicis.add(new Exercici("Bicicleta Normal 6 km", "6", 120, 1000, true, null, 0, 0, null, 1, "cycling", 200));
        cjtExercicis.add(new Exercici("Bicicleta Normal 8 km", "8", 120, 1000, true, null, 0, 0, null, 1, "cycling", 225));
        cjtExercicis.add(new Exercici("Bicicleta Normal 10 km", "10", 120, 1000, true, null, 0, 0, null, 1, "cycling", 250));

        cjtExercicis.add(new Exercici("Bicicleta Difícil 11 km", "11", 120, 1000, true, null, 0, 0, null, 2, "cycling", 300));
        cjtExercicis.add(new Exercici("Bicicleta Difícil 13 km", "13", 120, 1000, true, null, 0, 0, null, 2, "cycling", 325));
        cjtExercicis.add(new Exercici("Bicicleta Difícil 15 km", "15", 120, 1000, true, null, 0, 0, null, 2, "cycling", 350));



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
