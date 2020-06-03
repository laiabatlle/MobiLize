package com.app.mobilize.Vista.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Interface.LoginInterface;
import com.app.mobilize.Presentador.LoginPresenter;
import com.app.mobilize.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginInterface.View, View.OnClickListener{

    private EditText email, password;

    private Button login,register;

    private ProgressDialog progressDialog;

    private LoginInterface.Presenter presenter;

    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setViews();
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = mAuth.getCurrentUser();

                            final List<String> list = new ArrayList<>();
                            final boolean[] newUser = {true};
                            db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(document.getId().equals(user.getEmail())) newUser[0] = false;
                                        }
                                        if(newUser[0]){
                                            Usuari usuari = new Usuari(acct.getDisplayName(), "ojsadf'39iwj3erfd'e0w9ijf90dsij'fodj'aeoa'39ifj'9dsj'3f'", user.getEmail());
                                            db.collection("users").document(user.getEmail()).set(usuari).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    SaveSharedPreference.setEmail(LoginActivity.this, user.getEmail());
                                                    Map<String, Object> rank = new HashMap<>();
                                                    rank.put("user", user.getDisplayName());
                                                    rank.put("points", 0);
                                                    db.collection("Ranking").document(user.getEmail()).set(rank);
                                                    Intent sig = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(sig);
                                                    finish();
                                                }
                                            });
                                        }
                                        else{
                                            SaveSharedPreference.setEmail(LoginActivity.this, user.getEmail());
                                            Intent sig = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(sig);
                                            finish();
                                        }
                                    }
                                }
                            });
                        }

                        // ...
                    }
                });
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
        SaveSharedPreference.setEmail(this, email.getText().toString());
        Intent sig = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(sig);
        finish();
    }

    public void goRegister() {
        Intent sig = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(sig);
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
