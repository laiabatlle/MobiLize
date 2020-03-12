package com.app.mobilize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        TextView tv = findViewById(R.id.textView2);
        tv.setText("PRIMERA PANTALLA DE L'APP");
        tv.setTextSize(18.f);
    }
}
