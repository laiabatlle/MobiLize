package com.app.mobilize.Vista.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.mobilize.Presentador.Interface.LoginInterface;
import com.app.mobilize.Presentador.LoginPresenter;
import com.app.mobilize.R;

public class LoginActivity extends AppCompatActivity implements LoginInterface.View, View.OnClickListener {

    private EditText email, password;

    private Button login,register;

    private ProgressDialog progressDialog;

    private LoginInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setViews();
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    public void setViews() {
        presenter = new LoginPresenter(this);
        email = findViewById(R.id.etUser);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);
        progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.auth));
        progressDialog.setCancelable(false);
    }

    private void setInputs(boolean enable){
        email.setEnabled(enable);
        password.setEnabled(enable);
        login.setEnabled(enable);
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

    @Override
    public void handleLogin() {
        if(!isValidEmail()){
            Toast.makeText(this, getResources().getString(R.string.introduceValidEmail), Toast.LENGTH_SHORT).show();
            email.setError(getResources().getString(R.string.incorrectEmail));
        }
        if (!isValidPassword()){
            password.setError(getResources().getString(R.string.incorrectPassword));
        }else{
            presenter.toLogin(email.getText().toString().trim(), password.getText().toString().trim());
        }
    }

    @Override
    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
    }

    @Override
    public boolean isValidPassword() {
        return ((!TextUtils.isEmpty(password.getText().toString())) && (password.getText().toString().length() > 6 || password.getText().toString().length() < 10));
    }

    @Override
    public void onError(String error) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        if(error.equals("confirmEmail")) builder.setMessage(getResources().getString(R.string.confirmEmail)).setTitle("Error").setCancelable(true);
        else builder.setMessage(getResources().getString(R.string.errorEmail)).setTitle("Error").setCancelable(true);
        AlertDialog alert = builder.create();

        alert.setTitle("Error");
        alert.show();
    }

    @Override
    public void goMainMenu() {
        Intent sig = new Intent(LoginActivity.this, MainActivity.class);
        sig.putExtra("email", email.getText().toString());
        startActivity(sig);
    }

    public void goRegister() {
        Intent sig = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(sig);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                handleLogin();
                break;
            case R.id.registerButton:
                goRegister();
                break;
            default:
                break;
        }
    }
}
