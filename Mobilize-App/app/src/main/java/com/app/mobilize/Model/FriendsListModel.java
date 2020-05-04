package com.app.mobilize.Model;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.FriendListPresenter;
import com.app.mobilize.Presentador.Interface.FriendsListInterface;
import com.app.mobilize.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FriendsListModel implements FriendsListInterface.Model {

    private FriendsListInterface.TaskListener listener;
    private CollectionReference user_ref;
    private CollectionReference req_ref;

    public FriendsListModel(FriendsListInterface.TaskListener taskListener) {
        this.listener = taskListener;
        user_ref = FirebaseFirestore.getInstance().collection("users");
        req_ref = FirebaseFirestore.getInstance().collection("FriendRequests");
    }

    @Override
    public void doGetFriendReq(String user) {
        req_ref.document(user).collection("request").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getData().get("request_type").equals("received")) {
                            user_ref.whereEqualTo("email", document.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Usuari u = new Usuari();
                                            u.setUsername(document.getData().get("username").toString());
                                            u.setEmail(document.getData().get("email").toString());
                                            u.setImage(document.getData().get("image").toString());
                                            listener.addListaReq(u);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
        listener.onSuccess("req");
    }

    @Override
    public void doGetFriendList(final String user) {
        user_ref.whereEqualTo("email", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> friendList = (List<String>) task.getResult().getDocuments().get(0).getData().get("friendsList");
                    if (friendList.size() == 0) listener.onError("¡Encuentra nuevos amigos!");
                    else {
                        for (String friend : friendList) {
                            user_ref.whereEqualTo("email", friend).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Usuari u = new Usuari();
                                            u.setUsername(document.getData().get("username").toString());
                                            u.setEmail(document.getData().get("email").toString());
                                            u.setImage(document.getData().get("image").toString());
                                            listener.addListaFriends(u);
                                        }
                                    }
                                }

                            });
                        }
                        listener.onSuccess("friends");
                    }
                }
            }
        });
    }
}
