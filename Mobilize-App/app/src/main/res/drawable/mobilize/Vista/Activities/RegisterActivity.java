package com.app.mobilize.Vista.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.Presentador.Interface.RegisterInterface;
import com.app.mobilize.Presentador.RegisterPresenter;
import com.app.mobilize.R;

public class RegisterActivity extends AppCompatActivity implements RegisterInterface.View, View.OnClickListener{

    EditText username, email, password;
    Button register;
    private ProgressDialog progressDialog;

    private String e;

    private RegisterInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        setViews();
        register.setOnClickListener(this);
    }

    public void setViews() {
        presenter = new RegisterPresenter(this);
        username = findViewById(R.id.usernameRegister);
        password = findViewById(R.id.passwordRegister);
        email = findViewById(R.id.emailRegister);
        register = findViewById(R.id.buttonRegister);
        progressDialog = new ProgressDialog(RegisterActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.auth));
        progressDialog.setCancelable(false);
    }

    private void setInputs(boolean enable){
        username.setEnabled(enable);
        email.setEnabled(enable);
        password.setEnabled(enable);
        register.setEnabled(enable);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void showPrgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }


    public void handleRegister () {
        // Create the user with the email and password introduced
        if(!isValidUsername()){
            username.setError(getResources().getString(R.string.incorrectUsername));
        }
        if(!isValidEmail()){
            email.setError(getResources().getString(R.string.incorrectEmail));
        }
        if(!isValidPassword()){
            password.setError(getResources().getString(R.string.incorrectPassword));
        }
        else {
            presenter.toRegister(username.getText().toString().trim(), email.getText().toString().trim(), password.getText().toString().trim(), false);
        }
    }

    @Override
    public boolean isValidUsername() {
        return !TextUtils.isEmpty(username.getText().toString());
    }

    @Override
    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
    }

    @Override
    public boolean isValidPassword() {
        return ((!TextUtils.isEmpty(password.getText().toString())) && (password.getText().toString().length() > 6));
    }

    @Override
    public void onError(String error) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setMessage(error).setTitle("Error").setCancelable(true);

        AlertDialog alert = builder.create();

        alert.setTitle("Error");
        alert.show();
    }

    @Override
    public void goConfirmation() {
        Intent sig = new Intent(RegisterActivity.this, RegisterMessageActivity.class);
        String confirm = getResources().getString(R.string.confirmation) + e;
        sig.putExtra("text", confirm);
        startActivity(sig);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegister:
                e = email.getText().toString();
                handleRegister();
                break;
            default:
                break;
        }
    }
}