package com.app.mobilize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiAutomation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class optionsActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button logout, deleteUser;
    String username;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        username = this.getIntent().getStringExtra("username");

        deleteUser = findViewById(R.id.deleteUser);
        deleteUser.setText("Delete account");
        deleteUser.setTextSize(18.f);
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   showAlertDialog();
            }
        });

        sharedPreferences = this.getSharedPreferences("myShared", Context.MODE_PRIVATE);

        logout = findViewById(R.id.logOut);
        logout.setText("LogOut");
        logout.setTextSize(18.f);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.getCurrentUser().reload();
                mAuth.signOut();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("email");
                editor.commit();

                goToLogin();
            }
        });
    }

    public void showAlertDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(true);
        alertBuilder.setMessage("Esta seguro de que quiere eliminar su cuenta? Se perderán todos sus datos.");
        alertBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.getCurrentUser().delete();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(username).delete();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", null);
                editor.apply();

                goToLogin();
            }
        });
        alertBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public void goToLogin () {
        Intent intent = new Intent( this, LoginActivity.class);
        startActivity(intent);
        this.finish();
        finishAffinity();
    }
}