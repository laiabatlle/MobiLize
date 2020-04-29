package com.app.mobilize.Model;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.Interface.RegisterInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class RegisterModel implements RegisterInterface.Model {

    private RegisterInterface.TaskListener listener;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public RegisterModel(RegisterInterface.TaskListener taskListener) {
        this.listener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void doRegister(final String username, final String email, final String password) {
        db.collection("users").whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(!task.getResult().isEmpty()) {
                        listener.onError("Este nombre de usuario ya existe");
                    }
                    else{
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Getting the id of the current user
                                    Usuari user = new Usuari(username, password, email);

                                    db.collection("users").document(email).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            listener.onSuccess();
                                            ConfirmationEmail();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            listener.onError("Error en el registro. Inténtelo de nuevo más tarde");
                                        }
                                    });
                                } else {
                                    // If the user aldready exists show the alert
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        listener.onError("Este correo ya tiene una cuenta asociada, porfavor introduce otro correo electrónico o acceda a su cuenta");
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        listener.onError("Contraseña incorrecta. Por favor, introduce una contraseña válida. Debe contener entre 6 y 10 carácteres alfanuméricos");
                                    } catch (Exception e) {
                                        listener.onError(task.getException().getMessage());
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void ConfirmationEmail() {
        if (!mAuth.getCurrentUser().isEmailVerified()) {
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if ( task.isSuccessful() );
                    else listener.onError("No se pudo enviar el correo de confirmación, inténtelo de nuevo más tarde.");
                }
            });
        }
    }

}
