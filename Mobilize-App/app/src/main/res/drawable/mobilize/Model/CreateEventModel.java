package com.app.mobilize.Model;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.Interface.CreateEventInterface;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class CreateEventModel implements CreateEventInterface.Model {

    private Events event;
    private CreateEventInterface.TaskListener listener;
    private CollectionReference event_ref;
    private StorageReference st;


    public CreateEventModel(CreateEventInterface.TaskListener taskListener) {
        this.listener = taskListener;
        event_ref = FirebaseFirestore.getInstance().collection("Events");
        st = FirebaseStorage.getInstance().getReference();
        event = new Events();
    }

    @Override
    public void doCreateEvent(Uri imageUri, final String title, String desciption, String dateEvent, String hourEvent, String sportEvent, String max_part, String creator, ArrayList<String> inscriptions, final int actionId) {
        event.setImage(imageUri.toString());
        event.setTitle(title);
        event.setDescription(desciption);
        event.setDateEvent(dateEvent);
        event.setHourEvent(hourEvent);
        event.setSportEvent(sportEvent);
        event.setMax_part(max_part);
        event.setCreator(creator);
        event.setInscripcionsList(inscriptions);
        event_ref.document(title).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("CreateEventFragment", "DocumentSnapshot successfully written!");
                if (actionId == 0) listener.onSuccess("SuccesEventCreate");
                else listener.onSuccess("SuccesEventModified");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("CreateEventFragment", "Error writing document", e);
            }
        });
    }

    @Override
    public void doImageChange(Uri uri) {
        final StorageReference filePath = st.child("eventsImages").child(uri.getLastPathSegment());
        UploadTask uploadTask = filePath.putFile(uri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    listener.OnSuccesImageChange(downloadUri);
                }
            }
        });
    }
}
