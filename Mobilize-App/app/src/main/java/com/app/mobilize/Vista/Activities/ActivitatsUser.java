package com.app.mobilize.Vista.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.app.mobilize.Model.ActivitatFinalitzada;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.mobilize.R;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivitatsUser extends AppCompatActivity {

    String email;
    ArrayList<ActivitatFinalitzada> activitats;
    ListView listView;
    Map<String, Object> mapActivitats;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitats_user);

        email = this.getIntent().getStringExtra("email");
        activitats = new ArrayList<>();
        getActivitats();

        listView = findViewById(R.id.listViewActivitats);
    }

    private void putActivitatsList () {
        ActivitatListAdapter adapter = new ActivitatListAdapter(this, R.layout.fila_activitat, activitats);
        listView.setAdapter(adapter);
    }

    public void getActivitats(){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("ActivitatsFinalitzades").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if ( task.isSuccessful() ) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if ( documentSnapshot.getData() == null ) {
                        Log.i("TASKFIREBASE1", "NULL");
                    }
                    else if ( documentSnapshot.getData().isEmpty() ) {
                        Log.i("TASKFIREBASE1", "EMPTY");
                    }
                    else {
                        Log.i("TASKFIREBASE1", "SUCCES");
                        Object obj = documentSnapshot.getData();
                       // Toast.makeText(getApplicationContext(), obj.toString(), Toast.LENGTH_LONG).show();
                        Log.i("TASKFIREBASE", "Size " +  String.valueOf(activitats.size()));
                        Map <String, Map<String, String>> mapAux = (Map<String, Map<String, String>>) obj;
                        putActivitats(mapAux);

                    }
                }
            }
        });
    }

    private void putActivitats( Map <String, Map<String, String>> map ) {
        for ( int i=0; i<map.size(); i++ ) {
            String data = map.get(String.valueOf(i)).get("data");
            String kcal = map.get(String.valueOf(i)).get("kcal");
            String email = map.get(String.valueOf(i)).get("email");
            String distancia = map.get(String.valueOf(i)).get("distancia");
            String temps = map.get(String.valueOf(i)).get("temps");
            String tipus = map.get(String.valueOf(i)).get("tipus");

            ActivitatFinalitzada activitatFinalitzada = new ActivitatFinalitzada( data, email, Long.parseLong(temps), Double.parseDouble(distancia), Integer.parseInt(tipus), Double.parseDouble(kcal));
            activitats.add(activitatFinalitzada);
        }

        putActivitatsList();
    }
}