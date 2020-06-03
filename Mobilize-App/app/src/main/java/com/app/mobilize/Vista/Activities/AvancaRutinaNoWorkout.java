package com.app.mobilize.Vista.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.mobilize.Model.ActivitatFinalitzada;
import com.app.mobilize.R;
import com.app.sqliteopenhelper.Exercici;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AvancaRutinaNoWorkout extends AppCompatActivity {

    ArrayList<Exercici> exercici;
    int pos;
    int puntstotals;
    double kcaltotals;
    String email;
    long time1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avanca_rutina_no_workout);
        time1 = SystemClock.elapsedRealtime();

        TextView tvnom = (TextView) findViewById(R.id.textView30);
        TextView tvkmh = (TextView) findViewById(R.id.textView31);
        TextView tvdurada = (TextView) findViewById(R.id.textView32);
        TextView tvkcal = (TextView) findViewById(R.id.textView33);
        TextView tvpendent = (TextView) findViewById(R.id.textView34);
        TextView tvpunts = (TextView) findViewById(R.id.textView35);

        exercici = getIntent().getParcelableArrayListExtra("exercici");
        pos = getIntent().getIntExtra("pos", 0);
        puntstotals = getIntent().getIntExtra("puntstotals",0);
        kcaltotals = getIntent().getDoubleExtra("kcaltotals", 0);


        tvnom.setText(exercici.get(pos).getNom());
        String km_h = exercici.get(pos).getKmh() + " " + getString(R.string.kmh);
        tvkmh.setText(km_h);


        String kcal = Double.toString(exercici.get(pos).getKcal()) + " " + getString(R.string.Kcal);
        String durada = Integer.toString(exercici.get(pos).getDuradamin()) + " " + getString(R.string.minuts);
        String punts = Integer.toString(exercici.get(pos).getPunts()) + " " + getString(R.string.punts);

        tvdurada.setText(durada);
        tvkcal.setText(kcal);

        puntstotals = exercici.get(pos).getPunts() + puntstotals;
        kcaltotals = exercici.get(pos).getKcal() + kcaltotals;

        if(exercici.get(pos).getPendent()) {
            tvpendent.setText(getString(R.string.inclou_pendent));
        }

        else tvpendent.setText(getString(R.string.sense_pendent));

        tvpunts.setText(punts);
    }




    public void Track(View view) {

        Intent intent = new Intent(this, TrackActivity.class);  //aqui haurem de passar cap a la pestanya process
        intent.putExtra("puntstotals", puntstotals);
        intent.putExtra("kcaltotals",kcaltotals);
        intent.putParcelableArrayListExtra("exercici", exercici);
        intent.putExtra("pos", pos);
        intent.putExtra("tipus", exercici.get(pos).getModalitat());
        intent.putExtra("rutina", true);
        intent.putExtra("initialDistance", Double.valueOf(exercici.get(pos).getKmh()));
        startActivityForResult(intent, 0);

    }

    public void Completed(View view){
        Calendar calendar = Calendar.getInstance();
        String data = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(calendar.get(Calendar.MONTH)) + "/" + String.valueOf(calendar.get(Calendar.YEAR));
        long tempsActivitat = time1 - SystemClock.elapsedRealtime(); // no agafa be el temps perque se'n va a una altre Activity

        email = SaveSharedPreference.getEmail(this);

        ActivitatFinalitzada activitatFinalitazada = new ActivitatFinalitzada(data, email, tempsActivitat, 0, 2, kcaltotals);
        getMapFirebase(activitatFinalitazada);

        FirebaseFirestore.getInstance().collection("Ranking").document(SaveSharedPreference.getEmail(this)).update("points", FieldValue.increment(puntstotals));

        finish();
    }

    private void getMapFirebase (ActivitatFinalitzada activitatFinalitzada) {
        Map<String, ArrayList<ActivitatFinalitzada>> mapAux = new HashMap<>();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("ActivitatsFinalitzades").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.getData() == null) {

                    } else if (documentSnapshot.getData().isEmpty()) {

                    } else {
                        Log.i("TASKFIREBASE", "SUCCES");
                        Map<String, Object> mapAux = documentSnapshot.getData();
                        putMapFirebase(activitatFinalitzada, mapAux.size(), mapAux);
                    }
                }
            }
        });
    }

    private void putMapFirebase (ActivitatFinalitzada activitatFinalitzada, int size, Map<String, Object> map ) {
        Map<String, String> mapAux2 = new HashMap<>();
        mapAux2.put("data", activitatFinalitzada.getData());
        mapAux2.put("email", activitatFinalitzada.getUsername());
        mapAux2.put("distancia", String.valueOf(activitatFinalitzada.getDistancia()));
        mapAux2.put("kcal", String.valueOf(activitatFinalitzada.getKcalCremades()));
        mapAux2.put("temps", String.valueOf(activitatFinalitzada.getTemps()));
        mapAux2.put("tipus", String.valueOf(activitatFinalitzada.getTipus()));
        if ( size == 0 ) {
            Map<String, Object> mapAux = new HashMap<>();
            mapAux.put(String.valueOf(size), mapAux2);

            FirebaseFirestore.getInstance().collection("ActivitatsFinalitzades").document(email).set(mapAux).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) Log.i("TASKFIREBASE", "Succesful");
                    else Log.i("TASKFIREBASE", "Not Succesful");
                }
            });
        }
        else {
            map.put(String.valueOf(size), mapAux2);
            FirebaseFirestore.getInstance().collection("ActivitatsFinalitzades").document(email).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) Log.i("TASKFIREBASE", "Succesful");
                    else Log.i("TASKFIREBASE", "Not Succesful");
                }
            });
        }
    }
}
