package com.app.mobilize.Vista.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

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

public class AvancaRutina extends AppCompatActivity {

    VideoView videoActivity;
    ArrayList<Exercici> exercici;
    int pos;
    int puntstotals;
    double kcaltotals;

    String email;
    long time1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avanca_rutina);

        time1 = SystemClock.elapsedRealtime();

        TextView tvnom = (TextView) findViewById(R.id.textView25);
        TextView tvmusculs = (TextView) findViewById(R.id.textView26);
        TextView tvseries = (TextView) findViewById(R.id.textView27);
        TextView tvrepeticions = (TextView) findViewById(R.id.textView28);
        TextView tvpunts = (TextView) findViewById(R.id.textView29);

         exercici = getIntent().getParcelableArrayListExtra("exercici");
         pos = getIntent().getIntExtra("pos", 0);
         puntstotals = getIntent().getIntExtra("puntstotals",0);
         kcaltotals = getIntent().getDoubleExtra("kcaltotals", 0);

        tvnom.setText(exercici.get(pos).getNom());
        tvmusculs.setText(exercici.get(pos).getMusculs());
        String ser = Integer.toString(exercici.get(pos).getSeries()) + " " + getString(R.string.sèries);
        String rep = Integer.toString(exercici.get(pos).getRepeticions()) + " " + getString(R.string.repeticions);
        String punts = Integer.toString(exercici.get(pos).getPunts()) + " " + getString(R.string.punts);
        tvseries.setText(ser);
        tvrepeticions.setText(rep);
        tvpunts.setText(punts);
        puntstotals = exercici.get(pos).getPunts() + puntstotals;
        kcaltotals = exercici.get(pos).getKcal() + kcaltotals;


        videoActivity = findViewById(R.id.videoView2);
        videoActivity.setVideoPath("android.resource://" + getPackageName() + "/raw/" + exercici.get(pos).getTecnica());
        videoActivity.start();

        videoActivity.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }



    public void Continua(View view) {

        Log.i("AvançaRutinaContinua", String.valueOf(pos));

        pos = pos + 1;

        if (exercici.size() == pos) {

            Calendar calendar = Calendar.getInstance();
            String data = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(calendar.get(Calendar.MONTH)) + "/" + String.valueOf(calendar.get(Calendar.YEAR));
            long tempsActivitat = time1 - SystemClock.elapsedRealtime(); // no agafa be el temps perque se'n va a una altre Activity

            email = SaveSharedPreference.getEmail(this);
            Log.i("EmailRutina", email);

            ActivitatFinalitzada activitatFinalitazada = new ActivitatFinalitzada(data, email, tempsActivitat, 0, 2, kcaltotals);
            getMapFirebase(activitatFinalitazada);

            FirebaseFirestore.getInstance().collection("Ranking").document(SaveSharedPreference.getEmail(this)).update("points", FieldValue.increment(puntstotals));

            /*Intent intent = new Intent(this, NivellEntrenament.class);  //aqui haurem de passar cap a la pestanya process
            intent.putExtra("puntstotals", puntstotals);
            intent.putExtra("kcaltotals",kcaltotals);
            startActivityForResult(intent, 0);*/
            this.finish();;
        }

        else {
            if (exercici.get(pos).getKmh() == null) {
                Intent intent = new Intent(this, AvancaRutina.class);
                intent.putParcelableArrayListExtra("exercici", exercici);
                intent.putExtra("pos", pos);
                intent.putExtra("puntstotals", puntstotals);
                intent.putExtra("kcaltotals",kcaltotals);
                startActivityForResult(intent, 0);
                this.finish();

            } else {
                Intent intent = new Intent(this, AvancaRutinaNoWorkout.class);
                intent.putParcelableArrayListExtra("exercici", exercici);
                intent.putExtra("pos", pos);
                intent.putExtra("puntstotals", puntstotals);
                intent.putExtra("kcaltotals",kcaltotals);
                startActivityForResult(intent, 0);
                this.finish();
            }
        }
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
