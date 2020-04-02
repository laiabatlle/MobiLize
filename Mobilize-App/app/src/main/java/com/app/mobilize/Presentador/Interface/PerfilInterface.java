package com.app.mobilize.Presentador.Interface;

import android.net.Uri;

public interface PerfilInterface {
    interface View {
        void disableInputs();
        void enableInputs();

        void handleImage(Uri image);
        void handleOptions();
        void handleGuardarCambios();

        void onSuccess(String message);

        void onSuccesImageChange(Uri uriImage);
    }

    interface Presenter{
        void toGuardarCambios(String username, String dateNaixement, String gendre, String weight, String height, String image);
        void toImageChange(Uri uri);
    }

    interface Model{
        void doGuardarCambios(String username, String dateNaixement, String gendre, String weight, String height, String image);
        void doImageChange(Uri uri);
    }

    interface TaskListener{
        void onSuccess(String message);
        void onError(String error);

        void OnSuccesImageChange(Uri uriImage);
    }
}
