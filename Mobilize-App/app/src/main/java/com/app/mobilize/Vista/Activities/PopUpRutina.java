package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.mobilize.R;

import com.app.sqliteopenhelper.AdminSQLiteOpenHelper;
import com.app.sqliteopenhelper.Exercici;
import com.app.sqliteopenhelper.Rutina;

import java.io.Serializable;
import java.util.ArrayList;

public class PopUpRutina extends AppCompatActivity implements AdapterDatos.OnNoteListener {

    ArrayList<Exercici> exe;
    RecyclerView recycler;
    VideoView videoActivity;

    public static ArrayList<Exercici> eaux = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_rutina);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

       /*videoActivity = findViewById(R.id.videoActivity);
        Log.d("title", getResources().getResourceName(R.raw.abdominals));
        videoActivity.setVideoPath("android.resource://" + getPackageName() + "/raw/abdominals");
        videoActivity.start();

        videoActivity.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        }); */

        recycler = (RecyclerView) findViewById(R.id.recyclerView3);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Rutina item = (Rutina) getIntent().getSerializableExtra("rutina");
        EditText ed1 = (EditText) findViewById(R.id.editText6);
        EditText ed2 = (EditText) findViewById(R.id.editText7);
        ed1.setText(item.getNom());
        ed2.setText(item.getInfo());

        exe = new ArrayList<>();
        exe = stringToArray(item.getExercicis(), item.getNivell(), item.getModalitat());
        eaux.clear();

        AdapterDatos adapter = new AdapterDatos(exe, this, "no");
        recycler.setAdapter(adapter);



    }

    private ArrayList<Exercici> stringToArray(String exercicis, int dificultat, String modalitat) { // he de buscar l'exercici que tingui aquells parametres, i crear un nou exercici i afegirlo a e.
        ArrayList<Exercici> e = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDades = admin.getWritableDatabase();


        String[] args;
        Cursor fila;

        String s = "";
        for(int i = 0; i < exercicis.length(); ++i) {
            char c = exercicis.charAt(i);
            if(c == ',' ) {

                args = new String[] {s};
                fila = BaseDeDades.rawQuery("select kmh, durada_min, kcal, pendent, musculs, repeticions, series, tecnica, punts from Exercicis where nom =?", args);

                while (fila.moveToNext()) {
                    String kmh = fila.getString(0);
                    int durada_min = fila.getInt(1);
                    double kcal = fila.getDouble(2);
                    boolean pendent = false;
                    String musculs = fila.getString(4);
                    int repeticions = fila.getInt(5);
                    int series = fila.getInt(6);
                    String tecnica = fila.getString(7);
                    int punts = fila.getInt(8);
                    Exercici ex = new Exercici(s, kmh, durada_min, kcal, pendent, musculs, repeticions, series, tecnica, dificultat, modalitat, punts);
                    e.add(ex);
                }



                s = "";
            }

            else {
                s = s + c;
            }
        }
        BaseDeDades.close();
        return e;
    }


    public void Elimina(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Rutina item = (Rutina) getIntent().getSerializableExtra("rutina");

        String[] args = new String[] {item.getNom()};
        BaseDeDatos.delete("Rutines", "nom=?", args);


        BaseDeDatos.close();



        Intent intent = new Intent(view.getContext(),  NivellEntrenament.class);
        startActivityForResult(intent, 0);
        this.finish();


        Toast.makeText(this, R.string.EsborraRutinaCorrecte, Toast.LENGTH_SHORT).show();

    }

    public void Start(View view) {
        if(exe.get(0).getKmh() == null) {
            Intent intent = new Intent(this, AvancaRutina.class);
            intent.putParcelableArrayListExtra("exercici", exe);
            intent.putExtra("pos", 0);
            intent.putExtra("puntstotals", 0);
            intent.putExtra("kcaltotals",0);
            startActivityForResult(intent, 0);

        }

        else {
            Intent intent = new Intent(this, AvancaRutinaNoWorkout.class);
            intent.putParcelableArrayListExtra("exercici", exe);
            intent.putExtra("pos", 0);
            intent.putExtra("puntstotals", 0);
            intent.putExtra("kcaltotals",0);
            startActivityForResult(intent, 0);
        }


    }


    public void Modifica(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        EditText ed1 = (EditText) findViewById(R.id.editText6);
        EditText ed2 = (EditText) findViewById(R.id.editText7);

        String s = transformaArray(eaux);

        ContentValues registro = new ContentValues();
        registro.put("nom", String.valueOf(ed1.getText()));
        registro.put("info", String.valueOf(ed2.getText()));
        registro.put("exercicis", s);

        Rutina item = (Rutina) getIntent().getSerializableExtra("rutina");

        String[] args = new String[] {item.getNom()};
        BaseDeDatos.update("Rutines", registro, "nom=?", args);


        BaseDeDatos.close();

        Intent intent = new Intent(view.getContext(), NivellEntrenament.class);
        startActivityForResult(intent, 0);
        this.finish();





        Toast.makeText(this, R.string.ModificaRutinaCorrecte, Toast.LENGTH_SHORT).show();

    }

    private String transformaArray(ArrayList<Exercici> eaux) {
        String s = "";
        for (int i = 0; i < eaux.size(); ++i) {
            s = s + eaux.get(i).getNom() + ",";
        }
        return s;
    }

    public static void unsetExercici(Exercici e) {

        for (int i = 0; i < eaux.size(); ++i) {
            if(eaux.get(i) == e) eaux.remove(i);
        }
    }

    public static void setExercici(Exercici e) {

        eaux.add(e);
    }

    @Override
    public void onNoteClick(int position) {

        if(exe.get(position).getKmh() == null) {
            Intent intent = new Intent(this, VeureExercici.class);
            intent.putExtra("exercici", exe.get(position));
            startActivityForResult(intent, 0);
            this.finish();

        }

        else {
            Intent intent = new Intent(this, VeureExerciciNoWorkout.class);
            intent.putExtra("exercici", exe.get(position));
            startActivityForResult(intent, 0);
            this.finish();
        }



    }
}
