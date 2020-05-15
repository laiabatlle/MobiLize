package com.app.mobilize.Vista.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePasswordActivity extends AppCompatActivity {

    private Button change;
    EditText password;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        setViews();
    }

    public void setViews() {
        db = FirebaseFirestore.getInstance();
        password = findViewById(R.id.changePasswordText);
        change = findViewById(R.id.changeButton);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChange();
            }
        });
    }

    private void goToChange() {
        if(!isValidPassword()){
            password.setError(getResources().getString(R.string.incorrectPassword));
        }
        else {
            db.collection("users").document(SaveSharedPreference.getEmail(this)).update("password", password.getText().toString());
            Toast.makeText(this, getResources().getString(R.string.passwordChanged), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public boolean isValidPassword() {
        return ((!TextUtils.isEmpty(password.getText().toString())) && (password.getText().toString().length() > 6));
    }
}
