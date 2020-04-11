package com.app.mobilize.Presentador.Interface;

import com.google.firebase.auth.FirebaseUser;

public interface LoginInterface {
    interface View {
        void disableInputs();
        void enableInputs();

        void showPrgress();
        void hideProgress();

        void handleLogin();
        boolean isValidEmail();
        boolean isValidPassword();

        void onError(String error);

        void goMainMenu();
    }

    interface Presenter{
        void toLogin(String email, String password);
    }

    interface Model{
        void doLogin(String email, String password);
    }

    interface TaskListener{
        void onSuccess();
        void onError(String error);
    }
}
