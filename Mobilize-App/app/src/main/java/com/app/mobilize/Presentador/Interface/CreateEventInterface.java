package com.app.mobilize.Presentador.Interface;

import android.net.Uri;

import com.app.mobilize.Model.Events;

public interface CreateEventInterface {
    interface View {
        void disableInputs();
        void enableInputs();

        void handleCreateEvent();
        void handleImage(Uri image);

        void onSuccesImageChange(Uri uriImage);
        void onSuccess(String message);
    }

    interface Presenter{
        void toCreateEvent(Uri imageUri, String s, String toString, String string, String s1);

        void toImageChange(Uri image);
    }

    interface Model{
        void doCreateEvent(Uri imageUri, String desciption, String dateEvent, String max_part, String min_part);
        void doImageChange(Uri uri);
    }

    interface TaskListener{
        void onSuccess(String message);
        void onError(String error);

        void OnSuccesImageChange(Uri downloadUri);
    }
}
