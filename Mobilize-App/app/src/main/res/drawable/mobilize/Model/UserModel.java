package com.app.mobilize.Model;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.Interface.UserInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserModel implements UserInterface.Model {

    private UserInterface.TaskListener listener;
    private CollectionReference user_ref;
    private CollectionReference req_ref;

    public UserModel(UserInterface.TaskListener taskListener) {
        this.listener = taskListener;
        user_ref = FirebaseFirestore.getInstance().collection("users");
        req_ref = FirebaseFirestore.getInstance().collection("FriendRequests");
    }

    @Override
    public void doAcceptReq(final String currentUser, final String userperfil, String CURRENT_STATE) {
        req_ref.document(currentUser).collection("request").document(userperfil).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    req_ref.document(userperfil).collection("request").document(currentUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                listener.onSuccess("friends", userperfil, "newFriendsMes");
                            }
                        }
                    });
                }
            }
        });
        user_ref.document(currentUser).update("friendsList", FieldValue.arrayUnion(userperfil));
        user_ref.document(userperfil).update("friendsList", FieldValue.arrayUnion(currentUser));
    }

    @Override
    public void doSendReq(final String currentUser, final String userperfil, String CURRENT_STATE) {
        Map<String, Object> request = new HashMap<>();
        request.put("user", userperfil);
        request.put("request_type", "sent");
        req_ref.document(currentUser).collection("request").document(userperfil).set(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Map<String, Object> request = new HashMap<>();
                    request.put("user", currentUser);
                    request.put("request_type", "received");
                    req_ref.document(userperfil).collection("request").document(currentUser).set(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                listener.onSuccess("request_sent", "", "SentFriendReqMes");
                            }

                        }
                    });
                }
            }
        });
    }

    @Override
    public void doCancelReq(final String currentUser, final String userperfil, String CURRENT_STATE) {
        req_ref.document(currentUser).collection("request").document(userperfil).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    req_ref.document(userperfil).collection("request").document(currentUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                listener.onSuccess("not_friends", "", "CancelFriendReqMes");
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void doUnfriend(String currentUser, String userperfil, String CURRENT_STATE) {
        user_ref.document(currentUser).update("friendsList", FieldValue.arrayRemove(userperfil));
        user_ref.document(userperfil).update("friendsList", FieldValue.arrayRemove(currentUser));
        listener.onSuccess("not_friends", userperfil, userperfil + "unfriendsMes");
    }

    @Override
    public void MaintanceofButtons(String currentUser, final String userperfil, String current_state) {
        req_ref.document(currentUser).collection("request").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.getData().get("user").toString().equals(userperfil)) {
                            String request_type = document.getData().get("request_type").toString();

                            if (request_type.equals("sent")) {
                                listener.MaintanceofButtons("request_sent");
                            }
                            else if (request_type.equals("received")) {
                                listener.MaintanceofButtons("request_received");
                            }
                        }
                    }
                }
            }
        });

        user_ref.whereEqualTo("email", currentUser).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> friendList = (List<String>) task.getResult().getDocuments().get(0).getData().get("friendsList");
                    if (friendList.contains(userperfil)) {
                        listener.MaintanceofButtons("friends");
                    }
                }
            }
        });
    }
}
