package com.app.mobilize.Presentador.Interface;

import android.net.Uri;

public interface PerfilInterface {
    interface View {
        void disableInputs();
        void enableInputs();

        void handleImage(Uri image);
        void handleOptions();
        void handleFriendList();
        void handleGuardarCambios();

        void onSuccess(String message);

        void onSuccesImageChange(Uri uriImage);
        void setReq(boolean b);
    }

    interface Presenter{
        void toGuardarCambios(String username, String dateNaixement, String gendre, String weight, String height, String image/*, String privacity*/);
        void toImageChange(Uri uri);

        void haveAnyFriendReq(String username);
    }

    interface Model{
        void haveAnyFriendReq(String username);
        void doGuardarCambios(String username, String dateNaixement, String gendre, String weight, String height, String image/*, String privacity*/);
        void doImageChange(Uri uri);

    }

    interface TaskListener{
        void onSuccess(String message);
        void onError(String error);

        void OnSuccesImageChange(Uri uriImage);

        void setReq(boolean b);
    }
}
