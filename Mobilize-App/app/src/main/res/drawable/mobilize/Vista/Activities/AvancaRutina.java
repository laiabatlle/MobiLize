package com.app.mobilize.Vista.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.R;
import com.app.sqliteopenhelper.Exercici;

import java.util.ArrayList;

public class AvancaRutina extends AppCompatActivity {

    VideoView videoActivity;
    ArrayList<Exercici> exercici;
    int pos;
    int puntstotals;
    double kcaltotals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avanca_rutina);

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

        pos = pos + 1;

        if (exercici.size() == pos) {
            Intent intent = new Intent(this, NivellEntrenament.class);  //aqui haurem de passar cap a la pestanya process
            intent.putExtra("puntstotals", puntstotals);
            intent.putExtra("kcaltotals",kcaltotals);
            startActivityForResult(intent, 0);
        }

        else {
            if (exercici.get(pos).getKmh() == null) {
                Intent intent = new Intent(this, AvancaRutina.class);
                intent.putParcelableArrayListExtra("exercici", exercici);
                intent.putExtra("pos", pos);
                intent.putExtra("puntstotals", puntstotals);
                intent.putExtra("kcaltotals",kcaltotals);
                startActivityForResult(intent, 0);

            } else {
                Intent intent = new Intent(this, AvancaRutinaNoWorkout.class);
                intent.putParcelableArrayListExtra("exercici", exercici);
                intent.putExtra("pos", pos);
                intent.putExtra("puntstotals", puntstotals);
                intent.putExtra("kcaltotals",kcaltotals);
                startActivityForResult(intent, 0);
            }
        }


    }
}
