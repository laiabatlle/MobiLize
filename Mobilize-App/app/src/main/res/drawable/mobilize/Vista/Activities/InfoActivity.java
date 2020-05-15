package com.app.mobilize.Vista.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.R;

public class InfoActivity extends AppCompatActivity {

    private Button contact, resp, privacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        contact = findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToContact();
            }
        });

        resp = findViewById(R.id.resp);
        resp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToResp();
            }
        });

        privacity = findViewById(R.id.privacity);
        privacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPrivacity();
            }
        });
    }

    private void goToContact() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:mobilizeapp.fib@gmail.com?subject=" + getResources().getString(R.string.version));
        intent.setData(data);
        startActivity(intent);
    }

    private void goToResp() {
        showAlertDialog(R.string.respMessage, R.string.resp);
    }

    private void goToPrivacity() {
        showAlertDialog(R.string.privacityMessage, R.string.privacity);
    }

    public void showAlertDialog(int message, int title){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(message);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton(R.string.accept,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}
