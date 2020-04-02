package com.app.mobilize.Model;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.Interface.BuscadorUserInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class BuscadorUserModel implements BuscadorUserInterface.Model {

    private BuscadorUserInterface.TaskListener listener;
    private FirebaseFirestore ref;


    public BuscadorUserModel(BuscadorUserInterface.TaskListener taskListener) {
        this.listener = taskListener;
        ref = FirebaseFirestore.getInstance();
    }

    @Override
    public void doGetUserList() {
        ref.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Usuari u = new Usuari();
                        u.setUsername(document.getData().get("username").toString());
                        u.setImage(document.getData().get("image").toString());
                        listener.addLista(u);
                    }
                    listener.onSuccess();
                } else {
                    listener.onError("No se ha encontrado ningún usuario con ese nombre.");
                }
            }
        });
    }
}
