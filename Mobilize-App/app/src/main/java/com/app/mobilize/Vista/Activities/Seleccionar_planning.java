package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.app.mobilize.R;

public class Seleccionar_planning extends AppCompatActivity {

    TextView tv1;
    TextView tv2;
    TextView tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_planning);

        tv1 = findViewById(R.id.textView36);
        tv2 = findViewById(R.id.textView37);
        tv3 = findViewById(R.id.textView38);

        int a = getIntent().getIntExtra("nivell", 0);
        String s = Integer.toString(a);

        tv1.setText(getIntent().getStringExtra("modalitat"));
        tv2.setText(getIntent().getStringExtra("duracio"));
        tv3.setText(s);

    }
}
