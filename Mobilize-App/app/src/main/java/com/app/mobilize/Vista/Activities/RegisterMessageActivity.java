package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.mobilize.R;

public class RegisterMessageActivity extends AppCompatActivity {

    TextView tv, tv2;
    Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_message_activity);
        setViews();
    }

    public void setViews() {
        String text = this.getIntent().getStringExtra("text");
        tv = findViewById(R.id.textConfirmationEmail);
        tv.setText(text);

        tv2 = findViewById(R.id.goToLogin);

        bLogin = findViewById(R.id.loginButton);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent( RegisterMessageActivity.this, LoginActivity.class);
                startActivity(sig);
                finish();
            }
        });
    }
}