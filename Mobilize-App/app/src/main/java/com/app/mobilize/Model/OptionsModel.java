package com.app.mobilize.Model;

import com.app.mobilize.Presentador.Interface.OptionsInterface;
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
    public void doDelete(String username) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser().delete();
        db.collection("users").document(username).delete();
    }
}
