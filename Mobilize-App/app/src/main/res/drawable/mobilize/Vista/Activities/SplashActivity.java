package com.app.mobilize.Vista.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.R;

public class SplashActivity extends AppCompatActivity {

    private final int DURATION_SPLASH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);


        if(SaveSharedPreference.getEmail(this).length() != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent sig = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(sig);
                    finish();
                }

                ;
            }, DURATION_SPLASH);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                ;
            }, DURATION_SPLASH);
        }
    }
}
