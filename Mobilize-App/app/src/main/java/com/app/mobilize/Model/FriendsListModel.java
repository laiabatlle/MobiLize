package com.app.mobilize.Model;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.FriendListPresenter;
import com.app.mobilize.Presentador.Interface.FriendsListInterface;
import com.app.mobilize.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FriendsListModel implements FriendsListInterface.Model {

    private FriendsListInterface.TaskListener listener;
    private FirebaseFirestore ref;

    public FriendsListModel(FriendsListInterface.TaskListener taskListener) {
        this.listener = taskListener;
        ref = FirebaseFirestore.getInstance();
    }

    @Override
    public void doGetFriendList(final String username) {
        ref.collection("users").whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> friendList = (List<String>) task.getResult().getDocuments().get(0).getData().get("friendsList");
                    if (friendList.size() == 0) listener.onError("¡Encuentra nuevos amigos!");
                    else {
                        for (String friend : friendList) {
                            ref.collection("users").whereEqualTo("username", friend).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Usuari u = new Usuari();
                                            u.setUsername(document.getData().get("username").toString());
                                            u.setImage(document.getData().get("image").toString());
                                            Log.d("Modelfor1", document.getData().get("username").toString());
                                            listener.addLista(u);
                                        }
                                    }
                                }

                            });
                        }
                        Log.d("Modelfor2", friendList.get(0));
                        listener.onSuccess();
                    }
                }
            }
        });
    }
}
