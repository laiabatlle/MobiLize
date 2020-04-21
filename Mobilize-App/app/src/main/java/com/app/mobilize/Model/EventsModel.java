package com.app.mobilize.Model;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.Interface.EventsInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class EventsModel implements EventsInterface.Model {

    private EventsInterface.TaskListener listener;
    private CollectionReference event_ref;

    public EventsModel(EventsInterface.TaskListener taskListener ) {
        this.listener = taskListener;
        event_ref = FirebaseFirestore.getInstance().collection("Events");
    }

    @Override
    public void doGetEvents() {
        event_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Events e = new Events();
                        e.setImage(document.getData().get("image").toString());
                        e.setDescription(document.getData().get("description").toString());
                        e.setDateEvent(document.getData().get("dateEvent").toString());
                        e.setHourEvent(document.getData().get("hourEvent").toString());
                        e.setMax_part(document.getData().get("max_part").toString());
                        e.setMin_part(document.getData().get("min_part").toString());
                        e.setSportEvent(document.getData().get("sport").toString());
                        e.setInscripcionsList((List<String>) document.get("inscripcionsList"));
                        listener.addEventsList(e);
                    }
                    listener.onSuccess();
                }
            }
        });
    }
}
