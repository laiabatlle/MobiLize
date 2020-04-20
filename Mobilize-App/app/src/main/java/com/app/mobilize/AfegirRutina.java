package com.app.mobilize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.sqliteopenhelper.AdminSQLiteOpenHelper;

public class AfegirRutina extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_rutina);

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

        ContentValues registro = new ContentValues();
        registro.put("nom", nom);
        registro.put("info", info);
        registro.put("nivell", dificultat);
        registro.put("modalitat", modalitat);

        BaseDeDatos.insert("Rutines", null, registro);


        BaseDeDatos.close();

        nomedx.setText("Nom");
        infoedx.setText("Info");

        Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();

    }
}
