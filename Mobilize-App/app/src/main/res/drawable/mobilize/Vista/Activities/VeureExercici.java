package com.app.mobilize.Vista.Activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.R;
import com.app.sqliteopenhelper.Exercici;

public class VeureExercici extends AppCompatActivity {

    VideoView videoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veure_exercici);

        TextView tvnom = (TextView) findViewById(R.id.textView12);
        TextView tvmusculs = (TextView) findViewById(R.id.textView15);
        TextView tvseries = (TextView) findViewById(R.id.textView16);
        TextView tvrepeticions = (TextView) findViewById(R.id.textView17);
        TextView tvpunts = (TextView) findViewById(R.id.textView24);

        Exercici exercici = (Exercici) getIntent().getParcelableExtra("exercici");

        tvnom.setText(exercici.getNom());
        tvmusculs.setText(exercici.getMusculs());
        String ser = Integer.toString(exercici.getSeries()) + " " + getString(R.string.sèries);
        String rep = Integer.toString(exercici.getRepeticions()) + " " + getString(R.string.repeticions);
        String punts = Integer.toString(exercici.getPunts()) + " " + getString(R.string.punts);
        tvseries.setText(ser);
        tvrepeticions.setText(rep);
        tvpunts.setText(punts);



       videoActivity = findViewById(R.id.videoView);
        videoActivity.setVideoPath("android.resource://" + getPackageName() + "/raw/" + exercici.getTecnica());
        videoActivity.start();

        videoActivity.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }
}
