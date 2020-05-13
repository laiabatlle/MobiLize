package com.app.mobilize.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.Interface.BuscadorUserInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class PopUpEventListInscriptionsModel implements BuscadorUserInterface.Model{

    private BuscadorUserInterface.TaskListener listener;
    private CollectionReference event_ref;
    private FirebaseFirestore ref;
    private String event;
    private ArrayList<String> inscriptions;

    public PopUpEventListInscriptionsModel(BuscadorUserInterface.TaskListener taskListener, String event) {
        this.listener = taskListener;
        this.event = event;
        event_ref = FirebaseFirestore.getInstance().collection("Events");
        ref = FirebaseFirestore.getInstance();
    }

    @Override
    public void doGetUserList(final Usuari currentUser) {
        inscriptions = new ArrayList<>();
        event_ref.whereEqualTo("title", event).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    inscriptions = (ArrayList<String>) task.getResult().getDocuments().get(0).getData().get("inscripcionsList");
                    for (String user : inscriptions) {
                        Log.d("user:: ", user);
                        ref.collection("users").whereEqualTo("email", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                        if (!document.getData().get("username").toString().equals(currentUser.getUsername())) {
                                            Usuari u = new Usuari();
                                            u.setUsername(document.getData().get("username").toString());
                                            u.setEmail(document.getData().get("email").toString());
                                            u.setImage(document.getData().get("image").toString());
                                            listener.addLista(u);
                                        }
                                    }
                                }
                            }
                        });
                    }
                    listener.onSuccess();
                }
            }
        });
    }
}