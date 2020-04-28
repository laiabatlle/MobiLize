package com.app.mobilize.Presentador.Interface;

import android.net.Uri;

import java.text.ParseException;

public interface CreateEventInterface {
    interface View {
        void disableInputs();
        void enableInputs();

        void handleCreateEvent() throws ParseException;
        void handleImage(Uri image);

        void onSuccesImageChange(Uri uriImage);
        void onSuccess(String message);
    }

    interface Presenter{
        void toCreateEvent(Uri imageUri, String title, String desciption, String dateEvent, String hourEvent, String sportEvent, String max_part, String min_part);

        void toImageChange(Uri image);
    }

    interface Model{
        void doCreateEvent(Uri imageUri, String title, String desciption, String dateEvent, String hourEvent, String sportEvent, String max_part, String min_part);
        void doImageChange(Uri uri);
    }

    interface TaskListener{
        void onSuccess(String message);

        void OnSuccesImageChange(Uri downloadUri);
    }
}
