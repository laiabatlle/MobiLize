package com.app.mobilize.Vista.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.app.mobilize.Presentador.Interface.OptionsInterface;
import com.app.mobilize.Presentador.OptionsPresenter;
import com.app.mobilize.R;
import com.google.firebase.auth.FirebaseAuth;

public class OptionsActivity extends AppCompatActivity implements OptionsInterface.View{

    private Button logout, deleteUser, idiom, info, alerts, change;
    private Switch privacity;
    private String user, privacy;
    private OptionsInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        presenter = new OptionsPresenter(this);

        user = SaveSharedPreference.getEmail(this);
        String privacyExtra = this.getIntent().getStringExtra("user_privacity");
        privacy = privacyExtra;

        deleteUser = findViewById(R.id.deleteUser);
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        privacity = (Switch) this.findViewById(R.id.privacitySwitch);
        if(privacy.equals("private")) privacity.setChecked(true);
        else privacity.setChecked(false);
        privacity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) handlePrivacity("private");
                else handlePrivacity("public");
            }
        });
        logout = findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.toLogout();
                goToLogin(true);
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

        change = findViewById(R.id.changePassword);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChangePassword();
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
                presenter.toDelete(user);
                goToLogin(false);
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public void goToLogin (boolean notDelete) {
        SaveSharedPreference.clearEmail(this);
        if(notDelete) FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent( this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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

    private void goToChangePassword() {
        Intent intent = new Intent( this, ChangePasswordActivity.class);
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
    public void handlePrivacity(String privacity) {
        presenter.toPrivacity(user, privacity);
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }
}
