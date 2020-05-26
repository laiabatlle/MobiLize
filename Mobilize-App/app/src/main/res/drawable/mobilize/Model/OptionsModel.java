package com.app.mobilize.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.Interface.OptionsInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OptionsModel implements OptionsInterface.Model {
    private OptionsInterface.TaskListener listener;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference st;


    public OptionsModel(OptionsInterface.TaskListener taskListener) {
        this.listener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        st = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void doLogout() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser().reload();
        mAuth.signOut();
    }

    @Override
    public void doDelete(String user) {
        Log.d("hola",user);
        db.collection("users").document(user).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("hola","success");
                mAuth = FirebaseAuth.getInstance();
                mAuth.getCurrentUser().delete();
                mAuth.signOut();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("hola","fail", e);
                    }
                });
    }

    @Override
    public void doPrivacity(String user, String privacity) {
        db.collection("users").document(user).update("privacity", privacity);
    }
}
