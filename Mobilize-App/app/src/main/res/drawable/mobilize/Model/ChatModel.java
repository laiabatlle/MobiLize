package com.app.mobilize.Model;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.Interface.ChatInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ChatModel implements ChatInterface.Model {

    private ChatInterface.TaskListener listener;
    private CollectionReference chat_ref;

    public ChatModel(ChatInterface.TaskListener taskListener) {
        this.listener = taskListener;
        this.chat_ref = FirebaseFirestore.getInstance().collection("Chat");
    }

    @Override
    public void doGetMessageList(String currentUser, String user) {
        chat_ref.document(currentUser).collection(user).orderBy("time").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Messages m = new Messages();
                        m.setDate(document.getData().get("date").toString());
                        m.setTime(document.getData().get("time").toString());
                        m.setFrom(document.getData().get("from").toString());
                        m.setMessage(document.getData().get("message").toString());
                        listener.addLista(m);
                    }
                    listener.onSuccess();
                }
            }
        });
    }

    @Override
    public void doSendMessage(final String currentUser, final String user, final String date, final String time, final String message_text) {

        final Map<String, Object> message = new HashMap<>();
            message.put("message", message_text);
            message.put("from", currentUser);
            message.put("time", time);
            message.put("date", date);

        chat_ref.document(currentUser).collection(user).add(message).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                chat_ref.document(user).collection(currentUser).add(message);
                listener.addLista(new Messages(date, time, currentUser, message_text));
            }
        });
    }
}
