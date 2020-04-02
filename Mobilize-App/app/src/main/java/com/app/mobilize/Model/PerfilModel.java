package com.app.mobilize.Model;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.Interface.PerfilInterface;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PerfilModel implements PerfilInterface.Model {

    private PerfilInterface.TaskListener listener;
    private FirebaseFirestore db;
    private StorageReference st;


    public PerfilModel(PerfilInterface.TaskListener taskListener) {
        this.listener = taskListener;
        db = FirebaseFirestore.getInstance();
        st = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void doGuardarCambios(String username, String dateNaixement, String gendre, String weight, String height, String image) {
        db.collection("users").document(username).update("weight", weight);
        db.collection("users").document(username).update("height", height);
        db.collection("users").document(username).update("gender", gendre);
        db.collection("users").document(username).update("dateNaixement", dateNaixement);
        db.collection("users").document(username).update("image", image);
        listener.onSuccess("Sus datos se han actualizado correctamente.");
    }

    @Override
    public void doImageChange(Uri uri) {
        final StorageReference filePath = st.child("profileImages").child(uri.getLastPathSegment());
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
