package com.app.mobilize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class optionsActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        logout = findViewById(R.id.logOut);
        logout.setText("LogOut");
        logout.setTextSize(18.f);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.getCurrentUser().reload();
                mAuth.signOut();
                goToLogin();
            }
        });
    }

    public void goToLogin () {
        Intent intent = new Intent( this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}