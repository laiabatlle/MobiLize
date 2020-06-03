package com.app.mobilize.Model;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.Interface.PerfilInterface;
import com.app.mobilize.Vista.Activities.SaveSharedPreference;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PerfilModel implements PerfilInterface.Model {

    private PerfilInterface.TaskListener listener;
    private FirebaseAuth mAuth;
    private CollectionReference user_ref;
    private FirebaseFirestore db;
    private StorageReference st;
    private boolean request;


    public PerfilModel(PerfilInterface.TaskListener taskListener) {
        this.listener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        user_ref = FirebaseFirestore.getInstance().collection("users");
        db = FirebaseFirestore.getInstance();
        st = FirebaseStorage.getInstance().getReference();
        request = false;
    }

    @Override
    public void haveAnyFriendReq(String username) {
        db.collection("FriendRequests").document(username).collection("request").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(!request) {
                            if (document.getData().get("request_type").toString().equals("received")) {
                                Log.d("Model", document.getData().get("request_type").toString());
                                listener.setReq(true);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void doGuardarCambios(String username, String email, String dateNaixement, String gendre, String weight, String height, String image/*, String privacity*/) {
        FirebaseFirestore.getInstance().collection("Ranking").document(email).update("user", username);
        user_ref.document(email).update("weight", weight);
        user_ref.document(email).update("username", username);
        user_ref.document(email).update("height", height);
        user_ref.document(email).update("gender", gendre);
        user_ref.document(email).update("dateNaixement", dateNaixement);
        user_ref.document(email).update("image", image);
        listener.onSuccess("1");
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
