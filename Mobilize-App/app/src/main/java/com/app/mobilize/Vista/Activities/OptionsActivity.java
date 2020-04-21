package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.mobilize.Presentador.Interface.OptionsInterface;
import com.app.mobilize.Presentador.OptionsPresenter;
import com.app.mobilize.R;

public class OptionsActivity extends AppCompatActivity implements OptionsInterface.View{

    private Button logout, deleteUser, idiom, info, alerts;
    private String username;
    private OptionsInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        presenter = new OptionsPresenter(this);

        username = this.getIntent().getStringExtra("username");

        deleteUser = findViewById(R.id.deleteUser);
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        logout = findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.toLogout();
                goToLogin();
            }
        });

        idiom = findViewById(R.id.idiom);
        idiom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToIdiom();
            }
        });

        info = findViewById(R.id.information);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInfo();
            }
        });

        alerts = findViewById(R.id.alerts);
        alerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAlerts();
            }
        });
    }

    public void showAlertDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(true);
        alertBuilder.setMessage(R.string.confirmationDelete);
        alertBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBuilder.setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.toDelete(username);
                goToLogin();
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public void goToLogin () {
        SaveSharedPreference.clearEmail(this);
        Intent intent = new Intent( this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
        finishAffinity();
    }

    public void goToIdiom () {
        Intent intent = new Intent( this, IdiomActivity.class);
        startActivity(intent);
    }

    public void goToInfo () {
        Intent intent = new Intent( this, InfoActivity.class);
        startActivity(intent);
    }

    public void goToAlerts () {
        Intent intent = new Intent( this, AlertsActivity.class);
        startActivity(intent);
    }

    private void setInputs(boolean enable){
        logout.setEnabled(enable);
        deleteUser.setEnabled(enable);
        idiom.setEnabled(enable);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }
}
